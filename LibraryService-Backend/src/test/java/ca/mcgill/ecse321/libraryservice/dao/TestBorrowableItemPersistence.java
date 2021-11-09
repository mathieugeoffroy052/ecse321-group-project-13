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

import ca.mcgill.ecse321.libraryservice.model.BorrowableItem.ItemState;
import ca.mcgill.ecse321.libraryservice.model.LibraryItem.ItemType;
import ca.mcgill.ecse321.libraryservice.model.BorrowableItem;
import ca.mcgill.ecse321.libraryservice.model.LibraryItem;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class TestBorrowableItemPersistence {
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
    public void testPersistAndLoadBorrowableItem() { 

        //create inputs for BorrowableItem
        ItemState state = ItemState.Available;

        //create inputs for LibraryItem
        boolean viewable = true;
        Date releaseDate = new Date(2019, 5, 6);
        ItemType itemType = ItemType.Music;
        String creator = "Maluma";
        String name = "11:11";

        //create library item
        LibraryItem libraryItem = new LibraryItem(name, itemType, releaseDate, creator, viewable);
        libraryItemRepository.save(libraryItem);
        
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

}
