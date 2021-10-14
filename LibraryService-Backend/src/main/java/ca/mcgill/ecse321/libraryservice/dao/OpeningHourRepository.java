package ca.mcgill.ecse321.libraryservice.dao;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.libraryservice.model.OpeningHour;

public interface OpeningHourRepository extends CrudRepository<OpeningHour, Integer>{
	OpeningHour findOpeningHourByHourID(int hourID);
}