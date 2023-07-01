package bitcamp.myapp.vo;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

public class Member implements Serializable, CsvObject {

  private static final long serialVersionUID = 1L;

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

  public static Member fromCsv(String csv) {
    String[] values = csv.split(",");

    Member member = new Member(Integer.parseInt(values[0]));
    member.setDate(LocalDate.parse(values[1]));
    member.setStudentName(values[2]);
    member.setEntryTime(LocalTime.parse(values[3]));
    member.setExitTime(LocalTime.parse(values[4]));
    member.setStudyTime(LocalTime.parse(values[5]));
    member.setLateStatus(values[6]);

    if (Member.userId <= member.getNo()) {
      Member.userId = member.getNo() + 1;
    }

    return member;
  }

  @Override
  public String toCsvString() {
    return String.format("%d,%s,%s,%s,%s,%s,%s", this.getNo(), this.getDate(),
        this.getStudentName(), this.getEntryTime(), this.getExitTime(), this.getStudyTime(),
        this.getLateStatus());
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

  public void setStudentName(String name) {
    this.studentName = name;
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
