package ca.mcgill.ecse321.libraryservice.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.libraryservice.model.BorrowableItem;
import ca.mcgill.ecse321.libraryservice.model.Transaction;
import ca.mcgill.ecse321.libraryservice.model.UserAccount;

public interface TransactionRepository extends CrudRepository<UserAccount, Integer> {
	
	Transaction findTransactionByTransactionID(int transactionID);
	
	List<Transaction> findByBorrowableItem(BorrowableItem borrowableItem);
	
	List<Transaction> findByUserAccount(UserAccount userAccount);

}
