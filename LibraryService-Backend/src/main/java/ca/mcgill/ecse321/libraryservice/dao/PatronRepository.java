package ca.mcgill.ecse321.libraryservice.dao;

//imports
import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.libraryservice.model.Address;
import ca.mcgill.ecse321.libraryservice.model.Patron;

public interface PatronRepository extends CrudRepository<Patron, Integer> {
	
	Patron findPatronByUserID(int userID);

	Patron findPatronByAddress(Address address);
		
}
