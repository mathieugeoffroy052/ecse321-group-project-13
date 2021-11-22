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

import ca.mcgill.ecse321.libraryservice.dao.HolidayRepository;
import ca.mcgill.ecse321.libraryservice.dao.UserAccountRepository;
import ca.mcgill.ecse321.libraryservice.dto.HolidayDTO;
import ca.mcgill.ecse321.libraryservice.dto.LibrarianDTO;
import ca.mcgill.ecse321.libraryservice.dto.TimeslotDTO;
import ca.mcgill.ecse321.libraryservice.dao.HeadLibrarianRepository;
import ca.mcgill.ecse321.libraryservice.model.*;


@ExtendWith(MockitoExtension.class)
public class TestHolidayService { 
    @Mock
    private HolidayRepository holidayDao;
    @Mock
    private HeadLibrarianRepository headLibrarianDao;
    @Mock
    private UserAccountRepository userAccountDao;

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
    private static final Time HOLIDAY_START_TIME = Time.valueOf("08:00:00");
    private static final Time HOLIDAY_END_TIME = Time.valueOf("20:00:00");

    private static final int HOLIDAY_ID2 = 10;
    private static final Date HOLIDAY_DATE2 = Date.valueOf("2020-12-25");
    private static final Time HOLIDAY_START_TIME2 = Time.valueOf("06:00:00");
    private static final Time HOLIDAY_END_TIME2 = Time.valueOf("23:00:00");
    

    @BeforeEach
    public void setMockOutput() {
        lenient().when(holidayDao.findHolidayByHolidayID(anyInt())).thenAnswer( (InvocationOnMock invocation) -> {
            if(invocation.getArgument(0).equals(HOLIDAY_ID)) {
                Holiday holiday = new Holiday(HOLIDAY_DATE, HOLIDAY_START_TIME, HOLIDAY_END_TIME, this.headLibrarian);
                this.headLibrarian.setLibrarianID(HEAD_LIBRARIAN_ID);
                holiday.setHolidayID(HOLIDAY_ID);
                return holiday;
            } else {
                return null;
            }
        });

        lenient().when(holidayDao.findByHeadLibrarian(any(HeadLibrarian.class))).thenAnswer( (InvocationOnMock invocation) -> {
            if(((HeadLibrarian) invocation.getArgument(0)).getUserID() == (HEAD_LIBRARIAN_ID)) {
                Holiday holiday = new Holiday(HOLIDAY_DATE, HOLIDAY_START_TIME, HOLIDAY_END_TIME, this.headLibrarian);
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

        lenient().when(userAccountDao.findUserAccountByUserID(anyInt())).thenAnswer( (InvocationOnMock invocation) -> {
            if(((int) invocation.getArgument(0)) > 0) {
                this.headLibrarian.setLibrarianID(HEAD_LIBRARIAN_ID);
                return this.headLibrarian;
            } else {
                return null;
            }
            
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
     * Test create holiday
     * Failure case: Invalid Date
     * @author Mathieu Geoffroy
     */
    @Test
    public void testCreateHolidayInvalidDate() {
        String error = null;
        
        Date date = null;
        Time startTime = Time.valueOf("08:00:00");
        Time endTime = Time.valueOf("20:00:00");

        Holiday holiday = null;

        try {
            holiday = service.createHoliday(date, startTime, endTime);
        } catch (Exception e) {
            error = e.getMessage();
        }

        assertNull(holiday);
        assertEquals("Invalid date.", error);
    }

    /**
     * Test create holiday
     * Failure case: Invalid startTime
     * @author Mathieu Geoffroy
     */
    @Test
    public void testCreateHolidayInvalidStartTime() {
        String error = null;
        
        Date date = Date.valueOf("2021-12-25");
        Time startTime = null;
        Time endTime = Time.valueOf("20:00:00");

        Holiday holiday = null;

        try {
            holiday = service.createHoliday(date, startTime, endTime);
        } catch (Exception e) {
            error = e.getMessage();
        }

        assertNull(holiday);
        assertEquals("Invalid startTime.", error);
    }

    /**
     * Test create holiday
     * Failure case: Invalid endTime
     * @author Mathieu Geoffroy
     */
    @Test
    public void testCreateHolidayInvalidEndTime() {
        String error = null;
        
        Date date = Date.valueOf("2021-12-25");
        Time startTime = Time.valueOf("20:00:00");
        Time endTime = null;

        Holiday holiday = null;

        try {
            holiday = service.createHoliday(date, startTime, endTime);
        } catch (Exception e) {
            error = e.getMessage();
        }

        assertNull(holiday);
        assertEquals("Invalid endTime.", error);
    }

    /**
     * Test create holiday
     * Failure case: endTime before startTime
     * @author Mathieu Geoffroy
     */
    @Test
    public void testCreateHolidayInvalidTimeArrangement() {
        String error = null;
        
        Date date = Date.valueOf("2021-12-25");
        Time startTime = Time.valueOf("20:00:00");
        Time endTime = Time.valueOf("18:00:00");

        Holiday holiday = null;

        try {
            holiday = service.createHoliday(date, startTime, endTime);
        } catch (Exception e) {
            error = e.getMessage();
        }

        assertNull(holiday);
        assertEquals("StartTime must be before endTime.", error);
    }

    /**
     * Test get holiday from headLibrarian
     * Success case
     * @author Mathieu Geoffroy
     */
    @Test
    public void testGetHolidayFromHeadLibrarian() {
        String error = null;
        Date date = Date.valueOf("2021-12-25");
        Time startTime = Time.valueOf("08:00:00");
        Time endTime = Time.valueOf("20:00:00");

        Holiday holiday = new Holiday(date, startTime, endTime, this.headLibrarian);
        holiday.setHolidayID(HOLIDAY_ID);
        holidayDao.save(holiday);

        List<Holiday> holidays = null;

        lenient().when(headLibrarianDao.existsById(anyInt())).thenReturn(true);

        try {
            holidays = service.getHolidaysFromHeadLibrarian(HEAD_LIBRARIAN_ID);
        } catch (Exception e) {
            error = e.getMessage();
        }
        assertNotNull(holiday);
        assertHolidayAttributes(holiday, date, startTime, endTime);
    }

    /**
     * Test get holiday from headLibrarian
     * Failure case: null headLibrarain
     * @author Mathieu Geoffroy
     */
    @Test
    public void testGetHolidayFromHeadLibrarianNull() {
        String error = null;
        Date date = Date.valueOf("2021-12-25");
        Time startTime = Time.valueOf("08:00:00");
        Time endTime = Time.valueOf("20:00:00");

        Holiday holiday = new Holiday(date, startTime, endTime, this.headLibrarian);
        holiday.setHolidayID(HOLIDAY_ID);
        holidayDao.save(holiday);

        holiday = null;

        try {
            holiday = service.getHolidaysFromHeadLibrarian(-1).get(0); //invalid id
        } catch (Exception e) {
            error = e.getMessage();
        }

        assertNull(holiday);
        assertEquals("Headlibrarian doesn't exists", error);
    }


    /**
     * Test get holiday from ID
     * Success case
     * @author Amani Jammoul
     */
    @Test
    public void testGetHolidayFromIDSuccess() {
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

    @Test
    public void testGetAllHolidays() throws Exception {
        lenient().when(holidayDao.findAll()).thenAnswer( (InvocationOnMock invocation) -> {
            Holiday holiday1 = new Holiday(HOLIDAY_DATE, HOLIDAY_START_TIME, HOLIDAY_END_TIME, headLibrarian);
            holiday1.setHolidayID(HOLIDAY_ID);
            Holiday holiday2 = new Holiday(HOLIDAY_DATE2, HOLIDAY_START_TIME2, HOLIDAY_END_TIME2, headLibrarian);
            holiday2.setHolidayID(HOLIDAY_ID2);
            List<Holiday> list = new ArrayList<Holiday>();
            list.add(holiday1);
            list.add(holiday2);
            return list;
        });
        List<Holiday>  holidays = null;
        try{
            holidays = service.getAllHolidays();
        }catch(IllegalArgumentException e){
            throw new Exception("could not retrieve users");
        }
        
        assertEquals(2, holidays.size());
    }

    /**
     * Test get holiday from ID
     * Failure case : get holiday from invalid ID
     * @author Amani Jammoul
     */
    @Test
    public void testGetHolidayFromInvalidID() {
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
     * Test delete opening hour 
     * Success case 
     * @author Mathieu Geoffroy
     */
    @Test
    public void testDeleteHolidaySuccess() {
        boolean test = false;
        Holiday holiday = holidayDao.findHolidayByHolidayID(HOLIDAY_ID);
        lenient().when(holidayDao.existsById(anyInt())).thenReturn(true);
        
        int endLength = 0;
        try {
            test = service.deleteHoliday(headLibrarian.getUserID(), holiday.getHolidayID());
            Iterable<Holiday> allHolidays = holidayDao.findAll();
            ArrayList<Holiday> listHolidaysAfter = new ArrayList<Holiday>();
            for(Holiday h: allHolidays){
                listHolidaysAfter.add(h);
            }
            endLength = listHolidaysAfter.size();
        } catch (Exception e) {
            fail();
        }

        assertEquals(0, endLength);
        assertTrue(test);
    }

    /**
     * Test delete opening hour 
     * Failure case : invalid ID
     * @author Mathieu Geoffroy
     */
    @Test
    public void testDeleteHolidayInvalidId() {
        String error = null;
        
        lenient().when(holidayDao.existsById(anyInt())).thenReturn(true);

        try {
            service.deleteHoliday(headLibrarian.getUserID(), HOLIDAY_INVALID_ID);
        } catch (Exception e) {
            error = e.getMessage();
        }

        assertEquals("Invalid holidayID.", error);
    }

    /**
     * Test delete opening hour 
     * Failure case : null account
     * @author Mathieu Geoffroy
     */
    @Test
    public void testDeleteHolidayNullAccount() {
        String error = "";
        Holiday holiday = holidayDao.findHolidayByHolidayID(HOLIDAY_ID);
        lenient().when(holidayDao.existsById(anyInt())).thenReturn(true);

        try {
            service.deleteHoliday(-1, holiday.getHolidayID()); //invalid id
            if (holidayDao.findAll().iterator().hasNext()) {  //gets timeslot if there, othewise, set to null
                holiday = holidayDao.findAll().iterator().next();
            } else {
                holiday = null;
            }
        } catch (Exception e) {
            error = e.getMessage();
        }

        assertEquals("Invalid account.", error);
    }


    /**
     * Verifies all Holiday params are equivalent to those for the object given
     * @param holiday
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
