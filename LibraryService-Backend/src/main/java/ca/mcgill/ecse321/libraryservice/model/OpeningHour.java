/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4607.2d2b84eb8 modeling language!*/

package ca.mcgill.ecse321.libraryservice.model;
import java.sql.Time;
import javax.persistence.*;

@Entity
// line 145 "../../../../../../library.ump 15-05-01-147.ump 15-45-27-537.ump 16-05-11-860.ump"
public class OpeningHour
{

  //------------------------
  // ENUMERATIONS
  //------------------------

  public enum DayOfWeek { Monday, Tuesday, Wednesday, Thursday, Friday, Saturday, Sunday }

  //------------------------
  // STATIC VARIABLES
  //------------------------

  private static int nextHourID = 1;

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //OpeningHour Attributes
  private DayOfWeek dayOfWeek;
  private Time startTime;
  private Time endTime;

  //Autounique Attributes
  private int hourID;

  private HeadLibrarian headLibrarian;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public OpeningHour() {
    hourID = nextHourID++;
  }

  public OpeningHour(DayOfWeek aDayOfWeek, Time aStartTime, Time aEndTime, HeadLibrarian aHeadLibrarian)
  {
    dayOfWeek = aDayOfWeek;
    startTime = aStartTime;
    endTime = aEndTime;
    hourID = nextHourID++;
    boolean didAddHeadLibrarian = setHeadLibrarian(aHeadLibrarian);
    if (!didAddHeadLibrarian)
    {
      throw new RuntimeException("Unable to create openingHour due to headLibrarian. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  //------------------------
  // PRIMARY KEY
  //------------------------

  public boolean setHourID(int aHourID)
  {
    hourID = aHourID;
    if(hourID==aHourID){
      return true;
    }
    else return false;
  }

  @Id
  public int getHourID()
  {
    return hourID;
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setDayOfWeek(DayOfWeek aDayOfWeek)
  {
    boolean wasSet = false;
    dayOfWeek = aDayOfWeek;
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

  public boolean setEndTime(Time aEndTime)
  {
    boolean wasSet = false;
    endTime = aEndTime;
    wasSet = true;
    return wasSet;
  }

  public DayOfWeek getDayOfWeek()
  {
    return dayOfWeek;
  }

  public Time getStartTime()
  {
    return startTime;
  }

  public Time getEndTime()
  {
    return endTime;
  }

  /* Code from template association_GetOne */
  @ManyToOne(optional=false)
  public HeadLibrarian getHeadLibrarian()
  {
    return headLibrarian;
  }
  
  /* Code from template association_SetOneToAtMostN */
  public boolean setHeadLibrarian(HeadLibrarian aHeadLibrarian)
  {
    boolean wasSet = false;
    //Must provide headLibrarian to openingHour
    if (aHeadLibrarian == null)
    {
      return wasSet;
    }
    this.headLibrarian = aHeadLibrarian;
    wasSet = true;
    return wasSet;
  }

  public String toString()
  {
    return super.toString() + "["+
            "hourID" + ":" + getHourID()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "dayOfWeek" + "=" + (getDayOfWeek() != null ? !getDayOfWeek().equals(this)  ? getDayOfWeek().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "startTime" + "=" + (getStartTime() != null ? !getStartTime().equals(this)  ? getStartTime().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "endTime" + "=" + (getEndTime() != null ? !getEndTime().equals(this)  ? getEndTime().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "headLibrarian = "+(getHeadLibrarian()!=null?Integer.toHexString(System.identityHashCode(getHeadLibrarian())):"null");
  }
}