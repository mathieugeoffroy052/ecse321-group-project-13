/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4607.2d2b84eb8 modeling language!*/

package ca.mcgill.ecse321.libraryservice.model;
import java.sql.Date;
import javax.persistence.*;

@Entity
// line 82 "../../../../../../library.ump 15-05-01-147.ump 15-45-27-537.ump 16-05-11-860.ump"
public class LibraryItem
{

  //------------------------
  // STATIC VARIABLES
  //------------------------

  private static int nextIsbn = 1;

  public enum ItemType { Book, Room, Movie, Music, NewspaperArticle }

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //LibraryItem Attributes
  private String name;
  private boolean isViewable;
  private Date date;
  private String creator;
  private ItemType itemType;

  //Autounique Attributes
  private int isbn;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public LibraryItem() {
    isbn = nextIsbn++;
  }

  public LibraryItem(String aName, ItemType aItemType, Date aDate, String aCreator, boolean aIsViewable)
  {
    name = aName;
    isbn = nextIsbn++;
    date = aDate;
    itemType = aItemType;
    creator = aCreator;
    isViewable = aIsViewable;
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

  public boolean setDate(Date aDate)
  {
    boolean wasSet = false;
    date = aDate;
    wasSet = true;
    return wasSet;
  }

  public boolean setType(ItemType aItemType){
    boolean wasSet = false;
    itemType = aItemType;
    wasSet = true;
    return wasSet;
  }

  public boolean setIsViewable(boolean aIsViewable){
    boolean wasSet = false;
    isViewable = aIsViewable;
    wasSet = true;
    return wasSet;
  }

  public boolean setIsDate(Date aDate){
    boolean wasSet = false;
    date = aDate;
    wasSet = true;
    return wasSet;
  }

  public boolean setCreator(String aCreator){
    boolean wasSet = false;
    creator = aCreator;
    wasSet = true;
    return wasSet;
  }
  
  public String getCreator()
  {
    return creator;
  }

  public Date getDate()
  {
    return date;
  }

  public String getName()
  {
    return name;
  }

  public ItemType getType()
  {
    return itemType;
  }

  public boolean getIsViewable()
  {
    return isViewable;
  }

  public String toString()
  {
    return super.toString() + "["+
            "isbn" + ":" + getIsbn()+ "," +
            "name" + ":" + getName()+ "]" + System.getProperties().getProperty("line.separator");
  }
}