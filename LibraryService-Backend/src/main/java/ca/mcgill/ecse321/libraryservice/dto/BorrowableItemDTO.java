package ca.mcgill.ecse321.libraryservice.dto;

public class BorrowableItemDTO {
  private enum ItemState { Borrowed, Damaged, Available, Reserved, Booked }

  private ItemState state;
  private LibraryItemDTO libraryItem;

  public BorrowableItemDTO(){}

  public BorrowableItemDTO(ItemState state, LibraryItemDTO item){
      this.state = state;
      this.libraryItem = item;
  }

  public ItemState getItemState(){
      return this.state;
  }

  public LibraryItemDTO getLibraryItem(){
      return this.libraryItem;
  }
    
}
