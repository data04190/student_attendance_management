package bitcamp.myapp.vo;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

public class Attendance implements Serializable {

  private static final long serialVersionUID = 1L;

  final LocalTime lateTime = LocalTime.parse("09:30");

  private int no;
  private LocalDate date;
  private Member studentNo;
  private LocalTime entryTime;
  private LocalTime exitTime;
  private LocalTime studyTime;
  private String lateStatus;

  public Attendance() {}

  public Attendance(int no) {
    this.no = no;
  }

  public boolean equals(Object obj) {
    if (obj == null) {
      return false;
    }

    if (this.getClass() != obj.getClass()) {
      return false;
    }

    Attendance m = (Attendance) obj;

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

  public Member getStudentNo() {
    return studentNo;
  }

  public void setStudentNo(Member studentNo) {
    this.studentNo = studentNo;
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

  public void setExitTime(LocalTime exitTime) {
    this.exitTime = exitTime;
  }

  public LocalTime getStudyTime() {
    return this.studyTime;
  }

  public void setStudyTime(LocalTime studyTime) {
    this.studyTime = studyTime;
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
