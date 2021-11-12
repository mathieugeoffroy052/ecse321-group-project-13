package ca.mcgill.ecse321.libraryservice.service;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
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
private static final int PATRON_ID = 12345;
private static final String PATRON_FIRST_NAME = "John";
private static final String PATRON_LAST_NAME = "Smith";
private static final String PATRON_EMAIL = "johnsmith@email.com";
private static final int PATRON_BALANCE = 0;
private static final UserAccount PATRON_CREATOR = new Librarian();
private static final boolean PATRON_ONLINE_ACCOUNT = true;
private static final String PATRON_ADDRESS = "123 Smith Street";
private static final boolean PATRON_VALIDATED_ACCOUNT = false;
private static final String PATRON_PASSWORD = "patron123";
private static final int HEAD_ID = 100;


/**
 * This method is used to mock the patron database methods by creating test 
 * objects and storing them in the system so that the 
 * service methods can execute. This is done before each of
 * the tests.
 * @author Zoya Malhi
 */
@BeforeEach
public void setMockOutput() {
    lenient().when(patronDAO.findPatronByUserID(anyInt())).thenAnswer( (InvocationOnMock invocation) -> {
        if(invocation.getArgument(0).equals(PATRON_ID)) {
           
        	Patron patron = new Patron();
            patron.setPatronID(PATRON_ID);
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
    lenient().when(patronDAO.findAll()).thenAnswer( (InvocationOnMock invocation) -> {
    	
    	Patron patron = new Patron();
		patron.setPatronID(PATRON_ID);
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
    
    lenient().when(headLibrarianDAO.findHeadLibrarianByUserID(anyInt())).thenAnswer((InvocationOnMock invocation) -> {
    	if(invocation.getArgument(0).equals(HEAD_ID)) {
    	HeadLibrarian headlibrarian = new HeadLibrarian();
            headlibrarian.setLibrarianID(HEAD_ID);
            headlibrarian.setFirstName("head");
            headlibrarian.setLastName("lib"); 
            headlibrarian.setEmail("headlib@email.com");
            headlibrarian.setPassword("library123");
            headlibrarian.setBalance(0);
            headlibrarian.setOnlineAccount(true);
            headlibrarian.setAddress("100 Library Street");
        
		
		return headlibrarian;
    	}else {
            return null;
        }
	});
 // Whenever anything is saved, just return the parameter object
 		Answer<?> returnParameterAsAnswer = (InvocationOnMock invocation) -> {
 			return invocation.getArgument(0);
 		};
lenient().when(patronDAO.save(any(Patron.class))).thenAnswer(returnParameterAsAnswer);
lenient().when(userAccountDAO.save(any(UserAccount.class))).thenAnswer(returnParameterAsAnswer);
lenient().when(headLibrarianDAO.save(any(HeadLibrarian.class))).thenAnswer(returnParameterAsAnswer);
}

/**
 * This tests verifies if a patron is created successfully in
 * the database.
 * 
 * @author Zoya Malhi
 * @throws Exception
 */
@Test
public void testCreatePatronSuccessful() throws Exception {
	
	Patron patron = null;
	
	try {
		patron = service.createPatron(PATRON_CREATOR, PATRON_FIRST_NAME, PATRON_LAST_NAME, PATRON_ONLINE_ACCOUNT, PATRON_ADDRESS, PATRON_VALIDATED_ACCOUNT, PATRON_PASSWORD, PATRON_BALANCE, PATRON_EMAIL);
	
	}
	catch (IllegalArgumentException e) {
		fail();
		
	}
		//verify error
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
 * This test checks if the first name of the patron is null when 
 * creating a patron in the system. It returns an error if the first name is null.
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
		patron = service.createPatron(PATRON_CREATOR, firstName, PATRON_LAST_NAME, PATRON_ONLINE_ACCOUNT, PATRON_ADDRESS, PATRON_VALIDATED_ACCOUNT, PATRON_PASSWORD, PATRON_BALANCE, PATRON_EMAIL);
		
	}
	catch (IllegalArgumentException e) {
		error = e.getMessage();
		
	}
		assertNull(patron);
		
		//verify error
		assertEquals("First Name cannot be empty!", error);
	
}

/**
 * This test checks if the first name of the patron is empty when 
 * creating a patron in the system. It returns an error if the first name is empty.
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
		patron = service.createPatron(PATRON_CREATOR, firstName, PATRON_LAST_NAME, PATRON_ONLINE_ACCOUNT, PATRON_ADDRESS, PATRON_VALIDATED_ACCOUNT, PATRON_PASSWORD, PATRON_BALANCE, PATRON_EMAIL);
		
	}
	catch (IllegalArgumentException e) {
		error = e.getMessage();
		
	}
		assertNull(patron);
		
		//verify error
		assertEquals("First Name cannot be empty!", error);
	
}

/**
 * This test checks if the last name of the patron is null when 
 * creating a patron in the system. It returns an error if the last name is null.
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
		patron = service.createPatron(PATRON_CREATOR, PATRON_FIRST_NAME, lastName, PATRON_ONLINE_ACCOUNT, PATRON_ADDRESS, PATRON_VALIDATED_ACCOUNT, PATRON_PASSWORD, PATRON_BALANCE, PATRON_EMAIL);
		
	}
	catch (IllegalArgumentException e) {
		error = e.getMessage();
		
	}
		assertNull(patron);
		
		//verify error
		assertEquals("Last Name cannot be empty!", error);
	
}

/**
 * This test checks if the last name of the patron is empty when 
 * creating a patron in the system. It returns an error if the last name is empty.
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
		patron = service.createPatron(PATRON_CREATOR, PATRON_FIRST_NAME, lastName, PATRON_ONLINE_ACCOUNT,  PATRON_ADDRESS, PATRON_VALIDATED_ACCOUNT, PATRON_PASSWORD, PATRON_BALANCE, PATRON_EMAIL);
		
	}
	catch (IllegalArgumentException e) {
		error = e.getMessage();
		
	}
		assertNull(patron);
		
		//verify error
		assertEquals("Last Name cannot be empty!", error);
	
}

/**
 * This test checks if the address of the patron is null when 
 * creating a patron in the system. It returns an error if the address is null.
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
		patron = service.createPatron(PATRON_CREATOR, PATRON_FIRST_NAME, PATRON_LAST_NAME, PATRON_ONLINE_ACCOUNT, address, PATRON_VALIDATED_ACCOUNT, PATRON_PASSWORD, PATRON_BALANCE, PATRON_EMAIL);
		
	}
	catch (IllegalArgumentException e) {
		error = e.getMessage();
	}
		assertNull(patron);
		//verify error
		assertEquals("Address cannot be empty!", error);
	
}

/**
 * This test checks if the address of the patron is empty when 
 * creating a patron in the system. It returns an error if the address is empty.
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
		patron = service.createPatron(PATRON_CREATOR, PATRON_FIRST_NAME, PATRON_LAST_NAME, PATRON_ONLINE_ACCOUNT, address, PATRON_VALIDATED_ACCOUNT, PATRON_PASSWORD, PATRON_BALANCE, PATRON_EMAIL);
		
	}
	catch (IllegalArgumentException e) {
		error = e.getMessage();
		
	}
		assertNull(patron);
		
		//verify error
		assertEquals("Address cannot be empty!", error);
}

/**
 * This test checks if the password of the patron is null when 
 * creating a patron in the system. It returns an error if the password is null.
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
		patron = service.createPatron(PATRON_CREATOR, PATRON_FIRST_NAME, PATRON_LAST_NAME, PATRON_ONLINE_ACCOUNT, PATRON_ADDRESS, PATRON_VALIDATED_ACCOUNT, password, PATRON_BALANCE, PATRON_EMAIL);
		
	}
	catch (IllegalArgumentException e) {
		error = e.getMessage();
	}
		assertNull(patron);
		//verify error
		assertEquals("Password cannot be empty!", error);
	
}

/**
 * This test checks if the password of the patron is empty when 
 * creating a patron in the system. It returns an error if the password is empty.
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
		patron = service.createPatron(PATRON_CREATOR, PATRON_FIRST_NAME, PATRON_LAST_NAME, PATRON_ONLINE_ACCOUNT, PATRON_ADDRESS, PATRON_VALIDATED_ACCOUNT, password, PATRON_BALANCE, PATRON_EMAIL);
		
	}
	catch (IllegalArgumentException e) {
		error = e.getMessage();
		
	}
		assertNull(patron);
		
		//verify error
		assertEquals("Password cannot be empty!", error);
}

/**
 * This test checks if the email of the patron is null when 
 * creating a patron in the system. It returns an error if the email is null.
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
		patron = service.createPatron(PATRON_CREATOR, PATRON_FIRST_NAME, PATRON_LAST_NAME, PATRON_ONLINE_ACCOUNT, PATRON_ADDRESS, PATRON_VALIDATED_ACCOUNT, PATRON_PASSWORD, PATRON_BALANCE, email);
		
	}
	catch (IllegalArgumentException e) {
		error = e.getMessage();
	}
		assertNull(patron);
		//verify error
		assertEquals("Email cannot be empty!", error);
	
}

/**
 * This test checks if the email of the patron is empty when 
 * creating a patron in the system. It returns an error if the email is empty.
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
		patron = service.createPatron(PATRON_CREATOR, PATRON_FIRST_NAME, PATRON_LAST_NAME, PATRON_ONLINE_ACCOUNT, PATRON_ADDRESS, PATRON_VALIDATED_ACCOUNT, PATRON_PASSWORD, PATRON_BALANCE, email);
		
	}
	catch (IllegalArgumentException e) {
		error = e.getMessage();
		
	}
		assertNull(patron);
		
		//verify error
		assertEquals("Email cannot be empty!", error);
}

/**
 * This tests checks if the creator of the patron is null when 
 * creating a patron in the system. It returns an error if the creator is null.
 *  
 * @author Zoya Malhi
 * @throws Exception
 */
@Test
public void testCreatePatronNullCreator() throws Exception {
	String error = null;
	Patron patron = null;
	UserAccount creator = null;
	try {
		patron = service.createPatron(creator, PATRON_FIRST_NAME, PATRON_LAST_NAME, PATRON_ONLINE_ACCOUNT, PATRON_ADDRESS, PATRON_VALIDATED_ACCOUNT, PATRON_PASSWORD, PATRON_BALANCE, PATRON_EMAIL);
	}
	
	catch (IllegalArgumentException e) {
		error = e.getMessage();
	}
		assertNull(patron);
		//verify error
		assertEquals("There needs to be a creator for this method", error);
}

/**
 * This test checks if the creator of the patron is not a librarian when 
 * creating a patron in the system. It returns an error if the creator is not an instance of Librarian.
 *  
 * @author Zoya Malhi
 * @throws Exception
 */
@Test
public void testCreatePatronPatronAsCreator() throws Exception {
	String error = null;
	Patron patron = null;
	Patron creator = new Patron();
	boolean onlineAccount = false;
	
	try {
		patron = service.createPatron(creator, PATRON_FIRST_NAME, PATRON_LAST_NAME, onlineAccount, PATRON_ADDRESS, PATRON_VALIDATED_ACCOUNT, PATRON_PASSWORD, PATRON_BALANCE, PATRON_EMAIL);
	}
	
	catch (IllegalArgumentException e) {
		error = e.getMessage();
	}
		assertNull(patron);
		//verify error
		assertEquals("Only a Librarian can create an in-person account", error);
}

/**
 * This test calls the getPatronByUserID method which 
 * queries the database and returns the patron with the same ID.
 *  
 * @author Zoya Malhi
 * @throws Exception
 */
@Test 
public void testGetPatronFromID() throws Exception {
	assertEquals(PATRON_ID, service.getPatronByUserId(PATRON_ID).getPatronID());
}

/**
 * This tests deletes a patron by calling the deleteAPatronbyUserID 
 * method which queries the database for the patron specified and deletes it from the system.
 * 
 * @author Zoya Malhi
 * @throws Exception
 */
@Test 
public void testDeletePatronByUserIDSuccessful() throws Exception{
     	boolean success = false;
		Patron patron = null;
		try {
			success = service.deleteAPatronbyUserID(PATRON_CREATOR, PATRON_ID);
		
		}
		catch (IllegalArgumentException e) {
			fail();
			
		}
		assertTrue(success);
		assertNull(patron);

	
		patronDAO.deleteAll();
        userAccountDAO.deleteAll();
        headLibrarianDAO.deleteAll();
	}

/**
 * This test attempts to delete a patron specified by
 *  its userID in the system, but returns and error 
 *  instead because the creator does not have the 
 *  credentials to perform the operation.
 * 
 *  
 * @author Zoya Malhi
 * @throws Exception
 */
@Test
public void testDeletePatronByUserIDWrongCreator() throws Exception{
	String error ="";
	Patron patron = patronDAO.findPatronByUserID(PATRON_ID);
	boolean success = false;
	try {
		success = service.deleteAPatronbyUserID(patron, PATRON_ID);
	}
	catch (Exception e) {
		assertEquals("This user does not have the credentials to delete an existing patron", e.getMessage());
	}

    headLibrarianDAO.deleteAll();
	patronDAO.deleteAll(); 
    userAccountDAO.deleteAll();
	
}

/**
 * This test gets a patron from the system given their first and last name. No error is returned.
 *  
 * @author Zoya Malhi
 * @throws Exception
 */
@Test
public void testGetPatronFromFullNameSuccessful() throws Exception {
	
	Patron patron = null;
	try {
		patron = service.getPatronFromFullName(PATRON_FIRST_NAME, PATRON_LAST_NAME);
		
	}
	catch (IllegalArgumentException e) {
		fail();
		
	}
		//verify error
		assertEquals(PATRON_FIRST_NAME, patron.getFirstName());
		assertEquals(PATRON_LAST_NAME, patron.getLastName());
		
		patronDAO.deleteAll();
        userAccountDAO.deleteAll();
}

/**
 * This test gets a patron from the system given their first and last name,
 * but where the first name is null. This returns an error since the first name is null.
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
		
	}
	catch (IllegalArgumentException e) {
		error = e.getMessage();
		
	}
		assertNull(patron);
		
		//verify error
		assertEquals("First Name cannot be empty!", error);
		patronDAO.deleteAll();
        userAccountDAO.deleteAll();
	
}

/**
 * This test gets a patron from the system given their first and last name,
 * but where the first name is empty. This returns an error since the first name is empty.
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
		
	}
	catch (IllegalArgumentException e) {
		error = e.getMessage();
		
	}
		assertNull(patron);
		
		//verify error
		assertEquals("First Name cannot be empty!", error);
	
}

/**
 * This test gets a patron from the system given their first and last name,
 * but where the last name is null. This returns an error since the last name is null.
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
		
	}
	catch (IllegalArgumentException e) {
		error = e.getMessage();
		
	}
		assertNull(patron);
		
		//verify error
		assertEquals("Last Name cannot be empty!", error);
	
}

/**
 * This test gets a patron from the system given their first and last name,
 * but where the last name is empty. This returns an error since the last name is empty.
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
		
	}
	catch (IllegalArgumentException e) {
		error = e.getMessage();
		
	}
		assertNull(patron);
		
		//verify error
		assertEquals("Last Name cannot be empty!", error);
	
}

/**
 * This test gets a patron from the system given their first and last name,
 * but where the first name is null. This returns an error since the first name is null.
 *  
 * @author Zoya Malhi
 * @throws Exception
 */
@Test
public void testSetValidatedAccountSuccessful() throws Exception {
	
	String error = null;
	Patron patron = patronDAO.findPatronByUserID(PATRON_ID);
	
	try {
		patron = service.setValidatedAccount(patron, PATRON_VALIDATED_ACCOUNT, PATRON_CREATOR);
		
	}
	catch (IllegalArgumentException e) {
		error = e.getMessage();
		
	}
		//assertNull(patron);
		assertEquals(PATRON_VALIDATED_ACCOUNT, patron.getValidatedAccount());
		//verify error

		patronDAO.deleteAll();
        userAccountDAO.deleteAll();
	
}


/**
 * This test attempts to set a patron account as validated,
 * but given that the creator is not an instance of a librarian.An error is thrown.
 * 
 * @author Zoya Malhi
 * @throws Exception
 */
@Test
public void testSetValidatedAccountWrongCreator() throws Exception {
	
	String error = null;
	Patron creator = new Patron();
	Patron patron = null;
	
	try {
		patron = service.setValidatedAccount(service.getPatronFromFullName(PATRON_FIRST_NAME, PATRON_LAST_NAME), PATRON_VALIDATED_ACCOUNT, creator);
		
	}
	catch (IllegalArgumentException e) {
		error = e.getMessage();
		
	}
		//verify error
		assertNull(patron);
		assertEquals("Only a Librarian can change the validity of an account", error);

		patronDAO.deleteAll();
        userAccountDAO.deleteAll();
}

/**
 * This test attempts to set a patron account as validated,
 * but given that the creator is null. An error is thrown.
 * 
 * @author Zoya Malhi
 * @throws Exception
 */
@Test
public void testSetValidatedAccountNullCreator() throws Exception {
	
	String error = null;
	
	Patron patron = null;
	Librarian creator = null;
	try {
		patron = service.setValidatedAccount(service.getPatronFromFullName(PATRON_FIRST_NAME, PATRON_LAST_NAME), PATRON_VALIDATED_ACCOUNT, creator);
		
	}
	catch (IllegalArgumentException e) {
		error = e.getMessage();
		
	}
		//verify error
		assertNull(patron);
		assertEquals("The creator cannot be null", error);
		
		patronDAO.deleteAll();
        userAccountDAO.deleteAll();
}

/**
 * This test attempts to set a patron account as validated,
 * but given that the patron is null. An error is thrown.
 * 
 * @author Zoya Malhi
 * @throws Exception
 */
@Test
public void testSetValidatedAccountNullPatron() throws Exception {
	
	String error = null;
	Patron p = null;
	Patron patron = null;
	
	try {
		patron = service.setValidatedAccount(p, PATRON_VALIDATED_ACCOUNT, PATRON_CREATOR);
		
	}
	catch (IllegalArgumentException e) {
		error = e.getMessage();
		
	}
		//verify error
		assertNull(patron);
		assertEquals("The patron cannot be null", error);
		patronDAO.deleteAll();
        userAccountDAO.deleteAll();
}

/**
 * This test gets all patrons from the database by calling the getAllPatrons() method. 
 * A list of patrons in the system is returned.
 * 
 * @author Zoya Malhi
 * @throws Exception
 */
@Test
public void testGetAllPatronsSuccessful() throws Exception {
List<Patron> patrons = null;
String error = "";
	Patron patron = null;
	try {
		patrons = service.getAllPatrons();
		patron = patrons.get(0);
	}
	catch (IllegalArgumentException e) {
		error = e.getMessage();
		
	}
	assertNotNull(patron);
	assertEquals(PATRON_FIRST_NAME, patron.getFirstName());
	assertEquals(PATRON_LAST_NAME, patron.getLastName());
}

	
}
