/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4607.2d2b84eb8 modeling language!*/

package ca.mcgill.ecse321.libraryservice.model;

import javax.persistence.Entity;
import java.util.Set;
import javax.persistence.*;

@Entity
public class BorrowableItem
{

  //------------------------
  // ENUMERATIONS
  //------------------------

  public enum ItemState { Borrowed, Damaged, Available, Reserved }

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //BorrowableItem Attributes
  private int barCodeNumber;
  private ItemState state;

  //BorrowableItem Associations
  private LibraryItem itemType;
  private UserAccount borrower;
  private UserAccount reserver;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public BorrowableItem(int aBarCodeNumber, ItemState aState, LibraryItem aItemType)
  {
    barCodeNumber = aBarCodeNumber;
    state = aState;
    boolean didAddItemType = setItemType(aItemType);
    if (!didAddItemType)
    {
      throw new RuntimeException("Unable to create item due to itemType. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setBarCodeNumber(int aBarCodeNumber)
  {
    boolean wasSet = false;
    barCodeNumber = aBarCodeNumber;
    wasSet = true;
    return wasSet;
  }

  public boolean setState(ItemState aState)
  {
    boolean wasSet = false;
    state = aState;
    wasSet = true;
    return wasSet;
  }

  @Id
  public int getBarCodeNumber()
  {
    return barCodeNumber;
  }

  public ItemState getState()
  {
    return state;
  }
  /* Code from template association_GetOne */
  public LibraryItem getItemType()
  {
    return itemType;
  }
  /* Code from template association_GetOne */
  public UserAccount getBorrower()
  {
    return borrower;
  }

  public boolean hasBorrower()
  {
    boolean has = borrower != null;
    return has;
  }
  /* Code from template association_GetOne */
  public UserAccount getReserver()
  {
    return reserver;
  }

  public boolean hasReserver()
  {
    boolean has = reserver != null;
    return has;
  }
  /* Code from template association_SetOneToMany */
  @ManyToOne(cascade={CascadeType.ALL})
  public boolean setItemType(LibraryItem aItemType)
  {
    boolean wasSet = false;
    if (aItemType == null)
    {
      return wasSet;
    }

    LibraryItem existingItemType = itemType;
    itemType = aItemType;
    if (existingItemType != null && !existingItemType.equals(aItemType))
    {
      existingItemType.removeItem(this);
    }
    itemType.addItem(this);
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_SetOptionalOneToMany */
  @ManyToOne(cascade={CascadeType.ALL})
  public boolean setBorrower(UserAccount aBorrower)
  {
    boolean wasSet = false;
    UserAccount existingBorrower = borrower;
    borrower = aBorrower;
    if (existingBorrower != null && !existingBorrower.equals(aBorrower))
    {
      existingBorrower.removeItemBorrowed(this);
    }
    if (aBorrower != null)
    {
      aBorrower.addItemBorrowed(this);
    }
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_SetOptionalOneToMany */
  @ManyToOne(cascade={CascadeType.ALL})
  public boolean setReserver(UserAccount aReserver)
  {
    boolean wasSet = false;
    UserAccount existingReserver = reserver;
    reserver = aReserver;
    if (existingReserver != null && !existingReserver.equals(aReserver))
    {
      existingReserver.removeItemReserved(this);
    }
    if (aReserver != null)
    {
      aReserver.addItemReserved(this);
    }
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    LibraryItem placeholderItemType = itemType;
    this.itemType = null;
    if(placeholderItemType != null)
    {
      placeholderItemType.removeItem(this);
    }
    if (borrower != null)
    {
      UserAccount placeholderBorrower = borrower;
      this.borrower = null;
      placeholderBorrower.removeItemBorrowed(this);
    }
    if (reserver != null)
    {
      UserAccount placeholderReserver = reserver;
      this.reserver = null;
      placeholderReserver.removeItemReserved(this);
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "barCodeNumber" + ":" + getBarCodeNumber()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "state" + "=" + (getState() != null ? !getState().equals(this)  ? getState().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "itemType = "+(getItemType()!=null?Integer.toHexString(System.identityHashCode(getItemType())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "borrower = "+(getBorrower()!=null?Integer.toHexString(System.identityHashCode(getBorrower())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "reserver = "+(getReserver()!=null?Integer.toHexString(System.identityHashCode(getReserver())):"null");
  }
}