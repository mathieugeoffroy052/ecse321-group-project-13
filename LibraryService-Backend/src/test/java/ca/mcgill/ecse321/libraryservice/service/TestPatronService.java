package ca.mcgill.ecse321.libraryservice.service;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.lenient;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import ca.mcgill.ecse321.libraryservice.model.*;
import ca.mcgill.ecse321.libraryservice.dao.*;

@ExtendWith(MockitoExtension.class)
public class TestPatronService {

	@Mock
	private PatronRepository patronDAO;
	@Mock
	private UserAccountRepository userAccountDAO;
	@Mock
	private HeadLibrarianRepository headLibrarianDAO;

	@InjectMocks
	private LibraryServiceService service;

	private static final int CREATOR_ID = 123;
	private static final String CREATOR_FIRST_NAME = "Tristan";
	private static final String CREATOR_LAST_NAME = "Golden";
	private static final String CREATOR_EMAIL = "creator@email.com";
	private static final int CREATOR_BALANCE = 0;
	private static final boolean CREATOR_ONLINE_ACCOUNT = true;
	private static final String CREATOR_ADDRESS = "1234 ave jack";
	private static final String CREATOR_PASSWORD = "creator123";

	private static final int PATRON_ID = 12345;
	private static final String PATRON_FIRST_NAME = "John";
	private static final String PATRON_LAST_NAME = "Smith";
	private static final String PATRON_EMAIL = "johnsmith@email.com";
	private static final int PATRON_BALANCE = 0;
	private static final int PATRON_CREATOR = CREATOR_ID;

	private static final boolean PATRON_ONLINE_ACCOUNT = true;
	private static final String PATRON_ADDRESS = "123 Smith Street";
	private static final boolean PATRON_VALIDATED_ACCOUNT = false;
	private static final String PATRON_PASSWORD = "patron123";

	/**
	 * This method is used to mock the patron database methods by creating test
	 * objects and storing them in the system so that the service methods can
	 * execute. This is done before each of the tests.
	 * 
	 * @author Zoya Malhi
	 */
	@BeforeEach
	public void setMockOutput() {
		lenient().when(patronDAO.findPatronByUserID(anyInt())).thenAnswer((InvocationOnMock invocation) -> {
			if (invocation.getArgument(0).equals(PATRON_ID)) {

				Patron patron = new Patron();
				patron.setUserID(PATRON_ID);
				patron.setFirstName(PATRON_FIRST_NAME);
				patron.setLastName(PATRON_LAST_NAME);
				patron.setEmail(PATRON_EMAIL);
				patron.setPassword(PATRON_PASSWORD);
				patron.setBalance(PATRON_BALANCE);
				patron.setOnlineAccount(PATRON_ONLINE_ACCOUNT);
				patron.setAddress(PATRON_ADDRESS);
				patron.setValidatedAccount(PATRON_VALIDATED_ACCOUNT);

				return patron;
			} else {
				return null;
			}
		});
		lenient().when(userAccountDAO.findUserAccountByUserID(anyInt())).thenAnswer((InvocationOnMock invocation) -> {
			if (invocation.getArgument(0).equals(PATRON_ID)) {

				Patron patron = new Patron();
				patron.setUserID(PATRON_ID);
				patron.setFirstName(PATRON_FIRST_NAME);
				patron.setLastName(PATRON_LAST_NAME);
				patron.setEmail(PATRON_EMAIL);
				patron.setPassword(PATRON_PASSWORD);
				patron.setBalance(PATRON_BALANCE);
				patron.setOnlineAccount(PATRON_ONLINE_ACCOUNT);
				patron.setAddress(PATRON_ADDRESS);
				patron.setValidatedAccount(PATRON_VALIDATED_ACCOUNT);

				return patron;
			} else if (invocation.getArgument(0).equals(CREATOR_ID)) {

				Librarian user = new Librarian();
				user.setUserID(CREATOR_ID);
				user.setFirstName(CREATOR_FIRST_NAME);
				user.setLastName(CREATOR_LAST_NAME);
				user.setEmail(CREATOR_EMAIL);
				user.setPassword(CREATOR_PASSWORD);
				user.setBalance(CREATOR_BALANCE);
				user.setOnlineAccount(CREATOR_ONLINE_ACCOUNT);
				user.setAddress(CREATOR_ADDRESS);

				return user;
			} else {
				return null;
			}

		});

		lenient().when(patronDAO.findAll()).thenAnswer((InvocationOnMock invocation) -> {

			Patron patron = new Patron();
			patron.setUserID(PATRON_ID);
			patron.setFirstName(PATRON_FIRST_NAME);
			patron.setLastName(PATRON_LAST_NAME);
			patron.setEmail(PATRON_EMAIL);
			patron.setPassword(PATRON_PASSWORD);
			patron.setBalance(PATRON_BALANCE);
			patron.setOnlineAccount(PATRON_ONLINE_ACCOUNT);
			patron.setAddress(PATRON_ADDRESS);
			patron.setValidatedAccount(PATRON_VALIDATED_ACCOUNT);

			List<Patron> patrons = new ArrayList<Patron>();
			patrons.add(patron);
			return patrons;

		});

		// Whenever anything is saved, just return the parameter object
		Answer<?> returnParameterAsAnswer = (InvocationOnMock invocation) -> {
			return invocation.getArgument(0);
		};
		lenient().when(patronDAO.save(any(Patron.class))).thenAnswer(returnParameterAsAnswer);
		lenient().when(userAccountDAO.save(any(UserAccount.class))).thenAnswer(returnParameterAsAnswer);
		lenient().when(headLibrarianDAO.save(any(HeadLibrarian.class))).thenAnswer(returnParameterAsAnswer);
	}

	@AfterEach
	public void clearMockOutputs() {
		patronDAO.deleteAll();
		userAccountDAO.deleteAll();
	}

	/**
	 * This tests verifies if a patron is created successfully in the database.
	 * 
	 * @author Zoya Malhi
	 * @throws Exception
	 */
	@Test
	public void testCreatePatronSuccessful() throws Exception {

		Patron patron = null;

		try {
			patron = service.createPatron(PATRON_CREATOR, PATRON_FIRST_NAME, PATRON_LAST_NAME, PATRON_ONLINE_ACCOUNT,
					PATRON_ADDRESS, PATRON_VALIDATED_ACCOUNT, PATRON_PASSWORD, PATRON_BALANCE, PATRON_EMAIL, true);

		} catch (IllegalArgumentException e) {
			fail();

		}
		// verify error
		assertEquals(PATRON_FIRST_NAME, patron.getFirstName());
		assertEquals(PATRON_LAST_NAME, patron.getLastName());
		assertEquals(PATRON_ADDRESS, patron.getAddress());
		assertEquals(PATRON_ONLINE_ACCOUNT, patron.getOnlineAccount());
		assertEquals(PATRON_VALIDATED_ACCOUNT, patron.getValidatedAccount());
		assertEquals(PATRON_EMAIL, patron.getEmail());
		assertEquals(PATRON_PASSWORD, patron.getPassword());
		assertEquals(PATRON_BALANCE, patron.getBalance());

	}

	/**
	 * This test checks if the first name of the patron is null when creating a
	 * patron in the system. It returns an error if the first name is null.
	 * 
	 * @author Zoya Malhi
	 * @throws Exception
	 */
	@Test
	public void testCreatePatronNullFirstName() throws Exception {
		String error = null;
		Patron patron = null;
		String firstName = null;
		try {
			patron = service.createPatron(PATRON_CREATOR, firstName, PATRON_LAST_NAME, PATRON_ONLINE_ACCOUNT,
					PATRON_ADDRESS, PATRON_VALIDATED_ACCOUNT, PATRON_PASSWORD, PATRON_BALANCE, PATRON_EMAIL, true);

		} catch (IllegalArgumentException e) {
			error = e.getMessage();

		}
		assertNull(patron);

		// verify error
		assertEquals("First Name cannot be empty!", error);

	}

	/**
	 * This test checks if the first name of the patron is empty when creating a
	 * patron in the system. It returns an error if the first name is empty.
	 * 
	 * @author Zoya Malhi
	 * @throws Exception
	 */
	@Test
	public void testCreatePatronEmptyFirstName() throws Exception {
		String error = null;
		Patron patron = null;
		String firstName = "";
		try {
			patron = service.createPatron(PATRON_CREATOR, firstName, PATRON_LAST_NAME, PATRON_ONLINE_ACCOUNT,
					PATRON_ADDRESS, PATRON_VALIDATED_ACCOUNT, PATRON_PASSWORD, PATRON_BALANCE, PATRON_EMAIL, true);

		} catch (IllegalArgumentException e) {
			error = e.getMessage();

		}
		assertNull(patron);

		// verify error
		assertEquals("First Name cannot be empty!", error);

	}

	/**
	 * This test checks if the last name of the patron is null when creating a
	 * patron in the system. It returns an error if the last name is null.
	 * 
	 * @author Zoya Malhi
	 * @throws Exception
	 */
	@Test
	public void testCreatePatronNullLastName() throws Exception {
		String error = null;
		Patron patron = null;
		String lastName = null;
		try {
			patron = service.createPatron(PATRON_CREATOR, PATRON_FIRST_NAME, lastName, PATRON_ONLINE_ACCOUNT,
					PATRON_ADDRESS, PATRON_VALIDATED_ACCOUNT, PATRON_PASSWORD, PATRON_BALANCE, PATRON_EMAIL, true);

		} catch (IllegalArgumentException e) {
			error = e.getMessage();

		}
		assertNull(patron);

		// verify error
		assertEquals("Last Name cannot be empty!", error);

	}

	/**
	 * This test checks if the last name of the patron is empty when creating a
	 * patron in the system. It returns an error if the last name is empty.
	 * 
	 * @author Zoya Malhi
	 * @throws Exception
	 */
	@Test
	public void testCreatePatronEmptyLastName() throws Exception {
		String error = null;
		Patron patron = null;
		String lastName = "";
		try {
			patron = service.createPatron(PATRON_CREATOR, PATRON_FIRST_NAME, lastName, PATRON_ONLINE_ACCOUNT,
					PATRON_ADDRESS, PATRON_VALIDATED_ACCOUNT, PATRON_PASSWORD, PATRON_BALANCE, PATRON_EMAIL, true);

		} catch (IllegalArgumentException e) {
			error = e.getMessage();

		}
		assertNull(patron);

		// verify error
		assertEquals("Last Name cannot be empty!", error);

	}

	/**
	 * This test checks if the address of the patron is null when creating a patron
	 * in the system. It returns an error if the address is null.
	 * 
	 * @author Zoya Malhi
	 * @throws Exception
	 */
	@Test
	public void testCreatePatronNullAddress() throws Exception {
		String error = null;
		Patron patron = null;
		String address = null;
		try {
			patron = service.createPatron(PATRON_CREATOR, PATRON_FIRST_NAME, PATRON_LAST_NAME, PATRON_ONLINE_ACCOUNT,
					address, PATRON_VALIDATED_ACCOUNT, PATRON_PASSWORD, PATRON_BALANCE, PATRON_EMAIL, true);

		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertNull(patron);
		// verify error
		assertEquals("Address cannot be empty!", error);

	}

	/**
	 * This test checks if the address of the patron is empty when creating a patron
	 * in the system. It returns an error if the address is empty.
	 * 
	 * @author Zoya Malhi
	 * @throws Exception
	 */
	@Test
	public void testCreatePatronEmptyAddress() throws Exception {
		String error = null;
		Patron patron = null;
		String address = "";
		try {
			patron = service.createPatron(PATRON_CREATOR, PATRON_FIRST_NAME, PATRON_LAST_NAME, PATRON_ONLINE_ACCOUNT,
					address, PATRON_VALIDATED_ACCOUNT, PATRON_PASSWORD, PATRON_BALANCE, PATRON_EMAIL, true);

		} catch (IllegalArgumentException e) {
			error = e.getMessage();

		}
		assertNull(patron);

		// verify error
		assertEquals("Address cannot be empty!", error);
	}

	/**
	 * This test checks if the password of the patron is null when creating a patron
	 * in the system. It returns an error if the password is null.
	 * 
	 * @author Zoya Malhi
	 * @throws Exception
	 */
	@Test
	public void testCreatePatronNullPassword() throws Exception {
		String error = null;
		Patron patron = null;
		String password = null;
		try {
			patron = service.createPatron(PATRON_CREATOR, PATRON_FIRST_NAME, PATRON_LAST_NAME, PATRON_ONLINE_ACCOUNT,
					PATRON_ADDRESS, PATRON_VALIDATED_ACCOUNT, password, PATRON_BALANCE, PATRON_EMAIL, true);

		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertNull(patron);
		// verify error
		assertEquals("Password cannot be empty!", error);

	}

	/**
	 * This test checks if the password of the patron is empty when creating a
	 * patron in the system. It returns an error if the password is empty.
	 * 
	 * @author Zoya Malhi
	 * @throws Exception
	 */
	@Test
	public void testCreatePatronEmptyPassword() throws Exception {
		String error = null;
		Patron patron = null;
		String password = "";
		try {
			patron = service.createPatron(PATRON_CREATOR, PATRON_FIRST_NAME, PATRON_LAST_NAME, PATRON_ONLINE_ACCOUNT,
					PATRON_ADDRESS, PATRON_VALIDATED_ACCOUNT, password, PATRON_BALANCE, PATRON_EMAIL, true);

		} catch (IllegalArgumentException e) {
			error = e.getMessage();

		}
		assertNull(patron);

		// verify error
		assertEquals("Password cannot be empty!", error);
	}

	/**
	 * This test checks if the email of the patron is null when creating a patron in
	 * the system. It returns an error if the email is null.
	 * 
	 * @author Zoya Malhi
	 * @throws Exception
	 */
	@Test
	public void testCreatePatronNullEmail() throws Exception {
		String error = null;
		Patron patron = null;
		String email = null;
		try {
			patron = service.createPatron(PATRON_CREATOR, PATRON_FIRST_NAME, PATRON_LAST_NAME, PATRON_ONLINE_ACCOUNT,
					PATRON_ADDRESS, PATRON_VALIDATED_ACCOUNT, PATRON_PASSWORD, PATRON_BALANCE, email, true);

		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertNull(patron);
		// verify error
		assertEquals("Email cannot be empty!", error);

	}

	/**
	 * This test checks if the email of the patron is empty when creating a patron
	 * in the system. It returns an error if the email is empty.
	 * 
	 * @author Zoya Malhi
	 * @throws Exception
	 */
	@Test
	public void testCreatePatronEmptyEmail() throws Exception {
		String error = null;
		Patron patron = null;
		String email = "";
		try {
			patron = service.createPatron(PATRON_CREATOR, PATRON_FIRST_NAME, PATRON_LAST_NAME, PATRON_ONLINE_ACCOUNT,
					PATRON_ADDRESS, PATRON_VALIDATED_ACCOUNT, PATRON_PASSWORD, PATRON_BALANCE, email, true);

		} catch (IllegalArgumentException e) {
			error = e.getMessage();

		}
		assertNull(patron);

		// verify error
		assertEquals("Email cannot be empty!", error);
	}

	/**
	 * This tests checks if the creator of the patron is null when creating a patron
	 * in the system. It returns an error if the creator is null.
	 * 
	 * @author Zoya Malhi
	 * @throws Exception
	 */
	@Test
	public void testCreatePatronNullCreator() throws Exception {
		String error = null;
		Patron patron = null;
		try {
			patron = service.createPatron(126647, PATRON_FIRST_NAME, PATRON_LAST_NAME, PATRON_ONLINE_ACCOUNT,
					PATRON_ADDRESS, PATRON_VALIDATED_ACCOUNT, PATRON_PASSWORD, PATRON_BALANCE, PATRON_EMAIL, true);
		}

		catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertNull(patron);
		// verify error
		assertEquals("The creator does not exist", error);
	}

	/**
	 * This test checks if the creator of the patron is not a librarian when
	 * creating a patron in the system. It returns an error if the creator is not an
	 * instance of Librarian.
	 * 
	 * @author Zoya Malhi
	 * @throws Exception
	 */
	@Test
	public void testCreatePatronPatronAsCreator() throws Exception {
		String error = null;
		Patron patron = null;
		boolean onlineAccount = false;

		try {
			patron = service.createPatron(PATRON_ID, PATRON_FIRST_NAME, PATRON_LAST_NAME, onlineAccount, PATRON_ADDRESS,
					PATRON_VALIDATED_ACCOUNT, PATRON_PASSWORD, PATRON_BALANCE, PATRON_EMAIL, true);
		}

		catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertNull(patron);
		// verify error
		assertEquals("Only a Librarian can create an in-person account", error);
	}

	/**
	 * This test calls the getPatronByUserID method which queries the database and
	 * returns the patron with the same ID.
	 * 
	 * @author Zoya Malhi
	 * @throws Exception
	 */
	@Test
	public void testGetPatronFromID() throws Exception {
		assertEquals(PATRON_ID, service.getPatronByUserId(PATRON_ID).getUserID());
	}

	/**
	 * This test calls the getPatronByUserID method which queries the database and
	 * returns the patron with the same ID.
	 * 
	 * @author Zoya Malhi
	 * @throws Exception
	 */
	@Test
	public void testGetPatronFromIDError() throws Exception {
		int falseID = 0;
		String error = "";
		Patron patron = null;
		try {
			patron = service.getPatronByUserId(falseID);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertNull(patron);
		assertEquals("This patron does not exist.", error);

	}

	/**
	 * This tests deletes a patron by calling the deleteAPatronbyUserID method which
	 * queries the database for the patron specified and deletes it from the system.
	 * 
	 * @author Zoya Malhi
	 * @throws Exception
	 */
	@Test
	public void testDeletePatronByUserIDSuccessful() throws Exception {
		boolean success = false;
		try {
			success = service.deleteAPatronbyUserID(PATRON_CREATOR, PATRON_ID);

		} catch (Exception e) {
			fail();

		}
		assertTrue(success);

	}

	/**
	 * This tests deletes a patron by calling the deleteAPatronbyUserID method which
	 * queries the database for the patron specified and deletes it from the system.
	 * Error is rasied since the patron does not exist.
	 * 
	 * @author Zoya Malhi
	 * @throws Exception
	 */
	@Test
	public void testDeletePatronByUserIDFail() throws Exception {
		Patron patron = null;
		String error = "";
		try {
			service.deleteAPatronbyUserID(PATRON_CREATOR, 15845);

		} catch (Exception e) {
			error = e.getMessage();

		}
		// verify error
		assertNull(patron);
		assertEquals("This user Id does not exist as a Patron", error);

		patronDAO.deleteAll();
		userAccountDAO.deleteAll();
		headLibrarianDAO.deleteAll();
	}

	/**
	 * This test attempts to delete a patron specified by its userID in the system,
	 * but returns and error instead because the creator does not have the
	 * credentials to perform the operation.
	 * 
	 * 
	 * @author Zoya Malhi
	 * @throws Exception
	 */
	@Test
	public void testDeletePatronByUserIDWrongCreator() throws Exception {
		try {
			service.deleteAPatronbyUserID(PATRON_ID, PATRON_ID);
		} catch (Exception e) {
			assertEquals("This user does not have the credentials to delete an existing patron", e.getMessage());
		}

		headLibrarianDAO.deleteAll();
	}

	/**
	 * This test gets a patron from the system given their first and last name. No
	 * error is returned.
	 * 
	 * @author Zoya Malhi
	 * @throws Exception
	 */
	@Test
	public void testGetPatronFromFullNameSuccessful() throws Exception {

		Patron patron = null;
		try {
			patron = service.getPatronFromFullName(PATRON_FIRST_NAME, PATRON_LAST_NAME);

		} catch (IllegalArgumentException e) {
			fail();

		}
		// verify error
		assertEquals(PATRON_FIRST_NAME, patron.getFirstName());
		assertEquals(PATRON_LAST_NAME, patron.getLastName());

	}

	/**
	 * This test gets a patron from the system given their first and last name.
	 * Error is returned.
	 * 
	 * @author Zoya Malhi
	 * @throws Exception
	 */
	@Test
	public void testGetPatronFromFullNameError() throws Exception {
		String error = "";
		Patron patron = null;
		try {
			patron = service.getPatronFromFullName("Mary", "Adams");

		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertNull(patron);
		// verify error
		assertEquals("No patron found with this name! ", error);
	}

	/**
	 * This test gets a patron from the system given their first and last name, but
	 * where the first name is null. This returns an error since the first name is
	 * null.
	 * 
	 * @author Zoya Malhi
	 * @throws Exception
	 */
	@Test
	public void testGetPatronFromFullNameNullFirstName() throws Exception {

		String error = null;
		Patron patron = null;
		String firstName = null;
		try {
			patron = service.getPatronFromFullName(firstName, PATRON_LAST_NAME);

		} catch (IllegalArgumentException e) {
			error = e.getMessage();

		}
		assertNull(patron);

		// verify error
		assertEquals("First Name cannot be empty!", error);

	}

	/**
	 * This test gets a patron from the system given their first and last name, but
	 * where the first name is empty. This returns an error since the first name is
	 * empty.
	 * 
	 * @author Zoya Malhi
	 * @throws Exception
	 */
	@Test
	public void testGetPatronFromFullNameEmptyFirstName() throws Exception {

		String error = null;
		Patron patron = null;
		String firstName = "";
		try {
			patron = service.getPatronFromFullName(firstName, PATRON_LAST_NAME);

		} catch (IllegalArgumentException e) {
			error = e.getMessage();

		}
		assertNull(patron);

		// verify error
		assertEquals("First Name cannot be empty!", error);

	}

	/**
	 * This test gets a patron from the system given their first and last name, but
	 * where the last name is null. This returns an error since the last name is
	 * null.
	 * 
	 * @author Zoya Malhi
	 * @throws Exception
	 */
	@Test
	public void testGetPatronFromFullNameNullLastName() throws Exception {

		String error = null;
		Patron patron = null;
		String lastName = null;
		try {
			patron = service.getPatronFromFullName(PATRON_FIRST_NAME, lastName);

		} catch (IllegalArgumentException e) {
			error = e.getMessage();

		}
		assertNull(patron);

		// verify error
		assertEquals("Last Name cannot be empty!", error);

	}

	/**
	 * This test gets a patron from the system given their first and last name, but
	 * where the last name is empty. This returns an error since the last name is
	 * empty.
	 * 
	 * @author Zoya Malhi
	 * @throws Exception
	 */
	@Test
	public void testGetPatronFromFullNameEmptyLastName() throws Exception {

		String error = null;
		Patron patron = null;
		String lastName = "";
		try {
			patron = service.getPatronFromFullName(PATRON_FIRST_NAME, lastName);

		} catch (IllegalArgumentException e) {
			error = e.getMessage();

		}
		assertNull(patron);

		// verify error
		assertEquals("Last Name cannot be empty!", error);

	}

	/**
	 * This test gets a patron from the system given their first and last name, but
	 * where the first name is null. This returns an error since the first name is
	 * null.
	 * 
	 * @author Zoya Malhi
	 * @throws Exception
	 */
	@Test
	public void testSetValidatedAccountSuccessful() throws Exception {
		Patron patron = patronDAO.findPatronByUserID(PATRON_ID);
		try {
			patron = service.setValidatedAccount(patron.getUserID(), PATRON_VALIDATED_ACCOUNT, PATRON_CREATOR);
		} catch (IllegalArgumentException e) {
			fail();
		}
		assertEquals(PATRON_VALIDATED_ACCOUNT, patron.getValidatedAccount());

	}

	/**
	 * This test attempts to set a patron account as validated, but given that the
	 * creator is not an instance of a librarian.An error is thrown.
	 * 
	 * @author Zoya Malhi
	 * @throws Exception
	 */
	@Test
	public void testSetValidatedAccountWrongCreator() throws Exception {

		String error = null;
		Patron patron = null;

		try {
			patron = service.setValidatedAccount(PATRON_ID, PATRON_VALIDATED_ACCOUNT, PATRON_ID);

		} catch (IllegalArgumentException e) {
			error = e.getMessage();

		}
		// verify error
		assertNull(patron);
		assertEquals("Only a Librarian can change the validity of an account", error);

	}

	/**
	 * This test attempts to set a patron account as validated, but given that the
	 * creator is null. An error is thrown.
	 * 
	 * @author Zoya Malhi
	 * @throws Exception
	 */
	@Test
	public void testSetValidatedAccountNullCreator() throws Exception {

		String error = null;

		Patron patron = null;

		try {
			patron = service.setValidatedAccount(PATRON_ID, PATRON_VALIDATED_ACCOUNT, 47365);

		} catch (IllegalArgumentException e) {
			error = e.getMessage();

		}
		// verify error
		assertNull(patron);
		assertEquals("The creator does not exist", error);

	}

	/**
	 * This test attempts to set a patron account as validated, but given that the
	 * patron is null. An error is thrown.
	 * 
	 * @author Zoya Malhi
	 * @throws Exception
	 */
	@Test
	public void testSetValidatedAccountNullPatron() throws Exception {

		String error = null;
		Patron patron = null;

		try {
			patron = service.setValidatedAccount(3465, PATRON_VALIDATED_ACCOUNT, PATRON_CREATOR);

		} catch (IllegalArgumentException e) {
			error = e.getMessage();

		}
		// verify error
		assertNull(patron);
		assertEquals("The patron does not exist", error);

	}

	/**
	 * This test gets all patrons from the database by calling the getAllPatrons()
	 * method. A list of patrons in the system is returned.
	 * 
	 * @author Zoya Malhi
	 * @throws Exception
	 */
	@Test
	public void testGetAllPatronsSuccessful() throws Exception {
		List<Patron> patrons = null;

		try {
			patrons = service.getAllPatrons();

		} catch (IllegalArgumentException e) {
			throw new Exception("could not retrieve patrons");

		}
		assertEquals(1, patrons.size());

	}

}
