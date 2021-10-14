package ca.mcgill.ecse321.libraryservice.dao;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import ca.mcgill.ecse321.libraryservice.model.TimeSlot;
import ca.mcgill.ecse321.libraryservice.model.Librarian;

public interface TimeSlotRepository extends CrudRepository<TimeSlot, Integer>{
	
	TimeSlot findTimeSlotByTimeSlotID(int timeSlotID);
	
	List<TimeSlot> findByLibrarian(Librarian librarian);
	
}
