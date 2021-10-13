package ca.mcgill.ecse321.libraryservice.model;
/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4607.2d2b84eb8 modeling language!*/
import javax.persistence.*;
import java.util.*;

@Entity
// line 95 "Library.ump"
public class Book extends LibraryItem
{

  //------------------------
  // STATIC VARIABLES
  //------------------------

  private static int nextBookID = 1;

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Autounique Attributes
  private int bookID;

  //Book Associations
  private List<Person> author;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Book(String aName, LibrarySystem aLibrarySystem, Person... allAuthor)
  {
    super(aName, aLibrarySystem);
    bookID = nextBookID++;
    author = new ArrayList<Person>();
    boolean didAddAuthor = setAuthor(allAuthor);
    if (!didAddAuthor)
    {
      throw new RuntimeException("Unable to create Book, must have at least 1 author. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------
  @Id
  public int getBookID()
  {
    return bookID;
  }
  /* Code from template association_GetMany */
  public Person getAuthor(int index)
  {
    Person aAuthor = author.get(index);
    return aAuthor;
  }

  public List<Person> getAuthor()
  {
    List<Person> newAuthor = Collections.unmodifiableList(author);
    return newAuthor;
  }

  public int numberOfAuthor()
  {
    int number = author.size();
    return number;
  }

  public boolean hasAuthor()
  {
    boolean has = author.size() > 0;
    return has;
  }

  public int indexOfAuthor(Person aAuthor)
  {
    int index = author.indexOf(aAuthor);
    return index;
  }
  /* Code from template association_IsNumberOfValidMethod */
  public boolean isNumberOfAuthorValid()
  {
    boolean isValid = numberOfAuthor() >= minimumNumberOfAuthor();
    return isValid;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfAuthor()
  {
    return 1;
  }
  /* Code from template association_AddManyToManyMethod */
  public boolean addAuthor(Person aAuthor)
  {
    boolean wasAdded = false;
    if (author.contains(aAuthor)) { return false; }
    author.add(aAuthor);
    if (aAuthor.indexOfBook(this) != -1)
    {
      wasAdded = true;
    }
    else
    {
      wasAdded = aAuthor.addBook(this);
      if (!wasAdded)
      {
        author.remove(aAuthor);
      }
    }
    return wasAdded;
  }
  /* Code from template association_AddMStarToMany */
  public boolean removeAuthor(Person aAuthor)
  {
    boolean wasRemoved = false;
    if (!author.contains(aAuthor))
    {
      return wasRemoved;
    }

    if (numberOfAuthor() <= minimumNumberOfAuthor())
    {
      return wasRemoved;
    }

    int oldIndex = author.indexOf(aAuthor);
    author.remove(oldIndex);
    if (aAuthor.indexOfBook(this) == -1)
    {
      wasRemoved = true;
    }
    else
    {
      wasRemoved = aAuthor.removeBook(this);
      if (!wasRemoved)
      {
        author.add(oldIndex,aAuthor);
      }
    }
    return wasRemoved;
  }
  /* Code from template association_SetMStarToMany */
  @ManyToMany
  public boolean setAuthor(Person... newAuthor)
  {
    boolean wasSet = false;
    ArrayList<Person> verifiedAuthor = new ArrayList<Person>();
    for (Person aAuthor : newAuthor)
    {
      if (verifiedAuthor.contains(aAuthor))
      {
        continue;
      }
      verifiedAuthor.add(aAuthor);
    }

    if (verifiedAuthor.size() != newAuthor.length || verifiedAuthor.size() < minimumNumberOfAuthor())
    {
      return wasSet;
    }

    ArrayList<Person> oldAuthor = new ArrayList<Person>(author);
    author.clear();
    for (Person aNewAuthor : verifiedAuthor)
    {
      author.add(aNewAuthor);
      if (oldAuthor.contains(aNewAuthor))
      {
        oldAuthor.remove(aNewAuthor);
      }
      else
      {
        aNewAuthor.addBook(this);
      }
    }

    for (Person anOldAuthor : oldAuthor)
    {
      anOldAuthor.removeBook(this);
    }
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addAuthorAt(Person aAuthor, int index)
  {  
    boolean wasAdded = false;
    if(addAuthor(aAuthor))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfAuthor()) { index = numberOfAuthor() - 1; }
      author.remove(aAuthor);
      author.add(index, aAuthor);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveAuthorAt(Person aAuthor, int index)
  {
    boolean wasAdded = false;
    if(author.contains(aAuthor))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfAuthor()) { index = numberOfAuthor() - 1; }
      author.remove(aAuthor);
      author.add(index, aAuthor);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addAuthorAt(aAuthor, index);
    }
    return wasAdded;
  }

  public void delete()
  {
    ArrayList<Person> copyOfAuthor = new ArrayList<Person>(author);
    author.clear();
    for(Person aAuthor : copyOfAuthor)
    {
      aAuthor.removeBook(this);
    }
    super.delete();
  }


  public String toString()
  {
    return super.toString() + "["+
            "bookID" + ":" + getBookID()+ "]";
  }
}