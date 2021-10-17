/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4607.2d2b84eb8 modeling language!*/

package ca.mcgill.ecse321.libraryservice.model;
import javax.persistence.*;
@Entity
// line 120 "../../../../../../library.ump 15-05-01-147.ump 15-45-27-537.ump 16-05-11-860.ump"
public class Music extends LibraryItem
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  private String artist;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Music(String aName, LibrarySystem aLibrarySystem, String aArtist)
  {
    super(aName, aLibrarySystem);
    artist = aArtist;
  }

  //------------------------
  // INTERFACE
  //------------------------

  public String getArtist(){
    return artist;
  }

  public boolean setArtist(String aArtist)
  {
    boolean wasSet = false;
    artist = aArtist;
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    super.delete();
  }

}