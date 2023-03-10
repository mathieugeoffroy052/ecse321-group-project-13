package ca.mcgill.ecse321.libraryservice.service;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.lenient;

import java.util.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import ca.mcgill.ecse321.libraryservice.model.*;
import ca.mcgill.ecse321.libraryservice.dao.*;

@ExtendWith(MockitoExtension.class)
public class TestUserAccountService {

    @Mock
    private UserAccountRepository userAccountDao;

    @Mock
    private UserAccountRepository userAccountRepository;

    @Mock
    private PatronRepository patronRepository;

    private static final int CREATOR_ID = 123;
    private static final String CREATOR_FIRST_NAME = "Tristan";
    private static final String CREATOR_LAST_NAME = "Golden";
    private static final String CREATOR_EMAIL = "creator@email.com";
    private static final int CREATOR_BALANCE = 0;
    private static final boolean CREATOR_ONLINE_ACCOUNT = true;
    private static final String CREATOR_ADDRESS = "1234 ave jack";
    private static final String CREATOR_PASSWORD = "creator123";

    private static final int USER_ID = 12345;
    private static final int USER_ID2 = 123456;
    private static final String USER_FIRST_NAME = "John";
    private static final String USER_LAST_NAME = "Smith";
    private static final String USER_EMAIL = "johnsmith@email.com";
    private static final int USER_BALANCE = 0;
    private static final boolean USER_ONLINE_ACCOUNT = true;
    private static final String USER_ADDRESS = "123 Smith Street";
    private static final boolean USER_VALIDATED_ACCOUNT = false;
    private static final String USER_PASSWORD = "patron123";

    @InjectMocks
    private LibraryServiceService service;

    /* Patron attributes */
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
     * 
     * @author Amani Jammoul, Gabrielle Halpin and Ramin Akhavan-Sarraf
     */
    @BeforeEach
    public void setMockOutput() {
        lenient().when(userAccountRepository.findUserAccountByUserID(anyInt()))
                .thenAnswer((InvocationOnMock invocation) -> {
                    if (invocation.getArgument(0).equals(USER_ID)) {

                        Patron user = new Patron();
                        user.setUserID(USER_ID);
                        user.setFirstName(USER_FIRST_NAME);
                        user.setLastName(USER_LAST_NAME);
                        user.setEmail(USER_EMAIL);
                        user.setPassword(USER_PASSWORD);
                        user.setBalance(USER_BALANCE);
                        user.setOnlineAccount(USER_ONLINE_ACCOUNT);
                        user.setAddress(USER_ADDRESS);
                        user.setValidatedAccount(USER_VALIDATED_ACCOUNT);

                        return user;

                    } else if (invocation.getArgument(0).equals(USER_ID2)) {

                        Patron user = new Patron();
                        user.setUserID(USER_ID2);
                        user.setFirstName(USER_FIRST_NAME);
                        user.setLastName(USER_LAST_NAME);
                        user.setEmail(null);
                        user.setPassword(null);
                        user.setBalance(USER_BALANCE);
                        user.setOnlineAccount(false);
                        user.setAddress(USER_ADDRESS);
                        user.setValidatedAccount(USER_VALIDATED_ACCOUNT);

                        return user;
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
                    } else if (invocation.getArgument(0).equals(VALID_PATRON_USER_ID)) {
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
                    } else if (invocation.getArgument(0).equals(INVALID_PATRON_USER_ID)) {
                        Patron pAccount = new Patron();
                        pAccount.setFirstName(PATRON_FIRST_NAME_2);
                        pAccount.setLastName(PATRON_LAST_NAME_2);
                        pAccount.setValidatedAccount(!PATRON_VALIDATED);
                        pAccount.setOnlineAccount(ONLINE);
                        return pAccount;
                    } else {
                        return null;
                    }
                });
        lenient().when(userAccountRepository.findAll()).thenAnswer((InvocationOnMock invocation) -> {
            List<UserAccount> accounts = new ArrayList<UserAccount>();
            Patron user1 = new Patron();
            user1.setUserID(USER_ID);
            user1.setFirstName(USER_FIRST_NAME);
            user1.setLastName(USER_LAST_NAME);
            user1.setEmail(USER_EMAIL);
            user1.setPassword(USER_PASSWORD);
            user1.setBalance(USER_BALANCE);
            user1.setOnlineAccount(USER_ONLINE_ACCOUNT);
            user1.setAddress(USER_ADDRESS);
            user1.setValidatedAccount(USER_VALIDATED_ACCOUNT);

            Librarian user2 = new Librarian();
            user2.setUserID(USER_ID2);
            user2.setFirstName(USER_FIRST_NAME);
            user2.setLastName(USER_LAST_NAME);
            user2.setEmail(null);
            user2.setPassword(null);
            user2.setBalance(USER_BALANCE);
            user2.setOnlineAccount(false);
            user2.setAddress(USER_ADDRESS);

            Patron pAccount = new Patron();
            pAccount.setFirstName(PATRON_FIRST_NAME);
            pAccount.setLastName(PATRON_LAST_NAME);
            pAccount.setValidatedAccount(PATRON_VALIDATED);
            pAccount.setOnlineAccount(ONLINE);
            pAccount.setEmail(PATRON_EMAIL);
            pAccount.setPassword(PATRON_PASSWORD);
            pAccount.setBalance(PATRON_BALANCE);
            pAccount.setAddress(PATRON_ADDRESS);
            accounts.add(pAccount);

            Patron pAccount2 = new Patron();
            pAccount2.setFirstName(PATRON_FIRST_NAME_2);
            pAccount2.setLastName(PATRON_LAST_NAME_2);
            pAccount2.setValidatedAccount(!PATRON_VALIDATED);
            pAccount2.setOnlineAccount(ONLINE);
            accounts.add(pAccount2);

            accounts.add(user1);
            accounts.add(user2);

            return accounts;
        });
        Answer<?> returnParameterAsAnswer = (InvocationOnMock invocation) -> {
            return invocation.getArgument(0);
        };
        lenient().when(userAccountRepository.save(any(UserAccount.class))).thenAnswer(returnParameterAsAnswer);

    }

    /**
     * This test case checks to see if the appropriate user account is returned when
     * searching using a first name and last name, for the
     * getUserAccountFromFullName service method
     * 
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
     * Fail test case - Checks to make sure a first name field is needed when
     * searching for a user of the system using their first name and last name (for
     * the getUserAccountFromFullName service method)
     * 
     * @throws Exception
     */
    @Test
    public void testFailedGetUserAccountFromFullNameEmptyFirstNameError() throws Exception {

        String error = "";
        try {
            service.getUserAccountFromFullName(null, PATRON_LAST_NAME);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }
        assertEquals(error, "First name cannot be empty!");

    }

    /**
     * Fail test case - Checks to make sure a last name field is needed when
     * searching for a user of the system using their first name and last name (for
     * the getUserAccountFromFullName service method)
     * 
     * @throws Exception
     */
    @Test
    public void testFailedGetUserAccountFromFullNameEmptyLastNameError() throws Exception {

        String error = "";
        try {
            service.getUserAccountFromFullName(PATRON_FIRST_NAME, null);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }
        assertEquals(error, "Last name cannot be empty!");

    }

    /**
     * Fail test case - Checks to make sure no user account is returned when the
     * user cannot be found in the system when using the getUserAccountFromFullName
     * service method
     * 
     * @throws Exception
     */
    @Test
    public void testFailedGetUserAccountFromFullNameFakeNameError() throws Exception {

        String error = "";
        try {
            service.getUserAccountFromFullName(PATRON_FAKE_FIRST_NAME, PATRON_FAKE_LAST_NAME);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }
        assertEquals(error, "No user found with this name! ");

    }

    /**
     * This helper method is called to do an extensive check of a user account's
     * details when a user account is found in any of the test cases
     * 
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

    /***
     * @author Gabrielle Halpin This tests the changing of a password
     * @throws Exception
     */
    @Test
    public void testChangePasswordSuccessful() throws Exception {
        UserAccount patron = null;

        try {
            patron = userAccountRepository.findUserAccountByUserID(12345);
        } catch (IllegalArgumentException e) {
            fail();
        }

        String newPassword = "helloWorld";
        UserAccount account = service.changePassword(newPassword, patron.getUserID());

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
     * @author gabrielle halpin This test chould try to change the password for
     *         non-online account and will fail
     */
    @Test
    public void testChangePasswordUnsuccessfulNotOnlineAccount() throws Exception {
        UserAccount patron = null;

        try {
            patron = userAccountRepository.findUserAccountByUserID(123456);
        } catch (IllegalArgumentException e) {
            fail();
        }

        String newPassword = null;
        try {
            service.changePassword(newPassword, patron.getUserID());
        } catch (IllegalArgumentException e) {
            assertEquals("The account must be an online account", e.getMessage());
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
     * @author gabrielle halpin This test chould try to change the password with
     *         null password
     */
    @Test
    public void testChangePasswordUnsuccessfulEmptyPassword() throws Exception {
        UserAccount patron = null;

        try {
            patron = userAccountRepository.findUserAccountByUserID(12345);
        } catch (IllegalArgumentException e) {
            fail();
        }

        String newPassword = null;
        try {
            service.changePassword(newPassword, patron.getUserID());
        } catch (IllegalArgumentException e) {
            assertEquals("Password cannot be empty!", e.getMessage());
        }

        assertEquals(patron.getFirstName(), USER_FIRST_NAME);
        assertEquals(patron.getLastName(), USER_LAST_NAME);
        assertEquals(patron.getOnlineAccount(), true);
        assertEquals(patron.getAddress(), USER_ADDRESS);
        assertEquals(patron.getPassword(), USER_PASSWORD);
        assertEquals(patron.getBalance(), USER_BALANCE);
        assertEquals(patron.getEmail(), USER_EMAIL);

        patronRepository.deleteAll();
        userAccountRepository.deleteAll();
    }

    /**
     * @author gabrielle halpin This test chould try to change the password with
     *         null account
     */
    @Test
    public void testChangePasswordUnsuccessfulNullAccount() throws Exception {

        String newPassword = "Hello";
        try {
            service.changePassword(newPassword, 1435);
        } catch (IllegalArgumentException e) {
            assertEquals("The patron does not exist", e.getMessage());
        }

        patronRepository.deleteAll();
        userAccountRepository.deleteAll();
    }

    /**
     * This test checks if an account can be set to an online account
     * 
     * @throws Exception
     */
    @Test
    public void testUpdateOnlineSuccessful() throws Exception {
        UserAccount patron = null;

        try {
            patron = userAccountRepository.findUserAccountByUserID(123456);
        } catch (IllegalArgumentException e) {
            fail();
        }

        patron = service.setOnlineAccount(patron.getUserID(), USER_EMAIL, USER_PASSWORD, true, CREATOR_ID);
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
     * @author Gabrielle Halpin This test gives a null email for the online account
     *         and should throw an error
     * @throws Exception
     */
    @Test
    public void testUpdateOnlineUnsuccessfulEmptyEmail() throws Exception {
        UserAccount patron = null;

        try {
            patron = userAccountRepository.findUserAccountByUserID(123456);
        } catch (IllegalArgumentException e) {
            fail();
        }

        try {
            service.setOnlineAccount(patron.getUserID(), null, USER_PASSWORD, true, CREATOR_ID);
        } catch (IllegalArgumentException e) {
            assertEquals("Email cannot be empty!", e.getMessage());
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
     * @author Gabrielle Halpin This test takes an empty password for the online
     *         account and should through an error
     * @throws Exception
     */
    @Test
    public void testUpdateOnlineUnsuccessfulEmptyPassword() throws Exception {
        UserAccount patron = null;

        try {
            patron = userAccountRepository.findUserAccountByUserID(123456);
        } catch (IllegalArgumentException e) {
            fail();
        }

        try {
            service.setOnlineAccount(patron.getUserID(), USER_EMAIL, null, true, CREATOR_ID);
        } catch (IllegalArgumentException e) {
            assertEquals("Password cannot be empty!", e.getMessage());
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
     * @author Gabrielle Halpin This test takes a null creator for the online
     *         account and should throw an error
     * @throws Exception
     */
    @Test
    public void testUpdateOnlineUnsuccessfulEmptyCreator() throws Exception {
        UserAccount patron = null;

        try {
            patron = userAccountRepository.findUserAccountByUserID(123456);
        } catch (IllegalArgumentException e) {
            fail();
        }

        try {
            service.setOnlineAccount(patron.getUserID(), USER_EMAIL, USER_PASSWORD, true, 6797);
        } catch (IllegalArgumentException e) {
            assertEquals("The creator does not exist", e.getMessage());
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
     * @author Gabrielle Halpin This test takes a null account for the online
     *         account and should throw an error
     * @throws Exception
     */
    @Test
    public void testUpdateOnlineUnsuccessfulNullAccount() throws Exception {
        UserAccount patron = null;

        try {
            patron = userAccountRepository.findUserAccountByUserID(123456);
        } catch (IllegalArgumentException e) {
            fail();
        }

        try {
            service.setOnlineAccount(6879, USER_EMAIL, USER_PASSWORD, true, CREATOR_ID);
        } catch (IllegalArgumentException e) {
            assertEquals("The patron does not exist", e.getMessage());
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
     * @author Gabrielle Halpin This tests checks a successful firstName change
     * @throws Exception
     */
    @Test
    public void testUpdateFirstNameSuccessful() throws Exception {
        UserAccount patron = null;

        try {
            patron = userAccountRepository.findUserAccountByUserID(12345);
        } catch (IllegalArgumentException e) {
            fail();
        }
        String newName = "BOB";
        patron = service.changeFirstName(newName, patron.getUserID());
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
     * @author Gabrielle Halpin This tests the unsuccessful change of firstName by
     *         passing the sameName
     * @throws Exception
     */
    @Test
    public void testUpdateFirstNameUnsuccessfulSameName() throws Exception {
        UserAccount patron = null;

        try {
            patron = userAccountRepository.findUserAccountByUserID(12345);
        } catch (IllegalArgumentException e) {
            fail();
        }

        try {
            service.changeFirstName(USER_FIRST_NAME, patron.getUserID());
        } catch (IllegalArgumentException e) {
            assertEquals("This is already your firstName.", e.getMessage());
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
     * @author Gabrielle Halpin This tests the unsuccessful change of firstName by
     *         passing an empty name
     * @throws Exception
     */
    @Test
    public void testUpdateFirstNameUnsuccessfulEmptyName() throws Exception {
        UserAccount patron = null;

        try {
            patron = userAccountRepository.findUserAccountByUserID(12345);
        } catch (IllegalArgumentException e) {
            fail();
        }

        try {
            service.changeFirstName("", patron.getUserID());
        } catch (IllegalArgumentException e) {
            assertEquals("firstName cannot be empty!", e.getMessage());
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
     * @author Gabrielle Halpin This tests the unsuccessful change of firstName by
     *         passing a null account
     * @throws Exception
     */
    @Test
    public void testUpdateFirstNameUnsuccessfulNullAccount() throws Exception {
        UserAccount patron = null;

        try {
            patron = userAccountRepository.findUserAccountByUserID(12345);
        } catch (IllegalArgumentException e) {
            fail();
        }

        try {
            service.changeFirstName("BOB", 1754);
        } catch (IllegalArgumentException e) {
            assertEquals("The patron does not exist", e.getMessage());
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
     * This methods test the users ability to cahnge their lastname, the test should
     * be successful.
     * 
     * @author Gabrielle Halpin
     * @throws Exception
     */
    @Test
    public void testUpdateLastNameSuccessful() throws Exception {
        UserAccount patron = null;

        try {
            patron = userAccountRepository.findUserAccountByUserID(12345);
        } catch (IllegalArgumentException e) {
            fail();
        }
        String newName = "BOB";
        patron = service.changeLastName(newName, patron.getUserID());
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
     * This method tests teh unsuccessful change of the lastName by passing the same
     * name
     * 
     * @author Gabrielle Halpin
     * @throws Exception
     */
    @Test
    public void testUpdateLastNameUnsuccessfulSameLastName() throws Exception {
        UserAccount patron = null;

        try {
            patron = userAccountRepository.findUserAccountByUserID(12345);
        } catch (IllegalArgumentException e) {
            fail();
        }

        try {
            service.changeLastName(USER_LAST_NAME, patron.getUserID());
        } catch (IllegalArgumentException e) {
            assertEquals("This is already your lastname.", e.getMessage());
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
     * This method tests teh unsuccessful change of the lastName due to empty last
     * name
     * 
     * @author Gabrielle Halpin
     * @throws Exception
     */
    @Test
    public void testUpdateLastNameUnsuccessfulEmptyLastName() throws Exception {
        UserAccount patron = null;

        try {
            patron = userAccountRepository.findUserAccountByUserID(12345);
        } catch (IllegalArgumentException e) {
            fail();
        }

        try {
            service.changeLastName("", patron.getUserID());
        } catch (IllegalArgumentException e) {
            assertEquals("lastname cannot be empty!", e.getMessage());
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
     * This method tests teh unsuccessful change of the lastName due to null account
     * 
     * @author Gabrielle Halpin
     * @throws Exception
     */
    @Test
    public void testUpdateLastNameUnsuccessfulNullAccount() throws Exception {
        UserAccount patron = null;

        try {
            patron = userAccountRepository.findUserAccountByUserID(12345);
        } catch (IllegalArgumentException e) {
            fail();
        }

        try {
            service.changeLastName("BOB", 23563);
        } catch (IllegalArgumentException e) {
            assertEquals("The patron does not exist", e.getMessage());
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
     * 
     * @throws Exception
     */
    @Test
    public void testUpdateAddressSuccessful() throws Exception {
        UserAccount patron = null;

        try {
            patron = userAccountRepository.findUserAccountByUserID(12345);
        } catch (IllegalArgumentException e) {
            fail();
        }
        String address = "2 Avenue bobRoss";
        patron = service.changeAddress(address, patron.getUserID());
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
     * This methods tests the unsuccessful update of the user's address This test
     * will fail because the address is null
     * 
     * @author Gabrielle Halpin
     */
    @Test
    public void testUpdateAddresssUnsuccessfulNullAddress() throws Exception {
        UserAccount patron = null;

        try {
            patron = userAccountRepository.findUserAccountByUserID(12345);
        } catch (IllegalArgumentException e) {
            fail();
        }

        String newAddress = null;
        try {
            service.changeAddress(newAddress, patron.getUserID());
        } catch (IllegalArgumentException e) {
            assertEquals("Address cannot be empty!", e.getMessage());
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
     * This methods tests the unsuccessful update of the user's address This test
     * will fail because the account is null
     * 
     * @author Gabrielle Halpin
     */
    @Test
    public void testUpdateAddresssUnsuccessfulNullAccount() throws Exception {
        UserAccount patron = null;

        try {
            patron = userAccountRepository.findUserAccountByUserID(12345);
        } catch (IllegalArgumentException e) {
            fail();
        }

        String newAddress = "2 avenue what";
        try {
            service.changeAddress(newAddress, 15545);
        } catch (IllegalArgumentException e) {
            assertEquals("The patron does not exist", e.getMessage());
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
     * This methods tests the unsuccessful update of the user's address This test
     * will fail because the address is null
     * 
     * @author Gabrielle Halpin
     */
    @Test
    public void testUpdateAddresssUnsuccessfulSameAddress() throws Exception {
        UserAccount patron = null;

        try {
            patron = userAccountRepository.findUserAccountByUserID(12345);
        } catch (IllegalArgumentException e) {
            fail();
        }

        try {
            service.changeAddress(USER_ADDRESS, patron.getUserID());
        } catch (IllegalArgumentException e) {
            assertEquals("This is already your Address.", e.getMessage());
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
     * @author Gabrielle Halpin This tests the successful update of teh user's
     *         email.
     * @throws Exception
     */
    @Test
    public void testUpdateEmailSuccessful() throws Exception {
        UserAccount patron = null;

        try {
            patron = userAccountRepository.findUserAccountByUserID(12345);
        } catch (IllegalArgumentException e) {
            fail();
        }
        String email = "bob@email.com";
        patron = service.changeEmail(email, patron.getUserID());
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
     * @author Gabrielle Halpin This method test the unsuccessful update of the
     *         Email. This test will fail because this account is not an online
     *         account and does not have an email.
     * @throws Exception
     */
    @Test
    public void testUpdateEmailUnsuccessfulNotOnline() throws Exception {
        UserAccount patron = null;

        try {
            patron = userAccountRepository.findUserAccountByUserID(123456);
        } catch (IllegalArgumentException e) {
            fail();
        }

        String newEmail = "eamil@email.com";
        try {
            service.changeEmail(newEmail, patron.getUserID());
        } catch (IllegalArgumentException e) {
            assertEquals("The account must be an online account", e.getMessage());
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
     * @author Gabrielle Halpin This method test the unsuccessful update of the
     *         Email. This test will fail because this account is null
     * @throws Exception
     */
    @Test
    public void testUpdateEmailUnsuccessfulNullAccount() throws Exception {
        UserAccount patron = null;

        try {
            patron = userAccountRepository.findUserAccountByUserID(123456);
        } catch (IllegalArgumentException e) {
            fail();
        }

        String newEmail = "eamil@email.com";
        try {
            service.changeEmail(newEmail, 1663);
        } catch (IllegalArgumentException e) {
            assertEquals("The patron does not exist", e.getMessage());
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
     * @author Gabrielle Halpin This method test the unsuccessful update of the
     *         Email. This test will fail because this account is null
     * @throws Exception
     */
    @Test
    public void testUpdateEmailUnsuccessfulNullEmail() throws Exception {
        UserAccount patron = null;

        try {
            patron = userAccountRepository.findUserAccountByUserID(12345);
        } catch (IllegalArgumentException e) {
            fail();
        }

        try {
            service.changeEmail(null, patron.getUserID());
        } catch (IllegalArgumentException e) {
            assertEquals("Email cannot be empty!", e.getMessage());
        }

        assertEquals(patron.getFirstName(), USER_FIRST_NAME);
        assertEquals(patron.getLastName(), USER_LAST_NAME);
        assertEquals(patron.getOnlineAccount(), true);
        assertEquals(patron.getAddress(), USER_ADDRESS);
        assertEquals(patron.getPassword(), USER_PASSWORD);
        assertEquals(patron.getBalance(), USER_BALANCE);
        assertEquals(patron.getEmail(), USER_EMAIL);

        patronRepository.deleteAll();
        userAccountRepository.deleteAll();

    }

    /**
     * @author Gabrielle Halpin This method test the unsuccessful update of the
     *         Email. This test will fail because this account is null
     * @throws Exception
     */
    @Test
    public void testUpdateEmailUnsuccessfulSameEmail() throws Exception {
        UserAccount patron = null;

        try {
            patron = userAccountRepository.findUserAccountByUserID(12345);
        } catch (IllegalArgumentException e) {
            fail();
        }

        try {
            service.changeEmail(USER_EMAIL, patron.getUserID());
        } catch (IllegalArgumentException e) {
            assertEquals("This is already your Email.", e.getMessage());
        }

        assertEquals(patron.getFirstName(), USER_FIRST_NAME);
        assertEquals(patron.getLastName(), USER_LAST_NAME);
        assertEquals(patron.getOnlineAccount(), true);
        assertEquals(patron.getAddress(), USER_ADDRESS);
        assertEquals(patron.getPassword(), USER_PASSWORD);
        assertEquals(patron.getBalance(), USER_BALANCE);
        assertEquals(patron.getEmail(), USER_EMAIL);

        patronRepository.deleteAll();
        userAccountRepository.deleteAll();

    }

    /**
     * @author Gabrielle Halpin This test checks if all Users are successfully
     *         retrieved from the database
     * @throws Exception
     */
    @Test
    public void testGetAllUsers() throws Exception {

        List<UserAccount> accounts = null;
        try {
            accounts = service.getAllUsers();
        } catch (IllegalArgumentException e) {
            throw new Exception("could not retrieve users");
        }

        assertEquals(4, accounts.size());

        patronRepository.deleteAll();
        userAccountRepository.deleteAll();

    }

    /**
     * @author Gabrielle Halpin This test checks if the user with specific valid ID
     *         can be tretrieved from the database
     * @throws Exception
     */
    @Test
    public void testGetUserByID() throws Exception {
        assertEquals(USER_ID, service.getUserbyUserId(USER_ID).getUserID());
        patronRepository.deleteAll();
        userAccountRepository.deleteAll();

    }

    /**
     * @author Gabrielle Halpin This test checks if the user with specific invalid
     *         ID can be tretrieved from the database
     * @throws Exception
     */
    @Test
    public void testGetUserByIDInvalidID() throws Exception {
        try {
            service.getUserbyUserId(123);
        } catch (IllegalArgumentException e) {
            assertEquals(e.getMessage(), "This user does not exist.");
        }
        patronRepository.deleteAll();
        userAccountRepository.deleteAll();

    }
    
    /**
	 * Login UserAccount successfully	
	 * @author Zoya Malhi
     * @throws Exception 
	 */
	@Test
	public void testLoginUserAccountSuccessfully() throws Exception {
		assertEquals(4, service.getAllUsers().size());

		UserAccount user = null; 
		try {
			user = service.loginUserAccount(USER_ID, USER_PASSWORD);
		} catch (IllegalArgumentException e) {
			fail();
		}

		assertNotNull(user);			
		assertEquals(USER_ID, user.getUserID());
		assertEquals(USER_PASSWORD, user.getPassword());
		assertEquals(USER_FIRST_NAME, user.getFirstName());
	}

    
    
    
    
}