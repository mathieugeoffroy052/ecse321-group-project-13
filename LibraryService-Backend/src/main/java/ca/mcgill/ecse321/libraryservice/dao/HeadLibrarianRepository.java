package ca.mcgill.ecse321.libraryservice.dao;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.libraryservice.model.HeadLibrarian;
import java.util.List;
public interface HeadLibrarianRepository extends CrudRepository<HeadLibrarian, Integer> {

	HeadLibrarian findHeadLibrarianByUserID(int userID);
	
	List<HeadLibrarian>  findByLastName( String lastName);
}
