/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4607.2d2b84eb8 modeling language!*/

package ca.mcgill.ecse321.libraryservice.model;
import javax.persistence.*;

@Entity
// line 63 "../../../../../../library.ump 15-05-01-147.ump 15-45-27-537.ump 16-05-11-860.ump"
public class BorrowableItem
{

  //------------------------
  // ENUMERATIONS
  //------------------------

  public enum ItemState { Borrowed, Damaged, Available, Reserved, Booked }

  //------------------------
  // STATIC VARIABLES
  //------------------------

  private static int nextBarCodeNumber = 1;

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //BorrowableItem Attributes
  private ItemState state;

  //Autounique Attributes
  private int barCodeNumber;

  //BorrowableItem Associations
  private LibraryItem libraryItem;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public BorrowableItem() {
    barCodeNumber = nextBarCodeNumber++;
  }

  public BorrowableItem(ItemState aState, LibraryItem aLibraryItem)
  {
    state = aState;
    barCodeNumber = nextBarCodeNumber++;
    boolean didAddLibraryItem = setLibraryItem(aLibraryItem);
    if (!didAddLibraryItem)
    {
      throw new RuntimeException("Unable to create item due to libraryItem. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  //------------------------
  // PRIMARY KEY
  //------------------------

  public boolean setBarCodeNumber(int aBarCodeNumber)
  {
    barCodeNumber = aBarCodeNumber;
    if(barCodeNumber==aBarCodeNumber){
      return true;
    }
    else return false;
  }
  
  @Id
  public int getBarCodeNumber()
  {
    return barCodeNumber;
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setState(ItemState aState)
  {
    boolean wasSet = false;
    state = aState;
    wasSet = true;
    return wasSet;
  }

  public ItemState getState()
  {
    return state;
  }

  /* Code from template association_GetOne */
  @ManyToOne(optional = false)
  public LibraryItem getLibraryItem()
  {
    return libraryItem;
  }

  /* Code from template association_SetOneToMany */
  public boolean setLibraryItem(LibraryItem aLibraryItem)
  {
    boolean wasSet = false;
    if (aLibraryItem == null)
    {
      return wasSet;
    }
    libraryItem.setBorrowableItem(aLibraryItem.getBorrowableItem());
    wasSet = true;
    return wasSet;
  }


  public String toString()
  {
    return super.toString() + "["+
            "barCodeNumber" + ":" + getBarCodeNumber()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "state" + "=" + (getState() != null ? !getState().equals(this)  ? getState().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "libraryItem = "+(getLibraryItem()!=null?Integer.toHexString(System.identityHashCode(getLibraryItem())):"null");
  }
}