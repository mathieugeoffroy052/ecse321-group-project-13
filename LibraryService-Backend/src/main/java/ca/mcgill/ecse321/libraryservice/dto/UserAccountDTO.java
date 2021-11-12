package ca.mcgill.ecse321.libraryservice.dto;

public class UserAccountDTO {
  private String firstName;
  private String lastName;
  private boolean onlineAccount;
  private String password;
  private int balance;
  private String address;
  private String email;
  private int userID;

  public UserAccountDTO() {}

  public UserAccountDTO(String aFirstName, String aLastName, boolean aOnlineAccount, String aAddress, String aPassword, int aBalance, String aEmail)
  {
    firstName = aFirstName;
    lastName = aLastName;
    password = aPassword;
    balance = aBalance;
    email = aEmail;
    onlineAccount = aOnlineAccount;
    address = aAddress;
  }
 
  public UserAccountDTO(String aFirstName, String aLastName, boolean aOnlineAccount, String aAddress, String aPassword, int aBalance, String aEmail, int aUserID)
  {
    firstName = aFirstName;
    lastName = aLastName;
    password = aPassword;
    balance = aBalance;
    email = aEmail;
    onlineAccount = aOnlineAccount;
    address = aAddress;
    userID = aUserID;
  }

  public String getFirstName()
  {
    return firstName;
  }

  public String getLastName()
  {
    return lastName;
  }

  public String getEmail()
  {
    return email;
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

  public int getUserID()
  {
    return userID;
  }
}
