package bitcamp.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import bitcamp.myapp.dao.MemberDao;
import bitcamp.myapp.vo.Member;

public class MySQLMemberDao implements MemberDao {

  Connection con;

  public MySQLMemberDao(Connection con) {
    this.con = con;
  }

  @Override
  public void insert(Member member) {
    try (PreparedStatement stmt = con.prepareStatement(
        "insert into myapp_member(date, student_name, entry_time, exit_time, study_time, late_status) values(?, ?, ?, ?, ?, ?)")) {

      stmt.setString(1, member.getDate().toString());
      stmt.setString(2, member.getStudentName());
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
  public List<Member> list() {
    try (PreparedStatement stmt = con.prepareStatement(
        "select member_no, date, student_name, entry_time, exit_time, study_time, late_status from myapp_member order by member_no asc");
        ResultSet rs = stmt.executeQuery()) {

      List<Member> list = new ArrayList<>();

      while (rs.next()) {
        Member m = new Member();
        m.setNo(rs.getInt("member_no"));
        m.setDate(rs.getDate("date").toLocalDate());
        m.setStudentName(rs.getString("student_name"));
        m.setEntryTime(rs.getTime("entry_time").toLocalTime());
        m.setExitTime(rs.getTime("exit_time").toLocalTime());
        m.setStudyTime(rs.getTime("study_time").toLocalTime());
        m.setLateStatus(rs.getString("late_status"));

        list.add(m);
      }

      return list;

    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public Member findBy(int no) {
    try (PreparedStatement stmt = con.prepareStatement(
        "select member_no, date, student_name, entry_time, exit_time, study_time, late_status from myapp_member where member_no=?")) {
      stmt.setInt(1, no);

      try (ResultSet rs = stmt.executeQuery()) {

        if (rs.next()) {
          Member m = new Member();
          m.setNo(rs.getInt("member_no"));
          m.setDate(rs.getDate("date").toLocalDate());
          m.setStudentName(rs.getString("student_name"));
          m.setEntryTime(rs.getTime("entry_time").toLocalTime());
          m.setExitTime(rs.getTime("exit_time").toLocalTime());
          m.setStudyTime(rs.getTime("study_time").toLocalTime());
          m.setLateStatus(rs.getString("late_status"));

          return m;
        }

        return null;
      }

    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public int update(Member member) {
    try (PreparedStatement stmt = con.prepareStatement(
        "update myapp_member set student_name=?, entry_time=?, exit_time=?, study_time=?, late_status=? where member_no=?")) {

      stmt.setString(1, member.getStudentName());
      stmt.setTime(2, Time.valueOf(member.getEntryTime()));
      stmt.setTime(3, Time.valueOf(member.getExitTime()));
      stmt.setTime(4, Time.valueOf(member.getStudyTime()));
      stmt.setString(5, member.getLateStatus());
      stmt.setInt(6, member.getNo());

      return stmt.executeUpdate();

    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public int delete(int no) {
    try (PreparedStatement stmt =
        con.prepareStatement("delete from myapp_member where member_no=?")) {

      stmt.setInt(1, no);

      return stmt.executeUpdate();

    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}
