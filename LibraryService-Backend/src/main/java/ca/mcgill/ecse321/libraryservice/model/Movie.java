/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4607.2d2b84eb8 modeling language!*/

package ca.mcgill.ecse321.libraryservice.model;
import javax.persistence.*;

@Entity
// line 113 "../../../../../../library.ump 15-05-01-147.ump 15-45-27-537.ump 16-05-11-860.ump"
public class Movie extends LibraryItem
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  private String director;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Movie() {
    super();
  }

  public Movie(String aName, LibrarySystem aLibrarySystem, String aDirector)
  {
    super(aName, aLibrarySystem);
    director = aDirector;
  }

  //------------------------
  // INTERFACE
  //------------------------

  public String getDirector(){
    return director;
  }

  public boolean setDirector(String aDirector)
  {
    boolean wasSet = false;
    director = aDirector;
    wasSet = true;
    return wasSet;
  }

}