package ca.mcgill.ecse321.libraryservice.service;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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
import ca.mcgill.ecse321.libraryservice.model.BorrowableItem.ItemState;
import ca.mcgill.ecse321.libraryservice.model.LibraryItem.ItemType;
import ca.mcgill.ecse321.libraryservice.dao.LibraryItemRepository;
import ca.mcgill.ecse321.libraryservice.dao.BorrowableItemRepository;
import ca.mcgill.ecse321.libraryservice.dao.UserAccountRepository;
import ca.mcgill.ecse321.libraryservice.dao.TransactionRepository;


@ExtendWith(MockitoExtension.class)
public class TestBorrowableItemService {
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
	private static final boolean LIBRARY_ITEM_VIEWABLE = true;
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

	/* Borrowable Item attributes*/
	private static final int BOOK_BARCODENUMBER = 123456;
	private static final int INVALID_BOOK_BARCODENUMBER = 11111;

	private static final ItemState AVAILABLE_STATE = ItemState.Available;

	private static final ItemState BOOK_STATE = AVAILABLE_STATE;
	
	

	/**
	 * This method is used to mock the database outputs, so that we can use
	 * fake data to test the methods. Specifically the borrowableItem table
	 * and library item table are mocked
	 * @author Ramin Akhavan-Sarraf
	 */
	@BeforeEach
	public void setMockOutput() {
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
		
		lenient().when(borrowableItemDao.findByLibraryItem(any(LibraryItem.class))).thenAnswer((InvocationOnMock invocation) -> {
			if (invocation.getArgument(0).toString().contains(BOOK_NAME)) {
				LibraryItem book = new LibraryItem(BOOK_NAME, BOOK_TYPE, BOOK_DATE, BOOK_CREATOR, LIBRARY_ITEM_VIEWABLE);
				book.setIsbn(BOOK_ISBN);
				List<BorrowableItem> allBorrowableItems = new ArrayList<BorrowableItem>();
				BorrowableItem borrowableBook = new BorrowableItem(BOOK_STATE, book);
				borrowableBook.setBarCodeNumber(BOOK_BARCODENUMBER);
				allBorrowableItems.add(borrowableBook); 
				return allBorrowableItems;
			}
			else {
				return null;
			}
		});

		lenient().when(borrowableItemDao.findBorrowableItemByBarCodeNumber(anyInt())).thenAnswer((InvocationOnMock invocation) -> {
			if (invocation.getArgument(0).equals(BOOK_BARCODENUMBER)) {
				LibraryItem book = new LibraryItem(BOOK_NAME, BOOK_TYPE, BOOK_DATE, BOOK_CREATOR, LIBRARY_ITEM_VIEWABLE);
				book.setIsbn(BOOK_ISBN);
				BorrowableItem borrowableBook = new BorrowableItem(BOOK_STATE, book);
				borrowableBook.setBarCodeNumber(BOOK_BARCODENUMBER);
				return borrowableBook;
			}
			else {
				return null;
			}
		});
		
		lenient().when(borrowableItemDao.findAll()).thenAnswer((InvocationOnMock invocation) -> {
			List<BorrowableItem> allBorrowableItems = new ArrayList<BorrowableItem>();
			LibraryItem book = new LibraryItem(BOOK_NAME, BOOK_TYPE, BOOK_DATE, BOOK_CREATOR, LIBRARY_ITEM_VIEWABLE);
			book.setIsbn(BOOK_ISBN);
			BorrowableItem borrowableBook = new BorrowableItem(BOOK_STATE, book);
			borrowableBook.setBarCodeNumber(BOOK_BARCODENUMBER);
			allBorrowableItems.add(borrowableBook); 

			return allBorrowableItems;
		});
		
		Answer<?> returnParameterAsAnswer = (InvocationOnMock invocation) -> {
			return invocation.getArgument(0);
		};
		lenient().when(libraryItemDao.save(any(LibraryItem.class))).thenAnswer(returnParameterAsAnswer);
		lenient().when(borrowableItemDao.save(any(BorrowableItem.class))).thenAnswer(returnParameterAsAnswer);

	}
	
	/**
	 * This helper method checks all parameters of the appropriate library
	 * item based on the type of item
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
	 * Tests the getBorrowableItemsFromIsbn service method to see if the correct 
	 * items are returned when looking for items with the same isbn
	 * @throws Exception
	 * @author Ramin Akhavan-Sarraf
	 */
	@Test
	public void testGetBorrowableItemsFromIsbn() throws Exception {
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
		checkBasicLibraryItemList(book_lib_item);
		
	}

	/**
	 * Tests the getBorrowableItemsFromBarCodeNumber method to see if the correct 
	 * item is returned when looking for a unique bar code number
	 * @throws Exception
	 * @author Ramin Akhavan-Sarraf
	 */
	@Test
	public void testGetBorrowableItemFromBarCodeNumber() throws Exception {
		BorrowableItem book = null;
		try {
			book = service.getBorrowableItemFromBarCodeNumber(BOOK_BARCODENUMBER);
		} catch (IllegalArgumentException e) {
			fail();
		}
		assertNotNull(book);
		assertEquals(book.getBarCodeNumber(), BOOK_BARCODENUMBER);
		assertEquals(book.getState(), BOOK_STATE);
		LibraryItem book_lib_item = book.getLibraryItem();
		checkBasicLibraryItemList(book_lib_item);
		
	}
	
	/**
	 * This tests verifies if a borrowable item is made in the database
	 * 
	 * @author Ramin Akhavan-Sarraf
	 * @throws Exception
	 */
	@Test
	public void testCreateBorrowableItemSuccess() throws Exception {
		
		BorrowableItem borrowableItem = null;
		LibraryItem libraryItem = null;
		
		try {
			libraryItem = service.createLibraryItem(BOOK_NAME, BOOK_TYPE.toString(), BOOK_DATE, BOOK_CREATOR, LIBRARY_ITEM_VIEWABLE);
			libraryItem.setIsbn(BOOK_ISBN);
			borrowableItem = service.createBorrowableItem(BOOK_STATE.toString(), libraryItem);
		}
		catch (IllegalArgumentException e) {
			fail();
			
		}
		assertNotNull(borrowableItem);
		assertEquals(borrowableItem.getState(), BOOK_STATE);
		checkBasicLibraryItemList(borrowableItem.getLibraryItem());
			
	}

	/**
	 * This tests verifies if a borrowable item is made in the database
	 * 
	 * @author Ramin Akhavan-Sarraf
	 * @throws Exception
	 */
	@Test
	public void testCreateBorrowableItemNoStateFail() throws Exception {
		
		String error = "";
		LibraryItem libraryItem = null;
		
		try {
			libraryItem = service.createLibraryItem(BOOK_NAME, BOOK_TYPE.toString(), BOOK_DATE, BOOK_CREATOR, LIBRARY_ITEM_VIEWABLE);
			libraryItem.setIsbn(BOOK_ISBN);
			service.createBorrowableItem(null, libraryItem);
		}
		catch (IllegalArgumentException e) {
			error = e.getMessage();
			
		}
		assertEquals(error, "Item state cannot be empty!");
			
	}

	/**
	 * This tests verifies if a borrowable item is made in the database
	 * 
	 * @author Ramin Akhavan-Sarraf
	 * @throws Exception
	 */
	@Test
	public void testCreateBorrowableItemNoLibraryItemFail() throws Exception {
		
		String error = "";

		try {
			service.createBorrowableItem(BOOK_STATE.toString(), null);
		}
		catch (IllegalArgumentException e) {
			error = e.getMessage();
			
		}
		assertEquals(error, "Library item cannot be empty!");
			
	}


	/**
	 * This tests verifies that a borrowable item was deleted from the database
	 * 
	 * @author Ramin Akhavan-Sarraf
	 * @throws Exception
	 */
	@Test
	public void testDeleteBorrowableItemSuccess() throws Exception {
		boolean borrowableDelete = false;
		try {
			borrowableDelete = service.deleteBorrowableItem(BOOK_BARCODENUMBER);
		
		}
		catch (IllegalArgumentException e) {
			fail();
			
		}
		assertTrue(borrowableDelete);
			
	}

	/**
	 * This tests verifies that a borrowable item was not deleted from the database
	 * 
	 * @author Ramin Akhavan-Sarraf
	 * @throws Exception
	 */
	@Test
	public void testDeleteBorrowableItemFail() throws Exception {
		String error = "";
		boolean borrowableDelete = false;
		try {
			borrowableDelete = service.deleteBorrowableItem(INVALID_BOOK_BARCODENUMBER);
		
		}
		catch (Exception e) {
			error = e.getMessage();
			
		}
		assertEquals(error, "This bar code number does not exist as a Borrowable Item");
		assertFalse(borrowableDelete);
			
	}
}