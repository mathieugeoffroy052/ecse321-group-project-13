package ca.mcgill.ecse321.libraryservice.service;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalTime;
import java.time.LocalDate;
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

import ca.mcgill.ecse321.libraryservice.model.*;
import ca.mcgill.ecse321.libraryservice.model.LibraryItem.ItemType;
import ca.mcgill.ecse321.libraryservice.dao.*;


@ExtendWith(MockitoExtension.class)
public class TestLibraryServiceService {

	@Mock
	private LibraryItemRepository libraryItemDao;

	@Mock
	private LibrarySystemRepository librarySystemDao;

	@InjectMocks
	private LibraryServiceService service;
	
	private LibrarySystem libSystem;
	
	private static final int LIBRARY_ITEM_ISBN = 123;
	private static final String LIBRARY_ITEM_CREATOR = "Jeff Joseph";
	private static final String LIBRARY_ITEM_NAME = "History Of Java";
	private static final ItemType LIBRARY_ITEM_TYPE = ItemType.Book;
	private static final Date LIBRARY_ITEM_DATE = Date.valueOf("2009-03-15");
	private static final boolean LIBRARY_ITEM_IS_VIEWABLE = false;
	
	private static final int LIBRARY_SYSTEM_ID = 1;

	@BeforeEach
	public void setMockOutput() {
		lenient().when(librarySystemDao.findBySystemId(anyInt())).thenAnswer((InvocationOnMock invocation) -> {
			if (invocation.getArgument(0).equals(LIBRARY_SYSTEM_ID)) {
				LibrarySystem librarySystem = new LibrarySystem();
				librarySystem.setSystemId(LIBRARY_SYSTEM_ID);
				this.libSystem = librarySystem;
				return librarySystem;
			} else {
				return null;
			}
		});
		lenient().when(libraryItemDao.findByIsbn(anyInt())).thenAnswer((InvocationOnMock invocation) -> {
			if (invocation.getArgument(0).equals(LIBRARY_ITEM_ISBN)) {
				LibraryItem libraryItem = new LibraryItem();
				libraryItem.setIsbn(LIBRARY_ITEM_ISBN);
				libraryItem.setCreator(LIBRARY_ITEM_CREATOR);
				libraryItem.setDate(LIBRARY_ITEM_DATE);
				libraryItem.setName(LIBRARY_ITEM_NAME);
				libraryItem.setType(LIBRARY_ITEM_TYPE);
				libraryItem.setIsViewable(LIBRARY_ITEM_IS_VIEWABLE);
				libraryItem.setLibrarySystem(libSystem);
				return libraryItem;
			} else {
				return null;
			}
		});
		// Whenever anything is saved, just return the parameter object
		Answer<?> returnParameterAsAnswer = (InvocationOnMock invocation) -> {
			return invocation.getArgument(0);
		};
		lenient().when(librarySystemDao.save(any(LibrarySystem.class))).thenAnswer(returnParameterAsAnswer);
		lenient().when(libraryItemDao.save(any(LibraryItem.class))).thenAnswer(returnParameterAsAnswer);
		System.err.println(libraryItemDao.findAll());
	}

	@Test
	public void testGetBookFromTitle() {
		try {
			assertEquals(LIBRARY_ITEM_NAME, service.getBookFromTitle(LIBRARY_ITEM_NAME).getName());
			assertEquals(true, false);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.err.print("Could not get book!");
		}
		
	}
    
}
