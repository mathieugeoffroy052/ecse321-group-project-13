package ca.mcgill.ecse321.libraryservice.dao;

//imports
import org.springframework.data.repository.CrudRepository;
import ca.mcgill.ecse321.libraryservice.model.UserAccount;
import ca.mcgill.ecse321.libraryservice.model.Address;
import ca.mcgill.ecse321.libraryservice.model.LibrarySystem;
import java.util.List;

public interface UserAccountRepository extends CrudRepository<UserAccount, Integer>{

	UserAccount findUserAccountByUserAccountID(Integer userID);
	
	List<UserAccount> findByAddress(Address addressID);
	
	List<UserAccount> findByLibrarySystem(LibrarySystem systemID);
	
}
