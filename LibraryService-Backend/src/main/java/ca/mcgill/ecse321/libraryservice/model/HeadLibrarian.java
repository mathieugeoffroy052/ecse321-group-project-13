/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4607.2d2b84eb8 modeling language!*/

package ca.mcgill.ecse321.libraryservice.model;
import java.util.*;
import java.sql.Date;
import java.sql.Time;
import javax.persistence.*;

@Entity
// line 54 "../../../../../../library.ump 15-05-01-147.ump 15-45-27-537.ump 16-05-11-860.ump"
public class HeadLibrarian extends Librarian
{

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public HeadLibrarian(String aFirstName, String aLastName, boolean aOnlineAccount, LibrarySystem aLibrarySystem, Address aAddress, String aPassword)
  {
    super(aFirstName, aLastName, aOnlineAccount, aLibrarySystem, aAddress, aPassword);
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setLibrarianID(int aLibrarianID)
  {
    boolean wasSet = false;
    super.setLibrarianID(aLibrarianID);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    super.delete();
  }

}