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

import ca.mcgill.ecse321.libraryservice.dao.OpeningHourRepository;
import ca.mcgill.ecse321.libraryservice.dao.HeadLibrarianRepository;
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
    private static final int HEAD_LIBRARIAN_BALANCE = 0;
    private static final boolean ONLINE = true;

    // Single headLibrarian
    private HeadLibrarian headLibrarian = new HeadLibrarian(HEAD_LIBRARIAN_FIRSTNAME, HEAD_LIBRARIAN_LASTNAME, ONLINE, HEAD_LIBRARIAN_ADDRESS, HEAD_LIBRARIAN_PASSWORD, HEAD_LIBRARIAN_BALANCE, HEAD_LIBRARIAN_EMAIL);

    private static final int OPENING_HOUR_ID = 100;
    private static final int OPENING_HOUR_INVALID_ID = -100;
    private static final DayOfWeek OPENING_HOUR_DAYOFWEEK = DayOfWeek.Friday;
    private static final Time OPENING_HOUR_START_TIME = Time.valueOf("08:00:00");
    private static final Time OPENING_HOUR_END_TIME = Time.valueOf("20:00:00");    

    @BeforeEach
    public void setMockOutput() {
        lenient().when(openingHourDao.findOpeningHourByHourID(anyInt())).thenAnswer( (InvocationOnMock invocation) -> {
            if(invocation.getArgument(0).equals(OPENING_HOUR_ID)) {
                OpeningHour openingHour = new OpeningHour(OPENING_HOUR_DAYOFWEEK, OPENING_HOUR_START_TIME, OPENING_HOUR_END_TIME, this.headLibrarian);
                this.headLibrarian.setLibrarianID(HEAD_LIBRARIAN_ID);
                openingHour.setHourID(OPENING_HOUR_ID);
                return openingHour;
            } else {
                return null;
            }
        });

        lenient().when(openingHourDao.findByDayOfWeek(any(DayOfWeek.class))).thenAnswer( (InvocationOnMock invocation) -> {
            if(invocation.getArgument(0).equals(DayOfWeek.Friday)) {
                OpeningHour openingHour = new OpeningHour(OPENING_HOUR_DAYOFWEEK, OPENING_HOUR_START_TIME, OPENING_HOUR_END_TIME, this.headLibrarian);
                this.headLibrarian.setLibrarianID(HEAD_LIBRARIAN_ID);
                openingHour.setHourID(OPENING_HOUR_ID);

                List<OpeningHour> openingHours = new ArrayList<OpeningHour>();
                openingHours.add(openingHour);
                return openingHours;
            } else {
                return null;
            }
        });

        lenient().when(headLibrarianDao.findAll()).thenAnswer( (InvocationOnMock invocation) -> {
                this.headLibrarian.setLibrarianID(HEAD_LIBRARIAN_ID);
                List<HeadLibrarian> list = new ArrayList<HeadLibrarian>();
                list.add(this.headLibrarian);
                return list;
        });

        lenient().when(headLibrarianDao.findHeadLibrarianByUserID(anyInt())).thenAnswer( (InvocationOnMock invocation) -> {
            this.headLibrarian.setLibrarianID(HEAD_LIBRARIAN_ID);
            return this.headLibrarian;
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
     * Test create opening hour 
     * Success case
     * @author Amani Jammoul
     */
    @Test
    public void testCreateOpeningHourSuccess() {
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

        assertNotNull(openingHour);
        assertOpeningHourAttributes(openingHour, dayOfWeek.toString(), startTime, endTime);
    }

     /**
     * Test create opening hour 
     * Failure case : try to create opening hour with invalid Day Of Week
     * @author Amani Jammoul
     */
    @Test
    public void testCreateOpeningHourInvalidDOW() {
        try {
            assertEquals(0, service.getAllOpeningHours().size());
        } catch (Exception e) {
            fail();
        }
        
        String dayOfWeek = "January";
        Time startTime = Time.valueOf("08:00:00");
        Time endTime = Time.valueOf("20:00:00");

        OpeningHour openingHour = null;

        String error = "";

        try {
            openingHour = service.createOpeningHour(dayOfWeek, startTime, endTime);
        } catch (Exception e) {
            error = e.getMessage();
        }

        assertNull(openingHour);
        assertEquals("Invalid day", error);
    }

    /**
     * Test create opening hour 
     * Failure case : try to create opening hour with invalid startTime
     * @author Mathieu Geoffroy
     */
    @Test
    public void testCreateOpeningHourInvalidStartTime() {
        try {
            assertEquals(0, service.getAllOpeningHours().size());
        } catch (Exception e) {
            fail();
        }
        
        String dayOfWeek = "Monday";
        Time startTime = null;
        Time endTime = Time.valueOf("20:00:00");

        OpeningHour openingHour = null;

        String error = "";

        try {
            openingHour = service.createOpeningHour(dayOfWeek, startTime, endTime);
        } catch (Exception e) {
            error = e.getMessage();
        }

        assertNull(openingHour);
        assertEquals("Invalid StartTime", error);
    }


    /**
     * Test create opening hour 
     * Failure case : try to create opening hour with invalid endTime
     * @author Mathieu Geoffroy
     */
    @Test
    public void testCreateOpeningHourInvalidEndTime() {
        try {
            assertEquals(0, service.getAllOpeningHours().size());
        } catch (Exception e) {
            fail();
        }
        
        String dayOfWeek = "Monday";
        Time startTime = Time.valueOf("20:00:00");
        Time endTime = null;

        OpeningHour openingHour = null;

        String error = "";

        try {
            openingHour = service.createOpeningHour(dayOfWeek, startTime, endTime);
        } catch (Exception e) {
            error = e.getMessage();
        }

        assertNull(openingHour);
        assertEquals("Invalid EndTime", error);
    }

    /**
     * Test get opening hour from ID
     * Success case
     * @author Amani Jammoul
     */
    @Test
    public void testGetOpeningHourFromIDSuccess() {
        DayOfWeek dayOfWeek = DayOfWeek.Friday;
        Time startTime = Time.valueOf("08:00:00");
        Time endTime = Time.valueOf("20:00:00");

        OpeningHour openingHour = new OpeningHour(dayOfWeek, startTime, endTime, this.headLibrarian);
        openingHour.setHourID(OPENING_HOUR_ID);
        openingHourDao.save(openingHour);

        openingHour = null;

        try {
            openingHour = service.getOpeningHourFromID(OPENING_HOUR_ID);
        } catch (Exception e) {
            fail();
        }

        assertNotNull(openingHour);
        assertOpeningHourAttributes(openingHour, dayOfWeek.toString(), startTime, endTime);
    }

    /**
     * Test get opening hour from ID
     * Failure case : get opening hour from invalid ID
     * @author Amani Jammoul
     */
    @Test
    public void testGetOpeningHourFromInvalidID() {
        DayOfWeek dayOfWeek = DayOfWeek.Friday;
        Time startTime = Time.valueOf("08:00:00");
        Time endTime = Time.valueOf("20:00:00");

        OpeningHour openingHour = new OpeningHour(dayOfWeek, startTime, endTime, this.headLibrarian);
        openingHour.setHourID(OPENING_HOUR_ID);
        openingHourDao.save(openingHour);

        openingHour = null;

        String error = "";

        try {
            openingHour = service.getOpeningHourFromID(OPENING_HOUR_INVALID_ID);
        } catch (Exception e) {
            error = e.getMessage();
        }

        assertNull(openingHour);
        assertEquals("Invalid id", error);
    }

    /**
     * Test delete opening hour 
     * Success case 
     * @author Mathieu Geoffroy
     */
    @Test
    public void testDeleteOpeningHourSuccess() {
        boolean test = false;
        try {
            assertEquals(0, service.getAllOpeningHours().size());
        } catch (Exception e) {
            fail();
        }
        
        OpeningHour openingHour = openingHourDao.findOpeningHourByHourID(OPENING_HOUR_ID);
        lenient().when(openingHourDao.existsById(anyInt())).thenReturn(true);

        try {
            test = service.deleteOpeningHour(headLibrarian, openingHour.getHourID());
            if (openingHourDao.findAll().iterator().hasNext()) {  //gets timeslot if there, othewise, set to null
                openingHour = openingHourDao.findAll().iterator().next();
            } else {
                openingHour = null;
            }
        } catch (Exception e) {
            fail();
        }

        assertNull(openingHour);
        assertTrue(test);
    }

    /**
     * Test get opening hour from day of week
     * Success case
     * @author Mathieu
     */
    @Test
    public void testGetOpeningHourFromDayOfWeekSuccess() {
        DayOfWeek dayOfWeek = DayOfWeek.Friday;
        Time startTime = Time.valueOf("08:00:00");
        Time endTime = Time.valueOf("20:00:00");

        OpeningHour openingHour = new OpeningHour(dayOfWeek, startTime, endTime, this.headLibrarian);
        openingHour.setHourID(OPENING_HOUR_ID);
        openingHourDao.save(openingHour);

        openingHour = null;

        try {
            openingHour = service.getOpeningHoursByDayOfWeek(dayOfWeek.toString()).get(0);
        } catch (Exception e) {
            fail();
        }

        assertNotNull(openingHour);
        assertOpeningHourAttributes(openingHour, dayOfWeek.toString(), startTime, endTime);
    }

    /**
     * Test get opening hour from day of week
     * Failure case: invalid day of week
     * @author Mathieu
     */
    @Test
    public void testGetOpeningHourFromDayOfWeekInvalidDay() {
        String error = null;
        DayOfWeek dayOfWeek = DayOfWeek.Friday;
        Time startTime = Time.valueOf("08:00:00");
        Time endTime = Time.valueOf("20:00:00");

        OpeningHour openingHour = new OpeningHour(dayOfWeek, startTime, endTime, this.headLibrarian);
        openingHour.setHourID(OPENING_HOUR_ID);
        openingHourDao.save(openingHour);

        openingHour = null;

        try {
            openingHour = service.getOpeningHoursByDayOfWeek(dayOfWeek.toString() + "extra junk").get(0);
        } catch (Exception e) {
            error = e.getMessage();
        }

        assertNull(openingHour);
        assertEquals("Invalid day", error);
    }

    /**
     * Verifies all OpeningHour params are equivalent to those for the object given
     * @param openingHour
     * @param dayOfWeek
     * @param startTime
     * @param endTime
     * @author Amani Jammoul
     */
    private void assertOpeningHourAttributes(OpeningHour openingHour, String dayOfWeek, Time startTime, Time endTime) {
        assertNotNull(openingHour);
        assertEquals(dayOfWeek.toString(), openingHour.getDayOfWeek().toString());
        assertEquals(startTime.toString(), openingHour.getStartTime().toString());
        assertEquals(endTime.toString(), openingHour.getEndTime().toString());
        assertEquals(this.headLibrarian, openingHour.getHeadLibrarian());
    }

}
