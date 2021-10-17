/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4607.2d2b84eb8 modeling language!*/

package ca.mcgill.ecse321.libraryservice.model;
import javax.persistence.*;

@Entity
// line 17 "../../../../../../library.ump 15-05-01-147.ump 15-45-27-537.ump 16-05-11-860.ump"
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

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Address(String aAddress, String aCity, String aCountry)
  {
    address = aAddress;
    city = aCity;
    country = aCountry;
    addressID = nextAddressID++;
  }

  //------------------------
  // PRIMARY KEY
  //------------------------

  public boolean setAddressID(int aAddressID)
  {
    addressID = aAddressID;
    if(addressID==aAddressID){
      return true;
    }
    else return false;
  }
  
  @Id
  public int getAddressID()
  {
    return addressID;
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


  public String toString()
  {
    return super.toString() + "["+
            "addressID" + ":" + getAddressID()+ "," +
            "address" + ":" + getAddress()+ "," +
            "city" + ":" + getCity()+ "," +
            "country" + ":" + getCountry()+ "]" + System.getProperties().getProperty("line.separator");
  }
}