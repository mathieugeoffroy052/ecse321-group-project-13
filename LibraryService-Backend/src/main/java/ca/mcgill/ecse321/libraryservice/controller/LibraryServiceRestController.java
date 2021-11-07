package ca.mcgill.ecse321.libraryservice.controller;

import java.sql.Time;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;

import ca.mcgill.ecse321.libraryservice.dto.*;
import ca.mcgill.ecse321.libraryservice.dto.LibraryItemDTO.ItemType;
import ca.mcgill.ecse321.libraryservice.model.*;
import ca.mcgill.ecse321.libraryservice.service.LibraryServiceService;

@CrossOrigin(origins = "*")
@RestController
public class LibraryServiceRestController {
    @Autowired
	private LibraryServiceService service;


    ////////// Helper methods - convertToDTO////////

    /**
     * @author Gabrielle Halpin
     * @param patron
     * @return
     */
    private PatronDTO convertToDto(Patron patron) {
        if (patron == null) {
            throw new IllegalArgumentException("There is no such patron!");
        }
        PatronDTO patronDTO = new PatronDTO(patron.getFirstName(),patron.getLastName(),patron.getOnlineAccount(),patron.getAddress(), patron.getValidatedAccount(), patron.getPassword(), patron.getBalance(), patron.getEmail());
        return patronDTO;
    }

    /**
     * @author Gabrielle Halpin
     * @param librarian
     * @return
     */
    private LibrarianDTO convertToDto(Librarian librarian) {
        if (librarian == null) {
            throw new IllegalArgumentException("There is no such patron!");
        }
        LibrarianDTO librarianDTO = new LibrarianDTO(librarian.getFirstName(),librarian.getLastName(),librarian.getOnlineAccount(),librarian.getAddress(), librarian.getPassword(), librarian.getBalance(), librarian.getEmail());
        return librarianDTO;
    }

    /**
     * @author Ramin Akhavan-Sarraf
     * @param HeadLibrarian 
     * @return HeadLibrarianDTO
     */
    private HeadLibrarianDTO convertToDto(HeadLibrarian headLibrarian) {
        if (headLibrarian== null) {
            throw new IllegalArgumentException("There is no such head librarian!");
        }
        HeadLibrarianDTO headLibrarianDTO = new HeadLibrarianDTO(headLibrarian.getFirstName(),headLibrarian.getLastName(),headLibrarian.getOnlineAccount(),headLibrarian.getAddress(), headLibrarian.getPassword(), headLibrarian.getBalance(), headLibrarian.getEmail());
        return headLibrarianDTO;
    }

    /**
     * @author Ramin Akhavan-Sarraf
     * @param Holiday
     * @return HolidayDTO
     */
    private HolidayDTO convertToDto(Holiday holiday) {
        if (holiday == null) {
            throw new IllegalArgumentException("There is no such holiday!");
        }
        HeadLibrarian headLibrarian = holiday.getHeadLibrarian();
        HeadLibrarianDTO headLibrarianDTO = new HeadLibrarianDTO(headLibrarian.getFirstName(),headLibrarian.getLastName(),headLibrarian.getOnlineAccount(),headLibrarian.getAddress(), headLibrarian.getPassword(), headLibrarian.getBalance(), headLibrarian.getEmail());
        HolidayDTO holidayDTO = new HolidayDTO(holiday.getDate(), holiday.getStartTime(), holiday.getEndtime(), headLibrarianDTO);
        return holidayDTO;
    }

    /**
     * @author Ramin Akhavan-Sarraf
     * @param OpeningHour
     * @return OpeningHourDTO
     */
    private OpeningHourDTO convertToDto(OpeningHour openingHour) {
        if (openingHour == null) {
            throw new IllegalArgumentException("There is no such opening hour!");
        }
        HeadLibrarian headLibrarian = openingHour.getHeadLibrarian();
        HeadLibrarianDTO headLibrarianDTO = new HeadLibrarianDTO(headLibrarian.getFirstName(),headLibrarian.getLastName(),headLibrarian.getOnlineAccount(),headLibrarian.getAddress(), headLibrarian.getPassword(), headLibrarian.getBalance(), headLibrarian.getEmail());
        OpeningHourDTO.DayOfWeek dayOfWeek = OpeningHourDTO.DayOfWeek.valueOf(openingHour.getDayOfWeek().toString());
        OpeningHourDTO openingHourDTO = new OpeningHourDTO(dayOfWeek, openingHour.getStartTime(), openingHour.getEndTime(), headLibrarianDTO);
        return openingHourDTO;
    }

    /**
     * @author Ramin Akhavan-Sarraf
     * @param LibraryItem
     * @return LibraryItemDTO
     */
    private LibraryItemDTO convertToDto(LibraryItem libraryItem) {
        if (libraryItem== null) {
            throw new IllegalArgumentException("There is no such library item!");
        }
        LibraryItemDTO.ItemType itemType = LibraryItemDTO.ItemType.valueOf(libraryItem.getType().toString());
        LibraryItemDTO libraryItemDTO = new LibraryItemDTO(libraryItem.getName(), itemType, libraryItem.getDate(), libraryItem.getCreator(), libraryItem.getIsViewable());
        return libraryItemDTO;
    }

    private BorrowableItemDTO convertToDto(BorrowableItem borrowableItem) {
        if (borrowableItem== null) {
            throw new IllegalArgumentException("There is no such library item!");
        }
        LibraryItemDTO item = convertToDto(borrowableItem.getLibraryItem());
        BorrowableItemDTO.ItemState itemState = BorrowableItemDTO.ItemState.valueOf(borrowableItem.getState().toString());
        BorrowableItemDTO borrowableItemDTO = new BorrowableItemDTO(itemState, item);
        return borrowableItemDTO;
    }

    /**
     * @author Gabrielle Halpin
     * @param timeslot
     * @return
     */
    private TimeslotDTO convertToDto(TimeSlot timeslot) {
        if (timeslot == null) {
            throw new IllegalArgumentException("There is no such library item!");
        }
        HeadLibrarianDTO headLibrarianDTO = convertToDto(timeslot.getHeadLibrarian());
        Set<LibrarianDTO> librarianDTO = new HashSet<LibrarianDTO>();
        for (Librarian librarian: timeslot.getLibrarian()){
            LibrarianDTO lib = convertToDto(librarian);
            librarianDTO.add(lib);
        }
        TimeslotDTO timeslotDTO= new TimeslotDTO(timeslot.getStartDate(), timeslot.getStartTime(), timeslot.getEndDate(), timeslot.getEndTime(), librarianDTO, headLibrarianDTO);
        return timeslotDTO;
    }

    /**
     * @author Gabrielle Halpin
     * @param transaction
     * @return
     */
    private TransactionDTO convertToDto(Transaction transaction) {
        if (transaction== null) {
            throw new IllegalArgumentException("There is no such library item!");
        }
        BorrowableItemDTO item = convertToDto(transaction.getBorrowableItem());
        UserAccountDTO userAccountDTO = convertToDto(transaction.getUserAccount());
        TransactionDTO.TransactionType itemType = TransactionDTO.TransactionType.valueOf(transaction.getTransactionType().toString());
        TransactionDTO transactionDTO = new TransactionDTO(itemType, transaction.getDeadline(), item, userAccountDTO);
        return transactionDTO;
    }

    /**
     * @author Gabrielle Halpin
     * @param userAccount
     * @return
     */
    private UserAccountDTO convertToDto(UserAccount userAccount) {
        if (userAccount == null) {
            throw new IllegalArgumentException("There is no such library item!");
        }
        UserAccountDTO userAccountDTO = new UserAccountDTO(userAccount.getFirstName(), userAccount.getLastName(), userAccount.getOnlineAccount(), userAccount.getAddress(), userAccount.getPassword(), userAccount.getBalance(), userAccount.getEmail());
        return userAccountDTO;
    }

    ///////// Helper methods - convertToDomainObject//////////
    //each method need to check to make sure the individual is in the system before creating them.

    private BorrowableItem convertToDomainObject(BorrowableItemDTO borrowableItemDTO) {
        return null;
    }

    /**
     * Gets the corresponding regular library item from the DTO version
     * @param LibraryItemDTO
     * @returns LibraryItem
     * @author Ramin Akhavan-Sarraf
     */
    private LibraryItem convertToDomainObject(LibraryItemDTO libraryItemDTO) {
        List<LibraryItem> libraryItems;
        LibraryItem theLibraryItem = null;
        try{
            if(libraryItemDTO.getType() == ItemType.Book){
                libraryItems = service.getAllBooks();
            }
            else if(libraryItemDTO.getType() == ItemType.Movie){
                libraryItems = service.getAllMovies();
            }
            else if(libraryItemDTO.getType() == ItemType.Music){
                libraryItems = service.getAllMusic();
            }
            else if(libraryItemDTO.getType() == ItemType.NewspaperArticle){
                libraryItems = service.getAllNewspapers();
            }
            else{
                libraryItems = service.getAllRoomReservations();
            }
        }
        catch (Exception e){
            throw new IllegalArgumentException("Could not get library item from service!");
        }
        for (LibraryItem libraryItem: libraryItems){
            if(libraryItem.getName().equalsIgnoreCase(libraryItemDTO.getName())){
                theLibraryItem = libraryItem;
            }
        }
        
        if (theLibraryItem == null){
            throw new IllegalArgumentException("There is no such library item dto!");
        }
        return theLibraryItem;
    }

    /**
     * Gets the corresponding regular head librarian from the DTO version
     * @param HeadLibrarianDTO
     * @returns HeadLibrarian
     * @author Ramin Akhavan-Sarraf
     */
    private HeadLibrarian convertToDomainObject(HeadLibrarianDTO headLibrarianDTO) {
        HeadLibrarian headLibrarian;
        try {
            headLibrarian = service.getIfLibrarianHeadFromFullName(headLibrarianDTO.getFirstName(), headLibrarianDTO.getLastName());
        } catch (Exception e) {
            throw new IllegalArgumentException("Could not get head librarian from service!");
        }

        if (headLibrarian == null) {
            throw new IllegalArgumentException("There is no such head librarian dto!");
        }
        return headLibrarian;
    }

  /***
     * Gets the corresponding regular holiday from the DTO version
     * @param HolidayDTO
     * @returns Holiday
     * @author Ramin Akhavan-Sarraf
     */
    private Holiday convertToDomainObject(HolidayDTO holidayDTO) {
        List<Holiday> holidays;
        Holiday theHoliday = null;
        try {
            holidays = service.getAllHolidays();
        } catch (Exception e) {
            throw new IllegalArgumentException("Could not get librarian from service!");
        }
        for(Holiday holiday: holidays){
            if(holiday.getDate().toLocalDate().isEqual(holidayDTO.getDate().toLocalDate())){
                if (holiday.getStartTime().toLocalTime().compareTo(holidayDTO.getStartTime().toLocalTime()) == 0){
                    if (holiday.getEndtime().toLocalTime().compareTo(holidayDTO.getEndTime().toLocalTime()) == 0){
                        theHoliday = holiday;
                    }
                }

            }
        }
        if(theHoliday == null){
            throw new IllegalArgumentException("There is no such holiday dto!");
        }
        return theHoliday;
    }


    /**
     * Gets the corresponding regular librarian from the DTO version
     * @param LibrarianDTO
     * @returns Librarian
     * @author Ramin Akhavan-Sarraf
     */
    private Librarian convertToDomainObject(LibrarianDTO librarianDTO){
        Librarian librarian;
        try {
            librarian = service.getLibrarianFromFullName(librarianDTO.getFirstName(), librarianDTO.getLastName());
        } catch (Exception e) {
            throw new IllegalArgumentException("Could not get librarian from service!");
        }
        if (librarian == null) {
            throw new IllegalArgumentException("There is no such librarian dto!");
        }
        return librarian;
    }

    /**
     * This method converts a openingHour DTO to a domain object opening hour.
     * @author Zoya Malhi
     * @param OpeningHourDTO
     * @return null
     */
	 private OpeningHour convertToDomainObject(OpeningHourDTO openingHourDTO){
		 List<OpeningHour> openingHours;
	     OpeningHour openingHour = null;
	     try {
	         openingHours = service.getAllOpeningHours();
	         for(OpeningHour o : openingHours) {
	        	 
	                 if (o.getStartTime().toLocalTime().compareTo(openingHourDTO.getStartTime().toLocalTime()) == 0){
	                     if (o.getEndTime().toLocalTime().compareTo(openingHourDTO.getEndTime().toLocalTime()) == 0){
	                         openingHour = o;
	                     }
	                 }
	     }
	     } catch (Exception e) {
	         throw new IllegalArgumentException("Could not get opening hours from service!");
	     }
	     
	     return null;
	 }
    
    /** 
     * This method converts a patron DTO to a patron object.
     * @author Zoya Malhi
     * @param patronDTO
     * @return null
     */
    private Patron convertToDomainObject(PatronDTO patronDTO){
    	Patron patron;
	     try {
	     patron = service.getPatronFromFullName(patronDTO.getFirstName(), patronDTO.getLastName());
	    	
	     } catch (Exception e) {
	         throw new IllegalArgumentException("Could not get patron from service!");
	     }
	     if (patron == null) {
	            throw new IllegalArgumentException("There is no such patron dto!");
	        }
	     return null;
    	
    }
    /**
     * This method converts a timslot DTO to a timeslot object.
     * @author Zoya Malhi
     * @param timeslotDTO
     * @return null
     */
    private TimeSlot convertToDomainObject(TimeslotDTO timeslotDTO){
    	List<TimeSlot> timeslots;
	     TimeSlot timeslot = null;
	     try {
	         timeslots = service.getAllTimeSlots();
	         for(TimeSlot t : timeslots) {
	        	 if (t.getStartTime().toLocalTime().compareTo(timeslotDTO.getStartTime().toLocalTime()) == 0){
                     if (t.getEndTime().toLocalTime().compareTo(timeslotDTO.getEndTime().toLocalTime()) == 0){
                         timeslot = t;
                     }
                 }
	         }
	        	 
	         }catch (Exception e) {
		         throw new IllegalArgumentException("Could not get timeslot from service!");
		 }
	     if (timeslot == null) {
	            throw new IllegalArgumentException("There is no such patron dto!");
	     }
	     
    	return null;
    }

    /**
     * This method converts a transaction DTO to a transaction object.
     * @author Zoya Malhi
     * @param transactionDTO
     * @return null
     */
    private Transaction convertToDomainObject(TransactionDTO transactionDTO){
    	 try {
	    	 Transaction transaction;
	        // transaction = service.getAllUsers(null);
	     
	     } catch (Exception e) {
	         throw new IllegalArgumentException("Could not get patrons from service!");
	     }
    	 if (transaction == null) {
	            throw new IllegalArgumentException("There is no such patron dto!");
	        }
	     return null;
    }
    
    /**
     * @author Zoya Malhi
     * @param userAccountDTO
     * @return null
     * @throws Exception 
     */
    private UserAccount convertToDomainObject(UserAccountDTO userAccountDTO) throws Exception{
    	LibrarySystem librarySystem;
    	List<UserAccount> userAccounts;
    	
    	try {
    	librarySystem = service.getLibrarySystemfrom1();
    	userAccounts = service.getAllUsers(librarySystem);
    
    	}catch (Exception e) {
	         throw new IllegalArgumentException("Could not get userAccount from service!");
	     }
    	if (userAccounts == null) {
            throw new IllegalArgumentException("There is no such userAccount dto!");
        }
	     return null;
    	
    }
}
