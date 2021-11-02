package ca.mcgill.ecse321.libraryservice.dto;

import java.sql.Date;

import ca.mcgill.ecse321.libraryservice.model.BorrowableItem;
import ca.mcgill.ecse321.libraryservice.model.UserAccount;

public class TransactionDTO {
  private enum TransactionType { Borrowing, ItemReservation, RoomReservation, Waitlist, Renewal, Return }

  private TransactionType transactionType;

  private Date deadline;

  private BorrowableItem borrowableItem;
  private UserAccount userAccount;
  

  public TransactionDTO(TransactionType type, BorrowableItem borrowableItem, UserAccount userAccount){
    this(type, Date.valueOf("2001-01-01"), borrowableItem, userAccount);
  }

  public TransactionDTO(TransactionType type, Date deadline, BorrowableItem borrowableItem, UserAccount userAccount){
      this.transactionType = type;
      this.deadline = deadline;
      this.borrowableItem = borrowableItem;
      this.userAccount = userAccount;
  } 

  public TransactionType getTransactionType(){
      return this.transactionType;
  }

  public Date getDeadline(){
      return this.deadline;
  }

  public BorrowableItem getBorrowableItem(){
    return this.borrowableItem;
  }

  public UserAccount getUserAccount(){
      return this.userAccount;
  }

}
