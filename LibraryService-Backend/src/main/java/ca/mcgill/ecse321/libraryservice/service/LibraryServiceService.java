package ca.mcgill.ecse321.libraryservice.service;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.libraryservice.dao.*;
import ca.mcgill.ecse321.libraryservice.model.*;


@Service
public class LibraryServiceService {

    //all DAOs
    @Autowired
    private BorrowableItemRepository borrowableItemRepository;
    @Autowired
    private HeadLibrarianRepository headLibrarianRepository;
    @Autowired
    private HolidayRepository holidayRepository;
    @Autowired
    private LibrarianRepository librarianRepository;
    @Autowired
    private LibraryItemRepository libraryItemRepository;
    @Autowired
    private LibrarySystemRepository librarySystemRepository;
    @Autowired
    private OpeningHourRepository openingHourRepository;
    @Autowired
    private PatronRepository patronRepository;
    @Autowired
    private TimeSlotRepository timeSlotRepository;
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private UserAccountRepository userAccountRepository;
	
    @Transactional
	public Librarian createLibrarian(String aFirstName, String aLastName, boolean aOnlineAccount, LibrarySystem aLibrarySystem, String aAddress, String aPassword, int aBalance) {
		Librarian librarian = new Librarian();
		librarian.setFirstName(aFirstName);
        librarian.setLastName(aLastName);
        librarian.setOnlineAccount(aOnlineAccount);
        librarian.setLibrarySystem(aLibrarySystem);
        librarian.setAddress(aAddress);
        librarian.setPassword(aPassword);
        librarian.setBalance(aBalance);
		librarianRepository.save(librarian);
		return librarian;
	}

    @Transactional
	public UserAccount getUser(int userID) {
		UserAccount person = userAccountRepository.findUserAccountByUserID(userID);
		return person;
	}

    @Transactional
	public Patron createPatron(String aFirstName, String aLastName, boolean aOnlineAccount, LibrarySystem aLibrarySystem, String aAddress, boolean aValidatedAccount, String aPassword, int aBalance) {
		Patron patron = new Patron();
		patron.setFirstName(aFirstName);
        patron.setLastName(aLastName);
        patron.setOnlineAccount(aOnlineAccount);
        patron.setLibrarySystem(aLibrarySystem);
        patron.setAddress(aAddress);
        patron.setPassword(aPassword);
        patron.setBalance(aBalance);
        patron.setValidatedAccount(aValidatedAccount);
		patronRepository.save(patron);
		return patron;
	}

    @Transactional
	public Patron getPatron(int userID) {
		Patron person = patronRepository.findPatronByUserID(userID);
		return person;
	}

    // @Transactional
	// public List<Patron> getAllPatron() {
	// 	return toList(patronRepository.findAll());
	// }

    // @Transactional
	// public List<UserAccount> getAllUserAccounts() {
	// 	return toList(userAccountRepository.findAll());
	// }

}