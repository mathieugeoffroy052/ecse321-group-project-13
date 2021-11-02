package ca.mcgill.ecse321.libraryservice.dto;

public class UserAccountDTO {
    
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

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public UserAccountDTO() {}

  public UserAccountDTO(String aFirstName, String aLastName, boolean aOnlineAccount, String aAddress, String aPassword, int aBalance)
  {
    firstName = aFirstName;
    lastName = aLastName;
    password = aPassword;
    balance = aBalance;
    onlineAccount = aOnlineAccount;
    address = aAddress;
  }

  public String getFirstName()
  {
    return firstName;
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
}
