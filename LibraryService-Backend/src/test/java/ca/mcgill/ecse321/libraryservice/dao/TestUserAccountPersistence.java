package ca.mcgill.ecse321.libraryservice.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import ca.mcgill.ecse321.libraryservice.model.Patron;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class TestUserAccountPersistence {

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
    public void testPersistAndLoadUserAccount() {

        // inputs for patron
        String firstName = "Jane";
        String lastName = "Doe";
        boolean online = true;
        boolean validated = true;
        String password = "thisIsJane";
        String email = "Jane@email.com";
        int balance = 25;
        String address = "4000 McGill, Montreal, Canada";

        // create patron and persist
        Patron patron = new Patron(firstName, lastName, online, address, validated, password, balance, email);

        patronRepository.save(patron);
        userAccountRepository.save(patron);
        int id = patron.getUserID();
        // clear patron
        patron = null;

        // retrieve patron by library system from DB
        patron = (Patron) userAccountRepository.findUserAccountByUserID(id);

        // test functionality
        assertNotNull(patron, "No Patron retrieved");
        assertEquals(firstName, patron.getFirstName(), "patron.firstName mismatch");
        assertEquals(lastName, patron.getLastName(), "patron.lastName mismatch");
        assertEquals(online, patron.getOnlineAccount(), "patron.onlineAccount mismatch");
        assertEquals(validated, patron.getValidatedAccount(), "patron.validatedAccount mismatch");
        assertEquals(password, patron.getPassword(), "patron.password mismatch");
        assertEquals(address, patron.getAddress(), "patron.address mismatch");
        assertEquals(email, patron.getEmail(), "patron.email mismatch");
    }
}
