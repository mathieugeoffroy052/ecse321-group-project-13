package ca.mcgill.ecse321.libraryservice.service;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.booleanThat;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Calendar;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

import ca.mcgill.ecse321.libraryservice.dao.*;
import ca.mcgill.ecse321.libraryservice.model.*;

@ExtendWith(MockitoExtension.class) @SuppressWarnings("deprecation")
public class TestTimeSlotService {
    @Mock
    private TimeSlotRepository timeslotDao;
    @Mock
    private HeadLibrarianRepository headLibrarianDao;
    @Mock
    private LibrarianRepository librarianDao;

    @InjectMocks
    private LibraryServiceService service;

    private static final int TIMESLOT_KEY = 100;
    private static final Date TIMESLOT_STARTDATE = new Date(2020, 12, 25);
    private static final Date TIMESLOT_ENDDATE = new Date(2020, 12, 28);
    private static final Time TIMESLOT_STARTTIME = new Time(10, 00, 00);
    private static final Time TIMESLOT_ENDTIME = new Time(11, 00, 00);
    private static final HeadLibrarian TIMESLOT_HEADLIBRARIAN = new HeadLibrarian();

    private static final int HEADLIBRARIAN_KEY = 400;
    private static final String HEADLIBRARIAN_FIRSTNAME = "Jane";
    private static final String HEADLIBRARIAN_LASTNAME = "Doe";
    private static final String HEADLIBRARIAN_ADDRESS = "40 durocher";
    private static final String HEADLIBRARIAN_PASSWORD = "badpassword";
    private static final String HEADLIBRARIAN_EMAIL = "jane@mail.com";
    private static final boolean HEADLIBRARIAN_VALIDACC = true;
    private static final int HEADLIBRARIAN_BALANCE = 0;

    private static final int LIBRARIAN_KEY = 2550;
    private static final String LIBRARIAN_FIRSTNAME = "John";
    private static final String LIBRARIAN_LASTNAME = "Marx";
    private static final String LIBRARIAN_ADDRESS = "150 durocher";
    private static final String LIBRARIAN_PASSWORD = "mehpassword";
    private static final String LIBRARIAN_EMAIL = "jaohn@mail.com";
    private static final boolean LIBRARIAN_VALIDACC = true;
    private static final int LIBRARIAN_BALANCE = 0;




    @BeforeEach
    public void setMockOutput() {
        lenient().when(timeslotDao.findTimeSlotByTimeSlotID(anyInt())).thenAnswer( (InvocationOnMock invocation) -> {
            if(invocation.getArgument(0).equals(TIMESLOT_KEY)) {
                TimeSlot timeslot = new TimeSlot(TIMESLOT_STARTDATE, TIMESLOT_STARTTIME, TIMESLOT_ENDDATE, TIMESLOT_ENDTIME, TIMESLOT_HEADLIBRARIAN);
                timeslot.setTimeSlotID(TIMESLOT_KEY);
                return timeslot;
            } else {
                return null;
            }
        });

        lenient().when(headLibrarianDao.findAll()).thenAnswer( (InvocationOnMock invocation) -> {
                HeadLibrarian headLibrarian = new HeadLibrarian(HEADLIBRARIAN_FIRSTNAME, HEADLIBRARIAN_LASTNAME, HEADLIBRARIAN_VALIDACC, HEADLIBRARIAN_ADDRESS, HEADLIBRARIAN_PASSWORD, HEADLIBRARIAN_BALANCE, HEADLIBRARIAN_EMAIL);
                headLibrarian.setUserID(HEADLIBRARIAN_KEY);
                
                List<HeadLibrarian> list = new ArrayList<HeadLibrarian>();
                list.add(headLibrarian);
                return list;
        });

        lenient().when(headLibrarianDao.findHeadLibrarianByUserID(anyInt())).thenAnswer( (InvocationOnMock invocation) -> {
            HeadLibrarian headLibrarian = new HeadLibrarian(HEADLIBRARIAN_FIRSTNAME, HEADLIBRARIAN_LASTNAME, HEADLIBRARIAN_VALIDACC, HEADLIBRARIAN_ADDRESS, HEADLIBRARIAN_PASSWORD, HEADLIBRARIAN_BALANCE, HEADLIBRARIAN_EMAIL);
            headLibrarian.setUserID(HEADLIBRARIAN_KEY);
            return headLibrarian;
    });

        lenient().when(librarianDao.findLibrarianByUserID(anyInt())).thenAnswer( (InvocationOnMock invocation) -> {
            Librarian librarian = new Librarian(LIBRARIAN_FIRSTNAME, LIBRARIAN_LASTNAME, LIBRARIAN_VALIDACC, LIBRARIAN_ADDRESS, LIBRARIAN_PASSWORD, LIBRARIAN_BALANCE, LIBRARIAN_EMAIL);
            librarian.setUserID(LIBRARIAN_KEY);
            return librarian;
    });
        Answer<?> returnParameterAsAnswer = (InvocationOnMock invocation) -> {
			return invocation.getArgument(0);
		};
        lenient().when(timeslotDao.save(any(TimeSlot.class))).thenAnswer(returnParameterAsAnswer);
    }

    @AfterEach
    public void clearMockOutputs() {
        timeslotDao.deleteAll();
        headLibrarianDao.deleteAll();
    }

    @Test
    public void testCreateTimeSlot() {
        assertEquals(0, service.getAllTimeSlots().size());
        
        Date startDate = new Date(2021, 12, 25);
        Date endDate = new Date(2021, 12, 28);
        Time startTime = new Time(17, 00, 00);
        Time endTime = new Time(19, 00, 00);

        TimeSlot timeslot = null;

        try {
            timeslot = service.createTimeSlot(startDate, startTime, endDate, endTime);
        } catch (Exception e) {
            fail();
        }

        assertTimeSlotAttributes(timeslot, startDate, startTime, endDate, endTime);

    }

    @Test
    public void testCreateTimeSlotStartDateNull() {
        assertEquals(0, service.getAllTimeSlots().size());
        String error = null;

        Date startDate = null;
        Date endDate = new Date(2021, 12, 28);
        Time startTime = new Time(17, 00, 00);
        Time endTime = new Time(19, 00, 00);

        TimeSlot timeslot = null;

        try {
            timeslot = service.createTimeSlot(startDate, startTime, endDate, endTime);
        } catch (Exception e) {
            error = e.getMessage();
        }

        assertNull(timeslot);
        assertEquals("Invalid startDate", error);

    }

    @Test
    public void testCreateTimeSlotStartTimeNull() {
        assertEquals(0, service.getAllTimeSlots().size());
        String error = null;

        Date startDate = new Date(2021, 12, 25);
        Date endDate = new Date(2021, 12, 28);
        Time startTime = null;
        Time endTime = new Time(19, 00, 00);

        TimeSlot timeslot = null;

        try {
            timeslot = service.createTimeSlot(startDate, startTime, endDate, endTime);
        } catch (Exception e) {
            error = e.getMessage();
        }

        assertNull(timeslot);
        assertEquals("Invalid startTime", error);

    }

    @Test
    public void testCreateTimeSlotEndDateNull() {
        assertEquals(0, service.getAllTimeSlots().size());
        String error = null;

        Date startDate = new Date(2021, 12, 28);
        Date endDate = null;
        Time startTime = new Time(17, 00, 00);
        Time endTime = new Time(19, 00, 00);

        TimeSlot timeslot = null;

        try {
            timeslot = service.createTimeSlot(startDate, startTime, endDate, endTime);
        } catch (Exception e) {
            error = e.getMessage();
        }

        assertNull(timeslot);
        assertEquals("Invalid endDate", error);

    }

    @Test
    public void testCreateTimeSlotEndTimeNull() {
        assertEquals(0, service.getAllTimeSlots().size());
        String error = null;

        Date startDate = new Date(2021, 12, 25);
        Date endDate = new Date(2021, 12, 28);
        Time startTime = new Time(17, 00, 00);
        Time endTime = null;

        TimeSlot timeslot = null;

        try {
            timeslot = service.createTimeSlot(startDate, startTime, endDate, endTime);
        } catch (Exception e) {
            error = e.getMessage();
        }

        assertNull(timeslot);
        assertEquals("Invalid endTime", error);

    }

    @Test
    public void testCreateTimeSlotStartTimeAfterEndTime() {
        assertEquals(0, service.getAllTimeSlots().size());
        String error = null;

        Date startDate = new Date(2021, 12, 25);
        Date endDate = new Date(2021, 12, 28);
        Time startTime = new Time(19, 00, 00);
        Time endTime = new Time(17, 00, 00);

        TimeSlot timeslot = null;

        try {
            timeslot = service.createTimeSlot(startDate, startTime, endDate, endTime);
        } catch (Exception e) {
            error = e.getMessage();
        }

        assertNull(timeslot);
        assertEquals("StartTime cannot be after endTime", error);

    }

    @Test
    public void testCreateTimeSlotStartDateAfterEndDate() {
        assertEquals(0, service.getAllTimeSlots().size());
        String error = null;

        Date startDate = new Date(2021, 12, 28);
        Date endDate = new Date(2021, 12, 25);
        Time startTime = new Time(17, 00, 00);
        Time endTime = new Time(19, 00, 00);

        TimeSlot timeslot = null;

        try {
            timeslot = service.createTimeSlot(startDate, startTime, endDate, endTime);
        } catch (Exception e) {
            error = e.getMessage();
        }

        assertNull(timeslot);
        assertEquals("StartDate cannot be after endDate", error);

    }

    @Test
    public void testAssignTimeSlot() {
        assertEquals(0, service.getAllTimeSlots().size());

        Librarian librarian = librarianDao.findLibrarianByUserID(LIBRARIAN_KEY);
        TimeSlot timeslot = timeslotDao.findTimeSlotByTimeSlotID(TIMESLOT_KEY);
        lenient().when(librarianDao.existsById(anyInt())).thenReturn(true);
        lenient().when(timeslotDao.existsById(anyInt())).thenReturn(true);
        try {
            timeslot = service.assignTimeSlotToLibrarian(timeslot, librarian);
        } catch (Exception e) {
            fail();
        }

        assertNotNull(timeslot);
        assertTimeSlotAttributes(timeslot, TIMESLOT_STARTDATE, TIMESLOT_STARTTIME, TIMESLOT_ENDDATE, TIMESLOT_ENDTIME, librarian);

    }

    @Test
    public void testAssignNullTimeSlot() {
        assertEquals(0, service.getAllTimeSlots().size());
        String error = null;

        Librarian librarian = librarianDao.findLibrarianByUserID(LIBRARIAN_KEY);
        lenient().when(librarianDao.existsById(anyInt())).thenReturn(true);
        TimeSlot timeslot = null;
        try {
            timeslot = service.assignTimeSlotToLibrarian(timeslot, librarian);
        } catch (Exception e) {
            error = e.getMessage();
        }

        assertEquals("TimeSlot needs to be selected for registration!", error);

    }

    @Test
    public void testAssignTimeSlotNullLibrarian() {
        assertEquals(0, service.getAllTimeSlots().size());
        String error = null;

        TimeSlot timeslot = timeslotDao.findTimeSlotByTimeSlotID(TIMESLOT_KEY);
        lenient().when(timeslotDao.existsById(anyInt())).thenReturn(true);

        Librarian librarian = null;


        try {
            timeslot = service.assignTimeSlotToLibrarian(timeslot, librarian);
        } catch (Exception e) {
            error = e.getMessage();
        }

        assertEquals("Librarian needs to be selected for registration!", error);

    }

    @Test
    public void testDeleteTimeSlot() {
        assertEquals(0, service.getAllTimeSlots().size());
        String error = null;
        boolean test = false;

        HeadLibrarian headLibrarian = headLibrarianDao.findHeadLibrarianByUserID(HEADLIBRARIAN_KEY);
        TimeSlot timeslot = timeslotDao.findTimeSlotByTimeSlotID(TIMESLOT_KEY);
        lenient().when(headLibrarianDao.existsById(anyInt())).thenReturn(true);
        lenient().when(timeslotDao.existsById(anyInt())).thenReturn(true);
        try {
            test = service.deleteTimeSlot(headLibrarian, timeslot.getTimeSlotID());
        } catch (Exception e) {
            fail();
        }

        assertNotNull(timeslot);
        assertTrue(test);
        

    }

    
    /**
     * Asserts all timeslot attributes
     * @param timeslot
     * @param startDate
     * @param startTime
     * @param endDate
     * @param endTime
     * @author Mathieu Geoffroy
     */
    private void assertTimeSlotAttributes(TimeSlot timeslot, Date startDate, Time startTime, Date endDate, Time endTime) {
        assertNotNull(timeslot);
        assertEquals(startDate.toString(), timeslot.getStartDate().toString());
        assertEquals(startTime.toString(), timeslot.getStartTime().toString());
        assertEquals(endDate.toString(), timeslot.getEndDate().toString());
        assertEquals(endTime.toString(), timeslot.getEndTime().toString());
    }

    /**
     * Asserts all timeslot attributes and tests librarian association
     * @param timeslot
     * @param startDate
     * @param startTime
     * @param endDate
     * @param endTime
     * @param librarian Must only have 1 librarian in the system
     * @author Mathieu Geoffroy
     */
    private void assertTimeSlotAttributes(TimeSlot timeslot, Date startDate, Time startTime, Date endDate, Time endTime, Librarian librarian) {
        assertNotNull(timeslot);
        assertEquals(startDate.toString(), timeslot.getStartDate().toString());
        assertEquals(startTime.toString(), timeslot.getStartTime().toString());
        assertEquals(endDate.toString(), timeslot.getEndDate().toString());
        assertEquals(endTime.toString(), timeslot.getEndTime().toString());
        assertEquals(librarian.getFirstName(), timeslot.getLibrarian().iterator().next().getFirstName());
        assertEquals(librarian.getLastName(), timeslot.getLibrarian().iterator().next().getLastName());
    }
}
