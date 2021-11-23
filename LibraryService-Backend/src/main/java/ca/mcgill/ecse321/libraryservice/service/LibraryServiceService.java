package ca.mcgill.ecse321.libraryservice.service;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.libraryservice.dao.*;
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
     * @param userID
     * @return UserAccount - account for user with given full name
     * @author Amani Jammoul
     * @throws Exception
     */
    @Transactional
    public UserAccount getUserAccountByUserID(int userID) throws Exception{
        String error = "";
        if (userID < 1) {
            error += "ID cannot be 0 or negative ";
        }

        error = error.trim();
        if (error.length() > 0) {
            throw new IllegalArgumentException(error);
        }

        UserAccount account = userAccountRepository.findUserAccountByUserID(userID);
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
            if(i.getType().equals(ItemType.Movie)){
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
        } 
        /* else if (borrowableItemRepository.findBorrowableItemByBarCodeNumber(item.getBarCodeNumber()) == null){
            error += "Borrowable item does not exist!";
        } */

        if (account == null) {
            error = error + "Account cannot be null! ";
        } 
        /*else if (userAccountRepository.findUserAccountByUserID(account.getUserID()) == null){
            error += "User does not exist!";
        } */
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
        } 
        /*else if (borrowableItemRepository.findBorrowableItemByBarCodeNumber(item.getBarCodeNumber()) == null){
            error += "Borrowable item does not exist!";
        } */

        if (account == null) {
            error = error + "Account cannot be null! ";
        } 
        /*else if (userAccountRepository.findUserAccountByUserID(account.getUserID()) == null){
            error += "User does not exist!";
        } */
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
            error = "User account is unvalidated, cannot complete room reservation transaction! ";
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

        Transaction roomReservation = new Transaction(item, account, TransactionType.RoomReservation, null); // No deadline for room reservation
        transactionRepository.save(roomReservation);
        return roomReservation;
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
        } 
        /*else if (borrowableItemRepository.findBorrowableItemByBarCodeNumber(item.getBarCodeNumber()) == null){
            error += "Borrowable item does not exist!";
        }*/

        if (account == null) {
            error = error + "Account cannot be null! ";
        } 
        /*else if (userAccountRepository.findUserAccountByUserID(account.getUserID()) == null){
            error += "User does not exist!";
        }*/

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
        item.setState(ItemState.Borrowed);
        borrowableItemRepository.save(item);
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
        } 
        /*else if (borrowableItemRepository.findBorrowableItemByBarCodeNumber(item.getBarCodeNumber()) == null){
            error += "Borrowable item does not exist!";
        }*/

        if (account == null) {
            error = error + "Account cannot be null! ";
        } 
        /*else if (userAccountRepository.findUserAccountByUserID(account.getUserID()) == null){
            error += "User does not exist!";
        }*/ 
        error = error.trim();
        if (error.length() > 0) {
            throw new IllegalArgumentException(error);
        }

        Transaction itemReservation = new Transaction(item, account, TransactionType.Return, null); // No deadline for return
        transactionRepository.save(itemReservation);
        item.setState(ItemState.Available);
        borrowableItemRepository.save(item);
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
        } 
        /*else if (borrowableItemRepository.findBorrowableItemByBarCodeNumber(item.getBarCodeNumber()) == null){
            error += "Borrowable item does not exist!";
        }*/

        if (account == null) {
            error = error + "Account cannot be null! ";
        } 
        /*else if (userAccountRepository.findUserAccountByUserID(account.getUserID()) == null){
            error += "User does not exist!";
        }*/
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
        } 
        /*else if (borrowableItemRepository.findBorrowableItemByBarCodeNumber(item.getBarCodeNumber()) == null){
            error += "Borrowable item does not exist!";
        }*/

        if (account == null) {
            error = error + "Account cannot be null! ";
        } 
        /*else if (userAccountRepository.findUserAccountByUserID(account.getUserID()) == null){
            error += "User does not exist!";
        }*/ 
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
            error = "User account is unvalidated, cannot complete renewal transaction! ";
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
        } 
        /*else if(userAccountRepository.findUserAccountByUserID(account.getUserID()) == null){
            throw new IllegalArgumentException("User does not exist!");
        } */

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
        } 
        /*else if(userAccountRepository.findUserAccountByUserID(account.getUserID()) == null){
            throw new IllegalArgumentException("User does not exist!");
        }*/

        List<Transaction> allUserTransactions = transactionRepository.findByUserAccount(account);
        List<BorrowableItem> allReservedItems = new ArrayList<BorrowableItem>();
        for(Transaction t : allUserTransactions){
            if(t.getTransactionType().equals(TransactionType.ItemReservation)){
                allReservedItems.add(t.getBorrowableItem()); 
            } 
            if(t.getTransactionType().equals(TransactionType.RoomReservation)){
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
        } 
        /*else if(userAccountRepository.findUserAccountByUserID(account.getUserID()) == null){
            throw new IllegalArgumentException("User does not exist!");
        }*/

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
        } 
        /*else if (borrowableItemRepository.findBorrowableItemByBarCodeNumber(item.getBarCodeNumber()) == null){
            throw new IllegalArgumentException("Item does not exist!");
        } */
        List<Transaction> allItemTransactions = transactionRepository.findByBorrowableItem(item);
        List<UserAccount> allWaitlistedUsers = new ArrayList<UserAccount>();
        for(Transaction t : allItemTransactions){
            if(t.getTransactionType().equals(TransactionType.Waitlist)){
                allWaitlistedUsers.add(t.getUserAccount());
            } 
        }
        return allWaitlistedUsers;
    }
//
   
    /**
     * Headlibrarian getters 
     * 1.get HeadLibrarian by ID (THROWS EXCPEPTION IF NOT EXIST)
     * @author Eloyann Roy-Javanbakht
     * 
     * */
    @Transactional
    public HeadLibrarian getHeadLibrarianFromUserId(int userID) throws Exception{
        HeadLibrarian headLibrarian=null;
        if(userID<1){
            throw new Exception("This User ID does not correspond to a Head Librarian");
        }
       try {
        headLibrarian= headLibrarianRepository.findHeadLibrarianByUserID(userID);
       } catch (Exception e) {
        throw new Exception("This User ID does not correspond to a Librarian");
       }
       return headLibrarian;
    }

     /**
     * Headlibrarian helper Method
     * boolean to ensure only and always  1  head librarian instance at any time
     * @author Eloyann Roy-Javanbakht
     * 
     * */ 
    @Transactional
    private boolean checkOnlyOneHeadLibrarian(){
        Iterable<HeadLibrarian> counter=headLibrarianRepository.findAll();
        ArrayList<HeadLibrarian> headLibrarians = new ArrayList<HeadLibrarian>();
        for (HeadLibrarian headLibrarian : counter) {
            headLibrarians.add(headLibrarian);
        }
        if(headLibrarians.size()!=1) return false;
        else return true;

    }



    /**
     * Headlibrarian getters 
     * get currentheadlibrarian in the system
     * @author Eloyann Roy-Javanbakht
     * 
     * */
    public HeadLibrarian getHeadLibrarian() throws Exception{
    
        try {
         Iterable<HeadLibrarian> headLibrarian;
         headLibrarian= headLibrarianRepository.findAll();
         if (headLibrarian==null) throw new Exception("There isn't any headLibrarian");

        for(HeadLibrarian head: headLibrarian){ return head;}
        } catch (Exception e) {
            throw new Exception("There isn't any headLibrarian");
        }
        return null;
 
 
          
     }
    /**
     * Headlibrarian getters 
     * get headlibrarian from fullName
     * @author Eloyann Roy-Javanbakht
     * *private will be made public
     * */
     private HeadLibrarian getHeadLibrarianFromFullName(String firstName, String lastName)throws Exception{
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
        
        if(!(librarian instanceof HeadLibrarian)){
            throw new Exception("the name privided does not correcpond to a Head librarian");
        }
        return (HeadLibrarian) librarian;

    }

     /**
     * Create a new Librarian 
     * Checks 1 : input validity
     * Checks 2 : theres is 0 HeadLibrarian in the system before adding this one
     * Calls on check if 
     * @author Eloyann Roy-Javanbakht
     */
    public HeadLibrarian createNewHeadLibrarian(String aFirstName, String aLastName, boolean aOnlineAccount, String aAddress, String aPassword, int aBalance , String aEmail)
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
        Iterable<HeadLibrarian> counter=headLibrarianRepository.findAll();
        ArrayList<HeadLibrarian> headLibrarians = new ArrayList<HeadLibrarian>();
        for (HeadLibrarian headLibrarian : counter) {
            headLibrarians.add(headLibrarian);
        }
        HeadLibrarian headLibrarian;
        if(headLibrarians.size()>0) throw new  Exception("There is already a HeadLibrarian AccountExisting");
   
        headLibrarian = new HeadLibrarian(aFirstName, aLastName, aOnlineAccount, aAddress, aPassword, aBalance, aEmail);
        librarianRepository.save(headLibrarian);
        
        return headLibrarian;
        
    }

    /**
     * Headlibrarian 
     * delete a HeadLibrarian object with user ID
     * @author Eloyann Roy-Javanbakht
     * */
    public HeadLibrarian deleteHeadLibrarian(int  userID)
    throws Exception {
       HeadLibrarian headLibrarian=getHeadLibrarian();
       HeadLibrarian thisone=getHeadLibrarianFromUserId(userID);
       if(thisone.getUserID()==(headLibrarian.getUserID())==false) throw new Exception("The UserID provided does not correspond to a  Head Librarian Account"); 
      
       headLibrarianRepository.delete(headLibrarian);
    
        return headLibrarian;
        
    }


     /**
     * Create a new Librarian 
     * Checks 1 : input validity
     * Checks 2 : theres creater is instance of HeadLibrarian before adding this one
     * Checks 3: that this user does not already have an account as a librarian
     * Calls on check if 
     * @author Eloyann Roy-Javanbakht
     * 
     * CHANGING TO STRING INPUT 
     * */
    public Librarian createANewLibrarian(int createrID, String aFirstName, String aLastName, boolean aOnlineAccount, String aAddress, String aPassword, int aBalance , String aEmail) throws Exception {
  
        String error = "";
        if ((aFirstName == null || aFirstName.trim().length() == 0)&& error.length()==0) {
            throw new  Exception("First Name  cannot be empty!");
        }
        if ((aLastName == null || aLastName.trim().length() == 0)&& error.length()==0) {
            throw new  Exception("Last Name  cannot be empty!");
        }
        if ((aAddress == null|| aAddress.trim().length() == 0)&& error.length()==0) {
            throw new  Exception("Address cannot be empty!");
        }
        if ((aPassword == null|| aPassword.trim().length() == 0) && aOnlineAccount == true && error.length()==0) {
            throw new  Exception("Password cannot be empty!");
        }
        if ((aEmail == null|| aEmail.trim().length() == 0) && aOnlineAccount == true && error.length()==0) {
            throw new  Exception("Email cannot be empty!");
        }


        try {
            if(!(getHeadLibrarianFromUserId(createrID) instanceof HeadLibrarian)){
                throw new  Exception("This User  does not the credentials to add a new librarian");
            }

        } catch (Exception e) {
            error = e.getMessage();
        }

        error = error.trim();
        if (error.length() > 0) {
            throw new IllegalArgumentException(error);
        }


        UserAccount librarianDuplicate = userAccountRepository.findByFirstNameAndLastName(aFirstName, aLastName);
            
        if(librarianDuplicate!=null) {
             throw new Exception("This User already has a librarian account");
        }
        
        Librarian librarian=new Librarian(aFirstName,aLastName, aOnlineAccount, aAddress, aPassword, aBalance, aEmail);

        librarianRepository.save(librarian);
        return librarian;

        
        

        
    }
     /**
     * Create a new Librarian from existing Patron Account
     * Checks 1 : input validity
     * Checks 2 : theres creater is instance of HeadLibrarian before adding this one
     * Checks 3: that this user does not already have an account as a librarian
     * Calls on check if 
     * @author Eloyann Roy-Javanbakht
     * not yet implemented currently put private
     * */
    private Librarian upgradePatronAccountToLibrarian(int  userIDHeadLibrarian, int userID) throws Exception {
        //checks credentials

        if(userIDHeadLibrarian!=getHeadLibrarian().getUserID())  
        throw new  Exception("This User  does not the credentials to add a new librarian");
          

        UserAccount librarian=null;
  

            librarian= getUserbyUserId(userID);
            if (librarian==null) throw new  Exception("This ID is not associated to an existing account");


       if((librarian instanceof Librarian) ){
            throw new Exception("the ID privided  correcponds already to a Staff Member");
        }
         
        if(!(librarian instanceof Patron) ){
            throw new Exception("the ID privided  does not correcponds to a Patron");
        }
        deleteAPatronbyUserID(userIDHeadLibrarian, userID);
        
       
        librarianRepository.save((Librarian)librarian);
         return (Librarian) librarian; 
      }

    
        /**Delete an instance of Librarian 
        * checks if user has credentials 
        *checks if it exists as a librarian
        * @author Eloyann Roy-Javanbakht
        */
    public Librarian deleteLibrarian(int  userIDHeadLibrarian, int userID) throws Exception {
        
      if(userIDHeadLibrarian!=getHeadLibrarian().getUserID())  
      throw new  Exception("This User  does not the credentials to add a new librarian");
      Librarian librarian=null;

           librarian= getLibrarianFromUserId(userID);
    

      if(librarian==null) throw new Exception ("This librarian does not exits");
      librarianRepository.delete(librarian);
      return librarian;
        
        
    }
        /**Librarian getters
        * 1. get librarian from full name
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

        /**Librarian getters
        * 1. get librarian from userID name
        * @author Eloyann Roy-Javanbakht
        */
       public Librarian getLibrarianFromUserId(int userID) throws Exception{
        
        
            Librarian librarian;
        
        if(userID<1){
                throw new Exception("This User ID does not correspond to a Librarian");
        }
            librarian= librarianRepository.findLibrarianByUserID(userID);
            
            if(librarian==null)   throw new Exception("This User ID does not correspond to a Librarian");
        
       
        return librarian;
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
    public List<TimeSlot> getAllTimeSlots() {
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
    public List<TimeSlot> getTimeSlotsFromLibrarian(int librarianUserID) {
        Librarian librarian = librarianRepository.findLibrarianByUserID(librarianUserID);
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
     * Get timeslot list (workshifts) for a specific librarian by inputing the librarian,s UserId
     * @author Mathieu Geoffroy
     * @param id - Librarian's user id
     * @return list of timeslots
     * checked
     * @throws Exception
     */
    @Transactional
    public List<TimeSlot> getTimeSlotsFromLibrarianUserID(int id) throws Exception {
        if (id < 1) throw new IllegalArgumentException("Invalid id");
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
    public TimeSlot getTimeSlotsFromId(int id) throws Exception{
        if (id < 1) throw new IllegalArgumentException("Invalid id");
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
    public TimeSlot createTimeSlot(int accountID, Date startDate, Time startTime, Date endDate, Time endTime) throws Exception {
        UserAccount account = userAccountRepository.findUserAccountByUserID(accountID);
        if (account == null) throw new IllegalArgumentException("Invalid account");
        if (!(account instanceof HeadLibrarian)) throw new IllegalArgumentException("Only a Head Librarian can create a new timeslot.");
        if (startDate == null) throw new IllegalArgumentException("Invalid startDate");
        if (startTime == null) throw new IllegalArgumentException("Invalid startTime");
        if (endDate == null) throw new IllegalArgumentException("Invalid endDate");
        if (endTime == null) throw new IllegalArgumentException("Invalid endTime");
        if (startDate.toLocalDate().isAfter(endDate.toLocalDate())) throw new IllegalArgumentException("StartDate cannot be after endDate");
        if (startTime.toLocalTime().isAfter(endTime.toLocalTime())) throw new IllegalArgumentException("StartTime cannot be after endTime");
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
    public TimeSlot assignTimeSlotToLibrarian(int accountId, int timeSlotID, int librarianUserId) {
        TimeSlot ts = timeSlotRepository.findTimeSlotByTimeSlotID(timeSlotID);
        Librarian librarian = librarianRepository.findLibrarianByUserID(librarianUserId);
        UserAccount account = userAccountRepository.findUserAccountByUserID(accountId);
        String error="";

        if (account == null) error = error + "Invalid account. ";
        else if (!(account instanceof HeadLibrarian)) error = error + "This User ID does not correspond to a Head Librarian. ";

        if (error.length() > 0) throw new IllegalArgumentException(error);
        if (ts == null) {
            error = error + "TimeSlot needs to be selected for registration! ";
        }
        if (librarian == null) {
            error = error + "Librarian needs to be selected for registration!";
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
     * @param timeslotID id of the timeslot to delete
     * @return true is deleted successfully
     * @throws Exception if invalid inputs
     * @throws Exception if user is not the head librarian
     * @author Mathieu Geoffroy
     */

    @Transactional
    public boolean deleteTimeSlot(int accountId, int timeslotID) throws Exception {
        UserAccount account = userAccountRepository.findUserAccountByUserID(accountId);
        String error = "";
        if (account == null) error = error + "Invalid account. ";
        else if (!(account instanceof HeadLibrarian)) error = error + "This User ID does not correspond to a Head Librarian. ";
        if (timeslotID < 1) error = error + "Invalid timeslotID. ";
        
        error = error.trim();

        if (error.length() > 0) throw new IllegalArgumentException(error);

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
    public OpeningHour getOpeningHourFromID(int id) throws IllegalArgumentException {
        if (id < 1) throw new IllegalArgumentException("Invalid id");
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
    public List<OpeningHour> getOpeningHoursFromHeadLibrarian(int headLibrarianUserId) {
        HeadLibrarian headLibrarian = headLibrarianRepository.findHeadLibrarianByUserID(headLibrarianUserId);
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
        if (startTime == null) throw new IllegalArgumentException("Invalid StartTime");
        if (endTime == null) throw new IllegalArgumentException("Invalid EndTime");
        HeadLibrarian headLibrarian = getHeadLibrarian();
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
    public boolean deleteOpeningHour(int accountId, int openingHourID) throws Exception{
        UserAccount account = userAccountRepository.findUserAccountByUserID(accountId);
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
        if(id < 1) throw new IllegalArgumentException("Invalid id");
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
    public List<Holiday> getHolidaysFromHeadLibrarian(int headLibrarianUserId) {
        HeadLibrarian headLibrarian = headLibrarianRepository.findHeadLibrarianByUserID(headLibrarianUserId);
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
        List<Holiday> holidays = holidayRepository.findByHeadLibrarian(headLibrarian);

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
    public Holiday createHoliday(int accountId, Date date, Time startTime, Time endTime) throws Exception{
        if (accountId < 1) throw new IllegalArgumentException("Invalid account id.");
        UserAccount account = userAccountRepository.findUserAccountByUserID(accountId);
        if (!(account instanceof HeadLibrarian)) throw new IllegalArgumentException("Account creating the holiday must be a head librarian.");
        if (date == null) throw new IllegalArgumentException("Invalid date.");
        if (startTime == null) throw new IllegalArgumentException("Invalid startTime.");
        if (endTime == null) throw new IllegalArgumentException("Invalid endTime.");
        if (startTime.toLocalTime().isAfter(endTime.toLocalTime())) throw new IllegalArgumentException("StartTime must be before endTime.");
        Holiday holiday = new Holiday(date, startTime, endTime, (HeadLibrarian) account);
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
    public boolean deleteHoliday(int accountId, int holidayID) throws Exception {
        UserAccount account = userAccountRepository.findUserAccountByUserID(accountId);
        String error = "";
        if (account == null) error = error + "Invalid account. ";
        if (holidayID < 1) error = error + "Invalid holidayID. ";
        error = error.trim();

        if (error.length() > 0) throw new IllegalArgumentException(error);

        getHeadLibrarianFromUserId(account.getUserID()); //throws error is account is not head librarian

        holidayRepository.delete(holidayRepository.findHolidayByHolidayID(holidayID));
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
	public Patron createPatron(int userID, String aFirstName, String aLastName, boolean aOnlineAccount, String aAddress, boolean aValidatedAccount, String aPassword, int aBalance, String aEmail) {
		
        String error = "";
        if (userID <= 0){
            throw new IllegalArgumentException("Invalid ID");
        }
        UserAccount creator = userAccountRepository.findUserAccountByUserID(userID);
        if ((aFirstName == null || aFirstName.trim().length() == 0)&& error.length() == 0) {
            throw new IllegalArgumentException("First Name cannot be empty!");
        }
        if ((aLastName == null || aLastName.trim().length() == 0)&& error.length() == 0) {
            throw new IllegalArgumentException("Last Name cannot be empty!");
        }
        if (aAddress == null|| aAddress.trim().length() == 0 && error.length() == 0) {
            throw new IllegalArgumentException("Address cannot be empty!");
        }
        if ((aPassword == null|| aPassword.trim().length() == 0) && aOnlineAccount == true && error.length() == 0) {
            throw new IllegalArgumentException("Password cannot be empty!");
        }
        if ((aEmail == null|| aEmail.trim().length() == 0) && aOnlineAccount == true && error.length() == 0) {
            throw new IllegalArgumentException("Email cannot be empty!");
        }
        if (creator == null) {
            throw new IllegalArgumentException("The creator does not exist");
        }
        if (creator instanceof Patron && aOnlineAccount == false) {
            throw new IllegalArgumentException("Only a Librarian can create an in-person account");
        }

        // the system will set the validity of the account to false, making sure that 
        // the user goes to validate whether they are a resident or not.
        if (aOnlineAccount == true){
            aValidatedAccount = false;
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

    /**
     * This method creates a new borrowable item in the system
     * @param item state
     * @param library item
     * @return the new borrowable item
     * @author Ramin Akhavan-Sarraf
     */
    @Transactional
	public BorrowableItem createBorrowableItem(String item, LibraryItem libraryItem) throws Exception{
        
        String error = "";
        if (item == null && error.length() == 0) {
            error = error + "Item state cannot be empty!";
        }
        if (libraryItem == null && error.length() == 0) {
            error = error + "Library item cannot be empty!";
        }

        error = error.trim();
        if (error.length() > 0) {
            throw new IllegalArgumentException(error);
        }
        ItemState itemState = ItemState.valueOf(item);
		BorrowableItem borrowableItem = new BorrowableItem(itemState, libraryItem);
        borrowableItemRepository.save(borrowableItem);
        return borrowableItem;
	}

    /**
     * This method creates a new library item in the system
     * @param name
     * @param itemType
     * @param date
     * @param creator
     * @param isViewable
     * @return the new library item
     * @author Ramin Akhavan-Sarraf
     */
    @Transactional
	public LibraryItem createLibraryItem(String name, String itemType, Date date, String creator, boolean isViewable) throws Exception{
        String error = "";
        if ((name == null || name.trim().length() == 0)&& error.length() == 0) {
            error = error + "Name cannot be empty!";
        }
        if (itemType == null && error.length() == 0) {
            error = error + "Item type cannot be empty!";
        }

        error = error.trim();
        if (error.length() > 0) {
            throw new IllegalArgumentException(error);
        }
		LibraryItem item = new LibraryItem(name, ItemType.valueOf(itemType), date, creator, isViewable);
        libraryItemRepository.save(item);
        return item;
	}

    /**
     * This method deletes an existing borrowable item
     * @param barCodeNumber
     * @return boolean
     * @throws Exception
     */
    @Transactional
    public boolean deleteBorrowableItem(int barCodeNumber) throws Exception {
        BorrowableItem borrowableItem = borrowableItemRepository.findBorrowableItemByBarCodeNumber(barCodeNumber);
        if(borrowableItem == null) {
        	throw new  Exception("This bar code number does not exist as a Borrowable Item");
        }
        else {
        	borrowableItemRepository.delete(borrowableItem);
        	return true;
        }
 
    }

    /**
     * This method deletes an existing library item
     * @param isbn
     * @return boolean
     * @throws Exception
     */
    @Transactional
    public boolean deleteLibraryItem(int isbn) throws Exception {
    	LibraryItem libraryItem = null;
    	libraryItem = libraryItemRepository.findByIsbn(isbn);
        if(libraryItem == null) {
        	throw new  Exception("This isbn does not exist as a Library Item");
        }
        else {
        	libraryItemRepository.delete(libraryItem);
        	return true;
        }
 
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
		String error = "";
		Patron person = patronRepository.findPatronByUserID(userID);
		

		if (person == null) {
			error += "This patron does not exist.";
		}
		
		 error = error.trim();
	     if (error.length() > 0) {
	    	 throw new IllegalArgumentException(error);
	     }
		return person;
	}
    
    /***
    * This method gets the patron object, given the userID, from a list of patrons in the database.
    * @author Zoya Malhi
    * @param firstName, lastName
    * @return null 
    added checks -elo
    checked
     * @throws Exception 
    */
    @Transactional
    public Patron getPatronFromFullName(String firstName, String lastName) throws Exception{
       		
        String error = "";
        if (firstName == null || firstName.trim().length() == 0) {
            error = error + "First Name cannot be empty!";
        }
        if (lastName == null || lastName.trim().length() == 0) {
            error = error + "Last Name cannot be empty!";
        }
       

        error = error.trim();
        if (error.length() > 0) {
            throw new IllegalArgumentException(error);
        }

        List<Patron> allPatrons = (List<Patron>) patronRepository.findAll();
        Patron patron = null;
        for(Patron p : allPatrons){
            if(p.getFirstName().equals(firstName) && p.getLastName().equals(lastName)){
                patron = p;
            }
        }
        if(patron != null) return patron;
        else throw new IllegalArgumentException("No patron found with this name! ");
       

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
    public boolean deleteAPatronbyUserID(int headID, int userID) throws Exception {
      UserAccount head = userAccountRepository.findUserAccountByUserID(headID);
    	if(!(head instanceof Librarian)){
            throw new  Exception("This user does not have the credentials to delete an existing patron");
        }

       String error = "";
            Patron patronAccount = patronRepository.findPatronByUserID(userID);
            patronRepository.delete(patronAccount);
            if (patronAccount ==null) {
            	error += "This user Id does not exist as a Patron";
            }
           
            error = error.trim();
     	     if (error.length() > 0) {
     	    	 throw new IllegalArgumentException(error);
     	     }
  return true;
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
        
    	 Iterable<Patron> allPatrons = patronRepository.findAll();
         List<Patron> patrons = new ArrayList<Patron>();
         String error = "";
         for(Patron patron: allPatrons){
             patrons.add(patron);
         }
         if(patrons.size()==0 || patrons == null){
             error = "There are no patrons in the system";
         }
        	
        	error = error.trim();
            if (error.length() > 0) {
                throw new IllegalArgumentException(error);
            }
			return patrons;
         
        	
         
        
    	
       
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
     * @param userID
     * @return boolean 
     */
    
    public UserAccount changePassword(String aPassWord, int userID){
        if (userID <= 0){
            throw new IllegalArgumentException("Invalid ID");
        }
        UserAccount account = userAccountRepository.findUserAccountByUserID(userID);
        if (account == null){
            throw new IllegalArgumentException("The patron does not exist");
        }
        if (account.getOnlineAccount() == false){
            throw new IllegalArgumentException("The account must be an online account");
        }
        if (aPassWord == null|| aPassWord.trim().length() == 0) {
            throw new IllegalArgumentException("Password cannot be empty!");
        }
        if (aPassWord == account.getPassword()){
            throw new IllegalArgumentException("This is already your password.");
        }

        account.setPassword(aPassWord);
        userAccountRepository.save(account);
        if(account instanceof Librarian){
            librarianRepository.save((Librarian)account);
            if(account instanceof HeadLibrarian){
                headLibrarianRepository.save((HeadLibrarian)account);
            }
        }
        else{
            patronRepository.save((Patron)account);
        }
        return account;
    }

    /**
     * @author Gabrielle Halpin
     * This method allows the user to change their firstName
     * @param aFirstName
     * @param userID
     * @return Useraccount account
     */
    public UserAccount changeFirstName(String aFirstName, int userID){
        if (userID <= 0){
            throw new IllegalArgumentException("Invalid ID");
        }
        UserAccount account = userAccountRepository.findUserAccountByUserID(userID);
        if (account == null){
            throw new IllegalArgumentException("The patron does not exist"); 
        }
        if (aFirstName == null|| aFirstName.trim().length() == 0) {
            throw new IllegalArgumentException("firstName cannot be empty!"); 
        }
        if (aFirstName == account.getFirstName()){
            throw new IllegalArgumentException("This is already your firstName.");
        }

        account.setFirstName(aFirstName);
        userAccountRepository.save(account);
        if(account instanceof Librarian){
            librarianRepository.save((Librarian)account);
            if(account instanceof HeadLibrarian){
                headLibrarianRepository.save((HeadLibrarian)account);
            }
        }
        else{
            patronRepository.save((Patron)account);
        }
        return account;
    }

    /**
     * @author Gabrielle Halpin
     * This method allows the user to change their lastName
     * @param aLastName
     * @param userID
     * @return Useraccount account
     */
    public UserAccount changeLastName(String aLastname, int userID){
        if (userID <= 0){
            throw new IllegalArgumentException("Invalid ID");
        }
        UserAccount account = userAccountRepository.findUserAccountByUserID(userID);
        if (account == null){
            throw new IllegalArgumentException("The patron does not exist");
        }
        if (aLastname == null|| aLastname.trim().length() == 0) {
            throw new IllegalArgumentException("lastname cannot be empty!");
        }
        if (aLastname == account.getLastName()){
            throw new IllegalArgumentException("This is already your lastname.");
        }

        account.setLastName(aLastname);
        userAccountRepository.save(account);
        if(account instanceof Librarian){
            librarianRepository.save((Librarian)account);
            if(account instanceof HeadLibrarian){
                headLibrarianRepository.save((HeadLibrarian)account);
            }
        }
        else{
            patronRepository.save((Patron)account);
        }
        return account;
    }

    /**
     * @author Gabrielle Halpin
     * This method allows the user to change their address 
     * @param aAddress
     * @param userID
     * @return Useraccount account
     */
    public UserAccount changeAddress(String aAddress, int userID){
        if (userID <= 0){
            throw new IllegalArgumentException("Invalid ID");
        }
        UserAccount account = userAccountRepository.findUserAccountByUserID(userID);
        if (account == null ){
            throw new IllegalArgumentException("The patron does not exist"); 
        }
        if (aAddress == null|| aAddress.trim().length() == 0) {
            throw new IllegalArgumentException("Address cannot be empty!");
        }
        if (aAddress == account.getAddress()){
            throw new IllegalArgumentException("This is already your Address.");
        }

        account.setAddress(aAddress);
        userAccountRepository.save(account);
        if(account instanceof Librarian){
            librarianRepository.save((Librarian)account);
            if(account instanceof HeadLibrarian){
                headLibrarianRepository.save((HeadLibrarian)account);
            }
        }
        else{
            patronRepository.save((Patron)account);
        }
        return account;
    }

    /**
     * @author Gabrielle Halpin
     * This method allows the user to change their email 
     * @param aEmail
     * @param userID
     * @return Useraccount account
     */
    public UserAccount changeEmail(String aEmail, int userID){
        if (userID <= 0){
            throw new IllegalArgumentException("Invalid ID");
        }
        UserAccount account = userAccountRepository.findUserAccountByUserID(userID);
        if (account == null){
            throw new IllegalArgumentException("The patron does not exist");
        }
        if (account.getOnlineAccount() == false){
            throw new IllegalArgumentException("The account must be an online account");
        }
        if (aEmail == null|| aEmail.trim().length() == 0) {
            throw new IllegalArgumentException("Email cannot be empty!");
        }
        if (aEmail == account.getEmail()){
            throw new IllegalArgumentException("This is already your Email.");
        }

        account.setEmail(aEmail);
        userAccountRepository.save(account);
        if(account instanceof Librarian){
            librarianRepository.save((Librarian)account);
            if(account instanceof HeadLibrarian){
                headLibrarianRepository.save((HeadLibrarian)account);
            }
        }
        else{
            patronRepository.save((Patron)account);
        }
        return account;
    }

    /**
     * This mathod is called when the Librarian set's a customer's account to an online account
     * @author Gabrielle Hapin
     * @param userID
     * @param aEmail
     * @param aPassword
     * @param aOnlineAccount
     * @param creatorID
     * @return UserAccount
     */
    public UserAccount setOnlineAccount(int userID, String aEmail, String aPassword, boolean aOnlineAccount, int creatorID){
        if (userID <= 0){
            throw new IllegalArgumentException("Invalid ID");
        }
        UserAccount account = userAccountRepository.findUserAccountByUserID(userID);
        if (account == null ){
            throw new IllegalArgumentException("The patron does not exist");
        }
        if (account.getOnlineAccount() == true ){
            throw new IllegalArgumentException("The account is already an online account.");
        }
        if ((aPassword == null|| aPassword.trim().length() == 0) && aOnlineAccount == true ) {
            throw new IllegalArgumentException("Password cannot be empty!");
        }
        if ((aEmail == null|| aEmail.trim().length() == 0) && aOnlineAccount == true ) {
            throw new IllegalArgumentException("Email cannot be empty!");
        }
        if (creatorID <= 0){
            throw new IllegalArgumentException("Invalid ID");
        }
        UserAccount creator = userAccountRepository.findUserAccountByUserID(creatorID);
        if (creator == null ){
            throw new IllegalArgumentException("The creator does not exist");
        }
        if (!(creator instanceof Librarian)){
            throw new IllegalArgumentException("The creator must be a librarian");
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
     * @param patronID
     * @param validated
     * @param creatorID
     * @return PatronDTO
     * @throws Exception
     */
    public Patron setValidatedAccount(int patronID, boolean validated, int creatorID) throws Exception{
        if (patronID <= 0){
            throw new IllegalArgumentException("Invalid ID");
        }
        Patron patron = patronRepository.findPatronByUserID(patronID);
        if (creatorID <= 0){
            throw new IllegalArgumentException("Invalid ID");
        }
        UserAccount creator = userAccountRepository.findUserAccountByUserID(creatorID);
        if (creator == null){
            throw new IllegalArgumentException("The creator does not exist");
        }
        if (!(creator instanceof Librarian)){
            throw new IllegalArgumentException("Only a Librarian can change the validity of an account");
        }
        if (patron == null){
            throw new IllegalArgumentException("The patron does not exist");
        }
        else {
        	
        	patron.setValidatedAccount(validated);
        }
        userAccountRepository.save(patron);
        patronRepository.save(patron);
            return patron;
            
//        try {
//            
//           } catch (Exception e) {
//            throw new Exception("This user does not exists in the database.");
//        }
           
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
        for(UserAccount user: allusers){
            users.add(user);
        }
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
	   String error = "";
	   List<Librarian> list = new ArrayList();
	   list = toList(librarianRepository.findAll());
	   if (list == null && list.size() == 0) {
   		error += "There are no librarians in the database.";
	   	}
       error = error.trim();
	     if (error.length() > 0) {
	    	 throw new IllegalArgumentException(error);
	     }
		return list;
    }

}



