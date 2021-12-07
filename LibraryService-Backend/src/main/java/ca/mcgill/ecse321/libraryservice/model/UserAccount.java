/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4607.2d2b84eb8 modeling language!*/

package ca.mcgill.ecse321.libraryservice.model;
import javax.persistence.*;

@Entity
// line 28 "../../../../../../library.ump 15-05-01-147.ump 15-45-27-537.ump 16-05-11-860.ump"
public abstract class UserAccount
{

  //------------------------
  // STATIC VARIABLES
  //------------------------

  private static int nextUserID = 1;

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //UserAccount Attributes
  private String firstName;
  private String lastName;
  private boolean onlineAccount;
  private String password;
  private int balance;
  private String address;
  private String email;
  

  //Autounique Attributes
  private int userID;
  //------------------------
  // CONSTRUCTOR
  //------------------------

  public UserAccount() {
    userID = nextUserID++;
  }

  public UserAccount(String aFirstName, String aLastName, boolean aOnlineAccount, String aAddress, String aPassword, int aBalance, String aEmail)
  {
    firstName = aFirstName;
    lastName = aLastName;
    password = aPassword;
    balance = aBalance;
    email = aEmail;
    onlineAccount = aOnlineAccount;
    userID = nextUserID++;
    address = aAddress;
  }

  //------------------------
  // PRIMARY KEY
  //------------------------

  public boolean setUserID(int aUserID)
  {
    userID = aUserID;
    if(userID==aUserID){
      return true;
    }
    else return false;
  }

  @Id
  public int getUserID()
  {
    return userID;
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setFirstName(String aFirstName)
  {
    boolean wasSet = false;
    firstName = aFirstName;
    wasSet = true;
    return wasSet;
  }

  public boolean setEmail(String aEmail)
  {
    boolean wasSet = false;
    email = aEmail;
    wasSet = true;
    return wasSet;
  }

  public boolean setLastName(String aLastName)
  {
    boolean wasSet = false;
    lastName = aLastName;
    wasSet = true;
    return wasSet;
  }

  public boolean setAddress(String aAddress)
  {
    boolean wasSet = false;
    address = aAddress;
    wasSet = true;
    return wasSet;
  }

  public boolean setPassword(String aPassword)
  {
    boolean wasSet = false;
    password = aPassword;
    wasSet = true;
    return wasSet;
  }

  public boolean setBalance(int aBalance)
  {
    boolean wasSet = false;
    balance = aBalance;
    wasSet = true;
    return wasSet;
  }

  public boolean setOnlineAccount(boolean aOnlineAccount)
  {
    boolean wasSet = false;
    onlineAccount = aOnlineAccount;
    wasSet = true;
    return wasSet;
  }

  public String getFirstName()
  {
    return firstName;
  }

  public String getEmail()
  {
    return email;
  }

  public String getLastName()
  {
    return lastName;
  }

  public String getPassword()
  {
    return password;
  }

  public int getBalance()
  {
    return balance;
  }

  public boolean getOnlineAccount()
  {
    return onlineAccount;
  }

  public String getAddress()
  {
    return address;
  }

  public String toString()
  {
    return super.toString() + "["+
            "userID" + ":" + getUserID()+ "," +
            "firstName" + ":" + getFirstName()+ "," +
            "lastName" + ":" + getLastName()+ "," +
            "balance" + ":" + getBalance()+ ","+
            "onlineAccount" + ":" + getOnlineAccount()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "address = "+(getAddress()!=null?Integer.toHexString(System.identityHashCode(getAddress())):"null");
  }
  
}