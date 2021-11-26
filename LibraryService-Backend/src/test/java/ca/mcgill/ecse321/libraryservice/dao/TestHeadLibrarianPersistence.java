package ca.mcgill.ecse321.libraryservice.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import ca.mcgill.ecse321.libraryservice.model.HeadLibrarian;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class TestHeadLibrarianPersistence {
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
    public void testPersistAndLoadHeadLibrarian() {

        // create inputs for head librarian constructor
        String firstName = "Laura";
        String lastName = "Porto";
        boolean online = true;
        String password = "asdfghjkl";
        String email = "Laura@email.com";
        int balance = 0;
        String address = "500 Sherbrooke, Montreal, Canada";

        // create head librarian
        HeadLibrarian headLibrarian = new HeadLibrarian(firstName, lastName, online, address, password, balance, email);

        // get headLibrarianID to retrieve head librarian from DB
        int headLibrarianID = headLibrarian.getUserID();

        // save head librarian in DB
        headLibrarianRepository.save(headLibrarian);
        librarianRepository.save(headLibrarian);
        userAccountRepository.save(headLibrarian);

        // clear head librarian
        headLibrarian = null;

        // get head librarian from DB
        headLibrarian = headLibrarianRepository.findHeadLibrarianByUserID(headLibrarianID);

        // test functionnality
        assertNotNull(headLibrarian, "No headlibrarian retrieved");
        assertEquals(firstName, headLibrarian.getFirstName(), "headLibrarian.firstName mismatch");
        assertEquals(lastName, headLibrarian.getLastName(), "headLibrarian.lastName mismatch");
        assertEquals(online, headLibrarian.getOnlineAccount(), "headLibrarian.onlineAccount mismatch");
        assertEquals(password, headLibrarian.getPassword(), "headLibrarian.password msmatch");
        assertEquals(address, headLibrarian.getAddress(), "headLibrarian.address mismatch");
        assertEquals(email, headLibrarian.getEmail(), "headLibrarian.email mismatch");
    }

}
