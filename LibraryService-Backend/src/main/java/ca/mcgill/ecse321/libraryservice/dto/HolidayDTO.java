package ca.mcgill.ecse321.libraryservice.dto;

import java.sql.Date;
import java.sql.Time;


public class HolidayDTO {
  private Date date;
  private Time startTime;
  private Time endtime;
  private int holidayID;

  private HeadLibrarianDTO headLibrarian;

  public HolidayDTO(HeadLibrarianDTO headLibrarian){
    this(Date.valueOf("1999-12-25"), Time.valueOf("00:00:00"), Time.valueOf("23:59:59"), headLibrarian);
  }

  public HolidayDTO(Date date, Time startTime, Time endTime, HeadLibrarianDTO headLibrarian){
      this.date = date;
      this.startTime = startTime;
      this.endtime = endTime;
      this.headLibrarian = headLibrarian;
   }
 
  public HolidayDTO(Date date, Time startTime, Time endTime, HeadLibrarianDTO headLibrarian, int holidayID){
      this.date = date;
      this.startTime = startTime;
      this.endtime = endTime;
      this.headLibrarian = headLibrarian;
      this.holidayID = holidayID;
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

   public HeadLibrarianDTO getHeadLibrarian(){
       return this.headLibrarian;
   }
   
	public int getHolidayID() {
		return holidayID;
	}
  
}
