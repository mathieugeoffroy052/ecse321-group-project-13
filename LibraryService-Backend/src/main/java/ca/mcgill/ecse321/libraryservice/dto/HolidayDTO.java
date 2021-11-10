package ca.mcgill.ecse321.libraryservice.dto;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonFormat;


public class HolidayDTO {

// use local date and times because easier to pass as JSON for URL endpoints
  @JsonFormat(pattern = "yyyy-MM-dd")
  private Date date;
  @JsonFormat(pattern = "HH:mm:ss")
  private Time startTime;
  @JsonFormat(pattern = "HH:mm:ss")
  private Time endTime;

  private HeadLibrarianDTO headLibrarian;

  public HolidayDTO(HeadLibrarianDTO headLibrarian){
    this(Date.valueOf("1999-12-25"), Time.valueOf("00:00:00"), Time.valueOf("23:59:59"), headLibrarian);
  }

  public HolidayDTO() {

  }

  public HolidayDTO(Date date, Time startTime, Time endTime, HeadLibrarianDTO headLibrarian){
      this.date = date;
      this.startTime = startTime;
      this.endTime = endTime;
      this.headLibrarian = headLibrarian;
   }

   public Date getDate(){
       return this.date;
   }

   public Time getStartTime(){
       return this.startTime;
   }
   
   public Time getEndTime(){
       return this.endTime;
   }

   public HeadLibrarianDTO getHeadLibrarian(){
       return this.headLibrarian;
   }
  
}
