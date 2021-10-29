/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4607.2d2b84eb8 modeling language!*/

package ca.mcgill.ecse321.libraryservice.model;
import java.util.*;
import java.sql.Date;
import javax.persistence.*;

import org.dom4j.dtd.AttributeDecl;

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
  private Set<NewspaperArticle> articles;
  private LibrarySystem librarySystem;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Newspaper() {
    paperID = nextPaperID++;
  }

  public Newspaper(String aName, LibrarySystem aLibrarySystem)
  {
    name = aName;
    paperID = nextPaperID++;
    boolean didAddLibrarySystem = setLibrarySystem(aLibrarySystem);
    if (!didAddLibrarySystem)
    {
      throw new RuntimeException("Unable to create newspaper due to librarySystem. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }


  //------------------------
  // PRIMARY KEY
  //------------------------

  public boolean setPaperID(int aPaperID)
  {
    this.paperID = aPaperID;
    if(paperID==aPaperID){
      return true;
    }
    else return false;
  }

  @Id
  public int getPaperID()
  {
    return this.paperID;
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

  public boolean setArticles(Set<NewspaperArticle> aArticles)
  {
    boolean wasSet = false;
    articles = aArticles;
    wasSet = true;
    return wasSet;
  }

  public String getName()
  {
    return this.name;
  }



  @OneToMany(mappedBy ="newspaper")
  public Set<NewspaperArticle> getArticles()
  {
    return articles;
  }

  public int numberOfArticles()
  {
   
    return articles.size();
  }

  public boolean hasArticles()
  {
    boolean has = articles.size() > 0;
    return has;
  }



  /* Code from template association_GetOne */
  @ManyToOne(optional=false)
  public LibrarySystem getLibrarySystem()
  {
    return librarySystem;
  }

  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfArticles()
  {
    return 0;
  }
  
  /* Code from template association_SetOneToMany */
  public boolean setLibrarySystem(LibrarySystem aLibrarySystem)
  {
    boolean wasSet = false;
    if (aLibrarySystem == null)
    {
      return wasSet;
    }
    librarySystem = aLibrarySystem;
    wasSet = true;
    return wasSet;
  }

  public String toString()
  {
    return super.toString() + "["+
            "paperID" + ":" + getPaperID()+ "," +
            "name" + ":" + getName()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "librarySystem = "+(getLibrarySystem()!=null?Integer.toHexString(System.identityHashCode(getLibrarySystem())):"null");
  }
}