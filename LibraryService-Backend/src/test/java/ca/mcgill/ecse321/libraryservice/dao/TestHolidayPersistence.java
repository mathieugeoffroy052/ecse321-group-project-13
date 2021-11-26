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
import ca.mcgill.ecse321.libraryservice.model.Holiday;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class TestHolidayPersistence {
    // adding all CRUD interface instances
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
        // delete all instances from bottom to top of model
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

    @Test
    @SuppressWarnings("deprecation")
    public void testPersistAndLoadHoliday() {

        // create inputs for holiday constructor
        Date date = new Date(2020, 12, 25);
        Time startTime = new Time(12, 43, 0);
        Time endTime = new Time(13, 55, 3);

        // create inputs for head librarian constructor
        String firstName = "Lorri";
        String lastName = "Kent";
        boolean online = false;
        String password = "zxcvbnm";
        String email = "Lorri@email.com";
        int balance = 0;
        String address = "10 Road, Toronto, Canada";

        // create head librarian
        HeadLibrarian headLibrarian = new HeadLibrarian(firstName, lastName, online, address, password, balance, email);

        headLibrarianRepository.save(headLibrarian);
        librarianRepository.save(headLibrarian);
        userAccountRepository.save(headLibrarian);

        // create holiday
        Holiday holiday = new Holiday(date, startTime, endTime, headLibrarian);

        // get holidayID to retreive from DB
        int holidayID = holiday.getHolidayID();

        // save holiday to DB
        holidayRepository.save(holiday);

        // clear timeslot
        holiday = null;

        // get holiday from DB
        holiday = holidayRepository.findHolidayByHolidayID(holidayID);

        // test functionality
        assertNotNull(holiday, "No holiday retrieved");
        assertEquals(date, holiday.getDate(), "holiday.date mismatch");
        assertEquals(startTime, holiday.getStartTime(), "holiday.startTime mismatch");
        assertEquals(endTime, holiday.getEndtime(), "holiday.endTime mismatch");

        // test persistence of head librarian within holiday
        assertNotNull(holiday.getHeadLibrarian(), "No head librarian retrieved");
        assertEquals(firstName, holiday.getHeadLibrarian().getFirstName(), "holiday.headLibrarian.firstName mismatch");
        assertEquals(lastName, holiday.getHeadLibrarian().getLastName(), "holiday.headLibrarian.lastName mismatch");
        assertEquals(online, holiday.getHeadLibrarian().getOnlineAccount(),"holiday.headLibrarian.onlineAccount mismatch");
        assertEquals(password, holiday.getHeadLibrarian().getPassword(), "holiday.headLibrarian.password mismatch");
        assertEquals(address, holiday.getHeadLibrarian().getAddress(), "holiday.headLibrarian.address mismatch");
        assertEquals(email, holiday.getHeadLibrarian().getEmail(), "holiday.headLibrarian.email mismatch");

    }

    @Test
    @SuppressWarnings("deprecation")
    public void testPersistAndLoadHolidayByReference() {
        // create inputs for holiday constructor
        Date date = new Date(2020, 12, 25);
        Time startTime = new Time(12, 43, 0);
        Time endTime = new Time(13, 55, 3);

        // create inputs for head librarian constructor
        String firstName = "Lorri";
        String lastName = "Kent";
        boolean online = false;
        String password = "zxcvbnm";
        String email = "Lorri@email.com";
        int balance = 0;
        String address = "10 Road, Toronto, Canada";

        // create head librarian
        HeadLibrarian headLibrarian = new HeadLibrarian(firstName, lastName, online, address, password, balance, email);

        headLibrarianRepository.save(headLibrarian);
        librarianRepository.save(headLibrarian);
        userAccountRepository.save(headLibrarian);

        // create holiday
        Holiday holiday = new Holiday(date, startTime, endTime, headLibrarian);

        // save holiday to DB
        holidayRepository.save(holiday);

        // clear timeslot
        holiday = null;

        // get holiday from DB
        holiday = holidayRepository.findByHeadLibrarian(headLibrarian).get(0);

        // test functionality
        assertNotNull(holiday, "No holiday retrieved");
        assertEquals(date, holiday.getDate(), "holiday.date mismatch");
        assertEquals(startTime, holiday.getStartTime(), "holiday.startTime mismatch");
        assertEquals(endTime, holiday.getEndtime(), "holiday.endTime mismatch");

        // test persistence of head librarian within holiday
        assertNotNull(holiday.getHeadLibrarian(), "No head librarian retrieved");
        assertEquals(firstName, holiday.getHeadLibrarian().getFirstName(), "holiday.headLibrarian.firstName mismatch");
        assertEquals(lastName, holiday.getHeadLibrarian().getLastName(), "holiday.headLibrarian.lastName mismatch");
        assertEquals(online, holiday.getHeadLibrarian().getOnlineAccount(),"holiday.headLibrarian.onlineAccount mismatch");
        assertEquals(password, holiday.getHeadLibrarian().getPassword(), "holiday.headLibrarian.password mismatch");
        assertEquals(address, holiday.getHeadLibrarian().getAddress(),"holiday.headLibrarian.address.address mismatch");
        assertEquals(email, holiday.getHeadLibrarian().getEmail(), "holiday.headLibrarian.address.email mismatch");
    }

}
