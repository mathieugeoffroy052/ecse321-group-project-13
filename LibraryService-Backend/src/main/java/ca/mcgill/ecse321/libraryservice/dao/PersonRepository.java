package ca.mcgill.ecse321.libraryservice.dao;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.libraryservice.model.Person;

public interface PersonRepository extends CrudRepository<Person, Integer>{

}
