package ca.mcgill.ecse321.libraryservice.service;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.NoSuchElementException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.FlashMapManager;

import ca.mcgill.ecse321.libraryservice.dao.*;
import ca.mcgill.ecse321.libraryservice.dto.PatronDTO;
import ca.mcgill.ecse321.libraryservice.dto.TimeslotDTO;
import ca.mcgill.ecse321.libraryservice.dto.UserAccountDTO;
import ca.mcgill.ecse321.libraryservice.model.*;
import ca.mcgill.ecse321.libraryservice.model.BorrowableItem.ItemState;
import ca.mcgill.ecse321.libraryservice.model.LibraryItem.ItemType;
import ca.mcgill.ecse321.libraryservice.model.OpeningHour.DayOfWeek;
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
    private OpeningHourRepository openingHourRepository;
    @Autowired
    private PatronRepository patronRepository;
    @Autowired
    private TimeSlotRepository timeSlotRepository;
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private UserAccountRepository userAccountRepository;

    /** 
     * @param isbn
     * @return List<BorrowableItem> - list of borrowable items with given isbn
     * @author Amani Jammoul
     * checked
     */
    @Transactional
    public List<BorrowableItem> getBorrowableItemsFromItemIsbn(int isbn){
        LibraryItem item = libraryItemRepository.findByIsbn(isbn);
        List<BorrowableItem> allBorrowableItems = borrowableItemRepository.findByLibraryItem(item);
        return allBorrowableItems;
    }

    /** 
     * @param barCodeNumber
     * @return BorrowableItem - borrowable item of given bar code number
     * @author Amani Jammoul
     */
    @Transactional
    public BorrowableItem getBorrowableItemFromBarCodeNumber(int barCodeNumber){
        BorrowableItem item = borrowableItemRepository.findBorrowableItemByBarCodeNumber(barCodeNumber);
        return item;
    }

    /** 
     * @param userID
     * @return UserAccount - account of given ID
     * @author Amani Jammoul
     */
    @Transactional
    public UserAccount getUserAccountFromUserID(int userID){
        UserAccount account = userAccountRepository.findUserAccountByUserID(userID);
        return account;
    }

    /** 
     * @param userID
     * @return UserAccount - account for user with given full name
     * @author Amani Jammoul
     * @throws Exception
     */
    @Transactional
    public UserAccount getUserAccountFromFullName(String firstName, String lastName) throws Exception{
        String error = "";
        if (firstName == null || firstName.trim().length() == 0) {
            error += "First name cannot be empty! ";
        } else if(lastName == null || lastName.trim().length() == 0){
            error += "Last name cannot be empty! ";
        }

        error = error.trim();
        if (error.length() > 0) {
            throw new IllegalArgumentException(error);
        }

        List<UserAccount> allAccounts = (List<UserAccount>) userAccountRepository.findAll();
        UserAccount account = null;
        for(UserAccount a : allAccounts){
            if(a.getFirstName().equals(firstName) && a.getLastName().equals(lastName)){
                account = a;
            }
        }
        if(account != null) return account;
        else throw new IllegalArgumentException("No user found with this name! ");
    }

    /** 
     * @return List<LibraryItem> - all library items in library system
     * @throws Exception
     * @author Amani Jammoul
     */
    @Transactional
    public List<LibraryItem> getAllLibraryItems() throws Exception{
        List<LibraryItem> allLibraryItems = (List<LibraryItem>) libraryItemRepository.findAll();
        return allLibraryItems;
    }

    /** 
     * @param creeatorName
     * @return List<LibraryItem> - library items by creator
     * @throws Exception
     * @author Amani Jammoul
     */
    @Transactional
    public List<LibraryItem> getLibraryItemsFromCreator(String creatorName) throws Exception{
        if (creatorName == null || creatorName.trim().length() == 0) {
            throw new IllegalArgumentException("Creator name cannot be empty!");
        }

        List<LibraryItem> allItems = getAllLibraryItems();
        List<LibraryItem> itemsByCreator = new ArrayList<LibraryItem>();
        for(LibraryItem a : allItems){
            if(a.getCreator().equals(creatorName)) itemsByCreator.add(a);
        }
        return itemsByCreator;
    }

    /** 
     * @param itemTitle
     * @return List<LibraryItem> - library items with the given title
     * @throws Exception
     * @author Amani Jammoul
     */
    @Transactional
    public List<LibraryItem> getLibraryItemsFromTitle(String itemTitle) throws Exception{
        if (itemTitle == null || itemTitle.trim().length() == 0) {
            throw new IllegalArgumentException("Item title cannot be empty!");
        }

        List<LibraryItem> allItems = getAllLibraryItems();
        List<LibraryItem> itemsByTitle = new ArrayList<LibraryItem>();
        for(LibraryItem a : allItems){
            if(a.getName().equals(itemTitle)) itemsByTitle.add(a);
        }
        return itemsByTitle;
    }

    /** 
     * @param creatorName
     * @param itemTitle
     * @return LibraryItem - library items with the given title by given creator
     * @throws Exception
     * @author Amani Jammoul
     */
    @Transactional
    public List<LibraryItem> getLibraryItemFromCreatorAndTitle(String creatorName, String itemTitle) throws Exception{
        if (creatorName == null || creatorName.trim().length() == 0) {
            throw new IllegalArgumentException("Creator name cannot be empty! ");
        } else if (itemTitle == null || itemTitle.trim().length() == 0) {
            throw new IllegalArgumentException("Item title cannot be empty! ");
        }

        List<LibraryItem> allItems = getAllLibraryItems();
        List<LibraryItem> itemsByCreatorAndTitle = new ArrayList<LibraryItem>();
        for(LibraryItem a : allItems){
            if(a.getCreator().equals(creatorName) && a.getName().equals(itemTitle)) itemsByCreatorAndTitle.add(a);
        }
        return itemsByCreatorAndTitle;
    }

    
    /** 
     * @return List<LibraryItem> - all books in library system
     * @throws Exception
     * @author Amani Jammoul
     * checked
     */
    @Transactional
    public List<LibraryItem> getAllBooks() throws Exception{
        Iterable<LibraryItem> allLibraryItems = libraryItemRepository.findAll();
        List<LibraryItem> allBooks = new ArrayList<LibraryItem>();
        for(LibraryItem i : allLibraryItems){
            if(i.getType().equals(ItemType.Book)){
                allBooks.add(i);
            }
        }
        return allBooks;
    }

    
    /** 
     * @param authorName
     * @return List<LibraryItem> - books written by an author
     * @throws Exception
     * @author Amani Jammoul
     * checked
     */
    @Transactional
    public List<LibraryItem> getBooksFromAuthor(String authorName) throws Exception{
        if (authorName == null || authorName.trim().length() == 0) {
            throw new IllegalArgumentException("Author name cannot be empty!");
        }

        List<LibraryItem> allBooks = getAllBooks();
        List<LibraryItem> booksByAuthor = new ArrayList<LibraryItem>();
        for(LibraryItem a : allBooks){
            if(a.getCreator().equals(authorName)) booksByAuthor.add(a);
        }
        return booksByAuthor;
    }

    
    /** 
     * @param bookTitle
     * @return List<LibraryItem> - books with the given title
     * @throws Exception
     * @author Amani Jammoul
     * checked
     */
    @Transactional
    public List<LibraryItem> getBooksFromTitle(String bookTitle) throws Exception{
        if (bookTitle == null || bookTitle.trim().length() == 0) {
            throw new IllegalArgumentException("Book title cannot be empty!");
        }

        List<LibraryItem> allBooks = getAllBooks();
        List<LibraryItem> booksByTitle = new ArrayList<LibraryItem>();
        for(LibraryItem a : allBooks){
            if(a.getName().equals(bookTitle)) booksByTitle.add(a);
        }
        return booksByTitle;
    }

    /** 
     * @param authorName
     * @param bookTitle
     * @return LibraryItem - book with the given title written by given author
     * @throws Exception
     * @author Amani Jammoul
     */
    @Transactional
    public LibraryItem getBookFromAuthorAndTitle(String authorName, String bookTitle) throws Exception{
        if (authorName == null || authorName.trim().length() == 0) {
            throw new IllegalArgumentException("Author name cannot be empty! ");
        } else if (bookTitle == null || bookTitle.trim().length() == 0) {
            throw new IllegalArgumentException("Book title cannot be empty! ");
        }

        List<LibraryItem> allBooks = getAllBooks();
        for(LibraryItem a : allBooks){
            if(a.getCreator().equals(authorName) && a.getName().equals(bookTitle)) return a;
        }
        return null;
    }

    
    /** 
     * @return List<LibraryItem> - list of all music items
     * @throws Exception
     * @author Amani Jammoul
     * checked
     */
    @Transactional
    public List<LibraryItem> getAllMusic() throws Exception{
        Iterable<LibraryItem> allLibraryItems = libraryItemRepository.findAll();
        List<LibraryItem> allMusic = new ArrayList<LibraryItem>();
        for(LibraryItem i : allLibraryItems){
            if(i.getType().equals(ItemType.Music)){
                allMusic.add(i);
            }
        }
        return allMusic;
    }

    
    /** 
     * @param artistName
     * @return List<LibraryItem> - all music by artist
     * @throws Exception
     * @author Amani Jammoul
     * checked
     */
    @Transactional
    public List<LibraryItem> getMusicsFromArtist(String artistName) throws Exception{
        if (artistName == null || artistName.trim().length() == 0) {
            throw new IllegalArgumentException("Artist name cannot be empty!");
        }

        List<LibraryItem> allMusics = getAllMusic();
        List<LibraryItem> musicsByArtist = new ArrayList<LibraryItem>();
        for(LibraryItem a : allMusics){
            if(a.getCreator().equals(artistName)) musicsByArtist.add(a);
        }
        return musicsByArtist;
    }

    
    /** 
     * @param musicTitle
     * @return List<LibraryItem> - all music with the given title
     * @throws Exception
     * @author Amani Jammoul
     * checked
     */
    @Transactional
    public List<LibraryItem> getMusicsFromTitle(String musicTitle) throws Exception{
        if (musicTitle == null || musicTitle.trim().length() == 0) {
            throw new IllegalArgumentException("Music title cannot be empty!");
        }

        List<LibraryItem> allMusics = getAllMusic();
        List<LibraryItem> musicsByTitle = new ArrayList<LibraryItem>();
        for(LibraryItem a : allMusics){
            if(a.getName().equals(musicTitle)) musicsByTitle.add(a);
        }
        return musicsByTitle;
    }

    /** 
     * @param artistName
     * @param musicTitle
     * @return LibraryItem - music with the given title by given artist
     * @throws Exception
     * @author Amani Jammoul
     */
    @Transactional
    public LibraryItem getMusicFromArtistAndTitle(String artistName, String musicTitle) throws Exception{
        if (artistName == null || artistName.trim().length() == 0) {
            throw new IllegalArgumentException("Artist name cannot be empty! ");
        } else if (musicTitle == null || musicTitle.trim().length() == 0) {
            throw new IllegalArgumentException("Music title cannot be empty! ");
        }

        List<LibraryItem> allMusics = getAllMusic();
        for(LibraryItem a : allMusics){
            if(a.getCreator().equals(artistName) && a.getName().equals(musicTitle)) return a;
        }
        return null;
    }

    
    /** 
     * @return List<LibraryItem> - list of all movie items
     * @throws Exception
     * @author Amani Jammoul
     * checked
     */
    @Transactional
    public List<LibraryItem> getAllMovies() throws Exception{
        Iterable<LibraryItem> allLibraryItems = libraryItemRepository.findAll();
        List<LibraryItem> allMovies = new ArrayList<LibraryItem>();
        for(LibraryItem i : allLibraryItems){
            if(i.getType().equals(ItemType.Music)){
                allMovies.add(i);
            }
        }
        return allMovies;
    }

    
    /** 
     * @param directorName
     * @return List<LibraryItem> - all movies by director
     * @throws Exception
     * @author Amani Jammoul
     * checked
     */
    @Transactional
    public List<LibraryItem> getMoviesFromDirector(String directorName) throws Exception{
        if (directorName == null || directorName.trim().length() == 0) {
            throw new IllegalArgumentException("Director name cannot be empty!");
        }

        List<LibraryItem> allMovies = getAllMovies();
        List<LibraryItem> moviesByDirector = new ArrayList<LibraryItem>();
        for(LibraryItem a : allMovies){
            if(a.getCreator().equals(directorName)) moviesByDirector.add(a);
        }
        return moviesByDirector;
    }

    
    /** 
     * @param movieTitle
     * @return List<LibraryItem> - all movies with the given title
     * @throws Exception
     * @author Amani Jammoul
     * checked
     */
    @Transactional
    public List<LibraryItem> getMoviesFromTitle(String movieTitle) throws Exception{
        if (movieTitle == null || movieTitle.trim().length() == 0) {
            throw new IllegalArgumentException("Movie title cannot be empty!");
        }

        List<LibraryItem> allMovies = getAllMovies();
        List<LibraryItem> moviesByTitle = new ArrayList<LibraryItem>();
        for(LibraryItem a : allMovies){
            if(a.getName().equals(movieTitle)) moviesByTitle.add(a);
        }
        return moviesByTitle;
    }

    /** 
     * @param directorName
     * @param movieTitle
     * @return LibraryItem - music with the given title by given artist
     * @throws Exception
     * @author Amani Jammoul
     */
    @Transactional
    public LibraryItem getMovieFromDirectorAndTitle(String directorName, String movieTitle) throws Exception{
        if (directorName == null || directorName.trim().length() == 0) {
            throw new IllegalArgumentException("Director name cannot be empty! ");
        } else if (movieTitle == null || movieTitle.trim().length() == 0) {
            throw new IllegalArgumentException("Music title cannot be empty! ");
        }

        List<LibraryItem> allMovies = getAllMovies();
        for(LibraryItem a : allMovies){
            if(a.getCreator().equals(directorName) && a.getName().equals(movieTitle)) return a;
        }
        return null;
    }

    /**
     * Get all room reservations that are in the system
     * @throws if there is no library system found
     * @returns a list of room reservations
     * @author Ramin Akhavan-Sarraf 
     * checked
     */

    @Transactional
    public List<LibraryItem> getAllRoomReservations() throws Exception{        
        Iterable<LibraryItem> allLibraryItems = libraryItemRepository.findAll();
        List<LibraryItem> allRooms = new ArrayList<>();
        for(LibraryItem libItem : allLibraryItems){
            if (libItem.getType() == ItemType.Room){
                allRooms.add(libItem);
            }
        }
        return allRooms;
    }

    /**
     * Get all newspapers that are in the system
     * @throws if there is no library system found
     * @returns a list of newspapers
     * @author Ramin Akhavan-Sarraf
     * checked
     */
    @Transactional
    public List<LibraryItem> getAllNewspapers() throws Exception{
        Iterable<LibraryItem> allLibraryItems = libraryItemRepository.findAll();
        List<LibraryItem> allNewspapers = new ArrayList<>();
        for(LibraryItem libItem : allLibraryItems){
            if (libItem.getType() == ItemType.NewspaperArticle){
                allNewspapers.add(libItem);
            }
        }
        return allNewspapers;
    }

    /**
     * Get newspapers based on the title of the article
     * @param newspaper article title
     * @returns a newspaper
     * @author Ramin Akhavan-Sarraf
     * I added a throw I think u forgot :) -elo
     * checked
     */
    @Transactional
    public List<LibraryItem> getNewspaperFromTitle(String newspaperTitle) throws Exception{
        if (newspaperTitle == null || newspaperTitle.trim().length() == 0) {
            throw new IllegalArgumentException("Newspaper title cannot be empty!");
        }
        List<LibraryItem> allNewspapers = getAllNewspapers();
        List<LibraryItem> newspapersByTitle = new ArrayList<LibraryItem>();
        for(LibraryItem newspaper : allNewspapers){
            if(newspaper.getName().equals(newspaperTitle)) newspapersByTitle.add(newspaper);
        }
        return newspapersByTitle;
    }

    /**
     * Get newspapers based on the writer of the article
     * @param newspaper article writer
     * @returns a newspaper
     * @author Ramin Akhavan-Sarraf
     *     * I added a throw I think u forgot :) -elo
     * checked
     */
    @Transactional
    public List<LibraryItem> getNewspaperFromWriter(String writerName) throws Exception{
        if (writerName == null || writerName.trim().length() == 0) {
            throw new IllegalArgumentException("Writer Name  cannot be empty!");
        } 
        List<LibraryItem> allNewspapers = getAllNewspapers();
        List<LibraryItem> newspapersByWriter = new ArrayList<LibraryItem>();
        for(LibraryItem newspaper : allNewspapers){
            if(newspaper.getCreator().equals(writerName)) newspapersByWriter.add(newspaper);
        }
        return newspapersByWriter;
    }

    /** 
     * @param writerName
     * @param newspaperTitle
     * @return LibraryItem - music with the given title by given artist
     * @throws Exception
     * @author Amani Jammoul
     */
    @Transactional
    public LibraryItem getNewspaperFromWriterAndTitle(String writerName, String newspaperTitle) throws Exception{
        if (writerName == null || writerName.trim().length() == 0) {
            throw new IllegalArgumentException("Writer name cannot be empty! ");
        } else if (newspaperTitle == null || newspaperTitle.trim().length() == 0) {
            throw new IllegalArgumentException("Newspaper title cannot be empty! ");
        }

        List<LibraryItem> allNewspapers = getAllNewspapers();
        for(LibraryItem a : allNewspapers){
            if(a.getCreator().equals(writerName) && a.getName().equals(newspaperTitle)) return a;
        }
        return null;
    }

    
    /** 
     * Creates an item reservation transaction between a user account and a borrowable item
     * @param item
     * @param account
     * @return Transaction - Type : ItemReservation
     * @author Amani Jammoul
     * checked
     */
    @Transactional
    public Transaction createItemReserveTransaction(BorrowableItem item, UserAccount account){
        // Input validation
        String error = "";
        if (item == null) {
            error = error + "Item cannot be null! ";
        } else if (borrowableItemRepository.findBorrowableItemByBarCodeNumber(item.getBarCodeNumber()) == null){
            error += "Borrowable item does not exist!";
        }

        if (account == null) {
            error = error + "Account cannot be null! ";
        } else if (userAccountRepository.findUserAccountByUserID(account.getUserID()) == null){
            error += "User does not exist!";
        }
        error = error.trim();
        if (error.length() > 0) {
            throw new IllegalArgumentException(error);
        }

        error = "";
        // UserAccount validation
        boolean validAccount = false;
        if(account instanceof Librarian) validAccount = true;
        else{
            validAccount = ((Patron) account).getValidatedAccount(); 
        }

        if(!validAccount){
            error = "User account is unvalidated, cannot complete item reservation transaction! ";
        }

        // Item validation
        if(item.getLibraryItem().getType() == ItemType.NewspaperArticle){
            error += "Newspapers cannot be reserved, only viewed ; item reservation transaction not complete! ";
        } else if(item.getLibraryItem().getType() == ItemType.Room){
            error += "Rooms cannot be reserved as such, please try a room reservation transaction! ";
        }
        else if(item.getState() != ItemState.Available){
            error += "Item is not available for reservation, please try waitlist! ";
        }

        error = error.trim();
        if (error.length() > 0) {
            throw new IllegalArgumentException(error);
        }
        
        LocalDate localDeadline = LocalDate.now().plusDays(7); // 7 day deadline for reservation?
        Date deadline = Date.valueOf(localDeadline);
        Transaction itemReservation = new Transaction(item, account, TransactionType.ItemReservation, deadline); 
        transactionRepository.save(itemReservation);
        return itemReservation;
    }

    
    /** 
     * Creates a room reservation transaction between a user account and a room (borrowable item)
     * @param item
     * @param account
     * @param date - date of reservation
     * @param startTime - start time of reservation
     * @param endTime - end time of reservation
     * @return Transaction - Type : RoomReservation
     * @author Amani Jammoul
     * checked
     */
    @Transactional
    public Transaction createRoomReserveTransaction(BorrowableItem item, UserAccount account, Date date, Time startTime, Time endTime){
        // Input validation
        String error = "";
        if (item == null) {
            error = error + "Item cannot be null! ";
        } else if (borrowableItemRepository.findBorrowableItemByBarCodeNumber(item.getBarCodeNumber()) == null){
            error += "Borrowable item does not exist!";
        }

        if (account == null) {
            error = error + "Account cannot be null! ";
        } else if (userAccountRepository.findUserAccountByUserID(account.getUserID()) == null){
            error += "User does not exist!";
        } 
        int check = startTime.compareTo(endTime);
        if(check > 0){
            error += "Start time must be before end time! ";
        }

        error = error.trim();
        if (error.length() > 0) {
            throw new IllegalArgumentException(error);
        }

        error = "";
        // UserAccount validation
        boolean validAccount = false;
        if(account instanceof Librarian) validAccount = true;
        else{
            validAccount = ((Patron) account).getValidatedAccount(); 
        }

        if(!validAccount){
            error = "User account is unvalidated, cannot complete item reservation transaction! ";
        }

        // Item validation
        if(item.getLibraryItem().getType() != ItemType.Room){
            error += "Item is not a room, cannot complete room reservation transaction! ";
        } else if(item.getState() != ItemState.Available){
            error += "Room is not available for reservation, please try waitlist! ";
        }

        error = error.trim();
        if (error.length() > 0) {
            throw new IllegalArgumentException(error);
        }

        Transaction itemReservation = new Transaction(item, account, TransactionType.RoomReservation, null); // No deadline for room reservation
        transactionRepository.save(itemReservation);
        return itemReservation;
    }

    
    /** 
     * Creates an item borrow transaction between a user account and a borrowable item
     * @param item
     * @param account
     * @return Transaction
     * @author Amani Jammoul
     * checked
     */
    @Transactional
    public Transaction createItemBorrowTransaction(BorrowableItem item, UserAccount account){
        // Input validation
        String error = "";
        if (item == null) {
            error = error + "Item cannot be null! ";
        } else if (borrowableItemRepository.findBorrowableItemByBarCodeNumber(item.getBarCodeNumber()) == null){
            error += "Borrowable item does not exist!";
        }

        if (account == null) {
            error = error + "Account cannot be null! ";
        } else if (userAccountRepository.findUserAccountByUserID(account.getUserID()) == null){
            error += "User does not exist!";
        }
        error = error.trim();
        if (error.length() > 0) {
            throw new IllegalArgumentException(error);
        }

        error = "";
        // UserAccount validation
        boolean validAccount = false;
        if(account instanceof Librarian) validAccount = true;
        else{
            validAccount = ((Patron) account).getValidatedAccount(); 
        }

        if(!validAccount){
            error = "User account is unvalidated, cannot complete borrow transaction! ";
        }

        int borrowedItems = getBorrowedItemsFromUser(account).size();
        if(borrowedItems >= 25){
            error += "User already has 25 borrowable items, cannot borrow any more before returning! ";
        }

        // Item validation
        if(item.getState() != ItemState.Available){
            error += "This item is not available and cannot be borrowed, please try waitlist! ";
        } else if(item.getLibraryItem().getType() == ItemType.Room || item.getLibraryItem().getType() == ItemType.NewspaperArticle){
            error += "This item cannot be borrowed! ";
        }

        error = error.trim();
        if (error.length() > 0) {
            throw new IllegalArgumentException(error);
        }

        LocalDate localDeadline = LocalDate.now().plusDays(20); // Deadline is set 20 days away from current day
        Date deadline = Date.valueOf(localDeadline);
        Transaction itemReservation = new Transaction(item, account, TransactionType.Borrowing, deadline); 
        transactionRepository.save(itemReservation);
        return itemReservation;
    }

    
    /** 
     * Creates an item return transaction between a user account and a borrowable item
     * @param item
     * @param account
     * @return Transaction
     * @author Amani Jammoul
     * cheked
     */
    @Transactional
    public Transaction createItemReturnTransaction(BorrowableItem item, UserAccount account){
        // Input validation
        String error = "";
        if (item == null) {
            error = error + "Item cannot be null! ";
        } else if (borrowableItemRepository.findBorrowableItemByBarCodeNumber(item.getBarCodeNumber()) == null){
            error += "Borrowable item does not exist!";
        }

        if (account == null) {
            error = error + "Account cannot be null! ";
        } else if (userAccountRepository.findUserAccountByUserID(account.getUserID()) == null){
            error += "User does not exist!";
        }
        error = error.trim();
        if (error.length() > 0) {
            throw new IllegalArgumentException(error);
        }

        Transaction itemReservation = new Transaction(item, account, TransactionType.Return, null); // No deadline for return
        transactionRepository.save(itemReservation);
        return itemReservation;
    }

    
    /** 
     * Creates an item waitlist transaction between a user account and a borrowable item
     * @param item
     * @param account
     * @return Transaction
     * @author Amani Jammoul
     * Added a check if item is available or not 
     * cheked
     */
    @Transactional
    public Transaction createItemWaitlistTransaction(BorrowableItem item, UserAccount account){
        // Input validation
        String error = "";
        if (item == null) {
            error = error + "Item cannot be null! ";
        } else if (borrowableItemRepository.findBorrowableItemByBarCodeNumber(item.getBarCodeNumber()) == null){
            error += "Borrowable item does not exist!";
        }

        if (account == null) {
            error = error + "Account cannot be null! ";
        } else if (userAccountRepository.findUserAccountByUserID(account.getUserID()) == null){
            error += "User does not exist!";
        }
        error = error.trim();
        if (error.length() > 0) {
            throw new IllegalArgumentException(error);
        }

        error = "";
        // UserAccount validation
        boolean validAccount = false;
        if(account instanceof Librarian) validAccount = true;
        else{
            validAccount = ((Patron) account).getValidatedAccount(); 
        }

        if(!validAccount){
            error = "User account is unvalidated, cannot complete waitlist transaction! ";
        }
        
        // Item validation
        if(item.getLibraryItem().getType() == ItemType.NewspaperArticle){
            error += "Newspapers cannot be borrowed and therefore do not have a waitlist ; waitlist transaction not complete! ";
        }
        else if(item.getState() != ItemState.Borrowed){
            error += "This item is available for reservation or borrowing, no Waitlist necessary! ";
        }

        error = error.trim();
        if (error.length() > 0) {
            throw new IllegalArgumentException(error);
        }

        Transaction itemReservation = new Transaction(item, account, TransactionType.Waitlist, null); // No deadline for waitlist
        transactionRepository.save(itemReservation);
        return itemReservation;
    }

    
    /** 
     * Creates an item renewal transaction between a user account and a borrowable item
     * @param item
     * @param account
     * @return Transaction
     * @author Amani Jammoul
     */
    @Transactional
    public Transaction createItemRenewalTransaction(BorrowableItem item, UserAccount account){
        // Input validation
        String error = "";
        if (item == null) {
            error = error + "Item cannot be null! ";
        } else if (borrowableItemRepository.findBorrowableItemByBarCodeNumber(item.getBarCodeNumber()) == null){
            error += "Borrowable item does not exist!";
        }

        if (account == null) {
            error = error + "Account cannot be null! ";
        } else if (userAccountRepository.findUserAccountByUserID(account.getUserID()) == null){
            error += "User does not exist!";
        }
        error = error.trim();
        if (error.length() > 0) {
            throw new IllegalArgumentException(error);
        }

        error = "";
        // UserAccount validation
        boolean validAccount = false;
        if(account instanceof Librarian) validAccount = true;
        else{
            validAccount = ((Patron) account).getValidatedAccount(); 
        }

        if(!validAccount){
            error = "User account is unvalidated, cannot complete waitlist transaction! ";
        }

        // Item validation
        int waitlistSize = getUsersOnWaitlist(item).size();
        if(waitlistSize != 0){
            error += "There are users on the waitlist, cannot complete renewal transaction (please return item)! ";
        }

        error = error.trim();
        if (error.length() > 0) {
            throw new IllegalArgumentException(error);
        }

        LocalDate localDeadline = LocalDate.now().plusDays(20); // Deadline is set 20 days away from current day
        Date deadline = Date.valueOf(localDeadline);
        Transaction itemReservation = new Transaction(item, account, TransactionType.Renewal, deadline); 
        transactionRepository.save(itemReservation);
        return itemReservation;
    }

    /**
     * Gets all transacitons from the DB
     * @return List of all transactions
     * @author Mathieu Geoffroy
     */
    public List<Transaction> getAllTransactions() {
        Iterable<Transaction> transactions = transactionRepository.findAll();
        List<Transaction> transactionList = new ArrayList<Transaction>();
        for (Transaction t : transactions){
            transactionList.add(t);
        }
        return transactionList;
    }

    
    /** 
     * @param account
     * @return List<BorrowableItem> - list of all items borrowed (checked out) by a user
     * @author Amani Jammoul
     * checked
     */
    @Transactional
    public List<BorrowableItem> getBorrowedItemsFromUser(UserAccount account){
        if (account == null) {
            throw new IllegalArgumentException("Account cannot be null!");
        } else if(userAccountRepository.findUserAccountByUserID(account.getUserID()) == null){
            throw new IllegalArgumentException("User does not exist!");
        }

        List<Transaction> allUserTransactions = transactionRepository.findByUserAccount(account);
        List<BorrowableItem> allBorrowedItems = new ArrayList<BorrowableItem>();
        for(Transaction t : allUserTransactions){
            if(t.getTransactionType().equals(TransactionType.Borrowing) || t.getTransactionType().equals(TransactionType.Renewal)){
               allBorrowedItems.add(t.getBorrowableItem()); 
            } 
        }
        return allBorrowedItems;
    }
    
    /** 
     * @param account
     * @return List<BorrowableItem> - list of all items reserved for a user
     * @author Amani Jammoul
     * checked
     */
    @Transactional
    public List<BorrowableItem> getReservedItemsFromUser(UserAccount account){
        if (account == null) {
            throw new IllegalArgumentException("Account cannot be null!");
        } else if(userAccountRepository.findUserAccountByUserID(account.getUserID()) == null){
            throw new IllegalArgumentException("User does not exist!");
        }

        List<Transaction> allUserTransactions = transactionRepository.findByUserAccount(account);
        List<BorrowableItem> allReservedItems = new ArrayList<BorrowableItem>();
        for(Transaction t : allUserTransactions){
            if(t.getTransactionType().equals(TransactionType.ItemReservation)){
                allReservedItems.add(t.getBorrowableItem()); 
            } 
        }
        return allReservedItems;
    }

    /** 
     * @param account
     * @return List<BorrowableItem> - list of all items user is on the waitlist for
     * @author Amani Jammoul
     */
    @Transactional
    public List<BorrowableItem> getItemWaitlistsFromUser(UserAccount account){
        if (account == null) {
            throw new IllegalArgumentException("Account cannot be null!");
        } else if(userAccountRepository.findUserAccountByUserID(account.getUserID()) == null){
            throw new IllegalArgumentException("User does not exist!");
        }

        List<Transaction> allUserTransactions = transactionRepository.findByUserAccount(account);
        List<BorrowableItem> allItemWaitlists = new ArrayList<BorrowableItem>();
        for(Transaction t : allUserTransactions){
            if(t.getTransactionType().equals(TransactionType.Waitlist)){
                allItemWaitlists.add(t.getBorrowableItem()); 
            } 
        }
        return allItemWaitlists;
    }

    
    /** 
     * @param item
     * @return List<UserAccount> - list of all user accounts that are on the waitlist for an item
     * @author Amani Jammoul
     * cheked
     */
    @Transactional
    public List<UserAccount> getUsersOnWaitlist(BorrowableItem item){
        if (item == null) {
            throw new IllegalArgumentException("Item cannot be null!");
        } else if (borrowableItemRepository.findBorrowableItemByBarCodeNumber(item.getBarCodeNumber()) == null){
            throw new IllegalArgumentException("Item does not exist!");
        }
        List<Transaction> allItemTransactions = transactionRepository.findByBorrowableItem(item);
        List<UserAccount> allWaitlistedUsers = new ArrayList<UserAccount>();
        for(Transaction t : allItemTransactions){
            if(t.getTransactionType().equals(TransactionType.Waitlist)){
                allWaitlistedUsers.add(t.getUserAccount());
            } 
        }
        return allWaitlistedUsers;
    }

   
    /**
     * Headlibrarian getters 
     * 1.get HeadLibrarian by ID (THROWS EXCPEPTION IF NOT EXIST)
     * 2.get the Headlibrarian by from first & lastName
     * 3.boolean to ensure only have 1 librarian instance at any time
     * 4.boolean version of 1 Checing if user is a head librarian
     * 5.get currentheadlibrarian
     * @author Eloyann Roy-Javanbakht
     * 
     * */
    @Transactional
    public HeadLibrarian getHeadLibrarianFromUserId(int userID) throws Exception{
    
       try {
        HeadLibrarian headLibrarian;
        headLibrarian= headLibrarianRepository.findHeadLibrarianByUserID(userID);
        return headLibrarian;
       } catch (Exception e) {
        throw new Exception("This User ID does not correspond to a Head Librarian");
       }

    }

    public HeadLibrarian getIfLibrarianHeadFromFullName(String firstName, String lastName){
    
        String error = "";
        if (firstName == null || lastName.trim().length() == 0) {
            error = error + "First Name  cannot be empty! ";
        }
        if (firstName == null || lastName.trim().length() == 0) {
            error = error + "Last Name  cannot be empty! ";
        }
        error = error.trim();
        if (error.length() > 0) {
            throw new IllegalArgumentException(error);
        }
        UserAccount head =  userAccountRepository.findByFirstNameAndLastName(firstName, lastName);
       
        if (!(head instanceof HeadLibrarian)){
            throw new IllegalArgumentException("This there is no head Librarian by this name.");
        }
        return (HeadLibrarian)head;

    }

    @Transactional
    public boolean checkOnlyOneHeadLibrarian(){
      long counter=headLibrarianRepository.count();
        if(counter!=1) return false;
        else return true;

    }

    @Transactional
    public boolean checkIfHeadLibrarianFromUserId(int userID) throws Exception {
        try { 
        headLibrarianRepository.findHeadLibrarianByUserID(userID);
        return true;
      } catch (Exception e) {
        throw new  Exception("This User ID does not correspond to a Head Librarian");
      }
      
    }

    public HeadLibrarian getHeadLibrarian() throws Exception{
    
        try {
         Iterable<HeadLibrarian> headLibrarian;
         headLibrarian= headLibrarianRepository.findAll();
        for(HeadLibrarian head: headLibrarian){ return head;}
       
        } catch (Exception e) {
         throw new Exception("There isn't any headLibrarian");
        }
        return null;
 
 
          
     }
    /**HeadLibrarian & Librarian Create and Replace
     * 1. Create A Headlibrarian-- checks if has or not 
     * 2. Replacing the current headlibrarian with a new one
     * 3. Create Librarian--checks the user creating it is a head librarian
     * 4. remove LIbrarian--checks the user deleting it is a head librarian
     * @author Eloyann Roy-Javanbakht
     */


    public HeadLibrarian CreateANewHeadLibrarian(String aFirstName, String aLastName, boolean aOnlineAccount, String aAddress, String aPassword, int aBalance , String aEmail)
    throws Exception {

        String error = "";
        if ((aFirstName == null || aFirstName.trim().length() == 0) && error.length()==0) {
            error = error + "First Name  cannot be empty! ";
        }
        if ((aLastName == null || aLastName.trim().length() == 0)&& error.length()==0) {
            error = error + "Last Name  cannot be empty! ";
        }
        if ((aAddress == null|| aAddress.trim().length() == 0)&& error.length()==0) {
            error = error + "Address cannot be empty! ";
        }
        if ((aPassword == null|| aPassword.trim().length() == 0) && aOnlineAccount == true && error.length()==0) {
            error = error + "Password cannot be empty! ";
        }
        if ((aEmail == null|| aEmail.trim().length() == 0) && aOnlineAccount == true && error.length()==0) {
            error = error + "Email cannot be empty! ";
        }
        error = error.trim();
        if (error.length() > 0) {
            throw new IllegalArgumentException(error);
        }
        
        HeadLibrarian headLibrarian;
        if(checkOnlyOneHeadLibrarian()) throw new  Exception("This User  does not the credentials to add a new librarian");
   
        headLibrarian = new HeadLibrarian(aFirstName, aLastName, aOnlineAccount, aAddress, aPassword, aBalance, aEmail);
        librarianRepository.save(headLibrarian);
        
        return headLibrarian;
        
    }


    public boolean DeleteHeadLibrarian(int  userID)
    throws Exception {
       HeadLibrarian headLibrarian=getHeadLibrarian();
       HeadLibrarian thisone=getHeadLibrarianFromUserId(userID);
       if(thisone.equals(headLibrarian)) 
      headLibrarianRepository.delete(headLibrarian);
    
        return true;
        
    }


    /**
     * @author Gabrielle Halpin & Eloyann 
     * @param creater
     * @param aFirstName
     * @param aLastName
     * @param aOnlineAccount
     * @param aAddress
     * @param aPassword
     * @param aBalance
     * @return
     * @throws Exception
     */
    public Librarian createANewLibrarian(UserAccount creater, String aFirstName, String aLastName, boolean aOnlineAccount, String aAddress, String aPassword, int aBalance , String aEmail) throws Exception {
  
        String error = "";
        if ((aFirstName == null || aFirstName.trim().length() == 0)&& error.length()==0) {
            error = error + "First Name  cannot be empty! ";
        }
        if ((aLastName == null || aLastName.trim().length() == 0)&& error.length()==0) {
            error = error + "Last Name  cannot be empty! ";
        }
        if (creater == null && error.length()==0) {
            error = error + "User Requesting the change cannot be empty! ";
        }
        if ((aAddress == null|| aAddress.trim().length() == 0)&& error.length()==0) {
            error = error + "Address cannot be empty! ";
        }
        if ((aPassword == null|| aPassword.trim().length() == 0) && aOnlineAccount == true && error.length()==0) {
            error = error + "Password cannot be empty! ";
        }
        if ((aEmail == null|| aEmail.trim().length() == 0) && aOnlineAccount == true && error.length()==0) {
            error = error + "Email cannot be empty! ";
        }
        error = error.trim();
        if (error.length() > 0) {
            throw new IllegalArgumentException(error);
        }

        try {
        getHeadLibrarianFromUserId(creater.getUserID());

        } catch (Exception e) {
            throw new  Exception("This User  does not the credentials to add a new librarian");
        }
        if(getLibrarianFromFullName(aFirstName, aLastName).getAddress().equals(aAddress)) throw new Exception("This User already has a librarian account");
       
        Librarian librarian=new Librarian();
      
		librarian.setFirstName(aFirstName);
        librarian.setLastName(aLastName);
        librarian.setOnlineAccount(aOnlineAccount);
        librarian.setAddress(aAddress);
        librarian.setPassword(aPassword);
        librarian.setBalance(aBalance);
        librarian.setEmail(aEmail);
        librarianRepository.save(librarian);
        return librarian;
        
    }

        //librarian delete
    public boolean deleteALibrarian(int userID, int  userIDHeadLibrarian) throws Exception {
        
      if(userIDHeadLibrarian!=getHeadLibrarian().getUserID())  
      throw new  Exception("This User  does not the credentials to add a new librarian");
        

      try {
          Librarian librarian= getLibrarianFromUserId(userID);
        librarianRepository.delete(librarian);
      } catch (Exception e) {

        throw new  Exception("This librarian does not exits");
      }
        
        return true;
    }
        /**Librarian getters
        * 1. get librarian from name
        * 2. get librarian from ID
        * 3. get all librarians
        * @author Eloyann Roy-Javanbakht
        */
        public Librarian getLibrarianFromFullName(String firstName, String lastName)throws Exception{
            String error = "";
            if (firstName == null || firstName.trim().length() == 0) {
                error = error + "First Name  cannot be empty! ";
            }
            if (lastName == null || lastName.trim().length() == 0) {
                error = error + "Last Name  cannot be empty! ";
            }
            error = error.trim();
            if (error.length() > 0) {
                throw new IllegalArgumentException(error);
            }


                UserAccount librarian = userAccountRepository.findByFirstNameAndLastName(firstName, lastName);
            
            if(!(librarian instanceof Librarian)){
                throw new Exception("the name privided does not correcpond to a librarian");
            }
            return (Librarian) librarian;

        }

       public Librarian getLibrarianFromUserId(int userID) throws Exception{
        try {
         Librarian librarian;
         librarian= librarianRepository.findLibrarianByUserID(userID);
         return librarian;
        } catch (Exception e) {
         throw new Exception("This User ID does not correspond to a Head Librarian");
        }
    
          
        }

    //* TimeSlot Service Methods
    /**
     * Get all timeslots from the first and only library system.
     * @author Mathieu Geoffroy
     * @throws Exception - If there is no library system
     * @return List of TimeSlots
     * cheked
     */
    @Transactional
    public List<TimeSlot> getAllTimeSlots() throws Exception {
        Iterable<TimeSlot> allTimeSlots = timeSlotRepository.findAll();
        List<TimeSlot> timeSlots = new ArrayList<TimeSlot>();
        for(TimeSlot i : allTimeSlots){
            if(i instanceof TimeSlot){
                timeSlots.add(i);
            }
        }
        return timeSlots;
    }

    /**
     * Get a list of timeslots for a specific librarian
     * @author Mathieu Geoffroy
     * @param librarian - librarian that is 'working' those timeslots
     * @return List of timeslots
     * checked
     */
    @Transactional
    public List<TimeSlot> getTimeSlotsFromLibrarian(Librarian librarian) {
        String error="";
        if (librarian == null) {
            error = error + "A Librarian needs to be selected";
        } else if (!librarianRepository.existsById(librarian.getUserID())) {
            error = error + "librarian doesn't exists ";
        }
        error = error.trim();

        if (error.length() > 0) {
            throw new IllegalArgumentException(error);
        } 
       
       
        List<TimeSlot> librarianTimeSlots = timeSlotRepository.findByLibrarian(librarian);
        return librarianTimeSlots;
    }

    /**
     * Get a list of timeslots that have been assigned by the (only) head librarian
     * @author Mathieu Geoffroy
     * @return List of timeslots
     * added checks -elo
     * checked
     */
    @Transactional
    public List<TimeSlot> getTimeSlotsFromHeadLibrarian(HeadLibrarian headLibrarian) {
        
        String error="";
        if (headLibrarian == null) {
            error = error + "A HeadLibrarian needs to be selected";
        } else if (!headLibrarianRepository.existsById(headLibrarian.getUserID())) {
            error = error + "Headlibrarian doesn't exists ";
        }
        error = error.trim();

        if (error.length() > 0) {
            throw new IllegalArgumentException(error);
        }


        List<TimeSlot> timeSlots = timeSlotRepository.findByHeadLibrarian(headLibrarian);
        return timeSlots;
    }

    /**
     * Get timeslot list (workshits) for a specific librarian by inputing the librarian's first and last name
     * @author Mathieu Geoffroy
     * @param firstName - librarian's first name
     * @param lastName - librarian's last name
     * @return list of timeslots
     * checked
     * @throws Exception
     */
    @Transactional
    public List<TimeSlot> getTimeSlotsFromLibrarianFirstNameAndLastName(String firstName, String lastName) throws Exception {
        Librarian librarian = getLibrarianFromFullName(firstName, lastName);
        List<TimeSlot> librarianTimeSlots = timeSlotRepository.findByLibrarian(librarian);
        return librarianTimeSlots;
    }

    /**
     * Get timeslot list (workshifts) for a specific librarian by inputing the librarian,s UserId
     * @author Mathieu Geoffroy
     * @param id - Librarian's user id
     * @return list of timeslots
     * checked
     * @throws Exception
     */
    @Transactional
    public List<TimeSlot> getTimeSlotsFromLibrarianUserID(int id) throws Exception {
        Librarian librarian = getLibrarianFromUserId(id);
        List<TimeSlot> librarianTimeSlots = timeSlotRepository.findByLibrarian(librarian);
        return librarianTimeSlots;
    }

    /**
     * Get a timeslot by inputing its id
     * @author Mathieu Geoffroy
     * @param id - timeslot id
     * @return TimeSlot
     * checked
    */
    @Transactional
    public TimeSlot getTimeSlotsFromId(int id) {
        TimeSlot timeSlot = timeSlotRepository.findTimeSlotByTimeSlotID(id);
        return timeSlot;
    }

    /**
     * Create a timeslot in the first (and only) available library system with the first (and only)
     * available head librarian. The timeslot is saved to the DB.
     * @author Mathieu Geoffroy
     * @param startDate - Start date of timeslot
     * @param startTime - Start time of timeslot
     * @param endDate - End date of timeslot 
     * @param endTime - End time of timeslot
     * @return Timeslot that was created
     * @throws Exception - If the library system does not exit
     * @throws Exception - If there is no head librarian
     * checked
     */
    @Transactional
    public TimeSlot createTimeSlot(Date startDate, Time startTime, Date endDate, Time endTime) throws Exception {
        HeadLibrarian headLibrarian =getHeadLibrarian();
        TimeSlot timeSlot = new TimeSlot(startDate, startTime, endDate, endTime, headLibrarian);
        timeSlotRepository.save(timeSlot);
        return timeSlot;
    }

    /**
     * Assigns a librarian to a timeslot and update the DB
     * @author Mathieu Geoffroy
     * @param ts - the timeslot to which the librarian will be assigned
     * @param librarian - the librarian being assigned
     * @return updated TimeSlot
     * added checks -elo
     * checked
     */
    @Transactional
    public TimeSlot assignTimeSlotToLibrarian(TimeSlot ts, Librarian librarian) {
        
        String error="";
        if (ts == null) {
            error = error + "TimeSlot needs to be selected for registration! ";
        }
        if (librarian == null) {
            error = error + "Event needs to be selected for registration!";
        } else if (!librarianRepository.existsById(librarian.getUserID())) {
            error = error + "librarian doesn't exists ";
        }
        error = error.trim();

        if (error.length() > 0) {
            throw new IllegalArgumentException(error);
        }
        
        

        TimeSlot timeSlot = timeSlotRepository.findTimeSlotByTimeSlotID(ts.getTimeSlotID());
        timeSlot.addLibrarian(librarian);
        timeSlotRepository.save(timeSlot);
        return timeSlot;
    }
    /**
     * deletes the timeslot given the timeslot parameters
     * @param account user account calling the method
     * @param startDate start date of timeslot to delete
     * @param startTime start time of timeslot to delete
     * @param endDate end date of timeslot to delete
     * @param endTime end time of timeslot to delete
     * @param library current library system
     * @return true is deleted successfully
     * @throws Exception if invalid inputs
     * @throws Exception if user is not the head librarian
     * @author Mathieu Geoffroy
     */

    @Transactional
    public boolean deleteTimeSlot(UserAccount account, int timeslotID) throws Exception {

        String error = "";
        if (account == null) error = error + "Invalid account. ";
        if (timeslotID < 1) error = error + "Invalid timeslotID. ";
        error = error.trim();

        if (error.length() > 0) throw new IllegalArgumentException(error);

        getHeadLibrarianFromUserId(account.getUserID()); //will throw exception is account is not head librarian

        TimeSlot timeSlot = timeSlotRepository.findTimeSlotByTimeSlotID(timeslotID);
        timeSlotRepository.delete(timeSlot);
        return true;
    }

    //* Opening Hours Service Methods
    /**
     * get all the opening hours in the first (and only) available library system
     * @author Mathieu Geoffroy
     * @return lsit of OpeningHour
     * @throws Exception - when there is no library system
     */
    @Transactional
    public List<OpeningHour> getAllOpeningHours() throws Exception {
        Iterable<OpeningHour> allOpeningHours = openingHourRepository.findAll();
        List<OpeningHour> openingHours = new ArrayList<OpeningHour>();
        for(OpeningHour i : allOpeningHours){
            if(i instanceof OpeningHour){
                openingHours.add(i);
            }
        }
        return openingHours;
    }
    /**
     * get opening hour from its id
     * @author Mathieu Geoffroy
     * @param id - opening hour id
     * @return  openingHour 
     */
    @Transactional
    public OpeningHour getOpeningHourFromID(int id) {
        OpeningHour openingHour = openingHourRepository.findOpeningHourByHourID(id);
        return openingHour;
    }
     
    /**
     * get opening hour list for a given day of the week
     * @author Mathieu Geoffroy
     * @param day - string for day of week that MUST start with a capital letter (case sensitive matching)
     * @return list of opening hours of a certain day of week
     * @throws Exception - when the input is not match (case-sensitve) with the correct day of week
     * checked
     */
    @Transactional
    public List<OpeningHour> getOpeningHoursByDayOfWeek(String day) throws Exception{
      DayOfWeek dayOfWeek;
        try {
            dayOfWeek = DayOfWeek.valueOf(day); //case sensitive match
        } catch (IllegalArgumentException e) {
            throw new Exception("Invalid day");
        }
        List<OpeningHour> openingHours = openingHourRepository.findByDayOfWeek(dayOfWeek);
        return openingHours;
    }

    /**
     * get the opening hours that have been made by a head librarian
     * @author Mathieu Geoffroy
     * @param headLibrarian - the head librarian that made the opening hours
     * @return - list of opening hours
     * added some -elo
     * checked
     */
    @Transactional
    public List<OpeningHour> getOpeningHoursFromHeadLibrarian(HeadLibrarian headLibrarian) {
        String error="";
        if (headLibrarian == null) {
            error = error + "A HeadLibrarian needs to be selected";
        } else if (!headLibrarianRepository.existsById(headLibrarian.getUserID())) {
            error = error + "Headlibrarian doesn't exists ";
        }
        error = error.trim();

        if (error.length() > 0) {
            throw new IllegalArgumentException(error);
        }
        List<OpeningHour> openingHours = openingHourRepository.findByHeadLibrarian(headLibrarian);
        return openingHours;
    }

    /**
     * Create opening hour with the first (and only) available library system and first (and only) available
     * head librarian. Saves the opening hour to the DB
     * @author Mathieu Geoffroy
     * @param day - string of day of week (case-sensistive): MUST start with capital letter
     * @param startTime - start time of opening hour
     * @param endTime - end time of opening hour
     * @return the create opening hour
     * @throws Exception - When the day string does not match the DayOfWeek enum format
     * @throws Exception - When there is no library systme
     * @throws Exception - When there is no head librarian
     * cheked
     */
    @Transactional
    public OpeningHour createOpeningHour(String day, Time startTime, Time endTime) throws Exception {
        DayOfWeek dayOfWeek;
        try {
            dayOfWeek = DayOfWeek.valueOf(day); //case sensitive match
        } catch (IllegalArgumentException e) {
            throw new Exception("Invalid day");
        }
        HeadLibrarian headLibrarian =getHeadLibrarian();
        OpeningHour openingHour = new OpeningHour(dayOfWeek, startTime, endTime, headLibrarian);
        openingHourRepository.save(openingHour);
        return openingHour;
    }    

    /**
     * deletes the opening hour based on an opening hour with the same fields
     * @param account user account calling this method
     * @param day day of week (MUST start with capital)
     * @param startTime start time of opening hour
     * @param endTime end time of openinh hour
     * @param library current library system
     * @return true if openinghour is successfully deleted
     * @throws Exception if null inputs
     * @throws Exception if unauthorized user account (not head librarian)
     * @throws Exception if not day string is incorrect
     * @author Mathieu Geoffroy
     */

    @Transactional
    public boolean deleteOpeningHour(UserAccount account, int openingHourID) throws Exception{

        String error = "";
        if (account == null) error = error + "Invalid account. ";
        if (openingHourID < 1) error = error + "Invalid openinghourID. ";
        error = error.trim();

        if (error.length() > 0) throw new IllegalArgumentException(error);

        getHeadLibrarianFromUserId(account.getUserID()); //error is account accessing is not head librarian
        OpeningHour openingHour = openingHourRepository.findOpeningHourByHourID(openingHourID);
        openingHourRepository.delete(openingHour);
        return true;
    }

    //* Holiday service methods
    /**
     * get all the holidays from the first (and only) librry system
     * @author Mathieu Geoffroy
     * @return list of holidays
     * @throws Exception - when there is no library system
     * checked
     */
    @Transactional
    public List<Holiday> getAllHolidays() throws Exception {
        Iterable<Holiday> allHolidays = holidayRepository.findAll();
        List<Holiday> holidays = new ArrayList<Holiday>();
        for(Holiday i : allHolidays){
            if(i instanceof Holiday){
                holidays.add(i);
            }
        }
        return holidays;
    }

    /**
     * get holiday from its holiday id 
     * @author Mathieu Geoffroy
     * @param id - holiday id
     * @return holiday
     * checked
     */
    @Transactional
    public Holiday getHolidayFromId(int id) {
        Holiday holiday = holidayRepository.findHolidayByHolidayID(id);
        return holiday;
    }

    /**
     * get holidays made by the head librarian
     * @author Mathieu Geoffroy
     * @param headLibrarian - head librarian that created the holidays
     * @return list of holiday
     * checked
     */
    @Transactional
    public List<Holiday> getHolidaysFromHeadLibrarian(HeadLibrarian headLibrarian) {
        
        String error="";
        if (headLibrarian == null) {
            error = error + "A HeadLibrarian needs to be selected";
        } else if (!headLibrarianRepository.existsById(headLibrarian.getUserID())) {
            error = error + "Headlibrarian doesn't exists ";
        }
        error = error.trim();

        if (error.length() > 0) {
            throw new IllegalArgumentException(error);
        }List<Holiday> holidays = holidayRepository.findByHeadLibrarian(headLibrarian);
        return holidays;
    }

    /**
     * Create holiday with first (and only) available library system and the first (and only)
     * avaialbe head librarian. Saves to DB
     * @author Mathieu Geoffroy
     * @param date - date of holiday
     * @param startTime - start time of holiday
     * @param endTime - end time of holiday
     * @return holiday that was created
     * @throws Exception - when there is no library system
     * @throws Exception - when there is no head librarian
     * checked
     */
    @Transactional
    public Holiday createHoliday(Date date, Time startTime, Time endTime) throws Exception{
        HeadLibrarian headLibrarian =getHeadLibrarian();
        Holiday holiday = new Holiday(date, startTime, endTime, headLibrarian);
        holidayRepository.save(holiday);
        return holiday;
    }
    
    /**
     * deletes holiday form DB based on same input parameters
     * @param account user account calling this method
     * @param date date of holiday
     * @param startTime start time of holiday
     * @param endTime end time of holiday
     * @param library current library system
     * @return true if holiday successfully deleted
     * @throws Exception if invalid inputs
     * @throws Exception when user is not authorized (not head librarian)
     * @author Mathieu Geoffroy
     */

    @Transactional
    public boolean deleteHoliday(UserAccount account, int holidayID) throws Exception {

        String error = "";
        if (account == null) error = error + "Invalid account. ";
        if (holidayID < 1) error = error + "Invalid holidayID. ";
        error = error.trim();

        if (error.length() > 0) throw new IllegalArgumentException(error);

        getHeadLibrarianFromUserId(account.getUserID()); //throws error is account is not head librarian

        Holiday holiday = holidayRepository.findHolidayByHolidayID(holidayID);
        holidayRepository.delete(holiday);
        return true;
    }


    /***
     * This method retrieves the user from the database
     * @author gabrielle Halpin
     * @param userID
     * @return person
     * @throws Exception 
     */
    @Transactional
	public UserAccount getUserbyUserId(int userID) throws Exception {

		try {
            UserAccount person = userAccountRepository.findUserAccountByUserID(userID);
            return person;

		}catch (NoSuchElementException e) {
	         throw new Exception("This user does not exist.");
		}
	}

    /**
     * This method creates the Patron account and stores it in the database
     * @author Gabrielle Halpin
     * @param aFirstName
     * @param aLastName
     * @param aOnlineAccount
     * @param aAddress
     * @param aValidatedAccount
     * @param aPassword
     * @param aBalance
     * @param aEmail
     * @return patron
     * ADDED STUFF -ELO
     * checked
     */
    @Transactional
	public Patron createPatron(UserAccount creator, String aFirstName, String aLastName, boolean aOnlineAccount, String aAddress, boolean aValidatedAccount, String aPassword, int aBalance, String aEmail) {
		
        String error = "";
        if ((aFirstName == null || aFirstName.trim().length() == 0)&& error.length() == 0) {
            error = error + "First Name cannot be empty!";
        }
        if ((aLastName == null || aLastName.trim().length() == 0)&& error.length() == 0) {
            error = error + "Last Name cannot be empty!";
        }
        if (aAddress == null|| aAddress.trim().length() == 0 && error.length() == 0) {
            error = error + "Address cannot be empty!";
        }
        if ((aPassword == null|| aPassword.trim().length() == 0) && aOnlineAccount == true && error.length() == 0) {
            error = error + "Password cannot be empty!";
        }
        if ((aEmail == null|| aEmail.trim().length() == 0) && aOnlineAccount == true && error.length() == 0) {
            error = error + "Email cannot be empty!";
        }
        if (creator == null) {
            error = error + "There needs to be a creator for this method";
        }
        if (creator instanceof Patron && aOnlineAccount == false) {
            error = error + "Only a Librarian can create an in-person account";
        }

        // the system will set the validity of the account to false, making sure that 
        // the user goes to validate whether they are a resident or not.
        if (aOnlineAccount == true){
            aValidatedAccount = false;
        }

        error = error.trim();
        if (error.length() > 0) {
            throw new IllegalArgumentException(error);
        }
        Patron patron = new Patron();
		patron.setFirstName(aFirstName);
        patron.setLastName(aLastName);
        patron.setOnlineAccount(aOnlineAccount);
        patron.setAddress(aAddress);
        patron.setPassword(aPassword);
        patron.setBalance(aBalance);
        patron.setEmail(aEmail);
        patron.setValidatedAccount(aValidatedAccount);
		patronRepository.save(patron);
        userAccountRepository.save(patron);
		return patron;
	}

    /***
     * This method gets the patron object from the database
     * @author Gabrielle halpin
     * @param userID
     * @return person 
     * @throws Exception 
     * checked
     */
    @Transactional
	public Patron getPatronByUserId(int userID) throws Exception {
		try {
			Patron person = patronRepository.findPatronByUserID(userID);
		
		return person;
		}
		catch (Exception e) {
	         throw new Exception("This patron does not exist.");
		}
	}
    
    /***
    * This method gets the patron object, given the userID, from a list of patrons in the database.
    * @author Zoya Malhi
    * @param firstName, lastName
    * @return null 
    added checks -elo
    checked
    */
    @Transactional
    public Patron getPatronFromFullName(String firstName, String lastName){
       		
        String error = "";
        if (firstName == null || firstName.trim().length() == 0) {
            error = error + "First Name  cannot be empty! ";
        }
        if (lastName == null || lastName.trim().length() == 0) {
            error = error + "Last Name  cannot be empty! ";
        }
        error = error.trim();
        if (error.length() > 0) {
            throw new IllegalArgumentException(error);
        }
       
        try { 
       List<Patron> patron = getAllPatrons();
      
       for (Patron p: patron){
           if(p.getFirstName().equals(firstName) && p.getLastName().equals(lastName)) return p;

       }
      }
      catch (Exception e) {
          throw new IllegalArgumentException("Could not get patron from full name!");
      
      }
	return null;

    }
    
    /***
     * This method deletes a patron object from the database.
     * @author Zoya Malhi
     * @param head, aFirstNAme, aLastName, aOnlineAccount, aAddress, aValidatedAccount, aPassword, aBalance
     * @return patron 
     * added checks -elo
     * edited by Gabby
     * checked
     */
    @Transactional
    public Patron deleteAPatronbyUserID(UserAccount head, int userID) throws Exception {
        try {
        getHeadLibrarianFromUserId(head.getUserID());

        } catch (Exception e) {
            throw new  Exception("This user does not have the credentials to delete an existing patron");
        }

        try {
            Patron patronAccount = patronRepository.findPatronByUserID(userID);
            patronRepository.delete(patronAccount);
            
            return patronAccount;
        } catch (Exception e) {
            throw new  Exception("This user Id does not exist as a Patron");
        }
 
    }


    /***
     * This method gets all patrons in the database.
     * @author Zoya Malhi
     * @param none
     * @return list of patrons 
     * @throws Exception 
     * checked
     */
    
    @Transactional
    public List<Patron> getAllPatrons() throws Exception{
        try {
        return toList(patronRepository.findAll());         
        } catch (Exception e) {
         throw new Exception("There are no patrons in the database.");
        }    
    }
    
    /***
     * This helper method collects objects and stores them in a list. 
     * @author Zoya Malhi
     * @param iterable type
     * @return list 
     */
    private <T> List<T> toList(Iterable<T> iterable){
		List<T> list = new ArrayList<T>();
		for (T t : iterable) {
			list.add(t);
		}
		return list;
	}
    
    /**
     * This field allows the user to change their password for an online account
     * @author Gabrielle Halpin
     * @param aPassWord
     * @param account
     * @return boolean 
     */
    
    public UserAccount changePassword(String aPassWord, UserAccount account){
        String error = "";
        if (account == null && error.length()==0){
            error = error + "The account cannot be null";
        }
        if (account.getOnlineAccount() == false && error.length()==0){
            error = error + "The account must be an online account";
        }
        if ((aPassWord == null|| aPassWord.trim().length() == 0)&& error.length()==0) {
            error = error + "Password cannot be empty!";
        }
        if ((aPassWord == account.getPassword()&& error.length()==0)){
            error = error + "This is already your password.";
        }

        error = error.trim();
        if (error.length() > 0) {
            throw new IllegalArgumentException(error);
        }

        account.setPassword(aPassWord);
        return account;
    }

    /**
     * @author Gabrielle Halpin
     * This method allows the user to change their firstName
     * @param aFirstName
     * @param account
     * @return Useraccount account
     */
    public UserAccount changeFirstName(String aFirstName, UserAccount account){
        String error = "";
        if (account == null && error.length()==0){
            error = error + "The account cannot be null";
        }
        if ((aFirstName == null|| aFirstName.trim().length() == 0)&& error.length()==0) {
            error = error + "firstName cannot be empty!";
        }
        if ((aFirstName == account.getFirstName()&& error.length()==0)){
            error = error + "This is already your firstName.";
        }

        error = error.trim();
        if (error.length() > 0) {
            throw new IllegalArgumentException(error);
        }

        account.setFirstName(aFirstName);
        return account;
    }

    /**
     * @author Gabrielle Halpin
     * This method allows the user to change their lastName
     * @param aLastName
     * @param account
     * @return Useraccount account
     */
    public UserAccount changeLastName(String aLastname, UserAccount account){
        String error = "";
        if (account == null && error.length()==0){
            error = error + "The account cannot be null";
        }
        if ((aLastname == null|| aLastname.trim().length() == 0)&& error.length()==0) {
            error = error + "lastname cannot be empty!";
        }
        if ((aLastname == account.getLastName()&& error.length()==0)){
            error = error + "This is already your lastname.";
        }

        error = error.trim();
        if (error.length() > 0) {
            throw new IllegalArgumentException(error);
        }

        account.setLastName(aLastname);
        return account;
    }

    /**
     * @author Gabrielle Halpin
     * This method allows the user to change their address 
     * @param aAddress
     * @param account
     * @return Useraccount account
     */
    public UserAccount changeAddress(String aAddress, UserAccount account){
        String error = "";
        if (account == null && error.length()==0){
            error = error + "The account cannot be null";
        }
        if ((aAddress == null|| aAddress.trim().length() == 0)&& error.length()==0) {
            error = error + "Address cannot be empty!";
        }
        if ((aAddress == account.getAddress()&& error.length()==0)){
            error = error + "This is already your Address.";
        }

        error = error.trim();
        if (error.length() > 0) {
            throw new IllegalArgumentException(error);
        }

        account.setAddress(aAddress);
        return account;
    }

    /**
     * @author Gabrielle Halpin
     * This method allows the user to change their email 
     * @param aEmail
     * @param account
     * @return Useraccount account
     */
    public UserAccount changeEmail(String aEmail, UserAccount account){
        String error = "";
        if (account == null && error.length()==0){
            error = error + "The account cannot be null";
        }
        if (account.getOnlineAccount() == false && error.length()==0){
            error = error + "The account must be an online account";
        }
        if ((aEmail == null|| aEmail.trim().length() == 0)&& error.length()==0) {
            error = error + "Email cannot be empty!";
        }
        if ((aEmail == account.getEmail()&& error.length()==0)){
            error = error + "This is already your Email.";
        }

        error = error.trim();
        if (error.length() > 0) {
            throw new IllegalArgumentException(error);
        }

        account.setEmail(aEmail);
        return account;
    }

    /**
     * This mathod is called when the Librarian set's a customer's account to an online account
     * @author Gabrielle Hapin
     * @param account
     * @param aEmail
     * @param aPassword
     * @param aOnlineAccount
     * @param creator
     * @return UserAccount
     */
    public UserAccount setOnlineAccount(UserAccount account, String aEmail, String aPassword, boolean aOnlineAccount, UserAccount creator){
        String error = "";
        if (account == null && error.length()==0){
            error = error + "The account cannot be null";
        }
        if (creator == null && error.length()==0){
            error = error + "The creator cannot be null";
        }
        if (!(creator instanceof Librarian)){
            error = error + "The creator must be a librarian";
        }
        if (account.getOnlineAccount() == true && error.length()==0){
            error = error + "The account is already an online account.";
        }
        if ((aPassword == null|| aPassword.trim().length() == 0) && aOnlineAccount == true && error.length()==0) {
            error = error + "Password cannot be empty! ";
        }
        if ((aEmail == null|| aEmail.trim().length() == 0) && aOnlineAccount == true && error.length()==0) {
            error = error + "Email cannot be empty!";
        }

        error = error.trim();
        if (error.length() > 0) {
            throw new IllegalArgumentException(error);
        }

        boolean set1 = account.setEmail(aEmail);
        boolean set2 = account.setPassword(aPassword);
        boolean set3 = account.setOnlineAccount(true);
        if (set1 == false || set2 == false || set3 == false){
            throw new IllegalArgumentException("You cannot set this account to an online account.");
        }
        return account;
    }


    /**
     * This method sets the validity of the user account which must be done by a librarian
     * @author Gabrielle Halpin
     * @param patron
     * @param validated
     * @param creator
     * @return PatronDTO
     * @throws Exception
     */
    public Patron setValidatedAccount(Patron patron, boolean validated, UserAccount creator) throws Exception{
        String error="";
        if (!(creator instanceof Librarian) && error.length()==0){
            error = error + "Only a Librarian can change the validity of an account";
        }
        if (creator == null && error.length()==0){
            error = error + "The creator cannot be null";
        }
        if (patron == null && error.length()==0){
            error = error + "The creator cannot be null";
        }
        error = error.trim();
        if (error.length() > 0) {
            throw new IllegalArgumentException(error);
        }
        try {
            Patron patronAccount =  patronRepository.findPatronByUserID(patron.getUserID());
            patronAccount.setValidatedAccount(validated);
            return patronAccount;
            
           } catch (Exception e) {
            throw new Exception("This user does not exists in the database.");
        }
           
    }

    /***
     * This returns a list of all users in a library system.
     * @author Gabrielle Halpin
     * @param userID
     * @return users 
     * @throws Exception 
     * checked
     */
    @Transactional
	public List<UserAccount> getAllUsers() throws Exception {
        String error = "";
        Iterable<UserAccount> allusers = userAccountRepository.findAll();
        ArrayList<UserAccount> users = new ArrayList<UserAccount>();
        if(users.size()==0 || allusers == null){
            error = "There are no Users in the system";
        }
        error = error.trim();
        if (error.length() > 0) {
            throw new IllegalArgumentException(error);
        }
        return users;
	}



    /***
     * This method gets all librarians in the database.
     * @author Zoya Malhi & Eloyann RoyJavanbakht
     * @param none
     * @return list of librarians 
     * @throws Exception 
     * checked
     */
   public List<Librarian> getAllLibrarians() throws Exception{
       try {
       return toList(librarianRepository.findAll());
       
       } catch (Exception e) {
        throw new Exception("There are no librarians is the database.");
       }
      
    }

}



