// The "DatabaseApp" Class
import java.awt.*;
import java.io.*;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * The DatabaseApp class creates the window frame, menu bar, menus and menu items
 * containing the various FileIO functions of a virtual address book. This class creates a
 * new instance of the AddressBook class and handles all FileIO operations including
 * "Open", "New" and "Save" functions.
 * 
 * @author Ronald Perlas
 * @version 1.0 March 17 2014
 */
public class DatabaseApp extends JFrame implements ActionListener
{
  /** 
   * d Reference References the JDialog class and makes its methods and features available.
   */
  JDialog d;
  /**
   * a Reference References the AddressBook class, creating a new instance of it
   * and making its methods and features available.
   */
  AddressBook a = new AddressBook ();
  /**
   * fileName Reference References the File class and stores the relevant data regarding the FileIO files.
   */
  File fileName;
  /**
   * fileNameString String Stores the String represenation of the File class used in FileIO operations.
   */
  String fileNameString = "";
  
  /**
   * This constructor is responsible for creating  a new JFrame sized 490 by 400. It also
   * creates the menu bar, menus and menu items as well as adds ActionListeners to these objects.
   * 
   * @param newItem Reference References and creates the "New" JMenuItem.
   * @param quitItem Reference References and creates the "Quit" JMenuItem.
   * @param saveAsItem Reference References and creates the "Save As" JMenuItem.
   * @param saveItem Reference References and creates the "Save" JMenuItem.
   * @param openItem Reference References and creates the "Open" JMenuItem.
   * @param fileMenu Reference References and creates the "File" JMenu.
   * @param myMenus Reference References and creates the JMenuBar.
   */
  public DatabaseApp ()
  {
    super ("DatabaseApp");
    
    add (a);
    
    JMenuItem newItem = new JMenuItem ("New");
    JMenuItem quitItem = new JMenuItem ("Quit");
    JMenuItem saveAsItem = new JMenuItem ("Save As");
    JMenuItem saveItem = new JMenuItem ("Save");
    JMenuItem openItem = new JMenuItem ("Open");
    
    JMenu fileMenu = new JMenu ("File");

    fileMenu.add (newItem);
    fileMenu.add (openItem);
    fileMenu.add (saveAsItem);
    fileMenu.add (saveItem);
    fileMenu.add (quitItem);
    
    JMenuBar myMenus = new JMenuBar ();
    
    quitItem.addActionListener (this);
    saveAsItem.addActionListener (this);
    openItem.addActionListener (this);
    saveItem.addActionListener (this);
    newItem.addActionListener (this);
    
    myMenus.add (fileMenu);
    
    setJMenuBar (myMenus);
    
    setSize (490, 400);
    setVisible (true);
    
    setDefaultCloseOperation (JFrame.DISPOSE_ON_CLOSE);
  }
  
  /**
   * This method uses the ActionListener to detect file menu ActionEvents and invokes
   * the appropriate methods in response.
   *
   * @param ae ActionEvent Stores the action command.
   */
  public void actionPerformed (ActionEvent ae)
  {
    if (ae.getActionCommand().equals("Quit"))
    {
      if (a.unsaved == true)
        unsavedDialog(3);
      else
        System.exit(0);
    }
    if (ae.getActionCommand().equals ("Save As"))
    {
      if (a.blank == true)
        blankDialog();
      else
        saveAsFile();
    }
    if (ae.getActionCommand().equals ("Open"))
    {
      if (a.unsaved == true)
        unsavedDialog(1);
      else
        openFile ();      
    }
    if (ae.getActionCommand().equals ("Save"))
    {
      if (a.blank == true)
        blankDialog();
      else
        saveFile();
    }
    if (ae.getActionCommand().equals ("New"))
    {
      if (a.unsaved == true)
        unsavedDialog(2);
      else 
        newFile();
    }
    if (ae.getActionCommand().equals ("Close"))
    {
      d.dispose();
    }
  }
  
  /**
   * This method creates a new dialog box and prompts the user to save any unsaved files or otherwise
   * continue with their previous action.
   * 
   * @param saveLabel Reference References the "Warnin: Unsaved File" JLabel.
   * @param saveButton Reference References the "Save" JButton.
   * @param ignoreButton Reference References the "Continue" JButton.
   * @param response Integer Stores the JOptionPane user response.
   */
  public void unsavedDialog(final int type)
  {
      int response = JOptionPane.showConfirmDialog(this,
                                                   "Warning: Unsaved File. Would you like to save?",
                                                   "Unsaved File", JOptionPane.YES_NO_CANCEL_OPTION,
                                                   JOptionPane.WARNING_MESSAGE);
      if (response == JOptionPane.YES_OPTION)
      {
        saveFile();
        if (type == 1)
          openFile();
        else if (type == 2)
          newFile();
        else if (type == 3)
          System.exit(0);
      }
      else if (response == JOptionPane.NO_OPTION)
      {
        if (type == 1)
          openFile();
        else if (type == 2)
          newFile();
        else if (type == 3)
          System.exit(0);
      }
      else 
      {
        return;
      }
    }
  
  /**
   * This method clears the previously opened database and creates a blank new instance
   * of the AddressBook class in its place.
   */
  public void newFile()
  {
    setVisible (false);
    remove (a);
    a = new AddressBook ();
    PersonRecord.numRecords = 0;
    add (a);
    a.currentRecord = 0;
    a.clearData();
    setVisible (true);
    a.blank = true;
  }
  
  /**
   * This method saves data onto a previously existing file using FileIO operations.
   * 
   * @param pw Reference References the PrintWriter class and makes its methods and features available.
   * @exception IOException Catches FileIO errors.
   */
  public void saveFile ()
  {
    a.unsaved = false;
    if (!fileNameString.equals (""))
    {
      PrintWriter pw;
      try
      {
        pw = new PrintWriter (new FileWriter (fileNameString));
        pw.println ("Toblerone File Header");
        pw.println(PersonRecord.numRecords);
        for (int x = 0; x < PersonRecord.numRecords; x++)
        {
          pw.println (AddressBook.pr.get(x).getFirstName());
          pw.println (AddressBook.pr.get(x).getLastName());
          pw.println (AddressBook.pr.get(x).getEmail());
          pw.println (AddressBook.pr.get(x).getPhone());
        }
        pw.close();
      }
      catch (IOException a)
      {
      } 
    }
    else
      saveAsFile();
  }
  
  /**
   * This method receives the type of runtime error and creates an appropriate dialog box
   * to inform the user of the error.
   * 
   * @param blankButton Reference References the "Close" JButton.
   * @param blankLabel Reference References the label displaying the error information.
   */
  public void blankDialog ()
  {                                      
    JButton blankButton = new JButton ("Close");
    JLabel blankLabel = new JLabel ("You cannot save a blank file!");
    
    d = new JDialog ();
    d.setSize (200, 100);
    d.setResizable (false);
    d.setLayout (new FlowLayout());
    
    d.add (blankLabel);
    d.add (blankButton);
    blankButton.addActionListener (this);
    
    d.setLocationRelativeTo (this);
    d.setVisible (true);
  }
  
  /**
   * This method closes the current AddressBook and opens a previously existing database in its place
   * using the JFileChooser class.
   * 
   * @param fileChooser Reference References the JFileChooser class, making its methods and features available.
   * @param filter Reference References and creates a new FileNameExtensionFilter class.
   * @param result Integer Stores the action commands of the user in the JFileChooser dialog.
   * @param br Reference References the BufferedReader class, making its methods and features available.
   * @param first String Stores the first name of a previously existing PersonRecord.
   * @param last String Stores the last name of a previously existing PersonRecord.
   * @param email String Stores the email of a previously existing PersonRecord. 
   * @param phone String Stores the phone of a previously existing PersonRecord.
   * @exception IOException Catches FileIO errors.
   */
  public void openFile ()
  {    
    JFileChooser fileChooser = new JFileChooser (".\\");
    FileFilter filter = new FileNameExtensionFilter ("PersonRecord", "toblerone");
    fileChooser.setFileFilter (filter);
    fileChooser.setFileSelectionMode (JFileChooser.FILES_ONLY);    
    int result = fileChooser.showOpenDialog (this);   
    fileName = fileChooser.getSelectedFile ();
    
    if (result == fileChooser.APPROVE_OPTION)
    {
      if (fileName == null || fileName.getName ().equals ("") || fileName.getName ().length () > 255)
      {
        JOptionPane.showMessageDialog (this, "Invalid File Name", "Invalid File Name", JOptionPane.ERROR_MESSAGE);
        fileName = null;
      }
      else
      {
        setVisible (false);
        remove (a);
        a = new AddressBook ();
        PersonRecord.numRecords = 0;
        BufferedReader br;
        if (!fileName.toString().endsWith(".toblerone"))
        {
          fileNameString = fileName.toString() + ".toblerone";
        }
        else
        {
          fileNameString = fileName.toString();
        }
        try
        {
          br = new BufferedReader (new FileReader (fileNameString));
          br.readLine();
          int limit = Integer.parseInt (br.readLine());
          for (int x = 0; x < limit; x++)
          {            
            String first = br.readLine();
            String last = br.readLine ();
            String email = br.readLine ();
            String phone = br.readLine ();
            if (first.equals("") && last.equals("") && phone.equals("") && email.equals(""))
              x = 50;  
            a.pr.add(new PersonRecord (first, last, phone, email));
          }
        }
        catch (IOException e)
        {
          System.out.println ("Error");
        }
        add (a);
        a.currentRecord = 0;
        a.displayData();
        setVisible (true);
      }
    }
  }
  
  /**
   * This method creates a new file to store the current data and allows the user to properly
   * name the file using the JFileChooser class.
   * 
   * @param fileChooser Reference References the JFileChooser class, making its methods and features available.
   * @param filter Reference References and creates a new FileNameExtensionFilter class.
   * @param result Integer Stores the action commands of the user in the JFileChooser dialog.
   * @param pw Reference References the PrintWriter class, making its methods and features available.
   * @param response Integer Stores the overwrite confirmation dialog.
   * @exception IOException Catches FileIO errors.
   */
  public void saveAsFile ()
  {
    JFileChooser fileChooser = new JFileChooser (".\\");
    FileFilter filter = new FileNameExtensionFilter ("PersonRecord", "toblerone");
    fileChooser.setFileFilter (filter);
    fileChooser.setFileSelectionMode (JFileChooser.FILES_ONLY);    
    int result = fileChooser.showSaveDialog (this);   
    fileName = fileChooser.getSelectedFile ();
    
    if (result == fileChooser.APPROVE_OPTION)
    {
      if (fileName.exists())
      {
        int response = JOptionPane.showConfirmDialog(this,
                                                     "The file " + fileName.getName() + 
                                                     " already exists. Do you want to replace the existing file?",
                                                     "Ovewrite file", JOptionPane.YES_NO_OPTION,
                                                     JOptionPane.WARNING_MESSAGE);
        if (response != JOptionPane.YES_OPTION)
          return;
      }
      if (fileName == null || fileName.getName ().equals ("") || fileName.getName ().length () > 255)
      {
        JOptionPane.showMessageDialog (this, "Invalid File Name", "Invalid File Name", JOptionPane.ERROR_MESSAGE);
        fileName = null;
      }
      else
      {
        a.unsaved = false;
        PrintWriter pw;
        
        if (!fileName.toString().endsWith (".toblerone"))
          fileNameString = fileName.toString() + ".toblerone";
        else
          fileNameString = fileName.toString();
        
        try
        {
          pw = new PrintWriter (new FileWriter (fileNameString));
          pw.println ("Toblerone File Header");
          pw.println(PersonRecord.numRecords);
          for (int x = 0; x < PersonRecord.numRecords; x++)
          {
            pw.println (AddressBook.pr.get(x).getFirstName());
            pw.println (AddressBook.pr.get(x).getLastName());
            pw.println (AddressBook.pr.get(x).getEmail());
            pw.println (AddressBook.pr.get(x).getPhone());
          }
          pw.close();
        }
        catch (IOException a)
        {
        }    
      }
    }
  }
  
  /**
   * This main method executes the full program by creating the DatabaseApp class.
   */
  public static void main (String [] args)
  {
    new DatabaseApp ();
  }
  
} // End