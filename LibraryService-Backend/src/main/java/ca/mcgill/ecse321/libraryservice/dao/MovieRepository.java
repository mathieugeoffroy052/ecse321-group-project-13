package ca.mcgill.ecse321.libraryservice.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.libraryservice.model.Movie;

public interface MovieRepository extends CrudRepository<Movie, Integer>{
	
	Movie findMovieByMovieID(int movieID);
	
}
