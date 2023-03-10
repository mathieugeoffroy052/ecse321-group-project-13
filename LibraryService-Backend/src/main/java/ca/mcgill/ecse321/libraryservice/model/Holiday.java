/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4607.2d2b84eb8 modeling language!*/

package ca.mcgill.ecse321.libraryservice.model;
import java.sql.Date;
import java.sql.Time;
import javax.persistence.*;

@Entity
// line 155 "../../../../../../library.ump 15-05-01-147.ump 15-45-27-537.ump 16-05-11-860.ump"
public class Holiday
{

  //------------------------
  // STATIC VARIABLES
  //------------------------

  private static int nextHolidayID = 1;

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Holiday Attributes
  private Date date;
  private Time startTime;
  private Time endtime;

  //Autounique Attributes
  private int holidayID;

  //Holiday Associations
  private HeadLibrarian headLibrarian;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Holiday() {
    holidayID = nextHolidayID++;
  }

  public Holiday(Date aDate, Time aStartTime, Time aEndtime, HeadLibrarian aHeadLibrarian)
  {
    date = aDate;
    startTime = aStartTime;
    endtime = aEndtime;
    holidayID = nextHolidayID++;
    boolean didAddHeadLibrarian = setHeadLibrarian(aHeadLibrarian);
    if (!didAddHeadLibrarian)
    {
      throw new RuntimeException("Unable to create holiday due to headLibrarian. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  //------------------------
  // PRIMARY KEY
  //------------------------

  public boolean setHolidayID(int aholidayID)
  {
    holidayID = aholidayID;
    if(holidayID==aholidayID){
      return true;
    }
    else return false;
  }

  @Id
  public int getHolidayID()
  {
    return holidayID;
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setDate(Date aDate)
  {
    boolean wasSet = false;
    date = aDate;
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

  public boolean setEndtime(Time aEndtime)
  {
    boolean wasSet = false;
    endtime = aEndtime;
    wasSet = true;
    return wasSet;
  }

  public Date getDate()
  {
    return date;
  }

  public Time getStartTime()
  {
    return startTime;
  }

  public Time getEndtime()
  {
    return endtime;
  }

  /* Code from template association_GetOne */
  @ManyToOne(optional=false)
  public HeadLibrarian getHeadLibrarian()
  {
    return headLibrarian;
  }

  /* Code from template association_SetOneToMany */
  public boolean setHeadLibrarian(HeadLibrarian aHeadLibrarian)
  {
    boolean wasSet = false;
    this.headLibrarian = aHeadLibrarian;
    wasSet = true;
    return wasSet;
  }

  public String toString()
  {
    return super.toString() + "["+
            "holidayID" + ":" + getHolidayID()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "date" + "=" + (getDate() != null ? !getDate().equals(this)  ? getDate().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "startTime" + "=" + (getStartTime() != null ? !getStartTime().equals(this)  ? getStartTime().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "endtime" + "=" + (getEndtime() != null ? !getEndtime().equals(this)  ? getEndtime().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "headLibrarian = "+(getHeadLibrarian()!=null?Integer.toHexString(System.identityHashCode(getHeadLibrarian())):"null");
  }
}