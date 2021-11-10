package ca.mcgill.ecse321.libraryservice.dao;

//imports
import ca.mcgill.ecse321.libraryservice.model.Librarian;

import java.util.List;


import org.springframework.data.repository.CrudRepository;
public interface LibrarianRepository extends CrudRepository<Librarian, Integer> {

	Librarian findLibrarianByUserID(int userID);
}
