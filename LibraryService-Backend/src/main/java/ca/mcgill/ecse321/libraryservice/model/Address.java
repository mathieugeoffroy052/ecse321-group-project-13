/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4607.2d2b84eb8 modeling language!*/

package ca.mcgill.ecse321.libraryservice.model;
import javax.persistence.*;

@Entity
// line 16 "../../../../../../library.ump 15-05-01-147.ump 15-45-27-537.ump"
public class Address
{

  //------------------------
  // STATIC VARIABLES
  //------------------------

  private static int nextAddressID = 1;

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Address Attributes
  private String address;
  private String city;
  private String country;

  //Autounique Attributes
  private int addressID;

  //Address Associations
  private LibrarySystem librarySystem;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Address(String aAddress, String aCity, String aCountry, LibrarySystem aLibrarySystem)
  {
    address = aAddress;
    city = aCity;
    country = aCountry;
    addressID = nextAddressID++;
    boolean didAddLibrarySystem = setLibrarySystem(aLibrarySystem);
    if (!didAddLibrarySystem)
    {
      throw new RuntimeException("Unable to create address due to librarySystem. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setAddress(String aAddress)
  {
    boolean wasSet = false;
    address = aAddress;
    wasSet = true;
    return wasSet;
  }

  public boolean setCity(String aCity)
  {
    boolean wasSet = false;
    city = aCity;
    wasSet = true;
    return wasSet;
  }

  public boolean setCountry(String aCountry)
  {
    boolean wasSet = false;
    country = aCountry;
    wasSet = true;
    return wasSet;
  }

  public String getAddress()
  {
    return address;
  }

  public String getCity()
  {
    return city;
  }

  public String getCountry()
  {
    return country;
  }

  @Id
  public int getAddressID()
  {
    return addressID;
  }
  /* Code from template association_GetOne */
  
  public LibrarySystem getLibrarySystem()
  {
    return librarySystem;
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
      existingLibrarySystem.removeAddress(this);
    }
    librarySystem.addAddress(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    LibrarySystem placeholderLibrarySystem = librarySystem;
    this.librarySystem = null;
    if(placeholderLibrarySystem != null)
    {
      placeholderLibrarySystem.removeAddress(this);
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "addressID" + ":" + getAddressID()+ "," +
            "address" + ":" + getAddress()+ "," +
            "city" + ":" + getCity()+ "," +
            "country" + ":" + getCountry()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "librarySystem = "+(getLibrarySystem()!=null?Integer.toHexString(System.identityHashCode(getLibrarySystem())):"null");
  }
}