package ca.mcgill.ecse321.libraryservice.dao;

//imports
import ca.mcgill.ecse321.libraryservice.model.Librarian;
import ca.mcgill.ecse321.libraryservice.model.HeadLibrarian;
import org.springframework.data.repository.CrudRepository;
import java.util.List;

public interface LibrarianRepository extends CrudRepository<Librarian, Integer> {
	
	
	Librarian findLibrarianByUserAccount(Integer userID);
	
	List<Librarian> findByTimeSlot(Integer timeSlotID);
	
	
	
}


