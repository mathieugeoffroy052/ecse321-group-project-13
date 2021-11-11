package ca.mcgill.ecse321.libraryservice.service;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.lenient;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import ca.mcgill.ecse321.libraryservice.model.*;
import ca.mcgill.ecse321.libraryservice.dao.LibraryItemRepository;
import ca.mcgill.ecse321.libraryservice.dao.BorrowableItemRepository;
import ca.mcgill.ecse321.libraryservice.dao.UserAccountRepository;
import ca.mcgill.ecse321.libraryservice.dao.TransactionRepository;


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