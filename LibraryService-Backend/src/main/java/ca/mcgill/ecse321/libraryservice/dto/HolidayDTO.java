package ca.mcgill.ecse321.libraryservice.dto;

import java.sql.Date;
import java.sql.Time;

import ca.mcgill.ecse321.libraryservice.model.HeadLibrarian;

public class HolidayDTO {
  private Date date;
  private Time startTime;
  private Time endtime;

  private HeadLibrarian headLibrarian;

  public HolidayDTO(HeadLibrarian headLibrarian){
    this(Date.valueOf("1999-12-25"), Time.valueOf("00:00:00"), Time.valueOf("23:59:59"), headLibrarian);
  }

  public HolidayDTO(Date date, Time startTime, Time endTime, HeadLibrarian headLibrarian){
      this.date = date;
      this.startTime = startTime;
      this.endtime = endTime;
      this.headLibrarian = headLibrarian;
   }

   public Date getDate(){
       return this.date;
   }

   public Time getStartTime(){
       return this.startTime;
   }
   
   public Time getEndTime(){
       return this.endtime;
   }

   public HeadLibrarian getHeadLibrarian(){
       return this.headLibrarian;
   }
  
}
