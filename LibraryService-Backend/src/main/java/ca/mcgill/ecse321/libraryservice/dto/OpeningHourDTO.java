package ca.mcgill.ecse321.libraryservice.dto;

import java.sql.Time;

import com.fasterxml.jackson.annotation.JsonFormat;


public class OpeningHourDTO {
	@JsonFormat(shape = JsonFormat.Shape.OBJECT)
	public enum DayOfWeek { Monday, Tuesday, Wednesday, Thursday, Friday, Saturday, Sunday }

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

	public Time getStartTime() {
		return startTime;
	}

	public Time getEndTime() {
		return endTime;
	}

	public DayOfWeek getDayOfWeek() {
		return dayOfWeek;
	}

}
