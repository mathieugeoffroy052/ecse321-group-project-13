package ca.mcgill.ecse321.libraryservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
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

    @PostMapping(value = { "/createPatron/{firstName}/{lastName}/{onlineAccount}/{librarySystem}/{address}/{validatedAccount}/{passWord}/{balance}", "/createPatron/{firstName}/{lastName}/{onlineAccount}/{librarySystem}/{address}/{validatedAccount}/{passWord}/{balance}" })
    public PatronDTO createPatron(@PathVariable("firstName") String firstName, @PathVariable("lastName") String lastName,
    @PathVariable("onlineAccount") boolean onlineAccount, @PathVariable("librarySystem") LibrarySystem librarySystem, @PathVariable("address") String address, @PathVariable("validatedAccount") boolean validatedAccount,
    @PathVariable("passWord") String passWord, @PathVariable("balance") int balance) throws IllegalArgumentException {
        Patron patron = service.createPatron(firstName, lastName, onlineAccount, librarySystem, address, validatedAccount, passWord, balance);
        return convertToDto(patron);
    }

    @PostMapping(value = { "/createLibrarian/{firstName}/{lastName}/{onlineAccount}/{librarySystem}/{address}/{passWord}/{balance}", "/createLibrarian/{firstName}/{lastName}/{onlineAccount}/{librarySystem}/{address}/{passWord}/{balance}" })
    public LibrarianDTO createLibrarian(@PathVariable("firstName") String firstName, @PathVariable("lastName") String lastName,
    @PathVariable("onlineAccount") boolean onlineAccount, @PathVariable("librarySystem") LibrarySystem librarySystem, @PathVariable("address") String address,
    @PathVariable("passWord") String passWord, @PathVariable("balance") int balance) throws IllegalArgumentException {
        Librarian librarian = service.createLibrarian(firstName, lastName, onlineAccount, librarySystem, address, passWord, balance);
        return convertToDto(librarian);
    }

    private PatronDTO convertToDto(Patron patron) {
        if (patron == null) {
            throw new IllegalArgumentException("There is no such patron!");
        }
        PatronDTO patronDTO = new PatronDTO(patron.getFirstName(),patron.getLastName(),patron.getOnlineAccount(),patron.getAddress(), patron.getValidatedAccount(), patron.getPassword(), patron.getBalance());
        return patronDTO;
    }

    private LibrarianDTO convertToDto(Librarian librarian) {
        if (librarian == null) {
            throw new IllegalArgumentException("There is no such patron!");
        }
        LibrarianDTO librarianDTO = new LibrarianDTO(librarian.getFirstName(),librarian.getLastName(),librarian.getOnlineAccount(),librarian.getAddress(), librarian.getPassword(), librarian.getBalance());
        return librarianDTO;
    }

    private HeadLibrarianDTO convertToDto(HeadLibrarian headLibrarian) {
        if (headLibrarian== null) {
            throw new IllegalArgumentException("There is no such head librarian!");
        }
        HeadLibrarianDTO headLibrarianDTO = new HeadLibrarianDTO(headLibrarian.getFirstName(),headLibrarian.getLastName(),headLibrarian.getOnlineAccount(),headLibrarian.getAddress(), headLibrarian.getPassword(), headLibrarian.getBalance());
        return headLibrarianDTO;
    }

    private HolidayDTO convertToDto(Holiday holiday, HeadLibrarian headLibrarian) {
        if (holiday == null) {
            throw new IllegalArgumentException("There is no such holiday!");
        }
        HeadLibrarianDTO headLibrarianDTO = new HeadLibrarianDTO(headLibrarian.getFirstName(),headLibrarian.getLastName(),headLibrarian.getOnlineAccount(),headLibrarian.getAddress(), headLibrarian.getPassword(), headLibrarian.getBalance());
        HolidayDTO holidayDTO = new HolidayDTO(holiday.getDate(), holiday.getStartTime(), holiday.getEndtime(), headLibrarianDTO);
        return holidayDTO;
    }

    private OpeningHourDTO convertToDto(OpeningHour openingHour, HeadLibrarian headLibrarian) {
        if (openingHour == null) {
            throw new IllegalArgumentException("There is no such opening hour!");
        }
        HeadLibrarianDTO headLibrarianDTO = new HeadLibrarianDTO(headLibrarian.getFirstName(),headLibrarian.getLastName(),headLibrarian.getOnlineAccount(),headLibrarian.getAddress(), headLibrarian.getPassword(), headLibrarian.getBalance());
        OpeningHourDTO.DayOfWeek dayOfWeek = OpeningHourDTO.DayOfWeek.valueOf(openingHour.getDayOfWeek().toString());
        OpeningHourDTO openingHourDTO = new OpeningHourDTO(dayOfWeek, openingHour.getStartTime(), openingHour.getEndTime(), headLibrarianDTO);
        return openingHourDTO;
    }

    private LibraryItemDTO convertToDto(LibraryItem libraryItem) {
        if (libraryItem== null) {
            throw new IllegalArgumentException("There is no such library item!");
        }
        LibraryItemDTO.ItemType itemType = LibraryItemDTO.ItemType.valueOf(libraryItem.getType().toString());
        LibraryItemDTO libraryItemDTO = new LibraryItemDTO(libraryItem.getName(), itemType, libraryItem.getDate(), libraryItem.getCreator(), libraryItem.getIsViewable());
        return libraryItemDTO;
    }

}
