package ca.mcgill.ecse321.libraryservice.dao;

import org.springframework.data.repository.CrudRepository;
import ca.mcgill.ecse321.libraryservice.model.Holiday;

public interface HolidayRepository extends CrudRepository<Holiday, Integer>{
	
	Holiday findHolidayByHolidayID(int holidayID);
}