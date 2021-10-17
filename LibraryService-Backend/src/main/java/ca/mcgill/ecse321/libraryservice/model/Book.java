/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4607.2d2b84eb8 modeling language!*/

package ca.mcgill.ecse321.libraryservice.model;
import java.util.*;
import javax.persistence.*;

@Entity
// line 106 "../../../../../../library.ump 15-05-01-147.ump 15-45-27-537.ump 16-05-11-860.ump"
public class Book extends LibraryItem
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  private String author;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Book(String aName, LibrarySystem aLibrarySystem, String aAuthor)
  {
    super(aName, aLibrarySystem);
    author = aAuthor;
  }

  //------------------------
  // INTERFACE
  //------------------------

  public String getAuthor(){
    return author;
  }

  public boolean setAuthor(String aAuthor)
  {
    boolean wasSet = false;
    author = aAuthor;
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    super.delete();
  }

}