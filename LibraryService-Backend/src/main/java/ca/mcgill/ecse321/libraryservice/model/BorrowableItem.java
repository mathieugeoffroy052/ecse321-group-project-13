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

  public enum ItemState { Borrowed, Damaged, Available, Reserved }

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
  private LibraryItem itemType;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public BorrowableItem(ItemState aState, LibraryItem aItemType)
  {
    state = aState;
    barCodeNumber = nextBarCodeNumber++;
    boolean didAddItemType = setItemType(aItemType);
    if (!didAddItemType)
    {
      throw new RuntimeException("Unable to create item due to itemType. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
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

  @Id
  public int getBarCodeNumber()
  {
    return barCodeNumber;
  }

  public boolean setBarCodeNumber(int aBarCodeNumber)
  {
    barCodeNumber = aBarCodeNumber;
    if(barCodeNumber==aBarCodeNumber){
      return true;
    }
    else return false;
  }
  /* Code from template association_GetOne */
  @ManyToOne(optional=false)
  public LibraryItem getItemType()
  {
    return itemType;
  }
  /* Code from template association_SetOneToMany */
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

  public void delete()
  {
    LibraryItem placeholderItemType = itemType;
    this.itemType = null;
    if(placeholderItemType != null)
    {
      placeholderItemType.removeItem(this);
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "barCodeNumber" + ":" + getBarCodeNumber()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "state" + "=" + (getState() != null ? !getState().equals(this)  ? getState().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "itemType = "+(getItemType()!=null?Integer.toHexString(System.identityHashCode(getItemType())):"null");
  }
}