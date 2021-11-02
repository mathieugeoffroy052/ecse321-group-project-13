package ca.mcgill.ecse321.libraryservice.dto;

import ca.mcgill.ecse321.libraryservice.model.LibraryItem;

public class BorrowableItemDTO {
  private enum ItemState { Borrowed, Damaged, Available, Reserved, Booked }

  private ItemState state;
  private LibraryItem libraryItem;

  public BorrowableItemDTO(){}

  public BorrowableItemDTO(ItemState state, LibraryItem item){
      this.state = state;
      this.libraryItem = item;
  }

  public ItemState getItemState(){
      return this.state;
  }

  public LibraryItem getLibraryItem(){
      return this.libraryItem;
  }
    
}
