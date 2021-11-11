package ca.mcgill.ecse321.libraryservice.service;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
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

@Test 
public void testGetPatronFromID() throws Exception {
	assertEquals(PATRON_ID, service.getPatronByUserId(PATRON_ID).getPatronID());
}

@Test 
public void testDeletePatronByUserIDSuccessful() throws Exception{
     	boolean success = false;
		Patron patron = null;
		try {
			success = service.deleteAPatronbyUserID(service.getHeadLibrarian(), PATRON_ID);
		
		}
		catch (IllegalArgumentException e) {
			fail();
			
		}
		assertTrue(success);
//		assertNotNull(patron);
//		Patron patron2 = service.getPatronByUserId(PATRON_ID);
//		assertEquals(patron2.getFirstName(), patron.getFirstName());
//		assertEquals(patron2.getLastName(), patron.getLastName());
//		assertEquals(patron2.getAddress(), patron.getAddress());
//		assertEquals(patron2.getOnlineAccount(), patron.getOnlineAccount());
//		assertEquals(patron2.getValidatedAccount(), patron.getValidatedAccount());
//		assertEquals(patron2.getEmail(), patron.getEmail());
//		assertEquals(patron2.getPassword(), patron.getPassword());
//		assertEquals(patron2.getBalance(), patron.getBalance());
//		
		patronDAO.deleteAll();
        userAccountDAO.deleteAll();
        headLibrarianDAO.deleteAll();
	}

@Test
public void testDeletePatronByUserIDWrongCreator() throws Exception{
	String error ="";
	Patron creator = new Patron();
	Patron patron = null;
	boolean success = false;
	try {
		success = service.deleteAPatronbyUserID(creator, PATRON_ID);
	
	}
	catch (IllegalArgumentException e) {
		fail();
		//error = e.getMessage();
		
	}
	assertTrue(success);
//	assertNotNull(patron);
//	Patron patron2 = service.getPatronByUserId(PATRON_ID);
//	assertEquals(patron2.getFirstName(), patron.getFirstName());
//	assertEquals(patron2.getLastName(), patron.getLastName());
//	assertEquals(patron2.getAddress(), patron.getAddress());
//	assertEquals(patron2.getOnlineAccount(), patron.getOnlineAccount());
//	assertEquals(patron2.getValidatedAccount(), patron.getValidatedAccount());
//	assertEquals(patron2.getEmail(), patron.getEmail());
//	assertEquals(patron2.getPassword(), patron.getPassword());
//	assertEquals(patron2.getBalance(), patron.getBalance());
//	
//	assertNotNull(patron);
	assertEquals("This user does not have the credentials to delete an existing patron", error);

    headLibrarianDAO.deleteAll();
	patronDAO.deleteAll(); 
    userAccountDAO.deleteAll();
	
}
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
@Test
public void testSetValidatedAccountSuccessful() throws Exception {
	
	String error = null;
	Patron patron = null;
	
	try {
		patron = service.setValidatedAccount(service.getAllPatrons().get(0), PATRON_VALIDATED_ACCOUNT, PATRON_CREATOR);
		
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
