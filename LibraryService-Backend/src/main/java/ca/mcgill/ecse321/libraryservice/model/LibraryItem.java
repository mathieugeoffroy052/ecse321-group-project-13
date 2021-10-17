/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4607.2d2b84eb8 modeling language!*/

package ca.mcgill.ecse321.libraryservice.model;
import java.util.*;
import javax.persistence.*;

@Entity
// line 82 "../../../../../../library.ump 15-05-01-147.ump 15-45-27-537.ump 16-05-11-860.ump"
public abstract class LibraryItem
{

  //------------------------
  // STATIC VARIABLES
  //------------------------

  private static int nextIsbn = 1;

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //LibraryItem Attributes
  private String name;

  //Autounique Attributes
  private int isbn;

  //LibraryItem Associations
  private LibrarySystem librarySystem;
  private List<BorrowableItem> borrowableItem;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public LibraryItem(String aName, LibrarySystem aLibrarySystem)
  {
    name = aName;
    isbn = nextIsbn++;
    boolean didAddLibrarySystem = setLibrarySystem(aLibrarySystem);
    if (!didAddLibrarySystem)
    {
      throw new RuntimeException("Unable to create libraryItem due to librarySystem. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    borrowableItem = new ArrayList<BorrowableItem>();
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setName(String aName)
  {
    boolean wasSet = false;
    name = aName;
    wasSet = true;
    return wasSet;
  }

  public boolean setBorrowableItem(ArrayList<BorrowableItem> aItems)
  {
    boolean wasSet = false;
    borrowableItem = aItems;
    wasSet = true;
    return wasSet;
  }
  
  public String getName()
  {
    return name;
  }
  @Id
  public int getIsbn()
  {
    return isbn;
  }

  public boolean setIsbn(int aIsbn)
  {
    isbn = aIsbn;
    if(isbn==aIsbn){
      return true;
    }
    else return false;
  }

  /* Code from template association_GetOne */
  @ManyToOne(optional=false)
  public LibrarySystem getLibrarySystem()
  {
    return librarySystem;
  }
  /* Code from template association_GetMany */
  public BorrowableItem getBorrowableItem(int index)
  {
    BorrowableItem aItem = borrowableItem.get(index);
    return aItem;
  }
  @OneToMany
  public List<BorrowableItem> getBorrowableItem()
  {
    List<BorrowableItem> newItems = Collections.unmodifiableList(borrowableItem);
    return newItems;
  }

  public int numberOfBorrowableItem()
  {
    int number = borrowableItem.size();
    return number;
  }

  public boolean hasBorrowableItem()
  {
    boolean has = borrowableItem.size() > 0;
    return has;
  }

  public int indexOfBorrowableItem(BorrowableItem aItem)
  {
    int index = borrowableItem.indexOf(aItem);
    return index;
  }
  /* Code from template association_SetOneToMany */
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
      existingLibrarySystem.removeLibraryItem(this);
    }
    librarySystem.addLibraryItem(this);
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfBorrowableItem()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public BorrowableItem addBorrowableItem(BorrowableItem.ItemState aState)
  {
    return new BorrowableItem(aState, this);
  }

  public boolean addBorrowableItem(BorrowableItem aItem)
  {
    boolean wasAdded = false;
    if (borrowableItem.contains(aItem)) { return false; }
    LibraryItem existingLibraryItem = aItem.getLibraryItem();
    boolean isNewLibraryItem = existingLibraryItem != null && !this.equals(existingLibraryItem);
    if (isNewLibraryItem)
    {
      aItem.setLibraryItem(this);
    }
    else
    {
      borrowableItem.add(aItem);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeBorrowableItem(BorrowableItem aItem)
  {
    boolean wasRemoved = false;
    //Unable to remove aItem, as it must always have a libraryItem
    if (!this.equals(aItem.getLibraryItem()))
    {
      borrowableItem.remove(aItem);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addBorrowableItemAt(BorrowableItem aItem, int index)
  {  
    boolean wasAdded = false;
    if(addBorrowableItem(aItem))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfBorrowableItem()) { index = numberOfBorrowableItem() - 1; }
      borrowableItem.remove(aItem);
      borrowableItem.add(index, aItem);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveBorrowableItemAt(BorrowableItem aItem, int index)
  {
    boolean wasAdded = false;
    if(borrowableItem.contains(aItem))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfBorrowableItem()) { index = numberOfBorrowableItem() - 1; }
      borrowableItem.remove(aItem);
      borrowableItem.add(index, aItem);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addBorrowableItemAt(aItem, index);
    }
    return wasAdded;
  }

  public void delete()
  {
    LibrarySystem placeholderLibrarySystem = librarySystem;
    this.librarySystem = null;
    if(placeholderLibrarySystem != null)
    {
      placeholderLibrarySystem.removeLibraryItem(this);
    }
    for(int i=borrowableItem.size(); i > 0; i--)
    {
      BorrowableItem aItem = borrowableItem.get(i - 1);
      aItem.delete();
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "isbn" + ":" + getIsbn()+ "," +
            "name" + ":" + getName()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "librarySystem = "+(getLibrarySystem()!=null?Integer.toHexString(System.identityHashCode(getLibrarySystem())):"null");
  }
}