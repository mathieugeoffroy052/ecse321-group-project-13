package ca.mcgill.ecse321.libraryservice.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.libraryservice.model.Movie;
import ca.mcgill.ecse321.libraryservice.model.Person;

public interface MovieRepository extends CrudRepository<Movie, Integer>{
	
	Movie findMovieByIsbn(int isbn);
	
	List<Movie> findByPerson(Person director);
	
}
