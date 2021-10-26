/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4607.2d2b84eb8 modeling language!*/

package ca.mcgill.ecse321.libraryservice.model;
import java.util.*;
import javax.persistence.*;

@Entity
// line 5 "../../../../../../library.ump 15-05-01-147.ump 15-45-27-537.ump 16-05-11-860.ump"
public class LibrarySystem
{

  //------------------------
  // STATIC VARIABLES
  //------------------------

  private static int nextSystemId = 1;

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Autounique Attributes
  private int systemId;

  //LibrarySystem Associations
  private Set<UserAccount> userAccounts;
  private Set<OpeningHour> openingHours;
  private Set<TimeSlot> timeSlots;
  private Set<LibraryItem> libraryItems;
  private Set<Holiday> holidays;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public LibrarySystem()
  {
    systemId = nextSystemId++;
  }

  //------------------------
  // PRIMARY KEY
  //------------------------

  public boolean setSystemId(int aSystemId)
  {
    systemId = aSystemId;
    if(systemId==aSystemId){
      return true;
    }
    else return false;
  }
  
  @Id
  public int getSystemId()
  {
    return systemId;
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setHolidays(Set<Holiday> aholidays)
  {
    holidays = aholidays;
    if(holidays==aholidays){
      return true;
    }
    else return false;
  }

  public boolean setLibraryItems(Set<LibraryItem> aLibraryItems)
  {
    libraryItems = aLibraryItems;
    if(libraryItems==aLibraryItems){
      return true;
    }
    else return false;
  }

  public boolean setTimeSlots(Set<TimeSlot> aTimeSlots)
  {
    timeSlots = aTimeSlots;
    if(timeSlots==aTimeSlots){
      return true;
    }
    else return false;
  }

  public boolean setOpeningHours(Set<OpeningHour> aOpeningHours)
  {
    openingHours = aOpeningHours;
    if(openingHours==aOpeningHours){
      return true;
    }
    else return false;
  }

  public boolean setUserAccounts(Set<UserAccount> aUserAccounts)
  {
    userAccounts = aUserAccounts;
    if(userAccounts==aUserAccounts){
      return true;
    }
    else return false;
  }

  @OneToMany(cascade={CascadeType.ALL}, mappedBy = "librarySystem")
  public Set<UserAccount> getUserAccounts()
  {
    Set<UserAccount> newUserAccounts = userAccounts;
    return newUserAccounts;
  }

  public int numberOfUserAccounts()
  {
    int number = userAccounts.size();
    return number;
  }

  public boolean hasUserAccounts()
  {
    boolean has = userAccounts.size() > 0;
    return has;
  }

  @OneToMany(cascade={CascadeType.ALL}, mappedBy = "librarySystem")
  public Set<OpeningHour> getOpeningHours()
  {
    return openingHours;
  }

  public int numberOfOpeningHours()
  {
    int number = openingHours.size();
    return number;
  }

  public boolean hasOpeningHours()
  {
    boolean has = openingHours.size() > 0;
    return has;
  }

  @OneToMany(cascade={CascadeType.ALL}, mappedBy = "librarySystem")
  public Set<TimeSlot> getTimeSlots()
  {
    return timeSlots;
  }

  public int numberOfTimeSlots()
  {
    int number = timeSlots.size();
    return number;
  }

  public boolean hasTimeSlots()
  {
    boolean has = timeSlots.size() > 0;
    return has;
  }

  @OneToMany(cascade={CascadeType.ALL}, mappedBy = "librarySystem")
  public Set<LibraryItem> getLibraryItems()
  {
    return libraryItems;
  }

  public int numberOfLibraryItems()
  {
    int number = libraryItems.size();
    return number;
  }

  public boolean hasLibraryItems()
  {
    boolean has = libraryItems.size() > 0;
    return has;
  }

  @OneToMany(cascade={CascadeType.ALL}, mappedBy = "librarySystem")
  public Set<Holiday> getHolidays()
  {
    return holidays;
  }

  public int numberOfHolidays()
  {
    int number = holidays.size();
    return number;
  }

  public boolean hasholidays()
  {
    boolean has = holidays.size() > 0;
    return has;
  }

  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfUserAccounts()
  {
    return 0;
  }

  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfNewspapers()
  {
    return 0;
  }

  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfOpeningHours()
  {
    return 0;
  }

  /* Code from template association_MinimumNumberOfMethod */
  public static int maximumNumberOfOpeningHour()
  {
    return 7;
  }

  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfTimeSlots()
  {
    return 0;
  }

  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfNewspaperArticles()
  {
    return 0;
  }

  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfPersons()
  {
    return 0;
  }

  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfLibraryItems()
  {
    return 0;
  }

  public void delete()
  {
    if(!(userAccounts == null)){
      for (UserAccount account: userAccounts)
      {
        userAccounts.remove(account);
      }
    }
    
    if(!(openingHours==null)){
      for (OpeningHour hour : openingHours)
      {
        openingHours.remove(hour);
      }
    }
    
    if(!(timeSlots==null))
    {
      for (TimeSlot time: timeSlots)
      {
        timeSlots.remove(time);
      }
    }
    
    if(!(libraryItems==null))
    {
      for (LibraryItem item: libraryItems)
      {
        libraryItems.remove(item);
      }
    }

    if(!(holidays==null))
    {
      for (Holiday holiday: holidays)
      {
        holidays.remove(holiday);
      }
    }
    
  }


  public String toString()
  {
    return super.toString() + "["+
            "systemId" + ":" + getSystemId()+ "]";
  }
}