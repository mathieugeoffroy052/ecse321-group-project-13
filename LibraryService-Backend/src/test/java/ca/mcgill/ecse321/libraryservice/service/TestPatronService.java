package ca.mcgill.ecse321.libraryservice.service;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
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
import java.util.Calendar;

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
private static final boolean PATRON_VALIDATED_ACCOUNT = true;
private static final String PATRON_PASSWORD = "patron123";



//UserAccount creator, String aFirstName, String aLastName, boolean aOnlineAccount, LibrarySystem aLibrarySystem, String aAddress, boolean aValidatedAccount, String aPassword, int aBalance, String aEmail
@BeforeEach
public void setMockOutput() {
    lenient().when(patronDAO.findPatronByUserID(anyInt())).thenAnswer( (InvocationOnMock invocation) -> {
        if(invocation.getArgument(0).equals(PATRON_ID)) {
           
        	Patron patron = new Patron();
            LibrarySystem librarySystem = new LibrarySystem();
            patron.setPatronID(PATRON_ID);
            patron.setFirstName(PATRON_FIRST_NAME);
            patron.setLastName(PATRON_LAST_NAME); 
            patron.setEmail(PATRON_EMAIL);
            patron.setPassword(PATRON_PASSWORD);
            patron.setBalance(PATRON_BALANCE);
            patron.setOnlineAccount(PATRON_ONLINE_ACCOUNT);
            patron.setAddress(PATRON_ADDRESS);
            patron.setValidatedAccount(PATRON_VALIDATED_ACCOUNT);
            patron.setLibrarySystem(librarySystem);
            
            return patron;
        } else {
            return null;
        }
    });
    
 // Whenever anything is saved, just return the parameter object
 		Answer<?> returnParameterAsAnswer = (InvocationOnMock invocation) -> {
 			return invocation.getArgument(0);
 		};
lenient().when(patronDAO.save(any(Patron.class))).thenAnswer(returnParameterAsAnswer);
 	
}
@Test
public void testCreatePatronNull() throws Exception {
	String error = null;
	Patron patron = null;
	String firstName = null;
	try {
		LibrarySystem ls = new LibrarySystem();
		patron = service.createPatron(PATRON_CREATOR, firstName, PATRON_LAST_NAME, PATRON_ONLINE_ACCOUNT, ls, PATRON_ADDRESS, PATRON_VALIDATED_ACCOUNT, PATRON_PASSWORD, PATRON_BALANCE, PATRON_EMAIL);
		
	}
	catch (IllegalArgumentException e) {
		error = e.getMessage();
		
	}
		assertNull(patron);
		
		//verify error
		assertEquals("First Name cannot be empty!", error);
	
}

	
	
}
