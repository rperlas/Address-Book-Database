import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

/**
 * The DataCheck class contains errortrapping methods which check whether the data entered by the user in
 * the AddressBook class is appropriate or erroneous.
 * 
 * @author Ronald Perlas
 * @version 1.0 March 17 2014
 */
public class DataCheck
{
  /**
   * This method receives the entered phone number and checks whether or not it contains the appropriate 10 digits.
   * 
   * @param check Boolean Stores whether or not the entered data is appropriate.
   * @param digits Stores the amount of digits entered by the user.
   */
  public static boolean phoneCheck (String phone)
  {
    boolean check = true;
    int digits = 0;
    
    for (int x = 0; x < phone.length(); x++)
    {
      if (phone.charAt (x) == '1' || phone.charAt (x) == '2' || phone.charAt (x) == '3' || phone.charAt (x) == '4' || phone.charAt (x) == '5' || phone.charAt (x) == '6' || phone.charAt (x) == '7' || phone.charAt (x) == '8' || phone.charAt (x) == '9' || phone.charAt (x) == '0')
        digits++;
    }
    
    if (digits != 10)
      check = false;
    
    if (phone.equals (""))
      check = true;
    
    return check;    
  }
  
  /**
   * This method receives the entered email address and checks whether or not it is valid and properly formatted.
   * 
   * @param check Boolean Stores whether or not the entered data is appropriate.
   */
  public static boolean emailCheck (String email)
  {
    boolean check = true;
    
    if (email.endsWith ("@"))
      check = false;
    if (email.startsWith ("@"))
      check = false;
    if (!email.contains("@"))
      check = false;
    if (email.lastIndexOf ('@') != email.indexOf ('@'))
      check = false;
    if (email.equals (""))
      check = true;
    
    return check;
  }
}