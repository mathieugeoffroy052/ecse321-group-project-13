package ca.mcgill.ecse321.libraryservice.dao;

//imports
import org.springframework.data.repository.CrudRepository;
import ca.mcgill.ecse321.libraryservice.model.UserAccount;
import java.util.List;

public interface UserAccountRepository extends CrudRepository<UserAccount, Integer>{

	UserAccount findByUserID(Integer userID);
	
	List<UserAccount> findByAddress(Integer id);
	
	
	
}
