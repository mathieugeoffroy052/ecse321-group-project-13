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
import ca.mcgill.ecse321.libraryservice.model.*;
import ca.mcgill.ecse321.libraryservice.service.LibraryServiceService;

@CrossOrigin(origins = "*")
@RestController
public class LibraryServiceRestController {
    @Autowired
	private LibraryServiceService service;

    /***
     * @author Gabrielle Halpin
     * @param firstName
     * @param lastName
     * @param onlineAccount
     * @param librarySystem
     * @param address
     * @param validatedAccount
     * @param passWord
     * @param balance
     * @return
     * @throws IllegalArgumentException
     */
    @PostMapping(value = { "/createPatron/{firstName}/{lastName}/{onlineAccount}/{librarySystem}/{address}/{validatedAccount}/{passWord}/{balance}", "/createPatron/{firstName}/{lastName}/{onlineAccount}/{librarySystem}/{address}/{validatedAccount}/{passWord}/{balance}" })
    public PatronDTO createPatron(@PathVariable("firstName") String firstName, @PathVariable("lastName") String lastName,
    @PathVariable("onlineAccount") boolean onlineAccount, @PathVariable("librarySystem") LibrarySystem librarySystem, @PathVariable("address") String address, @PathVariable("validatedAccount") boolean validatedAccount,
    @PathVariable("passWord") String passWord, @PathVariable("balance") int balance) throws IllegalArgumentException {
        Patron patron = service.createPatron(firstName, lastName, onlineAccount, librarySystem, address, validatedAccount, passWord, balance);
        return convertToDto(patron);
    }


    /**
     * @author Gabrielle Halpin
     * @param firstName
     * @param lastName
     * @param onlineAccount
     * @param librarySystem
     * @param address
     * @param passWord
     * @param balance
     * @return
     * @throws IllegalArgumentException
     */
    @PostMapping(value = { "/createLibrarian/{firstName}/{lastName}/{onlineAccount}/{librarySystem}/{address}/{passWord}/{balance}", "/createLibrarian/{firstName}/{lastName}/{onlineAccount}/{librarySystem}/{address}/{passWord}/{balance}" })
    public LibrarianDTO createLibrarian(@PathVariable("firstName") String firstName, @PathVariable("lastName") String lastName,
    @PathVariable("onlineAccount") boolean onlineAccount, @PathVariable("librarySystem") LibrarySystem librarySystem, @PathVariable("address") String address,
    @PathVariable("passWord") String passWord, @PathVariable("balance") int balance) throws IllegalArgumentException {
        Librarian librarian = service.createLibrarian(firstName, lastName, onlineAccount, librarySystem, address, passWord, balance);
        return convertToDto(librarian);
    }


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
        PatronDTO patronDTO = new PatronDTO(patron.getFirstName(),patron.getLastName(),patron.getOnlineAccount(),patron.getAddress(), patron.getValidatedAccount(), patron.getPassword(), patron.getBalance());
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
        LibrarianDTO librarianDTO = new LibrarianDTO(librarian.getFirstName(),librarian.getLastName(),librarian.getOnlineAccount(),librarian.getAddress(), librarian.getPassword(), librarian.getBalance());
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
        HeadLibrarianDTO headLibrarianDTO = new HeadLibrarianDTO(headLibrarian.getFirstName(),headLibrarian.getLastName(),headLibrarian.getOnlineAccount(),headLibrarian.getAddress(), headLibrarian.getPassword(), headLibrarian.getBalance());
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
        HeadLibrarianDTO headLibrarianDTO = new HeadLibrarianDTO(headLibrarian.getFirstName(),headLibrarian.getLastName(),headLibrarian.getOnlineAccount(),headLibrarian.getAddress(), headLibrarian.getPassword(), headLibrarian.getBalance());
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
        HeadLibrarianDTO headLibrarianDTO = new HeadLibrarianDTO(headLibrarian.getFirstName(),headLibrarian.getLastName(),headLibrarian.getOnlineAccount(),headLibrarian.getAddress(), headLibrarian.getPassword(), headLibrarian.getBalance());
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
        UserAccountDTO userAccountDTO = new UserAccountDTO(userAccount.getFirstName(), userAccount.getLastName(), userAccount.getOnlineAccount(), userAccount.getAddress(), userAccount.getPassword(), userAccount.getBalance());
        return userAccountDTO;
    }

    ///////// Helper methods - convertToDomainObject//////////
    //each method need to check to make sure the individual is in the system before creating them.

    private BorrowableItem convertToDomainObject(BorrowableItemDTO borrowableItemDTO) {
    }

    private LibraryItem convertToDomainObject(LibraryItemDTO libraryItemDTO) {
    }

    private HeadLibrarian convertToDomainObject(HeadLibrarianDTO headLibrarianDTO) {
    }

    private Holiday convertToDomainObject(HolidayDTO holidayDTO) {
    }

    private Librarian convertToDomainObject(LibrarianDTO librarianDTO){

    }

    private OpeningHour convertToDomainObject(OpeningHourDTO openingHourDTO){
    }

    private Patron convertToDomainObject(PatronDTO patronDTO){
    }

    private TimeSlot convertToDomainObject(TimeslotDTO timeslotDTO){
    }

    private Transaction convertToDomainObject(TransactionDTO transactionDTO){
    }

    private UserAccount convertToDomainObject(UserAccountDTO userAccountDTO){
    }
}
