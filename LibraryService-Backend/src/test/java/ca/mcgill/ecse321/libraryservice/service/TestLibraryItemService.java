package ca.mcgill.ecse321.libraryservice.service;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.lenient;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import ca.mcgill.ecse321.libraryservice.model.*;
import ca.mcgill.ecse321.libraryservice.model.LibraryItem.ItemType;
import ca.mcgill.ecse321.libraryservice.dao.LibraryItemRepository;
import ca.mcgill.ecse321.libraryservice.dao.BorrowableItemRepository;
import ca.mcgill.ecse321.libraryservice.dao.UserAccountRepository;
import ca.mcgill.ecse321.libraryservice.dao.TransactionRepository;


@ExtendWith(MockitoExtension.class)
public class TestLibraryItemService {
	@Mock
	private LibraryItemRepository libraryItemDao;

	@Mock
	private BorrowableItemRepository borrowableItemDao;

	@Mock
	private UserAccountRepository userAccountDao;

	@Mock
	private TransactionRepository transactionDao;

	@InjectMocks
	private LibraryServiceService service;
	
	/* Library Item attributes */
	private static final boolean LIBRARY_ITEM_VIEWABLE = false;
	private static final boolean NEWSPAPER_VIEWABLE = false;

	private static final int BOOK_ISBN = 123;
	private static final String BOOK_CREATOR = "Jeff Joseph";
	private static final String BOOK_NAME = "History Of Java";
	private static final ItemType BOOK_TYPE = ItemType.Book;
	private static final Date BOOK_DATE = Date.valueOf("2009-03-15");

	private static final int INVALID_BOOK_ISBN = 8676;

	private static final int NEWSPAPER_ISBN = 124;
	private static final String NEWSPAPER_CREATOR = "Times";
	private static final String NEWSPAPER_NAME = "First edition";
	private static final ItemType NEWSPAPER_TYPE = ItemType.NewspaperArticle;
	private static final Date NEWSPAPER_DATE = Date.valueOf("1999-03-15");
	
	private static final int MUSIC_ISBN = 125;
	private static final String MUSIC_CREATOR = "Drake";
	private static final String MUSIC_NAME = "One Dance";
	private static final ItemType MUSIC_TYPE = ItemType.Music;
	private static final Date MUSIC_DATE = Date.valueOf("2018-04-25");

	private static final int MOVIE_ISBN = 126;
	private static final String MOVIE_CREATOR = "Denis Villeneuve";
	private static final String MOVIE_NAME = "Dune";
	private static final ItemType MOVIE_TYPE = ItemType.Movie;
	private static final Date MOVIE_DATE = Date.valueOf("2021-01-24");
	
	private static final String ROOM_NAME = "Room 1";
	private static final ItemType ROOM_TYPE = ItemType.Room;


	/**
	 * This method mocks the databases before each test execution so that fake
	 * database data can be used to test the service methods
	 * @author Amani Jammoul and Ramin Akhavan-Sarraf
	 */
	@BeforeEach
	public void setMockOutput() {
		lenient().when(libraryItemDao.findAll()).thenAnswer((InvocationOnMock invocation) -> {
			List<LibraryItem> allLibraryItems = new ArrayList<LibraryItem>();
			LibraryItem book = new LibraryItem(BOOK_NAME, BOOK_TYPE, BOOK_DATE, BOOK_CREATOR, LIBRARY_ITEM_VIEWABLE);
			book.setIsbn(BOOK_ISBN);
			
			
			LibraryItem music = new LibraryItem(MUSIC_NAME, MUSIC_TYPE, MUSIC_DATE, MUSIC_CREATOR, LIBRARY_ITEM_VIEWABLE);
			music.setIsbn(MUSIC_ISBN);

			LibraryItem movie = new LibraryItem(MOVIE_NAME, MOVIE_TYPE, MOVIE_DATE, MOVIE_CREATOR, LIBRARY_ITEM_VIEWABLE);
			movie.setIsbn(MOVIE_ISBN);

			LibraryItem newspaper = new LibraryItem(NEWSPAPER_NAME, NEWSPAPER_TYPE, NEWSPAPER_DATE, NEWSPAPER_CREATOR, NEWSPAPER_VIEWABLE);
			newspaper.setIsbn(NEWSPAPER_ISBN);

			LibraryItem room = new LibraryItem();
			room.setName(ROOM_NAME);
			room.setType(ROOM_TYPE);

			allLibraryItems.add(book); 
			allLibraryItems.add(newspaper); 
			allLibraryItems.add(room);
			allLibraryItems.add(music);
			allLibraryItems.add(movie);

			return allLibraryItems;
		});

		lenient().when(libraryItemDao.findByIsbn(anyInt())).thenAnswer((InvocationOnMock invocation) -> {
			if (invocation.getArgument(0).equals(BOOK_ISBN)) {
				LibraryItem book = new LibraryItem(BOOK_NAME, BOOK_TYPE, BOOK_DATE, BOOK_CREATOR, LIBRARY_ITEM_VIEWABLE);
				book.setIsbn(BOOK_ISBN);
	
				return book;
			}
			else if (invocation.getArgument(0).equals(MOVIE_ISBN)) {
				LibraryItem movie = new LibraryItem(MOVIE_NAME, MOVIE_TYPE, MOVIE_DATE, MOVIE_CREATOR, LIBRARY_ITEM_VIEWABLE);
				movie.setIsbn(MOVIE_ISBN);
	
				return movie;
			}
			else if (invocation.getArgument(0).equals(MUSIC_ISBN)) {
				LibraryItem music = new LibraryItem(MUSIC_NAME, MUSIC_TYPE, MUSIC_DATE, MUSIC_CREATOR, LIBRARY_ITEM_VIEWABLE);
				music.setIsbn(MUSIC_ISBN);
	
				return music;
			}
			else if (invocation.getArgument(0).equals(NEWSPAPER_ISBN)) {
				LibraryItem newspaper = new LibraryItem(NEWSPAPER_NAME, NEWSPAPER_TYPE, NEWSPAPER_DATE, NEWSPAPER_CREATOR, NEWSPAPER_VIEWABLE);
				newspaper.setIsbn(NEWSPAPER_ISBN);
	
				return newspaper;
			}
			else {
				return null;
			}
		});
		
		Answer<?> returnParameterAsAnswer = (InvocationOnMock invocation) -> {
			return invocation.getArgument(0);
		};
		lenient().when(libraryItemDao.save(any(LibraryItem.class))).thenAnswer(returnParameterAsAnswer);
	}
	
	/**
	 * This helper methods checks if the attributes of found library items fits
	 * what was expected depending on the type of item
	 * @param library item
	 * @author Ramin Akhavan-Sarraf
	 */
	public static void checkBasicLibraryItemList(LibraryItem item) {
		if(item.getType() == ItemType.Book) {
			assertEquals(item.getName(), BOOK_NAME);
			assertEquals(item.getType(), BOOK_TYPE);
			assertEquals(item.getCreator(), BOOK_CREATOR);
			assertEquals(item.getIsbn(), BOOK_ISBN);
			assertTrue(item.getDate().compareTo(BOOK_DATE) == 0);
		}
		else if (item.getType() == ItemType.Movie){
			assertEquals(item.getName(), MOVIE_NAME);
			assertEquals(item.getType(), MOVIE_TYPE);
			assertEquals(item.getCreator(), MOVIE_CREATOR);
			assertEquals(item.getIsbn(), MOVIE_ISBN);
			assertTrue(item.getDate().compareTo(MOVIE_DATE) == 0);
		}
		else if (item.getType() == ItemType.Music) {
			assertEquals(item.getName(), MUSIC_NAME);
			assertEquals(item.getType(), MUSIC_TYPE);
			assertEquals(item.getCreator(), MUSIC_CREATOR);
			assertEquals(item.getIsbn(), MUSIC_ISBN);
			assertTrue(item.getDate().compareTo(MUSIC_DATE) == 0);
		}
		else if (item.getType() == ItemType.NewspaperArticle){
			assertEquals(item.getName(), NEWSPAPER_NAME);
			assertEquals(item.getType(), NEWSPAPER_TYPE);
			assertEquals(item.getCreator(), NEWSPAPER_CREATOR);
			assertEquals(item.getIsbn(), NEWSPAPER_ISBN);
			assertTrue(item.getDate().compareTo(NEWSPAPER_DATE) == 0);
		} 
		else {
			assertEquals(item.getName(), ROOM_NAME);
			assertEquals(item.getType(), ROOM_TYPE);
		}
	}
	
	/**
	 * This method tests the getBooksFromAuthor service method to see if
	 * searching for books using the author name returns the correct list
	 * @throws Exception
	 * @author Ramin Akhavan-Sarraf
	 */
	@Test
	public void testGetBooksFromAuthor() throws Exception {
		List<LibraryItem> books = null;
		try {
			books = service.getBooksFromAuthor(BOOK_CREATOR);
		} catch (IllegalArgumentException e) {
			fail();
		}
		assertNotNull(books);
		checkBasicLibraryItemList(books.get(0));
	}

	/**
	 * This method tests the getBooksFromTitle service method to see if
	 * searching for books using the title name returns the correct list
	 * @throws Exception
	 * @author Ramin Akhavan-Sarraf
	 */
	@Test
	public void testGetBooksFromTitle() throws Exception {
		List<LibraryItem> books = null;
		try {
			books = service.getBooksFromTitle(BOOK_NAME);
		} catch (IllegalArgumentException e) {
			fail();
		}
		assertNotNull(books);
		checkBasicLibraryItemList(books.get(0));
	}

	/**
	 * This method tests the getBooksFromAuthorAndTitle service method to see if
	 * searching for a unique book using the author name and title returns the correct book
	 * @throws Exception
	 * @author Ramin Akhavan-Sarraf
	 */
	@Test
	public void testGetBooksFromAuthorAndTitle() throws Exception {
		LibraryItem book = null;
		try {
			book = service.getBookFromAuthorAndTitle(BOOK_CREATOR, BOOK_NAME);
		} catch (IllegalArgumentException e) {
			fail();
		}
		assertNotNull(book);
		checkBasicLibraryItemList(book);
	}

	/**
	 * This method tests the getMoviesFromAuthor service method to see if
	 * searching for movies using the author name returns the correct list
	 * @throws Exception
	 * @author Ramin Akhavan-Sarraf
	 */
	@Test
	public void testGetMoviesFromAuthor() throws Exception {
		List<LibraryItem> movies = null;
		try {
			movies = service.getMoviesFromDirector(MOVIE_CREATOR);
		} catch (IllegalArgumentException e) {
			fail();
		}
		assertNotNull(movies);
		checkBasicLibraryItemList(movies.get(0));
	}

	/**
	 * This method tests the getMoviesFromTitle service method to see if
	 * searching for movies using the title name returns the correct list
	 * @throws Exception
	 * @author Ramin Akhavan-Sarraf
	 */
	@Test
	public void testGetMoviesFromTitle() throws Exception {
		List<LibraryItem> movies = null;
		try {
			movies = service.getMoviesFromTitle(MOVIE_NAME);
		} catch (IllegalArgumentException e) {
			fail();
		}
		assertNotNull(movies);
		checkBasicLibraryItemList(movies.get(0));
	}

	/**
	 * This method tests the getMoviesFromDirectorAndTitle service method to see if
	 * searching for a unique movie using the director name and title returns the correct movie
	 * @throws Exception
	 * @author Ramin Akhavan-Sarraf
	 */
	@Test
	public void testGetMoviesFromDirectorAndTitle() throws Exception {
		LibraryItem movie = null;
		try {
			movie = service.getMovieFromDirectorAndTitle(MOVIE_CREATOR, MOVIE_NAME);
		} catch (IllegalArgumentException e) {
			fail();
		}
		assertNotNull(movie);
		checkBasicLibraryItemList(movie);
	}

	/**
	 * This method tests the getMusicsFromArtist service method to see if
	 * searching for musics using the artist name returns the correct list
	 * @throws Exception
	 * @author Ramin Akhavan-Sarraf
	 */
	@Test
	public void testGetMusicsFromArtist() throws Exception {
		List<LibraryItem> music = null;
		try {
			music = service.getMusicsFromArtist(MUSIC_CREATOR);
		} catch (IllegalArgumentException e) {
			fail();
		}
		assertNotNull(music);
		checkBasicLibraryItemList(music.get(0));
	}

	/**
	 * This method tests the getMusicsFromTitle service method to see if
	 * searching for musics using the title name returns the correct list
	 * @throws Exception
	 * @author Ramin Akhavan-Sarraf
	 */
	@Test
	public void testGetMusicsFromTitle() throws Exception {
		List<LibraryItem> music = null;
		try {
			music = service.getMusicsFromTitle(MUSIC_NAME);
		} catch (IllegalArgumentException e) {
			fail();
		}
		assertNotNull(music);
		checkBasicLibraryItemList(music.get(0));
	}

	/**
	 * This method tests the getMusicsFromDirectorAndTitle service method to see if
	 * searching for a unique music using the author and title name returns the correct music
	 * @throws Exception
	 * @author Ramin Akhavan-Sarraf
	 */
	@Test
	public void testGetMusicFromDirectorAndTitle() throws Exception {
		LibraryItem music = null;
		try {
			music = service.getMusicFromArtistAndTitle(MUSIC_CREATOR, MUSIC_NAME);
		} catch (IllegalArgumentException e) {
			fail();
		}
		assertNotNull(music);
		checkBasicLibraryItemList(music);
	}

	/**
	 * This method tests the getNewspapersFromWriter service method to see if
	 * searching for newspapers using the writer name returns the correct list
	 * @throws Exception
	 * @author Ramin Akhavan-Sarraf
	 */	
	@Test
	public void testGetNewspapersFromWriter() throws Exception {
		List<LibraryItem> newspapers = null;
		try {
			newspapers = service.getNewspaperFromWriter(NEWSPAPER_CREATOR);
		} catch (IllegalArgumentException e) {
			fail();
		}
		assertNotNull(newspapers);
		checkBasicLibraryItemList(newspapers.get(0));
	}

	/**
	 * This method tests the getNewspapersFromTitle service method to see if
	 * searching for newspapers using the title name returns the correct list
	 * @throws Exception
	 * @author Ramin Akhavan-Sarraf
	 */
	@Test
	public void testGetNewspapersFromTitle() throws Exception {
		List<LibraryItem> newspapers = null;
		try {
			newspapers = service.getNewspaperFromTitle(NEWSPAPER_NAME);
		} catch (IllegalArgumentException e) {
			fail();
		}
		assertNotNull(newspapers);
		checkBasicLibraryItemList(newspapers.get(0));
	}

	/**
	 * This method tests the getNewspaperFromWriterAndTitle service method to see if
	 * searching for a unique newspaper using the writer and title name returns the correct newspaper
	 * @throws Exception
	 * @author Ramin Akhavan-Sarraf
	 */
	@Test
	public void testGetNewspaperFromWriterAndTitle() throws Exception {
		LibraryItem newspaper = null;
		try {
			newspaper = service.getNewspaperFromWriterAndTitle(NEWSPAPER_CREATOR, NEWSPAPER_NAME);
		} catch (IllegalArgumentException e) {
			fail();
		}
		assertNotNull(newspaper);
		checkBasicLibraryItemList(newspaper);
	}
	
	/**
	 * This method tests the getAllBooks service method to see if
	 * all books of the library system are correctly returned
	 * @throws Exception
	 * @author Ramin Akhavan-Sarraf
	 */
	@Test
	public void testGetAllBooks() throws Exception {
		List<LibraryItem> books = null;
		try {
			books = service.getAllBooks();
		} catch (IllegalArgumentException e) {
			fail();
		}
		assertNotNull(books);
		assertEquals(1, books.size());
		LibraryItem book = books.get(0);
		checkBasicLibraryItemList(book);
		
	}

	/**
	 * This method tests the getAllNewspapers service method to see if
	 * all newspapers of the library system are correctly returned
	 * @throws Exception
	 * @author Ramin Akhavan-Sarraf
	 */
	@Test
	public void testGetAllNewspapers() throws Exception {
		List<LibraryItem> newspapers = null;
		try {
			newspapers = service.getAllNewspapers();
		} catch (IllegalArgumentException e) {
			fail();
		}
		assertNotNull(newspapers);
		assertEquals(1, newspapers.size());
		LibraryItem news = newspapers.get(0);
		checkBasicLibraryItemList(news);
		
	}

	/**
	 * This method tests the getAllMusic service method to see if
	 * all music of the library system are correctly returned
	 * @throws Exception
	 * @author Ramin Akhavan-Sarraf
	 */
	@Test
	public void testGetAllMusic() throws Exception {
		List<LibraryItem> music = null;
		try {
			music = service.getAllMusic();
		} catch (IllegalArgumentException e) {
			fail();
		}
		assertNotNull(music);
		assertEquals(1, music.size());
		LibraryItem song = music.get(0);
		checkBasicLibraryItemList(song);
		
	}

	/**
	 * This method tests the getAllMovies service method to see if
	 * all movies of the library system are correctly returned
	 * @throws Exception
	 * @author Ramin Akhavan-Sarraf
	 */
	@Test
	public void testGetAllMovies() throws Exception {
		List<LibraryItem> movies = null;
		try {
			movies = service.getAllMovies();
		} catch (IllegalArgumentException e) {
			fail();
		}
		assertNotNull(movies);
		assertEquals(1, movies.size());
		LibraryItem movie = movies.get(0);
		checkBasicLibraryItemList(movie);
		
	}

	/**
	 * This method tests the getAllRoomReservations service method to see if
	 * all room reservations of the library system are correctly returned
	 * @throws Exception
	 * @author Ramin Akhavan-Sarraf
	 */
	@Test
	public void testGetAllRooms() throws Exception {
		List<LibraryItem> rooms = null;
		try {
			rooms = service.getAllRoomReservations();
		} catch (IllegalArgumentException e) {
			fail();
		}
		assertNotNull(rooms);
		assertEquals(1, rooms.size());
		LibraryItem room = rooms.get(0);
		checkBasicLibraryItemList(room);
		
	}

	/**
	 * This method tests the getAllLibraryItems service method to see if
	 * all library items of the library system are correctly returned
	 * @throws Exception
	 * @author Ramin Akhavan-Sarraf
	 */
	@Test
	public void testGetAllLibraryItems() throws Exception {
		List<LibraryItem> libItems = null;
		try {
			libItems = service.getAllLibraryItems();
		} catch (IllegalArgumentException e) {
			fail();
		}
		assertNotNull(libItems);
		assertEquals(5, libItems.size());
		for(LibraryItem item: libItems) {
			checkBasicLibraryItemList(item);
		}
		
	}

	/**
	 * This method tests the getLibraryItemsFromCreator service method to see if
	 * searching for library items using the creator name returns the correct list
	 * @throws Exception
	 * @author Ramin Akhavan-Sarraf
	 */
	@Test
	public void testGetLibraryItemsFromCreator() throws Exception {
		List<LibraryItem> libItems = null;
		try {
			libItems = service.getLibraryItemsFromCreator(MOVIE_CREATOR);
		} catch (IllegalArgumentException e) {
			fail();
		}
		assertNotNull(libItems);
		assertEquals(1, libItems.size());
		checkBasicLibraryItemList(libItems.get(0));
		
	}

	/**
	 * This method tests the getLibraryItemsFromTitle service method to see if
	 * searching for library items using the title name returns the correct list
	 * @throws Exception
	 * @author Ramin Akhavan-Sarraf
	 */
	@Test
	public void testGetLibraryItemsFromTitle() throws Exception {
		List<LibraryItem> libItems = null;
		try {
			libItems = service.getLibraryItemsFromTitle(ROOM_NAME);
		} catch (IllegalArgumentException e) {
			fail();
		}
		assertNotNull(libItems);
		assertEquals(1, libItems.size());
		checkBasicLibraryItemList(libItems.get(0));
		
	}

	/**
	 * This method tests the getLibraryItemsFromCreatorAndTitle service method to see if
	 * searching for a unique library item using the creator and title name returns the correct list
	 * @throws Exception
	 * @author Ramin Akhavan-Sarraf
	 */
	@Test
	public void testGetLibraryItemsFromCreatorAndTitle() throws Exception {
		List<LibraryItem> libItems = null;
		try {
			libItems = service.getLibraryItemFromCreatorAndTitle(MOVIE_CREATOR, MOVIE_NAME);
		} catch (IllegalArgumentException e) {
			fail();
		}
		assertNotNull(libItems);
		assertEquals(1, libItems.size());
		checkBasicLibraryItemList(libItems.get(0));
		
	}

	/**
	 * This tests verifies if a library item is made in the database
	 * 
	 * @author Ramin Akhavan-Sarraf
	 * @throws Exception
	 */
	@Test
	public void testCreateLibraryItemSuccess() throws Exception {
		
		LibraryItem libraryItem = null;
		
		try {
			libraryItem = service.createLibraryItem(BOOK_NAME, BOOK_TYPE, BOOK_DATE, BOOK_CREATOR, LIBRARY_ITEM_VIEWABLE);
		}
		catch (IllegalArgumentException e) {
			fail();
			
		}
		assertNotNull(libraryItem);
		assertEquals(libraryItem.getName(), BOOK_NAME);
		assertEquals(libraryItem.getType(), BOOK_TYPE);
		assertEquals(libraryItem.getCreator(), BOOK_CREATOR);
		assertEquals(libraryItem.getType(), BOOK_TYPE);
		assertTrue(libraryItem.getDate().compareTo(BOOK_DATE) == 0);
			
	}

	/**
	 * This tests verifies that a library item is not made in the database due to
	 * an empty name field
	 * 
	 * @author Ramin Akhavan-Sarraf
	 * @throws Exception
	 */
	@Test
	public void testCreateLibraryItemNoNameFail() throws Exception {
		String error = "";
		LibraryItem libraryItem = null;
		
		try {
			libraryItem = service.createLibraryItem(null, BOOK_TYPE, BOOK_DATE, BOOK_CREATOR, LIBRARY_ITEM_VIEWABLE);
		}
		catch (IllegalArgumentException e) {
			error = e.getMessage();
			
		}
		assertEquals(error, "Name cannot be empty!");
			
	}

	/**
	 * This tests verifies that a library item is not made in the database due to
	 * an empty type field
	 * 
	 * @author Ramin Akhavan-Sarraf
	 * @throws Exception
	 */
	@Test
	public void testCreateLibraryItemNoTypeFail() throws Exception {
		String error = "";
		LibraryItem libraryItem = null;
		
		try {
			libraryItem = service.createLibraryItem(BOOK_NAME, null, BOOK_DATE, BOOK_CREATOR, LIBRARY_ITEM_VIEWABLE);
		}
		catch (IllegalArgumentException e) {
			error = e.getMessage();
			
		}
		assertEquals(error, "Item type cannot be empty!");
			
	}

	/**
	 * This tests verifies that a library item was deleted from the database
	 * 
	 * @author Ramin Akhavan-Sarraf
	 * @throws Exception
	 */
	@Test
	public void testDeleteLibraryItemSuccess() throws Exception {
		boolean libraryItemDelete = false;
		try {
			libraryItemDelete = service.deleteLibraryItem(BOOK_ISBN);
		
		}
		catch (IllegalArgumentException e) {
			fail();
			
		}
		assertTrue(libraryItemDelete);
			
	}

	/**
	 * This tests verifies that a library item was not deleted from the database
	 * 
	 * @author Ramin Akhavan-Sarraf
	 * @throws Exception
	 */
	@Test
	public void testDeleteLibraryItemFail() throws Exception {
		String error = "";
		boolean libraryItemDelete = false;
		try {
			libraryItemDelete = service.deleteLibraryItem(INVALID_BOOK_ISBN);
		
		}
		catch (Exception e) {
			error = e.getMessage();
			
		}
		assertEquals(error, "This isbn does not exist as a Library Item");
			
	}
}
