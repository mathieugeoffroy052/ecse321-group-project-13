/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4607.2d2b84eb8 modeling language!*/

package ca.mcgill.ecse321.libraryservice.model;
import javax.persistence.*;

@Entity
// line 46 "../../../../../../library.ump 15-05-01-147.ump 15-45-27-537.ump 16-05-11-860.ump"
public class Librarian extends UserAccount
{

  //------------------------
  // STATIC VARIABLES
  //------------------------

  private static int nextlibrarianID = 1;

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Autounique Attributes
  private int librarianID;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Librarian() {
    super();
    librarianID = nextlibrarianID++;
  }

  public Librarian(String aFirstName, String aLastName, boolean aOnlineAccount, LibrarySystem aLibrarySystem, String aAddress, String aPassword, int aBalance)
  {
    super(aFirstName, aLastName, aOnlineAccount, aLibrarySystem, aAddress, aPassword, aBalance);
    librarianID = nextlibrarianID++;
  }

  //------------------------
  // INTERFACE
  //------------------------
  
  public int getlibrarianID()
  {
    return librarianID;
  }

  public boolean setLibrarianID(int aLibrarianID)
  {
    librarianID = aLibrarianID;
    if(librarianID==aLibrarianID){
      return true;
    }
    else return false;
  }

  public String toString()
  {
    return super.toString() + "["+
            "librarianID" + ":" + getlibrarianID()+ "]";
  }
}