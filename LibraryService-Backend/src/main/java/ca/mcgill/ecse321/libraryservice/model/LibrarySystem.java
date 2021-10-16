/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4607.2d2b84eb8 modeling language!*/

package ca.mcgill.ecse321.libraryservice.model;
import java.util.*;
import java.sql.Time;
import java.sql.Date;
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
  private List<UserAccount> userAccounts;
  private List<Newspaper> newspapers;
  private List<OpeningHour> openingHours;
  private List<TimeSlot> timeSlots;
  private List<NewspaperArticle> newspaperArticles;
  private List<Person> persons;
  private List<LibraryItem> libraryItems;
  private List<Holiday> holidays;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public LibrarySystem()
  {
    systemId = nextSystemId++;
    userAccounts = new ArrayList<UserAccount>();
    newspapers = new ArrayList<Newspaper>();
    openingHours = new ArrayList<OpeningHour>();
    timeSlots = new ArrayList<TimeSlot>();
    newspaperArticles = new ArrayList<NewspaperArticle>();
    persons = new ArrayList<Person>();
    libraryItems = new ArrayList<LibraryItem>();
    holidays = new ArrayList<Holiday>();
  }

  //------------------------
  // INTERFACE
  //------------------------
  @Id
  public int getSystemId()
  {
    return systemId;
  }


  public boolean setSystemId(int aSystemId)
  {
    systemId = aSystemId;
    if(systemId==aSystemId){
      return true;
    }
    else return false;
  }

  public boolean setNewspaperArticles(ArrayList<NewspaperArticle> aNewspaperArticles)
  {
    newspaperArticles = aNewspaperArticles;
    if(newspaperArticles==aNewspaperArticles){
      return true;
    }
    else return false;
  }

  public boolean setHolidays(ArrayList<Holiday> aholidays)
  {
    holidays = aholidays;
    if(holidays==aholidays){
      return true;
    }
    else return false;
  }

  public boolean setPersons(ArrayList<Person> aPersons)
  {
    persons = aPersons;
    if(persons==aPersons){
      return true;
    }
    else return false;
  }

  public boolean setLibraryItems(ArrayList<LibraryItem> aLibraryItems)
  {
    libraryItems = aLibraryItems;
    if(libraryItems==aLibraryItems){
      return true;
    }
    else return false;
  }

  public boolean setTimeSlots(ArrayList<TimeSlot> aTimeSlots)
  {
    timeSlots = aTimeSlots;
    if(timeSlots==aTimeSlots){
      return true;
    }
    else return false;
  }

  public boolean setOpeningHours(ArrayList<OpeningHour> aOpeningHours)
  {
    openingHours = aOpeningHours;
    if(openingHours==aOpeningHours){
      return true;
    }
    else return false;
  }

  public boolean setNewspapers(ArrayList<Newspaper> aNewspapers)
  {
    newspapers = aNewspapers;
    if(newspapers==aNewspapers){
      return true;
    }
    else return false;
  }

  public boolean setUserAccounts(ArrayList<UserAccount> aUserAccounts)
  {
    userAccounts = aUserAccounts;
    if(userAccounts==aUserAccounts){
      return true;
    }
    else return false;
  }

  /* Code from template association_GetMany */
  public UserAccount getUserAccount(int index)
  {
    UserAccount aUserAccount = userAccounts.get(index);
    return aUserAccount;
  }
  @OneToMany
  public List<UserAccount> getUserAccounts()
  {
    List<UserAccount> newUserAccounts = Collections.unmodifiableList(userAccounts);
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

  public int indexOfUserAccount(UserAccount aUserAccount)
  {
    int index = userAccounts.indexOf(aUserAccount);
    return index;
  }
  /* Code from template association_GetMany */
  public Newspaper getNewspaper(int index)
  {
    Newspaper aNewspaper = newspapers.get(index);
    return aNewspaper;
  }
  @OneToMany
  public List<Newspaper> getNewspapers()
  {
    List<Newspaper> newNewspapers = Collections.unmodifiableList(newspapers);
    return newNewspapers;
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

  public int indexOfNewspaper(Newspaper aNewspaper)
  {
    int index = newspapers.indexOf(aNewspaper);
    return index;
  }
  /* Code from template association_GetMany */
  public OpeningHour getOpeningHour(int index)
  {
    OpeningHour aOpeningHour = openingHours.get(index);
    return aOpeningHour;
  }
  @OneToMany
  public List<OpeningHour> getOpeningHours()
  {
    List<OpeningHour> newOpeningHours = Collections.unmodifiableList(openingHours);
    return newOpeningHours;
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

  public int indexOfOpeningHour(OpeningHour aOpeningHour)
  {
    int index = openingHours.indexOf(aOpeningHour);
    return index;
  }
  /* Code from template association_GetMany */
  public TimeSlot getTimeSlot(int index)
  {
    TimeSlot aTimeSlot = timeSlots.get(index);
    return aTimeSlot;
  }
  @OneToMany
  public List<TimeSlot> getTimeSlots()
  {
    List<TimeSlot> newTimeSlots = Collections.unmodifiableList(timeSlots);
    return newTimeSlots;
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

  public int indexOfTimeSlot(TimeSlot aTimeSlot)
  {
    int index = timeSlots.indexOf(aTimeSlot);
    return index;
  }
  /* Code from template association_GetMany */
  public NewspaperArticle getNewspaperArticle(int index)
  {
    NewspaperArticle aNewspaperArticle = newspaperArticles.get(index);
    return aNewspaperArticle;
  }
  @OneToMany
  public List<NewspaperArticle> getNewspaperArticles()
  {
    List<NewspaperArticle> newNewspaperArticles = Collections.unmodifiableList(newspaperArticles);
    return newNewspaperArticles;
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

  public int indexOfNewspaperArticle(NewspaperArticle aNewspaperArticle)
  {
    int index = newspaperArticles.indexOf(aNewspaperArticle);
    return index;
  }
  /* Code from template association_GetMany */
  public Person getPerson(int index)
  {
    Person aPerson = persons.get(index);
    return aPerson;
  }
  @OneToMany
  public List<Person> getPersons()
  {
    List<Person> newPersons = Collections.unmodifiableList(persons);
    return newPersons;
  }

  public int numberOfPersons()
  {
    int number = persons.size();
    return number;
  }

  public boolean hasPersons()
  {
    boolean has = persons.size() > 0;
    return has;
  }

  public int indexOfPerson(Person aPerson)
  {
    int index = persons.indexOf(aPerson);
    return index;
  }

  /* Code from template association_GetMany */
  public LibraryItem getLibraryItem(int index)
  {
    LibraryItem aLibraryItem = libraryItems.get(index);
    return aLibraryItem;
  }
  @OneToMany
  public List<LibraryItem> getLibraryItems()
  {
    List<LibraryItem> newLibraryItems = Collections.unmodifiableList(libraryItems);
    return newLibraryItems;
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

  public int indexOfLibraryItem(LibraryItem aLibraryItem)
  {
    int index = libraryItems.indexOf(aLibraryItem);
    return index;
  }
  /* Code from template association_GetMany */
  public Holiday getHoliday(int index)
  {
    Holiday aHoliday = holidays.get(index);
    return aHoliday;
  }
  @OneToMany
  public List<Holiday> getholidays()
  {
    List<Holiday> newholidays = Collections.unmodifiableList(holidays);
    return newholidays;
  }

  public int numberOfholidays()
  {
    int number = holidays.size();
    return number;
  }

  public boolean hasholidays()
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
  public static int minimumNumberOfUserAccounts()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */


  public boolean addUserAccount(UserAccount aUserAccount)
  {
    boolean wasAdded = false;
    if (userAccounts.contains(aUserAccount)) { return false; }
    LibrarySystem existingLibrarySystem = aUserAccount.getLibrarySystem();
    boolean isNewLibrarySystem = existingLibrarySystem != null && !this.equals(existingLibrarySystem);
    if (isNewLibrarySystem)
    {
      aUserAccount.setLibrarySystem(this);
    }
    else
    {
      userAccounts.add(aUserAccount);
    }
    wasAdded = true;
    return wasAdded;
  }

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
  /* Code from template association_AddIndexControlFunctions */
  public boolean addUserAccountAt(UserAccount aUserAccount, int index)
  {  
    boolean wasAdded = false;
    if(addUserAccount(aUserAccount))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfUserAccounts()) { index = numberOfUserAccounts() - 1; }
      userAccounts.remove(aUserAccount);
      userAccounts.add(index, aUserAccount);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveUserAccountAt(UserAccount aUserAccount, int index)
  {
    boolean wasAdded = false;
    if(userAccounts.contains(aUserAccount))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfUserAccounts()) { index = numberOfUserAccounts() - 1; }
      userAccounts.remove(aUserAccount);
      userAccounts.add(index, aUserAccount);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addUserAccountAt(aUserAccount, index);
    }
    return wasAdded;
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
  /* Code from template association_AddIndexControlFunctions */
  public boolean addNewspaperAt(Newspaper aNewspaper, int index)
  {  
    boolean wasAdded = false;
    if(addNewspaper(aNewspaper))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfNewspapers()) { index = numberOfNewspapers() - 1; }
      newspapers.remove(aNewspaper);
      newspapers.add(index, aNewspaper);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveNewspaperAt(Newspaper aNewspaper, int index)
  {
    boolean wasAdded = false;
    if(newspapers.contains(aNewspaper))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfNewspapers()) { index = numberOfNewspapers() - 1; }
      newspapers.remove(aNewspaper);
      newspapers.add(index, aNewspaper);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addNewspaperAt(aNewspaper, index);
    }
    return wasAdded;
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
  /* Code from template association_AddIndexControlFunctions */
  public boolean addOpeningHourAt(OpeningHour aOpeningHour, int index)
  {  
    boolean wasAdded = false;
    if(addOpeningHour(aOpeningHour))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfOpeningHours()) { index = numberOfOpeningHours() - 1; }
      openingHours.remove(aOpeningHour);
      openingHours.add(index, aOpeningHour);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveOpeningHourAt(OpeningHour aOpeningHour, int index)
  {
    boolean wasAdded = false;
    if(openingHours.contains(aOpeningHour))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfOpeningHours()) { index = numberOfOpeningHours() - 1; }
      openingHours.remove(aOpeningHour);
      openingHours.add(index, aOpeningHour);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addOpeningHourAt(aOpeningHour, index);
    }
    return wasAdded;
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
  /* Code from template association_AddIndexControlFunctions */
  public boolean addTimeSlotAt(TimeSlot aTimeSlot, int index)
  {  
    boolean wasAdded = false;
    if(addTimeSlot(aTimeSlot))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfTimeSlots()) { index = numberOfTimeSlots() - 1; }
      timeSlots.remove(aTimeSlot);
      timeSlots.add(index, aTimeSlot);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveTimeSlotAt(TimeSlot aTimeSlot, int index)
  {
    boolean wasAdded = false;
    if(timeSlots.contains(aTimeSlot))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfTimeSlots()) { index = numberOfTimeSlots() - 1; }
      timeSlots.remove(aTimeSlot);
      timeSlots.add(index, aTimeSlot);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addTimeSlotAt(aTimeSlot, index);
    }
    return wasAdded;
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
  /* Code from template association_AddIndexControlFunctions */
  public boolean addNewspaperArticleAt(NewspaperArticle aNewspaperArticle, int index)
  {  
    boolean wasAdded = false;
    if(addNewspaperArticle(aNewspaperArticle))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfNewspaperArticles()) { index = numberOfNewspaperArticles() - 1; }
      newspaperArticles.remove(aNewspaperArticle);
      newspaperArticles.add(index, aNewspaperArticle);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveNewspaperArticleAt(NewspaperArticle aNewspaperArticle, int index)
  {
    boolean wasAdded = false;
    if(newspaperArticles.contains(aNewspaperArticle))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfNewspaperArticles()) { index = numberOfNewspaperArticles() - 1; }
      newspaperArticles.remove(aNewspaperArticle);
      newspaperArticles.add(index, aNewspaperArticle);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addNewspaperArticleAt(aNewspaperArticle, index);
    }
    return wasAdded;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfPersons()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public Person addPerson(String aFirstName, String aLastName)
  {
    return new Person(aFirstName, aLastName, this);
  }

  public boolean addPerson(Person aPerson)
  {
    boolean wasAdded = false;
    if (persons.contains(aPerson)) { return false; }
    LibrarySystem existingLibrarySystem = aPerson.getLibrarySystem();
    boolean isNewLibrarySystem = existingLibrarySystem != null && !this.equals(existingLibrarySystem);
    if (isNewLibrarySystem)
    {
      aPerson.setLibrarySystem(this);
    }
    else
    {
      persons.add(aPerson);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removePerson(Person aPerson)
  {
    boolean wasRemoved = false;
    //Unable to remove aPerson, as it must always have a librarySystem
    if (!this.equals(aPerson.getLibrarySystem()))
    {
      persons.remove(aPerson);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addPersonAt(Person aPerson, int index)
  {  
    boolean wasAdded = false;
    if(addPerson(aPerson))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfPersons()) { index = numberOfPersons() - 1; }
      persons.remove(aPerson);
      persons.add(index, aPerson);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMovePersonAt(Person aPerson, int index)
  {
    boolean wasAdded = false;
    if(persons.contains(aPerson))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfPersons()) { index = numberOfPersons() - 1; }
      persons.remove(aPerson);
      persons.add(index, aPerson);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addPersonAt(aPerson, index);
    }
    return wasAdded;
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
  /* Code from template association_AddIndexControlFunctions */
  public boolean addLibraryItemAt(LibraryItem aLibraryItem, int index)
  {  
    boolean wasAdded = false;
    if(addLibraryItem(aLibraryItem))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfLibraryItems()) { index = numberOfLibraryItems() - 1; }
      libraryItems.remove(aLibraryItem);
      libraryItems.add(index, aLibraryItem);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveLibraryItemAt(LibraryItem aLibraryItem, int index)
  {
    boolean wasAdded = false;
    if(libraryItems.contains(aLibraryItem))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfLibraryItems()) { index = numberOfLibraryItems() - 1; }
      libraryItems.remove(aLibraryItem);
      libraryItems.add(index, aLibraryItem);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addLibraryItemAt(aLibraryItem, index);
    }
    return wasAdded;
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
  /* Code from template association_AddIndexControlFunctions */
  public boolean addHolidayAt(Holiday aHoliday, int index)
  {  
    boolean wasAdded = false;
    if(addHoliday(aHoliday))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfholidays()) { index = numberOfholidays() - 1; }
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
      if(index > numberOfholidays()) { index = numberOfholidays() - 1; }
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
    while (userAccounts.size() > 0)
    {
      UserAccount aUserAccount = userAccounts.get(userAccounts.size() - 1);
      aUserAccount.delete();
      userAccounts.remove(aUserAccount);
    }
    
    while (newspapers.size() > 0)
    {
      Newspaper aNewspaper = newspapers.get(newspapers.size() - 1);
      aNewspaper.delete();
      newspapers.remove(aNewspaper);
    }
    
    while (openingHours.size() > 0)
    {
      OpeningHour aOpeningHour = openingHours.get(openingHours.size() - 1);
      aOpeningHour.delete();
      openingHours.remove(aOpeningHour);
    }
    
    while (timeSlots.size() > 0)
    {
      TimeSlot aTimeSlot = timeSlots.get(timeSlots.size() - 1);
      aTimeSlot.delete();
      timeSlots.remove(aTimeSlot);
    }
    
    while (newspaperArticles.size() > 0)
    {
      NewspaperArticle aNewspaperArticle = newspaperArticles.get(newspaperArticles.size() - 1);
      aNewspaperArticle.delete();
      newspaperArticles.remove(aNewspaperArticle);
    }
    
    while (persons.size() > 0)
    {
      Person aPerson = persons.get(persons.size() - 1);
      aPerson.delete();
      persons.remove(aPerson);
    }
    
    while (libraryItems.size() > 0)
    {
      LibraryItem aLibraryItem = libraryItems.get(libraryItems.size() - 1);
      aLibraryItem.delete();
      libraryItems.remove(aLibraryItem);
    }
    
    while (holidays.size() > 0)
    {
      Holiday aHoliday = holidays.get(holidays.size() - 1);
      aHoliday.delete();
      holidays.remove(aHoliday);
    }
    
  }


  public String toString()
  {
    return super.toString() + "["+
            "systemId" + ":" + getSystemId()+ "]";
  }
}