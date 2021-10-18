package ca.mcgill.ecse321.libraryservice.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.sql.Date;
import java.sql.Time;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import ca.mcgill.ecse321.libraryservice.model.*;
import ca.mcgill.ecse321.libraryservice.model.BorrowableItem.ItemState;
import ca.mcgill.ecse321.libraryservice.model.OpeningHour.DayOfWeek;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class TestLibraryServicePersistence {
    
    //adding all CRUD interface instances
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private BookRepository bookRepository;
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
    private MovieRepository movieRepository;
    @Autowired
    private MusicRepository musicRepository;
    @Autowired
    private NewspaperArticleRepository newspaperArticleRepository;
    @Autowired
    private NewspaperRepository newspaperRepository;
    @Autowired
    private OpeningHourRepository openingHourRepository;
    @Autowired
    private PatronRepository patronRepository;
    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private TimeSlotRepository timeSlotRepository;
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private UserAccountRepository userAccountRepository;

    private LibrarySystem library;

    @AfterEach
    public void clearDatabase() {
        //delete all instances from bottom to top of model
        newspaperRepository.deleteAll();
        newspaperArticleRepository.deleteAll();
        holidayRepository.deleteAll();
        openingHourRepository.deleteAll();
        timeSlotRepository.deleteAll();
        headLibrarianRepository.deleteAll();
        librarianRepository.deleteAll();
        patronRepository.deleteAll();
        borrowableItemRepository.deleteAll();
        roomRepository.deleteAll();
        musicRepository.deleteAll();
        movieRepository.deleteAll();
        bookRepository.deleteAll();
        librarianRepository.deleteAll();
        libraryItemRepository.deleteAll();
        transactionRepository.deleteAll();
        addressRepository.deleteAll();
        userAccountRepository.deleteAll();
        librarySystemRepository.deleteAll();

        library.delete();
    }

    @Test
    public void testPersistAndLoadPatron() {
        library = new LibrarySystem();
        librarySystemRepository.save(library);
        
        //create inputs for patron constructor
        String firstName = "John";
        String lastName = "Doe";
        boolean online = true;
        boolean validated = false;
        String password = "123456789";
        int balance = 0;

        //create address for patron constructor
        String streetAndNumber = "4000 McGill";
        String city = "Montreal";
        String country = "Canada";
        Address address = new Address(streetAndNumber, city, country);
        addressRepository.save(address);

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
    }

    @Test
    public void testPersistAndLoadPatronByReference() {
        library = new LibrarySystem();
        librarySystemRepository.save(library);

        //create inputs for patron constructor
        String firstName = "John";
        String lastName = "Doe";
        boolean online = true;
        boolean validated = false;
        String password = "123456789";
        int balance = 0;

        //create address for patron constructor
        String streetAndNumber = "4000 McGill";
        String city = "Montreal";
        String country = "Canada";
        Address address = new Address(streetAndNumber, city, country);
        addressRepository.save(address);

        //create patron
        Patron patron = new Patron(firstName, lastName, online, library, address, validated, password, balance);

        //save patron in DB
        patronRepository.save(patron);
        userAccountRepository.save(patron);

        //clear patron
        patron = null;

        //get patron from DB
        patron = patronRepository.findPatronByAddress(address);

        //test functionnality
        assertNotNull(patron, "No Patron retrieved");
        assertEquals(firstName, patron.getFirstName(), "patron.firstName mismatch");
        assertEquals(lastName, patron.getLastName(), "patron.lastName mismatch");
        assertEquals(online, patron.getOnlineAccount(), "patron.onlineAccount mismatch");
        assertEquals(validated, patron.getValidatedAccount());
        assertEquals(password, patron.getPassword());
    }

    @Test
    public void testPersistAndLoadLibrarian() {
        library = new LibrarySystem();
        librarySystemRepository.save(library);

        //create inputs for librarian constructor
        String firstName = "Jake";
        String lastName = "Morello";
        boolean online = false;
        String password = "qwertyuiop";
        int balance = 0;

        //create address for librarian constructor
        String streetAndNumber = "100 Durocher";
        String city = "Montreal";
        String country = "Canada";
        Address address = new Address(streetAndNumber, city, country);
        addressRepository.save(address);

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
        assertEquals(streetAndNumber, librarian.getAddress().getAddress(), "librarian.address.adress mismatch");
        assertEquals(city, librarian.getAddress().getCity(), "librarian.address.city mismatch");
        assertEquals(country, librarian.getAddress().getCountry(), "librarian.address.country mismatch");
    }

    @Test @SuppressWarnings("deprecation")
    public void testPersistAndLoadLibrarianByReference() {
        library = new LibrarySystem();
        librarySystemRepository.save(library);

        //create inputs for librarian constructor
        String firstName = "Jake";
        String lastName = "Morello";
        boolean online = false;
        String password = "qwertyuiop";
        int balance = 0;

        //create address for librarian constructor
        String streetAndNumber = "100 Durocher";
        String city = "Montreal";
        String country = "Canada";
        Address address = new Address(streetAndNumber, city, country);
        addressRepository.save(address);

        //create librarian
        Librarian librarian = new Librarian(firstName, lastName, online, library, address, password, balance);

        //create inputs for timeslot constructor
        Date startDate = new Date(2020, 12, 25);
        Time startTime =  new Time(12, 43, 0);
        Date endDate = new Date(2020, 12, 28);
        Time endTime = new Time(13, 55, 3);

        //create inputs for head librarian constructor
        String firstNameHeadLib = "Laura";
        String lastNameHeadLib = "Porto";
        boolean onlineHeadLib = true;
        String passwordHeadLib = "asdfghjkl";
        int balanceHeadLib = 0;

        //create address for head librarian constructor
        String streetAndNumberHeadLib = "500 Sherbrook";
        String cityHeadLib = "Montreal";
        String countryHeadLib = "Canada";
        Address addressHeadLib = new Address(streetAndNumberHeadLib, cityHeadLib, countryHeadLib);
        addressRepository.save(addressHeadLib);

        //create head librarian
        HeadLibrarian headLibrarian = new HeadLibrarian(firstNameHeadLib, lastNameHeadLib, onlineHeadLib, library, addressHeadLib, passwordHeadLib, balanceHeadLib);
        headLibrarianRepository.save(headLibrarian);

        //create timeslot
        TimeSlot timeSlot = new TimeSlot(startDate, startTime, endDate, endTime, library, headLibrarian);
        timeSlotRepository.save(timeSlot);

        //save librarian in DB
        librarianRepository.save(librarian);
        userAccountRepository.save(librarian);


        //clear librarian
        librarian = null;

        //get librarian from DB
        librarian = librarianRepository.findByTimeSlot(timeSlot).get(0);

        //test functionnality
        assertNotNull(librarian, "No librarian retrieved");
        assertEquals(firstName, librarian.getFirstName(), "librarian.firstName mismatch");
        assertEquals(lastName, librarian.getLastName(), "librarian.lastName mismatch");
        assertEquals(online, librarian.getOnlineAccount(), "librarian.onlineAccount mismatch");
        assertEquals(password, librarian.getPassword(), "librarian.password mismatch");
        assertEquals(streetAndNumber, headLibrarian.getAddress().getAddress(), "librarian.address.adress mismatch");
        assertEquals(city, headLibrarian.getAddress().getCity(), "librarian.address.city mismatch");
        assertEquals(country, headLibrarian.getAddress().getCountry(), "librarian.address.country mismatch");
    }

    @Test
    public void testPersistAndLoadHeadLibrarian() {
        library = new LibrarySystem();
        librarySystemRepository.save(library);

        //create inputs for head librarian constructor
        String firstName = "Laura";
        String lastName = "Porto";
        boolean online = true;
        String password = "asdfghjkl";
        int balance = 0;

        //create address for head librarian constructor
        String streetAndNumber = "500 Sherbrook";
        String city = "Montreal";
        String country = "Canada";
        Address address = new Address(streetAndNumber, city, country);
        addressRepository.save(address);

        //create head librarian
        HeadLibrarian headLibrarian = new HeadLibrarian(firstName, lastName, online, library, address, password, balance);

        //get headLibrarianID to retrieve head librarian from DB
        int headLibrarianID = headLibrarian.getUserID();

        //save head librarian in DB
        headLibrarianRepository.save(headLibrarian);

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
        assertEquals(streetAndNumber, headLibrarian.getAddress().getAddress(), "headLibrarian.address.adress mismatch");
        assertEquals(city, headLibrarian.getAddress().getCity(), "headLibrarian.address.city mismatch");
        assertEquals(country, headLibrarian.getAddress().getCountry(), "headLibrarian.address.country mismatch");
    }

    /* No findHeadLibrarianByReference */

    @Test @SuppressWarnings("deprecation")
    public void testPersistAndLoadTimeSlot() {
        library = new LibrarySystem();
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

        //create address for head librarian constructor
        String streetAndNumber = "10 Road";
        String city = "Toronto";
        String country = "Canada";
        Address address = new Address(streetAndNumber, city, country);
        addressRepository.save(address);

        //create head librarian
        HeadLibrarian headLibrarian = new HeadLibrarian(firstName, lastName, online, library, address, password, balance);
        headLibrarianRepository.save(headLibrarian);

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
        assertEquals(streetAndNumber, timeSlot.getHeadLibrarian().getAddress().getAddress(), "timeslot.headLibrarian.address.address mismatch");
        assertEquals(city, timeSlot.getHeadLibrarian().getAddress().getCity(), "timeslot.headLibrarian.address.city mismatch");
        assertEquals(country, timeSlot.getHeadLibrarian().getAddress().getCountry(), "timeslot.headLibrarian.address.country mismatch");

    }

    @Test @SuppressWarnings("deprecation")
    public void testPersistAndLoadTimeSlotByReference() {
        library = new LibrarySystem();
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

        //create address for head librarian constructor
        String streetAndNumber = "10 Road";
        String city = "Toronto";
        String country = "Canada";
        Address address = new Address(streetAndNumber, city, country);
        addressRepository.save(address);

        //create head librarian
        HeadLibrarian headLibrarian = new HeadLibrarian(firstName, lastName, online, library, address, password, balance);
        headLibrarianRepository.save(headLibrarian);

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
        assertEquals(streetAndNumber, timeSlot.getHeadLibrarian().getAddress().getAddress(), "timeslot.headLibrarian.address.address mismatch");
        assertEquals(city, timeSlot.getHeadLibrarian().getAddress().getCity(), "timeslot.headLibrarian.address.city mismatch");
        assertEquals(country, timeSlot.getHeadLibrarian().getAddress().getCountry(), "timeslot.headLibrarian.address.country mismatch");

    }

    @Test @SuppressWarnings("deprecation")
    public void testPersistAndLoadHoliday() {
        library = new LibrarySystem();
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

        //create address for head librarian constructor
        String streetAndNumber = "10 Road";
        String city = "Toronto";
        String country = "Canada";
        Address address = new Address(streetAndNumber, city, country);

        //create head librarian
        HeadLibrarian headLibrarian = new HeadLibrarian(firstName, lastName, online, library, address, password, balance);

        //create holiday
        Holiday holiday = new Holiday(date, startTime, endTime, library, headLibrarian);

        //get holidayID to retreive from DB
        int holidayID = holiday.getHolidayID();

        //save holiday to DB
        addressRepository.save(address);
        headLibrarianRepository.save(headLibrarian);
        holidayRepository.save(holiday);

        //clear timeslot
        holiday = null;

        //get holiday from DB
        holiday = holidayRepository.findHolidayByHolidayID(holidayID);

        //test functionality
        assertNotNull(holiday);
        assertEquals(date, holiday.getDate());
        assertEquals(startTime, holiday.getStartTime());
        assertEquals(endTime, holiday.getEndtime());
        assertEquals(library, holiday.getLibrarySystem());
        
        //test persistence of head librarian within holiday
        assertNotNull(headLibrarian);
        assertEquals(firstName, holiday.getHeadLibrarian().getFirstName());
        assertEquals(lastName, holiday.getHeadLibrarian().getLastName());
        assertEquals(online, holiday.getHeadLibrarian().getOnlineAccount());
        assertEquals(password, holiday.getHeadLibrarian().getPassword());
        assertEquals(streetAndNumber, holiday.getHeadLibrarian().getAddress().getAddress());
        assertEquals(city, holiday.getHeadLibrarian().getAddress().getCity());
        assertEquals(country, holiday.getHeadLibrarian().getAddress().getCountry());

    }

    @Test @SuppressWarnings("deprecation")
    public void testPersistAndLoadOpeningHours() {
        library = new LibrarySystem();
        //librarySystemRepository.save(library);

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

        //create address for head librarian constructor
        String streetAndNumber = "10 Road";
        String city = "Toronto";
        String country = "Canada";
        Address address = new Address(streetAndNumber, city, country);

        //create head librarian
        HeadLibrarian headLibrarian = new HeadLibrarian(firstName, lastName, online, library, address, password, balance);

        //create opening hour
        OpeningHour openingHour = new OpeningHour(dayOfWeek, startTime, endTime, library, headLibrarian);

        //get openingHourID to retreive from DB
        int openingHourID = openingHour.getHourID();

        //save openingHourID to DB
        addressRepository.save(address);
        headLibrarianRepository.save(headLibrarian);
        openingHourRepository.save(openingHour);
        librarySystemRepository.save(library);

        //clear openingHour
        openingHour = null;

        //get openingHour from DB
        openingHour = openingHourRepository.findOpeningHourByHourID(openingHourID);

        //test functionality
        assertNotNull(openingHour);
        assertEquals(dayOfWeek, openingHour.getDayOfWeek());
        assertEquals(startTime, openingHour.getStartTime());
        assertEquals(endTime, openingHour.getEndTime());
        assertEquals(library, openingHour.getLibrarySystem());
        
        //test persistence of head librarian within holiday
        assertNotNull(headLibrarian);
        assertEquals(firstName, openingHour.getHeadLibrarian().getFirstName());
        assertEquals(lastName, openingHour.getHeadLibrarian().getLastName());
        assertEquals(online, openingHour.getHeadLibrarian().getOnlineAccount());
        assertEquals(password, openingHour.getHeadLibrarian().getPassword());
        assertEquals(streetAndNumber, openingHour.getHeadLibrarian().getAddress().getAddress());
        assertEquals(city, openingHour.getHeadLibrarian().getAddress().getCity());
        assertEquals(country, openingHour.getHeadLibrarian().getAddress().getCountry());

    }

    @Test
    public void testPersistAndLoadAddress() {
        library = new LibrarySystem();
        

        //create address inputs
        String streetAndNumber = "60 Street";
        String city = "Vancouver";
        String country = "Canada";

        //create address
        Address address = new Address(streetAndNumber, city, country);

        //get addressID to find in DB
        int addressID = address.getAddressID();

        //save address in DB
        addressRepository.save(address);

        //clear address
        address = null;

        //retrieve address from DB
        address = addressRepository.findAddressByAddressID(addressID);

        //test functionality
        assertNotNull(address);
        assertEquals(streetAndNumber, address.getAddress());
        assertEquals(city, address.getCity());
        assertEquals(country, address.getCountry());
    }

    @Test @SuppressWarnings("deprecation")
    public void testPersistAndLoadTransaction() {
        library = new LibrarySystem();
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

        //create address for patron constructor
        String streetAndNumber = "4330 Durocher";
        String city = "Montreal";
        String country = "Canada";
        Address address = new Address(streetAndNumber, city, country);

        //create patron
        Patron patron = new Patron(firstName, lastName, online, library, address, validated, password, balance);

        //create input for book
        String author = "Shakespeare";
        String name = "Othello";

        //create book
        Book book = new Book(name, library, author);

        //create inputs for item
        ItemState state = ItemState.Available;

        //create item
        BorrowableItem item = new BorrowableItem(state, book);

        //create transaction
        Transaction transaction = new Transaction(item, patron, deadline);

        //get IDs to find in DB
        int transactionID = transaction.getTransactionID();

        //save in DB
        addressRepository.save(address);
        patronRepository.save(patron);
        bookRepository.save(book);
        borrowableItemRepository.save(item);
        transactionRepository.save(transaction);
        transactionRepository.save(transaction);

        //clear all instances
        transaction = null;

        //get transaction from DB
        transaction = transactionRepository.findTransactionByTransactionID(transactionID);

        //test functionality
        assertNotNull(transaction);
        assertEquals(deadline, transaction.getDeadline());

        //test patron within transaction
        assertEquals(firstName, transaction.getUserAccount().getFirstName());
        assertEquals(lastName, transaction.getUserAccount().getLastName());
        assertEquals(online, transaction.getUserAccount().getOnlineAccount());
        assertEquals(validated, ((Patron)transaction.getUserAccount()).getValidatedAccount());
        assertEquals(password, transaction.getUserAccount().getPassword());
        assertEquals(streetAndNumber, transaction.getUserAccount().getAddress().getAddress());
        assertEquals(city, transaction.getUserAccount().getAddress().getCity());
        assertEquals(country, transaction.getUserAccount().getAddress().getCountry());
        assertEquals(library, transaction.getUserAccount().getLibrarySystem());

        //test borrowable item within transaction
        assertEquals(state, transaction.getBorrowableItem().getState());

        //test library item within transaction
        assertEquals(name, transaction.getBorrowableItem().getLibraryItem().getName());
        assertEquals(author, ((Book) transaction.getBorrowableItem().getLibraryItem()).getAuthor()); 
        assertEquals(library, transaction.getBorrowableItem().getLibraryItem());

    }
    @Test
    public void testPersistAndLoadLibrarySystem() {

        int idTest=777;
        
        LibrarySystem librarySystemTest= new LibrarySystem(); //save/load
    
        librarySystemTest.setSystemId(idTest); //attribute save/load
        librarySystemRepository.save(librarySystemTest);
        
        librarySystemTest=null; //set object null to check references

        librarySystemTest=librarySystemRepository.findBySystemId(idTest);
        assertNotNull(librarySystemTest, "Returned null, object was not saved in persistance layer"); //write validation
        assertEquals(idTest, librarySystemTest.getSystemId(), "Value of system ID returned by db not equal to" +idTest ); //read validation from db





    }

    @Test
    public void testPersistAndLoadBookAndLibraryItemAndBorowableItem() { //boorrowableitem //book //library item
       //new library instance
        LibrarySystem lst = new LibrarySystem();

       

        //initiate varaibles for constructors
        String name= "maya";
        String author= "hamid";
       ItemState stateTest= ItemState.Available;
    

        
        Book bookTest= new Book(name, lst, author); // object +attributes
        int isbnTest= bookTest.getIsbn();

        //add borowable item
       BorrowableItem borroableItemTest=  new BorrowableItem(stateTest, bookTest); 
        
       borrowableItemRepository.save(borroableItemTest);
        bookRepository.save(bookTest);

        libraryItemRepository.save(bookTest);

        


    
        
        
       
       

        bookTest=null;


        bookTest=bookRepository.findByIsbn(isbnTest);
        assertNotNull(bookTest, "Returned null, object was not saved in persistance layer"); //write validation
        assertEquals(author, bookTest.getAuthor(), "Value of system ID returned by db not equal to" +author ); //read validation from db


    }

    @Test
    public void testPersistAndLoadMovieAndLibraryItem() { //movie
        String name= "maya";
        String director= "hamid";
        LibrarySystem lst = new LibrarySystem();

        
        Movie movieTest= new Movie(name, lst, director); // object +attributes
        int isbnTest= movieTest.getIsbn();

        movieRepository.save(movieTest);
        libraryItemRepository.save(movieTest);
        movieTest=null;

        movieTest=movieRepository.findByIsbn(isbnTest);
        assertNotNull(movieTest, "Returned null, object was not saved in persistance layer"); //write validation
        assertEquals(director, movieTest.getDirector(), "Value of system ID returned by db not equal to" +director ); //read validation from db

        //testing for abstract methods
       //having this as repo makes things awk
   
        assertNotNull(libraryItemRepository.findByLibrarySystem(library), "Returned null, object was not saved in persistance layer"); //write validation

        




    }




    @Test
    public void testPersistAndLoadMusicAndLibraryItem() { //music
        String name= "maya";
        String rapper= "hamid";

        Music musicTest= new Music(name, library, rapper); // object +attributes
        int isbnTest= musicTest.getIsbn();

        musicRepository.save(musicTest);
        libraryItemRepository.save(musicTest);
        musicTest=null;

        musicTest=musicRepository.findByIsbn(isbnTest);
        assertNotNull(musicTest, "Returned null, object was not saved in persistance layer"); //write validation
        assertEquals(rapper, musicTest.getArtist(), "Value of system ID returned by db not equal to" +rapper ); //read validation from db

        //testing for abstract methods
       //having this as repo makes things awk
   
        assertNotNull(libraryItemRepository.findByLibrarySystem(library), "Returned null, object was not saved in persistance layer"); //write validation



    }


    @Test 
    public void testPerisistAndLoadNewspaperArticleItem() {
        



    }

    


}

