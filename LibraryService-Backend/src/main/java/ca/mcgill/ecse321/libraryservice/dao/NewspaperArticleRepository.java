package ca.mcgill.ecse321.libraryservice.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.libraryservice.model.NewspaperArticle;
import ca.mcgill.ecse321.libraryservice.model.LibrarySystem;
import ca.mcgill.ecse321.libraryservice.model.Newspaper;

public interface NewspaperArticleRepository extends CrudRepository<NewspaperArticle, Integer> {
	
	NewspaperArticle findNewspaperArticleByBarCodeNumber(int barCodeNumber);
	
	List<NewspaperArticle> findByNewspaper(Newspaper newspaper);
	
	List<NewspaperArticle> findByLibrarySystem(LibrarySystem librarySystem);
	
}
