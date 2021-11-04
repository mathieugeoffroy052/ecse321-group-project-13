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
import org.springframework.web.servlet.FlashMapManager;

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

    // ASSUMPTION : There exists only 1 instance of the LibraryService in the database, and that instance is used in the following business methods

    /** 
     * @param isbn
     * @return List<BorrowableItem> - list of borrowable items with given isbn
     * @author Amani Jammoul
     */
    @Transactional
    public List<BorrowableItem> getBorrowableItemsFromItemIsbn(int isbn){
        LibraryItem item = libraryItemRepository.findByIsbn(isbn);
        List<BorrowableItem> allBorrowableItems = borrowableItemRepository.findByLibraryItem(item);
        return allBorrowableItems;
    }

    
    /** 
     * @return List<LibraryItem> - all library items in library system
     * @throws Exception
     * @author Amani Jammoul
     */
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

    
    /** 
     * @param authorName
     * @return LibraryItem - book written by the author
     * @throws Exception
     * @author Amani Jammoul
     */
    @Transactional
    public LibraryItem getBookFromAuthor(String authorName) throws Exception{
        List<LibraryItem> allBooks = getAllBooks();
        for(LibraryItem a : allBooks){
            if(a.getCreator().equals(authorName)) return a;
        }
        return null;
    }

    
    /** 
     * @param bookTitle
     * @return LibraryItem - book with the given title name
     * @throws Exception
     * @author Amani Jammoul
     */
    @Transactional
    public LibraryItem getBookFromTitle(String bookTitle) throws Exception{
        List<LibraryItem> allBooks = getAllBooks();
        for(LibraryItem a : allBooks){
            if(a.getName().equals(bookTitle)) return a;
        }
        return null;
    }

    
    /** 
     * @return List<LibraryItem> - list of all music items
     * @throws Exception
     * @author Amani Jammoul
     */
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

    
    /** 
     * @param artistName 
     * @return LibraryItem - music item created by artist
     * @throws Exception
     * @author Amani Jammoul
     */
    @Transactional
    public LibraryItem getMusicFromArtist(String artistName) throws Exception{
        List<LibraryItem> allMusic = getAllMusic();
        for(LibraryItem a : allMusic){
            if(a.getCreator().equals(artistName)) return a;
        }
        return null;
    }

    
    /** 
     * @param musicTitle
     * @return LibraryItem - music item with given title
     * @throws Exception
     * @author Amani Jammoul
     */
    @Transactional
    public LibraryItem getMusicFromTitle(String musicTitle) throws Exception{
        List<LibraryItem> allMusic = getAllMusic();
        for(LibraryItem a : allMusic){
            if(a.getName().equals(musicTitle)) return a;
        }
        return null;
    }

    
    /** 
     * @return List<LibraryItem> - list of all movie items
     * @throws Exception
     * @author Amani Jammoul
     */
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

    
    /** 
     * @param directorName
     * @return LibraryItem - movie item created by the director
     * @throws Exception
     * @author Amani Jammoul
     */
    @Transactional
    public LibraryItem getMovieFromDirector(String directorName) throws Exception{
        List<LibraryItem> allMovies = getAllMovies();
        for(LibraryItem a : allMovies){
            if(a.getCreator().equals(directorName)) return a;
        }
        return null;
    }

    
    /** 
     * @param movieTitle
     * @return LibraryItem - movie item with the given title
     * @throws Exception
     * @author Amani Jammoul
     */
    @Transactional
    public LibraryItem getMovieFromTitle(String movieTitle) throws Exception{
        List<LibraryItem> allMovies = getAllMovies();
        for(LibraryItem a : allMovies){
            if(a.getName().equals(movieTitle)) return a;
        }
        return null;
    }

    /**
     * Get all room reservations that are in the system
     * @throws if there is no library system found
     * @returns a list of room reservations
     * @author Ramin Akhavan-Sarraf 
     */

    @Transactional
    public List<LibraryItem> getAllRoomReservations() throws Exception{
        LibrarySystem library;
        try{
            library = (LibrarySystem) librarySystemRepository.findAll().iterator().next(); // uses the first library system found in the database
        }catch(NoSuchElementException e){
            throw new Exception("No library system(s) exist in the database");
        }
        List<LibraryItem> allLibraryItems = libraryItemRepository.findByLibrarySystem(library);
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
     */
    @Transactional
    public List<LibraryItem> getAllNewspapers() throws Exception{
        LibrarySystem library;
        try{
            library = (LibrarySystem) librarySystemRepository.findAll().iterator().next(); // uses the first library system found in the database
        }catch(NoSuchElementException e){
            throw new Exception("No library system(s) exist in the database");
        }
        List<LibraryItem> allLibraryItems = libraryItemRepository.findByLibrarySystem(library);
        List<LibraryItem> allNewspapers = new ArrayList<>();
        for(LibraryItem libItem : allLibraryItems){
            if (libItem.getType() == ItemType.NewspaperArticle){
                allNewspapers.add(libItem);
            }
        }
        return allNewspapers;
    }

    /**
     * Get a newspaper based on the title of the article
     * @param newspaper article title
     * @returns a newspaper
     * @author Ramin Akhavan-Sarraf
     */
    @Transactional
    public LibraryItem getNewspaperFromTitle(String newspaperTitle) throws Exception{
        List<LibraryItem> allNewspapers = getAllNewspapers();
        for(LibraryItem newspaper : allNewspapers){
            if(newspaper.getName().equals(newspaperTitle)) return newspaper;
        }
        return null;
    }

    /**
     * Get a newspaper based on the writer of the article
     * @param newspaper article writer
     * @returns a newspaper
     * @author Ramin Akhavan-Sarraf
     */
    @Transactional
    public LibraryItem getNewspaperFromWriter(String writerName) throws Exception{
        List<LibraryItem> allNewspapers = getAllNewspapers();
        for(LibraryItem newspaper : allNewspapers){
            if(newspaper.getCreator().equals(writerName)) return newspaper;
        }
        return null;
    }

    
    /** 
     * Creates an item reservation transaction between a user account and a borrowable item
     * @param item
     * @param account
     * @return Transaction - Type : ItemReservation
     * @author Amani Jammoul
     */
    @Transactional
    public Transaction createItemReserveTransaction(BorrowableItem item, UserAccount account){
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
     * @return Transaction - Type : RoomReservation
     * @author Amani Jammoul
     */
    @Transactional
    public Transaction createRoomReserveTransaction(BorrowableItem item, UserAccount account){
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
     */
    @Transactional
    public Transaction createItemBorrowTransaction(BorrowableItem item, UserAccount account){
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
     */
    @Transactional
    public Transaction createItemReturnTransaction(BorrowableItem item, UserAccount account){
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
     */
    @Transactional
    public Transaction createItemWaitlistTransaction(BorrowableItem item, UserAccount account){
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
        LocalDate localDeadline = LocalDate.now().plusDays(20); // Deadline is set 20 days away from current day
        Date deadline = Date.valueOf(localDeadline);
        Transaction itemReservation = new Transaction(item, account, TransactionType.Renewal, deadline); 
        transactionRepository.save(itemReservation);
        return itemReservation;
    }

    
    /** 
     * @param account
     * @return List<BorrowableItem> - list of all items borrowed (checked out) by a user
     * @author Amani Jammoul
     */
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
    
    /** 
     * @param account
     * @return List<BorrowableItem> - list of all items reserved for a user
     * @author Amani Jammoul
     */
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

    
    /** 
     * @param item
     * @return List<UserAccount> - list of all user accounts that are on the waitlist for an item
     * @author Amani Jammoul
     */
    @Transactional
    public List<UserAccount> getUsersOnWaitlist(BorrowableItem item){
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
    
      
         Iterable<HeadLibrarian> headlibrarians = headLibrarianRepository.findAll();
       
        for (HeadLibrarian n: headlibrarians){
            if(n.getFirstName().equals(firstName) && n.getLastName().equals(lastName)) return n;

        }
        return null;

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
    /**HeadLibrarian Create and Replace
     * 1. Create A Headlibrarian-- checks if has or not 
     * 2. Replacing the current headlibrarian with a new one
     * 3. Create Librarian--checks the user creating it is a head librarian
     * 4. remove LIbrarian--checks the user deleting it is a head librarian
     * @author Eloyann Roy-Javanbakht
     */


    public boolean CreateANewHeadLibrarian(String aFirstName, String aLastName, boolean aOnlineAccount, LibrarySystem aLibrarySystem, String aAddress, String aPassword, int aBalance )
    throws Exception {
        HeadLibrarian headLibrarian;
      if(checkOnlyOneHeadLibrarian()) throw new  Exception("This User  does not the credentials to add a new librarian");
   
      headLibrarian=new HeadLibrarian(aFirstName, aLastName, aOnlineAccount, aLibrarySystem, aAddress, aPassword, aBalance);

       librarianRepository.save(headLibrarian);
    
        return true;
        
    }

    public boolean ReplaceHeadLibrarian(UserAccount current, String aFirstName, String aLastName, boolean aOnlineAccount, LibrarySystem aLibrarySystem, String aAddress, String aPassword, int aBalance )
    throws Exception {
       HeadLibrarian headLibrarian=getHeadLibrarian();
       if(current.equals(headLibrarian)) 
      headLibrarianRepository.delete(headLibrarian);

      headLibrarian=new HeadLibrarian(aFirstName, aLastName, aOnlineAccount, aLibrarySystem, aAddress, aPassword, aBalance);
      librarianRepository.save(headLibrarian);
    
        return true;
        
    }




    //librarian create
    public Librarian createANewLibrarian(UserAccount creater, String aFirstName, String aLastName, boolean aOnlineAccount, LibrarySystem aLibrarySystem, String aAddress, String aPassword, int aBalance ) throws Exception {
        
        try {
        getHeadLibrarianFromUserId(creater.getUserID());

        } catch (Exception e) {
            throw new  Exception("This User  does not the credentials to add a new librarian");
        }
        if(getLibrarianFromFullName(aFirstName, aLastName).getAddress().equals(aAddress)) throw new Exception("This User already has a librarian account");
        Librarian librarian=new Librarian(aFirstName, aLastName, aOnlineAccount, aLibrarySystem, aAddress, aPassword, aBalance);
        librarianRepository.save(librarian);
        return librarian;

    

        
    }



        //librarian delete
    public Librarian deleteALibrarian(UserAccount creater, String aFirstName, String aLastName, boolean aOnlineAccount, LibrarySystem aLibrarySystem, String aAddress, String aPassword, int aBalance ) throws Exception {
    
        try {
        getHeadLibrarianFromUserId(creater.getUserID());

        } catch (Exception e) {
            throw new  Exception("This User  does not the credentials to add a new librarian");
        }

        Librarian librarian=new Librarian(aFirstName, aLastName, aOnlineAccount, aLibrarySystem, aAddress, aPassword, aBalance);
        librarianRepository.delete(librarian);
        return librarian;
    }



   /**Librarian getters
    * 1. get librarian from name
    * 2. get librarian from ID
    * 3. get all librarians
    * @author Eloyann Roy-Javanbakht
    */
   public Librarian getLibrarianFromFullName(String firstName, String lastName){
        
    Iterable<Librarian> librarians = librarianRepository.findAll();
  
   for (Librarian n: librarians){
       if(n.getFirstName().equals(firstName) && n.getLastName().equals(lastName)) return n;

   }
  return null;

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


    /***
     * This method creates and save the object in the database and returns the Librarian object
     * @author Gabrielle Halpin
     * @param aFirstName
     * @param aLastName
     * @param aOnlineAccount
     * @param aLibrarySystem
     * @param aAddress
     * @param aPassword
     * @param aBalance
     * @return librarian
     * 
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
     * @author gabrielle Halpin
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
     * @author Gabrielle Halpin
     * @param librarianID
     * @return librarian
     */
    @Transactional
	public UserAccount getLibrarian(int userID) {
		UserAccount librarian = userAccountRepository.findUserAccountByUserID(userID);
		return librarian;
	}

    /**
     * This method creates the Patron object and stores it in the database
     * @author Gabrielle Halpin
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
     * This method gets the patron object from the database
     * @author Gabrielle halpin
     * @param userID
     * @return person 
     */
    @Transactional
	public Patron getPatron(int userID) {
		Patron person = patronRepository.findPatronByUserID(userID);
		return person;
	}
    
    /***
     * This method gets the patron object from the database
     * @author Zoya Malhi
     * @param firstname, lastName
     * @return null 
     */
    public Patron getIfPatronFromFullName(String firstName, String lastName){
        Iterable<Patron> patron = patronRepository.findAll();
        for (Patron p: patron){
           if(p.getFirstName().equals(firstName) && p.getLastName().equals(lastName)) return p;
       }
       return null;

   }

    /***
     * This returns a list of all users associated to a specific account
     * @author Gabrielle Halpin
     * @param userID
     * @return users 
     */
    @Transactional
	public List<UserAccount> getAllUsers(LibrarySystem librarySystem) {
		List<UserAccount> users = userAccountRepository.findByLibrarySystem(librarySystem);
		return users;
	}


   public Iterable<Librarian>  getLibrarians() throws Exception{
    
       try {
        Iterable<Librarian> librarian;
        librarian= librarianRepository.findAll();
       return librarian;
       } catch (Exception e) {
        throw new Exception("This User ID does not correspond to a Head Librarian");
       }
    
          
    }
   

   /***
    * This returns the opening hour corresponding to the id
    * @author Zoya Malhi
    * @param hourID
    * @return openingHour 
    */
   @Transactional
   public OpeningHour getOpeningHourFromHourID(int hourID){
	   OpeningHour openingHour = openingHourRepository.findOpeningHourByHourID(hourID);
	   return openingHour;
   }
   
   

}


