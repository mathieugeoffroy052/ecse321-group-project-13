package ca.mcgill.ecse321.libraryservice.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

public class BorrowableItemDTO {

  private String state;
  private LibraryItemDTO libraryItem;
  private int barCodeNumber;

  public BorrowableItemDTO(){}

  public BorrowableItemDTO(String state, LibraryItemDTO item, int barCodeNumber){
      this.state = state;
      this.libraryItem = item;
      this.barCodeNumber = barCodeNumber;
  }

  public String getItemState(){
      return this.state;
  }

  public LibraryItemDTO getLibraryItem(){
      return this.libraryItem;
  }

  public int getBarCodeNumber() {
      return this.barCodeNumber;
  }
    
}
