package ca.mcgill.ecse321.libraryservice.model;
/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4607.2d2b84eb8 modeling language!*/

import javax.persistence.*;
import java.util.*;

@Entity
// line 71 "Library.ump"
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
  private List<BorrowableItem> items;

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
    items = new ArrayList<BorrowableItem>();
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

  public String getName()
  {
    return name;
  }
  @Id
  public int getIsbn()
  {
    return isbn;
  }
  /* Code from template association_GetOne */
  public LibrarySystem getLibrarySystem()
  {
    return librarySystem;
  }
  /* Code from template association_GetMany */
  public BorrowableItem getItem(int index)
  {
    BorrowableItem aItem = items.get(index);
    return aItem;
  }

  public List<BorrowableItem> getItems()
  {
    List<BorrowableItem> newItems = Collections.unmodifiableList(items);
    return newItems;
  }

  public int numberOfItems()
  {
    int number = items.size();
    return number;
  }

  public boolean hasItems()
  {
    boolean has = items.size() > 0;
    return has;
  }

  public int indexOfItem(BorrowableItem aItem)
  {
    int index = items.indexOf(aItem);
    return index;
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
      existingLibrarySystem.removeLibraryItem(this);
    }
    librarySystem.addLibraryItem(this);
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfItems()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public BorrowableItem addItem(BorrowableItem.ItemState aState)
  {
    return new BorrowableItem(aState, this);
  }

  public boolean addItem(BorrowableItem aItem)
  {
    boolean wasAdded = false;
    if (items.contains(aItem)) { return false; }
    LibraryItem existingItemType = aItem.getItemType();
    boolean isNewItemType = existingItemType != null && !this.equals(existingItemType);
    if (isNewItemType)
    {
      aItem.setItemType(this);
    }
    else
    {
      items.add(aItem);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeItem(BorrowableItem aItem)
  {
    boolean wasRemoved = false;
    //Unable to remove aItem, as it must always have a itemType
    if (!this.equals(aItem.getItemType()))
    {
      items.remove(aItem);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addItemAt(BorrowableItem aItem, int index)
  {  
    boolean wasAdded = false;
    if(addItem(aItem))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfItems()) { index = numberOfItems() - 1; }
      items.remove(aItem);
      items.add(index, aItem);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveItemAt(BorrowableItem aItem, int index)
  {
    boolean wasAdded = false;
    if(items.contains(aItem))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfItems()) { index = numberOfItems() - 1; }
      items.remove(aItem);
      items.add(index, aItem);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addItemAt(aItem, index);
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
    for(int i=items.size(); i > 0; i--)
    {
      BorrowableItem aItem = items.get(i - 1);
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