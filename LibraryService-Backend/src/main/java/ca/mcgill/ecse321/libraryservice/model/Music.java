/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4607.2d2b84eb8 modeling language!*/

package ca.mcgill.ecse321.libraryservice.model;
import java.util.*;
import javax.persistence.*;

@Entity
// line 112 "../../../../../../library.ump 15-05-01-147.ump 15-45-27-537.ump"
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
    boolean didAddArtist = setArtist(aArtist);
    if (!didAddArtist)
    {
      throw new RuntimeException("Unable to create music due to artist. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------
  /* Code from template association_GetOne */
  public Person getArtist()
  {
    return artist;
  }
  /* Code from template association_SetOneToMany */
  @ManyToOne(optional=false)
  public boolean setArtist(Person aArtist)
  {
    boolean wasSet = false;
    if (aArtist == null)
    {
      return wasSet;
    }

    Person existingArtist = artist;
    artist = aArtist;
    if (existingArtist != null && !existingArtist.equals(aArtist))
    {
      existingArtist.removeMusic(this);
    }
    artist.addMusic(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    Person placeholderArtist = artist;
    this.artist = null;
    if(placeholderArtist != null)
    {
      placeholderArtist.removeMusic(this);
    }
    super.delete();
  }

}