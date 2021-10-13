/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4607.2d2b84eb8 modeling language!*/

package ca.mcgill.ecse321.libraryservice.model;
import java.util.*;

import java.util.Set;
import javax.persistence.*;

@Entity
public class Librarian extends UserAccount
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Librarian Associations
  private List<TimeSlot> workshift;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Librarian(String aUserID, String aFirstName, String aLastName, boolean aOnlineAccount, LibrarySystem aLibrarySystem, Address aAddress)
  {
    super(aUserID, aFirstName, aLastName, aOnlineAccount, aLibrarySystem, aAddress);
    workshift = new ArrayList<TimeSlot>();
  }

  //------------------------
  // INTERFACE
  //------------------------
  /* Code from template association_GetMany */
  public TimeSlot getWorkshift(int index)
  {
    TimeSlot aWorkshift = workshift.get(index);
    return aWorkshift;
  }

  public List<TimeSlot> getWorkshift()
  {
    List<TimeSlot> newWorkshift = Collections.unmodifiableList(workshift);
    return newWorkshift;
  }

  public int numberOfWorkshift()
  {
    int number = workshift.size();
    return number;
  }

  public boolean hasWorkshift()
  {
    boolean has = workshift.size() > 0;
    return has;
  }

  public int indexOfWorkshift(TimeSlot aWorkshift)
  {
    int index = workshift.indexOf(aWorkshift);
    return index;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfWorkshift()
  {
    return 0;
  }
  /* Code from template association_AddManyToManyMethod */
  public boolean addWorkshift(TimeSlot aWorkshift)
  {
    boolean wasAdded = false;
    if (workshift.contains(aWorkshift)) { return false; }
    workshift.add(aWorkshift);
    if (aWorkshift.indexOfLibrarian(this) != -1)
    {
      wasAdded = true;
    }
    else
    {
      wasAdded = aWorkshift.addLibrarian(this);
      if (!wasAdded)
      {
        workshift.remove(aWorkshift);
      }
    }
    return wasAdded;
  }
  /* Code from template association_RemoveMany */
  public boolean removeWorkshift(TimeSlot aWorkshift)
  {
    boolean wasRemoved = false;
    if (!workshift.contains(aWorkshift))
    {
      return wasRemoved;
    }

    int oldIndex = workshift.indexOf(aWorkshift);
    workshift.remove(oldIndex);
    if (aWorkshift.indexOfLibrarian(this) == -1)
    {
      wasRemoved = true;
    }
    else
    {
      wasRemoved = aWorkshift.removeLibrarian(this);
      if (!wasRemoved)
      {
        workshift.add(oldIndex,aWorkshift);
      }
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addWorkshiftAt(TimeSlot aWorkshift, int index)
  {  
    boolean wasAdded = false;
    if(addWorkshift(aWorkshift))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfWorkshift()) { index = numberOfWorkshift() - 1; }
      workshift.remove(aWorkshift);
      workshift.add(index, aWorkshift);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveWorkshiftAt(TimeSlot aWorkshift, int index)
  {
    boolean wasAdded = false;
    if(workshift.contains(aWorkshift))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfWorkshift()) { index = numberOfWorkshift() - 1; }
      workshift.remove(aWorkshift);
      workshift.add(index, aWorkshift);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addWorkshiftAt(aWorkshift, index);
    }
    return wasAdded;
  }

  public void delete()
  {
    ArrayList<TimeSlot> copyOfWorkshift = new ArrayList<TimeSlot>(workshift);
    workshift.clear();
    for(TimeSlot aWorkshift : copyOfWorkshift)
    {
      aWorkshift.removeLibrarian(this);
    }
    super.delete();
  }

}