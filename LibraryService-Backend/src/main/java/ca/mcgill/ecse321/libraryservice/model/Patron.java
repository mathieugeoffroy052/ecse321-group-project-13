package ca.mcgill.ecse321.libraryservice.model;
/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4607.2d2b84eb8 modeling language!*/

import javax.persistence.*;
import java.util.*;

@Entity
// line 34 "Library.ump"
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

  public Patron(String aFirstName, String aLastName, boolean aOnlineAccount, LibrarySystem aLibrarySystem, Address aAddress, boolean aValidatedAccount)
  {
    super(aFirstName, aLastName, aOnlineAccount, aLibrarySystem, aAddress);
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
  @Id
  public int getPatronID()
  {
    return patronID;
  }

  public void delete()
  {
    super.delete();
  }


  public String toString()
  {
    return super.toString() + "["+
            "patronID" + ":" + getPatronID()+ "," +
            "validatedAccount" + ":" + getValidatedAccount()+ "]";
  }
}