package ca.mcgill.ecse321.libraryservice.service;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.lenient;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import ca.mcgill.ecse321.libraryservice.dao.*;
import ca.mcgill.ecse321.libraryservice.dto.UserAccountDTO;
import ca.mcgill.ecse321.libraryservice.model.*;



@ExtendWith(MockitoExtension.class)
public class TestHeadLibrarian {
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
private static final UserAccount LIBRARIAN_CREATOR = new Librarian();
private static final boolean LIBRARIAN_ONLINE_ACCOUNT = true;
private static final String LIBRARIAN_ADDRESS = "7 villeneuve Street";
private static final boolean LIBRARIAN_VALIDATED_ACCOUNT = false;
private static final String LIBRARIAN_PASSWORD = "jado";
private static final int LIBRARIAN_ID = 100;

private static final int HEADLIBRARIAN_USER_ID = 777;
private static final String HEADLIBRARIAN_FIRST_NAME = "Haytham";
private static final String HEADLIBRARIAN_LAST_NAME = "Yallah";
private static final String HEADLIBRARIAN_EMAIL = "yallahyallah@email.com";
private static final int HEADLIBRARIAN_BALANCE = 0;
private static final UserAccount HEADLIBRARIAN_CREATOR = new HeadLibrarian();
private static final boolean HEADLIBRARIAN_ONLINE_ACCOUNT = true;
private static final String HEADLIBRARIAN_ADDRESS = "7 jj street";
private static final boolean HEADLIBRARIAN_VALIDATED_ACCOUNT = false;
private static final String HEADLIBRARIAN_PASSWORD = "moha";
private static final int HEADLIBRARIAN_ID = 798;



private static final int USER_ID = 444;
private static final String USER_FIRST_NAME = "hamid";
private static final String USER_LAST_NAME = "Yallah";
private static final String USER_EMAIL = "hamidi@email.com";
private static final int USERBALANCE = 0;

private static final boolean USER_ONLINE_ACCOUNT = true;
private static final String USER_ADDRESS = "7 jj street";
private static final boolean USER_VALIDATED_ACCOUNT = false;
private static final String USER_PASSWORD = "mIMI";


@BeforeEach
    public void setMockOutput() {
        lenient().when(headLibrarianDAO.findHeadLibrarianByUserID(anyInt())).thenAnswer( (InvocationOnMock invocation) -> {
            HeadLibrarian headLibrarian =
             new HeadLibrarian(HEADLIBRARIAN_FIRST_NAME, HEADLIBRARIAN_LAST_NAME, HEADLIBRARIAN_VALIDATED_ACCOUNT, HEADLIBRARIAN_ADDRESS, HEADLIBRARIAN_PASSWORD, HEADLIBRARIAN_BALANCE, HEADLIBRARIAN_EMAIL);
            headLibrarian.setUserID(HEADLIBRARIAN_USER_ID);
            return headLibrarian;
    });
    lenient().when(librarianDAO.findLibrarianByUserID(anyInt())).thenAnswer( (InvocationOnMock invocation) -> {
        if(invocation.getArgument(0).equals(LIBRARIAN_USER_ID)) {
            Librarian librarian = 
            new Librarian(LIBRARIAN_FIRST_NAME , LIBRARIAN_LAST_NAME, LIBRARIAN_ONLINE_ACCOUNT, LIBRARIAN_ADDRESS, LIBRARIAN_PASSWORD, LIBRARIAN_BALANCE, LIBRARIAN_EMAIL);
            return librarian ;
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
 * tests with a faulty userID 
 * @author Eloyann Roy-Javanbakht
 * @throws Exception
 */

    @Test 
    public void testgetLibrarianFromNotAssociatedId () throws Exception {
    int impossibleID=-5;
    HeadLibrarian headlibrarian=null;
    String error="";
    try {
        headlibrarian=service.getHeadLibrarianFromUserId(impossibleID);
    } catch (Exception e) {
        error=e.getMessage();
    }
      assertEquals(error, "This User ID does not correspond to a Head Librarian");
      assertNull(headlibrarian);
     headLibrarianDAO.deleteAll();
     librarianDAO.deleteAll();
     userAccountDAO.deleteAll();
    
    }


/**
 * Tests getting headLibrarian with rightID
 * @author Eloyann Roy-JAVANBAKHT
 */
@Test
public void testgetHeadLibrarianWithGoodID () {
    
    HeadLibrarian headlibrariantest=null;
    String error="";
    try {
        headlibrariantest=service.getHeadLibrarianFromUserId(HEADLIBRARIAN_ID);
    } catch (Exception e) {
        error=e.getMessage();
    }
      assertNotNull(headlibrariantest);
      assertEquals(headlibrariantest.getFirstName(),HEADLIBRARIAN_FIRST_NAME);
      headLibrarianDAO.deleteAll();
      librarianDAO.deleteAll();
      userAccountDAO.deleteAll();
}

/**
 * tests DeleteHeadLibrarian Sucessful Case
 * @author Eloyann Roy-Javanbakht
 * @throws Exception
 */
@Test
public void testDeleteHeadLibrarian() {
    HeadLibrarian headlibrariantest=null;
    String error="";
    try {
        headlibrariantest=service.deleteHeadLibrarian(HEADLIBRARIAN_ID);
    } catch (Exception e) {
        error=e.getMessage();
    }

      assertNotNull(headlibrariantest);
      assertEquals(headlibrariantest.getFirstName(),HEADLIBRARIAN_FIRST_NAME);
      try {
        //headlibrariantest=service.DeleteHeadLibrarian(HEADLIBRARIAN_ID);
    } catch (Exception e) {
        error="yes";
    }
     
     
     assertEquals(error, "yes");
      headLibrarianDAO.deleteAll();
      librarianDAO.deleteAll();
      userAccountDAO.deleteAll();
}
/**
 * tests DeleteHeadLibrarian Unsucessful Case with an inexistance userID
 * @author Eloyann Roy-Javanbakht
 * @throws Exception
 */
@Test
public void testDeleteHeadLibrarianDoesntCorrespond () {
    HeadLibrarian headlibrariantest;
    String error="";
    try {
        headlibrariantest=service.deleteHeadLibrarian(-5);
    } catch (Exception e) {
        error=e.getMessage();
    }

     
      assertNotEquals(LIBRARIAN_ID,HEADLIBRARIAN_ID);

     
     
     assertEquals(error, "The UserID provided does not correspond to a  Head Librarian Account");
      headLibrarianDAO.deleteAll();
      librarianDAO.deleteAll();
      userAccountDAO.deleteAll();
    
}

/**
 *  tests CREATE headLibrarian with bad input
 * @author Eloyann Roy-Javanbakht
 * @throws Exception
 */
@Test
public void createHeadLibrarianwithNullFieldFirstName() {
    String error="";
    try {
        HeadLibrarian headlibrariantest=service.createNewHeadLibrarian(error, USER_LAST_NAME, USER_VALIDATED_ACCOUNT, USER_ADDRESS, USER_PASSWORD, USERBALANCE, USER_EMAIL );
    } catch (Exception e) {
        error=e.getMessage();
    }


     
     assertEquals(error, "First Name  cannot be empty! ");
      headLibrarianDAO.deleteAll();
      librarianDAO.deleteAll();
      userAccountDAO.deleteAll(); 
}
/**
 * tests CREATE headLibrarian with bad input
 * @author Eloyann Roy-Javanbakht
 * @throws Exception
 */
@Test
public void createHeadLibrarianwithNullFieldlasttName() {
    String error="";
    try {
        HeadLibrarian headlibrariantest=service.createNewHeadLibrarian(USER_FIRST_NAME, error, USER_VALIDATED_ACCOUNT, USER_ADDRESS, USER_PASSWORD, USERBALANCE, USER_EMAIL );
    } catch (Exception e) {
        error=e.getMessage();
    }


     
     assertEquals(error, "Last Name  cannot be empty! ");
      headLibrarianDAO.deleteAll();
      librarianDAO.deleteAll();
      userAccountDAO.deleteAll(); 
}
/**
 * tests CREATE headLibrarian with bad input
 * @author Eloyann Roy-Javanbakht
 * @throws Exception
 */
@Test
public void createHeadLibrarianwithNullAdress() {
    String error="";
    try {
        HeadLibrarian headlibrariantest=service.createNewHeadLibrarian(USER_FIRST_NAME, USER_LAST_NAME, USER_VALIDATED_ACCOUNT, error, USER_PASSWORD, USERBALANCE, USER_EMAIL );
    } catch (Exception e) {
        error=e.getMessage();
    }


     
     assertEquals(error, "Address cannot be empty! ");
      headLibrarianDAO.deleteAll();
      librarianDAO.deleteAll();
      userAccountDAO.deleteAll(); 
}
/**
 * tests CREATE headLibrarian with bad input
 * @author Eloyann Roy-Javanbakht
 * @throws Exception
 */
@Test
public void createHeadLibrarianwithNullPassword() {
    String error="";
    try {
        HeadLibrarian headlibrariantest=service.createNewHeadLibrarian(USER_FIRST_NAME, USER_LAST_NAME, USER_VALIDATED_ACCOUNT, USER_ADDRESS, error, USERBALANCE, USER_EMAIL );
    } catch (Exception e) {
        error=e.getMessage();
    }


     
     assertEquals(error, "Password cannot be empty! ");
      headLibrarianDAO.deleteAll();
      librarianDAO.deleteAll();
      userAccountDAO.deleteAll(); 
}
/**
 * tests CREATE headLibrarian with bad input
 * @author Eloyann Roy-Javanbakht
 * @throws Exception
 */
@Test
public void createHeadLibrarianwithNullEmail() {
    String error="";
    try {
        HeadLibrarian headlibrariantest=service.createNewHeadLibrarian(USER_FIRST_NAME, USER_LAST_NAME, USER_VALIDATED_ACCOUNT, USER_ADDRESS, USER_PASSWORD, USERBALANCE, error );
    } catch (Exception e) {
        error=e.getMessage();
    }


     
     assertEquals(error, "Email cannot be empty!");
      headLibrarianDAO.deleteAll();
      librarianDAO.deleteAll();
      userAccountDAO.deleteAll(); 
}


/**
 * tests CREATE headLibrarian with bad input
 * @author Eloyann Roy-Javanbakht
 * @throws Exception
 */
@Test
public void createHeadLibrarianwithoutBeingHeadLibrarian() {
    String error="";
    try {
        HeadLibrarian headlibrariantest=service.createNewHeadLibrarian(USER_FIRST_NAME, USER_LAST_NAME, USER_VALIDATED_ACCOUNT, USER_ADDRESS, USER_PASSWORD, USERBALANCE, USER_EMAIL );
    } catch (Exception e) {
        error=e.getMessage();
    }


     
     assertEquals(error, "There is already a HeadLibrarian AccountExisting");
      headLibrarianDAO.deleteAll();
      librarianDAO.deleteAll();
      userAccountDAO.deleteAll(); 
}

@Test
public void createHeadLibrarian() {
    String error="";
    HeadLibrarian created=null;
    HeadLibrarian headlibrariantest=null;
    headLibrarianDAO.deleteAll();
    librarianDAO.deleteAll();
    userAccountDAO.deleteAll(); 
    try {
        headlibrariantest=service.createNewHeadLibrarian(HEADLIBRARIAN_FIRST_NAME, HEADLIBRARIAN_LAST_NAME, HEADLIBRARIAN_ONLINE_ACCOUNT, HEADLIBRARIAN_ADDRESS, HEADLIBRARIAN_PASSWORD, HEADLIBRARIAN_BALANCE, HEADLIBRARIAN_EMAIL);
    } catch (Exception e) {
        error=e.getMessage();
    }
     
    assertNotNull(headlibrariantest);
     
    assertEquals(headlibrariantest.getFirstName(), headlibrariantest.getFirstName());
    assertEquals(headlibrariantest.getUserID(), headlibrariantest.getUserID());

    assertEquals(headlibrariantest.getLastName(), headlibrariantest.getLastName());
    assertEquals(headlibrariantest.getAddress(), headlibrariantest.getAddress());
    assertEquals(headlibrariantest.getOnlineAccount(), headlibrariantest.getOnlineAccount());
    
     
      
      headLibrarianDAO.deleteAll();
      librarianDAO.deleteAll();
      userAccountDAO.deleteAll();    
}
}