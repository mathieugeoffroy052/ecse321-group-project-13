package ca.mcgill.ecse321.libraryservice.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

public class BorrowableItemDTO {

  @JsonFormat(shape = JsonFormat.Shape.OBJECT)
  public enum ItemState { Borrowed, Damaged, Available, Reserved, Booked }

  private ItemState state;
  private LibraryItemDTO libraryItem;
  private int barCodeNumber;

  public BorrowableItemDTO(){}

  public BorrowableItemDTO(ItemState state, LibraryItemDTO item, int barCodeNumber){
      this.state = state;
      this.libraryItem = item;
      this.barCodeNumber = barCodeNumber;
  }

  public ItemState getItemState(){
      return this.state;
  }

  public LibraryItemDTO getLibraryItem(){
      return this.libraryItem;
  }

  public int getBarCodeNumber() {
      return this.barCodeNumber;
  }
    
}
