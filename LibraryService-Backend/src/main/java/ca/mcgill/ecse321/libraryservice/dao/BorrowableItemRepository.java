package ca.mcgill.ecse321.libraryservice.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.libraryservice.model.BorrowableItem;
import ca.mcgill.ecse321.libraryservice.model.LibraryItem;

public interface BorrowableItemRepository extends CrudRepository<BorrowableItem, Integer> {
    
    BorrowableItem findBorrowableItemByBarCodeNumber(int barCodeNumber);

    List<BorrowableItem> findByLibraryItem(LibraryItem libraryItem);

}
