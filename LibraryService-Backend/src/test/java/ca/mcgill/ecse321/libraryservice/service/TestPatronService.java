package ca.mcgill.ecse321.libraryservice.service;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
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


import ca.mcgill.ecse321.libraryservice.model.*;
import ca.mcgill.ecse321.libraryservice.dao.*;

@ExtendWith(MockitoExtension.class)
public class TestPatronService {

	
@Mock 
private PatronRepository patronDAO;

@InjectMocks
private static final int PATRON_ID = 12345;
private static final String PATRON_KEY = "TestPatron";

@BeforeEach
public void setMockOutput() {
    lenient().when(patronDAO.findPatronByUserID(anyInt())).thenAnswer( (InvocationOnMock invocation) -> {
        if(invocation.getArgument(0).equals(PATRON_ID)) {
            Patron patron = new Patron();
            patron.setPatronID(PATRON_ID);
            return patron;
        } else {
            return null;
        }
    });
}
	
	
	
	
}
