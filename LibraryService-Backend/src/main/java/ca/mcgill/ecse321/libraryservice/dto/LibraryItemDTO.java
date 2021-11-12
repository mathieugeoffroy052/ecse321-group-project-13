package ca.mcgill.ecse321.libraryservice.dto;
import java.sql.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

public class LibraryItemDTO {

  private String name;
  private boolean isViewable;
  @JsonFormat(pattern = "yyyy-MM-dd")
  private Date date;
  private String creator;
  private String itemType;
  private int isbn;

  public LibraryItemDTO() {
  }

  public LibraryItemDTO(String aName, String aItemType, Date aDate, String aCreator, boolean aIsViewable)
  {
    this.name = aName;
    this.date = aDate;
    this.itemType = aItemType;
    this.creator = aCreator;
    this.isViewable = aIsViewable;
  }

  public LibraryItemDTO(String aName, String aItemType, Date aDate, String aCreator, boolean aIsViewable, int isbn)
  {
    this.name = aName;
    this.date = aDate;
    this.itemType = aItemType;
    this.creator = aCreator;
    this.isViewable = aIsViewable;
    this.isbn = isbn;
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

  public String getType()
  {
    return this.itemType;
  }

  public boolean getIsViewable()
  {
    return isViewable;
  }
 
	public int getIsbn() {
		return isbn;
	}

}
