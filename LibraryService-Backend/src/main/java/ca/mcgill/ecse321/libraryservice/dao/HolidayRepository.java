package ca.mcgill.ecse321.libraryservice.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import ca.mcgill.ecse321.libraryservice.model.Holiday;
import ca.mcgill.ecse321.libraryservice.model.LibrarySystem;
import ca.mcgill.ecse321.libraryservice.model.HeadLibrarian;

public interface HolidayRepository extends CrudRepository<Holiday, Integer>{
	
	Holiday findHolidayByHolidayID(int holidayID);
	
	List<Holiday> findByHeadLibrarian(HeadLibrarian headLibrarian);
	
	List<Holiday> findByLibrarySystem(LibrarySystem librarySystem);
}