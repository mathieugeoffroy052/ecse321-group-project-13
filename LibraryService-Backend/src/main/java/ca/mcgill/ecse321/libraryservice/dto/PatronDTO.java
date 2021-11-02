package ca.mcgill.ecse321.libraryservice.dto;

public class PatronDTO extends UserAccountDTO{
      //------------------------
  // STATIC VARIABLES
  //------------------------

  private static int nextPatronID = 1;

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Patron Attributes
  private boolean validatedAccount;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public PatronDTO() {}

  public PatronDTO(String aFirstName, String aLastName, boolean aOnlineAccount, String aAddress, boolean aValidatedAccount, String aPassWord, int aBalance)
  {
    super(aFirstName, aLastName, aOnlineAccount, aAddress, aPassWord, aBalance);
    validatedAccount = aValidatedAccount;
  }

  public boolean getValidatedAccount()
  {
    return validatedAccount;
  }

}
