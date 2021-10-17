package ca.mcgill.ecse321.libraryservice.dao;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.libraryservice.model.Patron;
import ca.mcgill.ecse321.libraryservice.model.Room;

public interface RoomRepository extends CrudRepository<Room, Integer>{

	
	Room findRoomByIsbn(int isbn);
}