package bitcamp.myapp.vo;

import java.time.LocalDate;
import java.time.LocalTime;

public class Member {

  public static int userId = 1;
  final LocalTime lateTime = LocalTime.parse("09:30");

  private int no;
  private LocalDate date;
  private String studentName;
  private LocalTime entryTime;
  private LocalTime exitTime;
  private LocalTime studyTime;
  private String lateStatus;

  public Member() {
    this.no = userId++;
    this.date = LocalDate.now();
  }

  public Member(int no) {
    this.no = no;
  }

  public boolean equals(Object obj) {
    if (obj == null) {
      return false;
    }

    if (this.getClass() != obj.getClass()) {
      return false;
    }

    Member m = (Member) obj;

    if (this.getNo() != m.getNo()) {
      return false;
    }
    return true;
  }


  public int getNo() {
    return this.no;
  }

  public void setNo(int no) {
    this.no = no;
  }

  public LocalDate getDate() {
    return this.date;
  }

  public void setDate(LocalDate date) {
    this.date = date;
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

  public void setEntryTime(LocalTime entryTime) {
    this.entryTime = entryTime;
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

  public void setLateStatus(String lateStatus) {
    this.lateStatus = lateStatus;
  }

  public void setLateStatus() {
    this.lateStatus = this.entryTime.isAfter(lateTime) ? "O" : "X";
  }



}
