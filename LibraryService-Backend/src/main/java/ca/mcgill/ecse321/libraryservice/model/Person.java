/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4607.2d2b84eb8 modeling language!*/

package ca.mcgill.ecse321.libraryservice.model;
import java.util.*;
import javax.persistence.*;

@Entity
// line 119 "../../../../../../library.ump 15-05-01-147.ump 15-45-27-537.ump"
public class Person
{

  //------------------------
  // STATIC VARIABLES
  //------------------------

  private static int nextAuthorID = 1;

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Person Attributes
  private String firstName;
  private String lastName;

  //Autounique Attributes
  private int authorID;

  //Person Associations
  private LibrarySystem librarySystem;
  private List<Book> books;
  private List<Movie> movies;
  private List<Music> musics;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Person(String aFirstName, String aLastName, LibrarySystem aLibrarySystem)
  {
    firstName = aFirstName;
    lastName = aLastName;
    authorID = nextAuthorID++;
    boolean didAddLibrarySystem = setLibrarySystem(aLibrarySystem);
    if (!didAddLibrarySystem)
    {
      throw new RuntimeException("Unable to create person due to librarySystem. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    books = new ArrayList<Book>();
    movies = new ArrayList<Movie>();
    musics = new ArrayList<Music>();
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setFirstName(String aFirstName)
  {
    boolean wasSet = false;
    firstName = aFirstName;
    wasSet = true;
    return wasSet;
  }

  public boolean setLastName(String aLastName)
  {
    boolean wasSet = false;
    lastName = aLastName;
    wasSet = true;
    return wasSet;
  }

  public String getFirstName()
  {
    return firstName;
  }

  public String getLastName()
  {
    return lastName;
  }
  @Id
  public int getAuthorID()
  {
    return authorID;
  }
  /* Code from template association_GetOne */
  public LibrarySystem getLibrarySystem()
  {
    return librarySystem;
  }
  /* Code from template association_GetMany */
  public Book getBook(int index)
  {
    Book aBook = books.get(index);
    return aBook;
  }

  public List<Book> getBooks()
  {
    List<Book> newBooks = Collections.unmodifiableList(books);
    return newBooks;
  }

  public int numberOfBooks()
  {
    int number = books.size();
    return number;
  }

  public boolean hasBooks()
  {
    boolean has = books.size() > 0;
    return has;
  }

  public int indexOfBook(Book aBook)
  {
    int index = books.indexOf(aBook);
    return index;
  }
  /* Code from template association_GetMany */
  public Movie getMovy(int index)
  {
    Movie aMovy = movies.get(index);
    return aMovy;
  }

  public List<Movie> getMovies()
  {
    List<Movie> newMovies = Collections.unmodifiableList(movies);
    return newMovies;
  }

  public int numberOfMovies()
  {
    int number = movies.size();
    return number;
  }

  public boolean hasMovies()
  {
    boolean has = movies.size() > 0;
    return has;
  }

  public int indexOfMovy(Movie aMovy)
  {
    int index = movies.indexOf(aMovy);
    return index;
  }
  /* Code from template association_GetMany */
  public Music getMusic(int index)
  {
    Music aMusic = musics.get(index);
    return aMusic;
  }

  public List<Music> getMusics()
  {
    List<Music> newMusics = Collections.unmodifiableList(musics);
    return newMusics;
  }

  public int numberOfMusics()
  {
    int number = musics.size();
    return number;
  }

  public boolean hasMusics()
  {
    boolean has = musics.size() > 0;
    return has;
  }

  public int indexOfMusic(Music aMusic)
  {
    int index = musics.indexOf(aMusic);
    return index;
  }
  /* Code from template association_SetOneToMany */
  @ManyToOne(optional=false)
  public boolean setLibrarySystem(LibrarySystem aLibrarySystem)
  {
    boolean wasSet = false;
    if (aLibrarySystem == null)
    {
      return wasSet;
    }

    LibrarySystem existingLibrarySystem = librarySystem;
    librarySystem = aLibrarySystem;
    if (existingLibrarySystem != null && !existingLibrarySystem.equals(aLibrarySystem))
    {
      existingLibrarySystem.removePerson(this);
    }
    librarySystem.addPerson(this);
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfBooks()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public Book addBook(String aName, LibrarySystem aLibrarySystem)
  {
    return new Book(aName, aLibrarySystem, this);
  }

  public boolean addBook(Book aBook)
  {
    boolean wasAdded = false;
    if (books.contains(aBook)) { return false; }
    Person existingAuthor = aBook.getAuthor();
    boolean isNewAuthor = existingAuthor != null && !this.equals(existingAuthor);
    if (isNewAuthor)
    {
      aBook.setAuthor(this);
    }
    else
    {
      books.add(aBook);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeBook(Book aBook)
  {
    boolean wasRemoved = false;
    //Unable to remove aBook, as it must always have a author
    if (!this.equals(aBook.getAuthor()))
    {
      books.remove(aBook);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addBookAt(Book aBook, int index)
  {  
    boolean wasAdded = false;
    if(addBook(aBook))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfBooks()) { index = numberOfBooks() - 1; }
      books.remove(aBook);
      books.add(index, aBook);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveBookAt(Book aBook, int index)
  {
    boolean wasAdded = false;
    if(books.contains(aBook))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfBooks()) { index = numberOfBooks() - 1; }
      books.remove(aBook);
      books.add(index, aBook);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addBookAt(aBook, index);
    }
    return wasAdded;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfMovies()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public Movie addMovy(String aName, LibrarySystem aLibrarySystem)
  {
    return new Movie(aName, aLibrarySystem, this);
  }

  public boolean addMovy(Movie aMovy)
  {
    boolean wasAdded = false;
    if (movies.contains(aMovy)) { return false; }
    Person existingDirector = aMovy.getDirector();
    boolean isNewDirector = existingDirector != null && !this.equals(existingDirector);
    if (isNewDirector)
    {
      aMovy.setDirector(this);
    }
    else
    {
      movies.add(aMovy);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeMovy(Movie aMovy)
  {
    boolean wasRemoved = false;
    //Unable to remove aMovy, as it must always have a director
    if (!this.equals(aMovy.getDirector()))
    {
      movies.remove(aMovy);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addMovyAt(Movie aMovy, int index)
  {  
    boolean wasAdded = false;
    if(addMovy(aMovy))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfMovies()) { index = numberOfMovies() - 1; }
      movies.remove(aMovy);
      movies.add(index, aMovy);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveMovyAt(Movie aMovy, int index)
  {
    boolean wasAdded = false;
    if(movies.contains(aMovy))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfMovies()) { index = numberOfMovies() - 1; }
      movies.remove(aMovy);
      movies.add(index, aMovy);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addMovyAt(aMovy, index);
    }
    return wasAdded;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfMusics()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public Music addMusic(String aName, LibrarySystem aLibrarySystem)
  {
    return new Music(aName, aLibrarySystem, this);
  }

  public boolean addMusic(Music aMusic)
  {
    boolean wasAdded = false;
    if (musics.contains(aMusic)) { return false; }
    Person existingArtist = aMusic.getArtist();
    boolean isNewArtist = existingArtist != null && !this.equals(existingArtist);
    if (isNewArtist)
    {
      aMusic.setArtist(this);
    }
    else
    {
      musics.add(aMusic);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeMusic(Music aMusic)
  {
    boolean wasRemoved = false;
    //Unable to remove aMusic, as it must always have a artist
    if (!this.equals(aMusic.getArtist()))
    {
      musics.remove(aMusic);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addMusicAt(Music aMusic, int index)
  {  
    boolean wasAdded = false;
    if(addMusic(aMusic))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfMusics()) { index = numberOfMusics() - 1; }
      musics.remove(aMusic);
      musics.add(index, aMusic);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveMusicAt(Music aMusic, int index)
  {
    boolean wasAdded = false;
    if(musics.contains(aMusic))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfMusics()) { index = numberOfMusics() - 1; }
      musics.remove(aMusic);
      musics.add(index, aMusic);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addMusicAt(aMusic, index);
    }
    return wasAdded;
  }

  public void delete()
  {
    LibrarySystem placeholderLibrarySystem = librarySystem;
    this.librarySystem = null;
    if(placeholderLibrarySystem != null)
    {
      placeholderLibrarySystem.removePerson(this);
    }
    for(int i=books.size(); i > 0; i--)
    {
      Book aBook = books.get(i - 1);
      aBook.delete();
    }
    for(int i=movies.size(); i > 0; i--)
    {
      Movie aMovy = movies.get(i - 1);
      aMovy.delete();
    }
    for(int i=musics.size(); i > 0; i--)
    {
      Music aMusic = musics.get(i - 1);
      aMusic.delete();
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "authorID" + ":" + getAuthorID()+ "," +
            "firstName" + ":" + getFirstName()+ "," +
            "lastName" + ":" + getLastName()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "librarySystem = "+(getLibrarySystem()!=null?Integer.toHexString(System.identityHashCode(getLibrarySystem())):"null");
  }
}