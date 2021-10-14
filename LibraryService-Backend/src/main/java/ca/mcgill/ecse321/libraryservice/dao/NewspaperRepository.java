package ca.mcgill.ecse321.libraryservice.dao;

import org.springframework.data.repository.CrudRepository;
import ca.mcgill.ecse321.libraryservice.model.Newspaper;

public interface NewspaperRepository extends CrudRepository<Newspaper, Integer>{
	
	Newspaper findNewspaperByPaperID(int paperID);

}