package ca.mcgill.ecse321.libraryservice.dao;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.libraryservice.model.Book;

public interface BookRepository extends CrudRepository<Book, Integer> {
    
    Book findByIsbn(int isbn);

}