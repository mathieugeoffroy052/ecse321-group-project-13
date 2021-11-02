package ca.mcgill.ecse321.libraryservice.dto;


import org.apache.tomcat.jni.User;

public class LibrarianDTO extends UserAccountDTO{

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public LibrarianDTO() {}

  public LibrarianDTO(String aFirstName, String aLastName, boolean aOnlineAccount, String aAddress, String aPassword, int aBalance)
  {
    super(aFirstName, aLastName, aOnlineAccount,aAddress, aPassword, aBalance);
  }

}
