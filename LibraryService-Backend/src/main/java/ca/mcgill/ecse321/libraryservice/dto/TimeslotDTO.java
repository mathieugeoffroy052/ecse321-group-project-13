
package ca.mcgill.ecse321.libraryservice.dto;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonFormat;

public class TimeslotDTO {
  @JsonFormat(pattern = "yyyy-MM-dd")
  private Date startDate;
  @JsonFormat(pattern = "HH:mm:ss")
  private Time startTime;
  @JsonFormat(pattern = "yyyy-MM-dd")
  private Date endDate;
  @JsonFormat(pattern = "HH:mm:ss")
  private Time endTime;
  
  private int timeslotID;
  
  private Set<LibrarianDTO> librarian;
  private HeadLibrarianDTO headLibrarian;


  @SuppressWarnings("unchecked")
  public TimeslotDTO(HeadLibrarianDTO headLibrarian){
    this.startDate = Date.valueOf("2000-01-01");
    this.startTime = Time.valueOf("00:00:00");
    this.endDate = Date.valueOf("2000-01-01"); 
    this.endTime = Time.valueOf("23:59:59");
    this.librarian = (Set<LibrarianDTO>) new ArrayList<LibrarianDTO>();
    this.headLibrarian = headLibrarian;
  }

  public TimeslotDTO(Set<LibrarianDTO> librarian, HeadLibrarianDTO headLibrarian){
    this.startDate = Date.valueOf("2000-01-01");
    this.startTime = Time.valueOf("00:00:00");
    this.endDate = Date.valueOf("2000-01-01"); 
    this.endTime = Time.valueOf("23:59:59");
    this.librarian = librarian;
    this.headLibrarian = headLibrarian;
  }

  public TimeslotDTO(Date startDate, Time startTime, Date endDate, Time endTime, Set<LibrarianDTO> librarian, HeadLibrarianDTO headLibrarian, int timeslotID){
      this.startDate = startDate;
      this.startTime = startTime;
      this.endDate = endDate;
      this.endTime = endTime;
      this.librarian = librarian;
      this.headLibrarian = headLibrarian;
      this.timeslotID = timeslotID;
  }

  public TimeslotDTO() {
  }

  public Date getStartDate(){
      return this.startDate;
  }

  public Time getStartTime(){
      return this.startTime;
  }

  public Date getEndDate(){
      return this.endDate;
  }

  public Time getEndTime(){
      return this.endTime;
  }

  public Set<LibrarianDTO> getLibrarians(){
      return this.librarian;
  }

  public HeadLibrarianDTO getHeadLibrarian(){
      return this.headLibrarian;
  }
  
  public int getTimeSlotID() {
	  return timeslotID;
  }
  
    
}
