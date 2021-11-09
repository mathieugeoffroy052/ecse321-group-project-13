package ca.mcgill.ecse321.libraryservice.dao;

//imports
import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.libraryservice.model.UserAccount;

import java.util.List;

public interface UserAccountRepository extends CrudRepository<UserAccount, Integer> {

	UserAccount findUserAccountByUserID(int userID);
	//TODO: Add Unit test for this
	UserAccount findByFirstNameAndLastName(String firstName, String lastName);
	
}
