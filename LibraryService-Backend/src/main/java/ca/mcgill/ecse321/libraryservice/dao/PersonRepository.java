package ca.mcgill.ecse321.libraryservice.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.libraryservice.model.Book;
import ca.mcgill.ecse321.libraryservice.model.Movie;
import ca.mcgill.ecse321.libraryservice.model.Music;
import ca.mcgill.ecse321.libraryservice.model.Person;

public interface PersonRepository extends CrudRepository<Person, Integer>{
	
	Person findPersonByAuthorID(int authorID);

}
