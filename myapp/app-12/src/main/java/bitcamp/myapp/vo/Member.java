package bitcamp.myapp.vo;

import java.time.LocalDate;
import java.time.LocalTime;

public class Member {

  private int no;
  private LocalDate date;
  private String studentName;
  private LocalTime entryTime;
  private LocalTime exitTime;
  private LocalTime studyTime;
  private String lateStatus;

  final LocalTime lateTime = LocalTime.parse("09:30");

  public Member() {
    this.date = LocalDate.now();
  }

  public int getNo() {
    return this.no;
  }

  public int setNo(int no) {
    return this.no = no;
  }

  public LocalDate getDate() {
    return this.date;
  }

  public String getStudentName() {
    return this.studentName;
  }

  public String setStudentName(String name) {
    return this.studentName = name;
  }

  public LocalTime getEntryTime() {
    return this.entryTime;
  }

  public LocalTime setEntryTime(LocalTime entryTime) {
    return this.entryTime = entryTime;
  }

  public LocalTime getExitTime() {
    return this.exitTime;
  }

  public LocalTime setExitTime(LocalTime exitTime) {
    return this.exitTime = exitTime;
  }

  public LocalTime getStudyTime() {
    return this.studyTime;
  }

  public LocalTime setStudyTime(LocalTime studyTime) {
    return this.studyTime = studyTime;
  }

  public String getLateStatus() {
    return this.lateStatus;
  }

  public void setLateStatus() {
    this.lateStatus = this.getEntryTime().isAfter(lateTime) ? "O" : "X";
  }


}
