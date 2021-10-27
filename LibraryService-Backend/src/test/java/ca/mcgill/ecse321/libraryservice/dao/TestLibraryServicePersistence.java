package ca.mcgill.ecse321.libraryservice.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.sql.Date;
import java.sql.Time;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import ca.mcgill.ecse321.libraryservice.model.*;
import ca.mcgill.ecse321.libraryservice.model.BorrowableItem.ItemState;
import ca.mcgill.ecse321.libraryservice.model.LibraryItem.ItemType;
import ca.mcgill.ecse321.libraryservice.model.OpeningHour.DayOfWeek;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class TestLibraryServicePersistence {
    
    //adding all CRUD interface instances
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

    @AfterEach
    public void clearDatabase() {
        //delete all instances from bottom to top of model
        holidayRepository.deleteAll();
        openingHourRepository.deleteAll();
        timeSlotRepository.deleteAll();
        headLibrarianRepository.deleteAll();
        librarianRepository.deleteAll();
        patronRepository.deleteAll();
        transactionRepository.deleteAll();
        borrowableItemRepository.deleteAll();
        librarianRepository.deleteAll();
        libraryItemRepository.deleteAll();
        userAccountRepository.deleteAll();
        librarySystemRepository.deleteAll();
    }


    @Test
    public void testPersistAndLoadPatron() {
        LibrarySystem library = new LibrarySystem();
        librarySystemRepository.save(library);
        
        //create inputs for patron constructor
        String firstName = "John";
        String lastName = "Doe";
        boolean online = true;
        boolean validated = false;
        String password = "123456789";
        int balance = 0;
        String address = "4000 McGill, Montreal, Canada";

        //create patron
        Patron patron = new Patron(firstName, lastName, online, library, address, validated, password, balance);

        //get patronID to retrieve patron from DB
        int patronID = patron.getUserID();

        //save patron in DB
        patronRepository.save(patron);
        userAccountRepository.save(patron);     

        //clear patron
        patron = null;

        //get patron from DB
        patron = patronRepository.findPatronByUserID(patronID);

        //test functionnality
        assertNotNull(patron, "No Patron retrieved");
        assertEquals(firstName, patron.getFirstName(), "patron.firstName mismatch");
        assertEquals(lastName, patron.getLastName(), "patron.lastName mismatch");
        assertEquals(online, patron.getOnlineAccount(), "patron.onlineAccount mismatch");
        assertEquals(validated, patron.getValidatedAccount(), "patron.validatedAccount mismatch");
        assertEquals(password, patron.getPassword(), "patron.password mismatch");
        assertEquals(address, patron.getAddress(), "patron.address mismatch");
    }


    @Test
    public void testPersistAndLoadLibrarian() {
        LibrarySystem library = new LibrarySystem();
        librarySystemRepository.save(library);

        //create inputs for librarian constructor
        String firstName = "Jake";
        String lastName = "Morello";
        boolean online = false;
        String password = "qwertyuiop";
        int balance = 0;
        String address = "100 Durocher, Montreal, Canada";
        
        //create librarian
        Librarian librarian = new Librarian(firstName, lastName, online, library, address, password, balance);

        //get librarianID to retrieve librarian from DB
        int librarianID = librarian.getUserID();

        //save librarian in DB
        librarianRepository.save(librarian);
        userAccountRepository.save(librarian);


        //clear librarian
        librarian = null;

        //get librarian from DB
        librarian = librarianRepository.findLibrarianByUserID(librarianID);

        //test functionnality
        assertNotNull(librarian, "No librarian retrieved");
        assertEquals(firstName, librarian.getFirstName(), "librarian.firstName mismatch");
        assertEquals(lastName, librarian.getLastName(), "librarian.lastName mismatch");
        assertEquals(online, librarian.getOnlineAccount(), "librarian.onlineAccount mismatch");
        assertEquals(password, librarian.getPassword(), "librarian.password mismatch");
        assertEquals(address, librarian.getAddress(), "librarian.address mismatch");
    }


    @Test
    public void testPersistAndLoadHeadLibrarian() {
        LibrarySystem library = new LibrarySystem();
        librarySystemRepository.save(library);

        //create inputs for head librarian constructor
        String firstName = "Laura";
        String lastName = "Porto";
        boolean online = true;
        String password = "asdfghjkl";
        int balance = 0;
        String address = "500 Sherbrooke, Montreal, Canada";

        //create head librarian
        HeadLibrarian headLibrarian = new HeadLibrarian(firstName, lastName, online, library, address, password, balance);

        //get headLibrarianID to retrieve head librarian from DB
        int headLibrarianID = headLibrarian.getUserID();

        //save head librarian in DB
        headLibrarianRepository.save(headLibrarian);
        librarianRepository.save(headLibrarian);
        userAccountRepository.save(headLibrarian);

        //clear head librarian
        headLibrarian = null;

        //get head librarian from DB
        headLibrarian = headLibrarianRepository.findHeadLibrarianByUserID(headLibrarianID);

        //test functionnality
        assertNotNull(headLibrarian, "No headlibrarian retrieved");
        assertEquals(firstName, headLibrarian.getFirstName(), "headLibrarian.firstName mismatch");
        assertEquals(lastName, headLibrarian.getLastName(), "headLibrarian.lastName mismatch");
        assertEquals(online, headLibrarian.getOnlineAccount(), "headLibrarian.onlineAccount mismatch");
        assertEquals(password, headLibrarian.getPassword(), "headLibrarian.password msmatch");
        assertEquals(address, headLibrarian.getAddress(), "headLibrarian.address mismatch");
    }

    /* No findHeadLibrarianByReference */

    @Test @SuppressWarnings("deprecation")
    public void testPersistAndLoadTimeSlot() {
        LibrarySystem library = new LibrarySystem();
        librarySystemRepository.save(library);

        //create inputs for timeslot constructor
        Date startDate = new Date(2020, 12, 25);
        Time startTime =  new Time(12, 43, 0);
        Date endDate = new Date(2020, 12, 28);
        Time endTime = new Time(13, 55, 3);

        //create inputs for head librarian constructor
        String firstName = "Lorri";
        String lastName = "Kent";
        boolean online = false;
        String password = "zxcvbnm";
        int balance = 0;
        String address = "10 Road, Toronto, Canada";

        //create head librarian
        HeadLibrarian headLibrarian = new HeadLibrarian(firstName, lastName, online, library, address, password, balance);
        
        headLibrarianRepository.save(headLibrarian);
        librarianRepository.save(headLibrarian);
        userAccountRepository.save(headLibrarian);

        //create timeslot
        TimeSlot timeSlot = new TimeSlot(startDate, startTime, endDate, endTime, library, headLibrarian);

        //get timeslotID to retreive from DB
        int timeSlotID = timeSlot.getTimeSlotID();

        //save timeslot to DB
        timeSlotRepository.save(timeSlot);

        //clear timeslot
        timeSlot = null;


        //get timeslot from DB
        timeSlot = timeSlotRepository.findTimeSlotByTimeSlotID(timeSlotID);

        //test functionality
        assertNotNull(timeSlot, "No timeslot retrieved");
        assertEquals(startDate, timeSlot.getStartDate(), "timeslot.startDate mismatch");
        assertEquals(startTime, timeSlot.getStartTime(), "timeslot.startTime mismatch");
        assertEquals(endDate, timeSlot.getEndDate(), "timeslot.endDate mismatch");
        assertEquals(endTime, timeSlot.getEndTime(), "timeslot.endTime mismatch");
        
        //test persistence of head librarian within timeslot
        assertNotNull(timeSlot.getHeadLibrarian(), "No head librarian retrieved");
        assertEquals(firstName, timeSlot.getHeadLibrarian().getFirstName(), "timeslot.headLibrarian.firstName mismatch");
        assertEquals(lastName, timeSlot.getHeadLibrarian().getLastName(), "timeslot.headLibrarian.lastName mismatch");
        assertEquals(online, timeSlot.getHeadLibrarian().getOnlineAccount(), "timeslot.headLibrarian.onlineAccount mismatch");
        assertEquals(password, timeSlot.getHeadLibrarian().getPassword(), "timeslot.headLibrarian.password mismatch");
        assertEquals(address, timeSlot.getHeadLibrarian().getAddress(), "timeslot.headLibrarian.address mismatch");
    }


    @Test @SuppressWarnings("deprecation")
    public void testPersistAndLoadTimeSlotByReferenceByHeadLibrarian() {
        LibrarySystem library = new LibrarySystem();
        librarySystemRepository.save(library);

        //create inputs for timeslot constructor
        Date startDate = new Date(2020, 12, 25);
        Time startTime =  new Time(12, 43, 0);
        Date endDate = new Date(2020, 12, 28);
        Time endTime = new Time(13, 55, 3);

        //create inputs for head librarian constructor
        String firstName = "Lorri";
        String lastName = "Kent";
        boolean online = false;
        String password = "zxcvbnm";
        int balance = 0;
        String address = "10 Road, Toronto, Canada";

        //create head librarian
        HeadLibrarian headLibrarian = new HeadLibrarian(firstName, lastName, online, library, address, password, balance);
        
        headLibrarianRepository.save(headLibrarian);
        librarianRepository.save(headLibrarian);
        userAccountRepository.save(headLibrarian);

        //create timeslot
        TimeSlot timeSlot = new TimeSlot(startDate, startTime, endDate, endTime, library, headLibrarian);

        //save timeslot to DB
        timeSlotRepository.save(timeSlot);

        //clear timeslot
        timeSlot = null;

        //get timeslot from DB
        timeSlot = timeSlotRepository.findByHeadLibrarian(headLibrarian).get(0);

        //test functionality
        assertNotNull(timeSlot, "No timeslot retrieved");
        assertEquals(startDate, timeSlot.getStartDate(), "timeslot.startDate mismatch");
        assertEquals(startTime, timeSlot.getStartTime(), "timeslot.startTime mismatch");
        assertEquals(endDate, timeSlot.getEndDate(), "timeslot.endDate mismatch");
        assertEquals(endTime, timeSlot.getEndTime(), "timeslot.endTime mismatch");
        
        //test persistence of head librarian within timeslot
        assertNotNull(timeSlot.getHeadLibrarian(), "No head librarian retrieved");
        assertEquals(firstName, timeSlot.getHeadLibrarian().getFirstName(), "timeslot.headLibrarian.firstName mismatch");
        assertEquals(lastName, timeSlot.getHeadLibrarian().getLastName(), "timeslot.headLibrarian.lastName mismatch");
        assertEquals(online, timeSlot.getHeadLibrarian().getOnlineAccount(), "timeslot.headLibrarian.onlineAccount mismatch");
        assertEquals(password, timeSlot.getHeadLibrarian().getPassword(), "timeslot.headLibrarian.password mismatch");
        assertEquals(address, timeSlot.getHeadLibrarian().getAddress(), "timeslot.headLibrarian.address mismatch");

    }


    @Test
    public void testPersistAndLoadTimeSlotByReferenceLibrarian() {
        LibrarySystem library = new LibrarySystem();
        librarySystemRepository.save(library);

         //create inputs for TimeSlot 
         Date startDate = new Date(2021, 10, 26);
         Time startTime =  new Time(9, 30, 0);
         Date endDate = new Date(2021, 10, 26);
         Time endTime = new Time(16, 30, 0);

        //inputs for head librarian
        String hLibFirstName = "Anna";
        String hLibLastName = "Banana";
        boolean hLibOnline = true;
        String hLibAddress = "50 Rodeo Dr, Beverly Hills, CA, USA";
        String hLibPassword = "boss";
        int hLibBalance = 0;

        //create head libarian and persist
        HeadLibrarian headLibrarian = new HeadLibrarian(hLibFirstName, hLibLastName, hLibOnline, library, hLibAddress, hLibPassword, hLibBalance);

        headLibrarianRepository.save(headLibrarian);
        librarianRepository.save(headLibrarian);
        userAccountRepository.save(headLibrarian);

        //create time slot and persist
        TimeSlot timeSlot = new TimeSlot(startDate, startTime, endDate, endTime, library, headLibrarian);

        timeSlotRepository.save(timeSlot);

        //inputs for librarian
        String libFirstName = "Ben";
        String libLastName = "William";
        boolean libOnline = true;
        String libAddress = "1000 Rue Sherbrooke O, Montreal, Canada";
        String libPassword = "benspassword";
        int libBalance = 0;

        //create librarian, persist
        Librarian librarian = new Librarian(libFirstName, libLastName, libOnline, library, libAddress, libPassword, libBalance);

        librarianRepository.save(librarian);
        userAccountRepository.save(librarian);

        //add librarian to time slot
        timeSlot.addLibrarian(librarian);

        //null time slot
        timeSlot = null;

        //time slot findByLibrarian 
        timeSlot = timeSlotRepository.findByLibrarian(librarian).get(0);

        //test functionality
        assertNotNull(timeSlot, "No timeSlot retrieved");
        assertEquals(startDate, timeSlot.getStartDate(), "transaction.startDate mismatch");
        assertEquals(startTime, timeSlot.getStartTime(), "transaction.startTime mismatch");
        assertEquals(endDate, timeSlot.getEndDate(), "transaction.endDate mismatch");
        assertEquals(endTime, timeSlot.getEndTime(), "transaction.endTime mismatch");
        assertEquals(library.getSystemId(), timeSlot.getLibrarySystem().getSystemId(), "timeSlot.headLibrarian.librarySystem.systemID mismatch");

        //test persistence of head librian within transaction
        assertEquals(hLibFirstName, timeSlot.getHeadLibrarian().getFirstName(), "timeSlot.headLibrarian.firstName mismatch");
        assertEquals(hLibLastName, timeSlot.getHeadLibrarian().getLastName(), "timeSlot.headLibrarian.lastName mismatch");
        assertEquals(hLibOnline, timeSlot.getHeadLibrarian().getOnlineAccount(), "timeSlot.headLibrarian.onlineAccount mismatch");
        assertEquals(hLibPassword, timeSlot.getHeadLibrarian().getPassword(), "timeSlot.headLibrarian.password mismatch");
        assertEquals(hLibAddress, timeSlot.getHeadLibrarian().getAddress(), "timeSlot.headLibrarian.address mismatch");
        assertEquals(hLibBalance, timeSlot.getHeadLibrarian().getBalance(), "timeSlot.headLibrarian.balance mismatch");
        assertEquals(library.getSystemId(), timeSlot.getHeadLibrarian().getLibrarySystem().getSystemId(), "timeSlot.headLibrarian.librarySystem.systemID mismatch");

        //test persistence of librarian
        Librarian libFromTimeSlot = (Librarian) timeSlot.getLibrarian().toArray()[0];
        assertEquals(libFirstName, libFromTimeSlot.getFirstName(), "timeSlot.librarian[0].firstName mismatch");
        assertEquals(libLastName, libFromTimeSlot.getLastName(), "timeSlot.librarian[0].lastName mismatch");
        assertEquals(libOnline, libFromTimeSlot.getOnlineAccount(), "timeSlot.librarian[0].onlineAccount mismatch");
        assertEquals(libPassword, libFromTimeSlot.getPassword(), "timeSlot.librarian[0].password mismatch");
        assertEquals(libAddress, libFromTimeSlot.getAddress(), "timeSlot.librarian[0].address mismatch");
        assertEquals(libBalance, libFromTimeSlot.getBalance(), "timeSlot.librarian[0].balance mismatch");
        assertEquals(library.getSystemId(), libFromTimeSlot.getLibrarySystem().getSystemId(), "timeSlot.librarian[0].librarySystem.systemID mismatch");
    }


    @Test @SuppressWarnings("deprecation")
    public void testPersistAndLoadHoliday() {
        LibrarySystem library = new LibrarySystem();
        librarySystemRepository.save(library);

        //create inputs for holiday constructor
        Date date = new Date(2020, 12, 25);
        Time startTime =  new Time(12, 43, 0);
        Time endTime = new Time(13, 55, 3);

        //create inputs for head librarian constructor
        String firstName = "Lorri";
        String lastName = "Kent";
        boolean online = false;
        String password = "zxcvbnm";
        int balance = 0;
        String address = "10 Road, Toronto, Canada";

        //create head librarian
        HeadLibrarian headLibrarian = new HeadLibrarian(firstName, lastName, online, library, address, password, balance);
        
        headLibrarianRepository.save(headLibrarian);
        librarianRepository.save(headLibrarian);
        userAccountRepository.save(headLibrarian);

        //create holiday
        Holiday holiday = new Holiday(date, startTime, endTime, library, headLibrarian);

        //get holidayID to retreive from DB
        int holidayID = holiday.getHolidayID();

        //save holiday to DB
        holidayRepository.save(holiday);

        //clear timeslot
        holiday = null;

        //get holiday from DB
        holiday = holidayRepository.findHolidayByHolidayID(holidayID);

        //test functionality
        assertNotNull(holiday, "No holiday retrieved");
        assertEquals(date, holiday.getDate(), "holiday.date mismatch");
        assertEquals(startTime, holiday.getStartTime(), "holiday.startTime mismatch");
        assertEquals(endTime, holiday.getEndtime(), "holiday.endTime mismatch");
        
        
        //test persistence of head librarian within holiday
        assertNotNull(holiday.getHeadLibrarian(), "No head librarian retrieved");
        assertEquals(firstName, holiday.getHeadLibrarian().getFirstName(), "holiday.headLibrarian.firstName mismatch");
        assertEquals(lastName, holiday.getHeadLibrarian().getLastName(), "holiday.headLibrarian.lastName mismatch");
        assertEquals(online, holiday.getHeadLibrarian().getOnlineAccount(), "holiday.headLibrarian.onlineAccount mismatch");
        assertEquals(password, holiday.getHeadLibrarian().getPassword(), "holiday.headLibrarian.password mismatch");
        assertEquals(address, holiday.getHeadLibrarian().getAddress(), "holiday.headLibrarian.address mismatch");

    }


    @Test @SuppressWarnings("deprecation")
    public void testPersistAndLoadHolidayByReference() {
        LibrarySystem library = new LibrarySystem();
        librarySystemRepository.save(library);

        //create inputs for holiday constructor
        Date date = new Date(2020, 12, 25);
        Time startTime =  new Time(12, 43, 0);
        Time endTime = new Time(13, 55, 3);

        //create inputs for head librarian constructor
        String firstName = "Lorri";
        String lastName = "Kent";
        boolean online = false;
        String password = "zxcvbnm";
        int balance = 0;
        String address = "10 Road, Toronto, Canada";

        //create head librarian
        HeadLibrarian headLibrarian = new HeadLibrarian(firstName, lastName, online, library, address, password, balance);
        
        headLibrarianRepository.save(headLibrarian);
        librarianRepository.save(headLibrarian);
        userAccountRepository.save(headLibrarian);

        //create holiday
        Holiday holiday = new Holiday(date, startTime, endTime, library, headLibrarian);

        //save holiday to DB
        holidayRepository.save(holiday);

        //clear timeslot
        holiday = null;

        //get holiday from DB
        holiday = holidayRepository.findByHeadLibrarian(headLibrarian).get(0);

        //test functionality
        assertNotNull(holiday, "No holiday retrieved");
        assertEquals(date, holiday.getDate(), "holiday.date mismatch");
        assertEquals(startTime, holiday.getStartTime(), "holiday.startTime mismatch");
        assertEquals(endTime, holiday.getEndtime(), "holiday.endTime mismatch");
        
        
        //test persistence of head librarian within holiday
        assertNotNull(holiday.getHeadLibrarian(), "No head librarian retrieved");
        assertEquals(firstName, holiday.getHeadLibrarian().getFirstName(), "holiday.headLibrarian.firstName mismatch");
        assertEquals(lastName, holiday.getHeadLibrarian().getLastName(), "holiday.headLibrarian.lastName mismatch");
        assertEquals(online, holiday.getHeadLibrarian().getOnlineAccount(), "holiday.headLibrarian.onlineAccount mismatch");
        assertEquals(password, holiday.getHeadLibrarian().getPassword(), "holiday.headLibrarian.password mismatch");
        assertEquals(address, holiday.getHeadLibrarian().getAddress(), "holiday.headLibrarian.address.address mismatch");
    }

    
    @Test @SuppressWarnings("deprecation")
    public void testPersistAndLoadOpeningHours() {
        LibrarySystem library = new LibrarySystem();
        librarySystemRepository.save(library);

        //create inputs for Opening hours constructor
        DayOfWeek dayOfWeek = DayOfWeek.Saturday;
        Time startTime =  new Time(12, 43, 0);
        Time endTime = new Time(13, 55, 3);

        //create inputs for head librarian constructor
        String firstName = "Lorri";
        String lastName = "Kent";
        boolean online = false;
        String password = "zxcvbnm";
        int balance = 0;
        String address = "10 Road, Toronto, Canada";

        //create head librarian
        HeadLibrarian headLibrarian = new HeadLibrarian(firstName, lastName, online, library, address, password, balance);
       
        headLibrarianRepository.save(headLibrarian);
        librarianRepository.save(headLibrarian);
        userAccountRepository.save(headLibrarian);

        //create opening hour
        OpeningHour openingHour = new OpeningHour(dayOfWeek, startTime, endTime, library, headLibrarian);
        

        //get openingHourID to retreive from DB
        int openingHourID = openingHour.getHourID();

        //save openingHourID to DB
        openingHourRepository.save(openingHour);

        //clear openingHour
        openingHour = null;

        //get openingHour from DB
        openingHour = openingHourRepository.findOpeningHourByHourID(openingHourID);

        //test functionality
        assertNotNull(openingHour, "No openinghour retrieved");
        assertEquals(dayOfWeek, openingHour.getDayOfWeek(), "openingHour.dayOfWeek mismatch");
        assertEquals(startTime, openingHour.getStartTime(), "openingHour.startTime mismatch");
        assertEquals(endTime, openingHour.getEndTime(), "openingHour.endTime mismatch");
        assertEquals(library.getSystemId(), openingHour.getLibrarySystem().getSystemId(), "openingHour.librarySystem.SystemID mismatch");
        
        //test persistence of head librarian within opening hour
        assertNotNull(openingHour.getHeadLibrarian(), "No head librarian retrieved");
        assertEquals(firstName, openingHour.getHeadLibrarian().getFirstName(), "openingHour.headLibrarian.firstName mismatch");
        assertEquals(lastName, openingHour.getHeadLibrarian().getLastName(), "openingHour.headLibrarian.lastName mismatch");
        assertEquals(online, openingHour.getHeadLibrarian().getOnlineAccount(), "openingHour.headLibrarian.onlineAccount mismatch");
        assertEquals(password, openingHour.getHeadLibrarian().getPassword(), "openingHour.headLibrarian.password mismatch");
        assertEquals(address, openingHour.getHeadLibrarian().getAddress(), "openingHour.headLibrarian.address mismatch");

    }


    @Test @SuppressWarnings("deprecation")
    public void testPersistAndLoadOpeningHoursByReference() {
        LibrarySystem library = new LibrarySystem();
        librarySystemRepository.save(library);

        //create inputs for Opening hours constructor
        DayOfWeek dayOfWeek = DayOfWeek.Saturday;
        Time startTime =  new Time(12, 43, 0);
        Time endTime = new Time(13, 55, 3);

        //create inputs for head librarian constructor
        String firstName = "Lorri";
        String lastName = "Kent";
        boolean online = false;
        String password = "zxcvbnm";
        int balance = 0;
        String address = "10 Road, Toronto, Canada";

        //create head librarian
        HeadLibrarian headLibrarian = new HeadLibrarian(firstName, lastName, online, library, address, password, balance);
        
        headLibrarianRepository.save(headLibrarian);
        librarianRepository.save(headLibrarian);
        userAccountRepository.save(headLibrarian);

        //create opening hour
        OpeningHour openingHour = new OpeningHour(dayOfWeek, startTime, endTime, library, headLibrarian);

        //save openingHourID to DB
        openingHourRepository.save(openingHour);

        //clear openingHour
        openingHour = null;

        //get openingHour from DB
        openingHour = openingHourRepository.findByHeadLibrarian(headLibrarian).get(0);

        //test functionality
        assertNotNull(openingHour, "No openinghour retrieved");
        assertEquals(dayOfWeek, openingHour.getDayOfWeek(), "openingHour.dayOfWeek mismatch");
        assertEquals(startTime, openingHour.getStartTime(), "openingHour.startTime mismatch");
        assertEquals(endTime, openingHour.getEndTime(), "openingHour.endTime mismatch");
        assertEquals(library.getSystemId(), openingHour.getLibrarySystem().getSystemId(), "openingHour.librarySystem.SystemID mismatch");
        
        //test persistence of head librarian within opening hour
        assertNotNull(openingHour.getHeadLibrarian(), "No head librarian retrieved");
        assertEquals(firstName, openingHour.getHeadLibrarian().getFirstName(), "openingHour.headLibrarian.firstName mismatch");
        assertEquals(lastName, openingHour.getHeadLibrarian().getLastName(), "openingHour.headLibrarian.lastName mismatch");
        assertEquals(online, openingHour.getHeadLibrarian().getOnlineAccount(), "openingHour.headLibrarian.onlineAccount mismatch");
        assertEquals(password, openingHour.getHeadLibrarian().getPassword(), "openingHour.headLibrarian.password mismatch");
        assertEquals(address, openingHour.getHeadLibrarian().getAddress(), "openingHour.headLibrarian.address.address mismatch");
    }


    @Test @SuppressWarnings("deprecation")
    public void testPersistAndLoadTransaction() {
        LibrarySystem library = new LibrarySystem();
        librarySystemRepository.save(library);

        //create inputs for transaction
        Date deadline =  new Date(2021, 5, 10);

        //create input for Patron (user)
        String firstName = "Matty";
        String lastName = "Pattaty";
        boolean online = true;
        String address = "1000 Rue Sherbrooke O, Montreal, Canada";
        boolean validated = true;
        String password = "thisisapassword";
        int balance = 0;

        //create patron
        Patron patron = new Patron(firstName, lastName, online, library, address, validated, password, balance);
        
        patronRepository.save(patron);
        userAccountRepository.save(patron);

        //create inputs for LibraryItem
        boolean viewable = true;
        Date publishingDate = new Date(1590, 10, 20);
        ItemType itemType = ItemType.Book;
        String creator = "Shakespeare";
        String name = "Hamlet";

        //create library item
        LibraryItem libraryItem = new LibraryItem(name, library, itemType, publishingDate, creator, viewable);

        //create inputs for BorrowableItem
        ItemState state = ItemState.Available;

        //create item
        BorrowableItem item = new BorrowableItem(state, libraryItem);
        borrowableItemRepository.save(item);

        //create transaction
        Transaction transaction = new Transaction(item, patron, deadline);

        //get IDs to find in DB
        int transactionID = transaction.getTransactionID();

        //save in DB
        transactionRepository.save(transaction);

        //clear all instances
        transaction = null;

        //get transaction from DB
        transaction = transactionRepository.findTransactionByTransactionID(transactionID);

        //test functionality
        assertNotNull(transaction, "No transaction retrieved");
        assertEquals(deadline, transaction.getDeadline(), "transaction.deadline mismatch");

        //test patron within transaction
        assertEquals(firstName, transaction.getUserAccount().getFirstName(), "transaction.userAccount.firstName mismatch");
        assertEquals(lastName, transaction.getUserAccount().getLastName(), "transaction.userAccount.lastName mismatch");
        assertEquals(online, transaction.getUserAccount().getOnlineAccount(), "transaction.userAccount.onlineAccount mismatch");
        assertEquals(validated, ((Patron)transaction.getUserAccount()).getValidatedAccount(), "transaction.userAccount.validateAccount mismatch");
        assertEquals(password, transaction.getUserAccount().getPassword(), "transaction.userAccount.password mismatch");
        assertEquals(address, transaction.getUserAccount().getAddress(), "transaction.userAccount.address mismatch");
        assertEquals(balance, transaction.getUserAccount().getBalance(), "transaction.userAccount.balance mismatch");
        assertEquals(library.getSystemId(), transaction.getUserAccount().getLibrarySystem().getSystemId(), "transaction.userAccount.librarySystem.systemID mismatch");

        //test borrowable item within transaction
        assertEquals(state, transaction.getBorrowableItem().getState(), "transaction.borrowableItem.state mismatch");

        //test library item within transaction
        assertEquals(viewable, transaction.getBorrowableItem().getLibraryItem().getIsViewable(), "transaction.borrowableItem.libraryItem.isViewable mismatch");
        assertEquals(publishingDate, transaction.getBorrowableItem().getLibraryItem().getDate(), "transaction.borrowableItem.libraryItem.date mismatch");
        assertEquals(name, transaction.getBorrowableItem().getLibraryItem().getName(), "transaction.borrowableItem.libraryItem.state mismatch");
        assertEquals(creator, transaction.getBorrowableItem().getLibraryItem().getCreator(), "transaction.borrowableitem.libraryItem.creator mismatch"); 
        assertEquals(library.getSystemId(), transaction.getBorrowableItem().getLibraryItem().getLibrarySystem(), "transaction.borrowableitem.libraryItem.librarySystem.systemID mismatch");
    }


    @Test @SuppressWarnings("deprecation")
    public void testPersistAndLoadTransactionByReferenceBorrowableitem() {
        LibrarySystem library = new LibrarySystem();
        librarySystemRepository.save(library);

        //create inputs for transaction
        Date deadline =  new Date(2021, 5, 10);

        //create input for Patron (user)
        String firstName = "Matty";
        String lastName = "Pattaty";
        boolean online = true;
        boolean validated = true;
        String password = "thisisapassword";
        int balance = 0;
        String address = "50 Rue Prince Arthur, Montreal, Canada";

        //create patron
        Patron patron = new Patron(firstName, lastName, online, library, address, validated, password, balance);

        patronRepository.save(patron);
        userAccountRepository.save(patron);

        //create inputs for LibraryItem
        boolean viewable = true;
        Date publishingDate = new Date(1590, 10, 20);
        ItemType itemType = ItemType.Book;
        String creator = "Shakespeare";
        String name = "Hamlet";

        //create library item
        LibraryItem libraryItem = new LibraryItem(name, library, itemType, publishingDate, creator, viewable);

        //create inputs for BorrowableItem
        ItemState state = ItemState.Available;

        //create item
        BorrowableItem item = new BorrowableItem(state, libraryItem);
        borrowableItemRepository.save(item);

        //create transaction
        Transaction transaction = new Transaction(item, patron, deadline);

        //save in DB
        transactionRepository.save(transaction);

        //clear all instances
        transaction = null;

        //get transaction from DB
        transaction = transactionRepository.findByBorrowableItem(item).get(0);

        //test functionality
        assertNotNull(transaction, "No transaction retrieved");
        assertEquals(deadline, transaction.getDeadline(), "transaction.deadline mismatch");

        //test patron within transaction
        assertEquals(firstName, transaction.getUserAccount().getFirstName(), "transaction.userAccount.firstName mismatch");
        assertEquals(lastName, transaction.getUserAccount().getLastName(), "transaction.userAccount.lastName mismatch");
        assertEquals(online, transaction.getUserAccount().getOnlineAccount(), "transaction.userAccount.onlineAccount mismatch");
        assertEquals(validated, ((Patron)transaction.getUserAccount()).getValidatedAccount(), "transaction.userAccount.validateAccount mismatch");
        assertEquals(password, transaction.getUserAccount().getPassword(), "transaction.userAccount.password mismatch");
        assertEquals(address, transaction.getUserAccount().getAddress(), "transaction.userAccount.address mismatch");
        assertEquals(balance, transaction.getUserAccount().getBalance(), "transaction.userAccount.balance mismatch");
        assertEquals(library.getSystemId(), transaction.getUserAccount().getLibrarySystem().getSystemId(), "transaction.userAccount.librarySystem.systemID mismatch");

        //test borrowable item within transaction
        assertEquals(state, transaction.getBorrowableItem().getState(), "transaction.borrowableItem.state mismatch");

        //test library item within transaction
        assertEquals(viewable, transaction.getBorrowableItem().getLibraryItem().getIsViewable(), "transaction.borrowableItem.libraryItem.isViewable mismatch");
        assertEquals(publishingDate, transaction.getBorrowableItem().getLibraryItem().getDate(), "transaction.borrowableItem.libraryItem.date mismatch");
        assertEquals(name, transaction.getBorrowableItem().getLibraryItem().getName(), "transaction.borrowableItem.libraryItem.state mismatch");
        assertEquals(creator, transaction.getBorrowableItem().getLibraryItem().getCreator(), "transaction.borrowableitem.libraryItem.creator mismatch"); 
        assertEquals(library.getSystemId(), transaction.getBorrowableItem().getLibraryItem().getLibrarySystem(), "transaction.borrowableitem.libraryItem.librarySystem.systemID mismatch");
    }


    @Test
    public void testPersistAndLoadTransactionByReferenceUserAccount() {
        LibrarySystem library = new LibrarySystem();
        librarySystemRepository.save(library);

        //create inputs for transaction
        Date deadline =  new Date(2021, 12, 5);

        //create input for Patron (user)
        String firstName = "Amanda";
        String lastName = "Rose";
        boolean online = true;
        boolean validated = true;
        String password = "heythisismypassword";
        int balance = 100;
        String address = "88 Av du Parc, Montreal, Canada";

        //create patron
        Patron patron = new Patron(firstName, lastName, online, library, address, validated, password, balance);

        patronRepository.save(patron);
        userAccountRepository.save(patron);

        //create inputs for LibraryItem
        boolean viewable = true;
        Date releaseDate = new Date(2005, 10, 20);
        ItemType itemType = ItemType.Movie;
        String creator = "James Cameron";
        String name = "Titanic";

        //create library item
        LibraryItem libraryItem = new LibraryItem(name, library, itemType, releaseDate, creator, viewable);

        //create inputs for BorrowableItem
        ItemState state = ItemState.Available;

        //create item
        BorrowableItem item = new BorrowableItem(state, libraryItem);
        borrowableItemRepository.save(item);

        //create transaction
        Transaction transaction = new Transaction(item, patron, deadline);

        //save in DB
        transactionRepository.save(transaction);

        //clear all instances
        transaction = null;

        //get transaction from DB
        transaction = transactionRepository.findByUserAccount(patron).get(0);

        //test functionality
        assertNotNull(transaction, "No transaction retrieved");
        assertEquals(deadline, transaction.getDeadline(), "transaction.deadline mismatch");

        //test patron within transaction
        assertEquals(firstName, transaction.getUserAccount().getFirstName(), "transaction.userAccount.firstName mismatch");
        assertEquals(lastName, transaction.getUserAccount().getLastName(), "transaction.userAccount.lastName mismatch");
        assertEquals(online, transaction.getUserAccount().getOnlineAccount(), "transaction.userAccount.onlineAccount mismatch");
        assertEquals(validated, ((Patron)transaction.getUserAccount()).getValidatedAccount(), "transaction.userAccount.validateAccount mismatch");
        assertEquals(password, transaction.getUserAccount().getPassword(), "transaction.userAccount.password mismatch");
        assertEquals(address, transaction.getUserAccount().getAddress(), "transaction.userAccount.address mismatch");
        assertEquals(balance, transaction.getUserAccount().getBalance(), "transaction.userAccount.balance mismatch");
        assertEquals(library.getSystemId(), transaction.getUserAccount().getLibrarySystem().getSystemId(), "transaction.userAccount.librarySystem.systemID mismatch");

        //test borrowable item within transaction
        assertEquals(state, transaction.getBorrowableItem().getState(), "transaction.borrowableItem.state mismatch");

        //test library item within transaction
        assertEquals(viewable, transaction.getBorrowableItem().getLibraryItem().getIsViewable(), "transaction.borrowableItem.libraryItem.isViewable mismatch");
        assertEquals(releaseDate, transaction.getBorrowableItem().getLibraryItem().getDate(), "transaction.borrowableItem.libraryItem.date mismatch");
        assertEquals(name, transaction.getBorrowableItem().getLibraryItem().getName(), "transaction.borrowableItem.libraryItem.state mismatch");
        assertEquals(creator, transaction.getBorrowableItem().getLibraryItem().getCreator(), "transaction.borrowableitem.libraryItem.creator mismatch"); 
        assertEquals(library.getSystemId(), transaction.getBorrowableItem().getLibraryItem().getLibrarySystem(), "transaction.borrowableitem.libraryItem.librarySystem.systemID mismatch");
    }


    @Test
    public void testPersistAndLoadBorrowableItem() { 
        LibrarySystem library = new LibrarySystem();
        librarySystemRepository.save(library);

        //create inputs for BorrowableItem
        ItemState state = ItemState.Available;

        //create inputs for LibraryItem
        boolean viewable = true;
        Date releaseDate = new Date(2019, 5, 6);
        ItemType itemType = ItemType.Music;
        String creator = "Maluma";
        String name = "11:11";

        //create library item
        LibraryItem libraryItem = new LibraryItem(name, library, itemType, releaseDate, creator, viewable);

        //create borrowable item
        BorrowableItem borrowableItem = new BorrowableItem(state, libraryItem);

        //get bar code number to find
        int barCodeNumber = borrowableItem.getBarCodeNumber();

        borrowableItemRepository.save(borrowableItem);

        //clear borrowable item
        borrowableItem = null;

        //retrieve borrowable item from DB
        borrowableItem = borrowableItemRepository.findBorrowableItemByBarCodeNumber(barCodeNumber);

        //test functionality
        assertNotNull(borrowableItem, "No borrowableItem retrieved");
        assertEquals(state, borrowableItem.getState(), "borrowableItem.state mismatch");

        //test library item within borrowable item
        assertEquals(viewable, borrowableItem.getLibraryItem().getIsViewable(), "borrowableItem.libraryItem.isViewable mismatch");
        assertEquals(releaseDate, borrowableItem.getLibraryItem().getDate(), "borrowableItem.libraryItem.date mismatch");
        assertEquals(itemType, borrowableItem.getLibraryItem().getType(), "borrowableItem.libraryItem.type mismatch");
        assertEquals(creator, borrowableItem.getLibraryItem().getCreator(), "borrowableItem.libraryItem.creator mismatch");
        assertEquals(name, borrowableItem.getLibraryItem().getName(), "borrowableItem.libraryItem.name mismatch");
    }


    @Test
    public void testPersistAndLoadBorrowableItemByRefLibraryItem() { 
        LibrarySystem library = new LibrarySystem();
        librarySystemRepository.save(library);

        //inputs for borrowableItem
        ItemState state = ItemState.Available;

        //inputs for libraryItem
        boolean viewable = true;
        Date releaseDate = new Date(2017, 10, 20);
        ItemType itemType = ItemType.Music;
        String creator = "Trey Songz";
        String name = "Tremaine the Album";

        //create libary item and persist
        LibraryItem libraryItem = new LibraryItem(name, library, itemType, releaseDate, creator, viewable);

        libraryItemRepository.save(libraryItem);

        //create borrowable item and persist
        BorrowableItem borrowableItem = new BorrowableItem(state, libraryItem);

        borrowableItemRepository.save(borrowableItem);

        //clear borrowable item
        borrowableItem = null;

        //retrieve borrowable item by library item from DB
        borrowableItem = borrowableItemRepository.findByLibraryItem(libraryItem).get(0);

        //test functionality
        assertNotNull(borrowableItem, "No borrowableItem retrieved");
        assertEquals(state, borrowableItem.getState(), "borrowableItem.state mismatch");

        //test library item
        assertEquals(viewable, borrowableItem.getLibraryItem().getIsViewable(), "borrowableItem.libraryItem.isViewable mismatch");
        assertEquals(releaseDate, borrowableItem.getLibraryItem().getDate(), "borrowableItem.libraryItem.date mismatch");
        assertEquals(name, borrowableItem.getLibraryItem().getName(), "borrowableItem.libraryItem.state mismatch");
        assertEquals(creator, borrowableItem.getLibraryItem().getCreator(), "borrowableitem.libraryItem.creator mismatch"); 
        assertEquals(library.getSystemId(), borrowableItem.getLibraryItem().getLibrarySystem(), "borrowableitem.libraryItem.librarySystem.systemID mismatch");
    }


    @Test
    public void testPersistAndLoadLibraryItem() { 
        LibrarySystem library = new LibrarySystem();
        librarySystemRepository.save(library);

        //inputs for libraryItem
        boolean viewable = true;
        Date releaseDate = new Date(2021, 8, 20);
        ItemType itemType = ItemType.NewspaperArticle;
        String creator = "New York Time";
        String name = "September 20, 2021";

        //create library item and persist
        LibraryItem libraryItem = new LibraryItem(name, library, itemType, releaseDate, creator, viewable);

        libraryItemRepository.save(libraryItem);

        //get library item isbn
        int isbn = libraryItem.getIsbn();

        //clear library item
        libraryItem = null;

        //retrieve library item from DB
        libraryItem = libraryItemRepository.findByIsbn(isbn);

        //test functionality
        assertNotNull(libraryItem, "No libraryItem retrieved");
        assertEquals(viewable, libraryItem.getIsViewable(), "libraryItem.isViewable mismatch");
        assertEquals(releaseDate, libraryItem.getDate(), "libraryItem.date mismatch");
        assertEquals(itemType, libraryItem.getType(), "libraryItem.type mismatch");
        assertEquals(creator, libraryItem.getCreator(), "libraryItem.creator mismatch");
        assertEquals(name, libraryItem.getName(), "libraryItem.name mismatch");
        assertEquals(library.getSystemId(), libraryItem.getLibrarySystem().getSystemId(), "libraryItem.librarySystem.systemID mismatch");
    }


    @Test
    public void testPersistAndLoadLibrarySystem() {
        int idTest=777;
        
        LibrarySystem librarySystemTest = new LibrarySystem(); //save/load
    
        librarySystemTest.setSystemId(idTest); //attribute save/load
        librarySystemRepository.save(librarySystemTest);
        
        librarySystemTest=null; //set object null to check references

        librarySystemTest=librarySystemRepository.findBySystemId(idTest);
        assertNotNull(librarySystemTest, "Returned null, object was not saved in persistance layer"); //write validation
        assertEquals(idTest, librarySystemTest.getSystemId(), "Value of system ID returned by db not equal to" +idTest ); //read validation from db
    }

    @Test
    public void testPersistAndLoadHolidayByRefLibrarySystem() { 
        LibrarySystem library = new LibrarySystem();
        librarySystemRepository.save(library);

        //inputs for holiday
        Date date = new Date(2020, 12, 25);
        Time startTime =  new Time(7, 30, 0);
        Time endTime = new Time(21, 30, 0);

        //inputs headLibrarian
        String firstName = "Samantha";
        String lastName = "Jules";
        boolean online = true;
        String password = "a1b2c3d4";
        int balance = 0;
        String address = "10203 5th Av, New York, New York, USA";

        //create head librarian and persist
        HeadLibrarian headLibrarian = new HeadLibrarian(firstName, lastName, online, library, address, password, balance);
        
        headLibrarianRepository.save(headLibrarian);
        librarianRepository.save(headLibrarian);
        userAccountRepository.save(headLibrarian);

        //create holiday and persist
        Holiday holiday = new Holiday(date, startTime, endTime, library, headLibrarian);

        holidayRepository.save(holiday);

        //clear holiday
        holiday = null;

        //retrieve holiday by library system from DB
        holiday = holidayRepository.findByLibrarySystem(library).get(0);

        //test functionality
        assertNotNull(holiday, "No holiday retrieved");
        assertEquals(date, holiday.getDate(), "holiday.date mismatch");
        assertEquals(startTime, holiday.getStartTime(), "holiday.startTime mismatch");
        assertEquals(endTime, holiday.getEndtime(), "holiday.endTime mismatch");
    }

    @Test
    public void testPersistAndLoadOpeningHourByRefLibrarySystem() { 
        LibrarySystem library = new LibrarySystem();
        librarySystemRepository.save(library);

        //inputs for openingHours
        DayOfWeek dayOfWeek = DayOfWeek.Monday;
        Time startTime =  new Time(7, 30, 0);
        Time endTime = new Time(21, 30, 0);

        //inputs headLibrarian
        String firstName = "Samantha";
        String lastName = "Jules";
        boolean online = true;
        String password = "a1b2c3d4";
        int balance = 0;
        String address = "10203 5th Av, New York, New York, USA";

        //create head librarian and persist
        HeadLibrarian headLibrarian = new HeadLibrarian(firstName, lastName, online, library, address, password, balance);
        
        headLibrarianRepository.save(headLibrarian);
        librarianRepository.save(headLibrarian);
        userAccountRepository.save(headLibrarian);

        //create opening hours and persist
        OpeningHour openingHour = new OpeningHour(dayOfWeek, startTime, endTime, library, headLibrarian);

        openingHourRepository.save(openingHour);

        //clear opening hours
        openingHour = null;

        //retrieve opening hours by library system from DB
        openingHour = openingHourRepository.findByLibrarySystem(library).get(0);

        //test functionality
        assertNotNull(openingHour, "No openinghour retrieved");
        assertEquals(dayOfWeek, openingHour.getDayOfWeek(), "openingHour.dayOfWeek mismatch");
        assertEquals(startTime, openingHour.getStartTime(), "openingHour.startTime mismatch");
        assertEquals(endTime, openingHour.getEndTime(), "openingHour.endTime mismatch");
        assertEquals(library.getSystemId(), openingHour.getLibrarySystem().getSystemId(), "openingHour.librarySystem.SystemID mismatch");
    }

    @Test
    public void testPersistAndLoadTimeSlotByRefLibrarySystem() {
        LibrarySystem library = new LibrarySystem();
        librarySystemRepository.save(library);

        //inputs for timeSlot
        Date startDate = new Date(2021, 1, 20);
        Time startTime =  new Time(10, 0, 0);
        Date endDate = new Date(2021, 1, 20);
        Time endTime = new Time(18, 0, 0);

        //inputs headLibrarian
        String firstName = "Samantha";
        String lastName = "Jules";
        boolean online = true;
        String password = "a1b2c3d4";
        int balance = 0;
        String address = "10203 5th Av, New York, New York, USA";

        //create head librarian and persist
        HeadLibrarian headLibrarian = new HeadLibrarian(firstName, lastName, online, library, address, password, balance);
        
        headLibrarianRepository.save(headLibrarian);
        librarianRepository.save(headLibrarian);
        userAccountRepository.save(headLibrarian);

        //create time slot and persist
        TimeSlot timeSlot = new TimeSlot(startDate, startTime, endDate, endTime, library, headLibrarian);

        timeSlotRepository.save(timeSlot);
        
        //clear time slot
        timeSlot = null;

        //retrieve time slot by library system from DB
        timeSlot = timeSlotRepository.findByLibrarySystem(library).get(0);

        //test functionality
        assertNotNull(timeSlot, "No timeslot retrieved");
        assertEquals(startDate, timeSlot.getStartDate(), "timeslot.startDate mismatch");
        assertEquals(startTime, timeSlot.getStartTime(), "timeslot.startTime mismatch");
        assertEquals(endDate, timeSlot.getEndDate(), "timeslot.endDate mismatch");
        assertEquals(endTime, timeSlot.getEndTime(), "timeslot.endTime mismatch");
    }

    @Test
    public void testPersistAndLoadLibraryItemByRefLibrarySystem() {
        LibrarySystem library = new LibrarySystem();
        librarySystemRepository.save(library);

        //inputs for libraryItem
        boolean viewable = true;
        Date releaseDate = new Date(2021, 8, 20);
        ItemType itemType = ItemType.Book;
        String creator = "Gillian Flynn";
        String name = "Gone Girl";

        //create library item and persist
        LibraryItem libraryItem = new LibraryItem(name, library, itemType, releaseDate, creator, viewable);

        libraryItemRepository.save(libraryItem);

        //clear library item
        libraryItem = null;

        //retrieve library item by library system from DB
        libraryItem = libraryItemRepository.findByLibrarySystem(library).get(0);

        //test functionality
        assertNotNull(libraryItem, "No libraryItem retrieved");
        assertEquals(viewable, libraryItem.getIsViewable(), "libraryItem.isViewable mismatch");
        assertEquals(releaseDate, libraryItem.getDate(), "libraryItem.date mismatch");
        assertEquals(itemType, libraryItem.getType(), "libraryItem.type mismatch");
        assertEquals(creator, libraryItem.getCreator(), "libraryItem.creator mismatch");
        assertEquals(name, libraryItem.getName(), "libraryItem.name mismatch");
        assertEquals(library.getSystemId(), libraryItem.getLibrarySystem().getSystemId(), "libraryItem.librarySystem.systemID mismatch");
    }

    @Test
    public void testPersistAndLoadUserAccountByRefLibrarySystem() {
        LibrarySystem library = new LibrarySystem();
        librarySystemRepository.save(library);

        //inputs for patron
        String firstName = "Jane";
        String lastName = "Doe";
        boolean online = true;
        boolean validated = true;
        String password = "thisIsJane";
        int balance = 25;
        String address = "4000 McGill, Montreal, Canada";

        //create patron and persist
        Patron patron = new Patron(firstName, lastName, online, library, address, validated, password, balance);
        
        patronRepository.save(patron);
        userAccountRepository.save(patron); 

        //clear patron
        patron = null;

        //retrieve patron by library system from DB
        patron = (Patron) userAccountRepository.findByLibrarySystem(library).get(0);

        //test functionality
        assertNotNull(patron, "No Patron retrieved");
        assertEquals(firstName, patron.getFirstName(), "patron.firstName mismatch");
        assertEquals(lastName, patron.getLastName(), "patron.lastName mismatch");
        assertEquals(online, patron.getOnlineAccount(), "patron.onlineAccount mismatch");
        assertEquals(validated, patron.getValidatedAccount(), "patron.validatedAccount mismatch");
        assertEquals(password, patron.getPassword(), "patron.password mismatch");
        assertEquals(address, patron.getAddress(), "patron.address mismatch");
    }
}

