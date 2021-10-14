package ca.mcgill.ecse321.libraryservice.dao;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.libraryservice.model.BorrowableItem;

public interface BorrowableItemRepository extends CrudRepository<BorrowableItem, Integer>{

}
