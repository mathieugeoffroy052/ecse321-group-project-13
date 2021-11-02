package ca.mcgill.ecse321.libraryservice.dto;
import java.util.*;
import javax.persistence.*;
public class LibraryItemDTO {

  private enum ItemType { Book, Room, Movie, Music, NewspaperArticle }

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //LibraryItem Attributes
  private String name;
  private boolean isViewable;
  private Date date;
  private String creator;
  private ItemType itemType;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public LibraryItemDTO() {
  }

  public LibraryItemDTO(String aName, ItemType aItemType, Date aDate, String aCreator, boolean aIsViewable)
  {
    name = aName;
    date = aDate;
    itemType = aItemType;
    creator = aCreator;
    isViewable = aIsViewable;
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

}
