/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4607.2d2b84eb8 modeling language!*/

package ca.mcgill.ecse321.libraryservice.model;
import java.util.*;
import java.sql.Time;
import java.sql.Date;
import javax.persistence.*;

import org.apache.catalina.User;

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
  private Set<Newspaper> newspapers;
  private Set<OpeningHour> openingHours;
  private Set<TimeSlot> timeSlots;
  private Set<NewspaperArticle> newspaperArticles;
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

  public boolean setNewspaperArticles(Set<NewspaperArticle> aNewspaperArticles)
  {
    newspaperArticles = aNewspaperArticles;
    if(newspaperArticles==aNewspaperArticles){
      return true;
    }
    else return false;
  }

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

  public boolean setNewspapers(Set<Newspaper> aNewspapers)
  {
    newspapers = aNewspapers;
    if(newspapers==aNewspapers){
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
  public Set<Newspaper> getNewspapers()
  {
    return newspapers;
  }

  public int numberOfNewspapers()
  {
    int number = newspapers.size();
    return number;
  }

  public boolean hasNewspapers()
  {
    boolean has = newspapers.size() > 0;
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
  public Set<NewspaperArticle> getNewspaperArticles()
  {
    return newspaperArticles;
  }

  public int numberOfNewspaperArticles()
  {
    int number = newspaperArticles.size();
    return number;
  }

  public boolean hasNewspaperArticles()
  {
    boolean has = newspaperArticles.size() > 0;
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
  /* Code from template association_AddManyToOne */


  // public boolean addUserAccount(UserAccount aUserAccount)
  // {
  //   boolean wasAdded = false;
  //   if (userAccounts)
  //   if (userAccounts.contains(aUserAccount)) { return false; }
  //   LibrarySystem existingLibrarySystem = aUserAccount.getLibrarySystem();
  //   boolean isNewLibrarySystem = existingLibrarySystem != null && !this.equals(existingLibrarySystem);
  //   if (isNewLibrarySystem)
  //   {
  //     aUserAccount.setLibrarySystem(this);
  //   }
  //   else
  //   {
  //     userAccounts.add(aUserAccount);
  //   }
  //   wasAdded = true;
  //   return wasAdded;
  // }

  public boolean removeUserAccount(UserAccount aUserAccount)
  {
    boolean wasRemoved = false;
    //Unable to remove aUserAccount, as it must always have a librarySystem
    if (!this.equals(aUserAccount.getLibrarySystem()))
    {
      userAccounts.remove(aUserAccount);
      wasRemoved = true;
    }
    return wasRemoved;
  }

  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfNewspapers()
  {
    return 0;
  }

  /* Code from template association_AddManyToOne */
  public Newspaper addNewspaper(String aName)
  {
    return new Newspaper(aName, this);
  }

  public boolean addNewspaper(Newspaper aNewspaper)
  {
    boolean wasAdded = false;
    if (newspapers.contains(aNewspaper)) { return false; }
    LibrarySystem existingLibrarySystem = aNewspaper.getLibrarySystem();
    boolean isNewLibrarySystem = existingLibrarySystem != null && !this.equals(existingLibrarySystem);
    if (isNewLibrarySystem)
    {
      aNewspaper.setLibrarySystem(this);
    }
    else
    {
      newspapers.add(aNewspaper);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeNewspaper(Newspaper aNewspaper)
  {
    boolean wasRemoved = false;
    //Unable to remove aNewspaper, as it must always have a librarySystem
    if (!this.equals(aNewspaper.getLibrarySystem()))
    {
      newspapers.remove(aNewspaper);
      wasRemoved = true;
    }
    return wasRemoved;
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

  /* Code from template association_AddManyToOne */
  public OpeningHour addOpeningHour(OpeningHour.DayOfWeek aDayOfWeek, Time aStartTime, Time aEndTime, HeadLibrarian aHeadLibrarian)
  {
    return new OpeningHour(aDayOfWeek, aStartTime, aEndTime, this, aHeadLibrarian);
  }

  public boolean addOpeningHour(OpeningHour aOpeningHour)
  {
    boolean wasAdded = false;
    if (openingHours.contains(aOpeningHour)) { return false; }
    LibrarySystem existingLibrarySystem = aOpeningHour.getLibrarySystem();
    boolean isNewLibrarySystem = existingLibrarySystem != null && !this.equals(existingLibrarySystem);
    if (isNewLibrarySystem)
    {
      aOpeningHour.setLibrarySystem(this);
    }
    else
    {
      openingHours.add(aOpeningHour);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeOpeningHour(OpeningHour aOpeningHour)
  {
    boolean wasRemoved = false;
    //Unable to remove aOpeningHour, as it must always have a librarySystem
    if (!this.equals(aOpeningHour.getLibrarySystem()))
    {
      openingHours.remove(aOpeningHour);
      wasRemoved = true;
    }
    return wasRemoved;
  }

  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfTimeSlots()
  {
    return 0;
  }

  /* Code from template association_AddManyToOne */
  public TimeSlot addTimeSlot(Date aStartDate, Time aStartTime, Date aEndDate, Time aEndTime, HeadLibrarian aHeadLibrarian)
  {
    return new TimeSlot(aStartDate, aStartTime, aEndDate, aEndTime, this, aHeadLibrarian);
  }

  public boolean addTimeSlot(TimeSlot aTimeSlot)
  {
    boolean wasAdded = false;
    if (timeSlots.contains(aTimeSlot)) { return false; }
    LibrarySystem existingLibrarySystem = aTimeSlot.getLibrarySystem();
    boolean isNewLibrarySystem = existingLibrarySystem != null && !this.equals(existingLibrarySystem);
    if (isNewLibrarySystem)
    {
      aTimeSlot.setLibrarySystem(this);
    }
    else
    {
      timeSlots.add(aTimeSlot);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeTimeSlot(TimeSlot aTimeSlot)
  {
    boolean wasRemoved = false;
    //Unable to remove aTimeSlot, as it must always have a librarySystem
    if (!this.equals(aTimeSlot.getLibrarySystem()))
    {
      timeSlots.remove(aTimeSlot);
      wasRemoved = true;
    }
    return wasRemoved;
  }

  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfNewspaperArticles()
  {
    return 0;
  }

  /* Code from template association_AddManyToOne */
  public NewspaperArticle addNewspaperArticle(Date aDate, Newspaper aNewspaper)
  {
    return new NewspaperArticle(aDate, this, aNewspaper);
  }

  public boolean addNewspaperArticle(NewspaperArticle aNewspaperArticle)
  {
    boolean wasAdded = false;
    if (newspaperArticles.contains(aNewspaperArticle)) { return false; }
    LibrarySystem existingLibrarySystem = aNewspaperArticle.getLibrarySystem();
    boolean isNewLibrarySystem = existingLibrarySystem != null && !this.equals(existingLibrarySystem);
    if (isNewLibrarySystem)
    {
      aNewspaperArticle.setLibrarySystem(this);
    }
    else
    {
      newspaperArticles.add(aNewspaperArticle);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeNewspaperArticle(NewspaperArticle aNewspaperArticle)
  {
    boolean wasRemoved = false;
    //Unable to remove aNewspaperArticle, as it must always have a librarySystem
    if (!this.equals(aNewspaperArticle.getLibrarySystem()))
    {
      newspaperArticles.remove(aNewspaperArticle);
      wasRemoved = true;
    }
    return wasRemoved;
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

  /* Code from template association_AddManyToOne */
  public boolean addLibraryItem(LibraryItem aLibraryItem)
  {
    boolean wasAdded = false;
    if (libraryItems.contains(aLibraryItem)) { return false; }
    LibrarySystem existingLibrarySystem = aLibraryItem.getLibrarySystem();
    boolean isNewLibrarySystem = existingLibrarySystem != null && !this.equals(existingLibrarySystem);
    if (isNewLibrarySystem)
    {
      aLibraryItem.setLibrarySystem(this);
    }
    else
    {
      libraryItems.add(aLibraryItem);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeLibraryItem(LibraryItem aLibraryItem)
  {
    boolean wasRemoved = false;
    //Unable to remove aLibraryItem, as it must always have a librarySystem
    if (!this.equals(aLibraryItem.getLibrarySystem()))
    {
      libraryItems.remove(aLibraryItem);
      wasRemoved = true;
    }
    return wasRemoved;
  }

  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfholidays()
  {
    return 0;
  }

  /* Code from template association_AddManyToOne */
  public Holiday addHoliday(Date aDate, Time aStartTime, Time aEndtime, HeadLibrarian aHeadLibrarian)
  {
    return new Holiday(aDate, aStartTime, aEndtime, this, aHeadLibrarian);
  }

  public boolean addHoliday(Holiday aHoliday)
  {
    boolean wasAdded = false;
    if (holidays.contains(aHoliday)) { return false; }
    LibrarySystem existingLibrarySystem = aHoliday.getLibrarySystem();
    boolean isNewLibrarySystem = existingLibrarySystem != null && !this.equals(existingLibrarySystem);
    if (isNewLibrarySystem)
    {
      aHoliday.setLibrarySystem(this);
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
    //Unable to remove aHoliday, as it must always have a librarySystem
    if (!this.equals(aHoliday.getLibrarySystem()))
    {
      holidays.remove(aHoliday);
      wasRemoved = true;
    }
    return wasRemoved;
  }

  public void delete()
  {
    if(!(userAccounts == null)){
      for (UserAccount account: userAccounts)
      {
        account.delete();
        userAccounts.remove(account);
      }
    }
    
    if(!(newspapers== null)){
      for (Newspaper newspaper:newspapers)
      {
        newspaper.delete();
        newspapers.remove(newspaper);
      }
    }
    
    if(!(openingHours==null)){
      for (OpeningHour hour : openingHours)
      {
        hour.delete();
        openingHours.remove(hour);
      }
    }
    
    if(!(timeSlots==null))
    {
      for (TimeSlot time: timeSlots)
      {
        time.delete();
        timeSlots.remove(time);
      }
    }
    
    if(!(newspaperArticles==null))
    {
      for (NewspaperArticle article: newspaperArticles)
      {
        article.delete();
        newspaperArticles.remove(article);
      }
    }
    
    if(!(libraryItems==null))
    {
      for (LibraryItem item: libraryItems)
      {
        item.delete();
        libraryItems.remove(item);
      }
    }

    if(!(holidays==null))
    {
      for (Holiday holiday: holidays)
      {
        holiday.delete();
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