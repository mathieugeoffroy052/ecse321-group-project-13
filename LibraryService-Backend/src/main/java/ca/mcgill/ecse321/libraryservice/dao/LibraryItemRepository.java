package ca.mcgill.ecse321.libraryservice.dao;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.libraryservice.model.LibraryItem;

public interface LibraryItemRepository extends CrudRepository<LibraryItem, Integer>{
    
    LibraryItem findByIsbn(int isbn);

}
