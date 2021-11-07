package ca.mcgill.ecse321.libraryservice.dto;

public class PatronDTO extends UserAccountDTO{
  private boolean validatedAccount;

  public PatronDTO() {}

  public PatronDTO(String aFirstName, String aLastName, boolean aOnlineAccount, String aAddress, boolean aValidatedAccount, String aPassWord, int aBalance, String aEmail)
  {
    super(aFirstName, aLastName, aOnlineAccount, aAddress, aPassWord, aBalance, aEmail);
    validatedAccount = aValidatedAccount;
  }

  public boolean getValidatedAccount()
  {
    return validatedAccount;
  }

}