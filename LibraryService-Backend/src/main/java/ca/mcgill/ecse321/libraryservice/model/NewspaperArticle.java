/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4607.2d2b84eb8 modeling language!*/

package ca.mcgill.ecse321.libraryservice.model;
import java.sql.Date;
import javax.persistence.*;

@Entity
// line 91 "../../../../../../library.ump 15-05-01-147.ump 15-45-27-537.ump"
public class NewspaperArticle
{

  //------------------------
  // STATIC VARIABLES
  //------------------------

  private static int nextBarCodeNumber = 1;

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //NewspaperArticle Attributes
  private Date date;

  //Autounique Attributes
  private int barCodeNumber;

  //NewspaperArticle Associations
  private LibrarySystem librarySystem;
  private Newspaper newspaper;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public NewspaperArticle(Date aDate, LibrarySystem aLibrarySystem, Newspaper aNewspaper)
  {
    date = aDate;
    barCodeNumber = nextBarCodeNumber++;
    boolean didAddLibrarySystem = setLibrarySystem(aLibrarySystem);
    if (!didAddLibrarySystem)
    {
      throw new RuntimeException("Unable to create newspaperArticle due to librarySystem. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    boolean didAddNewspaper = setNewspaper(aNewspaper);
    if (!didAddNewspaper)
    {
      throw new RuntimeException("Unable to create article due to newspaper. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setDate(Date aDate)
  {
    boolean wasSet = false;
    date = aDate;
    wasSet = true;
    return wasSet;
  }

  public Date getDate()
  {
    return date;
  }
  @Id
  public int getBarCodeNumber()
  {
    return barCodeNumber;
  }
  /* Code from template association_GetOne */
  public LibrarySystem getLibrarySystem()
  {
    return librarySystem;
  }
  /* Code from template association_GetOne */
  public Newspaper getNewspaper()
  {
    return newspaper;
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
      existingLibrarySystem.removeNewspaperArticle(this);
    }
    librarySystem.addNewspaperArticle(this);
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_SetOneToMany */
  @ManyToOne(optional=false)
  public boolean setNewspaper(Newspaper aNewspaper)
  {
    boolean wasSet = false;
    if (aNewspaper == null)
    {
      return wasSet;
    }

    Newspaper existingNewspaper = newspaper;
    newspaper = aNewspaper;
    if (existingNewspaper != null && !existingNewspaper.equals(aNewspaper))
    {
      existingNewspaper.removeArticle(this);
    }
    newspaper.addArticle(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    LibrarySystem placeholderLibrarySystem = librarySystem;
    this.librarySystem = null;
    if(placeholderLibrarySystem != null)
    {
      placeholderLibrarySystem.removeNewspaperArticle(this);
    }
    Newspaper placeholderNewspaper = newspaper;
    this.newspaper = null;
    if(placeholderNewspaper != null)
    {
      placeholderNewspaper.removeArticle(this);
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "barCodeNumber" + ":" + getBarCodeNumber()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "date" + "=" + (getDate() != null ? !getDate().equals(this)  ? getDate().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "librarySystem = "+(getLibrarySystem()!=null?Integer.toHexString(System.identityHashCode(getLibrarySystem())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "newspaper = "+(getNewspaper()!=null?Integer.toHexString(System.identityHashCode(getNewspaper())):"null");
  }
}