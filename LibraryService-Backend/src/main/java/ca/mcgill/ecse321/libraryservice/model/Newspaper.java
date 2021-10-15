/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4607.2d2b84eb8 modeling language!*/

package ca.mcgill.ecse321.libraryservice.model;
import java.util.*;
import java.sql.Date;
import javax.persistence.*;

@Entity
// line 91 "../../../../../../library.ump 15-05-01-147.ump 15-45-27-537.ump 16-05-11-860.ump"
public class Newspaper
{

  //------------------------
  // STATIC VARIABLES
  //------------------------

  private static int nextPaperID = 1;

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Newspaper Attributes
  private String name;

  //Autounique Attributes
  private int paperID;

  //Newspaper Associations
  private List<NewspaperArticle> articles;
  private LibrarySystem librarySystem;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Newspaper(String aName, LibrarySystem aLibrarySystem)
  {
    name = aName;
    paperID = nextPaperID++;
    articles = new ArrayList<NewspaperArticle>();
    boolean didAddLibrarySystem = setLibrarySystem(aLibrarySystem);
    if (!didAddLibrarySystem)
    {
      throw new RuntimeException("Unable to create newspaper due to librarySystem. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
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
  public int getPaperID()
  {
    return paperID;
  }
  /* Code from template association_GetMany */
  public NewspaperArticle getArticle(int index)
  {
    NewspaperArticle aArticle = articles.get(index);
    return aArticle;
  }

  public List<NewspaperArticle> getArticles()
  {
    List<NewspaperArticle> newArticles = Collections.unmodifiableList(articles);
    return newArticles;
  }

  public int numberOfArticles()
  {
    int number = articles.size();
    return number;
  }

  public boolean hasArticles()
  {
    boolean has = articles.size() > 0;
    return has;
  }

  public int indexOfArticle(NewspaperArticle aArticle)
  {
    int index = articles.indexOf(aArticle);
    return index;
  }
  /* Code from template association_GetOne */
  public LibrarySystem getLibrarySystem()
  {
    return librarySystem;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfArticles()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public NewspaperArticle addArticle(Date aDate, LibrarySystem aLibrarySystem)
  {
    return new NewspaperArticle(aDate, aLibrarySystem, this);
  }

  public boolean addArticle(NewspaperArticle aArticle)
  {
    boolean wasAdded = false;
    if (articles.contains(aArticle)) { return false; }
    Newspaper existingNewspaper = aArticle.getNewspaper();
    boolean isNewNewspaper = existingNewspaper != null && !this.equals(existingNewspaper);
    if (isNewNewspaper)
    {
      aArticle.setNewspaper(this);
    }
    else
    {
      articles.add(aArticle);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeArticle(NewspaperArticle aArticle)
  {
    boolean wasRemoved = false;
    //Unable to remove aArticle, as it must always have a newspaper
    if (!this.equals(aArticle.getNewspaper()))
    {
      articles.remove(aArticle);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addArticleAt(NewspaperArticle aArticle, int index)
  {  
    boolean wasAdded = false;
    if(addArticle(aArticle))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfArticles()) { index = numberOfArticles() - 1; }
      articles.remove(aArticle);
      articles.add(index, aArticle);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveArticleAt(NewspaperArticle aArticle, int index)
  {
    boolean wasAdded = false;
    if(articles.contains(aArticle))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfArticles()) { index = numberOfArticles() - 1; }
      articles.remove(aArticle);
      articles.add(index, aArticle);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addArticleAt(aArticle, index);
    }
    return wasAdded;
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
      existingLibrarySystem.removeNewspaper(this);
    }
    librarySystem.addNewspaper(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    for(int i=articles.size(); i > 0; i--)
    {
      NewspaperArticle aArticle = articles.get(i - 1);
      aArticle.delete();
    }
    LibrarySystem placeholderLibrarySystem = librarySystem;
    this.librarySystem = null;
    if(placeholderLibrarySystem != null)
    {
      placeholderLibrarySystem.removeNewspaper(this);
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "paperID" + ":" + getPaperID()+ "," +
            "name" + ":" + getName()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "librarySystem = "+(getLibrarySystem()!=null?Integer.toHexString(System.identityHashCode(getLibrarySystem())):"null");
  }
}