package ca.mcgill.ecse321.libraryservice.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.libraryservice.model.BorrowableItem;
import ca.mcgill.ecse321.libraryservice.model.LibraryItem;
import ca.mcgill.ecse321.libraryservice.model.UserAccount;

public interface BorrowableItemRepository extends CrudRepository<BorrowableItem, Integer>{
	
	BorrowableItem findBorrowableItemByBarCodeNumber(int barCodeNumber);
	
	List<BorrowableItem> findByLibraryItem(LibraryItem libraryItem);
	
	List<BorrowableItem> findByBorrower(UserAccount borrower);
	
	List<BorrowableItem> findByReserver(UserAccount reserver);
}
