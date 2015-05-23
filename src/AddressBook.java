// The "AdressBook" Class
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.util.*;

/**
 * The AddressBook class creates a new JPanel which contains the text fields and buttons which act as a user
 * interface for a database. This class acts as thee user interface which allows the user to enter and manipulate 
 * an array of contact records.
 * 
 * @author Ronald Perlas
 * @version 1.0 March 17 2014
 */
public class AddressBook extends JPanel implements ActionListener
{
  /**
   * d Reference References the JDialog class, making its methods and features available.
   */
  JDialog d;
  /**
   * blank Boolean Stores the blank status of a record: whether the current record is blank or not.
   */
  static boolean blank = true;
  /**
   * pr Reference References and creates an ArrayList of instances of the PersonRecord class.
   */
  static ArrayList <PersonRecord> pr = new ArrayList <PersonRecord> ();
  /**
   * currentRecord Integer Stores the number of the record which the user is currently viewing.
   */
  static int currentRecord = 0;
  /**
   * unsaved Boolean Stores the save status of a file: whether the current data has been saved or not.
   */
  static boolean unsaved = false;
  /**
   * firstInputField Reference References the JTextField class for entering first names.
   */
  final JTextField firstInputField;
  /**
   * lastInputField Reference References the JTextField class for entering last names.
   */
  final JTextField lastInputField;
  /**
   * mailInputField Reference References the JTextField class for entering emails.
   */
  final JTextField mailInputField;
  /**
   * phoneInputField Reference References the JTextField class for entering fphone numbers.
   */
  final JTextField phoneInputField;
  /**
   * recordOutputField Reference References the JTextField class for displaying the current record
   * over the total number of records.
   */
  final JTextField recordOutputField;
  
  /**
   * This constructor creates the labels, buttons and text fields in the JPanel for the database; 
   * creating a user interface for data display and manipulation.
   * 
   * @param firstField Reference References the "Please enter a first name: " JLabel.
   * @param lastField Reference References the "Please enter a last name: " JLabel.
   * @param mailField Reference References the "Please enter an email: " JLabel.
   * @param phoneField Reference References the "Please enter a phone number: " JLabel.
   * @param recordField Reference References the "Rec, #" JLabel.
   * @param enter Reference References the "Enter" JButton.
   * @param display Reference References the "Display" JButton.
   * @param clear Reference References the "Clear" JButton.
   * @param addRecord Reference References the "New" JButton.
   * @param forward Reference References the "Forward" JButton.
   * @param backward Reference References the "Backward" JButton.
   * @param destroy Reference References the "Destroy" JButton.
   */
  public AddressBook()
  {
    JLabel firstField, lastField, mailField, phoneField, recordField;
    JButton enter = new JButton ("Enter");
    JButton display = new JButton ("Display");
    JButton clear = new JButton ("Clear");
    JButton addRecord = new JButton ("New");
    JButton forward = new JButton ("Forward");
    JButton backward = new JButton ("Backward");
    JButton destroy = new JButton ("Destroy");
    
    firstField = new JLabel ("Please enter a first name: ");
    lastField = new JLabel ("Please enter a last name: ");
    mailField = new JLabel ("Please enter an email: ");
    phoneField = new JLabel ("Please enter a phone number: ");
    recordField = new JLabel ("Rec. #");
    
    firstInputField = new JTextField (20);
    lastInputField = new JTextField (20);
    mailInputField = new JTextField (20);
    phoneInputField = new JTextField (20);
    recordOutputField = new JTextField (5);
    recordOutputField.setEditable (false);
    
    add (firstField);
    add (firstInputField);
    add (lastField);
    add (lastInputField);
    add (mailField);
    add (mailInputField);
    add (phoneField);
    add (phoneInputField);
    add (enter);
    enter.addActionListener(this);
    add (display);
    display.addActionListener(this);
    add (clear);
    clear.addActionListener (this);
    add (addRecord);
    addRecord.addActionListener (this);
    add (forward);
    forward.addActionListener (this);
    add (backward);
    backward.addActionListener (this);
    add (destroy);
    destroy.addActionListener (this);
    add (recordField);
    add (recordOutputField);
    
    recordOutputField.setText ("" + Integer.toString (currentRecord + 1) + "/" + Integer.toString(PersonRecord.numRecords));
  }
  
  /**
   * This method uses the ActionListener to detect file menu ActionEvents and invokes
   * the appropriate methods in response.
   *
   * @param ae ActionEvent Stores the action command.
   */
  public void actionPerformed (ActionEvent ae)
  {
    if (ae.getActionCommand().equals ("Enter"))
    {
      unsaved = true;
      askData ();
      recordOutputField.setText ("" + Integer.toString (currentRecord + 1) + "/" + Integer.toString(PersonRecord.numRecords));
    }
    else if (ae.getActionCommand().equals ("Display") && PersonRecord.numRecords > 0)
    {
      displayData();
    }
    else if (ae.getActionCommand().equals ("Clear"))
    {
      clearData();
    }
    else if (ae.getActionCommand().equals ("New") && currentRecord < PersonRecord.numRecords)
    {
      clearData ();
      currentRecord = PersonRecord.numRecords;
      recordOutputField.setText ("" + Integer.toString (currentRecord + 1) + "/" + Integer.toString(PersonRecord.numRecords));
    }
    else if (ae.getActionCommand().equals ("Forward") && currentRecord + 1 >= PersonRecord.numRecords && PersonRecord.numRecords != 0)
    {
      currentRecord = 0;
      displayData();
    }
    else if (ae.getActionCommand().equals ("Forward") && PersonRecord.numRecords > currentRecord + 1)
    {
      currentRecord += 1;
      displayData();
      recordOutputField.setText ("" + Integer.toString (currentRecord + 1) + "/" + Integer.toString(PersonRecord.numRecords));
    }
    else if (ae.getActionCommand().equals ("Backward") && currentRecord > 0)
    {
      currentRecord -= 1;
      displayData();
      recordOutputField.setText ("" + Integer.toString (currentRecord + 1) + "/" + Integer.toString(PersonRecord.numRecords));
    }
    else if (ae.getActionCommand().equals ("Backward") && currentRecord <= 0 && PersonRecord.numRecords != 0)
    {
      currentRecord = PersonRecord.numRecords - 1;
      displayData();
    }
    else if (ae.getActionCommand().equals ("Destroy") && currentRecord <= 0 && PersonRecord.numRecords != 0)
    {
      pr.remove (currentRecord);
      PersonRecord.numRecords--;
      clearData();
    }
    else if (ae.getActionCommand().equals ("Destroy") && currentRecord < PersonRecord.numRecords && PersonRecord.numRecords != 0)
    {
      pr.remove (currentRecord);
      currentRecord -= 1;
      PersonRecord.numRecords--;
      displayData();
    }
    else if (ae.getActionCommand().equals ("Close"))
    {
      d.dispose();
    }
  }

  /**
   * This method receives data from the user and stores it in a new or existing
   * instance of the PersonRecord class.
   * 
   * @param first String Stores the text entered by the user as the first name.
   * @param last String Stores the text entered by the user as the last name.
   * @param mail String Stores the text entered by the user as the email.
   * @param phone String Stores the text entered by the user as the phone number.
   */
  public void askData ()
  {
    String first = firstInputField.getText();
    String last = lastInputField.getText();
    String mail = mailInputField.getText();
    String phone = phoneInputField.getText();
    if (first.equals("") && last.equals("") && mail.equals ("") && phone.equals(""))
    {
      errorDialog(2);
    }
    else if (DataCheck.emailCheck(mail) == false)
    {
      errorDialog(3);
    }
    else if (DataCheck.phoneCheck(phone) == false)
    {
      errorDialog(4);
    }
    else if (currentRecord < PersonRecord.numRecords)
    {
      PersonRecord.numRecords--;
      pr.add(new PersonRecord (first, last, phone, mail));
      blank = false;
    }
    else
    {
      pr.add(new PersonRecord (first, last, phone, mail));
      blank = false;
    }
  }
  
  /**
   * This method displays the correctly formated data stored in the currently viewed record
   * in the appropriate text fields.
   */
  public void displayData ()
  {
    firstInputField.setText (pr.get(currentRecord).getFirstName());
    lastInputField.setText (pr.get(currentRecord).getLastName());
    mailInputField.setText (pr.get(currentRecord).getEmail());
    phoneInputField.setText (pr.get(currentRecord).getPhone());
    recordOutputField.setText ("" + Integer.toString (currentRecord + 1) + "/" + Integer.toString(PersonRecord.numRecords));
  }
  
  /**
   * This method clears the text fields of all texts.
   */
  public void clearData ()
  {
    firstInputField.setText ("");
    lastInputField.setText ("");
    mailInputField.setText ("");
    phoneInputField.setText ("");    
    recordOutputField.setText ("" + Integer.toString (currentRecord + 1) + "/" + Integer.toString(PersonRecord.numRecords));
  }
  
  /**
   * This method receives the type of runtime error and creates an appropriate dialog box
   * to inform the user of the error.
   * 
   * @param errorButton Reference References the "Close" JButton.
   * @param errorLabel Reference References the label displaying the error information.
   */
  public void errorDialog (int type)
  {                                      
    JButton errorButton = new JButton ("Close");
    JLabel errorLabel;
    
    if (type == 1)
      errorLabel = new JLabel ("You are currently at the final record.");
    else if (type == 2)
      errorLabel = new JLabel ("You must enter at least one field!");
    else if (type == 3)
      errorLabel = new JLabel ("Please enter a valid email!");
    else
      errorLabel = new JLabel ("Please enter a valid phone number!");
    
    d = new JDialog ();
    d.setSize (250, 100);
    d.setResizable (false);
    d.setLayout (new FlowLayout());
    
    d.add (errorLabel);
    d.add (errorButton);
    errorButton.addActionListener (this);
    
    d.setLocationRelativeTo (this);
    d.setVisible (true);
  }
  
} //End