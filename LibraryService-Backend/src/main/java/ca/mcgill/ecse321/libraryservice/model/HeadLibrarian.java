/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4607.2d2b84eb8 modeling language!*/

package ca.mcgill.ecse321.libraryservice.model;
import java.util.*;
import java.sql.Date;
import java.sql.Time;
import javax.persistence.*;

@Entity
// line 53 "../../../../../../library.ump 15-05-01-147.ump 15-45-27-537.ump"
public class HeadLibrarian extends Librarian
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //HeadLibrarian Associations
  private List<TimeSlot> workshift;
  private List<OpeningHour> openingHour;
  private List<Holiday> holidays;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public HeadLibrarian(String aFirstName, String aLastName, boolean aOnlineAccount, LibrarySystem aLibrarySystem, Address aAddress)
  {
    super(aFirstName, aLastName, aOnlineAccount, aLibrarySystem, aAddress);
    workshift = new ArrayList<TimeSlot>();
    openingHour = new ArrayList<OpeningHour>();
    holidays = new ArrayList<Holiday>();
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
  /* Code from template association_GetMany */
  public OpeningHour getOpeningHour(int index)
  {
    OpeningHour aOpeningHour = openingHour.get(index);
    return aOpeningHour;
  }

  public List<OpeningHour> getOpeningHour()
  {
    List<OpeningHour> newOpeningHour = Collections.unmodifiableList(openingHour);
    return newOpeningHour;
  }

  public int numberOfOpeningHour()
  {
    int number = openingHour.size();
    return number;
  }

  public boolean hasOpeningHour()
  {
    boolean has = openingHour.size() > 0;
    return has;
  }

  public int indexOfOpeningHour(OpeningHour aOpeningHour)
  {
    int index = openingHour.indexOf(aOpeningHour);
    return index;
  }
  /* Code from template association_GetMany */
  public Holiday getHoliday(int index)
  {
    Holiday aHoliday = holidays.get(index);
    return aHoliday;
  }

  public List<Holiday> getHolidays()
  {
    List<Holiday> newHolidays = Collections.unmodifiableList(holidays);
    return newHolidays;
  }

  public int numberOfHolidays()
  {
    int number = holidays.size();
    return number;
  }

  public boolean hasHolidays()
  {
    boolean has = holidays.size() > 0;
    return has;
  }

  public int indexOfHoliday(Holiday aHoliday)
  {
    int index = holidays.indexOf(aHoliday);
    return index;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfWorkshift()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public TimeSlot addWorkshift(Date aStartDate, Time aStartTime, Date aEndDate, Time aEndTime, LibrarySystem aLibrarySystem)
  {
    return new TimeSlot(aStartDate, aStartTime, aEndDate, aEndTime, aLibrarySystem, this);
  }

  public boolean addWorkshift(TimeSlot aWorkshift)
  {
    boolean wasAdded = false;
    if (workshift.contains(aWorkshift)) { return false; }
    HeadLibrarian existingHeadLibrarian = aWorkshift.getHeadLibrarian();
    boolean isNewHeadLibrarian = existingHeadLibrarian != null && !this.equals(existingHeadLibrarian);
    if (isNewHeadLibrarian)
    {
      aWorkshift.setHeadLibrarian(this);
    }
    else
    {
      workshift.add(aWorkshift);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeWorkshift(TimeSlot aWorkshift)
  {
    boolean wasRemoved = false;
    //Unable to remove aWorkshift, as it must always have a headLibrarian
    if (!this.equals(aWorkshift.getHeadLibrarian()))
    {
      workshift.remove(aWorkshift);
      wasRemoved = true;
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
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfOpeningHour()
  {
    return 0;
  }
  /* Code from template association_MaximumNumberOfMethod */
  public static int maximumNumberOfOpeningHour()
  {
    return 7;
  }
  /* Code from template association_AddOptionalNToOne */
  public OpeningHour addOpeningHour(OpeningHour.DayOfWeek aDayOfWeek, Time aStartTime, Time aEndTime, LibrarySystem aLibrarySystem)
  {
    if (numberOfOpeningHour() >= maximumNumberOfOpeningHour())
    {
      return null;
    }
    else
    {
      return new OpeningHour(aDayOfWeek, aStartTime, aEndTime, aLibrarySystem, this);
    }
  }

  public boolean addOpeningHour(OpeningHour aOpeningHour)
  {
    boolean wasAdded = false;
    if (openingHour.contains(aOpeningHour)) { return false; }
    if (numberOfOpeningHour() >= maximumNumberOfOpeningHour())
    {
      return wasAdded;
    }

    HeadLibrarian existingHeadLibrarian = aOpeningHour.getHeadLibrarian();
    boolean isNewHeadLibrarian = existingHeadLibrarian != null && !this.equals(existingHeadLibrarian);
    if (isNewHeadLibrarian)
    {
      aOpeningHour.setHeadLibrarian(this);
    }
    else
    {
      openingHour.add(aOpeningHour);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeOpeningHour(OpeningHour aOpeningHour)
  {
    boolean wasRemoved = false;
    //Unable to remove aOpeningHour, as it must always have a headLibrarian
    if (!this.equals(aOpeningHour.getHeadLibrarian()))
    {
      openingHour.remove(aOpeningHour);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addOpeningHourAt(OpeningHour aOpeningHour, int index)
  {  
    boolean wasAdded = false;
    if(addOpeningHour(aOpeningHour))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfOpeningHour()) { index = numberOfOpeningHour() - 1; }
      openingHour.remove(aOpeningHour);
      openingHour.add(index, aOpeningHour);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveOpeningHourAt(OpeningHour aOpeningHour, int index)
  {
    boolean wasAdded = false;
    if(openingHour.contains(aOpeningHour))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfOpeningHour()) { index = numberOfOpeningHour() - 1; }
      openingHour.remove(aOpeningHour);
      openingHour.add(index, aOpeningHour);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addOpeningHourAt(aOpeningHour, index);
    }
    return wasAdded;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfHolidays()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public Holiday addHoliday(Date aDate, Time aStartTime, Time aEndtime, LibrarySystem aLibrarySystem)
  {
    return new Holiday(aDate, aStartTime, aEndtime, aLibrarySystem, this);
  }

  public boolean addHoliday(Holiday aHoliday)
  {
    boolean wasAdded = false;
    if (holidays.contains(aHoliday)) { return false; }
    HeadLibrarian existingHeadLibrarian = aHoliday.getHeadLibrarian();
    boolean isNewHeadLibrarian = existingHeadLibrarian != null && !this.equals(existingHeadLibrarian);
    if (isNewHeadLibrarian)
    {
      aHoliday.setHeadLibrarian(this);
    }
    else
    {
      holidays.add(aHoliday);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeHoliday(Holiday aHoliday)
  {
    boolean wasRemoved = false;
    //Unable to remove aHoliday, as it must always have a headLibrarian
    if (!this.equals(aHoliday.getHeadLibrarian()))
    {
      holidays.remove(aHoliday);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addHolidayAt(Holiday aHoliday, int index)
  {  
    boolean wasAdded = false;
    if(addHoliday(aHoliday))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfHolidays()) { index = numberOfHolidays() - 1; }
      holidays.remove(aHoliday);
      holidays.add(index, aHoliday);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveHolidayAt(Holiday aHoliday, int index)
  {
    boolean wasAdded = false;
    if(holidays.contains(aHoliday))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfHolidays()) { index = numberOfHolidays() - 1; }
      holidays.remove(aHoliday);
      holidays.add(index, aHoliday);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addHolidayAt(aHoliday, index);
    }
    return wasAdded;
  }

  public void delete()
  {
    for(int i=workshift.size(); i > 0; i--)
    {
      TimeSlot aWorkshift = workshift.get(i - 1);
      aWorkshift.delete();
    }
    for(int i=openingHour.size(); i > 0; i--)
    {
      OpeningHour aOpeningHour = openingHour.get(i - 1);
      aOpeningHour.delete();
    }
    for(int i=holidays.size(); i > 0; i--)
    {
      Holiday aHoliday = holidays.get(i - 1);
      aHoliday.delete();
    }
    super.delete();
  }

}