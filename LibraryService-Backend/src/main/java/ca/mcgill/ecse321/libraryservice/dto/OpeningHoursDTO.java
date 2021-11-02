package ca.mcgill.ecse321.libraryservice.dto;

import java.util.Collections;
import java.util.List;

import ca.mcgill.ecse321.libraryservice.model.HeadLibrarian;

public class OpeningHoursDTO {
	
	private HeadLibrarian headLibrarian;

	public OpeningHoursDTO() {
	}

	public OpeningHoursDTO(HeadLibrarian headLibrarian) {
		this.person = person;
		this.event = event;
	}

	public PersonDto getHeadLibrarian() {
		return headLibrarian;
	}

	public void setperson(PersonDto person) {
		this.person = person;
	}

	public EventDto getEvent() {
		return event;
	}

	public void setEvent(EventDto event) {
		this.event = event;
	}
}
