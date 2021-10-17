package ca.mcgill.ecse321.libraryservice.dao;

<<<<<<< HEAD
//imports
import org.springframework.data.repository.CrudRepository;
import ca.mcgill.ecse321.libraryservice.model.Patron;
import java.util.List;

public interface PatronRepository extends CrudRepository<Patron, Integer>{

	
	Patron findPatronByUserID(int userID);
	
	
	
	
=======
public interface PatronRepository {

>>>>>>> origin/ramin-crud-interfaces
}
