package ca.mcgill.ecse321.libraryservice.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.libraryservice.model.Book;
import ca.mcgill.ecse321.libraryservice.model.LibrarySystem;
import ca.mcgill.ecse321.libraryservice.model.Person;

public interface BookRepository extends CrudRepository<Book, Integer>{
	
	Book findBookByBookID(int bookID);
	
}
