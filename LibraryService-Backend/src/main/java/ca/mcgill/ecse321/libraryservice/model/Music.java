/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4607.2d2b84eb8 modeling language!*/

package ca.mcgill.ecse321.libraryservice.model;
import java.util.*;
import javax.persistence.*;

@Entity
// line 120 "../../../../../../library.ump 15-05-01-147.ump 15-45-27-537.ump 16-05-11-860.ump"
public class Music extends LibraryItem
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Music Associations
  private Person artist;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Music(String aName, LibrarySystem aLibrarySystem, Person aArtist)
  {
    super(aName, aLibrarySystem);
    if (!setArtist(aArtist))
    {
      throw new RuntimeException("Unable to create Music due to aArtist. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------
  /* Code from template association_GetOne */
  @ManyToOne(optional=false)
  public Person getArtist()
  {
    return artist;
  }
  /* Code from template association_SetUnidirectionalOne */
  public boolean setArtist(Person aNewArtist)
  {
    boolean wasSet = false;
    if (aNewArtist != null)
    {
      artist = aNewArtist;
      wasSet = true;
    }
    return wasSet;
  }

  public void delete()
  {
    artist = null;
    super.delete();
  }

}