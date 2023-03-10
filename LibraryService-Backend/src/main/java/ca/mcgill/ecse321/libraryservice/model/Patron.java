/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4607.2d2b84eb8 modeling language!*/

package ca.mcgill.ecse321.libraryservice.model;
import javax.persistence.*;

@Entity
// line 38 "../../../../../../library.ump 15-05-01-147.ump 15-45-27-537.ump 16-05-11-860.ump"
public class Patron extends UserAccount
{

  //------------------------
  // STATIC VARIABLES
  //------------------------

  private static int nextPatronID = 1;

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Patron Attributes
  private boolean validatedAccount;

  //Autounique Attributes
  private int patronID;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Patron() {
    super();
    patronID = nextPatronID++;
  }

  public Patron(String aFirstName, String aLastName, boolean aOnlineAccount, String aAddress, boolean aValidatedAccount, String aPassWord, int aBalance, String aEmail)
  {
    super(aFirstName, aLastName, aOnlineAccount, aAddress, aPassWord, aBalance, aEmail);
    validatedAccount = aValidatedAccount;
    patronID = nextPatronID++;
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

  public int getPatronID()
  {
    return patronID;
  }

  public boolean setPatronID(int aPatronID)
  {
    patronID = aPatronID;
    if(patronID==aPatronID){
      return true;
    }
    else return false;
  }


  public String toString()
  {
    return super.toString() + "["+
            "patronID" + ":" + getPatronID()+ "," +
            "validatedAccount" + ":" + getValidatedAccount()+ "]";
  }
}