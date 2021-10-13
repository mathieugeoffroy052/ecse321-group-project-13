package ca.mcgill.ecse321.libraryservice.model;
/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4607.2d2b84eb8 modeling language!*/

import javax.persistence.*;
import java.util.*;

@Entity
// line 103 "Library.ump"
public class Movie extends LibraryItem
{

  //------------------------
  // STATIC VARIABLES
  //------------------------

  private static int nextMovieID = 1;

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Autounique Attributes
  private int movieID;

  //Movie Associations
  private List<Person> director;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Movie(String aName, LibrarySystem aLibrarySystem, Person... allDirector)
  {
    super(aName, aLibrarySystem);
    movieID = nextMovieID++;
    director = new ArrayList<Person>();
    boolean didAddDirector = setDirector(allDirector);
    if (!didAddDirector)
    {
      throw new RuntimeException("Unable to create Movie, must have at least 1 director. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------
  @Id
  public int getMovieID()
  {
    return movieID;
  }
  /* Code from template association_GetMany */
  public Person getDirector(int index)
  {
    Person aDirector = director.get(index);
    return aDirector;
  }

  public List<Person> getDirector()
  {
    List<Person> newDirector = Collections.unmodifiableList(director);
    return newDirector;
  }

  public int numberOfDirector()
  {
    int number = director.size();
    return number;
  }

  public boolean hasDirector()
  {
    boolean has = director.size() > 0;
    return has;
  }

  public int indexOfDirector(Person aDirector)
  {
    int index = director.indexOf(aDirector);
    return index;
  }
  /* Code from template association_IsNumberOfValidMethod */
  public boolean isNumberOfDirectorValid()
  {
    boolean isValid = numberOfDirector() >= minimumNumberOfDirector();
    return isValid;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfDirector()
  {
    return 1;
  }
  /* Code from template association_AddManyToManyMethod */
  public boolean addDirector(Person aDirector)
  {
    boolean wasAdded = false;
    if (director.contains(aDirector)) { return false; }
    director.add(aDirector);
    if (aDirector.indexOfMovy(this) != -1)
    {
      wasAdded = true;
    }
    else
    {
      wasAdded = aDirector.addMovy(this);
      if (!wasAdded)
      {
        director.remove(aDirector);
      }
    }
    return wasAdded;
  }
  /* Code from template association_AddMStarToMany */
  public boolean removeDirector(Person aDirector)
  {
    boolean wasRemoved = false;
    if (!director.contains(aDirector))
    {
      return wasRemoved;
    }

    if (numberOfDirector() <= minimumNumberOfDirector())
    {
      return wasRemoved;
    }

    int oldIndex = director.indexOf(aDirector);
    director.remove(oldIndex);
    if (aDirector.indexOfMovy(this) == -1)
    {
      wasRemoved = true;
    }
    else
    {
      wasRemoved = aDirector.removeMovy(this);
      if (!wasRemoved)
      {
        director.add(oldIndex,aDirector);
      }
    }
    return wasRemoved;
  }
  /* Code from template association_SetMStarToMany */
  @ManyToMany
  public boolean setDirector(Person... newDirector)
  {
    boolean wasSet = false;
    ArrayList<Person> verifiedDirector = new ArrayList<Person>();
    for (Person aDirector : newDirector)
    {
      if (verifiedDirector.contains(aDirector))
      {
        continue;
      }
      verifiedDirector.add(aDirector);
    }

    if (verifiedDirector.size() != newDirector.length || verifiedDirector.size() < minimumNumberOfDirector())
    {
      return wasSet;
    }

    ArrayList<Person> oldDirector = new ArrayList<Person>(director);
    director.clear();
    for (Person aNewDirector : verifiedDirector)
    {
      director.add(aNewDirector);
      if (oldDirector.contains(aNewDirector))
      {
        oldDirector.remove(aNewDirector);
      }
      else
      {
        aNewDirector.addMovy(this);
      }
    }

    for (Person anOldDirector : oldDirector)
    {
      anOldDirector.removeMovy(this);
    }
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addDirectorAt(Person aDirector, int index)
  {  
    boolean wasAdded = false;
    if(addDirector(aDirector))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfDirector()) { index = numberOfDirector() - 1; }
      director.remove(aDirector);
      director.add(index, aDirector);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveDirectorAt(Person aDirector, int index)
  {
    boolean wasAdded = false;
    if(director.contains(aDirector))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfDirector()) { index = numberOfDirector() - 1; }
      director.remove(aDirector);
      director.add(index, aDirector);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addDirectorAt(aDirector, index);
    }
    return wasAdded;
  }

  public void delete()
  {
    ArrayList<Person> copyOfDirector = new ArrayList<Person>(director);
    director.clear();
    for(Person aDirector : copyOfDirector)
    {
      aDirector.removeMovy(this);
    }
    super.delete();
  }


  public String toString()
  {
    return super.toString() + "["+
            "movieID" + ":" + getMovieID()+ "]";
  }
}