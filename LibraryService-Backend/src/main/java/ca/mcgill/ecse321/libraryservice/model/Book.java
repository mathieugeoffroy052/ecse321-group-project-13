/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4607.2d2b84eb8 modeling language!*/

package ca.mcgill.ecse321.libraryservice.model;
import java.util.*;
import javax.persistence.*;

@Entity
// line 98 "../../../../../../library.ump 15-05-01-147.ump 15-45-27-537.ump"
public class Book extends LibraryItem
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Book Associations
  private Person author;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Book(String aName, LibrarySystem aLibrarySystem, Person aAuthor)
  {
    super(aName, aLibrarySystem);
    boolean didAddAuthor = setAuthor(aAuthor);
    if (!didAddAuthor)
    {
      throw new RuntimeException("Unable to create book due to author. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------
  /* Code from template association_GetOne */
  public Person getAuthor()
  {
    return author;
  }
  /* Code from template association_SetOneToMany */
  @ManyToOne(optional=false)
  public boolean setAuthor(Person aAuthor)
  {
    boolean wasSet = false;
    if (aAuthor == null)
    {
      return wasSet;
    }

    Person existingAuthor = author;
    author = aAuthor;
    if (existingAuthor != null && !existingAuthor.equals(aAuthor))
    {
      existingAuthor.removeBook(this);
    }
    author.addBook(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    Person placeholderAuthor = author;
    this.author = null;
    if(placeholderAuthor != null)
    {
      placeholderAuthor.removeBook(this);
    }
    super.delete();
  }

}