package ca.mcgill.ecse321.libraryservice.service;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.libraryservice.dao.*;
import ca.mcgill.ecse321.libraryservice.model.*;
import ca.mcgill.ecse321.libraryservice.model.LibraryItem.ItemType;
import ca.mcgill.ecse321.libraryservice.model.Transaction.TransactionType;


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
    public List<BorrowableItem> getBorrowableItemsFromItemIsbn(int isbn){
        LibraryItem item = libraryItemRepository.findByIsbn(isbn);
        List<BorrowableItem> allBorrowableItems = borrowableItemRepository.findByLibraryItem(item);
        return allBorrowableItems;
    }

    @Transactional
    public List<LibraryItem> getAllBooks() throws Exception{
        LibrarySystem library;
        try{
            library = (LibrarySystem) librarySystemRepository.findAll().iterator().next(); // uses the first library system found in the database
        }catch(NoSuchElementException e){
            throw new Exception("No library system(s) exist in the database");
        }
        List<LibraryItem> allLibraryItems = libraryItemRepository.findByLibrarySystem(library);
        List<LibraryItem> allBooks = new ArrayList<LibraryItem>();
        for(LibraryItem i : allLibraryItems){
            if(i.getType().equals(ItemType.Book)){
                allBooks.add(i);
            }
        }
        return allBooks;
    }

    @Transactional
    public LibraryItem getBookFromAuthor(String authorName) throws Exception{
        List<LibraryItem> allBooks = getAllBooks();
        for(LibraryItem a : allBooks){
            if(a.getCreator().equals(authorName)) return a;
        }
        return null;
    }

    @Transactional
    public LibraryItem getBookFromTitle(String bookTitle) throws Exception{
        List<LibraryItem> allBooks = getAllBooks();
        for(LibraryItem a : allBooks){
            if(a.getName().equals(bookTitle)) return a;
        }
        return null;
    }

    @Transactional
    public List<LibraryItem> getAllMusic() throws Exception{
        LibrarySystem library;
        try{
            library = (LibrarySystem) librarySystemRepository.findAll().iterator().next(); // uses the first library system found in the database
        }catch(NoSuchElementException e){
            throw new Exception("No library system(s) exist in the database");
        }
        List<LibraryItem> allLibraryItems = libraryItemRepository.findByLibrarySystem(library);
        List<LibraryItem> allMusic = new ArrayList<LibraryItem>();
        for(LibraryItem i : allLibraryItems){
            if(i.getType().equals(ItemType.Music)){
                allMusic.add(i);
            }
        }
        return allMusic;
    }

    @Transactional
    public LibraryItem getMusicFromArtist(String artistName) throws Exception{
        List<LibraryItem> allMusic = getAllMusic();
        for(LibraryItem a : allMusic){
            if(a.getCreator().equals(artistName)) return a;
        }
        return null;
    }

    @Transactional
    public LibraryItem getMusicFromTitle(String musicTitle) throws Exception{
        List<LibraryItem> allMusic = getAllMusic();
        for(LibraryItem a : allMusic){
            if(a.getName().equals(musicTitle)) return a;
        }
        return null;
    }

    @Transactional
    public List<LibraryItem> getAllMovies() throws Exception{
        LibrarySystem library;
        try{
            library = (LibrarySystem) librarySystemRepository.findAll().iterator().next(); // uses the first library system found in the database
        }catch(NoSuchElementException e){
            throw new Exception("No library system(s) exist in the database");
        }
        List<LibraryItem> allLibraryItems = libraryItemRepository.findByLibrarySystem(library);
        List<LibraryItem> allMovies = new ArrayList<LibraryItem>();
        for(LibraryItem i : allLibraryItems){
            if(i.getType().equals(ItemType.Music)){
                allMovies.add(i);
            }
        }
        return allMovies;
    }

    @Transactional
    public LibraryItem getMovieFromDirector(String directorName) throws Exception{
        List<LibraryItem> allMovies = getAllMovies();
        for(LibraryItem a : allMovies){
            if(a.getCreator().equals(directorName)) return a;
        }
        return null;
    }

    @Transactional
    public LibraryItem getMovieFromTitle(String movieTitle) throws Exception{
        List<LibraryItem> allMovies = getAllMovies();
        for(LibraryItem a : allMovies){
            if(a.getName().equals(movieTitle)) return a;
        }
        return null;
    }

    @Transactional
    public Transaction createItemReserveTransaction(BorrowableItem item, UserAccount account){
        LocalDate localDeadline = LocalDate.now().plusDays(7); // 7 day deadline for reservation?
        Date deadline = Date.valueOf(localDeadline);
        Transaction itemReservation = new Transaction(item, account, TransactionType.ItemReservation, deadline); 
        transactionRepository.save(itemReservation);
        return itemReservation;
    }

    @Transactional
    public Transaction createRoomReserveTransaction(BorrowableItem item, UserAccount account){
        Transaction itemReservation = new Transaction(item, account, TransactionType.RoomReservation, null); // No deadline for room reservation
        transactionRepository.save(itemReservation);
        return itemReservation;
    }


    @Transactional
    public Transaction createItemBorrowTransaction(BorrowableItem item, UserAccount account){
        LocalDate localDeadline = LocalDate.now().plusDays(20); // Deadline is set 20 days away from current day
        Date deadline = Date.valueOf(localDeadline);
        Transaction itemReservation = new Transaction(item, account, TransactionType.Borrowing, deadline); 
        transactionRepository.save(itemReservation);
        return itemReservation;
    }

    @Transactional
    public Transaction createItemReturnTransaction(BorrowableItem item, UserAccount account){
        Transaction itemReservation = new Transaction(item, account, TransactionType.Return, null); // No deadline for return
        transactionRepository.save(itemReservation);
        return itemReservation;
    }

    @Transactional
    public Transaction createItemWaitlistTransaction(BorrowableItem item, UserAccount account){
        Transaction itemReservation = new Transaction(item, account, TransactionType.Waitlist, null); // No deadline for waitlist
        transactionRepository.save(itemReservation);
        return itemReservation;
    }

    @Transactional
    public Transaction createItemRenewalTransaction(BorrowableItem item, UserAccount account){
        LocalDate localDeadline = LocalDate.now().plusDays(20); // Deadline is set 20 days away from current day
        Date deadline = Date.valueOf(localDeadline);
        Transaction itemReservation = new Transaction(item, account, TransactionType.Renewal, deadline); 
        transactionRepository.save(itemReservation);
        return itemReservation;
    }

    @Transactional
    public List<BorrowableItem> getBorrowedItemsFromUser(UserAccount account){
        List<Transaction> allUserTransactions = transactionRepository.findByUserAccount(account);
        List<BorrowableItem> allBorrowedItems = new ArrayList<BorrowableItem>();
        for(Transaction t : allUserTransactions){
            if(t.getTransactionType().equals(TransactionType.Borrowing) || t.getTransactionType().equals(TransactionType.Renewal)){
               allBorrowedItems.add(t.getBorrowableItem()); 
            } 
        }
        return allBorrowedItems;
    }

    @Transactional
    public List<BorrowableItem> getReservedItemsFromUser(UserAccount account){
        List<Transaction> allUserTransactions = transactionRepository.findByUserAccount(account);
        List<BorrowableItem> allReservedItems = new ArrayList<BorrowableItem>();
        for(Transaction t : allUserTransactions){
            if(t.getTransactionType().equals(TransactionType.ItemReservation)){
                allReservedItems.add(t.getBorrowableItem()); 
            } 
        }
        return allReservedItems;
    }

    @Transactional
    public List<UserAccount> getUsersOnWaitlist(BorrowableItem item){
        List<Transaction> allItemTransactions = transactionRepository.findByBorrowableItem(item);
        List<UserAccount> users = new ArrayList<UserAccount>();
        for(Transaction t : allItemTransactions) users.add(t.getUserAccount());
        return users;
    }
	

    /***
     * This method creates and save the object in the database and returns the Librarian object
     * @param aFirstName
     * @param aLastName
     * @param aOnlineAccount
     * @param aLibrarySystem
     * @param aAddress
     * @param aPassword
     * @param aBalance
     * @return librarian
     */
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


    /***
     * Thismethod retrieves the user from the database
     * @param userID
     * @return person
     */
    @Transactional
	public UserAccount getUser(int userID) {
		UserAccount person = userAccountRepository.findUserAccountByUserID(userID);
		return person;
	}

    /***
     * This methods returns the librarian objetct from teh database
     * @param librarianID
     * @return librarian
     */
    @Transactional
	public UserAccount getLibrarian(int librarianID) {
		UserAccount librarian = userAccountRepository.findUserAccountByUserID(librarianID);
		return librarian;
	}

    /**
     * This method creates the Patron object and stores it in the database
     * @param aFirstName
     * @param aLastName
     * @param aOnlineAccount
     * @param aLibrarySystem
     * @param aAddress
     * @param aValidatedAccount
     * @param aPassword
     * @param aBalance
     * @return patron
     */
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

    /***
     * This method gets the batron object from the database
     * @param userID
     * @return person 
     */
    @Transactional
	public Patron getPatron(int userID) {
		Patron person = patronRepository.findPatronByUserID(userID);
		return person;
	}

}