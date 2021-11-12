package ca.mcgill.ecse321.libraryservice.dao;

//imports
import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.libraryservice.model.Patron;
import ca.mcgill.ecse321.libraryservice.model.UserAccount;

public interface PatronRepository extends CrudRepository<Patron, Integer> {
	
	Patron findPatronByUserID(int userID);
	Patron findPatronByFullName(String firstName, String lastName);

}
