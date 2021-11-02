package ca.mcgill.ecse321.libraryservice.dto;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Set;

import ca.mcgill.ecse321.libraryservice.model.HeadLibrarian;
import ca.mcgill.ecse321.libraryservice.model.Librarian;

public class TimeslotDTO {
  private Date startDate;
  private Time startTime;
  private Date endDate;
  private Time endTime;
  
  private Set<Librarian> librarian;
  private HeadLibrarian headLibrarian;


  @SuppressWarnings("unchecked")
  public TimeslotDTO(HeadLibrarian headLibrarian){
    this(Date.valueOf("2000-01-01"), Time.valueOf("00:00:00"), Date.valueOf("2000-01-01"), Time.valueOf("23:59:59"), (Set<Librarian>) new ArrayList<Librarian>(), headLibrarian);
  }

  public TimeslotDTO(Set<Librarian> librarian, HeadLibrarian headLibrarian){
      this(Date.valueOf("2000-01-01"), Time.valueOf("00:00:00"), Date.valueOf("2000-01-01"), Time.valueOf("23:59:59"), librarian, headLibrarian);
  }

  public TimeslotDTO(Date startDate, Time startTime, Date endDate, Time endTime, Set<Librarian> librarian, HeadLibrarian headLibrarian){
      this.startDate = startDate;
      this.startTime = startTime;
      this.endDate = endDate;
      this.endTime = endTime;
      this.librarian = librarian;
      this.headLibrarian = headLibrarian;
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

  public Set<Librarian> getLibrarians(){
      return this.librarian;
  }

  public HeadLibrarian getHeadLibrarian(){
      return this.headLibrarian;
  }
  
    
}
