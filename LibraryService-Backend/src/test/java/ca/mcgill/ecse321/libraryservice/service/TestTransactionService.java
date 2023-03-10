package ca.mcgill.ecse321.libraryservice.service;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
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
import ca.mcgill.ecse321.libraryservice.model.Transaction.TransactionType;
import ca.mcgill.ecse321.libraryservice.dao.LibraryItemRepository;
import ca.mcgill.ecse321.libraryservice.dao.BorrowableItemRepository;
import ca.mcgill.ecse321.libraryservice.dao.UserAccountRepository;
import ca.mcgill.ecse321.libraryservice.dao.TransactionRepository;

@ExtendWith(MockitoExtension.class)
public class TestTransactionService {
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

	private static final int BOOK_ISBN = 123;
	private static final String BOOK_CREATOR = "Jeff Joseph";
	private static final String BOOK_NAME = "History Of Java";
	private static final ItemType BOOK_TYPE = ItemType.Book;
	private static final Date BOOK_DATE = Date.valueOf("2009-03-15");

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

	/* Borrowable Item attributes */
	private static final int BOOK_BARCODENUMBER = 123456;
	private static final int NEWSPAPER_BARCODENUMBER = 1111111;
	private static final int ROOM_BARCODENUMBER = 999999;
	private static final int MOVIE_BARCODENUMBER = 1212121;
	private static final int MUSIC_BARCODENUMBER = 989898;

	private static final ItemState AVAILABLE_STATE = ItemState.Available;
	private static final ItemState BORROWED_STATE = ItemState.Borrowed;

	/* Patron attributes */
	private static final int VALID_PATRON_USER_ID = 8;
	private static final int INVALID_PATRON_USER_ID = 7;
	private static final String PATRON_FIRST_NAME = "Jimmy";
	private static final String PATRON_LAST_NAME = "John";
	private static final boolean PATRON_VALIDATED = true;
	private static final boolean ONLINE = true;

	/* Transaction attributes */
	private static final int TRANSACTION_ID = 777;
	private static final Date TRANSACTION_DATE = Date.valueOf("2022-03-15");;
	private static final TransactionType TRANSACTION_TYPE = TransactionType.Borrowing;

	private static final int TRANSACTION_ID_2 = 888;
	private static final Date TRANSACTION_DATE_2 = Date.valueOf("2021-06-17");;
	private static final TransactionType TRANSACTION_TYPE_2 = TransactionType.ItemReservation;

	private static final int TRANSACTION_ID_3 = 999;
	private static final Date TRANSACTION_DATE_3 = Date.valueOf("2020-05-16");;
	private static final TransactionType TRANSACTION_TYPE_3 = TransactionType.Waitlist;

	/**
	 * This method mocks the database method return values so that we can use fake
	 * data to test the service methods that interact with the database
	 * 
	 * @author Amani Jammoul and Ramin Akhavan-Sarraf
	 */
	@BeforeEach
	public void setMockOutput() {
		lenient().when(borrowableItemDao.findBorrowableItemByBarCodeNumber(anyInt()))
				.thenAnswer((InvocationOnMock invocation) -> {
					if (invocation.getArgument(0).equals(BOOK_BARCODENUMBER)) {
						LibraryItem book = new LibraryItem(BOOK_NAME, BOOK_TYPE, BOOK_DATE, BOOK_CREATOR, LIBRARY_ITEM_VIEWABLE);
						book.setIsbn(BOOK_ISBN);
						BorrowableItem bookItem = new BorrowableItem(AVAILABLE_STATE, book);
						return bookItem;
					} else if (invocation.getArgument(0).equals(NEWSPAPER_BARCODENUMBER)) {
						LibraryItem newspaper = new LibraryItem(NEWSPAPER_NAME, NEWSPAPER_TYPE, NEWSPAPER_DATE, NEWSPAPER_CREATOR, LIBRARY_ITEM_VIEWABLE);
						BorrowableItem newspaperItem = new BorrowableItem(AVAILABLE_STATE, newspaper);
						return newspaperItem;
					} else if (invocation.getArgument(0).equals(ROOM_BARCODENUMBER)) {
						LibraryItem room = new LibraryItem();
						room.setName(ROOM_NAME);
						room.setType(ROOM_TYPE);
						BorrowableItem roomItem = new BorrowableItem(AVAILABLE_STATE, room);
						return roomItem;
					} else {
						return null;
					}
				});
		lenient().when(userAccountDao.findUserAccountByUserID(anyInt())).thenAnswer((InvocationOnMock invocation) -> {
			if (invocation.getArgument(0).equals(VALID_PATRON_USER_ID)) {
				Patron pAccount = new Patron();
				pAccount.setFirstName(PATRON_FIRST_NAME);
				pAccount.setLastName(PATRON_LAST_NAME);
				pAccount.setValidatedAccount(PATRON_VALIDATED);
				pAccount.setOnlineAccount(ONLINE);
				return pAccount;
			} else if (invocation.getArgument(0).equals(INVALID_PATRON_USER_ID)) {
				Patron pAccount = new Patron();
				pAccount.setFirstName(PATRON_FIRST_NAME);
				pAccount.setLastName(PATRON_LAST_NAME);
				pAccount.setValidatedAccount(!PATRON_VALIDATED);
				pAccount.setOnlineAccount(ONLINE);
				return pAccount;
			} else {
				return null;
			}
		});
		lenient().when(transactionDao.findByUserAccount(any(UserAccount.class)))
				.thenAnswer((InvocationOnMock invocation) -> {
					if (invocation.getArgument(0).toString().contains(PATRON_FIRST_NAME)) {
						List<Transaction> transactions = new ArrayList<>();
						Patron pAccount = new Patron();
						pAccount.setFirstName(PATRON_FIRST_NAME);
						pAccount.setLastName(PATRON_LAST_NAME);
						pAccount.setValidatedAccount(PATRON_VALIDATED);
						pAccount.setOnlineAccount(ONLINE);

						LibraryItem book = new LibraryItem(BOOK_NAME, BOOK_TYPE, BOOK_DATE, BOOK_CREATOR, LIBRARY_ITEM_VIEWABLE);
						book.setIsbn(BOOK_ISBN);
						BorrowableItem bookItem = new BorrowableItem(AVAILABLE_STATE, book);
						bookItem.setBarCodeNumber(BOOK_BARCODENUMBER);

						LibraryItem music = new LibraryItem(MUSIC_NAME, MUSIC_TYPE, MUSIC_DATE, MUSIC_CREATOR, LIBRARY_ITEM_VIEWABLE);
						music.setIsbn(MUSIC_ISBN);
						BorrowableItem musicItem = new BorrowableItem(AVAILABLE_STATE, music);
						musicItem.setBarCodeNumber(MUSIC_BARCODENUMBER);

						LibraryItem movie = new LibraryItem(MOVIE_NAME, MOVIE_TYPE, MOVIE_DATE, MOVIE_CREATOR, LIBRARY_ITEM_VIEWABLE);
						music.setIsbn(MOVIE_ISBN);
						BorrowableItem movieItem = new BorrowableItem(AVAILABLE_STATE, movie);
						movieItem.setBarCodeNumber(MOVIE_BARCODENUMBER);

						Transaction transaction = new Transaction();
						transaction.setDeadline(TRANSACTION_DATE);
						transaction.setBorrowableItem(bookItem);
						transaction.setUserAccount(pAccount);
						transaction.setTransactionType(TRANSACTION_TYPE);
						transaction.setTransactionID(TRANSACTION_ID);
						transactions.add(transaction);

						Transaction transaction2 = new Transaction();
						transaction2.setDeadline(TRANSACTION_DATE_2);
						transaction2.setBorrowableItem(musicItem);
						transaction2.setUserAccount(pAccount);
						transaction2.setTransactionType(TRANSACTION_TYPE_2);
						transaction2.setTransactionID(TRANSACTION_ID_2);
						transactions.add(transaction2);

						Transaction transaction3 = new Transaction();
						transaction3.setDeadline(TRANSACTION_DATE_3);
						transaction3.setBorrowableItem(movieItem);
						transaction3.setUserAccount(pAccount);
						transaction3.setTransactionType(TRANSACTION_TYPE_3);
						transaction3.setTransactionID(TRANSACTION_ID_3);
						transactions.add(transaction3);

						return transactions;
					}

				else {
						return null;
					}
				});

		Answer<?> returnParameterAsAnswer = (InvocationOnMock invocation) -> {
			return invocation.getArgument(0);
		};
		lenient().when(libraryItemDao.save(any(LibraryItem.class))).thenAnswer(returnParameterAsAnswer);
		lenient().when(borrowableItemDao.save(any(BorrowableItem.class))).thenAnswer(returnParameterAsAnswer);
		lenient().when(userAccountDao.save(any(UserAccount.class))).thenAnswer(returnParameterAsAnswer);
		lenient().when(transactionDao.save(any(Transaction.class))).thenAnswer(returnParameterAsAnswer);
	}

	/* ***************** ITEM RESERVATION TESTS ********************* */
	/**
	 * Success case
	 * 
	 * @author Amani Jammoul
	 */
	@Test
	public void testCreateItemReserveTransactionSuccessful() {
		BorrowableItem bookItem = borrowableItemDao.findBorrowableItemByBarCodeNumber(BOOK_BARCODENUMBER);

		Patron pAccount = (Patron) userAccountDao.findUserAccountByUserID(VALID_PATRON_USER_ID);

		Transaction itemReserveTrans = null;

		try {
			itemReserveTrans = service.createItemReserveTransaction(bookItem, pAccount);
		} catch (IllegalArgumentException e) {
			fail();
		}
		assertNotNull(itemReserveTrans);
		checkResultTransaction(itemReserveTrans, bookItem, pAccount);
	}

	/**
	 * Failure : item and account objects are null
	 * 
	 * @author Amani Jammoul
	 */
	@Test
	public void testCreateItemReserveTransactionNull() {
		BorrowableItem bookItem = null;
		Patron pAccount = null;

		Transaction itemReserveTrans = null;

		String error = null;

		try {
			itemReserveTrans = service.createItemReserveTransaction(bookItem, pAccount);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertNull(itemReserveTrans);
		assertEquals(error, "Item cannot be null! " + "Account cannot be null!");
	}

	/**
	 * Failure : patron has an unvalidated account
	 * 
	 * @author Amani Jammoul
	 */
	@Test
	public void testCreateItemReserveTransactionInvalidPatron() {
		BorrowableItem bookItem = borrowableItemDao.findBorrowableItemByBarCodeNumber(BOOK_BARCODENUMBER);

		Patron pAccount = (Patron) userAccountDao.findUserAccountByUserID(INVALID_PATRON_USER_ID);

		Transaction itemReserveTrans = null;

		String error = null;

		try {
			itemReserveTrans = service.createItemReserveTransaction(bookItem, pAccount);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertNull(itemReserveTrans);
		assertEquals(error, "User account is unvalidated, cannot complete item reservation transaction!");
	}

	/**
	 * Failure : trying to borrow a newspaper
	 * 
	 * @author Amani Jammoul
	 */
	@Test
	public void testCreateItemReserveTransactionNewspaper() {
		BorrowableItem newspaperItem = borrowableItemDao.findBorrowableItemByBarCodeNumber(NEWSPAPER_BARCODENUMBER);

		Patron pAccount = (Patron) userAccountDao.findUserAccountByUserID(VALID_PATRON_USER_ID);

		Transaction itemReserveTrans = null;

		String error = null;

		try {
			itemReserveTrans = service.createItemReserveTransaction(newspaperItem, pAccount);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertNull(itemReserveTrans);
		assertEquals(error, "Newspapers cannot be reserved, only viewed ; item reservation transaction not complete!");
	}

	/**
	 * Failure : trying to reverse an item that is unavailable
	 * 
	 * @author Amani Jammoul
	 */
	@Test
	public void testCreateItemReserveTransactionUnavailable() {
		BorrowableItem bookItem = borrowableItemDao.findBorrowableItemByBarCodeNumber(BOOK_BARCODENUMBER);
		bookItem.setState(ItemState.Borrowed); // make book unavailable for reservation

		Patron pAccount = (Patron) userAccountDao.findUserAccountByUserID(VALID_PATRON_USER_ID);

		Transaction itemReserveTrans = null;

		String error = null;

		try {
			itemReserveTrans = service.createItemReserveTransaction(bookItem, pAccount);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertNull(itemReserveTrans);
		assertEquals(error, "Item is not available for reservation, please try waitlist!");
	}

	/* ***************** ROOM RESERVATION TESTS ********************* */
	/**
	 * Success case
	 * 
	 * @author Amani Jammoul
	 */
	@Test
	public void testCreateRoomReserveTransactionSuccessful() {
		BorrowableItem roomItem = borrowableItemDao.findBorrowableItemByBarCodeNumber(ROOM_BARCODENUMBER);
		Patron pAccount = (Patron) userAccountDao.findUserAccountByUserID(VALID_PATRON_USER_ID);

		Transaction itemReserveTrans = null;

		Date reservationDate = Date.valueOf("2021-12-30");

		try {
			itemReserveTrans = service.createRoomReserveTransaction(roomItem, pAccount, reservationDate);
		} catch (IllegalArgumentException e) {
			fail();
		}
		assertNotNull(itemReserveTrans);
		checkResultTransaction(itemReserveTrans, roomItem, pAccount);
	}

	/**
	 * Failure : item and account objects are null
	 * 
	 * @author Amani Jammoul
	 */
	@Test
	public void testCreateRoomReserveTransactionNull() {
		BorrowableItem roomItem = null;
		Patron pAccount = null;

		Transaction roomReserveTrans = null;

		Date reservationDate = Date.valueOf("2021-12-30");

		String error = null;

		try {
			roomReserveTrans = service.createRoomReserveTransaction(roomItem, pAccount, reservationDate);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertNull(roomReserveTrans);
		assertEquals(error, "Item cannot be null! " + "Account cannot be null!");
	}

	/**
	 * Failure : patron has an unvalidated account
	 * 
	 * @author Amani Jammoul
	 */
	@Test
	public void testCreateItemRoomTransactionInvalidPatron() {
		BorrowableItem roomItem = borrowableItemDao.findBorrowableItemByBarCodeNumber(ROOM_BARCODENUMBER);

		Patron pAccount = (Patron) userAccountDao.findUserAccountByUserID(INVALID_PATRON_USER_ID);

		Transaction roomReserveTrans = null;

		Date reservationDate = Date.valueOf("2021-12-30");

		String error = null;

		try {
			roomReserveTrans = service.createRoomReserveTransaction(roomItem, pAccount, reservationDate);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertNull(roomReserveTrans);
		assertEquals(error, "User account is unvalidated, cannot complete room reservation transaction!");
	}

	/* ***************** ITEM BORROW TESTS ********************* */
	/**
	 * Success case
	 * 
	 * @author Amani Jammoul
	 */
	@Test
	public void testCreateItemBorrowTransactionSuccessful() {
		BorrowableItem bookItem = borrowableItemDao.findBorrowableItemByBarCodeNumber(BOOK_BARCODENUMBER);
		Patron pAccount = (Patron) userAccountDao.findUserAccountByUserID(VALID_PATRON_USER_ID);

		Transaction itemReserveTrans = null;

		try {
			itemReserveTrans = service.createItemBorrowTransaction(bookItem, pAccount);
		} catch (IllegalArgumentException e) {
			fail();
		}
		assertNotNull(itemReserveTrans);
		checkResultTransaction(itemReserveTrans, bookItem, pAccount);
	}

	/**
	 * Failure : item and account objects are null
	 * 
	 * @author Amani Jammoul
	 */
	@Test
	public void testCreateItemBorrowTransactionNull() {
		BorrowableItem bookItem = null;
		Patron pAccount = null;

		Transaction itemBorrowTrans = null;

		String error = null;

		try {
			itemBorrowTrans = service.createItemBorrowTransaction(bookItem, pAccount);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertNull(itemBorrowTrans);
		assertEquals(error, "Item cannot be null! " + "Account cannot be null!");
	}

	/**
	 * Failure : patron has an unvalidated account
	 * 
	 * @author Amani Jammoul
	 */
	@Test
	public void testCreateItemBorrowTransactionInvalidPatron() {
		BorrowableItem bookItem = borrowableItemDao.findBorrowableItemByBarCodeNumber(BOOK_BARCODENUMBER);

		Patron pAccount = (Patron) userAccountDao.findUserAccountByUserID(INVALID_PATRON_USER_ID);

		Transaction itemBorrowTrans = null;

		String error = null;

		try {
			itemBorrowTrans = service.createItemBorrowTransaction(bookItem, pAccount);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertNull(itemBorrowTrans);
		assertEquals(error, "User account is unvalidated, cannot complete borrow transaction!");
	}

	/**
	 * Failure : trying to borrow a newspaper
	 * 
	 * @author Amani Jammoul
	 */
	@Test
	public void testCreateItemBorrowTransactionNewspaper() {
		BorrowableItem newspaperItem = borrowableItemDao.findBorrowableItemByBarCodeNumber(NEWSPAPER_BARCODENUMBER);

		Patron pAccount = (Patron) userAccountDao.findUserAccountByUserID(VALID_PATRON_USER_ID);

		Transaction itemBorrowTrans = null;

		String error = null;

		try {
			itemBorrowTrans = service.createItemBorrowTransaction(newspaperItem, pAccount);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertNull(itemBorrowTrans);
		assertEquals(error, "This item cannot be borrowed!");
	}

	/* ***************** ITEM RETURN TESTS ********************* */
	/**
	 * Success case
	 * 
	 * @author Amani Jammoul
	 */
	@Test
	public void testCreateItemReturnTransactionSuccessful() {
		BorrowableItem bookItem = borrowableItemDao.findBorrowableItemByBarCodeNumber(BOOK_BARCODENUMBER);
		Patron pAccount = (Patron) userAccountDao.findUserAccountByUserID(VALID_PATRON_USER_ID);

		Transaction itemReserveTrans = null;

		try {
			itemReserveTrans = service.createItemReturnTransaction(bookItem, pAccount);
		} catch (IllegalArgumentException e) {
			fail();
		}
		assertNotNull(itemReserveTrans);
		checkResultTransaction(itemReserveTrans, bookItem, pAccount);
	}

	/**
	 * Failure : item and account objects are null
	 * 
	 * @author Amani Jammoul
	 */
	@Test
	public void testCreateItemReturnTransactionNull() {
		BorrowableItem bookItem = null;
		Patron pAccount = null;

		Transaction itemReturnTrans = null;

		String error = null;

		try {
			itemReturnTrans = service.createItemReturnTransaction(bookItem, pAccount);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertNull(itemReturnTrans);
		assertEquals(error, "Item cannot be null! " + "Account cannot be null!");
	}

	/* ***************** ITEM WAITLIST TESTS ********************* */
	/**
	 * Sucess case
	 * 
	 * @author Amani Jammoul
	 */
	@Test
	public void testCreateItemWaitlistTransactionSuccessful() {
		BorrowableItem bookItem = borrowableItemDao.findBorrowableItemByBarCodeNumber(BOOK_BARCODENUMBER);
		bookItem.setState(BORROWED_STATE); // assumed the book is borrowed by someone else

		Patron pAccount = (Patron) userAccountDao.findUserAccountByUserID(VALID_PATRON_USER_ID);

		Transaction itemReserveTrans = null;

		try {
			itemReserveTrans = service.createItemWaitlistTransaction(bookItem, pAccount);
		} catch (IllegalArgumentException e) {
			fail();
		}
		assertNotNull(itemReserveTrans);
		checkResultTransaction(itemReserveTrans, bookItem, pAccount);
	}

	/**
	 * Failure : item and account objects are null
	 * 
	 * @author Amani Jammoul
	 */
	@Test
	public void testCreateItemWaitlistTransactionNull() {
		BorrowableItem bookItem = null;
		Patron pAccount = null;

		Transaction itemWaitlistTrans = null;

		String error = null;

		try {
			itemWaitlistTrans = service.createItemWaitlistTransaction(bookItem, pAccount);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertNull(itemWaitlistTrans);
		assertEquals(error, "Item cannot be null! " + "Account cannot be null!");
	}

	/**
	 * Failure : patron has an unvalidated account
	 * 
	 * @author Amani Jammoul
	 */
	@Test
	public void testCreateItemWaitlistTransactionInvalidPatron() {
		BorrowableItem bookItem = borrowableItemDao.findBorrowableItemByBarCodeNumber(BOOK_BARCODENUMBER);
		bookItem.setState(BORROWED_STATE); // assumed the book is borrowed by someone else

		Patron pAccount = (Patron) userAccountDao.findUserAccountByUserID(INVALID_PATRON_USER_ID);

		Transaction itemWaitlistTrans = null;

		String error = null;

		try {
			itemWaitlistTrans = service.createItemWaitlistTransaction(bookItem, pAccount);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertNull(itemWaitlistTrans);
		assertEquals(error, "User account is unvalidated, cannot complete waitlist transaction!");
	}

	/* ***************** ITEM RENEWAL TESTS ********************* */
	/**
	 * Success case
	 * 
	 * @author Amani Jammoul
	 */
	@Test
	public void testCreateItemRenewalTransactionSuccessful() {
		BorrowableItem bookItem = borrowableItemDao.findBorrowableItemByBarCodeNumber(BOOK_BARCODENUMBER);

		Patron pAccount = (Patron) userAccountDao.findUserAccountByUserID(VALID_PATRON_USER_ID);

		Transaction itemReserveTrans = null;

		try {
			itemReserveTrans = service.createItemRenewalTransaction(bookItem, pAccount);
		} catch (IllegalArgumentException e) {
			fail();
		}
		assertNotNull(itemReserveTrans);
		checkResultTransaction(itemReserveTrans, bookItem, pAccount);
	}

	/**
	 * Failure : item and account objects are null
	 * 
	 * @author Amani Jammoul
	 */
	@Test
	public void testCreateItemRenewalTransactionNull() {
		BorrowableItem bookItem = null;
		Patron pAccount = null;

		Transaction itemRenewalTrans = null;

		String error = null;

		try {
			itemRenewalTrans = service.createItemReturnTransaction(bookItem, pAccount);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertNull(itemRenewalTrans);
		assertEquals(error, "Item cannot be null! " + "Account cannot be null!");
	}

	/**
	 * Failure : patron has an unvalidated account
	 * 
	 * @author Amani Jammoul
	 */
	@Test
	public void testCreateItemRenewalTransactionInvalidPatron() {
		BorrowableItem bookItem = borrowableItemDao.findBorrowableItemByBarCodeNumber(BOOK_BARCODENUMBER);

		Patron pAccount = (Patron) userAccountDao.findUserAccountByUserID(INVALID_PATRON_USER_ID);

		Transaction itemRenewalTrans = null;

		String error = null;

		try {
			itemRenewalTrans = service.createItemRenewalTransaction(bookItem, pAccount);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertNull(itemRenewalTrans);
		assertEquals(error, "User account is unvalidated, cannot complete renewal transaction!");
	}

	private void checkResultTransaction(Transaction transaction, BorrowableItem borrowableItem,
			UserAccount userAccount) {
		assertNotNull(transaction);
		assertEquals(borrowableItem, transaction.getBorrowableItem());
		assertEquals(userAccount, transaction.getUserAccount());
	}

	/**
	 * This test case is to see if the list of borrowed items that a user has in
	 * their account is correct
	 * 
	 * @throws Exception
	 * @author Ramin Akhavan-Sarraf
	 */
	@Test
	public void testGetBorrowedItemsFromUser() throws Exception {
		Patron pAccount = new Patron();
		pAccount.setFirstName(PATRON_FIRST_NAME);
		pAccount.setLastName(PATRON_LAST_NAME);
		pAccount.setValidatedAccount(PATRON_VALIDATED);
		pAccount.setOnlineAccount(ONLINE);

		List<BorrowableItem> borrowedItems = null;
		try {
			borrowedItems = service.getBorrowedItemsFromUser(pAccount);
		} catch (IllegalArgumentException e) {
			fail();
		}
		assertNotNull(borrowedItems);
		assertEquals(1, borrowedItems.size());
		checkUserItems(borrowedItems.get(0));

	}

	/**
	 * This test case is to see if the list of reserved items that a user has in
	 * their account is correct
	 * 
	 * @throws Exception
	 * @author Ramin Akhavan-Sarraf
	 */
	@Test
	public void testGetReservedItemsFromUser() throws Exception {
		Patron pAccount = new Patron();
		pAccount.setFirstName(PATRON_FIRST_NAME);
		pAccount.setLastName(PATRON_LAST_NAME);
		pAccount.setValidatedAccount(PATRON_VALIDATED);
		pAccount.setOnlineAccount(ONLINE);

		List<BorrowableItem> reservedItems = null;
		try {
			reservedItems = service.getReservedItemsFromUser(pAccount);
		} catch (IllegalArgumentException e) {
			fail();
		}
		assertNotNull(reservedItems);
		assertEquals(1, reservedItems.size());
		checkUserItems(reservedItems.get(0));

	}

	/**
	 * This test case is to see if the list of waitlist items that a user has in
	 * their account is correct
	 * 
	 * @throws Exception
	 * @author Ramin Akhavan-Sarraf
	 */
	@Test
	public void testGetWaitlistItemsFromUser() throws Exception {
		Patron pAccount = new Patron();
		pAccount.setFirstName(PATRON_FIRST_NAME);
		pAccount.setLastName(PATRON_LAST_NAME);
		pAccount.setValidatedAccount(PATRON_VALIDATED);
		pAccount.setOnlineAccount(ONLINE);

		List<BorrowableItem> waitlistItems = null;
		try {
			waitlistItems = service.getReservedItemsFromUser(pAccount);
		} catch (IllegalArgumentException e) {
			fail();
		}
		assertNotNull(waitlistItems);
		assertEquals(1, waitlistItems.size());
		checkUserItems(waitlistItems.get(0));

	}

	/**
	 * This helper method checks to see if the correct borrowable item is in the
	 * user's list of items, based on the unique bar code of the borrowable item
	 * 
	 * @param a borrowable item
	 */
	public void checkUserItems(BorrowableItem borrowableItem) {
		if (borrowableItem.getLibraryItem().getType() == ItemType.Book) {
			assertEquals(borrowableItem.getBarCodeNumber(), BOOK_BARCODENUMBER);
		} else if (borrowableItem.getLibraryItem().getType() == ItemType.Movie) {
			assertEquals(borrowableItem.getBarCodeNumber(), MOVIE_BARCODENUMBER);
		} else if (borrowableItem.getLibraryItem().getType() == ItemType.Music) {
			assertEquals(borrowableItem.getBarCodeNumber(), MUSIC_BARCODENUMBER);
		} else if (borrowableItem.getLibraryItem().getType() == ItemType.NewspaperArticle) {
			assertEquals(borrowableItem.getBarCodeNumber(), NEWSPAPER_BARCODENUMBER);
		} else {
			assertEquals(borrowableItem.getBarCodeNumber(), ROOM_BARCODENUMBER);
		}
	}

}
