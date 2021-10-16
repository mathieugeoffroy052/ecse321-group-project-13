package ca.mcgill.ecse321.libraryservice.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.libraryservice.model.Music;
import ca.mcgill.ecse321.libraryservice.model.Person;

public interface MusicRepository extends CrudRepository<Music, Integer>{
	
	Music findMusicByIsbn(int isbn);
	
	List<Music> findByPerson(Person artist);

}
