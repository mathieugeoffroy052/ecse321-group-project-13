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
import java.util.*;

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
private static final int USER_ID2 = 123456;
private static final String USER_FIRST_NAME = "John";
private static final String USER_LAST_NAME = "Smith";
private static final String USER_EMAIL = "johnsmith@email.com";
private static final int USER_BALANCE = 0;
private static final UserAccount USER_CREATOR = new Librarian();
private static final boolean USER_ONLINE_ACCOUNT = true;
private static final String USER_ADDRESS = "123 Smith Street";
private static final boolean USER_VALIDATED_ACCOUNT = false;
private static final String USER_PASSWORD = "patron123";


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
                
            } 
            else if(invocation.getArgument(0).equals(USER_ID2)) {
            
                Patron user = new Patron();
                user.setPatronID(USER_ID2);
                user.setFirstName(USER_FIRST_NAME);
                user.setLastName(USER_LAST_NAME); 
                user.setEmail(null);
                user.setPassword(null);
                user.setBalance(USER_BALANCE);
                user.setOnlineAccount(false);
                user.setAddress(USER_ADDRESS);
                user.setValidatedAccount(USER_VALIDATED_ACCOUNT);
                
                return user;
            }
            else {
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
        UserAccount patron = null;
	
        try {
            patron = userAccountRepository.findUserAccountByUserID(12345);
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
        UserAccount patron = null;
        
        try {
            patron = userAccountRepository.findUserAccountByUserID(123456);        }
        catch (IllegalArgumentException e) {
            fail();
        }
        
        String newPassword = null;
        UserAccount account = null;
        try{
            account = service.changePassword(newPassword, patron);
        }catch(IllegalArgumentException e){
            assertEquals("java.lang.IllegalArgumentException: The account must be an online account", e.toString());
        }
        

        assertEquals(patron.getFirstName(), USER_FIRST_NAME);
        assertEquals(patron.getLastName(), USER_LAST_NAME);
        assertEquals(patron.getOnlineAccount(), false);
        assertEquals(patron.getAddress(), USER_ADDRESS);
        assertEquals(patron.getPassword(), null);
        assertEquals(patron.getBalance(), USER_BALANCE);
        assertEquals(patron.getEmail(), null);
        
        patronRepository.deleteAll();
        userAccountRepository.deleteAll();
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
    /**
     * @author Gabrielle Halpin
     * This test give incorrect input for the online account and should through an error
     * @throws Exception
     */
    @Test
    public void testUpdateOnlineUnsuccessful() throws Exception {
        Patron patron = null;
        
        try {
            patron = service.createPatron(USER_CREATOR, USER_FIRST_NAME, USER_LAST_NAME, false, USER_ADDRESS, USER_VALIDATED_ACCOUNT, null, USER_BALANCE, null);
        }
        catch (IllegalArgumentException e) {
            fail();
        }
        
        UserAccount account = null;
        try{
            account = service.setOnlineAccount(patron, null, USER_PASSWORD, true, USER_CREATOR);
        }catch(IllegalArgumentException e){
            assertEquals("java.lang.IllegalArgumentException: Email cannot be empty!", e.toString());
        }
        

        assertEquals(patron.getFirstName(), USER_FIRST_NAME);
        assertEquals(patron.getLastName(), USER_LAST_NAME);
        assertEquals(patron.getOnlineAccount(), false);
        assertEquals(patron.getAddress(), USER_ADDRESS);
        assertEquals(patron.getPassword(), null);
        assertEquals(patron.getBalance(), USER_BALANCE);
        assertEquals(patron.getEmail(), null);

        patronRepository.deleteAll();
        userAccountRepository.deleteAll();
            
    }

    /**
     * @author Gabrielle Halpin
     * This tests checks a successful firstName change
     * @throws Exception
     */
    @Test
    public void testUpdateFirstNameSuccessful() throws Exception {
        UserAccount patron = null;
	
        try {
            patron = userAccountRepository.findUserAccountByUserID(12345);
        }
        catch (IllegalArgumentException e) {
            fail();
        }
        String newName = "BOB";
        UserAccount account = service.changeFirstName(newName, patron);
        assertEquals(patron.getFirstName(), newName);
        assertEquals(patron.getLastName(), USER_LAST_NAME);
        assertEquals(patron.getOnlineAccount(), USER_ONLINE_ACCOUNT);
        assertEquals(patron.getAddress(), USER_ADDRESS);
        assertEquals(patron.getPassword(), USER_PASSWORD);
        assertEquals(patron.getBalance(), USER_BALANCE);
        assertEquals(patron.getEmail(), USER_EMAIL);

        patronRepository.deleteAll();
        userAccountRepository.deleteAll();
            
    }
    /**
     * @author Gabrielle Halpin
     * This tests the unsuccessful change of firstName by passing the sameName
     * @throws Exception
     */
    @Test
    public void testUpdateFirstNameUnsuccessful() throws Exception {
        UserAccount patron = null;
        
        try {
            patron = userAccountRepository.findUserAccountByUserID(12345);        }
        catch (IllegalArgumentException e) {
            fail();
        }
  
        UserAccount account = null;
        try{
            account = service.changeFirstName(USER_FIRST_NAME, patron);
        }catch(IllegalArgumentException e){
            assertEquals("java.lang.IllegalArgumentException: This is already your firstName.", e.toString());
        }
        

        assertEquals(patron.getFirstName(), USER_FIRST_NAME);
        assertEquals(patron.getLastName(), USER_LAST_NAME);
        assertEquals(patron.getOnlineAccount(), USER_ONLINE_ACCOUNT);
        assertEquals(patron.getAddress(), USER_ADDRESS);
        assertEquals(patron.getPassword(), USER_PASSWORD);
        assertEquals(patron.getBalance(), USER_BALANCE);
        assertEquals(patron.getEmail(), USER_EMAIL);

        patronRepository.deleteAll();
        userAccountRepository.deleteAll();
            
    }

    /**
     * This methods test the users ability to cahnge their lastname, the test should be successful. 
     * @author Gabrielle Halpin
     * @throws Exception
     */
    @Test
    public void testUpdateLastNameSuccessful() throws Exception {
        UserAccount patron = null;
	
        try {
            patron = userAccountRepository.findUserAccountByUserID(12345);
        }
        catch (IllegalArgumentException e) {
            fail();
        }
        String newName = "BOB";
        UserAccount account = service.changeLastName(newName, patron);
        assertEquals(patron.getFirstName(), USER_FIRST_NAME);
        assertEquals(patron.getLastName(), newName);
        assertEquals(patron.getOnlineAccount(), USER_ONLINE_ACCOUNT);
        assertEquals(patron.getAddress(), USER_ADDRESS);
        assertEquals(patron.getPassword(), USER_PASSWORD);
        assertEquals(patron.getBalance(), USER_BALANCE);
        assertEquals(patron.getEmail(), USER_EMAIL);

        patronRepository.deleteAll();
        userAccountRepository.deleteAll();
            
    }

    /**
     * This method tests teh unsuccessful change of the lastName
     * @author Gabrielle Halpin
     * @throws Exception
     */
    @Test
    public void testUpdateLastNameUnsuccessful() throws Exception {
        UserAccount patron = null;
        
        try {
            patron = userAccountRepository.findUserAccountByUserID(12345);        }
        catch (IllegalArgumentException e) {
            fail();
        }
  
        UserAccount account = null;
        try{
            account = service.changeLastName(USER_LAST_NAME, patron);
        }catch(IllegalArgumentException e){
            assertEquals("java.lang.IllegalArgumentException: This is already your lastname.", e.toString());
        }
        

        assertEquals(patron.getFirstName(), USER_FIRST_NAME);
        assertEquals(patron.getLastName(), USER_LAST_NAME);
        assertEquals(patron.getOnlineAccount(), USER_ONLINE_ACCOUNT);
        assertEquals(patron.getAddress(), USER_ADDRESS);
        assertEquals(patron.getPassword(), USER_PASSWORD);
        assertEquals(patron.getBalance(), USER_BALANCE);
        assertEquals(patron.getEmail(), USER_EMAIL);

        patronRepository.deleteAll();
        userAccountRepository.deleteAll();
            
    }

    /**
     * This method test the successful change of the user's address
     * @throws Exception
     */
    @Test
    public void testUpdateAddressSuccessful() throws Exception {
        UserAccount patron = null;
	
        try {
            patron = userAccountRepository.findUserAccountByUserID(12345);
        }
        catch (IllegalArgumentException e) {
            fail();
        }
        String address = "2 Avenue bobRoss";
        UserAccount account = service.changeAddress(address, patron);
        assertEquals(patron.getFirstName(), USER_FIRST_NAME);
        assertEquals(patron.getLastName(), USER_LAST_NAME);
        assertEquals(patron.getOnlineAccount(), USER_ONLINE_ACCOUNT);
        assertEquals(patron.getAddress(), address);
        assertEquals(patron.getPassword(), USER_PASSWORD);
        assertEquals(patron.getBalance(), USER_BALANCE);
        assertEquals(patron.getEmail(), USER_EMAIL);

        patronRepository.deleteAll();
        userAccountRepository.deleteAll();
            
    }

    /**
     * This methods tests the unsuccessful update of the user's address 
     * This test will fail because the address is null
     * @author Gabrielle Halpin
     */
    @Test
    public void testUpdateAddresssUnsuccessful() throws Exception {
        UserAccount patron = null;
        
        try {
            patron = userAccountRepository.findUserAccountByUserID(12345);
        }
        catch (IllegalArgumentException e) {
            fail();
        }
  
        UserAccount account = null;
        String newAddress = null;
        try{
            account = service.changeAddress(newAddress, patron);
        }catch(IllegalArgumentException e){
            assertEquals("java.lang.IllegalArgumentException: Address cannot be empty!", e.toString());
        }
        

        assertEquals(patron.getFirstName(), USER_FIRST_NAME);
        assertEquals(patron.getLastName(), USER_LAST_NAME);
        assertEquals(patron.getOnlineAccount(), USER_ONLINE_ACCOUNT);
        assertEquals(patron.getAddress(), USER_ADDRESS);
        assertEquals(patron.getPassword(), USER_PASSWORD);
        assertEquals(patron.getBalance(), USER_BALANCE);
        assertEquals(patron.getEmail(), USER_EMAIL);

        patronRepository.deleteAll();
        userAccountRepository.deleteAll();
            
    }

    /**
     * @author Gabrielle Halpin
     * This tests the successful update of teh user's email.
     * @throws Exception
     */
    @Test
    public void testUpdateEmailSuccessful() throws Exception {
        UserAccount patron = null;
	
        try {
            patron = userAccountRepository.findUserAccountByUserID(12345);
        }
        catch (IllegalArgumentException e) {
            fail();
        }
        String email = "bob@email.com";
        UserAccount account = service.changeEmail(email, patron);
        assertEquals(patron.getFirstName(), USER_FIRST_NAME);
        assertEquals(patron.getLastName(), USER_LAST_NAME);
        assertEquals(patron.getOnlineAccount(), USER_ONLINE_ACCOUNT);
        assertEquals(patron.getAddress(), USER_ADDRESS);
        assertEquals(patron.getPassword(), USER_PASSWORD);
        assertEquals(patron.getBalance(), USER_BALANCE);
        assertEquals(patron.getEmail(), email);

        patronRepository.deleteAll();
        userAccountRepository.deleteAll();
            
    }

    /**
     * @author Gabrielle Halpin
     * This method test the unsuccessful update of the Email.
     * This test will fail because this account is not an online account and does not have an email.
     * @throws Exception
     */
    @Test
    public void testUpdateEmailUnsuccessful() throws Exception {
        UserAccount patron = null;
        
        try {
            patron = userAccountRepository.findUserAccountByUserID(123456);
        }
        catch (IllegalArgumentException e) {
            fail();
        }
  
        String newEmail = "eamil@email.com";
        try{
            UserAccount account = service.changeEmail(newEmail, patron);
        }catch(IllegalArgumentException e){
            assertEquals("java.lang.IllegalArgumentException: The account must be an online account", e.toString());
        }
        

        assertEquals(patron.getFirstName(), USER_FIRST_NAME);
        assertEquals(patron.getLastName(), USER_LAST_NAME);
        assertEquals(patron.getOnlineAccount(), false);
        assertEquals(patron.getAddress(), USER_ADDRESS);
        assertEquals(patron.getPassword(), null);
        assertEquals(patron.getBalance(), USER_BALANCE);
        assertEquals(patron.getEmail(), null);

        patronRepository.deleteAll();
        userAccountRepository.deleteAll();
            
    }

    // /**
    //  * @author Gabrielle Halpin
    //  * This test checks if all Users are successfully retrieved from the database
    //  * @throws Exception
    //  */
    // @Test
    // public void testGetAllUsers() throws Exception {
    //     UserAccount patron1 = null;
    //     UserAccount patron2 = null;
        
    //     try {
    //         patron1 = userAccountRepository.findUserAccountByUserID(12345);
    //         patron2 = userAccountRepository.findUserAccountByUserID(123456);
    //     }
    //     catch (IllegalArgumentException e) {
    //         fail();
    //     }
    //     List<UserAccount>  accounts = null;
    //     try{
    //         accounts = service.getAllUsers();
    //     }catch(IllegalArgumentException e){
    //         throw new Exception("could not retrieve users");
    //     }
        
    //     assertEquals(2, accounts.size());

    //     patronRepository.deleteAll();
    //     userAccountRepository.deleteAll();
            
    // }

    //     /**
    //  * @author Gabrielle Halpin
    //  * This test checks if all Users are successfully retrieved from the database
    //  * @throws Exception
    //  */
    // @Test
    // public void testGetUserByID() throws Exception {
    //     UserAccount patron1 = null;

    //     try {
    //         patron1 = userAccountRepository.findUserAccountByUserID(12345);
    //     }
    //     catch (IllegalArgumentException e) {
    //         fail();
    //     }
    //     List<UserAccount>  accounts = null;
    //     try{
    //         accounts = service.getAllUsers();
    //     }catch(IllegalArgumentException e){
    //         throw new Exception("could not retrieve users");
    //     }
        
    //     assertEquals(3, accounts.size());

    //     patronRepository.deleteAll();
    //     userAccountRepository.deleteAll();
            
    // }
}