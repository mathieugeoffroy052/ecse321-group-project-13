/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4607.2d2b84eb8 modeling language!*/

package ca.mcgill.ecse321.libraryservice.model;
import java.sql.Date;
import java.sql.Time;
import java.util.*;
import javax.persistence.*;

@Entity
// line 135 "../../../../../../library.ump 15-05-01-147.ump 15-45-27-537.ump 16-05-11-860.ump"
public class TimeSlot
{

  //------------------------
  // STATIC VARIABLES
  //------------------------

  private static int nextTimeSlotID = 1;

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //TimeSlot Attributes
  private Date startDate;
  private Time startTime;
  private Date endDate;
  private Time endTime;

  //Autounique Attributes
  private int timeSlotID;

  //TimeSlot Associations
  private LibrarySystem librarySystem;
  private List<Librarian> worker;
  private HeadLibrarian headLibrarian;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public TimeSlot(Date aStartDate, Time aStartTime, Date aEndDate, Time aEndTime, LibrarySystem aLibrarySystem, HeadLibrarian aHeadLibrarian)
  {
    startDate = aStartDate;
    startTime = aStartTime;
    endDate = aEndDate;
    endTime = aEndTime;
    timeSlotID = nextTimeSlotID++;
    boolean didAddLibrarySystem = setLibrarySystem(aLibrarySystem);
    if (!didAddLibrarySystem)
    {
      throw new RuntimeException("Unable to create timeSlot due to librarySystem. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    worker = new ArrayList<Librarian>();
    boolean didAddHeadLibrarian = setHeadLibrarian(aHeadLibrarian);
    if (!didAddHeadLibrarian)
    {
      throw new RuntimeException("Unable to create workshift due to headLibrarian. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setStartDate(Date aStartDate)
  {
    boolean wasSet = false;
    startDate = aStartDate;
    wasSet = true;
    return wasSet;
  }

  public boolean setStartTime(Time aStartTime)
  {
    boolean wasSet = false;
    startTime = aStartTime;
    wasSet = true;
    return wasSet;
  }

  public boolean setWorker(ArrayList<Librarian> aWorker)
  {
    boolean wasSet = false;
    worker = aWorker;
    wasSet = true;
    return wasSet;
  }

  public boolean setEndDate(Date aEndDate)
  {
    boolean wasSet = false;
    endDate = aEndDate;
    wasSet = true;
    return wasSet;
  }

  public boolean setEndTime(Time aEndTime)
  {
    boolean wasSet = false;
    endTime = aEndTime;
    wasSet = true;
    return wasSet;
  }

  public Date getStartDate()
  {
    return startDate;
  }

  public Time getStartTime()
  {
    return startTime;
  }

  public Date getEndDate()
  {
    return endDate;
  }

  public Time getEndTime()
  {
    return endTime;
  }
  @Id
  public int getTimeSlotID()
  {
    return timeSlotID;
  }

  public boolean setTimeSlotID(int aTimeSlotID)
  {
    timeSlotID = aTimeSlotID;
    if(timeSlotID==aTimeSlotID){
      return true;
    }
    else return false;
  }
  /* Code from template association_GetOne */
  @ManyToOne(optional=false)
  public LibrarySystem getLibrarySystem()
  {
    return librarySystem;
  }
  /* Code from template association_GetMany */
  public Librarian getWorker(int index)
  {
    Librarian aWorker = worker.get(index);
    return aWorker;
  }

  @ManyToMany
  public List<Librarian> getWorker()
  {
    List<Librarian> newWorker = Collections.unmodifiableList(worker);
    return newWorker;
  }

  public int numberOfWorker()
  {
    int number = worker.size();
    return number;
  }

  public boolean hasWorker()
  {
    boolean has = worker.size() > 0;
    return has;
  }

  public int indexOfWorker(Librarian aWorker)
  {
    int index = worker.indexOf(aWorker);
    return index;
  }
  /* Code from template association_GetOne */
  @ManyToOne(optional=false)
  public HeadLibrarian getHeadLibrarian()
  {
    return headLibrarian;
  }
  /* Code from template association_SetOneToMany */
  public boolean setLibrarySystem(LibrarySystem aLibrarySystem)
  {
    boolean wasSet = false;
    if (aLibrarySystem == null)
    {
      return wasSet;
    }

    LibrarySystem existingLibrarySystem = librarySystem;
    librarySystem = aLibrarySystem;
    if (existingLibrarySystem != null && !existingLibrarySystem.equals(aLibrarySystem))
    {
      existingLibrarySystem.removeTimeSlot(this);
    }
    librarySystem.addTimeSlot(this);
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfWorker()
  {
    return 0;
  }
  /* Code from template association_AddManyToManyMethod */
  public boolean addWorker(Librarian aWorker)
  {
    boolean wasAdded = false;
    if (worker.contains(aWorker)) { return false; }
    worker.add(aWorker);
    if (aWorker.indexOfWorkshift(this) != -1)
    {
      wasAdded = true;
    }
    else
    {
      wasAdded = aWorker.addWorkshift(this);
      if (!wasAdded)
      {
        worker.remove(aWorker);
      }
    }
    return wasAdded;
  }
  /* Code from template association_RemoveMany */
  public boolean removeWorker(Librarian aWorker)
  {
    boolean wasRemoved = false;
    if (!worker.contains(aWorker))
    {
      return wasRemoved;
    }

    int oldIndex = worker.indexOf(aWorker);
    worker.remove(oldIndex);
    if (aWorker.indexOfWorkshift(this) == -1)
    {
      wasRemoved = true;
    }
    else
    {
      wasRemoved = aWorker.removeWorkshift(this);
      if (!wasRemoved)
      {
        worker.add(oldIndex,aWorker);
      }
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addWorkerAt(Librarian aWorker, int index)
  {  
    boolean wasAdded = false;
    if(addWorker(aWorker))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfWorker()) { index = numberOfWorker() - 1; }
      worker.remove(aWorker);
      worker.add(index, aWorker);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveWorkerAt(Librarian aWorker, int index)
  {
    boolean wasAdded = false;
    if(worker.contains(aWorker))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfWorker()) { index = numberOfWorker() - 1; }
      worker.remove(aWorker);
      worker.add(index, aWorker);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addWorkerAt(aWorker, index);
    }
    return wasAdded;
  }
  /* Code from template association_SetOneToMany */
  public boolean setHeadLibrarian(HeadLibrarian aHeadLibrarian)
  {
    boolean wasSet = false;
    if (aHeadLibrarian == null)
    {
      return wasSet;
    }

    HeadLibrarian existingHeadLibrarian = headLibrarian;
    headLibrarian = aHeadLibrarian;
    if (existingHeadLibrarian != null && !existingHeadLibrarian.equals(aHeadLibrarian))
    {
      existingHeadLibrarian.removeWorkshift(this);
    }
    headLibrarian.addWorkshift(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    LibrarySystem placeholderLibrarySystem = librarySystem;
    this.librarySystem = null;
    if(placeholderLibrarySystem != null)
    {
      placeholderLibrarySystem.removeTimeSlot(this);
    }
    ArrayList<Librarian> copyOfWorker = new ArrayList<Librarian>(worker);
    worker.clear();
    for(Librarian aWorker : copyOfWorker)
    {
      aWorker.removeWorkshift(this);
    }
    HeadLibrarian placeholderHeadLibrarian = headLibrarian;
    this.headLibrarian = null;
    if(placeholderHeadLibrarian != null)
    {
      placeholderHeadLibrarian.removeWorkshift(this);
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "timeSlotID" + ":" + getTimeSlotID()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "startDate" + "=" + (getStartDate() != null ? !getStartDate().equals(this)  ? getStartDate().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "startTime" + "=" + (getStartTime() != null ? !getStartTime().equals(this)  ? getStartTime().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "endDate" + "=" + (getEndDate() != null ? !getEndDate().equals(this)  ? getEndDate().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "endTime" + "=" + (getEndTime() != null ? !getEndTime().equals(this)  ? getEndTime().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "librarySystem = "+(getLibrarySystem()!=null?Integer.toHexString(System.identityHashCode(getLibrarySystem())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "headLibrarian = "+(getHeadLibrarian()!=null?Integer.toHexString(System.identityHashCode(getHeadLibrarian())):"null");
  }
}