package ca.mcgill.ecse321.libraryservice.service;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
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
import ca.mcgill.ecse321.libraryservice.model.Transaction.TransactionType;
import ca.mcgill.ecse321.libraryservice.dao.*;


@ExtendWith(MockitoExtension.class)
public class TestUserAccountService {
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
	private static final int NEWSPAPER_BARCODENUMBER = 1111111;
    private static final int ROOM_BARCODENUMBER = 999999;
	private static final int MOVIE_BARCODENUMBER = 1212121;
    private static final int MUSIC_BARCODENUMBER = 989898;

	private static final ItemState AVAILABLE_STATE = ItemState.Available;
	private static final ItemState BORROWED_STATE = ItemState.Borrowed;

	/* Patron attributes*/
	private static final int VALID_PATRON_USER_ID = 8;
	private static final int INVALID_PATRON_USER_ID = 7;
	private static final String PATRON_FIRST_NAME = "Jimmy";
	private static final String PATRON_LAST_NAME = "John";
	private static final String PATRON_EMAIL = "jimmy123@hotmail.com";
	private static final String PATRON_PASSWORD = "johniscool";
	private static final int PATRON_BALANCE = 1000;
	private static final String PATRON_ADDRESS = "11 Beverly Hills Road";
	private static final boolean PATRON_VALIDATED = true;
	private static final boolean ONLINE = true;
	
	private static final String PATRON_FAKE_FIRST_NAME = "FakeJimmy";
	private static final String PATRON_FAKE_LAST_NAME = "FakeJohn";
	
	private static final String PATRON_FIRST_NAME_2 = "Hugh";
	private static final String PATRON_LAST_NAME_2 = "Smith";

	/* Transaction attributes*/
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
	 * This method is used to mock the user account database methods so that fake
	 * data can be used to test the service methods before each test case
	 * @author Amani Jammoul, Gabrielle Halpin and Ramin Akhavan-Sarraf
	 */
	@BeforeEach
	public void setMockOutput() {
		lenient().when(userAccountDao.findUserAccountByUserID(anyInt())).thenAnswer((InvocationOnMock invocation) -> {
			if (invocation.getArgument(0).equals(VALID_PATRON_USER_ID)) {
				Patron pAccount = new Patron();
				pAccount.setFirstName(PATRON_FIRST_NAME);
				pAccount.setLastName(PATRON_LAST_NAME);
				pAccount.setValidatedAccount(PATRON_VALIDATED);
				pAccount.setOnlineAccount(ONLINE);
				pAccount.setEmail(PATRON_EMAIL);
				pAccount.setPassword(PATRON_PASSWORD);
				pAccount.setBalance(PATRON_BALANCE);
				pAccount.setAddress(PATRON_ADDRESS);
				return pAccount;
			} 
			else if (invocation.getArgument(0).equals(INVALID_PATRON_USER_ID)) {
				Patron pAccount = new Patron();
				pAccount.setFirstName(PATRON_FIRST_NAME_2);
				pAccount.setLastName(PATRON_LAST_NAME_2);
				pAccount.setValidatedAccount(!PATRON_VALIDATED);
				pAccount.setOnlineAccount(ONLINE);
				return pAccount;
			} 
			else {
				return null;
			}
		});

		lenient().when(userAccountDao.findAll()).thenAnswer((InvocationOnMock invocation) -> {
			List<Patron> patrons = new ArrayList<>();
			
			Patron pAccount = new Patron();
			pAccount.setFirstName(PATRON_FIRST_NAME);
			pAccount.setLastName(PATRON_LAST_NAME);
			pAccount.setValidatedAccount(PATRON_VALIDATED);
			pAccount.setOnlineAccount(ONLINE);
			pAccount.setEmail(PATRON_EMAIL);
			pAccount.setPassword(PATRON_PASSWORD);
			pAccount.setBalance(PATRON_BALANCE);
			pAccount.setAddress(PATRON_ADDRESS);
			patrons.add(pAccount);

			Patron pAccount2 = new Patron();
			pAccount2.setFirstName(PATRON_FIRST_NAME_2);
			pAccount2.setLastName(PATRON_LAST_NAME_2);
			pAccount2.setValidatedAccount(!PATRON_VALIDATED);
			pAccount2.setOnlineAccount(ONLINE);
			patrons.add(pAccount2);
			
			return patrons;
		});
		
		Answer<?> returnParameterAsAnswer = (InvocationOnMock invocation) -> {
			return invocation.getArgument(0);
		};
		lenient().when(userAccountDao.save(any(UserAccount.class))).thenAnswer(returnParameterAsAnswer);
	}

	// @Test
	// public void testGetUserAccountFromUserID() throws Exception {
		
	// 	UserAccount userAccount = null;
	// 	try {
	// 		userAccount = service.getUserAccountFromUserID(VALID_PATRON_USER_ID);
	// 	} catch (IllegalArgumentException e) {
	// 		fail();
	// 	}
	// 	assertNotNull(userAccount);
	// 	checkFullUserDetails(userAccount);
		
	// }
	
	/**
	 * This test case checks to see if the appropriate user account is returned when
	 * searching using a first name and last name, for the getUserAccountFromFullName service method
	 * @throws Exception
	 */
	@Test
	public void testGetUserAccountFromFullName() throws Exception {
		
		UserAccount userAccount = null;
		try {
			userAccount = service.getUserAccountFromFullName(PATRON_FIRST_NAME, PATRON_LAST_NAME);
		} catch (IllegalArgumentException e) {
			fail();
		}
		assertNotNull(userAccount);
		checkFullUserDetails(userAccount);
		
	}

	/**
	 * Fail test case - Checks to make sure a first name field is needed when searching
	 * for a user of the system using their first name and last name (for the getUserAccountFromFullName 
	 * service method)
	 * @throws Exception
	 */
	@Test
	public void testFailedGetUserAccountFromFullNameEmptyFirstNameError() throws Exception {
		
		UserAccount userAccount = null;
		String error = "";
		try {
			userAccount = service.getUserAccountFromFullName(null, PATRON_LAST_NAME);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertEquals(error, "First name cannot be empty!");
		
	}

	/**
	 * Fail test case - Checks to make sure a last name field is needed when searching
	 * for a user of the system using their first name and last name (for the getUserAccountFromFullName 
	 * service method)
	 * @throws Exception
	 */
	@Test
	public void testFailedGetUserAccountFromFullNameEmptyLastNameError() throws Exception {
		
		UserAccount userAccount = null;
		String error = "";
		try {
			userAccount = service.getUserAccountFromFullName(PATRON_FIRST_NAME, null);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertEquals(error, "Last name cannot be empty!");
		
	}
	
	/**
	 * Fail test case - Checks to make sure no user account is returned when the user
	 * cannot be found in the system when using the getUserAccountFromFullName service method
	 * @throws Exception
	 */
	@Test
	public void testFailedGetUserAccountFromFullNameFakeNameError() throws Exception {
		
		UserAccount userAccount = null;
		String error = "";
		try {
			userAccount = service.getUserAccountFromFullName(PATRON_FAKE_FIRST_NAME, PATRON_FAKE_LAST_NAME);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertEquals(error, "No user found with this name! ");
		
	}
	
	/**
	 * This helper method is called to do an extensive check of a user account's details
	 * when a user account is found in any of the test cases
	 * @param userAccount
	 */
	public void checkFullUserDetails(UserAccount userAccount) {
		assertEquals(PATRON_ADDRESS, userAccount.getAddress());
		assertEquals(PATRON_BALANCE, userAccount.getBalance());
		assertEquals(PATRON_EMAIL, userAccount.getEmail());
		assertEquals(PATRON_FIRST_NAME, userAccount.getFirstName());
		assertEquals(PATRON_LAST_NAME, userAccount.getLastName());
		assertEquals(ONLINE, userAccount.getOnlineAccount());
		assertEquals(PATRON_PASSWORD, userAccount.getPassword());

	}

    
}