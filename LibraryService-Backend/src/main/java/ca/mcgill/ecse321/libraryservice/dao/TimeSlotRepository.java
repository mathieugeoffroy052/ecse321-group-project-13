package ca.mcgill.ecse321.libraryservice.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.libraryservice.model.TimeSlot;
import ca.mcgill.ecse321.libraryservice.model.Librarian;
import ca.mcgill.ecse321.libraryservice.model.LibrarySystem;
import ca.mcgill.ecse321.libraryservice.model.HeadLibrarian;

public interface TimeSlotRepository extends CrudRepository<TimeSlot, Integer> {
	
	TimeSlot findTimeSlotByTimeSlotID(int timeSlotID);
	
	List<TimeSlot> findByLibrarian(Librarian librarian);
	
	List<TimeSlot> findByHeadLibrarian(HeadLibrarian headLibrarian);
	
	List<TimeSlot> findByLibrarySystem(LibrarySystem librarySystem);
	
}
