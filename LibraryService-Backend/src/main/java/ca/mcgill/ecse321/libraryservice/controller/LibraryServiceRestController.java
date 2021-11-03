package ca.mcgill.ecse321.libraryservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;

import ca.mcgill.ecse321.libraryservice.dto.LibrarianDTO;
import ca.mcgill.ecse321.libraryservice.dto.PatronDTO;
import ca.mcgill.ecse321.libraryservice.dto.UserAccountDTO;
import ca.mcgill.ecse321.libraryservice.model.*;
import ca.mcgill.ecse321.libraryservice.service.LibraryServiceService;

@CrossOrigin(origins = "*")
@RestController
public class LibraryServiceRestController {
    @Autowired
	private LibraryServiceService service;

    @PostMapping(value = { "/patrons/{firstName, lastName, onlineAccount, librarySystem, address, validatedAccount, passWord, balance}", "/patrons/{firstName, lastName, onlineAccount, address, validatedAccount, passWord, balance}/" })
    public PatronDTO createPatron(@PathVariable("firstName") String firstName, @PathVariable("lastName") String lastName,
    @PathVariable("onlineAccount") boolean onlineAccount, @PathVariable("librarySystem") LibrarySystem librarySystem, @PathVariable("address") String address, @PathVariable("validatedAccount") boolean validatedAccount,
    @PathVariable("passWord") String passWord, @PathVariable("balance") int balance) throws IllegalArgumentException {
        Patron patron = service.createPatron(firstName, lastName, onlineAccount, librarySystem, address, validatedAccount, passWord, balance);
        return convertToDto(patron);
    }

    @PostMapping(value = { "/librarians/{firstName, lastName, onlineAccount, librarySystem, address, passWord, balance}", "/librarians/{firstName, lastName, onlineAccount, address, passWord, balance}/" })
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

}
