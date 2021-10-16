package ca.mcgill.ecse321.libraryservice.dao;

//imports
import org.springframework.data.repository.CrudRepository;
import ca.mcgill.ecse321.libraryservice.model.Patron;
import java.util.List;

public interface PatronRepository extends CrudRepository<Patron, Integer>{

	//Within the interface
	Patron findPatronByUserID(Integer id);
	
	
	
	
}
