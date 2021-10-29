package ca.mcgill.ecse321.libraryservice.dao;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.libraryservice.model.LibrarySystem;

public interface LibrarySystemRepository extends CrudRepository<LibrarySystem, Integer> {
    
    LibrarySystem findBySystemId(int systemId);

}
