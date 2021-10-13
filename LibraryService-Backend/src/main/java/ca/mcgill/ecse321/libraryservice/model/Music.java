package ca.mcgill.ecse321.libraryservice.model;
/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4607.2d2b84eb8 modeling language!*/

import javax.persistence.*;
import java.util.*;

@Entity
// line 111 "Library.ump"
public class Music extends LibraryItem
{

  //------------------------
  // STATIC VARIABLES
  //------------------------

  private static int nextMusicID = 1;

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Autounique Attributes
  private int musicID;

  //Music Associations
  private List<Person> artist;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Music(String aName, LibrarySystem aLibrarySystem, Person... allArtist)
  {
    super(aName, aLibrarySystem);
    musicID = nextMusicID++;
    artist = new ArrayList<Person>();
    boolean didAddArtist = setArtist(allArtist);
    if (!didAddArtist)
    {
      throw new RuntimeException("Unable to create Music, must have at least 1 artist. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------
  @Id
  public int getMusicID()
  {
    return musicID;
  }
  /* Code from template association_GetMany */
  public Person getArtist(int index)
  {
    Person aArtist = artist.get(index);
    return aArtist;
  }

  public List<Person> getArtist()
  {
    List<Person> newArtist = Collections.unmodifiableList(artist);
    return newArtist;
  }

  public int numberOfArtist()
  {
    int number = artist.size();
    return number;
  }

  public boolean hasArtist()
  {
    boolean has = artist.size() > 0;
    return has;
  }

  public int indexOfArtist(Person aArtist)
  {
    int index = artist.indexOf(aArtist);
    return index;
  }
  /* Code from template association_IsNumberOfValidMethod */
  public boolean isNumberOfArtistValid()
  {
    boolean isValid = numberOfArtist() >= minimumNumberOfArtist();
    return isValid;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfArtist()
  {
    return 1;
  }
  /* Code from template association_AddManyToManyMethod */
  public boolean addArtist(Person aArtist)
  {
    boolean wasAdded = false;
    if (artist.contains(aArtist)) { return false; }
    artist.add(aArtist);
    if (aArtist.indexOfMusic(this) != -1)
    {
      wasAdded = true;
    }
    else
    {
      wasAdded = aArtist.addMusic(this);
      if (!wasAdded)
      {
        artist.remove(aArtist);
      }
    }
    return wasAdded;
  }
  /* Code from template association_AddMStarToMany */
  public boolean removeArtist(Person aArtist)
  {
    boolean wasRemoved = false;
    if (!artist.contains(aArtist))
    {
      return wasRemoved;
    }

    if (numberOfArtist() <= minimumNumberOfArtist())
    {
      return wasRemoved;
    }

    int oldIndex = artist.indexOf(aArtist);
    artist.remove(oldIndex);
    if (aArtist.indexOfMusic(this) == -1)
    {
      wasRemoved = true;
    }
    else
    {
      wasRemoved = aArtist.removeMusic(this);
      if (!wasRemoved)
      {
        artist.add(oldIndex,aArtist);
      }
    }
    return wasRemoved;
  }
  /* Code from template association_SetMStarToMany */
  @ManyToMany
  public boolean setArtist(Person... newArtist)
  {
    boolean wasSet = false;
    ArrayList<Person> verifiedArtist = new ArrayList<Person>();
    for (Person aArtist : newArtist)
    {
      if (verifiedArtist.contains(aArtist))
      {
        continue;
      }
      verifiedArtist.add(aArtist);
    }

    if (verifiedArtist.size() != newArtist.length || verifiedArtist.size() < minimumNumberOfArtist())
    {
      return wasSet;
    }

    ArrayList<Person> oldArtist = new ArrayList<Person>(artist);
    artist.clear();
    for (Person aNewArtist : verifiedArtist)
    {
      artist.add(aNewArtist);
      if (oldArtist.contains(aNewArtist))
      {
        oldArtist.remove(aNewArtist);
      }
      else
      {
        aNewArtist.addMusic(this);
      }
    }

    for (Person anOldArtist : oldArtist)
    {
      anOldArtist.removeMusic(this);
    }
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addArtistAt(Person aArtist, int index)
  {  
    boolean wasAdded = false;
    if(addArtist(aArtist))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfArtist()) { index = numberOfArtist() - 1; }
      artist.remove(aArtist);
      artist.add(index, aArtist);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveArtistAt(Person aArtist, int index)
  {
    boolean wasAdded = false;
    if(artist.contains(aArtist))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfArtist()) { index = numberOfArtist() - 1; }
      artist.remove(aArtist);
      artist.add(index, aArtist);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addArtistAt(aArtist, index);
    }
    return wasAdded;
  }

  public void delete()
  {
    ArrayList<Person> copyOfArtist = new ArrayList<Person>(artist);
    artist.clear();
    for(Person aArtist : copyOfArtist)
    {
      aArtist.removeMusic(this);
    }
    super.delete();
  }


  public String toString()
  {
    return super.toString() + "["+
            "musicID" + ":" + getMusicID()+ "]";
  }
}