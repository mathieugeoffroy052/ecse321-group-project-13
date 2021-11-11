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

import ca.mcgill.ecse321.libraryservice.dao.TimeSlotRepository;
import ca.mcgill.ecse321.libraryservice.dao.HeadLibrarianRepository;
import ca.mcgill.ecse321.libraryservice.dao.LibrarianRepository;
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


    /**
     * mocks output of DB for timeslot get by id, headlibrarian find all, headlibrarian get by id, librarian get by id
     * @author Mathieu Geoffroy
     */
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

        lenient().when(timeslotDao.findByLibrarian(any(Librarian.class))).thenAnswer( (InvocationOnMock invocation) -> {
            if(((Librarian) invocation.getArgument(0)).getUserID() == LIBRARIAN_KEY) {
                TimeSlot timeslot = new TimeSlot(TIMESLOT_STARTDATE, TIMESLOT_STARTTIME, TIMESLOT_ENDDATE, TIMESLOT_ENDTIME, TIMESLOT_HEADLIBRARIAN);
                timeslot.setTimeSlotID(TIMESLOT_KEY);
                List<TimeSlot> list = new ArrayList<TimeSlot>();
                list.add(timeslot);
                return list;
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
            if(((int) invocation.getArgument(0)) == HEADLIBRARIAN_KEY) {
            HeadLibrarian headLibrarian = new HeadLibrarian(HEADLIBRARIAN_FIRSTNAME, HEADLIBRARIAN_LASTNAME, HEADLIBRARIAN_VALIDACC, HEADLIBRARIAN_ADDRESS, HEADLIBRARIAN_PASSWORD, HEADLIBRARIAN_BALANCE, HEADLIBRARIAN_EMAIL);
            headLibrarian.setUserID(HEADLIBRARIAN_KEY);
            return headLibrarian;
            } else {
                return null;
            }
        });

        lenient().when(librarianDao.findLibrarianByUserID(anyInt())).thenAnswer( (InvocationOnMock invocation) -> {
            if(((int) invocation.getArgument(0)) == LIBRARIAN_KEY) {
            Librarian librarian = new Librarian(LIBRARIAN_FIRSTNAME, LIBRARIAN_LASTNAME, LIBRARIAN_VALIDACC, LIBRARIAN_ADDRESS, LIBRARIAN_PASSWORD, LIBRARIAN_BALANCE, LIBRARIAN_EMAIL);
            librarian.setUserID(LIBRARIAN_KEY);
            return librarian;
            } else {
                return null;
            }
        });
        Answer<?> returnParameterAsAnswer = (InvocationOnMock invocation) -> {
			return invocation.getArgument(0);
		};
        lenient().when(timeslotDao.save(any(TimeSlot.class))).thenAnswer(returnParameterAsAnswer);
    }

    /**
     * Clears mock DB outputs after each test
     * @author Mathieu Geoffroy
     */
    @AfterEach
    public void clearMockOutputs() {
        timeslotDao.deleteAll();
        headLibrarianDao.deleteAll();
    }

    /**
     * test create timeslot 
     * @author Mathieu Geoffroy
     */
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

    /**
     * test create timeslot with a null startdate input
     * @author Mathieu Geoffroy
     */
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

    /**
     * test create timeslot with a null starttime input
     * @author Mathieu Geoffroy
     */
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

    /**
     * test create timeslot with a null enddate input
     * @author Mathieu Geoffroy
     */
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

    /**
     * test create timeslot with a null endtime input
     * @author Mathieu Geoffroy
     */
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

    /**
     * test create timeslot with starttime after endtime
     * @author Mathieu Geoffroy
     */
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

    /**
     * test create timeslot with startdate after enddate
     * @author Mathieu Geoffroy
     */
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

    /**
     * test assign timeslot to a librarian
     * @author Mathieu Geoffroy
     */
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

    /**
     * test assign a null timeslot to a librarian
     * @author Mathieu Geoffroy
     */
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

    /**
     * test assign timeslot to null librarian
     * @author Mathieu Geoffroy
     */
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

    /**
     * test delete timeslot from mock DB
     * @author Mathieu Geoffroy
     */
    @Test
    public void testDeleteTimeSlot() {
        assertEquals(0, service.getAllTimeSlots().size());
        boolean test = false;

        HeadLibrarian headLibrarian = headLibrarianDao.findHeadLibrarianByUserID(HEADLIBRARIAN_KEY);
        TimeSlot timeslot = timeslotDao.findTimeSlotByTimeSlotID(TIMESLOT_KEY);
        lenient().when(headLibrarianDao.existsById(anyInt())).thenReturn(true);
        lenient().when(timeslotDao.existsById(anyInt())).thenReturn(true);
        try {
            test = service.deleteTimeSlot(headLibrarian, timeslot.getTimeSlotID());
             if (timeslotDao.findAll().iterator().hasNext()) {  //gets timeslot if there, othewise, set to null
                 timeslot = timeslotDao.findAll().iterator().next();
             } else {
                 timeslot = null;
             }
        } catch (Exception e) {
            fail();
        }
        assertNull(timeslot);
        assertTrue(test);
    }

    /**
     * test delete timeslot from mock DB with an unathorized account
     * @author Mathieu Geoffroy
     */
    @Test
    public void testDeleteTimeSlotInvalidAccountCall() {
        assertEquals(0, service.getAllTimeSlots().size());
        boolean test = false;
        String error = null;

        Librarian librarian = librarianDao.findLibrarianByUserID(LIBRARIAN_KEY);
        TimeSlot timeslot = timeslotDao.findTimeSlotByTimeSlotID(TIMESLOT_KEY);
        lenient().when(librarianDao.existsById(anyInt())).thenReturn(true);
        lenient().when(timeslotDao.existsById(anyInt())).thenReturn(true);
        try {
            test = service.deleteTimeSlot(librarian, timeslot.getTimeSlotID());
             if (timeslotDao.findAll().iterator().hasNext()) {  //gets timeslot if there, othewise, set to null
                 timeslot = timeslotDao.findAll().iterator().next();
             } else {
                 timeslot = null;
             }
        } catch (Exception e) {
            error = e.getMessage();
        }
        assertEquals("This User ID does not correspond to a Head Librarian.", error);
        assertFalse(test);
    }

    /**
     * test delete timeslot from mock DB with an null account
     * @author Mathieu Geoffroy
     */
    @Test
    public void testDeleteTimeSlotNullAccount() {
        assertEquals(0, service.getAllTimeSlots().size());
        boolean test = false;
        String error = null;

        UserAccount account = null;
        TimeSlot timeslot = timeslotDao.findTimeSlotByTimeSlotID(TIMESLOT_KEY);
        lenient().when(timeslotDao.existsById(anyInt())).thenReturn(true);
        try {
            test = service.deleteTimeSlot(account, timeslot.getTimeSlotID());
             if (timeslotDao.findAll().iterator().hasNext()) {  //gets timeslot if there, othewise, set to null
                 timeslot = timeslotDao.findAll().iterator().next();
             } else {
                 timeslot = null;
             }
        } catch (Exception e) {
            error = e.getMessage();
        }
        assertEquals("Invalid account.", error);
        assertFalse(test);
    }

    /**
     * test get timeslot with its key
     * @author Mathieu Geoffroy
     */
    @Test
    public void testGetTimeSlotWithKey() {
        try {
            assertEquals(TIMESLOT_KEY, service.getTimeSlotsFromId(TIMESLOT_KEY).getTimeSlotID());
        } catch (Exception e) {
            fail();
        }
    }

    /**
     * test get timeslot with invalid key
     * @author Mathieu Geoffroy
     */
    @Test
    public void testGetTimeSlotWrongKey() {
        TimeSlot timeslot = null;
        String error = null;
        try {
            timeslot = service.getTimeSlotsFromId(-1);
        } catch (Exception e) {
            error = e.getMessage();
        }
        assertNull(timeslot);
        assertEquals("Invalid id", error);
    }

    /**
     * test get timeslot from librarian
     * @author Mathieu Geoffroy
     */
    @Test
    public void testGetTimeSlotWithLibrarian() {
        Librarian librarian = librarianDao.findLibrarianByUserID(LIBRARIAN_KEY);
        TimeSlot timeslot = timeslotDao.findTimeSlotByTimeSlotID(TIMESLOT_KEY);
        lenient().when(librarianDao.existsById(anyInt())).thenReturn(true);
        lenient().when(timeslotDao.existsById(anyInt())).thenReturn(true);
        
        try {
            service.assignTimeSlotToLibrarian(timeslot, librarian);
            assertEquals(TIMESLOT_KEY, service.getTimeSlotsFromLibrarian(librarian).get(0).getTimeSlotID());
        } catch (Exception e) {
            fail();
        }
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
