package ca.mcgill.ecse321.libraryservice.service;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.fail;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.anyInt;;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

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

    @InjectMocks
    private LibraryServiceService service;

    private static final int TIMESLOT_KEY = 1;
    private static final Date TIMESLOT_STARTDATE = new Date(2020, 12, 25);
    private static final Date TIMESLOT_ENDDATE = new Date(2020, 12, 28);
    private static final Time TIMESLOT_STARTTIME = new Time(10, 00, 00);
    private static final Time TIMESLOT_ENDTIME = new Time(11, 00, 00);
    private static final HeadLibrarian TIMESLOT_HEADLIBRARIAN = new HeadLibrarian();

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
        Answer<?> returnParameterAsAnswer = (InvocationOnMock invocation) -> {
			return invocation.getArgument(0);
		};
        lenient().when(timeslotDao.save(any(TimeSlot.class))).thenAnswer(returnParameterAsAnswer);
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
            service.createTimeSlot(startDate, startTime, endDate, endTime);
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
            service.createTimeSlot(startDate, startTime, endDate, endTime);
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
            service.createTimeSlot(startDate, startTime, endDate, endTime);
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
            service.createTimeSlot(startDate, startTime, endDate, endTime);
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
            service.createTimeSlot(startDate, startTime, endDate, endTime);
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
        Time startTime = new Time(17, 00, 00);
        Time endTime = new Time(19, 00, 00);

        TimeSlot timeslot = null;

        try {
            service.createTimeSlot(startDate, startTime, endDate, endTime);
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
            service.createTimeSlot(startDate, startTime, endDate, endTime);
        } catch (Exception e) {
            error = e.getMessage();
        }

        assertNull(timeslot);
        assertEquals("StartDate cannot be after endDate", error);

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
}
