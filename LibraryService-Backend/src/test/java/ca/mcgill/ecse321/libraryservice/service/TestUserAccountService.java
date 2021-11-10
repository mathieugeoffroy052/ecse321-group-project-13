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

import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.junit.jupiter.api.AfterEach;
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
public class TestUserAccountService {

	
@Mock 
private UserAccountRepository userAccountRepository;

@Mock 
private PatronRepository patronRepository;

@InjectMocks
private LibraryServiceService service;
private static final int USER_ID = 12345;
private static final String USER_FIRST_NAME = "John";
private static final String USER_LAST_NAME = "Smith";
private static final String USER_EMAIL = "johnsmith@email.com";
private static final int USER_BALANCE = 0;
private static final UserAccount USER_CREATOR = new Librarian();
private static final boolean USER_ONLINE_ACCOUNT = true;
private static final String USER_ADDRESS = "123 Smith Street";
private static final boolean USER_VALIDATED_ACCOUNT = false;
private static final String USER_PASSWORD = "patron123";

    // @AfterEach
    // public void clearDatabase() {
    //     //delete all instances from bottom to top of model
    //     holidayRepository.deleteAll();
    //     openingHourRepository.deleteAll();
    //     timeSlotRepository.deleteAll();
    //     headLibrarianRepository.deleteAll();
    //     librarianRepository.deleteAll();
    //     patronRepository.deleteAll();
    //     transactionRepository.deleteAll();
    //     borrowableItemRepository.deleteAll();
    //     librarianRepository.deleteAll();
    //     libraryItemRepository.deleteAll();
    //     userAccountRepository.deleteAll();
    // }

    //UserAccount creator, String aFirstName, String aLastName, boolean aOnlineAccount, LibrarySystem aLibrarySystem, String aAddress, boolean aValidatedAccount, String aPassword, int aBalance, String aEmail
    @BeforeEach
    public void setMockOutput() {
        lenient().when(userAccountRepository.findUserAccountByUserID(anyInt())).thenAnswer( (InvocationOnMock invocation) -> {
            if(invocation.getArgument(0).equals(USER_ID)) {
            
                Patron user = new Patron();
                user.setPatronID(USER_ID);
                user.setFirstName(USER_FIRST_NAME);
                user.setLastName(USER_LAST_NAME); 
                user.setEmail(USER_EMAIL);
                user.setPassword(USER_PASSWORD);
                user.setBalance(USER_BALANCE);
                user.setOnlineAccount(USER_ONLINE_ACCOUNT);
                user.setAddress(USER_ADDRESS);
                user.setValidatedAccount(USER_VALIDATED_ACCOUNT);
                
                return user;
            } else {
                return null;
            }
        });

    }

    /***
     * @author Gabrielle Halpin
     * This tests the changing of a password
     * @throws Exception
     */
    @Test
    public void testChangePasswordSuccessful() throws Exception {
        Patron patron = null;
	
        try {
            patron = service.createPatron(USER_CREATOR, USER_FIRST_NAME, USER_LAST_NAME, USER_ONLINE_ACCOUNT, USER_ADDRESS, USER_VALIDATED_ACCOUNT, USER_PASSWORD, USER_BALANCE, USER_EMAIL);
        }
        catch (IllegalArgumentException e) {
            fail();
        }

        String newPassword = "helloWorld";
        UserAccount account = service.changePassword(newPassword, patron);

        assertEquals(account.getFirstName(), USER_FIRST_NAME);
        assertEquals(account.getLastName(), USER_LAST_NAME);
        assertEquals(account.getOnlineAccount(), USER_ONLINE_ACCOUNT);
        assertEquals(account.getAddress(), USER_ADDRESS);
        assertEquals(account.getPassword(), newPassword);
        assertEquals(account.getBalance(), USER_BALANCE);
        assertEquals(account.getEmail(), USER_EMAIL);

        patronRepository.deleteAll();
        userAccountRepository.deleteAll();
    }

    /**
     * @author gabrielle halpin
     * This test chould try to change the password with invalid input an fail
     */
    @Test
    public void testChangePasswordUnsuccessful() throws Exception {
        Patron patron = null;
        
        try {
            patron = service.createPatron(USER_CREATOR, USER_FIRST_NAME, USER_LAST_NAME, USER_ONLINE_ACCOUNT, USER_ADDRESS, USER_VALIDATED_ACCOUNT, USER_PASSWORD, USER_BALANCE, USER_EMAIL);
            System.err.print("Hello");
        }
        catch (IllegalArgumentException e) {
            fail();
        }
        
        String newPassword = null;
        UserAccount account = null;
        try{
            account = service.changePassword(newPassword, patron);
        }catch(IllegalArgumentException e){
            assertEquals("java.lang.IllegalArgumentException: Password cannot be empty!", e.toString());
        }
        

        assertEquals(patron.getFirstName(), USER_FIRST_NAME);
        assertEquals(patron.getLastName(), USER_LAST_NAME);
        assertEquals(patron.getOnlineAccount(), USER_ONLINE_ACCOUNT);
        assertEquals(patron.getAddress(), USER_ADDRESS);
        assertEquals(patron.getPassword(), USER_PASSWORD);
        assertEquals(patron.getBalance(), USER_BALANCE);
        assertEquals(patron.getEmail(), USER_EMAIL);
            
    }
    /**
     * This test checks if an account can be set to an online account
     * @throws Exception
     */
    @Test
    public void testUpdateOnlineSuccessful() throws Exception {
        Patron patron = null;
	
        try {
            patron = service.createPatron(USER_CREATOR, USER_FIRST_NAME, USER_LAST_NAME, false, USER_ADDRESS, USER_VALIDATED_ACCOUNT, null, USER_BALANCE, null);
        }
        catch (IllegalArgumentException e) {
            fail();
        }

        
        UserAccount account = service.setOnlineAccount(patron, USER_EMAIL, USER_PASSWORD, true, USER_CREATOR);
        assertEquals(account.getFirstName(), USER_FIRST_NAME);
        assertEquals(account.getLastName(), USER_LAST_NAME);
        assertEquals(account.getOnlineAccount(), USER_ONLINE_ACCOUNT);
        assertEquals(account.getAddress(), USER_ADDRESS);
        assertEquals(account.getPassword(), USER_PASSWORD);
        assertEquals(account.getBalance(), USER_BALANCE);
        assertEquals(account.getEmail(), USER_EMAIL);

        patronRepository.deleteAll();
        userAccountRepository.deleteAll();
            
    }
    public void testUpdateOnlineUnsuccessful() throws Exception {
        
            
    }

    public void testUpdateFirstNameSuccessful() throws Exception {
        
            
    }
    public void testUpdateFirstNameUnsuccessful() throws Exception {
        
            
    }

    public void testUpdateLastNameSuccessful() throws Exception {
        
            
    }
    public void testUpdateLastNameUnsuccessful() throws Exception {
        
            
    }

    public void testUpdateAddressSuccessful() throws Exception {
        
            
    }
    public void testUpdateAddresssUnsuccessful() throws Exception {
        
            
    }

    public void testUpdateEmailSuccessful() throws Exception {
        
            
    }
    public void testUpdateEmailUnsuccessful() throws Exception {
        
            
    }
}