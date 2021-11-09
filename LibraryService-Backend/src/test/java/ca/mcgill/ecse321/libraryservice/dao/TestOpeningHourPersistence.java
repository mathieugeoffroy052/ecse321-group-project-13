package ca.mcgill.ecse321.libraryservice.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.sql.Time;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import ca.mcgill.ecse321.libraryservice.model.HeadLibrarian;
import ca.mcgill.ecse321.libraryservice.model.OpeningHour;
import ca.mcgill.ecse321.libraryservice.model.OpeningHour.DayOfWeek;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class TestOpeningHourPersistence {
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
    }

    @Test @SuppressWarnings("deprecation")
    public void testPersistAndLoadOpeningHours() {

        //create inputs for Opening hours constructor
        DayOfWeek dayOfWeek = DayOfWeek.Saturday;
        Time startTime =  new Time(12, 43, 0);
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
        HeadLibrarian headLibrarian = new HeadLibrarian(firstName, lastName, online, address, password, balance, email);
       
        headLibrarianRepository.save(headLibrarian);
        librarianRepository.save(headLibrarian);
        userAccountRepository.save(headLibrarian);

        //create opening hour
        OpeningHour openingHour = new OpeningHour(dayOfWeek, startTime, endTime, headLibrarian);
        

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
        
        //test persistence of head librarian within opening hour
        assertNotNull(openingHour.getHeadLibrarian(), "No head librarian retrieved");
        assertEquals(firstName, openingHour.getHeadLibrarian().getFirstName(), "openingHour.headLibrarian.firstName mismatch");
        assertEquals(lastName, openingHour.getHeadLibrarian().getLastName(), "openingHour.headLibrarian.lastName mismatch");
        assertEquals(online, openingHour.getHeadLibrarian().getOnlineAccount(), "openingHour.headLibrarian.onlineAccount mismatch");
        assertEquals(password, openingHour.getHeadLibrarian().getPassword(), "openingHour.headLibrarian.password mismatch");
        assertEquals(address, openingHour.getHeadLibrarian().getAddress(), "openingHour.headLibrarian.address mismatch");
        assertEquals(email, openingHour.getHeadLibrarian().getEmail(), "openingHour.headLibrarian.email mismatch");

    }


    @Test @SuppressWarnings("deprecation")
    public void testPersistAndLoadOpeningHoursByReference() {
        //create inputs for Opening hours constructor
        DayOfWeek dayOfWeek = DayOfWeek.Saturday;
        Time startTime =  new Time(12, 43, 0);
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
        HeadLibrarian headLibrarian = new HeadLibrarian(firstName, lastName, online, address, password, balance, email);
        
        headLibrarianRepository.save(headLibrarian);
        librarianRepository.save(headLibrarian);
        userAccountRepository.save(headLibrarian);

        //create opening hour
        OpeningHour openingHour = new OpeningHour(dayOfWeek, startTime, endTime, headLibrarian);

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
        
        //test persistence of head librarian within opening hour
        assertNotNull(openingHour.getHeadLibrarian(), "No head librarian retrieved");
        assertEquals(firstName, openingHour.getHeadLibrarian().getFirstName(), "openingHour.headLibrarian.firstName mismatch");
        assertEquals(lastName, openingHour.getHeadLibrarian().getLastName(), "openingHour.headLibrarian.lastName mismatch");
        assertEquals(online, openingHour.getHeadLibrarian().getOnlineAccount(), "openingHour.headLibrarian.onlineAccount mismatch");
        assertEquals(password, openingHour.getHeadLibrarian().getPassword(), "openingHour.headLibrarian.password mismatch");
        assertEquals(address, openingHour.getHeadLibrarian().getAddress(), "openingHour.headLibrarian.address.address mismatch");
        assertEquals(email, openingHour.getHeadLibrarian().getEmail(), "openingHour.headLibrarian.address.email mismatch");
    }
}
