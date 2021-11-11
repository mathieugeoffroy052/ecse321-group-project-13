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
public class TestHolidayService { 
    @Mock
    private HolidayRepository holidayDao;
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

    private static final int HOLIDAY_ID = 5;
    private static final int HOLIDAY_INVALID_ID = -5;
    private static final Date HOLIDAY_DATE = Date.valueOf("2021-12-25");
    private static final Time OPENING_HOUR_START_TIME = Time.valueOf("08:00:00");
    private static final Time OPENING_HOUR_END_TIME = Time.valueOf("20:00:00");
    

    @BeforeEach
    public void setMockOutput() {
        lenient().when(holidayDao.findHolidayByHolidayID(anyInt())).thenAnswer( (InvocationOnMock invocation) -> {
            if(invocation.getArgument(0).equals(HOLIDAY_ID)) {
                Holiday holiday = new Holiday(HOLIDAY_DATE, OPENING_HOUR_START_TIME, OPENING_HOUR_END_TIME, this.headLibrarian);
                this.headLibrarian.setLibrarianID(HEAD_LIBRARIAN_ID);
                holiday.setHolidayID(HOLIDAY_ID);
                return holiday;
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
        lenient().when(holidayDao.save(any(Holiday.class))).thenAnswer(returnParameterAsAnswer);
        lenient().when(headLibrarianDao.save(any(HeadLibrarian.class))).thenAnswer(returnParameterAsAnswer);

    }

    @AfterEach
    public void clearMockOutputs() {
        holidayDao.deleteAll();
        headLibrarianDao.deleteAll();
    }

     /**
     * Test create holiday
     * Success case
     * @author Amani Jammoul
     */
    @Test
    public void testCreateHolidaySuccess() {
        try {
            assertEquals(0, service.getAllHolidays().size());
        } catch (Exception e) {
            fail();
        }
        
        Date date = Date.valueOf("2021-12-25");
        Time startTime = Time.valueOf("08:00:00");
        Time endTime = Time.valueOf("20:00:00");

        Holiday holiday = null;

        try {
            holiday = service.createHoliday(date, startTime, endTime);
        } catch (Exception e) {
            fail();
        }

        assertNotNull(holiday);
        assertHolidayAttributes(holiday, date, startTime, endTime);
    }

    /**
     * Test get opening hour from ID
     * Success case
     * @author Amani Jammoul
     */
    @Test
    public void testGetOpeningHourFromIDSuccess() {
        Date date = Date.valueOf("2021-12-25");
        Time startTime = Time.valueOf("08:00:00");
        Time endTime = Time.valueOf("20:00:00");

        Holiday holiday = new Holiday(date, startTime, endTime, this.headLibrarian);
        holiday.setHolidayID(HOLIDAY_ID);
        holidayDao.save(holiday);

        holiday = null;

        try {
            holiday = service.getHolidayFromId(HOLIDAY_ID);
        } catch (Exception e) {
            fail();
        }

        assertNotNull(holiday);
        assertHolidayAttributes(holiday, date, startTime, endTime);
    }

    /**
     * Test get opening hour from ID
     * Failure case : get opening hour from invalid ID
     * @author Amani Jammoul
     */
    @Test
    public void testGetOpeningHourFromInvalidID() {
        Date date = Date.valueOf("2021-12-25");
        Time startTime = Time.valueOf("08:00:00");
        Time endTime = Time.valueOf("20:00:00");

        Holiday holiday = new Holiday(date, startTime, endTime, this.headLibrarian);
        holiday.setHolidayID(HOLIDAY_ID);
        holidayDao.save(holiday);

        holiday = null;

        String error = "";

        try {
            holiday = service.getHolidayFromId(HOLIDAY_INVALID_ID);
        } catch (Exception e) {
            error = e.getMessage();
        }

        assertNull(holiday);
        assertEquals("Invalid id", error);
    }


    /**
     * Verifies all Holiday params are equivalent to those for the object given
     * @param openingHour
     * @param dayOfWeek
     * @param startTime
     * @param endTime
     * @author Amani Jammoul
     */
    private void assertHolidayAttributes(Holiday holiday, Date date, Time startTime, Time endTime) {
        assertNotNull(holiday);
        assertEquals(date, holiday.getDate());
        assertEquals(startTime.toString(), holiday.getStartTime().toString());
        assertEquals(endTime.toString(), holiday.getEndtime().toString());
        assertEquals(this.headLibrarian, holiday.getHeadLibrarian());
    }
    
}
