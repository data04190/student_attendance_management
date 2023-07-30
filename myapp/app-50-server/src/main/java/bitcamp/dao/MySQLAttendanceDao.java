package bitcamp.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import bitcamp.myapp.dao.AttendanceDao;
import bitcamp.myapp.vo.Attendance;
import bitcamp.myapp.vo.Member;

public class MySQLAttendanceDao implements AttendanceDao {

  Connection con;

  public MySQLAttendanceDao(Connection con) {
    this.con = con;
  }

  @Override
  public void insert(Attendance member) {
    try (PreparedStatement stmt = con.prepareStatement(
        "insert into myapp_student_attendance(date, student_no, entry_time, exit_time, study_time, late_status) values(?, ?, ?, ?, ?, ?)")) {

      stmt.setString(1, member.getDate().toString());
      stmt.setInt(2, member.getStudentNo().getNo());
      stmt.setString(3, member.getEntryTime().toString());
      stmt.setString(4, member.getExitTime().toString());
      stmt.setString(5, member.getStudyTime().toString());
      stmt.setString(6, member.getLateStatus());
      stmt.executeUpdate();

    } catch (Exception e) {
      throw new RuntimeException(e);
    }

  }

  @Override
  public List<Attendance> list() {
    try (PreparedStatement stmt = con.prepareStatement(
        "select a.attendance_no, a.date, m.member_no, m.name, a.entry_time, a.exit_time,a.study_time, a.late_status"
            + " from myapp_student_attendance a inner join myapp_member m"
            + " on a.student_no = m.member_no" + " order by attendance_no asc");
        ResultSet rs = stmt.executeQuery()) {

      List<Attendance> list = new ArrayList<>();

      while (rs.next()) {
        Attendance a = new Attendance();
        a.setNo(rs.getInt("attendance_no"));
        a.setDate(rs.getDate("date").toLocalDate());
        a.setEntryTime(rs.getTime("entry_time").toLocalTime());
        a.setExitTime(rs.getTime("exit_time").toLocalTime());
        a.setStudyTime(rs.getTime("study_time").toLocalTime());
        a.setLateStatus(rs.getString("late_status"));

        Member student = new Member();
        student.setNo(rs.getInt("member_no"));
        student.setName(rs.getString("name"));
        a.setStudentNo(student);

        list.add(a);
      }

      return list;

    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public Attendance findBy(int no) {
    try (PreparedStatement stmt = con.prepareStatement(
        "select a.attendance_no, a.date, m.member_no, m.name, a.entry_time, a.exit_time, a.study_time, a.late_status"
            + " from myapp_student_attendance a inner join myapp_member m"
            + " on a.student_no = m.member_no" + " where attendance_no=?")) {

      stmt.setInt(1, no);

      try (ResultSet rs = stmt.executeQuery()) {

        if (rs.next()) {
          Attendance a = new Attendance();
          a.setNo(rs.getInt("attendance_no"));
          a.setDate(rs.getDate("date").toLocalDate());
          a.setEntryTime(rs.getTime("entry_time").toLocalTime());
          a.setExitTime(rs.getTime("exit_time").toLocalTime());
          a.setStudyTime(rs.getTime("study_time").toLocalTime());
          a.setLateStatus(rs.getString("late_status"));

          Member student = new Member();
          student.setNo(rs.getInt("member_no"));
          student.setName(rs.getString("name"));
          a.setStudentNo(student);

          return a;
        }

        return null;
      }

    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public int update(Attendance attendance) {
    try (PreparedStatement stmt = con.prepareStatement(
        "update myapp_student_attendance set entry_time=?, exit_time=?, study_time=?, late_status=? where attendance_no=?")) {

      stmt.setTime(1, Time.valueOf(attendance.getEntryTime()));
      stmt.setTime(2, Time.valueOf(attendance.getExitTime()));
      stmt.setTime(3, Time.valueOf(attendance.getStudyTime()));
      stmt.setString(4, attendance.getLateStatus());
      stmt.setInt(5, attendance.getNo());

      return stmt.executeUpdate();

    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public int delete(int no) {
    try (PreparedStatement stmt =
        con.prepareStatement("delete from myapp_student_attendance where attendance_no=?")) {

      stmt.setInt(1, no);

      return stmt.executeUpdate();

    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}
