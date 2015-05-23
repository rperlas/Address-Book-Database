/**
 * The "PersonRecord" class stores the first name, last name, email address and phone number as required by the
 * AddressBook class. It contains the appropriate methods to manipulate and properly format these four variables.
 * 
 * @author Ronald Perlas
 * @author Ms. Dyke
 * @version 1.0 March 17 2014
 */
public class PersonRecord 
{
  /**
   * first String Stores the first name.
   */
  private String first;
  /**
   * last String Stores the last name.
   */
  private String last;
  /**
   * email String Stores the email address.
   */
  private String email; 
  /**
   * phone String Stores the phone number.
   */
  private String phone;
  /**
   * numRecords Integer Stores the total amount of records created.
   */
  public static int numRecords = 0;
  
  /**
   * This constructor stores the values for the first name, last name, email address and phone number.
   */
  public PersonRecord (String first, String last, String phone, String email)
  {
    this.first = formatName(first);
    this.last = formatName(last);
    this.email = email;
    this.phone = formatPhone(phone);
    numRecords += 1;
  }
  
  /**
   * This constructor stores the values for the first name, last name and phone number.
   */
  public PersonRecord (String first, String last, String phone)
  {
    this.first = first;
    this.last = last;
    this.phone = phone;
    numRecords += 1;
  }
  
  /**
   * This constructor stores the values for the first name and last name.
   */
  public PersonRecord (String first, String last)
  {
    this.first = first;
    this.last = last;
    numRecords += 1;
  }
  
  /**
   * This constructor stores the values for the first name.
   */
  public PersonRecord (String first)
  {
    this.first = first;
    numRecords += 1;
  }
  
  /**
   * This constructor creates the PersonRecord class.
   */
  public PersonRecord ()
  {
    numRecords -=1;
  }
  
  /**
   * This mutator method sets the value of the first name.
   * 
   * @param newName String Stores the new name.
   */
  public void setFirst (String newName)
  {
    first = newName;
  }
  
  /**
   * This accessor method gets the value of the first name.
   */
  public String getFirstName ()
  {
    return first;
  }
 
  /**
   * This mutator method sets the value of the last name.
   * 
   * @param newName String Stores the new name.
   */
  public void setLast (String newName)
  {
    last = newName;
  }

  /**
   * This accessor method gets the value of the last name.
   */
  public String getLastName ()
  {
    return last;
  }
  
  /**
   * This mutator method sets the value of the phone number.
   * 
   * @param newPhone String Stores the new phone number.
   */
  public void setPhone (String newPhone)
  {
    phone = newPhone;
  }
  
  /**
   * This accessor method gets the value of the phone number.
   */
  public String getPhone ()
  {
    return phone;
  }
  
  /**
   * This mutator method sets the value of the email address.
   * 
   * @param newMail String Stores the new email.
   */
  public void setEmail (String newMail)
  {
    email = newMail;
  }
  
  /**
   * This accessor method gets the value of the email address.
   */
  public String getEmail ()
  {
    return email;
  }
  
  /**
   * This method formats the first or last names by capitalizing the fiest letter.
   * 
   * @param original String Stores the original name's first character.
   * @param capital String Stores the capitalized first character.
   * @param name String Stores the name.
   */
  private String formatName (String name)
  {
    String original, capital;
    
    if (!name.equals (""))
    {
    original = Character.toString(name.charAt(0));
    capital = original.toUpperCase();
    name = capital + name.substring(1);
    }
    
    return name;
  }
  
  /**
   * This method formats the phone number properly in the XXX-XXX-XXXX format.
   * 
   * @param newPhone String Stores the new phone number.
   * @param first String Stores the first three numbers.
   * @param second String Stores the second three numbers.
   * @param third String Stores the third three numbers.
   */
  private String formatPhone (String phone)
  {
    String newPhone = "", first, second, third;
    
    if (!phone.equals(""))
    {
      for (int x = 0; x < phone.length(); x++)
      {
        if (phone.charAt (x) == '1' || phone.charAt (x) == '2' || phone.charAt (x) == '3' || phone.charAt (x) == '4' || phone.charAt (x) == '5' || phone.charAt (x) == '6' || phone.charAt (x) == '7' || phone.charAt (x) == '8' || phone.charAt (x) == '9' || phone.charAt (x) == '0')
          newPhone = newPhone + phone.charAt(x);
      }
      first = newPhone.substring (0,3);
      second = newPhone.substring (3,6);
      third = newPhone.substring (6);
      
      phone = first + "-" + second + "-" + third;
    }
    
    return phone;
  }
  
}













// End