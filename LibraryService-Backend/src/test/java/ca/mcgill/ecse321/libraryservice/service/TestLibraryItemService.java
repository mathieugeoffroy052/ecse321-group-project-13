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

	private static final int MOVIE_ISBN = 125;
	private static final String MOVIE_CREATOR = "Denis Villeneuve";
	private static final String MOVIE_NAME = "Dune";
	private static final ItemType MOVIE_TYPE = ItemType.Movie;
	private static final Date MOVIE_DATE = Date.valueOf("2021-01-24");

	private static final String ROOM_NAME = "Room 1";
	private static final ItemType ROOM_TYPE = ItemType.Room;

	/* Borrowable Item attributes*/
	private static final int BOOK_BARCODENUMBER = 123456;
	private static final int NEWSPAPER_BARCODENUMBER = 1111111;
    private static final int ROOM_BARCODENUMBER = 999999;
	private static final int MOVIE_BARCODENUMBER = 1212121;
    private static final int MUSIC_BARCODENUMBER = 989898;

	private static final ItemState AVAILABLE_STATE = ItemState.Available;
	private static final ItemState BORROWED_STATE = ItemState.Borrowed;

	private static final ItemState BOOK_STATE = BORROWED_STATE;
	/* Patron attributes*/
	private static final int VALID_PATRON_USER_ID = 8;
	private static final int INVALID_PATRON_USER_ID = 7;
	private static final String PATRON_FIRST_NAME = "Jimmy";
	private static final String PATRON_LAST_NAME = "John";
	private static final boolean PATRON_VALIDATED = true;
	private static final boolean ONLINE = true;


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

		lenient().when(borrowableItemDao.findAll()).thenAnswer((InvocationOnMock invocation) -> {
			List<BorrowableItem> allBorrowableItems = new ArrayList<BorrowableItem>();
			LibraryItem book = new LibraryItem(BOOK_NAME, BOOK_TYPE, BOOK_DATE, BOOK_CREATOR, LIBRARY_ITEM_VIEWABLE);
			book.setIsbn(BOOK_ISBN);
			BorrowableItem borrowableBook = new BorrowableItem(BOOK_STATE, book);

			allBorrowableItems.add(borrowableBook); 

			return allBorrowableItems;
		});
		// Whenever anything is saved, just return the parameter object
		Answer<?> returnParameterAsAnswer = (InvocationOnMock invocation) -> {
			return invocation.getArgument(0);
		};
		lenient().when(libraryItemDao.save(any(LibraryItem.class))).thenAnswer(returnParameterAsAnswer);
		lenient().when(borrowableItemDao.save(any(BorrowableItem.class))).thenAnswer(returnParameterAsAnswer);
		lenient().when(userAccountDao.save(any(UserAccount.class))).thenAnswer(returnParameterAsAnswer);
		lenient().when(transactionDao.save(any(Transaction.class))).thenAnswer(returnParameterAsAnswer);
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
		//checkResultLibraryItemList(itemReserveTrans, bookItem, pAccount);
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
		assertEquals(book.getName(), BOOK_NAME);
		assertEquals(book.getType(), BOOK_TYPE);
		assertEquals(book.getCreator(), BOOK_CREATOR);
		assertEquals(book.getIsbn(), BOOK_ISBN);
		assertTrue(book.getDate().compareTo(BOOK_DATE) == 0);
		
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
		assertEquals(news.getName(), NEWSPAPER_NAME);
		assertEquals(news.getType(), NEWSPAPER_TYPE);
		assertEquals(news.getCreator(), NEWSPAPER_CREATOR);
		assertEquals(news.getIsbn(), NEWSPAPER_ISBN);
		assertTrue(news.getDate().compareTo(NEWSPAPER_DATE) == 0);
		
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
		assertEquals(song.getName(), MUSIC_NAME);
		assertEquals(song.getType(), MUSIC_TYPE);
		assertEquals(song.getCreator(), MUSIC_CREATOR);
		assertEquals(song.getIsbn(), MUSIC_ISBN);
		assertTrue(song.getDate().compareTo(MUSIC_DATE) == 0);
		
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
		assertEquals(movie.getName(), MOVIE_NAME);
		assertEquals(movie.getType(), MOVIE_TYPE);
		assertEquals(movie.getCreator(), MOVIE_CREATOR);
		assertEquals(movie.getIsbn(), MOVIE_ISBN);
		assertTrue(movie.getDate().compareTo(MOVIE_DATE) == 0);
		
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
		assertEquals(room.getName(), ROOM_NAME);
		assertEquals(room.getType(), ROOM_TYPE);
		
	}

	@Test
	public void testGetLibraryItemFromIsbn() throws Exception {
		List<BorrowableItem> books = null;
		try {
			books = service.getBorrowableItemsFromItemIsbn(BOOK_ISBN);
		} catch (IllegalArgumentException e) {
			fail();
		}
		assertNotNull(books);
		assertEquals(1, books.size());
		BorrowableItem book = books.get(0);
		assertEquals(book.getBarCodeNumber(), BOOK_BARCODENUMBER);
		assertEquals(book.getState(), BOOK_STATE);
		LibraryItem book_lib_item = book.getLibraryItem();
		assertEquals(book_lib_item.getName(), BOOK_NAME);
		assertEquals(book_lib_item.getType(), BOOK_TYPE);
		assertEquals(book_lib_item.getCreator(), BOOK_CREATOR);
		assertEquals(book_lib_item.getIsbn(), BOOK_ISBN);
		assertTrue(book_lib_item.getDate().compareTo(BOOK_DATE) == 0);
		
	}
}
