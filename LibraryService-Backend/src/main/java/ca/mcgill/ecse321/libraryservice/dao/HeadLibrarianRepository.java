package ca.mcgill.ecse321.libraryservice.dao;

import org.springframework.data.repository.CrudRepository;
import ca.mcgill.ecse321.libraryservice.model.HeadLibrarian;
import ca.mcgill.ecse321.libraryservice.model.Librarian;

import java.util.List;


public interface HeadLibrarianRepository extends CrudRepository<HeadLibrarian, Integer>{

	
	HeadLibrarian findByUserID(Integer userID);
	
	
	
}
