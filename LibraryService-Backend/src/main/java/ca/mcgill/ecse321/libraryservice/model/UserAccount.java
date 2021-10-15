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

  //Autounique Attributes
  private int userID;

  //UserAccount Associations
  private LibrarySystem librarySystem;
  private Address address;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public UserAccount(String aFirstName, String aLastName, boolean aOnlineAccount, LibrarySystem aLibrarySystem, Address aAddress)
  {
    firstName = aFirstName;
    lastName = aLastName;
    onlineAccount = aOnlineAccount;
    userID = nextUserID++;
    boolean didAddLibrarySystem = setLibrarySystem(aLibrarySystem);
    if (!didAddLibrarySystem)
    {
      throw new RuntimeException("Unable to create userAccount due to librarySystem. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    if (!setAddress(aAddress))
    {
      throw new RuntimeException("Unable to create UserAccount due to aAddress. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
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

  public boolean setLastName(String aLastName)
  {
    boolean wasSet = false;
    lastName = aLastName;
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

  public String getLastName()
  {
    return lastName;
  }

  public boolean getOnlineAccount()
  {
    return onlineAccount;
  }
  @Id
  public int getUserID()
  {
    return userID;
  }
  /* Code from template association_GetOne */
  public LibrarySystem getLibrarySystem()
  {
    return librarySystem;
  }
  /* Code from template association_GetOne */
  public Address getAddress()
  {
    return address;
  }
  /* Code from template association_SetOneToMany */
  @ManyToOne(optional=false)
  public boolean setLibrarySystem(LibrarySystem aLibrarySystem)
  {
    boolean wasSet = false;
    if (aLibrarySystem == null)
    {
      return wasSet;
    }

    LibrarySystem existingLibrarySystem = librarySystem;
    librarySystem = aLibrarySystem;
    if (existingLibrarySystem != null && !existingLibrarySystem.equals(aLibrarySystem))
    {
      existingLibrarySystem.removeUserAccount(this);
    }
    librarySystem.addUserAccount(this);
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_SetUnidirectionalOne */
  @ManyToOne(optional=false)
  public boolean setAddress(Address aNewAddress)
  {
    boolean wasSet = false;
    if (aNewAddress != null)
    {
      address = aNewAddress;
      wasSet = true;
    }
    return wasSet;
  }

  public void delete()
  {
    LibrarySystem placeholderLibrarySystem = librarySystem;
    this.librarySystem = null;
    if(placeholderLibrarySystem != null)
    {
      placeholderLibrarySystem.removeUserAccount(this);
    }
    address = null;
  }


  public String toString()
  {
    return super.toString() + "["+
            "userID" + ":" + getUserID()+ "," +
            "firstName" + ":" + getFirstName()+ "," +
            "lastName" + ":" + getLastName()+ "," +
            "onlineAccount" + ":" + getOnlineAccount()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "librarySystem = "+(getLibrarySystem()!=null?Integer.toHexString(System.identityHashCode(getLibrarySystem())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "address = "+(getAddress()!=null?Integer.toHexString(System.identityHashCode(getAddress())):"null");
  }
}