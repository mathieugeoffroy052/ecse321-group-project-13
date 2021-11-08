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

import ca.mcgill.ecse321.libraryservice.model.LibraryItem;
import ca.mcgill.ecse321.libraryservice.model.LibrarySystem;
import ca.mcgill.ecse321.libraryservice.model.LibraryItem.ItemType;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class TestLibraryItemPersistence {
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

    @Test @SuppressWarnings("deprecation")
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
}
