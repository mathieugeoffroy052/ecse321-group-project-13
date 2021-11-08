package ca.mcgill.ecse321.libraryservice.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.sql.Date;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import ca.mcgill.ecse321.libraryservice.model.BorrowableItem;
import ca.mcgill.ecse321.libraryservice.model.LibraryItem;
import ca.mcgill.ecse321.libraryservice.model.LibrarySystem;
import ca.mcgill.ecse321.libraryservice.model.Patron;
import ca.mcgill.ecse321.libraryservice.model.Transaction;
import ca.mcgill.ecse321.libraryservice.model.BorrowableItem.ItemState;
import ca.mcgill.ecse321.libraryservice.model.LibraryItem.ItemType;
import ca.mcgill.ecse321.libraryservice.model.Transaction.TransactionType;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class TestTransactionPersistence {
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
    public void testPersistAndLoadTransaction() {
        LibrarySystem library = new LibrarySystem();
        librarySystemRepository.save(library);

        //create inputs for transaction
        TransactionType transactionType = TransactionType.Borrowing;
        Date deadline =  new Date(2021, 5, 10);

        //create input for Patron (user)
        String firstName = "Matty";
        String lastName = "Pattaty";
        boolean online = true;
        String address = "1000 Rue Sherbrooke O, Montreal, Canada";
        boolean validated = true;
        String password = "thisisapassword";
        String email = "Matty@email.com";
        int balance = 0;

        //create patron
        Patron patron = new Patron(firstName, lastName, online, library, address, validated, password, balance, email);
        

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


        //create transaction
        Transaction transaction = new Transaction(item, patron, transactionType, deadline);

        //get IDs to find in DB
        int transactionID = transaction.getTransactionID();

        //save in DB
        userAccountRepository.save(patron);
        libraryItemRepository.save(libraryItem);
        borrowableItemRepository.save(item);
        transactionRepository.save(transaction);
        patronRepository.save(patron);

        //clear all instances
        transaction = null;

        //get transaction from DB
        transaction = transactionRepository.findTransactionByTransactionID(transactionID);

        //test functionality
        assertNotNull(transaction, "No transaction retrieved");
        assertEquals(transactionType, transaction.getTransactionType(), "transaction.transactionType mismatch");
        assertEquals(deadline, transaction.getDeadline(), "transaction.deadline mismatch");

        //test patron within transaction
        assertEquals(firstName, transaction.getUserAccount().getFirstName(), "transaction.userAccount.firstName mismatch");
        assertEquals(lastName, transaction.getUserAccount().getLastName(), "transaction.userAccount.lastName mismatch");
        assertEquals(online, transaction.getUserAccount().getOnlineAccount(), "transaction.userAccount.onlineAccount mismatch");
        assertEquals(validated, ((Patron)transaction.getUserAccount()).getValidatedAccount(), "transaction.userAccount.validateAccount mismatch");
        assertEquals(password, transaction.getUserAccount().getPassword(), "transaction.userAccount.password mismatch");
        assertEquals(address, transaction.getUserAccount().getAddress(), "transaction.userAccount.address mismatch");
        assertEquals(email, transaction.getUserAccount().getEmail(), "transaction.userAccount.email mismatch");
        assertEquals(balance, transaction.getUserAccount().getBalance(), "transaction.userAccount.balance mismatch");
        assertEquals(library.getSystemId(), transaction.getUserAccount().getLibrarySystem().getSystemId(), "transaction.userAccount.librarySystem.systemID mismatch");

        //test borrowable item within transaction
        assertEquals(state, transaction.getBorrowableItem().getState(), "transaction.borrowableItem.state mismatch");

        //test library item within transaction
        assertEquals(viewable, transaction.getBorrowableItem().getLibraryItem().getIsViewable(), "transaction.borrowableItem.libraryItem.isViewable mismatch");
        assertEquals(publishingDate, transaction.getBorrowableItem().getLibraryItem().getDate(), "transaction.borrowableItem.libraryItem.date mismatch");
        assertEquals(name, transaction.getBorrowableItem().getLibraryItem().getName(), "transaction.borrowableItem.libraryItem.state mismatch");
        assertEquals(creator, transaction.getBorrowableItem().getLibraryItem().getCreator(), "transaction.borrowableitem.libraryItem.creator mismatch"); 
        assertEquals(library.getSystemId(), transaction.getBorrowableItem().getLibraryItem().getLibrarySystem().getSystemId(), "transaction.borrowableitem.libraryItem.librarySystem.systemID mismatch");
    }


    @Test @SuppressWarnings("deprecation")
    public void testPersistAndLoadTransactionByReferenceBorrowableitem() {
        LibrarySystem library = new LibrarySystem();
        librarySystemRepository.save(library);

        //create inputs for transaction
        TransactionType transactionType = TransactionType.Renewal;
        Date deadline =  new Date(2021, 5, 10);

        //create input for Patron (user)
        String firstName = "Matty";
        String lastName = "Pattaty";
        boolean online = true;
        boolean validated = true;
        String password = "thisisapassword";
        String email = "Matty@email.com";
        int balance = 0;
        String address = "50 Rue Prince Arthur, Montreal, Canada";

        //create patron
        Patron patron = new Patron(firstName, lastName, online, library, address, validated, password, balance, email);

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
        libraryItemRepository.save(libraryItem);
        
        //create inputs for BorrowableItem
        ItemState state = ItemState.Available;

        //create item
        BorrowableItem item = new BorrowableItem(state, libraryItem);
        borrowableItemRepository.save(item);

        //create transaction
        Transaction transaction = new Transaction(item, patron, transactionType, deadline);

        //save in DB
        transactionRepository.save(transaction);

        //clear all instances
        transaction = null;

        //get transaction from DB
        transaction = transactionRepository.findByBorrowableItem(item).get(0);

        //test functionality
        assertNotNull(transaction, "No transaction retrieved");
        assertEquals(transactionType, transaction.getTransactionType(), "transaction.transactionType mismatch");
        assertEquals(deadline, transaction.getDeadline(), "transaction.deadline mismatch");

        //test patron within transaction
        assertEquals(firstName, transaction.getUserAccount().getFirstName(), "transaction.userAccount.firstName mismatch");
        assertEquals(lastName, transaction.getUserAccount().getLastName(), "transaction.userAccount.lastName mismatch");
        assertEquals(online, transaction.getUserAccount().getOnlineAccount(), "transaction.userAccount.onlineAccount mismatch");
        assertEquals(validated, ((Patron)transaction.getUserAccount()).getValidatedAccount(), "transaction.userAccount.validateAccount mismatch");
        assertEquals(password, transaction.getUserAccount().getPassword(), "transaction.userAccount.password mismatch");
        assertEquals(address, transaction.getUserAccount().getAddress(), "transaction.userAccount.address mismatch");
        assertEquals(email, transaction.getUserAccount().getEmail(), "transaction.userAccount.email mismatch");
        assertEquals(balance, transaction.getUserAccount().getBalance(), "transaction.userAccount.balance mismatch");
        assertEquals(library.getSystemId(), transaction.getUserAccount().getLibrarySystem().getSystemId(), "transaction.userAccount.librarySystem.systemID mismatch");

        //test borrowable item within transaction
        assertEquals(state, transaction.getBorrowableItem().getState(), "transaction.borrowableItem.state mismatch");

        //test library item within transaction
        assertEquals(viewable, transaction.getBorrowableItem().getLibraryItem().getIsViewable(), "transaction.borrowableItem.libraryItem.isViewable mismatch");
        assertEquals(publishingDate, transaction.getBorrowableItem().getLibraryItem().getDate(), "transaction.borrowableItem.libraryItem.date mismatch");
        assertEquals(name, transaction.getBorrowableItem().getLibraryItem().getName(), "transaction.borrowableItem.libraryItem.state mismatch");
        assertEquals(creator, transaction.getBorrowableItem().getLibraryItem().getCreator(), "transaction.borrowableitem.libraryItem.creator mismatch"); 
        assertEquals(library.getSystemId(), transaction.getBorrowableItem().getLibraryItem().getLibrarySystem().getSystemId(), "transaction.borrowableitem.libraryItem.librarySystem.systemID mismatch");
    }


    @Test @SuppressWarnings("deprecation")
    public void testPersistAndLoadTransactionByReferenceUserAccount() {
        LibrarySystem library = new LibrarySystem();
        librarySystemRepository.save(library);

        //create inputs for transaction
        TransactionType transactionType = TransactionType.Return;
        Date deadline =  new Date(2021, 12, 5);

        //create input for Patron (user)
        String firstName = "Amanda";
        String lastName = "Rose";
        boolean online = true;
        boolean validated = true;
        String password = "heythisismypassword";
        String email = "Amanda@email.com";
        int balance = 100;
        String address = "88 Av du Parc, Montreal, Canada";

        //create patron
        Patron patron = new Patron(firstName, lastName, online, library, address, validated, password, balance, email);

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
        libraryItemRepository.save(libraryItem);
        
        //create inputs for BorrowableItem
        ItemState state = ItemState.Available;

        //create item
        BorrowableItem item = new BorrowableItem(state, libraryItem);
        borrowableItemRepository.save(item);

        //create transaction
        Transaction transaction = new Transaction(item, patron, transactionType, deadline);

        //save in DB
        transactionRepository.save(transaction);

        //clear all instances
        transaction = null;

        //get transaction from DB
        transaction = transactionRepository.findByUserAccount(patron).get(0);

        //test functionality
        assertNotNull(transaction, "No transaction retrieved");
        assertEquals(transactionType, transaction.getTransactionType(), "transaction.transactionType mismatch");
        assertEquals(deadline, transaction.getDeadline(), "transaction.deadline mismatch");

        //test patron within transaction
        assertEquals(firstName, transaction.getUserAccount().getFirstName(), "transaction.userAccount.firstName mismatch");
        assertEquals(lastName, transaction.getUserAccount().getLastName(), "transaction.userAccount.lastName mismatch");
        assertEquals(online, transaction.getUserAccount().getOnlineAccount(), "transaction.userAccount.onlineAccount mismatch");
        assertEquals(validated, ((Patron)transaction.getUserAccount()).getValidatedAccount(), "transaction.userAccount.validateAccount mismatch");
        assertEquals(password, transaction.getUserAccount().getPassword(), "transaction.userAccount.password mismatch");
        assertEquals(address, transaction.getUserAccount().getAddress(), "transaction.userAccount.address mismatch");
        assertEquals(email, transaction.getUserAccount().getEmail(), "transaction.userAccount.email mismatch");
        assertEquals(balance, transaction.getUserAccount().getBalance(), "transaction.userAccount.balance mismatch");
        assertEquals(library.getSystemId(), transaction.getUserAccount().getLibrarySystem().getSystemId(), "transaction.userAccount.librarySystem.systemID mismatch");

        //test borrowable item within transaction
        assertEquals(state, transaction.getBorrowableItem().getState(), "transaction.borrowableItem.state mismatch");

        //test library item within transaction
        assertEquals(viewable, transaction.getBorrowableItem().getLibraryItem().getIsViewable(), "transaction.borrowableItem.libraryItem.isViewable mismatch");
        assertEquals(releaseDate, transaction.getBorrowableItem().getLibraryItem().getDate(), "transaction.borrowableItem.libraryItem.date mismatch");
        assertEquals(name, transaction.getBorrowableItem().getLibraryItem().getName(), "transaction.borrowableItem.libraryItem.state mismatch");
        assertEquals(creator, transaction.getBorrowableItem().getLibraryItem().getCreator(), "transaction.borrowableitem.libraryItem.creator mismatch"); 
        assertEquals(library.getSystemId(), transaction.getBorrowableItem().getLibraryItem().getLibrarySystem().getSystemId(), "transaction.borrowableitem.libraryItem.librarySystem.systemID mismatch");
    }

}
