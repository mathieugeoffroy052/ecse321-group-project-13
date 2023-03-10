package ca.mcgill.ecse321.libraryservice.service;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.fail;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.lenient;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import ca.mcgill.ecse321.libraryservice.dao.*;
import ca.mcgill.ecse321.libraryservice.model.*;

@ExtendWith(MockitoExtension.class)
public class TestLibrarian {
    @Mock
    private UserAccountRepository userAccountDAO;
    @Mock
    private LibrarianRepository librarianDAO;
    @Mock
    private HeadLibrarianRepository headLibrarianDAO;

    @InjectMocks
    private LibraryServiceService service;
    private static final int LIBRARIAN_USER_ID = 12345;
    private static final String LIBRARIAN_FIRST_NAME = "Maya";
    private static final String LIBRARIAN_LAST_NAME = "Menia";
    private static final String LIBRARIAN_EMAIL = "mayamenia@email.com";
    private static final int LIBRARIAN_BALANCE = 0;
    private static final boolean LIBRARIAN_ONLINE_ACCOUNT = true;
    private static final String LIBRARIAN_ADDRESS = "7 villeneuve Street";
    private static final String LIBRARIAN_PASSWORD = "jado";
    private static final int LIBRARIAN_ID = 100;

    private static final int HEADLIBRARIAN_USER_ID = 777;
    private static final String HEADLIBRARIAN_FIRST_NAME = "Haytham";
    private static final String HEADLIBRARIAN_LAST_NAME = "Yallah";
    private static final String HEADLIBRARIAN_EMAIL = "yallahyallah@email.com";
    private static final int HEADLIBRARIAN_BALANCE = 0;
    private static final boolean HEADLIBRARIAN_ONLINE_ACCOUNT = true;
    private static final String HEADLIBRARIAN_ADDRESS = "7 jj street";
    private static final boolean HEADLIBRARIAN_VALIDATED_ACCOUNT = false;
    private static final String HEADLIBRARIAN_PASSWORD = "moha";
    private static final int HEADLIBRARIAN_ID = 798;

    // variables for tests so I don't have to redefine
    private static final String USER_FIRST_NAME = "hamid";
    private static final String USER_LAST_NAME = "Yallah";
    private static final String USER_EMAIL = "hamidi@email.com";
    private static final int USERBALANCE = 0;

    private static final boolean USER_ONLINE_ACCOUNT = true;
    private static final String USER_ADDRESS = "7 jj street";
    private static final String USER_PASSWORD = "mIMI";

    @BeforeEach
    public void setMockOutput() {
        lenient().when(librarianDAO.findLibrarianByUserID(anyInt())).thenAnswer((InvocationOnMock invocation) -> {
            if (invocation.getArgument(0).equals(LIBRARIAN_USER_ID)) {
                Librarian librarian = new Librarian(LIBRARIAN_FIRST_NAME, LIBRARIAN_LAST_NAME, LIBRARIAN_ONLINE_ACCOUNT,
                        LIBRARIAN_ADDRESS, LIBRARIAN_PASSWORD, LIBRARIAN_BALANCE, LIBRARIAN_EMAIL);
                return librarian;
            } else {
                return null;
            }
        });

        lenient().when(headLibrarianDAO.findHeadLibrarianByUserID(anyInt()))
                .thenAnswer((InvocationOnMock invocation) -> {
                    HeadLibrarian headLibrarian = new HeadLibrarian(HEADLIBRARIAN_FIRST_NAME, HEADLIBRARIAN_LAST_NAME,
                            HEADLIBRARIAN_ONLINE_ACCOUNT, HEADLIBRARIAN_ADDRESS, HEADLIBRARIAN_PASSWORD,
                            HEADLIBRARIAN_BALANCE, HEADLIBRARIAN_EMAIL);
                    headLibrarian.setUserID(HEADLIBRARIAN_USER_ID);
                    return headLibrarian;
                });
        lenient().when(userAccountDAO.findByFirstNameAndLastName(anyString(), anyString()))
                .thenAnswer((InvocationOnMock invocation) -> {
                    if (invocation.getArgument(0).equals(LIBRARIAN_FIRST_NAME)
                            && invocation.getArgument(1).equals(LIBRARIAN_LAST_NAME)) {
                        Librarian librarian = new Librarian(LIBRARIAN_FIRST_NAME, LIBRARIAN_LAST_NAME,
                                LIBRARIAN_ONLINE_ACCOUNT, LIBRARIAN_ADDRESS, LIBRARIAN_PASSWORD, LIBRARIAN_BALANCE, LIBRARIAN_EMAIL);
                        librarian.setUserID(LIBRARIAN_USER_ID);
                        return librarian;
                    } else {
                        return null;
                    }
                });
        Answer<?> returnParameterAsAnswer = (InvocationOnMock invocation) -> {
            return invocation.getArgument(0);
        };
        lenient().when(librarianDAO.save(any(Librarian.class))).thenAnswer(returnParameterAsAnswer);
    }

    /**
     * this method tests get librarian from a user ID found in the service class
     * this tests the case where we have an ID which is not associated with a
     * librarian
     * 
     * @author Eloyann Roy Javanbakht
     * @throws Exception
     */
    @Test
    public void testgetLibrarianFromNotAssociatedId() {
        int impossibleID = 999;
        String error = "";
        try {
            service.getLibrarianFromUserId(impossibleID);
        } catch (Exception e) {
            error = e.getMessage();
        }
        // if(librarian==null) error="This User ID does not correspond to a Librarian";
        assertEquals(error, "This User ID does not correspond to a Librarian");

        headLibrarianDAO.deleteAll();
        librarianDAO.deleteAll();
        userAccountDAO.deleteAll();

    }

    /**
     * this method tests get librarian from a user ID found in the service class
     * this tests case for a successful completion of the method
     * 
     * @author Eloyann Roy Javanbakht
     * @throws Exception
     */
    @Test
    public void testgetLibrarianWithGoodID() {
        Librarian librarian = null;
        try {
            librarian = service.getLibrarianFromUserId(LIBRARIAN_USER_ID);
        } catch (Exception e) {
            fail();
        }
        assertNotNull(librarian);
        assertEquals(librarian.getFirstName(), LIBRARIAN_FIRST_NAME);
        headLibrarianDAO.deleteAll();
        librarianDAO.deleteAll();
        userAccountDAO.deleteAll();
    }

    /**
     * this method tests get librarian from a user's fullName this tests case-> call
     * the method with a partially invalid input -> firstName=null expect -> error
     * 
     * @author Eloyann Roy Javanbakht
     * @throws Exception
     */
    @Test
    public void testGetLibrarianFromNullFirstName() {
        Librarian librarian = null;
        String error = "";
        try {
            librarian = service.getLibrarianFromFullName(error, USER_LAST_NAME);
        } catch (Exception e) {
            error = e.getMessage();
        }
        assertEquals(error, "First Name  cannot be empty!");
        assertNull(librarian);
        headLibrarianDAO.deleteAll();
        librarianDAO.deleteAll();
        userAccountDAO.deleteAll();
    }

    /**
     * this method tests get librarian from a user's fullName this tests case-> call
     * the method with a partially invalid input -> lastName=null expect -> error
     * 
     * @author Eloyann Roy Javanbakht
     * @throws Exception
     */
    @Test
    public void testGetLibrarianFromNullLastName() {
        Librarian librarian = null;
        String error = "";
        try {
            librarian = service.getLibrarianFromFullName(USER_FIRST_NAME, error);
        } catch (Exception e) {
            error = e.getMessage();
        }
        assertEquals(error, "Last Name  cannot be empty!");
        assertNull(librarian);
        headLibrarianDAO.deleteAll();
        librarianDAO.deleteAll();
        userAccountDAO.deleteAll();
    }

    /**
     * this method tests get librarian from a user's fullName this tests case->
     * method called with a valid input but name & last name but the names are not
     * associated with an instance of Librarian Class expect ->error
     * 
     * @author Eloyann Roy Javanbakht
     * @throws Exception
     */
    @Test
    public void testGetLibrarianFromFullNameNotAssociatedWithLibrarianAccount() {
        Librarian librarian = null;
        String error = "";
        try {
            librarian = service.getLibrarianFromFullName(USER_FIRST_NAME, USER_LAST_NAME);
            ;
        } catch (Exception e) {
            error = e.getMessage();
        }
        assertEquals(error, "the name privided does not correcpond to a librarian");
        assertNull(librarian);
        headLibrarianDAO.deleteAll();
        librarianDAO.deleteAll();
        userAccountDAO.deleteAll();
    }

    /**
     * this method tests get librarian from a user's fullName this tests case->
     * method called with a valid input but name & last name & names are associated
     * with a Librarian Account in the repo Expect -> a Librarian Account instance
     * 
     * @author Eloyann Roy Javanbakht
     * @throws Exception
     */
    @Test
    public void testGetLibrarianFromFullName() {
        Librarian librarian = null;
        try {
            librarian = service.getLibrarianFromFullName(LIBRARIAN_FIRST_NAME, LIBRARIAN_LAST_NAME);
        } catch (Exception e) {
            fail();
        }
        assertEquals(LIBRARIAN_USER_ID, librarian.getUserID());
        assertEquals(LIBRARIAN_FIRST_NAME, librarian.getFirstName());

        headLibrarianDAO.deleteAll();
        librarianDAO.deleteAll();
        userAccountDAO.deleteAll();
    }

    /**
     * this method tests deletes a Librarian method with the "ID of deleter" & "ID
     * of to be deleted" as input this tests case-> method called with a invalid "ID
     * of deleter" not corresponding to a headlibrarian Expect -> error
     * 
     * @author Eloyann Roy Javanbakht
     * @throws Exception
     */
    @Test
    public void testDeleteLibrarianWithoutBeingHeadLibrarian() {
        lenient().when(headLibrarianDAO.findAll()).thenAnswer((InvocationOnMock invocation) -> {
            List<HeadLibrarian> accounts = new ArrayList<HeadLibrarian>();
            HeadLibrarian headLibrarian = new HeadLibrarian(HEADLIBRARIAN_FIRST_NAME, HEADLIBRARIAN_LAST_NAME,
                    HEADLIBRARIAN_VALIDATED_ACCOUNT, HEADLIBRARIAN_ADDRESS, HEADLIBRARIAN_PASSWORD,
                    HEADLIBRARIAN_BALANCE, HEADLIBRARIAN_EMAIL);
            headLibrarian.setUserID(HEADLIBRARIAN_USER_ID);
            accounts.add(headLibrarian);
            return accounts;
        });

        Librarian librarian = null;
        String error = "";
        try {
            librarian = service.deleteLibrarian(LIBRARIAN_ID, LIBRARIAN_USER_ID);
        } catch (Exception e) {
            error = e.getMessage();
        }
        assertNull(librarian);
        assertEquals(error, "This User  does not the credentials to add a new librarian");
        headLibrarianDAO.deleteAll();
        librarianDAO.deleteAll();
        userAccountDAO.deleteAll();

    }

    /**
     * this method tests deletes a Librarian method with the "ID of deleter" & "ID
     * of to be deleted" as input this tests case-> method called with a invalid
     * input of "ID to be deleted" not being associated with a Librarian Account in
     * the repo Expect -> error
     * 
     * @author Eloyann Roy Javanbakht
     * @throws Exception
     */
    @Test
    public void testDeleteLibrarianDoesntExists() {
        lenient().when(headLibrarianDAO.findAll()).thenAnswer((InvocationOnMock invocation) -> {
            List<HeadLibrarian> accounts = new ArrayList<HeadLibrarian>();
            HeadLibrarian headLibrarian = new HeadLibrarian(HEADLIBRARIAN_FIRST_NAME, HEADLIBRARIAN_LAST_NAME,
                    HEADLIBRARIAN_VALIDATED_ACCOUNT, HEADLIBRARIAN_ADDRESS, HEADLIBRARIAN_PASSWORD,
                    HEADLIBRARIAN_BALANCE, HEADLIBRARIAN_EMAIL);
            headLibrarian.setUserID(HEADLIBRARIAN_USER_ID);
            accounts.add(headLibrarian);
            return accounts;
        });
        int impossibleID = 125;
        String error = "";
        try {
            service.deleteLibrarian(HEADLIBRARIAN_USER_ID, impossibleID);
        } catch (Exception e) {
            error = e.getMessage();
        }

        assertEquals(error, "This User ID does not correspond to a Librarian");
        headLibrarianDAO.deleteAll();
        librarianDAO.deleteAll();
        userAccountDAO.deleteAll();

    }

    /**
     * this method tests deletes a Librarian method with the "ID of deleter" & "ID
     * of to be deleted" as input this tests case-> method called with a valid input
     * associated with a head librarian account and a Librarian Account in the repo
     * Expect -> a Librarian Account instance of the deleted accound
     * 
     * @author Eloyann Roy Javanbakht
     * @throws Exception
     */
    @Test
    public void testDeleteLibrarian() {

        Librarian librarian = null;
        lenient().when(headLibrarianDAO.findAll()).thenAnswer((InvocationOnMock invocation) -> {
            List<HeadLibrarian> accounts = new ArrayList<HeadLibrarian>();
            HeadLibrarian headLibrarian = new HeadLibrarian(HEADLIBRARIAN_FIRST_NAME, HEADLIBRARIAN_LAST_NAME,
                    HEADLIBRARIAN_VALIDATED_ACCOUNT, HEADLIBRARIAN_ADDRESS, HEADLIBRARIAN_PASSWORD,
                    HEADLIBRARIAN_BALANCE, HEADLIBRARIAN_EMAIL);
            headLibrarian.setUserID(HEADLIBRARIAN_USER_ID);
            accounts.add(headLibrarian);
            return accounts;
        });
        String error = "";
        try {
            librarian = service.deleteLibrarian(HEADLIBRARIAN_USER_ID, LIBRARIAN_USER_ID);
        } catch (Exception e) {
            error = e.getMessage();
        }

        assertEquals(error, "");
        assertEquals(librarian.getPassword(), LIBRARIAN_PASSWORD);

        headLibrarianDAO.deleteAll();
        librarianDAO.deleteAll();
        userAccountDAO.deleteAll();
    }

    /**
     * this method tests create a Librarian method with the creater and Librarian
     * attributes this tests case-> method called with a invalid input -> first name
     * is null Expect -> error
     * 
     * @author Eloyann Roy Javanbakht
     * @throws Exception
     */
    @Test
    public void createLibrarianwithNullFirstName() {
        String error = "";
        Librarian librariantest = null;
        HeadLibrarian headLibrarian = headLibrarianDAO.findHeadLibrarianByUserID(HEADLIBRARIAN_ID);
        try {
            librariantest = service.createANewLibrarian(headLibrarian.getUserID(), error, USER_LAST_NAME,
                    USER_ONLINE_ACCOUNT, USER_ADDRESS, USER_PASSWORD, USERBALANCE, USER_EMAIL);
        } catch (Exception e) {
            error = e.getMessage();
        }

        assertNull(librariantest);
        assertEquals(error, "First Name  cannot be empty!");
        headLibrarianDAO.deleteAll();
        librarianDAO.deleteAll();
        userAccountDAO.deleteAll();
    }

    /**
     * this method tests create a Librarian method with the creater and Librarian
     * attributes this tests case-> method called with a invalid input -> last name
     * is null Expect -> error
     * 
     * @author Eloyann Roy Javanbakht
     * @throws Exception
     */
    @Test
    public void createLibrarianwithNullLastName() {
        String error = "";
        Librarian librariantest = null;
        HeadLibrarian headLibrarian = headLibrarianDAO.findHeadLibrarianByUserID(HEADLIBRARIAN_ID);
        try {
            librariantest = service.createANewLibrarian(headLibrarian.getUserID(), USER_FIRST_NAME, error,
                    USER_ONLINE_ACCOUNT, USER_ADDRESS, USER_PASSWORD, USERBALANCE, USER_EMAIL);
        } catch (Exception e) {
            error = e.getMessage();
        }

        assertNull(librariantest);
        assertEquals(error, "Last Name  cannot be empty!");
        headLibrarianDAO.deleteAll();
        librarianDAO.deleteAll();
        userAccountDAO.deleteAll();
    }

    /**
     * this method tests create a Librarian method with the creater and Librarian
     * attributes this tests case-> method called with a invalid input -> addresse
     * name is null Expect -> error
     * 
     * @author Eloyann Roy Javanbakht
     * @throws Exception
     */

    @Test
    public void createLibrarianwithNullAdress() {
        String error = "";
        Librarian librariantest = null;
        try {
            librariantest = service.createANewLibrarian(HEADLIBRARIAN_USER_ID, USER_FIRST_NAME, USER_LAST_NAME,
                    USER_ONLINE_ACCOUNT, error, USER_PASSWORD, USERBALANCE, USER_EMAIL);
        } catch (Exception e) {
            error = e.getMessage();
        }

        assertNull(librariantest);
        assertEquals(error, "Address cannot be empty!");
        headLibrarianDAO.deleteAll();
        librarianDAO.deleteAll();
        userAccountDAO.deleteAll();
    }

    /**
     * this method tests create a Librarian method with the creater and Librarian
     * attributes this tests case-> method called with a invalid input -> password
     * name is null Expect -> error
     * 
     * @author Eloyann Roy Javanbakht
     * @throws Exception
     */

    @Test
    public void createLibrarianwithNullPassword() {
        String error = "";
        Librarian librariantest = null;
        try {
            librariantest = service.createANewLibrarian(HEADLIBRARIAN_USER_ID, USER_FIRST_NAME, USER_LAST_NAME,
                    USER_ONLINE_ACCOUNT, USER_ADDRESS, error, USERBALANCE, USER_EMAIL);
        } catch (Exception e) {
            error = e.getMessage();
        }
        assertNull(librariantest);
        assertEquals(error, "Password cannot be empty!");
        headLibrarianDAO.deleteAll();
        librarianDAO.deleteAll();
        userAccountDAO.deleteAll();
    }

    /**
     * this method tests create a Librarian method with the creater and Librarian
     * attributes this tests case-> method called with a invalid input -> email name
     * is null Expect -> error
     * 
     * @author Eloyann Roy Javanbakht
     * @throws Exception
     */
    @Test
    public void createLibrarianwithNullEmail() {
        String error = "";
        Librarian librariantest = null;
        try {
            librariantest = service.createANewLibrarian(HEADLIBRARIAN_USER_ID, USER_FIRST_NAME, USER_LAST_NAME,
                    USER_ONLINE_ACCOUNT, USER_ADDRESS, USER_PASSWORD, USERBALANCE, error);
        } catch (Exception e) {
            error = e.getMessage();
        }

        assertNull(librariantest);
        assertEquals(error, "Email cannot be empty!");
        headLibrarianDAO.deleteAll();
        librarianDAO.deleteAll();
        userAccountDAO.deleteAll();
    }

    /**
     * this method tests create a Librarian method with the creater and Librarian
     * attributes this tests case-> method called with a invalid input -> creater'S
     * ACCOUNT is not associated with a headlibrarian's ID Expect -> error
     * 
     * @author Eloyann Roy Javanbakht
     * @throws Exception
     */

    @Test
    public void createLibrarianwithoutBeingHeadLibrarian() {
        String error = "";
        Librarian librariantest = null;
        lenient().when(headLibrarianDAO.findHeadLibrarianByUserID(anyInt()))
                .thenAnswer((InvocationOnMock invocation) -> {
                    HeadLibrarian headLibrarian = null;
                    return headLibrarian;
                });
        Librarian librarian = librarianDAO.findLibrarianByUserID(LIBRARIAN_USER_ID);
        try {
            librariantest = service.createANewLibrarian(librarian.getUserID(), USER_FIRST_NAME, USER_LAST_NAME,
                    USER_ONLINE_ACCOUNT, USER_ADDRESS, USER_PASSWORD, USERBALANCE, USER_EMAIL);
        } catch (Exception e) {
            error = e.getMessage();
        }

        assertNull(librariantest);
        assertEquals(error, "This User  does not the credentials to add a new librarian");
        headLibrarianDAO.deleteAll();
        librarianDAO.deleteAll();
        userAccountDAO.deleteAll();
    }

    @Test
    public void createLibrarianthatAlreadyexists() {
        lenient().when(userAccountDAO.findByFirstNameAndLastName(anyString(), anyString()))
                .thenAnswer((InvocationOnMock invocation) -> {
                    if (invocation.getArgument(0).equals(LIBRARIAN_FIRST_NAME)
                            && invocation.getArgument(1).equals(LIBRARIAN_LAST_NAME)) {
                        Librarian librarian = new Librarian(LIBRARIAN_FIRST_NAME, LIBRARIAN_LAST_NAME,
                                LIBRARIAN_ONLINE_ACCOUNT, LIBRARIAN_ADDRESS, LIBRARIAN_PASSWORD, LIBRARIAN_BALANCE,
                                LIBRARIAN_EMAIL);
                        librarian.setUserID(LIBRARIAN_USER_ID);
                        return librarian;
                    } else {
                        return null;
                    }
                });

        HeadLibrarian headLibrarian = headLibrarianDAO.findHeadLibrarianByUserID(HEADLIBRARIAN_ID);

        String error = "";
        try {
            service.createANewLibrarian(headLibrarian.getUserID(), LIBRARIAN_FIRST_NAME, LIBRARIAN_LAST_NAME,
                    LIBRARIAN_ONLINE_ACCOUNT, LIBRARIAN_ADDRESS, LIBRARIAN_PASSWORD, LIBRARIAN_BALANCE, LIBRARIAN_EMAIL);
        } catch (Exception e) {
            error = e.getMessage();
        }

        assertEquals(error, "This User already has a librarian account");
        headLibrarianDAO.deleteAll();
        librarianDAO.deleteAll();
        userAccountDAO.deleteAll();

    }

    /**
     * this method tests create a Librarian method with the creater and Librarian
     * attributes this tests case-> method called with a invalid input ->
     * creater=NULL Expect -> error
     * 
     * @author Eloyann Roy Javanbakht
     * @throws Exception
     */
    @Test
    public void createLibrarianwithNullCreater() {
        String error = "";
        Librarian librariantest = null;
        try {
            librariantest = service.createANewLibrarian(-1, USER_FIRST_NAME, USER_LAST_NAME, USER_ONLINE_ACCOUNT,
                    USER_ADDRESS, USER_PASSWORD, USERBALANCE, USER_EMAIL);
        } catch (Exception e) {
            error = e.getMessage();
        }

        assertNull(librariantest);
        assertEquals(error, "This User ID does not correspond to a Head Librarian");
        headLibrarianDAO.deleteAll();
        librarianDAO.deleteAll();
        userAccountDAO.deleteAll();
    }

    /**
     * this method tests create a Librarian method with the creater and Librarian
     * attributes this tests case-> method called with a valid input -> creater is
     * associated with a headlibrarian and valid values for Librarian attributes
     * Expect -> Librarian instance newly created
     * 
     * @author Eloyann Roy Javanbakht
     * @throws Exception
     */
    @Test
    public void createLibrarian() {
        Librarian librarian = null;
        HeadLibrarian headLibrarian = headLibrarianDAO.findHeadLibrarianByUserID(HEADLIBRARIAN_USER_ID);

        try {
            librarian = service.createANewLibrarian(headLibrarian.getUserID(), USER_FIRST_NAME, USER_LAST_NAME,
                    USER_ONLINE_ACCOUNT, USER_ADDRESS, USER_PASSWORD, USERBALANCE, USER_EMAIL);
        } catch (Exception e) {
            fail();
        }

        assertNotNull(userAccountDAO.findById(librarian.getUserID()));
        assertNotNull(librarian);

        assertEquals(USER_FIRST_NAME, librarian.getFirstName(), "librarian.firstName mismatch");
        assertEquals(USER_LAST_NAME, librarian.getLastName(), "librarian.lastName mismatch");
        assertEquals(USER_ONLINE_ACCOUNT, librarian.getOnlineAccount(), "librarian.onlineAccount mismatch");
        assertEquals(USER_PASSWORD, librarian.getPassword(), "librarian.password mismatch");
        assertEquals(USER_ADDRESS, librarian.getAddress(), "librarian.address mismatch");
        assertEquals(USER_EMAIL, librarian.getEmail(), "librarian.email mismatch");

        // assertNotEquals(librariantest.getlibrarianID(), librarian.getlibrarianID());

        headLibrarianDAO.deleteAll();
        librarianDAO.deleteAll();
        userAccountDAO.deleteAll();

    }

}
