/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4607.2d2b84eb8 modeling language!*/

package ca.mcgill.ecse321.libraryservice.model;
import java.sql.Date;
import java.sql.Time;
import java.util.*;
import javax.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

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
  private Set<Librarian> librarian;
  private HeadLibrarian headLibrarian;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public TimeSlot() {
    timeSlotID = nextTimeSlotID++;
  }

  public TimeSlot(Date aStartDate, Time aStartTime, Date aEndDate, Time aEndTime, LibrarySystem aLibrarySystem, HeadLibrarian aHeadLibrarian)
  {
    startDate = aStartDate;
    startTime = aStartTime;
    endDate = aEndDate;
    endTime = aEndTime;
    timeSlotID = nextTimeSlotID++;
    headLibrarian = aHeadLibrarian;
    boolean didAddLibrarySystem = setLibrarySystem(aLibrarySystem);
    if (!didAddLibrarySystem)
    {
      throw new RuntimeException("Unable to create timeSlot due to librarySystem. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    boolean didAddHeadLibrarian = setHeadLibrarian(aHeadLibrarian);
    if (!didAddHeadLibrarian)
    {
      throw new RuntimeException("Unable to create timeSlot due to headLibrarian. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  //------------------------
  // PRIMARY KEY
  //------------------------

  public boolean setTimeSlotID(int aTimeSlotID)
  {
    timeSlotID = aTimeSlotID;
    if(timeSlotID==aTimeSlotID){
      return true;
    }
    else return false;
  }

  @Id
  public int getTimeSlotID()
  {
    return timeSlotID;
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

  public boolean setLibrarian(Set<Librarian> aLibrarian)
  {
    boolean wasSet = false;
    librarian = aLibrarian;
    wasSet = true;
    return wasSet;
  }

  public boolean addLibrarian(Librarian aLibrarian){
    boolean wasSet = false;
    if(librarian == null) {
    	librarian = new HashSet<Librarian>();
    }
    librarian.add(aLibrarian);
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

  /* Code from template association_GetOne */
  @ManyToOne(optional=false)
  public LibrarySystem getLibrarySystem()
  {
    return librarySystem;
  }

  
  @ManyToMany(fetch=FetchType.EAGER)
  @OnDelete (action = OnDeleteAction.CASCADE)
  public Set<Librarian> getLibrarian()
  {
    return librarian;
  }

  public int numberOfLibrarian()
  {
    int number = librarian.size();
    return number;
  }

  public boolean hasLibrarian()
  {
    boolean has = librarian.size() > 0;
    return has;
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
    librarySystem = aLibrarySystem;
    wasSet = true;
    return wasSet;
  }

  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfLibrarian()
  {
    return 0;
  }
  
  /* Code from template association_SetOneToMany */
  public boolean setHeadLibrarian(HeadLibrarian aHeadLibrarian)
  {
    boolean wasSet = false;
    if (aHeadLibrarian == null)
    {
      return wasSet;
    }
    headLibrarian = aHeadLibrarian;
    wasSet = true;
    return wasSet;
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