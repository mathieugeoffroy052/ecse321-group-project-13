package ca.mcgill.ecse321.libraryservice.service;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.libraryservice.dao.*;
import ca.mcgill.ecse321.libraryservice.model.*;
import ca.mcgill.ecse321.libraryservice.model.LibraryItem.ItemType;
import ca.mcgill.ecse321.libraryservice.model.OpeningHour.DayOfWeek;
import ca.mcgill.ecse321.libraryservice.model.Transaction.TransactionType;


@Service
public class LibraryServiceService {

    //all DAOs
    @Autowired
    private BorrowableItemRepository borrowableItemRepository;
    @Autowired
    private HeadLibrarianRepository headLibrarianRepository;
    @Autowired
    private HolidayRepository holidayRepository;
    @Autowired
    private LibrarianRepository librarianRepository;
    @Autowired
    private LibraryItemRepository libraryItemRepository;
    @Autowired
    private LibrarySystemRepository librarySystemRepository;
    @Autowired
    private OpeningHourRepository openingHourRepository;
    @Autowired
    private PatronRepository patronRepository;
    @Autowired
    private TimeSlotRepository timeSlotRepository;
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private UserAccountRepository userAccountRepository;

    @Transactional
    public List<BorrowableItem> getBorrowableItemsFromItemIsbn(int isbn){
        LibraryItem item = libraryItemRepository.findByIsbn(isbn);
        List<BorrowableItem> allBorrowableItems = borrowableItemRepository.findByLibraryItem(item);
        return allBorrowableItems;
    }

    @Transactional
    public List<LibraryItem> getAllBooks() throws Exception{
        LibrarySystem library;
        try{
            library = (LibrarySystem) librarySystemRepository.findAll().iterator().next(); // uses the first library system found in the database
        }catch(NoSuchElementException e){
            throw new Exception("No library system(s) exist in the database");
        }
        List<LibraryItem> allLibraryItems = libraryItemRepository.findByLibrarySystem(library);
        List<LibraryItem> allBooks = new ArrayList<LibraryItem>();
        for(LibraryItem i : allLibraryItems){
            if(i.getType().equals(ItemType.Book)){
                allBooks.add(i);
            }
        }
        return allBooks;
    }

    @Transactional
    public LibraryItem getBookFromAuthor(String authorName) throws Exception{
        List<LibraryItem> allBooks = getAllBooks();
        for(LibraryItem a : allBooks){
            if(a.getCreator().equals(authorName)) return a;
        }
        return null;
    }

    @Transactional
    public LibraryItem getBookFromTitle(String bookTitle) throws Exception{
        List<LibraryItem> allBooks = getAllBooks();
        for(LibraryItem a : allBooks){
            if(a.getName().equals(bookTitle)) return a;
        }
        return null;
    }

    @Transactional
    public List<LibraryItem> getAllMusic() throws Exception{
        LibrarySystem library;
        try{
            library = (LibrarySystem) librarySystemRepository.findAll().iterator().next(); // uses the first library system found in the database
        }catch(NoSuchElementException e){
            throw new Exception("No library system(s) exist in the database");
        }
        List<LibraryItem> allLibraryItems = libraryItemRepository.findByLibrarySystem(library);
        List<LibraryItem> allMusic = new ArrayList<LibraryItem>();
        for(LibraryItem i : allLibraryItems){
            if(i.getType().equals(ItemType.Music)){
                allMusic.add(i);
            }
        }
        return allMusic;
    }

    @Transactional
    public LibraryItem getMusicFromArtist(String artistName) throws Exception{
        List<LibraryItem> allMusic = getAllMusic();
        for(LibraryItem a : allMusic){
            if(a.getCreator().equals(artistName)) return a;
        }
        return null;
    }

    @Transactional
    public LibraryItem getMusicFromTitle(String musicTitle) throws Exception{
        List<LibraryItem> allMusic = getAllMusic();
        for(LibraryItem a : allMusic){
            if(a.getName().equals(musicTitle)) return a;
        }
        return null;
    }

    @Transactional
    public List<LibraryItem> getAllMovies() throws Exception{
        LibrarySystem library;
        try{
            library = (LibrarySystem) librarySystemRepository.findAll().iterator().next(); // uses the first library system found in the database
        }catch(NoSuchElementException e){
            throw new Exception("No library system(s) exist in the database");
        }
        List<LibraryItem> allLibraryItems = libraryItemRepository.findByLibrarySystem(library);
        List<LibraryItem> allMovies = new ArrayList<LibraryItem>();
        for(LibraryItem i : allLibraryItems){
            if(i.getType().equals(ItemType.Music)){
                allMovies.add(i);
            }
        }
        return allMovies;
    }

    @Transactional
    public LibraryItem getMovieFromDirector(String directorName) throws Exception{
        List<LibraryItem> allMovies = getAllMovies();
        for(LibraryItem a : allMovies){
            if(a.getCreator().equals(directorName)) return a;
        }
        return null;
    }

    @Transactional
    public LibraryItem getMovieFromTitle(String movieTitle) throws Exception{
        List<LibraryItem> allMovies = getAllMovies();
        for(LibraryItem a : allMovies){
            if(a.getName().equals(movieTitle)) return a;
        }
        return null;
    }

    @Transactional
    public Transaction createItemReserveTransaction(BorrowableItem item, UserAccount account){
        LocalDate localDeadline = LocalDate.now().plusDays(7); // 7 day deadline for reservation?
        Date deadline = Date.valueOf(localDeadline);
        Transaction itemReservation = new Transaction(item, account, TransactionType.ItemReservation, deadline); 
        transactionRepository.save(itemReservation);
        return itemReservation;
    }

    @Transactional
    public Transaction createRoomReserveTransaction(BorrowableItem item, UserAccount account){
        Transaction itemReservation = new Transaction(item, account, TransactionType.RoomReservation, null); // No deadline for room reservation
        transactionRepository.save(itemReservation);
        return itemReservation;
    }


    @Transactional
    public Transaction createItemBorrowTransaction(BorrowableItem item, UserAccount account){
        LocalDate localDeadline = LocalDate.now().plusDays(20); // Deadline is set 20 days away from current day
        Date deadline = Date.valueOf(localDeadline);
        Transaction itemReservation = new Transaction(item, account, TransactionType.Borrowing, deadline); 
        transactionRepository.save(itemReservation);
        return itemReservation;
    }

    @Transactional
    public Transaction createItemReturnTransaction(BorrowableItem item, UserAccount account){
        Transaction itemReservation = new Transaction(item, account, TransactionType.Return, null); // No deadline for return
        transactionRepository.save(itemReservation);
        return itemReservation;
    }

    @Transactional
    public Transaction createItemWaitlistTransaction(BorrowableItem item, UserAccount account){
        Transaction itemReservation = new Transaction(item, account, TransactionType.Waitlist, null); // No deadline for waitlist
        transactionRepository.save(itemReservation);
        return itemReservation;
    }

    @Transactional
    public Transaction createItemRenewalTransaction(BorrowableItem item, UserAccount account){
        LocalDate localDeadline = LocalDate.now().plusDays(20); // Deadline is set 20 days away from current day
        Date deadline = Date.valueOf(localDeadline);
        Transaction itemReservation = new Transaction(item, account, TransactionType.Renewal, deadline); 
        transactionRepository.save(itemReservation);
        return itemReservation;
    }

    @Transactional
    public List<BorrowableItem> getBorrowedItemsFromUser(UserAccount account){
        List<Transaction> allUserTransactions = transactionRepository.findByUserAccount(account);
        List<BorrowableItem> allBorrowedItems = new ArrayList<BorrowableItem>();
        for(Transaction t : allUserTransactions){
            if(t.getTransactionType().equals(TransactionType.Borrowing) || t.getTransactionType().equals(TransactionType.Renewal)){
               allBorrowedItems.add(t.getBorrowableItem()); 
            } 
        }
        return allBorrowedItems;
    }

    @Transactional
    public List<BorrowableItem> getReservedItemsFromUser(UserAccount account){
        List<Transaction> allUserTransactions = transactionRepository.findByUserAccount(account);
        List<BorrowableItem> allReservedItems = new ArrayList<BorrowableItem>();
        for(Transaction t : allUserTransactions){
            if(t.getTransactionType().equals(TransactionType.ItemReservation)){
                allReservedItems.add(t.getBorrowableItem()); 
            } 
        }
        return allReservedItems;
    }

    @Transactional
    public List<UserAccount> getUsersOnWaitlist(BorrowableItem item){
        List<Transaction> allItemTransactions = transactionRepository.findByBorrowableItem(item);
        List<UserAccount> users = new ArrayList<UserAccount>();
        for(Transaction t : allItemTransactions) users.add(t.getUserAccount());
        return users;
    }

    /* TimeSlot Service Methods */
    /**
     * Get all timeslots from the first and only library system.
     * @throws Exception - If there is no library system
     * @return List of TimeSlots
     */
    @Transactional
    public List<TimeSlot> getAllTimeSlots() throws Exception {
        LibrarySystem library;
        try{
            library = (LibrarySystem) librarySystemRepository.findAll().iterator().next(); // uses the first library system found in the database
        }catch(NoSuchElementException e){
            throw new Exception("No library system(s) exist in the database");
        }
        List<TimeSlot> allTimeSlots = timeSlotRepository.findByLibrarySystem(library);
        return allTimeSlots;
    }

    /**
     * Get a list of timeslots for a specific librarian
     * @param librarian - librarian that is 'working' those timeslots
     * @return List of timeslots
     */
    @Transactional
    public List<TimeSlot> getTimeSlotsFromLibrarian(Librarian librarian) {
        List<TimeSlot> librarianTimeSlots = timeSlotRepository.findByLibrarian(librarian);
        return librarianTimeSlots;
    }

    /**
     * Get a list of timeslots that have been assigned by the (only) head librarian
     * @return List of timeslots
     */
    @Transactional
    public List<TimeSlot> getTimeSlotsFromHeadLibrarian(HeadLibrarian headLibrarian) {
        List<TimeSlot> timeSlots = timeSlotRepository.findByHeadLibrarian(headLibrarian);
        return timeSlots;
    }

    /**
     * Get timeslot list (workshits) for a specific librarian by inputing the librarian's first and last name
     * @param firstName - librarian's first name
     * @param lastName - librarian's last name
     * @return list of timeslots
     */
    @Transactional
    public List<TimeSlot> getTimeSlotsFromLibrarianFirstNameAndLastName(String firstName, String lastName) {
        Librarian librarian = (Librarian) userAccountRepository.findByFirstNameAndLastName(firstName, lastName);
        List<TimeSlot> librarianTimeSlots = timeSlotRepository.findByLibrarian(librarian);
        return librarianTimeSlots;
    }

    /**
     * Get timeslot list (workshifts) for a specific librarian by inputing the librarian,s UserId
     * @param id - Librarian's user id
     * @return list of timeslots
     */
    @Transactional
    public List<TimeSlot> getTimeSlotsFromLibrarianUserID(int id) {
        Librarian librarian = (Librarian) userAccountRepository.findUserAccountByUserID(id);
        List<TimeSlot> librarianTimeSlots = timeSlotRepository.findByLibrarian(librarian);
        return librarianTimeSlots;
    }

    /**
     * Get a timeslot by inputing its id
     * @param id - timeslot id
     * @return TimeSlot
    */
    @Transactional
    public TimeSlot getTimeSlotsFromId(int id) {
        TimeSlot timeSlot = timeSlotRepository.findTimeSlotByTimeSlotID(id);
        return timeSlot;
    }

    /**
     * Create a timeslot in the first (and only) available library system with the first (and only)
     * available head librarian. The timeslot is saved to the DB.
     * @param startDate - Start date of timeslot
     * @param startTime - Start time of timeslot
     * @param endDate - End date of timeslot 
     * @param endTime - End time of timeslot
     * @return Timeslot that was created
     * @throws Exception - If the library system does not exit
     * @throws Exception - If there is no head librarian
     */
    @Transactional
    public TimeSlot createTimeSlot(Date startDate, Time startTime, Date endDate, Time endTime) throws Exception {
        LibrarySystem library;
        try{
            library = (LibrarySystem) librarySystemRepository.findAll().iterator().next(); // uses the first library system found in the database
        }catch(NoSuchElementException e){
            throw new Exception("No library system(s) exist in the database");
        }
        HeadLibrarian headLibrarian;
        try {
            headLibrarian = headLibrarianRepository.findAll().iterator().next(); //find first and only head librarian
        } catch(NoSuchElementException e) {
            throw new Exception("No Head Librarian exits in the database");
        }
        TimeSlot timeSlot = new TimeSlot(startDate, startTime, endDate, endTime, library, headLibrarian);
        timeSlotRepository.save(timeSlot);
        return timeSlot;
    }

    /**
     * Assigns a librarian to a timeslot and update the DB
     * @param ts - the timeslot to which the librarian will be assigned
     * @param librarian - the librarian being assigned
     * @return updated TimeSlot
     */
    @Transactional
    public TimeSlot assignTimeSlotToLibrarian(TimeSlot ts, Librarian librarian) {
        TimeSlot timeSlot = timeSlotRepository.findTimeSlotByTimeSlotID(ts.getTimeSlotID());
        timeSlot.addLibrarian(librarian);
        timeSlotRepository.save(timeSlot);
        return timeSlot;
    }

    /* Opening Hours Service Methods */
    /**
     * get all the opening hours in the first (and only) available library system
     * @return lsit of OpeningHour
     * @throws Exception - when there is no library system
     */
    @Transactional
    public List<OpeningHour> getAllOpeningHours() throws Exception {
        LibrarySystem library;
        try{
            library = (LibrarySystem) librarySystemRepository.findAll().iterator().next(); // uses the first library system found in the database
        }catch(NoSuchElementException e){
            throw new Exception("No library system(s) exist in the database");
        }
        List<OpeningHour> allOpeningHours = openingHourRepository.findByLibrarySystem(library);
        return allOpeningHours;
    }
    /**
     * get opening hour from its id
     * @param id - opening hour id
     * @return  openingHour 
     */
    @Transactional
    public OpeningHour getOpeningHourFromID(int id) {
        OpeningHour openingHour = openingHourRepository.findOpeningHourByHourID(id);
        return openingHour;
    }
     
    /**
     * get opening hour list for a given day of the week
     * @param day - string for day of week that MUST start with a capital letter (case sensitive matching)
     * @return list of opening hours of a certain day of week
     * @throws Exception - when the input is not match (case-sensitve) with the correct day of week
     */
    @Transactional
    public List<OpeningHour> getOpeningHoursByDayOfWeek(String day) throws Exception{
        DayOfWeek dayOfWeek;
        try {
            dayOfWeek = DayOfWeek.valueOf(day); //case sensitive match
        } catch (IllegalArgumentException e) {
            throw new Exception("Invalid day");
        }
        List<OpeningHour> openingHours = openingHourRepository.findByDayOfWeek(dayOfWeek);
        return openingHours;
    }

    /**
     * get the opening hours that have been made by a head librarian
     * @param headLibrarian - the head librarian that made the opening hours
     * @return - list of opening hours
     */
    @Transactional
    public List<OpeningHour> getOpeningHoursFromHeadLibrarian(HeadLibrarian headLibrarian) {
        List<OpeningHour> openingHours = openingHourRepository.findByHeadLibrarian(headLibrarian);
        return openingHours;
    }

    /**
     * Create opening hour with the first (and only) available library system and first (and only) available
     * head librarian. Saves the opening hour to the DB
     * @param day - string of day of week (case-sensistive): MUST start with capital letter
     * @param startTime - start time of opening hour
     * @param endTime - end time of opening hour
     * @return the create opening hour
     * @throws Exception - When the day string does not match the DayOfWeek enum format
     * @throws Exception - When there is no library systme
     * @throws Exception - When there is no head librarian
     */
    @Transactional
    public OpeningHour createOpeningHour(String day, Time startTime, Time endTime) throws Exception {
        DayOfWeek dayOfWeek;
        try {
            dayOfWeek = DayOfWeek.valueOf(day); //case sensitive match
        } catch (IllegalArgumentException e) {
            throw new Exception("Invalid day");
        }
        LibrarySystem library;
        try{
            library = (LibrarySystem) librarySystemRepository.findAll().iterator().next(); // uses the first library system found in the database
        }catch(NoSuchElementException e){
            throw new Exception("No library system(s) exist in the database");
        }
        HeadLibrarian headLibrarian;
        try {
            headLibrarian = headLibrarianRepository.findAll().iterator().next(); //find first and only head librarian
        } catch(NoSuchElementException e) {
            throw new Exception("No Head Librarian exits in the database");
        }
        OpeningHour openingHour = new OpeningHour(dayOfWeek, startTime, endTime, library, headLibrarian);
        openingHourRepository.save(openingHour);
        return openingHour;
    }    

    /* Holiday service methods */
    /**
     * get all the holidays from the first (and only) librry system
     * @return list of holidays
     * @throws Exception - when there is no library system
     */
    @Transactional
    public List<Holiday> getAllHolidays() throws Exception {
        LibrarySystem library;
        try{
            library = (LibrarySystem) librarySystemRepository.findAll().iterator().next(); // uses the first library system found in the database
        }catch(NoSuchElementException e){
            throw new Exception("No library system(s) exist in the database");
        }
        List<Holiday> allHolidays = holidayRepository.findByLibrarySystem(library);
        return allHolidays;
    }

    /**
     * get holiday from its holiday id 
     * @param id - holiday id
     * @return holiday
     */
    @Transactional
    public Holiday getHolidayFromId(int id) {
        Holiday holiday = holidayRepository.findHolidayByHolidayID(id);
        return holiday;
    }

    /**
     * get holidays made by the head librarian
     * @param headLibrarian - head librarian that created the holidays
     * @return list of holiday
     */
    @Transactional
    public List<Holiday> getHolidaysFromHeadLibrarian(HeadLibrarian headLibrarian) {
        List<Holiday> holidays = holidayRepository.findByHeadLibrarian(headLibrarian);
        return holidays;
    }

    /**
     * Create holiday with first (and only) available library system and the first (and only)
     * avaialbe head librarian. Saves to DB
     * @param date - date of holiday
     * @param startTime - start time of holiday
     * @param endTime - end time of holiday
     * @return holiday that was created
     * @throws Exception - when there is no library system
     * @throws Exception - when there is no head librarian
     */
    @Transactional
    public Holiday createHoliday(Date date, Time startTime, Time endTime) throws Exception{
        LibrarySystem library;
        try{
            library = (LibrarySystem) librarySystemRepository.findAll().iterator().next(); // uses the first library system found in the database
        }catch(NoSuchElementException e){
            throw new Exception("No library system(s) exist in the database");
        }
        HeadLibrarian headLibrarian;
        try {
            headLibrarian = headLibrarianRepository.findAll().iterator().next(); //find first and only head librarian
        } catch(NoSuchElementException e) {
            throw new Exception("No Head Librarian exits in the database");
        }
        Holiday holiday = new Holiday(date, startTime, endTime, library, headLibrarian);
        holidayRepository.save(holiday);
        return holiday;
    }

}