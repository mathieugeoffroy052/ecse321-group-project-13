/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4607.2d2b84eb8 modeling language!*/

package ca.mcgill.ecse321.libraryservice.model;
import javax.persistence.*;
import java.sql.Date;
import java.sql.Time;

@Entity
// line 73 "../../../../../../library.ump 15-05-01-147.ump 15-45-27-537.ump 16-05-11-860.ump"
public class Transaction
{

  //------------------------
  // ENUMERATIONS
  //------------------------

  public enum TransactionType { Borrow, Reserve, Waitlist }

  //------------------------
  // STATIC VARIABLES
  //------------------------

  private static int nextTransactionID = 1;

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Autounique Attributes
  private int transactionID;
  private Date deadline;

  //Transaction Associations
  private BorrowableItem item;
  private UserAccount user;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Transaction(BorrowableItem aItem, UserAccount aUser, Date aDeadline)
  {
    transactionID = nextTransactionID++;
    if (!setItem(aItem))
    {
      throw new RuntimeException("Unable to create Transaction due to aItem. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    if (!setUser(aUser))
    {
      throw new RuntimeException("Unable to create Transaction due to aUser. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    if (!setDeadline(aDeadline)){
      throw new RuntimeException("Unable to create Transaction due to aDeadline.");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------
  @Id
  public int getTransactionID()
  {
    return transactionID;
  }

  public Date getDeadline()
  {
    return deadline;
  }

  public boolean setTransactionID(int aTransactionID)
  {
    transactionID = aTransactionID;
    if(transactionID==aTransactionID){
      return true;
    }
    else return false;
  }

  public boolean setDeadline(Date aDeadline)
  {
    deadline = aDeadline;
    if(deadline==aDeadline){
      return true;
    }
    else return false;
  }


  /* Code from template association_GetOne */
  @ManyToOne(optional=false)
  public BorrowableItem getItem()
  {
    return item;
  }
  /* Code from template association_GetOne */
  @ManyToOne(optional=false)
  public UserAccount getUser()
  {
    return user;
  }
  /* Code from template association_SetUnidirectionalOne */
  public boolean setItem(BorrowableItem aNewItem)
  {
    boolean wasSet = false;
    if (aNewItem != null)
    {
      item = aNewItem;
      wasSet = true;
    }
    return wasSet;
  }
  /* Code from template association_SetUnidirectionalOne */
  public boolean setUser(UserAccount aNewUser)
  {
    boolean wasSet = false;
    if (aNewUser != null)
    {
      user = aNewUser;
      wasSet = true;
    }
    return wasSet;
  }

  public void delete()
  {
    item = null;
    user = null;
  }


  public String toString()
  {
    return super.toString() + "["+
            "transactionID" + ":" + getTransactionID()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "item = "+(getItem()!=null?Integer.toHexString(System.identityHashCode(getItem())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "user = "+(getUser()!=null?Integer.toHexString(System.identityHashCode(getUser())):"null");
  }
}