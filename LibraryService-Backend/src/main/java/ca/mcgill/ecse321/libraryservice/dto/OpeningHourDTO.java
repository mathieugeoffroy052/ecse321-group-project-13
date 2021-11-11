package ca.mcgill.ecse321.libraryservice.dto;

import java.sql.Time;


public class OpeningHourDTO {
	
	public enum DayOfWeek { Monday, Tuesday, Wednesday, Thursday, Friday, Saturday, Sunday }

	private HeadLibrarianDTO headLibrarian;
	private Time startTime;
	private Time endTime;
	private DayOfWeek dayOfWeek;
	private int openingHourID;

	public OpeningHourDTO() {
	}

	public OpeningHourDTO(DayOfWeek aDayOfWeek, Time aStartTime, Time aEndTime, HeadLibrarianDTO aHeadLibrarian) {
		headLibrarian = aHeadLibrarian;
		startTime = aStartTime;
		endTime = aEndTime;
		dayOfWeek = aDayOfWeek;
	}
	
	public OpeningHourDTO(DayOfWeek aDayOfWeek, Time aStartTime, Time aEndTime, HeadLibrarianDTO aHeadLibrarian, int anOpeningHourID) {
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

	public DayOfWeek getDayOfWeek() {
		return dayOfWeek;
	}
	
	public int getOpeningHourID() {
		return openingHourID;
	}

}
