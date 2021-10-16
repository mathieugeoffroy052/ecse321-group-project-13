/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4607.2d2b84eb8 modeling language!*/

package ca.mcgill.ecse321.libraryservice.model;
import java.util.*;
import javax.persistence.*;

@Entity
// line 113 "../../../../../../library.ump 15-05-01-147.ump 15-45-27-537.ump 16-05-11-860.ump"
public class Movie extends LibraryItem
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Movie Associations
  private Person director;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Movie(String aName, LibrarySystem aLibrarySystem, Person aDirector)
  {
    super(aName, aLibrarySystem);
    if (!setDirector(aDirector))
    {
      throw new RuntimeException("Unable to create Movie due to aDirector. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------
  /* Code from template association_GetOne */

  @ManyToOne(optional=false)
  public Person getDirector()
  {
    return director;
  }
  /* Code from template association_SetUnidirectionalOne */

  public boolean setDirector(Person aNewDirector)
  {
    boolean wasSet = false;
    if (aNewDirector != null)
    {
      director = aNewDirector;
      wasSet = true;
    }
    return wasSet;
  }

  public void delete()
  {
    director = null;
    super.delete();
  }

}