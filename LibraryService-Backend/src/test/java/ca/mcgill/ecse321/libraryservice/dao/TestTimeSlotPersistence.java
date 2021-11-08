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

import ca.mcgill.ecse321.libraryservice.model.HeadLibrarian;
import ca.mcgill.ecse321.libraryservice.model.Librarian;
import ca.mcgill.ecse321.libraryservice.model.LibrarySystem;
import ca.mcgill.ecse321.libraryservice.model.TimeSlot;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class TestTimeSlotPersistence {
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
        String email = "Lorri@email.com";
        int balance = 0;
        String address = "10 Road, Toronto, Canada";

        //create head librarian
        HeadLibrarian headLibrarian = new HeadLibrarian(firstName, lastName, online, library, address, password, balance, email);
        
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
        assertEquals(email, timeSlot.getHeadLibrarian().getEmail(), "timeslot.headLibrarian.email mismatch");
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
        String email = "Lorri@email.com";
        int balance = 0;
        String address = "10 Road, Toronto, Canada";

        //create head librarian
        HeadLibrarian headLibrarian = new HeadLibrarian(firstName, lastName, online, library, address, password, balance, email);
        
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
        assertEquals(email, timeSlot.getHeadLibrarian().getEmail(), "timeslot.headLibrarian.email mismatch");

    }


    @Test @SuppressWarnings("deprecation")
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
        String hLibEmail = "Anna@email.com";
        int hLibBalance = 0;

        //create head libarian and persist
        HeadLibrarian headLibrarian = new HeadLibrarian(hLibFirstName, hLibLastName, hLibOnline, library, hLibAddress, hLibPassword, hLibBalance, hLibEmail);

        headLibrarianRepository.save(headLibrarian);
        librarianRepository.save(headLibrarian);
        userAccountRepository.save(headLibrarian);

        //create time slot and persist
        TimeSlot timeSlot = new TimeSlot(startDate, startTime, endDate, endTime, library, headLibrarian);

        //inputs for librarian
        String libFirstName = "Ben";
        String libLastName = "William";
        boolean libOnline = true;
        String libAddress = "1000 Rue Sherbrooke O, Montreal, Canada";
        String libPassword = "benspassword";
        String libEmail = "ben@email.com";
        int libBalance = 0;

        //create librarian, persist
        Librarian librarian = new Librarian(libFirstName, libLastName, libOnline, library, libAddress, libPassword, libBalance, libEmail);
        
        librarianRepository.save(librarian);
        userAccountRepository.save(librarian);
        

        //add librarian to time slot
        timeSlot.addLibrarian(librarian);
        
        timeSlotRepository.save(timeSlot);

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
        assertEquals(hLibEmail, timeSlot.getHeadLibrarian().getEmail(), "timeSlot.headLibrarian.email mismatch");
        assertEquals(library.getSystemId(), timeSlot.getHeadLibrarian().getLibrarySystem().getSystemId(), "timeSlot.headLibrarian.librarySystem.systemID mismatch");

        //test persistence of librarian
        Librarian libFromTimeSlot = (Librarian) timeSlot.getLibrarian().toArray()[0];
        assertEquals(libFirstName, libFromTimeSlot.getFirstName(), "timeSlot.librarian[0].firstName mismatch");
        assertEquals(libLastName, libFromTimeSlot.getLastName(), "timeSlot.librarian[0].lastName mismatch");
        assertEquals(libOnline, libFromTimeSlot.getOnlineAccount(), "timeSlot.librarian[0].onlineAccount mismatch");
        assertEquals(libPassword, libFromTimeSlot.getPassword(), "timeSlot.librarian[0].password mismatch");
        assertEquals(libAddress, libFromTimeSlot.getAddress(), "timeSlot.librarian[0].address mismatch");
        assertEquals(libBalance, libFromTimeSlot.getBalance(), "timeSlot.librarian[0].balance mismatch");
        assertEquals(libEmail, libFromTimeSlot.getEmail(), "timeSlot.librarian[0].email mismatch");
        assertEquals(library.getSystemId(), libFromTimeSlot.getLibrarySystem().getSystemId(), "timeSlot.librarian[0].librarySystem.systemID mismatch");
    }

    @Test @SuppressWarnings("deprecation")
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
        String email = "Samantha@email.com";
        int balance = 0;
        String address = "10203 5th Av, New York, New York, USA";

        //create head librarian and persist
        HeadLibrarian headLibrarian = new HeadLibrarian(firstName, lastName, online, library, address, password, balance, email);

        //create time slot and persist
        TimeSlot timeSlot = new TimeSlot(startDate, startTime, endDate, endTime, library, headLibrarian);

        headLibrarianRepository.save(headLibrarian);
        librarianRepository.save(headLibrarian);
        userAccountRepository.save(headLibrarian);
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
}
