package ca.mcgill.ecse321.libraryservice.dto;

import java.sql.Time;


public class OpeningHourDTO {
	
	private enum DayOfWeek { Monday, Tuesday, Wednesday, Thursday, Friday, Saturday, Sunday }

	private HeadLibrarianDTO headLibrarian;
	private Time startTime;
	private Time endTime;
	private DayOfWeek dayOfWeek;

	public OpeningHourDTO() {
	}

	public OpeningHourDTO(DayOfWeek aDayOfWeek, Time aStartTime, Time aEndTime, HeadLibrarianDTO aHeadLibrarian) {
		headLibrarian = aHeadLibrarian;
		startTime = aStartTime;
		endTime = aEndTime;
		dayOfWeek = aDayOfWeek;
	}

	public HeadLibrarianDTO getHeadLibrarian() {
		return headLibrarian;
	}

	public void setHeadLibrarian(HeadLibrarianDTO aHeadLibrarian) {
		headLibrarian = aHeadLibrarian;
	}

	public Time getStartTime() {
		return startTime;
	}

	public void setStartTime(Time time) {
		startTime = time;
	}

	public Time getEndTime() {
		return endTime;
	}

	public void setEndTime(Time time) {
		endTime = time;
	}

	public DayOfWeek getDayOfWeek() {
		return dayOfWeek;
	}

	public void setDayOfWeek(DayOfWeek aDayOfWeek) {
		dayOfWeek = aDayOfWeek;
	}

}
