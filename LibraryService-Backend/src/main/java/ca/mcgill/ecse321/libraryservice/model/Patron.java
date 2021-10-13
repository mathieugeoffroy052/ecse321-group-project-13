/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4607.2d2b84eb8 modeling language!*/

package ca.mcgill.ecse321.libraryservice.model;
import java.util.*;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Patron extends UserAccount
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Patron Attributes
  private boolean validatedAccount;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Patron(String aUserID, String aFirstName, String aLastName, boolean aOnlineAccount, LibrarySystem aLibrarySystem, Address aAddress, boolean aValidatedAccount)
  {
    super(aUserID, aFirstName, aLastName, aOnlineAccount, aLibrarySystem, aAddress);
    validatedAccount = aValidatedAccount;
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setValidatedAccount(boolean aValidatedAccount)
  {
    boolean wasSet = false;
    validatedAccount = aValidatedAccount;
    wasSet = true;
    return wasSet;
  }

  public boolean getValidatedAccount()
  {
    return validatedAccount;
  }

  public void delete()
  {
    super.delete();
  }


  public String toString()
  {
    return super.toString() + "["+
            "validatedAccount" + ":" + getValidatedAccount()+ "]";
  }
}