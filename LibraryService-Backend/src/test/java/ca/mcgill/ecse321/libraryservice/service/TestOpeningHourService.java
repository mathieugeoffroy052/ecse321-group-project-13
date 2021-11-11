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
import ca.mcgill.ecse321.libraryservice.model.*;
import ca.mcgill.ecse321.libraryservice.model.OpeningHour.DayOfWeek;

@ExtendWith(MockitoExtension.class)
public class TestOpeningHourService {
    @Mock
    private OpeningHourRepository openingHourDao;
    @Mock
    private HeadLibrarianRepository headLibrarianDao;

    @InjectMocks
    private LibraryServiceService service;

    private static final int HEAD_LIBRARIAN_ID = 400;
    private static final String HEAD_LIBRARIAN_FIRSTNAME = "Jane";
    private static final String HEAD_LIBRARIAN_LASTNAME = "Doe";
    private static final String HEAD_LIBRARIAN_ADDRESS = "40 durocher";
    private static final String HEAD_LIBRARIAN_PASSWORD = "badpassword";
    private static final String HEAD_LIBRARIAN_EMAIL = "jane@mail.com";
    private static final boolean HEAD_LIBRARIAN_VALIDACC = true;
    private static final int HEAD_LIBRARIAN_BALANCE = 0;

    @BeforeEach
    public void setMockOutput() {
        lenient().when(headLibrarianDao.findAll()).thenAnswer( (InvocationOnMock invocation) -> {
                HeadLibrarian headLibrarian = new HeadLibrarian(HEAD_LIBRARIAN_FIRSTNAME, HEAD_LIBRARIAN_LASTNAME, HEAD_LIBRARIAN_VALIDACC, HEAD_LIBRARIAN_ADDRESS, HEAD_LIBRARIAN_PASSWORD, HEAD_LIBRARIAN_BALANCE, HEAD_LIBRARIAN_EMAIL);
                headLibrarian.setUserID(HEAD_LIBRARIAN_ID);
                
                List<HeadLibrarian> list = new ArrayList<HeadLibrarian>();
                list.add(headLibrarian);
                return list;
        });

        lenient().when(headLibrarianDao.findHeadLibrarianByUserID(anyInt())).thenAnswer( (InvocationOnMock invocation) -> {
            HeadLibrarian headLibrarian = new HeadLibrarian(HEAD_LIBRARIAN_FIRSTNAME, HEAD_LIBRARIAN_LASTNAME, HEAD_LIBRARIAN_VALIDACC, HEAD_LIBRARIAN_ADDRESS, HEAD_LIBRARIAN_PASSWORD, HEAD_LIBRARIAN_BALANCE, HEAD_LIBRARIAN_EMAIL);
            headLibrarian.setUserID(HEAD_LIBRARIAN_ID);
            return headLibrarian;
        });

        Answer<?> returnParameterAsAnswer = (InvocationOnMock invocation) -> {
			return invocation.getArgument(0);
		};
        lenient().when(openingHourDao.save(any(OpeningHour.class))).thenAnswer(returnParameterAsAnswer);
        lenient().when(headLibrarianDao.save(any(HeadLibrarian.class))).thenAnswer(returnParameterAsAnswer);

    }

    @AfterEach
    public void clearMockOutputs() {
        openingHourDao.deleteAll();
        headLibrarianDao.deleteAll();
    }

     /**
     * test create opening hour 
     * @author Amani Jammoul
     */
    @Test
    public void testCreateOpeningHour() {
        try {
            assertEquals(0, service.getAllOpeningHours().size());
        } catch (Exception e) {
            fail();
        }
        
        DayOfWeek dayOfWeek = DayOfWeek.Friday;
        Time startTime = Time.valueOf("08:00:00");
        Time endTime = Time.valueOf("20:00:00");

        OpeningHour openingHour = null;

        try {
            openingHour = service.createOpeningHour(dayOfWeek.toString(), startTime, endTime);
        } catch (Exception e) {
            fail();
        }

        assertOpeningHourAttributes(openingHour, dayOfWeek.toString(), startTime, endTime);
    }

    private void assertOpeningHourAttributes(OpeningHour openingHour, String dayOfWeek, Time startTime, Time endTime) {
        assertNotNull(openingHour);
        assertEquals(dayOfWeek.toString(), openingHour.getDayOfWeek().toString());
        assertEquals(startTime.toString(), openingHour.getStartTime().toString());
        assertEquals(endTime.toString(), openingHour.getEndTime().toString());
        HeadLibrarian headLibrarian = headLibrarianDao.findHeadLibrarianByUserID(HEAD_LIBRARIAN_ID);
        //assertEquals(headLibrarian, openingHour.getHeadLibrarian());
    }

}
