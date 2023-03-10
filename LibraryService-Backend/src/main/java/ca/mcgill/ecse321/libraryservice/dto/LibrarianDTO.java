package ca.mcgill.ecse321.libraryservice.dto;

public class LibrarianDTO extends UserAccountDTO{

  public LibrarianDTO() {}

  public LibrarianDTO(String aFirstName, String aLastName, boolean aOnlineAccount, String aAddress, String aPassword, int aBalance, String aEmail)
  {
    super(aFirstName, aLastName, aOnlineAccount,aAddress, aPassword, aBalance, aEmail);
  }

  public LibrarianDTO(String aFirstName, String aLastName, boolean aOnlineAccount, String aAddress, String aPassword, int aBalance, String aEmail, int aLibrarianID)
  {
    super(aFirstName, aLastName, aOnlineAccount,aAddress, aPassword, aBalance, aEmail, aLibrarianID);
  }

}
