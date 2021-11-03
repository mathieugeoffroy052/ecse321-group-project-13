package ca.mcgill.ecse321.libraryservice.dao;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

import ca.mcgill.ecse321.libraryservice.model.OpeningHour;
import ca.mcgill.ecse321.libraryservice.model.OpeningHour.DayOfWeek;
import ca.mcgill.ecse321.libraryservice.model.HeadLibrarian;
import ca.mcgill.ecse321.libraryservice.model.LibrarySystem;

public interface OpeningHourRepository extends CrudRepository<OpeningHour, Integer> {
	
	OpeningHour findOpeningHourByHourID(int hourID);
	
	List<OpeningHour> findByHeadLibrarian(HeadLibrarian headLibrarian);
	
	List<OpeningHour> findByLibrarySystem(LibrarySystem librarySystem);

	//TODO: Add unit test for this
	List<OpeningHour> findByDayOfWeek(DayOfWeek dayOfWeek);
}
