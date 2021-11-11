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


/** 
private static final int USER_ID = 444;
private static final String USER_FIRST_NAME = "hamid";
private static final String USER_LAST_NAME = "Yallah";
private static final String USER_EMAIL = "hamidi@email.com";
private static final int USERBALANCE = 0;
private static final UserAccount USER_CREATOR = new UserAccount(); 
private static final boolean USER_ONLINE_ACCOUNT = true;
private static final String USER_ADDRESS = "7 jj street";
private static final boolean USER_VALIDATED_ACCOUNT = false;
private static final String USER_PASSWORD = "mIMI";
*/

@BeforeEach
    public void setMockOutput() {
        lenient().when(librarianDAO.findLibrarianByUserID(anyInt())).thenAnswer( (InvocationOnMock invocation) -> {
            if(invocation.getArgument(0).equals(LIBRARIAN_USER_ID)) {
                Librarian librarian = 
                new Librarian(LIBRARIAN_FIRST_NAME , LIBRARIAN_LAST_NAME, LIBRARIAN_ONLINE_ACCOUNT, LIBRARIAN_ADDRESS, LIBRARIAN_PASSWORD, LIBRARIAN_BALANCE, LIBRARIAN_EMAIL);
                return librarian ;
            } else {
                return null;
            }
        });

        lenient().when(headLibrarianDAO.findHeadLibrarianByUserID(anyInt())).thenAnswer( (InvocationOnMock invocation) -> {
            HeadLibrarian headLibrarian =
             new HeadLibrarian(HEADLIBRARIAN_FIRST_NAME, HEADLIBRARIAN_LAST_NAME, HEADLIBRARIAN_VALIDATED_ACCOUNT, HEADLIBRARIAN_ADDRESS, HEADLIBRARIAN_PASSWORD, HEADLIBRARIAN_BALANCE, HEADLIBRARIAN_EMAIL);
            headLibrarian.setUserID(HEADLIBRARIAN_USER_ID);
            return headLibrarian;
    });

        Answer<?> returnParameterAsAnswer = (InvocationOnMock invocation) -> {
			return invocation.getArgument(0);
		};
        lenient().when(librarianDAO.save(any(Librarian.class))).thenAnswer(returnParameterAsAnswer);
    }

@Test 
public void testgetLibrarianFromNotAssociatedId () throws Exception {
    headLibrarianDAO.deleteAll();
    librarianDAO.deleteAll();
    userAccountDAO.deleteAll();

}

@Test
public void testgetLibrarianWithGoodID () {
    headLibrarianDAO.deleteAll();
    librarianDAO.deleteAll();
    userAccountDAO.deleteAll();
}


@Test
public void testGetLibrarianFromNullFirstName () {
    headLibrarianDAO.deleteAll();
    librarianDAO.deleteAll();
    userAccountDAO.deleteAll();
}

@Test
public void testGetLibrarianFromNullLastName () {
    headLibrarianDAO.deleteAll();
    librarianDAO.deleteAll();
    userAccountDAO.deleteAll();
}


@Test
public void testGetLibrarianFromFullNameNotAssociatedWithLibrarianAccount () {
    headLibrarianDAO.deleteAll();
    librarianDAO.deleteAll();
    userAccountDAO.deleteAll(); 
}

@Test
public void testGetLibrarianFromFullName () {
    headLibrarianDAO.deleteAll();
    librarianDAO.deleteAll();
    userAccountDAO.deleteAll();  
}

@Test
public void testDeleteLibrarianWithoutBeingHeadLibrarian () {
    headLibrarianDAO.deleteAll();
    librarianDAO.deleteAll();
    userAccountDAO.deleteAll(); 
}

@Test
public void testDeleteLibrarianDoesntExists () {
    headLibrarianDAO.deleteAll();
    librarianDAO.deleteAll();
    userAccountDAO.deleteAll();  
}

@Test
public void testDeleteLibrarian() {
    headLibrarianDAO.deleteAll();
    librarianDAO.deleteAll();
    userAccountDAO.deleteAll();   
}

@Test
public void createLibrarianwithNullFields() {
    headLibrarianDAO.deleteAll();
    librarianDAO.deleteAll();
    userAccountDAO.deleteAll();  
}

@Test
public void createLibrarianwithoutBeingHeadLibrarian() {
    headLibrarianDAO.deleteAll();
    librarianDAO.deleteAll();
    userAccountDAO.deleteAll();  
}

@Test
public void createLibrarian() {
    headLibrarianDAO.deleteAll();
    librarianDAO.deleteAll();
    userAccountDAO.deleteAll();   
}
    
}
