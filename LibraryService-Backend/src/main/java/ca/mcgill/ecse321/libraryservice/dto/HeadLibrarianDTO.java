package ca.mcgill.ecse321.libraryservice.dto;

public class HeadLibrarianDTO extends LibrarianDTO{

	public HeadLibrarianDTO () {
	}

	public HeadLibrarianDTO (String aFirstName, String aLastName, boolean aOnlineAccount, String aAddress, String aPassword, int aBalance) {
		super(aFirstName, aLastName, aOnlineAccount,aAddress, aPassword, aBalance);
    }

}