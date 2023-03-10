package ca.mcgill.ecse321.libraryservice.controller;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.sql.Date;
import java.util.*;
import java.util.stream.Collectors;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ca.mcgill.ecse321.libraryservice.dto.*;
import ca.mcgill.ecse321.libraryservice.model.*;
import ca.mcgill.ecse321.libraryservice.model.LibraryItem.ItemType;
import ca.mcgill.ecse321.libraryservice.service.LibraryServiceService;

@CrossOrigin(origins = "*")
@RestController
public class LibraryServiceRestController {
    @Autowired
    private LibraryServiceService service;

    // * Holiday methods
    /**
     * get all holidays
     * 
     * @return list of all holidays DTO
     * @author Mathieu Geoffroy
     * @throws Exception when there is no library system
     */
    @GetMapping(value = { "/holiday/viewall", "/holiday/viewall/" })
    public List<HolidayDTO> getAllHolidays() throws Exception {
        return service.getAllHolidays().stream().map(p -> convertToDto(p)).collect(Collectors.toList());
    }

    /**
     * view a specific holiday
     * 
     * @param id
     * @return holiday dto
     * @author Mathieu Geoffroy
     */
    @GetMapping(value = { "/holiday/view/{holidayID}", "/holiday/view/{holidayID}/" })
    public HolidayDTO getHolidayById(@PathVariable(name = "holidayID") int id) {
        return convertToDto(service.getHolidayFromId(id));
    }

    /**
     * create new holiday
     * 
     * @param holidayDTO
     * @return holiday DTO
     * @throws Exception
     * @author Mathieu Geoffroy
     */
    @PostMapping(value = { "/holiday/new", "/holiday/new/" })
    public HolidayDTO createHoliday(@RequestParam int currentUserID, @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE, pattern="yyyy-MM-dd") LocalDate date, @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.TIME, pattern="HH:mm") LocalTime startTime, @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.TIME, pattern="HH:mm") LocalTime endTime) throws Exception {
        return convertToDto(
                service.createHoliday(currentUserID, Date.valueOf(date), Time.valueOf(startTime), Time.valueOf(endTime)));
    }

    /**
     * delete holiday based on current account
     * @param holidayID
     * @param accountID
     * @return true when deleted successfully
     * @throws Exception
     */
    @DeleteMapping(value = {"/holiday/delete", "/holiday/delete/"})
    public boolean deleteHoliday(@RequestParam (name = "holidayID") Integer holidayID, @RequestParam (name = "accountID") Integer accountID) throws Exception {
        return service.deleteHoliday(accountID, holidayID);
    }

    // * Opening hour methods
    /**
     * get all opening hours
     * 
     * @return list of opening hours DTO
     * @throws Exception when there is no library system
     * @author Mathieu Geoffroy
     */
    @GetMapping(value = { "/openinghour/viewall", "/openinghour/viewall/" })
    public List<OpeningHourDTO> getAllOpeningHours() throws Exception {
        return service.getAllOpeningHours().stream().map(p -> convertToDto(p)).collect(Collectors.toList());
    }

    /**
     * view a specific opening hour
     * 
     * @param id
     * @return openinghourDTo
     * @author Mathieu Geoffroy
     */
    @GetMapping(value = { "/openinghour/view/id", "/openinghour/view/{openinghourID}" })
    public OpeningHourDTO getOpeningHourById(@RequestParam(name = "openinghourID") int id) {
        return convertToDto(service.getOpeningHourFromID(id));
    }

    /**
     * view a specific opening hour for day
     * 
     * @param dayofweek string with day of the week
     * @return openinghourDTo
     * @author Mathieu Geoffroy
     */
    @GetMapping(value = { "/openinghour/view/day", "/openinghour/view/day/" })
    public OpeningHourDTO getOpeningHourByDay(@RequestParam(name = "dayofweek") String day) throws Exception {
        return convertToDto(service.getOpeningHoursByDayOfWeek(day).get(0)); // should only be 1 opening hour per day
    }

    /**
     * create new opening hour
     * 
     * @param openingHourDTO
     * @return openinghour DTO
     * @throws Exception
     * @author Mathieu Geoffroy
     */
    @PostMapping(value = { "/openinghour/new", "/openinghour/new/" })
    public OpeningHourDTO createOpeningHour(@RequestParam (name = "day") String day, @RequestParam (name = "startTime") @DateTimeFormat(iso = DateTimeFormat.ISO.TIME, pattern="HH:mm") LocalTime startTime, @RequestParam (name = "endTime") @DateTimeFormat(iso = DateTimeFormat.ISO.TIME, pattern="HH:mm") LocalTime endTime) throws Exception {
        return convertToDto(service.createOpeningHour(day, Time.valueOf(startTime), Time.valueOf(endTime)));
    }

    /**
     * delete openinghour based on current account
     * @param holidayID
     * @param accountID
     * @return true when deleted successfully
     * @throws Exception
     */
    @DeleteMapping(value = {"/openinghour/delete", "/openinghour/delete/"})
    public boolean deleteOpeningHour(@RequestParam (name = "openinghourID") Integer openinghourID, @RequestParam (name = "accountID") Integer accountID) throws Exception {
        return service.deleteOpeningHour(accountID, openinghourID);
    }

    // * TimeSlot methods
    /**
     * get all timeslots
     * 
     * @return list of timeslot DTO
     * @throws Exception when there is no library system
     * @author Mahtieu Geoffroy
     */
    @GetMapping(value = { "/timeslot/viewall", "/timeslot/viewall/" })
    public List<TimeslotDTO> getAllTimeSlots() throws Exception {
        return service.getAllTimeSlots().stream().map(p -> convertToDto(p)).collect(Collectors.toList());
    }

    /**
     * returna specific timeslot for a specified user
     * @author Mathieu Geoffroy
     * @return list of specific timslots
     */
    @GetMapping(value = {"timeslot/view/librarianID/{userID}", "timeslot/view/librarianID/{userID}/"})
    public List<TimeslotDTO> getTimeslotByLibrarianID(@PathVariable (name = "userID") int userID) throws Exception {
        return service.getTimeSlotsFromLibrarian(userID).stream().map(p -> convertToDto(p)).collect(Collectors.toList());
    }

    /**
     * assign a librarian to a timeslot
     * 
     * @param timeslotDTO
     * @param librarianDTO
     * @return the timeslot dto with the assigned librarian
     * @throws Exception
     * @author Mathieu Geoffroy
     */
    @PutMapping(value = { "/timeslot/assign", "/timeslot/assign/" })
    public TimeslotDTO assignTimeSlot(@RequestParam(name = "timeslotID") int timeslotID,
            @RequestParam(name = "librarianID") int librarianID,
            @RequestParam(name = "currentUserID") int accountID) throws Exception {
        return convertToDto(service.assignTimeSlotToLibrarian(accountID, timeslotID, librarianID));
    }

    /**
     * get timeslots for a specific librarian
     * 
     * @param first name of librarian
     * @param last  name of librarian
     * @return list of timeslotDTO
     * @throws Exception when invalid inputs, or invalid librarian id
     * @author Mathieu Geoffroy
     */
    @GetMapping(value = { "/timeslot/view/{firstName}/{lastName}", "/timeslot/view/{firstName}/{lastName}/" })
    public List<TimeslotDTO> getTimeSlotForLibrarian(@PathVariable(name = "firstName") String first,
            @PathVariable(name = "lastName") String last) throws Exception {
        ArrayList<TimeSlot> list = new ArrayList<TimeSlot>();
        for (TimeSlot t : service.getAllTimeSlots()) {
            for (Librarian l : t.getLibrarian()) {
                if (l.getFirstName().equals(first) && l.getLastName().equals(last)) {
                    list.add(t);
                }
            }
        }
        return list.stream().map(p -> convertToDto(p)).collect(Collectors.toList());
    }

    /**
     * view s singular timeslot
     * 
     * @param timeslotID
     * @return the timeslot
     * @author Mathieu Geoffroy
     */
    @GetMapping(value = { "/timeslot/view/id", "/timeslot/view/id/" })
    public TimeslotDTO getTimeslotById(@RequestParam(name = "timeslotID") int timeslotID) throws Exception {
        return convertToDto(service.getTimeSlotsFromId(timeslotID));
    }

    /**
     * create a new timeslot
     * 
     * @param timeslotDto
     * @return the timeslotdto created
     * @throws Exception
     * @author Mathieu Geoffroy
     */
    @PostMapping(value = { "/timeslot/new", "timeslot/new/" })
    public TimeslotDTO createTimeslot(@RequestParam (name = "currentUserID") int accountID, @RequestParam (name = "startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE, pattern="yyyy-MM-dd") LocalDate startDate, @RequestParam (name = "startTime") @DateTimeFormat(iso = DateTimeFormat.ISO.TIME, pattern="HH:mm") LocalTime startTime, @RequestParam (name = "endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE, pattern="yyyy-MM-dd") LocalDate endDate, @RequestParam (name = "endTime") @DateTimeFormat(iso = DateTimeFormat.ISO.TIME, pattern="HH:mm") LocalTime endTime) throws Exception {
        TimeSlot timeslot = service.createTimeSlot(accountID, Date.valueOf(startDate), Time.valueOf(startTime), Date.valueOf(endDate), Time.valueOf(endTime));
        return convertToDto(timeslot);
    }

    /**
     * delete timeslot based on current account
     * @param timeslotID
     * @param accountID
     * @author Mathieu Geoffroy
     * @return true when deleted successfully
     * @throws Exception
     */
    @DeleteMapping(value = {"/timeslot/delete", "/timeslot/delete/"})
    public boolean deleteTimeSlot(@RequestParam (name = "timeslotID") int timeslotID, @RequestParam (name = "accountID") int accountID) throws Exception {
        return service.deleteTimeSlot(accountID, timeslotID);
    }

    /**
     * This methods gets all users for the database
     * 
     * @author Gabrielle Halpin and Amani Jammoul
     * @return userDTOs
     * @throws Exception
     */
    @GetMapping(value = { "/accounts", "/accounts/" })
    public List<UserAccountDTO> getAllUsers() throws Exception{
            List<UserAccountDTO> userDtos = new ArrayList<>();
            for (UserAccount users : service.getAllUsers()) {
                userDtos.add(convertToDto(users));
            }
            return userDtos;
    }

    /**
     * This methods gets all patrons for the database
     * 
     * @author Gabrielle Halpin
     * @return patrons
     * @throws Exception
     */
    @GetMapping(value = { "/patrons", "/patrons/" })
    public List<UserAccountDTO> getAllPatrons() throws Exception {
        List<UserAccountDTO> patrons = new ArrayList<>();
        for (UserAccount users : service.getAllPatrons()) {
            patrons.add(convertToDto(users));
        }
        return patrons;

    }

    /**
     * This methods gets all librarians for the database
     * 
     * @author Gabrielle Halpin
     * @return patrons
     * @throws Exception
     */
    @GetMapping(value = { "/librarians", "/librarians/" })
    public List<UserAccountDTO> getAllLibrarians() throws Exception {
        List<UserAccountDTO> librarians = new ArrayList<>();
        for (UserAccount users : service.getAllLibrarians()) {
            librarians.add(convertToDto(users));
        }
        return librarians;

    }

    /**
     * @author Gabrielle Halpin Delete a business information
     * @param userID
     * @param headLibrarian
	 * @return patronDTO
	 */
	@DeleteMapping(value = {"/deletePatron/{userID}","/deletePatron/{userID}/"})
	public boolean deletePatron(@PathVariable("userID") int userID, @RequestParam int headLibrarianID) throws Exception{
		return service.deleteAPatronbyUserID(headLibrarianID, userID);
	
	}


    /**
     * This method uses the getPatronByUserId to retrieve a Patron from the database
     * using their unique userID
     * 
     * @author Gabrielle Halpin
     * @param userID
     * @return PatronDTO
     * @throws IllegalArgumentException
     */
    @GetMapping(value = { "/patron/{userID}", "/patron/{userID}/" })
    public PatronDTO getPatronByUserId(@PathVariable("userID") int userID) throws Exception {
        return convertToDto(service.getPatronByUserId(userID));
    }

    /**
     * This methods gets a patron from their firstname and last name
     * 
     * @author Gabrielle Halpin
     * @param firstName
     * @param lastName
     * @return PatronDTO
     * @throws Exception
     */
    @GetMapping(value = { "/patrons/{firstName}/{lastName}", "/patrons/{firstname}/{lastName}/" })
    public PatronDTO getPatronFromFullName(@PathVariable("firstName") String firstName,
            @PathVariable("lastName") String lastName) throws Exception {
        return convertToDto(service.getPatronFromFullName(firstName, lastName));
    }

    /**
     * @author Gabrielle Halpin
	 * Update Password
	 * @param userID  
	 * @param password
	 * @return
	 */
	@PutMapping(value = {"/updatePassword", "/updatePassword/"})
	public UserAccountDTO updatePassword(@RequestParam int userID, @RequestParam("password") String password) {
		UserAccountDTO accountDTO = new UserAccountDTO();
		UserAccount  account = service.changePassword(password, userID);
		accountDTO = convertToDto(account);
		return accountDTO; 
	}
    /**
     * This method is called when the librarian wants to update the user's balance.
     * @param userID
     * @param balance
     * @author Mathieu Geoffroy
     * @return the accountDTO with the updated infromation
     */

    @PutMapping(value = {"/updateBalance", "/updateBalance/"})
	public UserAccountDTO updateBalance(@RequestParam int userID, @RequestParam int balance) {
		UserAccountDTO accountDTO = new UserAccountDTO();
		UserAccount  account = service.changeAccountBalance(balance, userID);
		accountDTO = convertToDto(account);
		return accountDTO; 
	}

    /**
     * @author Gabrielle Halpin
	 * Update Address of the user
	 * @param userID  
	 * @param address
	 * @return
	 */
	@PutMapping(value = {"/updateAddress", "/updateAddress/"})
	public UserAccountDTO updateAddress(@RequestParam int userID, @RequestParam("address") String aAddress) {
		UserAccountDTO accountDTO = new UserAccountDTO();
		UserAccount  account = service.changeAddress(aAddress, userID);
		accountDTO = convertToDto(account);
		return accountDTO; 
	}

    /**
     * @author Gabrielle Halpin
	 * Update firstName of the user
	 * @param userID  
	 * @param firstName
	 * @return
	 */
	@PutMapping(value = {"/updateFirstName", "/updateFirstName/"})
	public UserAccountDTO updateFirstName(@RequestParam int userID, @RequestParam("firstName") String firstName) {
		UserAccountDTO accountDTO = new UserAccountDTO();
		UserAccount  account = service.changeFirstName(firstName, userID);
		accountDTO = convertToDto(account);
		return accountDTO; 
	}

    /**
     * @author Gabrielle Halpin
	 * Update lastname of the user
	 * @param userID  
	 * @param lastName
	 * @return
	 */
	@PutMapping(value = {"/updateLastName", "/updateLastName/"})
	public UserAccountDTO updateLastName(@RequestParam int userID, @RequestParam("lastName") String lastName) {
		UserAccountDTO accountDTO = new UserAccountDTO();
		UserAccount  account = service.changeLastName(lastName, userID);
		accountDTO = convertToDto(account);
		return accountDTO; 
	}

    /**
     * @author Gabrielle Halpin
	 * Update Email of the user
	 * @param userID  
	 * @param email
	 * @return
	 */
	@PutMapping(value = {"/updateEmail", "/updateEmail/"})
	public UserAccountDTO updateEmail(@RequestParam int userID, @RequestParam("email") String email) {
		UserAccountDTO accountDTO = new UserAccountDTO();
		UserAccount  account = service.changeEmail(email, userID);
		accountDTO = convertToDto(account);
		return accountDTO; 
	}

    /**
     * This method sets the validity of the user's online account
     * @author Gabrielle Halpin
	 * Update Password
	 * @param userID  
	 * @param password
     * @param creatorID
	 * @return
	 */
	@PutMapping(value = {"/setAccountValidity", "/setAccountValidity/"})
	public UserAccountDTO setAccountValidity(@RequestParam int patronID, @RequestParam("validatedAccount") boolean validatedAccount, @RequestParam int creatorID) throws Exception{
		UserAccountDTO accountDTO = new UserAccountDTO();
		Patron  account = service.setValidatedAccount(patronID, validatedAccount, creatorID);
		accountDTO = convertToDto(account);
		return accountDTO; 
	}


    /**
     * @author Gabrielle Halpin This methods gets a userAccount from their unique
     *         userID
     * @param userID
     * @return UserAccountDTO
     * @throws Exception
     */
    @GetMapping(value = { "/account/{userID}", "/account/{userID}/" })
    public UserAccountDTO getUserbyUserID(@PathVariable("userID") int userID) throws Exception {
        return convertToDto(service.getUserbyUserId(userID));
    }

    /**
     * @author Gabrielle Halpin This method creates a Patron in the database and
     *         return a PatronDTO object
     * @param creator
     * @param firstName
     * @param lastName
     * @param onlineAccount
     * @param address
     * @param validatedAccount
     * @param password
     * @param balance
     * @param email
     * @return patronDTO
     * @throws Exception
     */
    @PostMapping(value = { "/createPatron/{firstName}/{lastName}", "/createPatron/{firstName}/{lastName}/" })
	public PatronDTO createPatron(@RequestParam(name="creatorID") Integer creatorID, @PathVariable(name="firstName") String firstName, @PathVariable(name="lastName") String lastName, @RequestParam(name="onlineAccount") Boolean onlineAccount, 
            @RequestParam(name="address") String address, @RequestParam(name="validatedAccount") boolean validatedAccount, @RequestParam(name="password") String password,
            @RequestParam(name="balance") Integer balance, @RequestParam(name="email") String email, @RequestParam(name="patronCreator") boolean patronCreator) throws Exception{
		Patron patron = service.createPatron( creatorID, firstName,  lastName,  onlineAccount,  address,  validatedAccount,  password,  balance,  email, patronCreator);
	return convertToDto(patron);
	}
    /**
	 * Login user account
	 * @author Zoya
	 * @param username
	 * @param password
	 * @return boolean if successful
     * @throws Exception 
	 */
	@PutMapping(value = {"/loginUserAccount/{userID}", "/loginUserAccount/{userID}/" })
	public UserAccountDTO loginUserAccount(@PathVariable("userID") int userID, @RequestParam("password") String password) throws Exception {
		UserAccount user = service.loginUserAccount(userID, password);
		return convertToDto(user);
	}

	/**
	 * Logout user account
	 * @author Zoya
	 * @param username
	 * @return boolean if successful
	 * @throws Exception 
	 */
	@PutMapping(value = {"/logoutUserAccount/{userID}", "/logoutUserAccount/{userID}/" })
	public UserAccountDTO logoutUserAccount(@PathVariable("userID") int userID) throws Exception {
		UserAccount user = service.logoutUserAccount(userID);
		return convertToDto(user);
	}
    
    
    
    /**
     * Gets all the books in the system
     * @return List of LibraryItemDTO
     * @throws Exception
     * @author Ramin Akhavan-Sarraf
     */
    @GetMapping(value = { "/books/", "/books" })
    public List<LibraryItemDTO> getAllBooks() throws Exception {
        ArrayList<LibraryItemDTO> books = new ArrayList<>();
        for(LibraryItem item: service.getAllBooks()){
            books.add(convertToDto(item));
        }
        return books;
    }

    /**
     * Gets all the movies in the system
     * @return List of LibraryItemDTO
     * @throws Exception
     * @author Ramin Akhavan-Sarraf
     */
    @GetMapping(value = { "/movies/", "/movies" })
    public List<LibraryItemDTO> getAllMovies() throws Exception {
        ArrayList<LibraryItemDTO> movies = new ArrayList<>();
        for(LibraryItem item: service.getAllMovies()){
            movies.add(convertToDto(item));
        }
        return movies;
    }

    /**
     * Gets all the newspapers in the system
     * @return List of LibraryItemDTO
     * @throws Exception
     * @author Ramin Akhavan-Sarraf
     */
    @GetMapping(value = { "/newspapers/", "/newspapers" })
    public List<LibraryItemDTO> getAllNewspapers() throws Exception {
        ArrayList<LibraryItemDTO> newspapers = new ArrayList<>();
        for(LibraryItem item: service.getAllNewspapers()){
            newspapers.add(convertToDto(item));
        }
        return newspapers;
    }

    /**
     * Gets all the music in the system
     * @return List of LibraryItemDTO
     * @throws Exception
     * @author Ramin Akhavan-Sarraf
     */
    @GetMapping(value = { "/music/", "/music" })
    public List<LibraryItemDTO> getAllMusic() throws Exception {
        ArrayList<LibraryItemDTO> music = new ArrayList<>();
        for(LibraryItem item: service.getAllMusic()){
            music.add(convertToDto(item));
        }
        return music;
    }

    /**
     * Gets all the user's room reservations
     * @return List of LibraryItemDTO
     * @throws Exception
     * @author Ramin Akhavan-Sarraf
     */
    @GetMapping(value = { "/account/{firstname}/{lastname}/rooms/", "/account/{firstname}/{lastname}/rooms" })
    public List<LibraryItemDTO> getAllUserRoomReservations(@PathVariable("firstname") String firstName, @PathVariable("lastname") String lastName) throws Exception {
        UserAccount user = service.getUserAccountFromFullName(firstName, lastName);
        List<BorrowableItem> borrowableItems = service.getReservedItemsFromUser(user);
        ArrayList<LibraryItemDTO> rooms = new ArrayList<>();
        for(BorrowableItem item: borrowableItems){
            BorrowableItemDTO dtoItem = convertToDto(item);
            if (dtoItem.getLibraryItem().getType().toString() == ItemType.Room.toString()){
                rooms.add(dtoItem.getLibraryItem());
            }
        }
        return rooms;
    }


    /**
     * Gets all the room reservations in the system
     * @return List of LibraryItemDTO
     * @throws Exception
     * @author Ramin Akhavan-Sarraf
     */
    @GetMapping(value = { "/rooms/", "/rooms" })
    public List<LibraryItemDTO> getAllRoomReservations() throws Exception {
        ArrayList<LibraryItemDTO> rooms = new ArrayList<>();
        for(LibraryItem reservation: service.getAllRoomReservations()){
            rooms.add(convertToDto(reservation));
        }
        return rooms;
    }
  
    /** 
     * Create an item reservation (transaction) between a user account and borrowable item, and convert to DTO
     * @param iDto - BorrowableItemDTO 
     * @param aDto - UserAccountDTO
     * @return TransactionDTO
     * @throws Exception
     * @author Amani Jammoul
     */
    @PostMapping(value = { "/reserve-item", "/reserve-item/" })
    public TransactionDTO reserveAnItem(@RequestParam(name = "barCodeNumber") int barCodeNumber,
            @RequestParam(name = "userID") int userID) throws Exception {
        BorrowableItem i = service.getBorrowableItemFromBarCodeNumber(barCodeNumber);
        UserAccount a = service.getUserAccountByUserID(userID);

        Transaction t = service.createItemReserveTransaction(i, a);
        return convertToDto(t); 
    }

    /**
     * Create a room reservation (transaction) between a user account and a room,
     * and convert to DTO
     * 
     * @param iDto      - BorrowableItemDto
     * @param aDto      - UserAccountDTO
     * @param date      - date of reservation
     * @param startTime - start time of reservation
     * @param endTime   - end time of reservation
     * @return TransactionDTO
     * @author Amani Jammoul
     */
    @PostMapping(value = { "/reserve-room", "/reserve-room/" })
    public TransactionDTO reserveARoom(@RequestParam(name = "userID") int userID, @RequestParam(name = "date") @DateTimeFormat(iso=DateTimeFormat.ISO.DATE, pattern="yyyy-MM-dd") LocalDate date)
            throws Exception {
        List<LibraryItem> rooms = service.getAllRoomReservations();
        BorrowableItem theItem = null;
        if (!rooms.isEmpty()){
            LibraryItem theRoom = rooms.get(0);
            List<BorrowableItem> borrowableRooms = service.getBorrowableItemsFromItemIsbn(theRoom.getIsbn());
            if (!borrowableRooms.isEmpty()){
                theItem = borrowableRooms.get(0);
            }
        }
        UserAccount a = service.getUserAccountByUserID(userID);

        Transaction t = service.createRoomReserveTransaction(theItem, a, Date.valueOf(date)); 
        TransactionDTO transaction = convertToDto(t);
        return transaction;
    }

    /**
     * Create a borrow transaction between a user account and an item, and convert
     * to DTO
     * 
     * @param iDto - BorrowableItemDto
     * @param aDto - UserAccountDTO
     * @return TransactionDTO
     * @throws Exception
     * @author Amani Jammoul
     */
    @PostMapping(value = { "/borrow", "/borrow/" })
    public TransactionDTO borrowAnItem(@RequestParam(name = "barCodeNumber") int barCodeNumber,
    @RequestParam(name = "userID") int userID) throws Exception {
        BorrowableItem i = service.getBorrowableItemFromBarCodeNumber(barCodeNumber);
        UserAccount a = service.getUserAccountByUserID(userID);

        Transaction t = service.createItemBorrowTransaction(i, a);
        return convertToDto(t);
    }

    /**
     * Get all the borrowbale items in teh system.
     * @author Mathieu Geoffroy
     * @return all borrowable Items
     */
    @GetMapping(value = {"/borrowableItems/viewall", "/borrowableItems/viewall/"})
    public List<BorrowableItemDTO> getAllBorrowableItems() {
        return service.getAllBorrowableItems().stream().map(p -> convertToDto(p)).collect(Collectors.toList());
    }

    /**
     * This returns all the transactiosn for a specific user
     * @param userID
     * @author Mathieu Geoffroy
     */
    @GetMapping(value = { "/transaction/viewall/id/{userID}", "/transaction/viewall/id/{userID}/"})
     public List<TransactionDTO> getAllTransactionsPerUser(@PathVariable(name = "userID") int userID) {
         List<TransactionDTO> transactions = new ArrayList<TransactionDTO>();
         for (Transaction t : service.getAllTransactions()) {
             if (t.getUserAccount().getUserID() == userID) {
                 transactions.add(convertToDto(t));
             }
         }
         return transactions;
     }

    /**
     * Create a renewal transaction between a user account and an item, and convert
     * to DTO
     * 
     * @param iDto - BorrowableItemDto
     * @param aDto - UserAccountDTO
     * @return TransactionDTO
     * @throws Exception
     * @author Amani Jammoul
     */
    @PostMapping(value = { "/renew", "/renew/" })
    public TransactionDTO renewAnItem(@RequestParam(name = "barCodeNumber") int barCodeNumber,
    @RequestParam(name = "userID") int userID) throws Exception {
        BorrowableItem i = service.getBorrowableItemFromBarCodeNumber(barCodeNumber);
        UserAccount a = service.getUserAccountByUserID(userID);

        Transaction t = service.createItemRenewalTransaction(i, a);
        return convertToDto(t);
    }

    /**
     * Create a waitlist transaction between a user account and an item, and convert
     * to DTO
     * 
     * @param iDto - BorrowableItemDto
     * @param aDto - UserAccountDTO
     * @return TransactionDTO
     * @throws Exception
     * @author Amani Jammoul
     */
    @PostMapping(value = { "/join-waitlist", "/join-waitlist/" })
    public TransactionDTO joinWaitlistForAnItem(@RequestParam(name = "barCodeNumber") int barCodeNumber,
    @RequestParam(name = "userID") int userID) throws Exception {
        BorrowableItem i = service.getBorrowableItemFromBarCodeNumber(barCodeNumber);
        UserAccount a = service.getUserAccountByUserID(userID);

        Transaction t = service.createItemWaitlistTransaction(i, a);
        return convertToDto(t);
    }

    /**
     * Create a return transaction between a user account and an item, and convert
     * to DTO
     * 
     * @param iDto - BorrowableItemDto
     * @param aDto - UserAccountDTO
     * @return TransactionDTO
     * @throws Exception
     * @author Amani Jammoul
     */
    @PostMapping(value = { "/return", "/return/" })
    public TransactionDTO returnAnItem(@RequestParam(name = "barCodeNumber") int barCodeNumber,
    @RequestParam(name = "userID") int userID) throws Exception {
        BorrowableItem i = service.getBorrowableItemFromBarCodeNumber(barCodeNumber);
        UserAccount a = service.getUserAccountByUserID(userID);

        Transaction t = service.createItemReturnTransaction(i, a);
        return convertToDto(t);
    }

    /**
     * Find user account by full name, and convert object to DTO
     * 
     * @param firstName
     * @param lastName
     * @return UserAccountDTO
     * @throws Exception
     * @author Amani Jammoul
     */
    @GetMapping(value = { "/account/firstName/lastName", "/account/firstName/lastName/" })
    public UserAccountDTO getUserAccountByFullName(@RequestParam("firstName") String firstName,
            @RequestParam("lastName") String lastName) throws Exception {
        UserAccount a = service.getUserAccountFromFullName(firstName, lastName);
        return convertToDto(a);
    }

    /**
     * Find user account by full name, then find all items that user has checked out
     * and convert those borrowable item objects to DTOs
     * 
     * @param firstName
     * @param lastName
     * @return List<BorrowableItemDTO> - items a user has borrowed/renewed (has in
     *         their possession)
     * @throws Exception
     * @author Amani Jammoul
     */
    @GetMapping(value = { "/account/{firstName}/{lastName}/borrowed-items",
            "/account/{firstName}/{lastName}/borrowed-items/" })
    public List<BorrowableItemDTO> getCheckedOutItemsByUser(@PathVariable("firstName") String firstName,
            @PathVariable("lastName") String lastName) throws Exception {
        UserAccount a = service.getUserAccountFromFullName(firstName, lastName);
        List<BorrowableItem> borrowableItems = service.getBorrowedItemsFromUser(a);
        List<BorrowableItemDTO> borrowableItemDTOs = new ArrayList<BorrowableItemDTO>();
        for (BorrowableItem b : borrowableItems) {
            borrowableItemDTOs.add(convertToDto(b));
        }
        return borrowableItemDTOs;
    }

    /**
     * Find user account by full name, then find all items that user has reserved
     * and convert those borrowable item objects to DTOs
     * 
     * @param firstName
     * @param lastName
     * @return List<BorrowableItemDTO> - items a user has reserved
     * @throws Exception
     * @author Amani Jammoul
     */
    @GetMapping(value = { "/account/{firstName}/{lastName}/reserved-items",
            "/account/{firstName}/{lastName}/reserved-items/" })
    public List<BorrowableItemDTO> getReservedItemsByUser(@PathVariable("firstName") String firstName,
            @PathVariable("lastName") String lastName) throws Exception {
        UserAccount a = service.getUserAccountFromFullName(firstName, lastName);
        List<BorrowableItem> borrowableItems = service.getReservedItemsFromUser(a);
        List<BorrowableItemDTO> borrowableItemDTOs = new ArrayList<BorrowableItemDTO>();
        for (BorrowableItem b : borrowableItems) {
            borrowableItemDTOs.add(convertToDto(b));
        }
        return borrowableItemDTOs;
    }

    /**
     * Find user account by full name, then find all items that user has request to
     * be on the waitlist for and convert those borrowable item objects to DTOs
     * 
     * @param firstName
     * @param lastName
     * @return List<BorrowableItemDTO> - items a user is on the waitlist for
     * @throws Exception
     * @author Amani Jammoul
     */
    @GetMapping(value = { "/account/{firstName}/{lastName}/waitlists", "/account/{firstName}/{lastName}/waitlists/" })
    public List<BorrowableItemDTO> getWaitlistsByUser(@PathVariable("firstName") String firstName,
            @PathVariable("lastName") String lastName) throws Exception {
        UserAccount a = service.getUserAccountFromFullName(firstName, lastName);
        List<BorrowableItem> borrowableItems = service.getItemWaitlistsFromUser(a);
        List<BorrowableItemDTO> borrowableItemDTOs = new ArrayList<BorrowableItemDTO>();
        for (BorrowableItem b : borrowableItems) {
            borrowableItemDTOs.add(convertToDto(b));
        }
        return borrowableItemDTOs;
    }

    /**
     * Finds all borrowable items for a certain library item object (by isbn), and
     * converts all those objects to DTOs
     * 
     * @param isbn
     * @return List<BorrowableItemDTO> - all borrowable items of a certain library
     *         item (identified by its isbn)
     * @throws Exception
     * @author Amani Jammoul
     */
    @GetMapping(value = { "/items/isbn", "/items/isbn/" })
    public List<BorrowableItemDTO> getAllBorrowableItemsByLibraryItemIsbn(@RequestParam("isbn") int isbn)
            throws Exception {
        List<BorrowableItem> borrowableItems = service.getBorrowableItemsFromItemIsbn(isbn);
        List<BorrowableItemDTO> borrowableItemDTOs = new ArrayList<BorrowableItemDTO>();
        for (BorrowableItem b : borrowableItems) {
            borrowableItemDTOs.add(convertToDto(b));
        }
        return borrowableItemDTOs;
    }

    /**
     * Find a borrowable item by its bar code number, and convert to DTO
     * 
     * @param barCodeNumber
     * @return BorrowableItemDTO - borrowable item with given bar code number
     * @throws Exception
     * @author Amani Jammoul
     */
    @GetMapping(value = { "/item/barCodeNumber", "/item/barCodeNumber/" })
    public BorrowableItemDTO getItemByBarCode(@RequestParam("barCodeNumber") int barCodeNumber) throws Exception {
        return convertToDto(service.getBorrowableItemFromBarCodeNumber(barCodeNumber));
    }

    /**
     * Find all items by title and convert those obejcts to DTOs
     * 
     * @param itemTitle
     * @return List<LibraryItemDTO> - items with the given title
     * @throws Exception
     * @author Amani Jammoul
     */
    @GetMapping(value = { "/items/title", "/items/title/" })
    public List<LibraryItemDTO> getItemsByTitle(@RequestParam("title") String itemTitle) throws Exception {
        List<LibraryItem> items = service.getLibraryItemsFromTitle(itemTitle);
        List<LibraryItemDTO> itemDTOs = new ArrayList<LibraryItemDTO>();
        for (LibraryItem i : items) {
            itemDTOs.add(convertToDto(i));
        }
        return itemDTOs;
    }

    /**
     * Find all items by creator and convert those obejcts to DTOs
     * 
     * @param creatorName
     * @return List<LibraryItemDTO> - items with the given creator
     * @throws Exception
     * @author Amani Jammoul
     */
    @GetMapping(value = { "/items/creator", "/items/creator/" })
    public List<LibraryItemDTO> getItemsByCreator(@RequestParam(name = "creator") String creatorName) throws Exception {
        List<LibraryItem> items = service.getLibraryItemsFromCreator(creatorName);
        List<LibraryItemDTO> itemDTOs = new ArrayList<LibraryItemDTO>();
        for (LibraryItem i : items) {
            itemDTOs.add(convertToDto(i));
        }
        return itemDTOs;
    }

    /**
     * Find all items by creator and title, and convert those obejcts to DTOs
     * 
     * @param creatorName
     * @param itemTitle
     * @return List<LibraryItemDTO> - items with the given title and creator
     * @throws Exception
     * @author Amani Jammoul
     */
    @GetMapping(value = { "/items/creator/title", "/items/creator/title/", "/items/title/creator",
            "/items/title/creator/" })
    public List<LibraryItemDTO> getItemsByCreatorAndTitle(@RequestParam("creator") String creatorName,
            @RequestParam("title") String itemTitle) throws Exception {
        List<LibraryItem> items = service.getLibraryItemFromCreatorAndTitle(creatorName, itemTitle);
        List<LibraryItemDTO> itemDTOs = new ArrayList<LibraryItemDTO>();
        for (LibraryItem i : items) {
            itemDTOs.add(convertToDto(i));
        }
        return itemDTOs;
    }

    /**
     * Find all books by title, and convert those objects to DTOs
     * 
     * @param bookTitle
     * @return List<LibraryItemDTO> - books with given title
     * @throws Exception
     * @author Amani Jammoul
     */
    @GetMapping(value = { "/books/title", "/books/title/" })
    public List<LibraryItemDTO> getBooksByTitle(@RequestParam("title") String bookTitle) throws Exception {
        List<LibraryItem> items = service.getBooksFromTitle(bookTitle);
        List<LibraryItemDTO> itemDTOs = new ArrayList<LibraryItemDTO>();
        for (LibraryItem i : items) {
            itemDTOs.add(convertToDto(i));
        }
        return itemDTOs;
    }

    /**
     * Find all books by author, and convert those objects to DTOs
     * 
     * @param authorName
     * @return List<LibraryItemDTO> - books written by given author name
     * @throws Exception
     * @author Amani Jammoul
     */
    @GetMapping(value = { "/books/author", "/books/author/" })
    public List<LibraryItemDTO> getBooksByAuthor(@RequestParam("author") String authorName) throws Exception {
        List<LibraryItem> items = service.getBooksFromAuthor(authorName);
        List<LibraryItemDTO> itemDTOs = new ArrayList<LibraryItemDTO>();
        for (LibraryItem i : items) {
            itemDTOs.add(convertToDto(i));
        }
        return itemDTOs;
    }

    /**
     * Find book by author and title, and convert to DTO
     * 
     * @param authorName
     * @param bookTitle
     * @return LibraryItemDTO - single book with given title written by given author
     * @throws Exception
     * @author Amani Jammoul
     */
    @GetMapping(value = { "/books/author/title", "/books/author/title/", "/books/title/author",
            "/books/title/author/" })
    public LibraryItemDTO getBooksByAuthorAndTitle(@RequestParam("author") String authorName,
            @RequestParam("title") String bookTitle) throws Exception {
        LibraryItem book = service.getBookFromAuthorAndTitle(authorName, bookTitle);
        return convertToDto(book);
    }

    /**
     * Find all music by title, and convert those objects to DTOs
     * 
     * @param musicTitle
     * @return List<LibraryItemDTO> - musics with given title
     * @throws Exception
     * @author Amani Jammoul
     */
    @GetMapping(value = { "/musics/title", "/musics/title/" })
    public List<LibraryItemDTO> getMusicsByTitle(@RequestParam("title") String musicTitle) throws Exception {
        List<LibraryItem> items = service.getMusicsFromTitle(musicTitle);
        List<LibraryItemDTO> itemDTOs = new ArrayList<LibraryItemDTO>();
        for (LibraryItem i : items) {
            itemDTOs.add(convertToDto(i));
        }
        return itemDTOs;
    }

    /**
     * Find all music by artist, and convert those objects to DTOs
     * 
     * @param artistName
     * @return List<LibraryItemDTO> - musics created by given artist name
     * @throws Exception
     * @author Amani Jammoul
     */
    @GetMapping(value = { "/musics/artist", "/musics/artist/" })
    public List<LibraryItemDTO> getMusicsByArtist(@RequestParam("artist") String artistName) throws Exception {
        List<LibraryItem> items = service.getMusicsFromArtist(artistName);
        List<LibraryItemDTO> itemDTOs = new ArrayList<LibraryItemDTO>();
        for (LibraryItem i : items) {
            itemDTOs.add(convertToDto(i));
        }
        return itemDTOs;
    }

    /**
     * Find music by artsits and title, and convert to DTO
     * 
     * @param artistName
     * @param musicTitle
     * @return LibraryItemDTO - single music with given title and created by given
     *         artist
     * @throws Exception
     * @author Amani Jammoul
     */
    @GetMapping(value = { "/musics/artist/title", "/musics/artist/title/", "/musics/title/artist",
            "/musics/title/artist/" })
    public LibraryItemDTO getMusicsByArtistAndTitle(@RequestParam("artist") String artistName,
            @RequestParam("title") String musicTitle) throws Exception {
        LibraryItem music = service.getMusicFromArtistAndTitle(artistName, musicTitle);
        return convertToDto(music);
    }

    /**
     * Find all movies by title, and convert those objects to DTOs
     * 
     * @param movieTitle
     * @return List<LibraryItemDTO> - movies with given title
     * @throws Exception
     * @author Amani Jammoul
     */
    @GetMapping(value = { "/movies/title", "/movies/title/" })
    public List<LibraryItemDTO> getMoviesByTitle(@RequestParam("title") String movieTitle) throws Exception {
        List<LibraryItem> items = service.getMoviesFromTitle(movieTitle);
        List<LibraryItemDTO> itemDTOs = new ArrayList<LibraryItemDTO>();
        for (LibraryItem i : items) {
            itemDTOs.add(convertToDto(i));
        }
        return itemDTOs;
    }

    /**
     * Find all movies by director, and convert those objects to DTOs
     * 
     * @param directorName
     * @return List<LibraryItemDTO> - movies directed by given director name
     * @throws Exception
     * @author Amani Jammoul
     */
    @GetMapping(value = { "/movies/director", "/movies/director/" })
    public List<LibraryItemDTO> getMoviesByDirector(@RequestParam("director") String directorName) throws Exception {
        List<LibraryItem> items = service.getMoviesFromDirector(directorName);
        List<LibraryItemDTO> itemDTOs = new ArrayList<LibraryItemDTO>();
        for (LibraryItem i : items) {
            itemDTOs.add(convertToDto(i));
        }
        return itemDTOs;
    }

    /**
     * Find movie by director and title, and convert to DTO
     * 
     * @param directorName
     * @param movieTitle
     * @return LibraryItemDTO - single movie with given title directed by given
     *         director
     * @throws Exception
     * @author Amani Jammoul
     */
    @GetMapping(value = { "/movies/director/title", "/movies/director/title/", "/movies/title/director",
            "/movies/title/director/" })
    public LibraryItemDTO getMoviesByDirectorAndTitle(@RequestParam("director") String directorName,
            @RequestParam("title") String movieTitle) throws Exception {
        LibraryItem movie = service.getMovieFromDirectorAndTitle(directorName, movieTitle);
        return convertToDto(movie);
    }

    /**
     * Find all newspapers by title, and convert those objects to DTOs
     * 
     * @param movieTitle
     * @return List<LibraryItemDTO> - newspapers with given title
     * @throws Exception
     * @author Ramin Akhavan-Sarraf
     */
    @GetMapping(value = { "/newspapers/title", "/newspapers/title/" })
    public List<LibraryItemDTO> getNewspapersByTitle(@RequestParam("title") String newspaperTitle) throws Exception {
        List<LibraryItem> items = service.getNewspaperFromTitle(newspaperTitle);
        List<LibraryItemDTO> itemDTOs = new ArrayList<LibraryItemDTO>();
        for (LibraryItem i : items) {
            itemDTOs.add(convertToDto(i));
        }
        return itemDTOs;
    }

    /**
     * Find all newspapers by writer, and convert those objects to DTOs
     * 
     * @param writerName
     * @return List<LibraryItemDTO> - newspapers written by writer
     * @throws Exception
     * @author Ramin Akhavan-Sarraf
     */
    @GetMapping(value = { "/newspapers/writer", "/newspapers/writer/" })
    public List<LibraryItemDTO> getNewspapersByWriter(@RequestParam("writer") String writerName) throws Exception {
        List<LibraryItem> items = service.getNewspaperFromWriter(writerName);
        List<LibraryItemDTO> itemDTOs = new ArrayList<LibraryItemDTO>();
        for (LibraryItem i : items) {
            itemDTOs.add(convertToDto(i));
        }
        return itemDTOs;
    }

    /**
     * Find newspaper by writer and title, and convert to DTO
     * 
     * @param writerName
     * @param newspaperTitle
     * @return LibraryItemDTO - single newspaper with given title directed by given
     *         writer
     * @throws Exception
     * @author Ramin Akhavan-Sarraf
     */
    @GetMapping(value = { "/newspapers/writer/title", "/newspapers/writer/title/", "/newspapers/title/writer",
            "/newspapers/title/writer/" })
    public LibraryItemDTO getNewspapersByWriterAndTitle(@RequestParam("writer") String writerName,
            @RequestParam("title") String newspaperTitle) throws Exception {
        LibraryItem newspaper = service.getNewspaperFromWriterAndTitle(writerName, newspaperTitle);
        return convertToDto(newspaper);
    }

     /** create new library item
     * 
     * @param LibraryItemDTO
     * @return LibraryItemDTO
     * @throws Exception
     * @author Ramin Akhavan-Sarraf
     */
    @PostMapping(value = { "/createLibraryItem", "/createLibraryItem/" })
    public LibraryItemDTO createLibraryItem(@RequestParam("name") String name, @RequestParam("itemType") String itemType, @RequestParam("date") Date date, @RequestParam("creator") String creator, @RequestParam("isViewable") boolean isViewable, @RequestParam("isbn") int isbn) throws Exception {
    	LibraryItem libraryItem = service.createLibraryItem(name, itemType, date, creator, isViewable);
        libraryItem = service.updateISBN(isbn, libraryItem);
    	return convertToDto(libraryItem);
    }
 
    /**
     * create new borrowable item
     * 
     * @param BorrowableItemDTO
     * @return BorrowableItemDTO
     * @throws Exception
     * @author Ramin Akhavan-Sarraf
     */
    @PostMapping(value = { "/createBorrowableItem", "/createBorrowableItem/" })
    public BorrowableItemDTO createBorrowableItem(@RequestParam("creator") String creator, @RequestParam("title") String title, @RequestParam("itemState") String itemState, @RequestParam("isbn") int isbn) throws Exception {
    	String borrowableItemState = itemState;
        LibraryItem libraryItem = null;
        for (LibraryItem i : service.getLibraryItemFromCreatorAndTitle(creator, title)) {
            if (i.getIsbn() == isbn) {
                libraryItem = i;
                break;
            } else {
                service.deleteLibraryItem(i);
            }
        }
    	BorrowableItem borrowableItem = service.createBorrowableItem(borrowableItemState, libraryItem);
    	return convertToDto(borrowableItem);
    }
 
    /**
     * delete library item
     * 
     * @param LibraryItemDTO
     * @return LibraryItemDTO
     * @throws Exception
     * @author Ramin Akhavan-Sarraf
     */
    @DeleteMapping(value = { "/deleteLibraryItem", "/deleteLibraryItem/" })
    public boolean deleteLibraryItem(@RequestParam int isbn) throws Exception {
    	boolean delete = service.deleteLibraryItem(isbn);
    	return delete;
    }
 
    /**
     * delete borrowable item
     * 
     * @param LibraryItemDTO
     * @return LibraryItemDTO
     * @throws Exception
     * @author Ramin Akhavan-Sarraf
     */
    @DeleteMapping(value = { "/deleteBorrowableItem", "/deleteBorrowableItem/" })
    public boolean createLibraryItem(@RequestParam int barCodeNumber) throws Exception {
    	boolean delete = service.deleteBorrowableItem(barCodeNumber);
    	return delete;
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

        PatronDTO patronDTO = new PatronDTO(patron.getFirstName(),patron.getLastName(),patron.getOnlineAccount(),patron.getAddress(), patron.getValidatedAccount(), patron.getPassword(), patron.getBalance(), patron.getEmail(), patron.getUserID());

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

        LibrarianDTO librarianDTO = new LibrarianDTO(librarian.getFirstName(),librarian.getLastName(),librarian.getOnlineAccount(),librarian.getAddress(), librarian.getPassword(), librarian.getBalance(), librarian.getEmail(), librarian.getUserID());

        return librarianDTO;
    }

    /**
     * @author Ramin Akhavan-Sarraf
     * @param HeadLibrarian
     * @return HeadLibrarianDTO
     */
    private HeadLibrarianDTO convertToDto(HeadLibrarian headLibrarian) {
        if (headLibrarian == null) {
            throw new IllegalArgumentException("There is no such head librarian!");
        }

        HeadLibrarianDTO headLibrarianDTO = new HeadLibrarianDTO(headLibrarian.getFirstName(),headLibrarian.getLastName(),headLibrarian.getOnlineAccount(),headLibrarian.getAddress(), headLibrarian.getPassword(), headLibrarian.getBalance(), headLibrarian.getEmail(), headLibrarian.getUserID());

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

        HeadLibrarianDTO headLibrarianDTO = new HeadLibrarianDTO(headLibrarian.getFirstName(),headLibrarian.getLastName(),headLibrarian.getOnlineAccount(),headLibrarian.getAddress(), headLibrarian.getPassword(), headLibrarian.getBalance(), headLibrarian.getEmail(), headLibrarian.getUserID());
        HolidayDTO holidayDTO = new HolidayDTO(holiday.getDate(), holiday.getStartTime(), holiday.getEndtime(), headLibrarianDTO, holiday.getHolidayID());

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

        HeadLibrarianDTO headLibrarianDTO = new HeadLibrarianDTO(headLibrarian.getFirstName(),headLibrarian.getLastName(),headLibrarian.getOnlineAccount(),headLibrarian.getAddress(), headLibrarian.getPassword(), headLibrarian.getBalance(), headLibrarian.getEmail(), headLibrarian.getUserID());
        String dayOfWeek = openingHour.getDayOfWeek().toString();
        OpeningHourDTO openingHourDTO = new OpeningHourDTO(dayOfWeek, openingHour.getStartTime(), openingHour.getEndTime(), headLibrarianDTO, openingHour.getHourID());

        return openingHourDTO;
    }

    /**
     * @author Ramin Akhavan-Sarraf
     * @param LibraryItem
     * @return LibraryItemDTO
     */
    private LibraryItemDTO convertToDto(LibraryItem libraryItem) {
        if (libraryItem == null) {
            throw new IllegalArgumentException("There is no such library item!");
        }
        String itemType = libraryItem.getType().toString();

        LibraryItemDTO libraryItemDTO = new LibraryItemDTO(libraryItem.getName(), itemType, libraryItem.getDate(), libraryItem.getCreator(), libraryItem.getIsViewable(), libraryItem.getIsbn());

        return libraryItemDTO;
    }

    private BorrowableItemDTO convertToDto(BorrowableItem borrowableItem) {
        if (borrowableItem == null) {
            throw new IllegalArgumentException("There is no such library item!");
        }
        LibraryItemDTO item = convertToDto(borrowableItem.getLibraryItem());
        String itemState = borrowableItem.getState().toString();
        int barCodeNumber = borrowableItem.getBarCodeNumber();
        BorrowableItemDTO borrowableItemDTO = new BorrowableItemDTO(itemState, item, barCodeNumber);
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
        TimeslotDTO timeslotDTO;
        if (timeslot.getLibrarian() != null) {
            for (Librarian librarian : timeslot.getLibrarian()) {
                LibrarianDTO lib = convertToDto(librarian);
                librarianDTO.add(lib);
            }
            timeslotDTO= new TimeslotDTO(timeslot.getStartDate(), timeslot.getStartTime(), timeslot.getEndDate(), timeslot.getEndTime(), librarianDTO, headLibrarianDTO, timeslot.getTimeSlotID());
        } else {
            timeslotDTO= new TimeslotDTO(timeslot.getStartDate(), timeslot.getStartTime(), timeslot.getEndDate(), timeslot.getEndTime(), null, headLibrarianDTO, timeslot.getTimeSlotID());
        }
        return timeslotDTO;
    }

    /**
     * @author Gabrielle Halpin
     * @param transaction
     * @return
     */
    private TransactionDTO convertToDto(Transaction transaction) {
        if (transaction == null) {
            throw new IllegalArgumentException("There is no such library item!");
        }
        BorrowableItemDTO item = convertToDto(transaction.getBorrowableItem());
        UserAccountDTO userAccountDTO = convertToDto(transaction.getUserAccount());

        TransactionDTO.TransactionType itemType = TransactionDTO.TransactionType.valueOf(transaction.getTransactionType().toString());
        TransactionDTO transactionDTO = new TransactionDTO(itemType, transaction.getDeadline(), item, userAccountDTO, transaction.getTransactionID());

        return transactionDTO;
    }

    /**
     * @author Gabrielle Halpin
     * @param userAccount
     * @return
     */
    private UserAccountDTO convertToDto(UserAccount userAccount) {
        if (userAccount == null) {
            throw new IllegalArgumentException("There is no such user!");
        }

        UserAccountDTO userAccountDTO = new UserAccountDTO(userAccount.getFirstName(), userAccount.getLastName(), userAccount.getOnlineAccount(), userAccount.getAddress(), userAccount.getPassword(), userAccount.getBalance(), userAccount.getEmail(), userAccount.getUserID());

        return userAccountDTO;
    }

    ///////// Helper methods - convertToDomainObject//////////

    //each method need to check to make sure the individual is in the system before creating them.

 
    /** 
    * @author Eloyann Roy Javanbakht
    * mapping GetHead Librarian from userId
    * @param userID
    * @return
    * @throws Exception
    */
    @GetMapping(value={"/headLibrarian/{userID}", "/headLibrarian/{userID}/"})
    public HeadLibrarianDTO getHeadLibrarianFromUserId(@PathVariable("userID") int userID) throws Exception  {
        HeadLibrarian headLibrarian=service.getHeadLibrarianFromUserId(userID);
        return convertToDto(headLibrarian);
    }

     /**
      *  /**
    * delete Librarian
    * @author Eloyann Roy Javanbakht
    *
    * @param userID
    * @return
    * @throws Exception
    */
    @GetMapping(value={"/librarians/{userID}", "/librarians/{userID}/"})
    public LibrarianDTO getLibrarianFromUserId(@PathVariable("userID") int userID) throws Exception  {
    Librarian librarian=service.getLibrarianFromUserId(userID);
    return convertToDto(librarian);
    }  

    /**
     * delete Librarian
     * @author Eloyann Roy Javanbakht
     * @param userID
     * @param userIDHeadLibrarian
     * @return
     * @throws Exception
     */
    @DeleteMapping(value={"/librarians/deleteAccount/{userID}", "/librarians/deleteAccount/{userID}/"})
    public boolean deleteALibrarian(@PathVariable("userID") int userID, 
    @RequestParam(name = "headlibrarianID") int userIDHeadLibrarian) throws Exception  {
    return service.deleteLibrarian(userIDHeadLibrarian, userID).getUserID() == userID;

    }  

    /**
     * deleted a headLibrarian 
     * @param userID
     * @return
     * @throws Exception
     */
    @DeleteMapping(value={"/headLibrarians/delete/{userID}", "/headLibrarians/delete/{userID}/"})
    public boolean deleteALibrarian(@PathVariable("userID") int userID) throws Exception  {
    return (service.deleteHeadLibrarian(userID).getUserID() == userID);

    }  

    /**
     * create HeadLibrarian
     * @author Eloyann Roy Javanbakht
     * @param firstName
     * @param aLastName
     * @param aOnlineAccount
     * @param aAddress
     * @param aPassword
     * @param aBalance
     * @param aEmail
     * @return
     * @throws Exception
     */
    @PostMapping(value={"/createHeadLibrarian", "/createHeadLibrarian/"})
    public HeadLibrarianDTO createHeadLibrarian(@RequestParam(name = "firstName") String firstName,
        @RequestParam(name = "lastName") String aLastName,
        @RequestParam(name = "online") boolean aOnlineAccount,
        @RequestParam(name = "address") String aAddress,
        @RequestParam(name = "password") String aPassword,
        @RequestParam(name = "balance") int aBalance,
        @RequestParam(name = "email") String aEmail) throws Exception{
        
            
            return convertToDto(service.createNewHeadLibrarian(firstName, aLastName, aOnlineAccount, aAddress, aPassword, aBalance, aEmail));
        }


    /**
     * @author Eloyann
     * create Librarian
     * @param firstName
     * @param aLastName
     * @param aOnlineAccount
     * @param aAddress
     * @param aPassword
     * @param aBalance
     * @param aEmail
     * @param creator
     * @return
     * @throws Exception
     */
    @PostMapping(value = { "/createLibrarian/{firstName}/{lastName}",
            "/createLibrarian/{firstName}/{lastName}/" })
    public LibrarianDTO createLibrarian(@PathVariable("firstName") String firstName,
    @PathVariable("lastName") String aLastName,
    @RequestParam(name = "online") boolean aOnlineAccount, @RequestParam(name = "address") String aAddress,
    @RequestParam(name = "password") String aPassword, @RequestParam(name = "balance") int aBalance, 
    @RequestParam(name = "email") String aEmail, @RequestParam(name = "userID") int aUserID ) throws Exception {

        return convertToDto(service.createANewLibrarian(aUserID, firstName, aLastName, aOnlineAccount, aAddress, aPassword,
                aBalance, aEmail));
    }
    
    /**
     * @author Eloyann
     * create Librarian
     * @param firstName
     * @param lastName
     * @return
     * @throws Exception
     */
    @GetMapping(value = { "/librarianAccount/{firstName}/{lastName}", "/librarianAccount/{firstName}/{lastName}/" })
    public UserAccountDTO getLibrarianAccountByFullName(@PathVariable("firstName") String firstName,
            @PathVariable("lastName") String lastName) throws Exception {
        return convertToDto(service.getLibrarianFromFullName(firstName, lastName));
    }







}