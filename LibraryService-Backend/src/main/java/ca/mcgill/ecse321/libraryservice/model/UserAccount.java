/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4607.2d2b84eb8 modeling language!*/

package ca.mcgill.ecse321.libraryservice.model;
import java.util.*;

import javax.persistence.*;
import java.util.Set;

@Entity
public abstract class UserAccount
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //UserAccount Attributes
  private String userID;
  private String firstName;
  private String lastName;
  private boolean onlineAccount;

  //UserAccount Associations
  private LibrarySystem librarySystem;
  private Address address;
  private List<BorrowableItem> itemBorrowed;
  private List<BorrowableItem> itemReserved;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public UserAccount(String aUserID, String aFirstName, String aLastName, boolean aOnlineAccount, LibrarySystem aLibrarySystem, Address aAddress)
  {
    userID = aUserID;
    firstName = aFirstName;
    lastName = aLastName;
    onlineAccount = aOnlineAccount;
    boolean didAddLibrarySystem = setLibrarySystem(aLibrarySystem);
    if (!didAddLibrarySystem)
    {
      throw new RuntimeException("Unable to create userAccount due to librarySystem. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    if (!setAddress(aAddress))
    {
      throw new RuntimeException("Unable to create UserAccount due to aAddress. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    itemBorrowed = new ArrayList<BorrowableItem>();
    itemReserved = new ArrayList<BorrowableItem>();
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setUserID(String aUserID)
  {
    boolean wasSet = false;
    userID = aUserID;
    wasSet = true;
    return wasSet;
  }

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

  @Id
  public String getUserID()
  {
    return userID;
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
  /* Code from template association_GetMany */
  public BorrowableItem getItemBorrowed(int index)
  {
    BorrowableItem aItemBorrowed = itemBorrowed.get(index);
    return aItemBorrowed;
  }

  public List<BorrowableItem> getItemBorrowed()
  {
    List<BorrowableItem> newItemBorrowed = Collections.unmodifiableList(itemBorrowed);
    return newItemBorrowed;
  }

  public int numberOfItemBorrowed()
  {
    int number = itemBorrowed.size();
    return number;
  }

  public boolean hasItemBorrowed()
  {
    boolean has = itemBorrowed.size() > 0;
    return has;
  }

  public int indexOfItemBorrowed(BorrowableItem aItemBorrowed)
  {
    int index = itemBorrowed.indexOf(aItemBorrowed);
    return index;
  }
  /* Code from template association_GetMany */
  public BorrowableItem getItemReserved(int index)
  {
    BorrowableItem aItemReserved = itemReserved.get(index);
    return aItemReserved;
  }

  public List<BorrowableItem> getItemReserved()
  {
    List<BorrowableItem> newItemReserved = Collections.unmodifiableList(itemReserved);
    return newItemReserved;
  }

  public int numberOfItemReserved()
  {
    int number = itemReserved.size();
    return number;
  }

  public boolean hasItemReserved()
  {
    boolean has = itemReserved.size() > 0;
    return has;
  }

  public int indexOfItemReserved(BorrowableItem aItemReserved)
  {
    int index = itemReserved.indexOf(aItemReserved);
    return index;
  }
  /* Code from template association_SetOneToMany */
  @ManyToOne(cascade={CascadeType.ALL})
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
  @ManyToOne(cascade={CascadeType.ALL})
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
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfItemBorrowed()
  {
    return 0;
  }
  /* Code from template association_AddManyToOptionalOne */
  public boolean addItemBorrowed(BorrowableItem aItemBorrowed)
  {
    boolean wasAdded = false;
    if (itemBorrowed.contains(aItemBorrowed)) { return false; }
    UserAccount existingBorrower = aItemBorrowed.getBorrower();
    if (existingBorrower == null)
    {
      aItemBorrowed.setBorrower(this);
    }
    else if (!this.equals(existingBorrower))
    {
      existingBorrower.removeItemBorrowed(aItemBorrowed);
      addItemBorrowed(aItemBorrowed);
    }
    else
    {
      itemBorrowed.add(aItemBorrowed);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeItemBorrowed(BorrowableItem aItemBorrowed)
  {
    boolean wasRemoved = false;
    if (itemBorrowed.contains(aItemBorrowed))
    {
      itemBorrowed.remove(aItemBorrowed);
      aItemBorrowed.setBorrower(null);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addItemBorrowedAt(BorrowableItem aItemBorrowed, int index)
  {  
    boolean wasAdded = false;
    if(addItemBorrowed(aItemBorrowed))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfItemBorrowed()) { index = numberOfItemBorrowed() - 1; }
      itemBorrowed.remove(aItemBorrowed);
      itemBorrowed.add(index, aItemBorrowed);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveItemBorrowedAt(BorrowableItem aItemBorrowed, int index)
  {
    boolean wasAdded = false;
    if(itemBorrowed.contains(aItemBorrowed))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfItemBorrowed()) { index = numberOfItemBorrowed() - 1; }
      itemBorrowed.remove(aItemBorrowed);
      itemBorrowed.add(index, aItemBorrowed);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addItemBorrowedAt(aItemBorrowed, index);
    }
    return wasAdded;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfItemReserved()
  {
    return 0;
  }
  /* Code from template association_AddManyToOptionalOne */
  public boolean addItemReserved(BorrowableItem aItemReserved)
  {
    boolean wasAdded = false;
    if (itemReserved.contains(aItemReserved)) { return false; }
    UserAccount existingReserver = aItemReserved.getReserver();
    if (existingReserver == null)
    {
      aItemReserved.setReserver(this);
    }
    else if (!this.equals(existingReserver))
    {
      existingReserver.removeItemReserved(aItemReserved);
      addItemReserved(aItemReserved);
    }
    else
    {
      itemReserved.add(aItemReserved);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeItemReserved(BorrowableItem aItemReserved)
  {
    boolean wasRemoved = false;
    if (itemReserved.contains(aItemReserved))
    {
      itemReserved.remove(aItemReserved);
      aItemReserved.setReserver(null);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addItemReservedAt(BorrowableItem aItemReserved, int index)
  {  
    boolean wasAdded = false;
    if(addItemReserved(aItemReserved))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfItemReserved()) { index = numberOfItemReserved() - 1; }
      itemReserved.remove(aItemReserved);
      itemReserved.add(index, aItemReserved);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveItemReservedAt(BorrowableItem aItemReserved, int index)
  {
    boolean wasAdded = false;
    if(itemReserved.contains(aItemReserved))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfItemReserved()) { index = numberOfItemReserved() - 1; }
      itemReserved.remove(aItemReserved);
      itemReserved.add(index, aItemReserved);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addItemReservedAt(aItemReserved, index);
    }
    return wasAdded;
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
    while( !itemBorrowed.isEmpty() )
    {
      itemBorrowed.get(0).setBorrower(null);
    }
    while( !itemReserved.isEmpty() )
    {
      itemReserved.get(0).setReserver(null);
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "onlineAccount" + ":" + getOnlineAccount()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "userID" + "=" + (getUserID() != null ? !getUserID().equals(this)  ? getUserID().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "firstName" + "=" + (getFirstName() != null ? !getFirstName().equals(this)  ? getFirstName().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "lastName" + "=" + (getLastName() != null ? !getLastName().equals(this)  ? getLastName().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "librarySystem = "+(getLibrarySystem()!=null?Integer.toHexString(System.identityHashCode(getLibrarySystem())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "address = "+(getAddress()!=null?Integer.toHexString(System.identityHashCode(getAddress())):"null");
  }
}