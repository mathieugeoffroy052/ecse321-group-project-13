package ca.mcgill.ecse321.libraryservice.dto;

import java.sql.Date;

import com.fasterxml.jackson.annotation.JsonFormat;


public class TransactionDTO {
  //@JsonFormat(shape = JsonFormat.Shape.OBJECT)
  public enum TransactionType { Borrowing, ItemReservation, RoomReservation, Waitlist, Renewal, Return }

  private TransactionType transactionType;
  
  private int transactionID;
  
  @JsonFormat(pattern = "yyyy-MM-dd")
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

  public TransactionDTO(TransactionType type, Date deadline, BorrowableItemDTO borrowableItem, UserAccountDTO userAccount, int transactionID){
      this.transactionType = type;
      this.deadline = deadline;
      this.borrowableItem = borrowableItem;
      this.userAccount = userAccount;
      this.transactionID = transactionID;
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
  
  public int getTransactionID() {
	  return transactionID;
  }

}
