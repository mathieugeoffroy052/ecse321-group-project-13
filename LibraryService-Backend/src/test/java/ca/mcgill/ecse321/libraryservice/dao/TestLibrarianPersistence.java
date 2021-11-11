package ca.mcgill.ecse321.libraryservice.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import ca.mcgill.ecse321.libraryservice.model.Librarian;
import ca.mcgill.ecse321.libraryservice.model.UserAccount;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class TestLibrarianPersistence {
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

    @Test
    public void testPersistAndLoadLibrarian() {
        //create inputs for librarian constructor
        String firstName = "Jake";
        String lastName = "Morello";
        boolean online = false;
        String password = "qwertyuiop";
        String email = "jake@email.com";
        int balance = 0;
        String address = "100 Durocher, Montreal, Canada";
        
        //create librarian
        Librarian librarian = new Librarian(firstName, lastName, online, address, password, balance, email);

        //get librarianID to retrieve librarian from DB
        int librarianUserID = librarian.getUserID();
        int librarianID = librarian.getlibrarianID();

        //save librarian in DB
        librarianRepository.save(librarian);
        userAccountRepository.save(librarian);


        //clear librarian
        librarian = null;

        //get librarian from DB
        librarian = librarianRepository.findLibrarianByUserID(librarianUserID);

        //test functionnality
        assertNotNull(librarian, "No librarian retrieved");
        assertEquals(firstName, librarian.getFirstName(), "librarian.firstName mismatch");
        assertEquals(lastName, librarian.getLastName(), "librarian.lastName mismatch");
        assertEquals(online, librarian.getOnlineAccount(), "librarian.onlineAccount mismatch");
        assertEquals(password, librarian.getPassword(), "librarian.password mismatch");
        assertEquals(address, librarian.getAddress(), "librarian.address mismatch");
        assertEquals(email, librarian.getEmail(), "librarian.email mismatch");
        assertEquals(librarianID, librarian.getlibrarianID(),"librarian user ID mismatch" );
    }

/**
 * For this test
 * Step 1 :I am retrieving from UserAccount a User with the corresponding name 
 * Step 2 : converting to librarian 
 * Checks:
 * step 1 : asserting not null of UserAccount if true assert equals with librarian instance
 * step 2 : asserting not null of UserAccount if true assert equals with librarian instance
 */



    @Test
    public void testPersistAndLoadLibrarianbyFullName() {
        //create inputs for librarian constructor
        String firstName = "Jake";
        String lastName = "Morello";
        boolean online = false;
        String password = "qwertyuiop";
        String email = "jake@email.com";
        int balance = 0;
        String address = "100 Durocher, Montreal, Canada";
        
        //create librarian
        Librarian librarian = new Librarian(firstName, lastName, online, address, password, balance, email);

        //get librarianID to retrieve librarian from DB
        int librarianUserID = librarian.getUserID();
        int librarianID = librarian.getlibrarianID();
        //save librarian in DB
        librarianRepository.save(librarian);
        userAccountRepository.save(librarian);


        //clear librarian
        librarian = null;
        UserAccount librarianpossible = userAccountRepository.findByFirstNameAndLastName(firstName, lastName);
         
        //UserAccount
        assertNotNull(librarianpossible, "No librarian retrieved");
        assertEquals(firstName, librarianpossible.getFirstName(), "librarian.firstName mismatch");
        assertEquals(lastName, librarianpossible.getLastName(), "librarian.lastName mismatch");
        assertEquals(online, librarianpossible.getOnlineAccount(), "librarian.onlineAccount mismatch");
        assertEquals(password, librarianpossible.getPassword(), "librarian.password mismatch");
        assertEquals(address, librarianpossible.getAddress(), "librarian.address mismatch");
        assertEquals(email, librarianpossible.getEmail(), "librarian.email mismatch");
        assertEquals(librarianUserID, librarianpossible.getUserID(), "librarian user ID mismatch");



        assertTrue(librarian instanceof Librarian, "the name privided does not correcpond to a librarian");
     
        librarian= (Librarian) librarianpossible;
        //get librarian from DB


        //test functionnality
        assertNotNull(librarian, "No librarian retrieved");
        assertEquals(firstName, librarian.getFirstName(), "librarian.firstName mismatch");
        assertEquals(lastName, librarian.getLastName(), "librarian.lastName mismatch");
        assertEquals(online, librarian.getOnlineAccount(), "librarian.onlineAccount mismatch");
        assertEquals(password, librarian.getPassword(), "librarian.password mismatch");
        assertEquals(address, librarian.getAddress(), "librarian.address mismatch");
        assertEquals(email, librarian.getEmail(), "librarian.email mismatch");
        
        //librarian account

        assertNotNull(librarian, "No librarian retrieved");
        assertEquals(firstName, librarian.getFirstName(), "librarian.firstName mismatch");
        assertEquals(lastName, librarian.getLastName(), "librarian.lastName mismatch");
        assertEquals(online, librarian.getOnlineAccount(), "librarian.onlineAccount mismatch");
        assertEquals(password, librarian.getPassword(), "librarian.password mismatch");
        assertEquals(address, librarian.getAddress(), "librarian.address mismatch");
        assertEquals(email, librarian.getEmail(), "librarian.email mismatch");
        assertEquals(librarianUserID, librarian.getUserID(), "librarian user ID mismatch");
        assertEquals(librarianID, librarian.getlibrarianID());



    }









}
