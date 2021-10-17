/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4607.2d2b84eb8 modeling language!*/

package ca.mcgill.ecse321.libraryservice.model;
import javax.persistence.*;
import java.sql.Date;

@Entity
// line 73 "../../../../../../library.ump 15-05-01-147.ump 15-45-27-537.ump 16-05-11-860.ump"
public class Transaction
{

  //------------------------
  // ENUMERATIONS
  //------------------------

  public enum TransactionType { Borrowing, Reservation, Waitlist, Renewal, Return }

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
  private BorrowableItem borrowableItem;
  private UserAccount userAccount;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Transaction(BorrowableItem aItem, UserAccount aUserAccount, Date aDeadline)
  {
    transactionID = nextTransactionID++;
    if (!setBorrowableItem(aItem))
    {
      throw new RuntimeException("Unable to create Transaction due to aItem. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    if (!setUserAccount(aUserAccount))
    {
      throw new RuntimeException("Unable to create Transaction due to aUser. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    if (!setDeadline(aDeadline)){
      throw new RuntimeException("Unable to create Transaction due to aDeadline.");
    }
  }

  //------------------------
  // PRIMARY KEY
  //------------------------

  public boolean setTransactionID(int aTransactionID)
  {
    transactionID = aTransactionID;
    if(transactionID==aTransactionID){
      return true;
    }
    else return false;
  }

  @Id
  public int getTransactionID()
  {
    return transactionID;
  }

  //------------------------
  // INTERFACE
  //------------------------

  public Date getDeadline()
  {
    return deadline;
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
  public BorrowableItem getBorrowableItem()
  {
    return borrowableItem;
  }

  /* Code from template association_GetOne */
  @ManyToOne(optional=false)
  public UserAccount getUserAccount()
  {
    return userAccount;
  }

  /* Code from template association_SetUnidirectionalOne */
  public boolean setBorrowableItem(BorrowableItem aNewItem)
  {
    boolean wasSet = false;
    if (aNewItem != null)
    {
      borrowableItem = aNewItem;
      wasSet = true;
    }
    return wasSet;
  }
  
  /* Code from template association_SetUnidirectionalOne */
  public boolean setUserAccount(UserAccount aNewUser)
  {
    boolean wasSet = false;
    if (aNewUser != null)
    {
      userAccount = aNewUser;
      wasSet = true;
    }
    return wasSet;
  }

  public void delete()
  {
    borrowableItem = null;
    userAccount = null;
  }


  public String toString()
  {
    return super.toString() + "["+
            "transactionID" + ":" + getTransactionID()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "borrowableitem = "+(getBorrowableItem()!=null?Integer.toHexString(System.identityHashCode(getBorrowableItem())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "userAccount = "+(getUserAccount()!=null?Integer.toHexString(System.identityHashCode(getUserAccount())):"null");
  }
}