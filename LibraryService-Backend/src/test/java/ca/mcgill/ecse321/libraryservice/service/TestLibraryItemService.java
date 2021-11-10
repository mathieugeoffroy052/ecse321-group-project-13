package ca.mcgill.ecse321.libraryservice.service;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalTime;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

import ca.mcgill.ecse321.libraryservice.model.*;
import ca.mcgill.ecse321.libraryservice.model.BorrowableItem.ItemState;
import ca.mcgill.ecse321.libraryservice.model.LibraryItem.ItemType;
import ca.mcgill.ecse321.libraryservice.dao.*;


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
		// Whenever anything is saved, just return the parameter object
		Answer<?> returnParameterAsAnswer = (InvocationOnMock invocation) -> {
			return invocation.getArgument(0);
		};
		lenient().when(libraryItemDao.save(any(LibraryItem.class))).thenAnswer(returnParameterAsAnswer);
	}
	
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
	
	@Test
	public void testGetNewspapersFromWritor() throws Exception {
		List<LibraryItem> newspapers = null;
		try {
			newspapers = service.getNewspaperFromWriter(NEWSPAPER_CREATOR);
		} catch (IllegalArgumentException e) {
			fail();
		}
		assertNotNull(newspapers);
		checkBasicLibraryItemList(newspapers.get(0));
	}

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
}
