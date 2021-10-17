package ca.mcgill.ecse321.libraryservice.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.libraryservice.model.LibraryItem;
import ca.mcgill.ecse321.libraryservice.model.LibrarySystem;

public interface LibraryItemRepository extends CrudRepository<LibraryItem, Integer> {
    
    LibraryItem findByIsbn(int isbn);

    List<LibraryItem> findByLibrarySystem(LibrarySystem librarySystem);
}
