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
  private Set<BorrowableItem> borrowableItem;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public LibraryItem() {
    isbn = nextIsbn++;
  }

  public LibraryItem(String aName, LibrarySystem aLibrarySystem)
  {
    name = aName;
    isbn = nextIsbn++;
    boolean didAddLibrarySystem = setLibrarySystem(aLibrarySystem);
    if (!didAddLibrarySystem)
    {
      throw new RuntimeException("Unable to create libraryItem due to librarySystem. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  //------------------------
  // PRIMARY KEY
  //------------------------
  
  public boolean setIsbn(int aIsbn)
  {
    isbn = aIsbn;
    if(isbn==aIsbn){
      return true;
    }
    else return false;
  }
  
  @Id
  public int getIsbn()
  {
    return isbn;
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

  public boolean setBorrowableItem(Set<BorrowableItem> aItems)
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

  /* Code from template association_GetOne */
  @ManyToOne(optional=false)
  public LibrarySystem getLibrarySystem()
  {
    return librarySystem;
  }

  @OneToMany(mappedBy = "libraryItem")
  public Set<BorrowableItem> getBorrowableItem()
  {
    return borrowableItem;
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
    librarySystem.setLibraryItems(aLibrarySystem.getLibraryItems());
    wasSet = true;
    return wasSet;
  }

  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfBorrowableItem()
  {
    return 0;
  }

  public void delete()
  {
    LibrarySystem placeholderLibrarySystem = librarySystem;
    this.librarySystem = null;
    if(placeholderLibrarySystem != null)
    {
      placeholderLibrarySystem.removeLibraryItem(this);
    }
    for(BorrowableItem item: borrowableItem)
    {
      item.delete();
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