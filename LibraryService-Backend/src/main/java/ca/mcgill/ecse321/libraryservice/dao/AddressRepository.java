package ca.mcgill.ecse321.libraryservice.dao;

//imports
import ca.mcgill.ecse321.libraryservice.model.Address;
import ca.mcgill.ecse321.libraryservice.model.UserAccount;
import org.springframework.data.repository.CrudRepository;
import java.util.List;

public interface AddressRepository extends CrudRepository<Address, Integer> {

	Address findAddressByAddressID(Integer addressID);
	
	List<Address> findByUserID(Integer userID);
}
