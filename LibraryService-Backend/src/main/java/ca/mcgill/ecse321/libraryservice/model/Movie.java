/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4607.2d2b84eb8 modeling language!*/

package ca.mcgill.ecse321.libraryservice.model;
import java.util.*;
import javax.persistence.*;

@Entity
// line 105 "../../../../../../library.ump 15-05-01-147.ump 15-45-27-537.ump"
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
    boolean didAddDirector = setDirector(aDirector);
    if (!didAddDirector)
    {
      throw new RuntimeException("Unable to create movy due to director. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------
  /* Code from template association_GetOne */
  public Person getDirector()
  {
    return director;
  }
  /* Code from template association_SetOneToMany */
  @ManyToOne(optional=false)
  public boolean setDirector(Person aDirector)
  {
    boolean wasSet = false;
    if (aDirector == null)
    {
      return wasSet;
    }

    Person existingDirector = director;
    director = aDirector;
    if (existingDirector != null && !existingDirector.equals(aDirector))
    {
      existingDirector.removeMovy(this);
    }
    director.addMovy(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    Person placeholderDirector = director;
    this.director = null;
    if(placeholderDirector != null)
    {
      placeholderDirector.removeMovy(this);
    }
    super.delete();
  }

}