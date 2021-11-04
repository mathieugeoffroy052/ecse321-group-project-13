package ca.mcgill.ecse321.libraryservice.dto;

import java.sql.Date;


public class TransactionDTO {
  public enum TransactionType { Borrowing, ItemReservation, RoomReservation, Waitlist, Renewal, Return }

  private TransactionType transactionType;

  private Date deadline;

  private BorrowableItemDTO borrowableItem;
  private UserAccountDTO userAccount;
  

  public TransactionDTO(TransactionType type, BorrowableItemDTO borrowableItem, UserAccountDTO userAccount){
    this(type, Date.valueOf("2001-01-01"), borrowableItem, userAccount);
  }

  public TransactionDTO(TransactionType type, Date deadline, BorrowableItemDTO borrowableItem, UserAccountDTO userAccount){
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

  public BorrowableItemDTO getBorrowableItem(){
    return this.borrowableItem;
  }

  public UserAccountDTO getUserAccount(){
      return this.userAccount;
  }

}
