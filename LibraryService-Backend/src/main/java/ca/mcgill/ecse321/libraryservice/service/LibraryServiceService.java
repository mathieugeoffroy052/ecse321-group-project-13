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

    @Transactional
    public List<TimeSlot> getTimeSlotsFromLibrarian(Librarian librarian) {
        List<TimeSlot> librarianTimeSlots = timeSlotRepository.findByLibrarian(librarian);
        return librarianTimeSlots;
    }

    @Transactional
    public List<TimeSlot> getTimeSlotsFromHeadLibrarian(HeadLibrarian headLibrarian) {
        List<TimeSlot> timeSlots = timeSlotRepository.findByHeadLibrarian(headLibrarian);
        return timeSlots;
    }

    @Transactional
    public List<TimeSlot> getTimeSlotsFromLibrarianFirstNameAndLastName(String firstName, String lastName) {
        Librarian librarian = (Librarian) userAccountRepository.findByFirstNameAndLastName(firstName, lastName);
        List<TimeSlot> librarianTimeSlots = timeSlotRepository.findByLibrarian(librarian);
        return librarianTimeSlots;
    }

    @Transactional
    public List<TimeSlot> getTimeSlotsFromLibrarianUserID(int id) {
        Librarian librarian = (Librarian) userAccountRepository.findUserAccountByUserID(id);
        List<TimeSlot> librarianTimeSlots = timeSlotRepository.findByLibrarian(librarian);
        return librarianTimeSlots;
    }

    @Transactional
    public TimeSlot getTimeSlotsFromId(int id) {
        TimeSlot timeSlot = timeSlotRepository.findTimeSlotByTimeSlotID(id);
        return timeSlot;
    }

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

    @Transactional
    public TimeSlot assignTimeSlotToLibrarian(TimeSlot timeSlot, Librarian librarian) {
        timeSlot.addLibrarian(librarian);
        return timeSlot;
    }

    /* Opening Hours Service Methods */
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

    @Transactional
    public OpeningHour getOpeningHourFromID(int id) {
        OpeningHour openingHour = openingHourRepository.findOpeningHourByHourID(id);
        return openingHour;
    }
     
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

    @Transactional
    public List<OpeningHour> getOpeningHoursFromHeadLibrarian(HeadLibrarian headLibrarian) {
        List<OpeningHour> openingHours = openingHourRepository.findByHeadLibrarian(headLibrarian);
        return openingHours;
    }

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

    @Transactional
    public Holiday getHolidayFromId(int id) {
        Holiday holiday = holidayRepository.findHolidayByHolidayID(id);
        return holiday;
    }

    @Transactional
    public List<Holiday> getHolidaysFromHeadLibrarian(HeadLibrarian headLibrarian) {
        List<Holiday> holidays = holidayRepository.findByHeadLibrarian(headLibrarian);
        return holidays;
    }

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