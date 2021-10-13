package ca.mcgill.ecse321.libraryservice.model;
/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4607.2d2b84eb8 modeling language!*/

import javax.persistence.*;
import java.util.*;

@Entity
// line 119 "Library.ump"
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
  /* Code from template association_AddManyToManyMethod */
  public boolean addBook(Book aBook)
  {
    boolean wasAdded = false;
    if (books.contains(aBook)) { return false; }
    books.add(aBook);
    if (aBook.indexOfAuthor(this) != -1)
    {
      wasAdded = true;
    }
    else
    {
      wasAdded = aBook.addAuthor(this);
      if (!wasAdded)
      {
        books.remove(aBook);
      }
    }
    return wasAdded;
  }
  /* Code from template association_RemoveMany */
  public boolean removeBook(Book aBook)
  {
    boolean wasRemoved = false;
    if (!books.contains(aBook))
    {
      return wasRemoved;
    }

    int oldIndex = books.indexOf(aBook);
    books.remove(oldIndex);
    if (aBook.indexOfAuthor(this) == -1)
    {
      wasRemoved = true;
    }
    else
    {
      wasRemoved = aBook.removeAuthor(this);
      if (!wasRemoved)
      {
        books.add(oldIndex,aBook);
      }
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
  /* Code from template association_AddManyToManyMethod */
  public boolean addMovy(Movie aMovy)
  {
    boolean wasAdded = false;
    if (movies.contains(aMovy)) { return false; }
    movies.add(aMovy);
    if (aMovy.indexOfDirector(this) != -1)
    {
      wasAdded = true;
    }
    else
    {
      wasAdded = aMovy.addDirector(this);
      if (!wasAdded)
      {
        movies.remove(aMovy);
      }
    }
    return wasAdded;
  }
  /* Code from template association_RemoveMany */
  public boolean removeMovy(Movie aMovy)
  {
    boolean wasRemoved = false;
    if (!movies.contains(aMovy))
    {
      return wasRemoved;
    }

    int oldIndex = movies.indexOf(aMovy);
    movies.remove(oldIndex);
    if (aMovy.indexOfDirector(this) == -1)
    {
      wasRemoved = true;
    }
    else
    {
      wasRemoved = aMovy.removeDirector(this);
      if (!wasRemoved)
      {
        movies.add(oldIndex,aMovy);
      }
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
  /* Code from template association_AddManyToManyMethod */
  public boolean addMusic(Music aMusic)
  {
    boolean wasAdded = false;
    if (musics.contains(aMusic)) { return false; }
    musics.add(aMusic);
    if (aMusic.indexOfArtist(this) != -1)
    {
      wasAdded = true;
    }
    else
    {
      wasAdded = aMusic.addArtist(this);
      if (!wasAdded)
      {
        musics.remove(aMusic);
      }
    }
    return wasAdded;
  }
  /* Code from template association_RemoveMany */
  public boolean removeMusic(Music aMusic)
  {
    boolean wasRemoved = false;
    if (!musics.contains(aMusic))
    {
      return wasRemoved;
    }

    int oldIndex = musics.indexOf(aMusic);
    musics.remove(oldIndex);
    if (aMusic.indexOfArtist(this) == -1)
    {
      wasRemoved = true;
    }
    else
    {
      wasRemoved = aMusic.removeArtist(this);
      if (!wasRemoved)
      {
        musics.add(oldIndex,aMusic);
      }
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
    ArrayList<Book> copyOfBooks = new ArrayList<Book>(books);
    books.clear();
    for(Book aBook : copyOfBooks)
    {
      if (aBook.numberOfAuthor() <= Book.minimumNumberOfAuthor())
      {
        aBook.delete();
      }
      else
      {
        aBook.removeAuthor(this);
      }
    }
    ArrayList<Movie> copyOfMovies = new ArrayList<Movie>(movies);
    movies.clear();
    for(Movie aMovy : copyOfMovies)
    {
      if (aMovy.numberOfDirector() <= Movie.minimumNumberOfDirector())
      {
        aMovy.delete();
      }
      else
      {
        aMovy.removeDirector(this);
      }
    }
    ArrayList<Music> copyOfMusics = new ArrayList<Music>(musics);
    musics.clear();
    for(Music aMusic : copyOfMusics)
    {
      if (aMusic.numberOfArtist() <= Music.minimumNumberOfArtist())
      {
        aMusic.delete();
      }
      else
      {
        aMusic.removeArtist(this);
      }
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