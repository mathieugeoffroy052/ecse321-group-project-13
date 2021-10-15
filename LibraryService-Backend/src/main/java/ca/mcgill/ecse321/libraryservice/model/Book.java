/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4607.2d2b84eb8 modeling language!*/

package ca.mcgill.ecse321.libraryservice.model;
import java.util.*;
import javax.persistence.*;

@Entity
// line 106 "../../../../../../library.ump 15-05-01-147.ump 15-45-27-537.ump 16-05-11-860.ump"
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
    if (!setAuthor(aAuthor))
    {
      throw new RuntimeException("Unable to create Book due to aAuthor. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
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
  /* Code from template association_SetUnidirectionalOne */
  @ManyToOne(optional=false)
  public boolean setAuthor(Person aNewAuthor)
  {
    boolean wasSet = false;
    if (aNewAuthor != null)
    {
      author = aNewAuthor;
      wasSet = true;
    }
    return wasSet;
  }

  public void delete()
  {
    author = null;
    super.delete();
  }

}