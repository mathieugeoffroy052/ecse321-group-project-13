package ca.mcgill.ecse321.libraryservice.dto;

import java.sql.Time;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;


public class OpeningHourDTO {

	private HeadLibrarianDTO headLibrarian;
	@JsonFormat(pattern = "HH:mm:ss")
	private Time startTime;
	@JsonFormat(pattern = "HH:mm:ss")
	private Time endTime;
	private String dayOfWeek;
	private int openingHourID;

	public OpeningHourDTO() {
	}

	public OpeningHourDTO(String aDayOfWeek, Time aStartTime, Time aEndTime, HeadLibrarianDTO aHeadLibrarian) {
		headLibrarian = aHeadLibrarian;
		startTime = aStartTime;
		endTime = aEndTime;
		dayOfWeek = aDayOfWeek;
	}
	
	public OpeningHourDTO(String aDayOfWeek, Time aStartTime, Time aEndTime, HeadLibrarianDTO aHeadLibrarian, int anOpeningHourID) {
		headLibrarian = aHeadLibrarian;
		startTime = aStartTime;
		endTime = aEndTime;
		dayOfWeek = aDayOfWeek;
		openingHourID = anOpeningHourID;
	}

	public HeadLibrarianDTO getHeadLibrarian() {
		return headLibrarian;
	}

	public Time getStartTime() {
		return startTime;
	}

	public Time getEndTime() {
		return endTime;
	}

	public String getDayOfWeek() {
		return dayOfWeek;
	}
	
	public int getOpeningHourID() {
		return openingHourID;
	}

}
