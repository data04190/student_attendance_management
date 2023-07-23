package bitcamp.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
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
    try (Statement stmt = con.createStatement()) {

      stmt.executeUpdate(String.format(
          "insert into myapp_member(date, student_name, entry_time, exit_time, study_time, late_status) values('%s', '%s', '%s', '%s', '%s', '%s')",
          member.getDate().toString(), member.getStudentName(), member.getEntryTime().toString(),
          member.getExitTime().toString(), member.getStudyTime().toString(),
          member.getLateStatus()));

    } catch (Exception e) {
      throw new RuntimeException(e);
    }

  }

  @Override
  public List<Member> list() {
    try (Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(
            "select member_no, date, student_name, entry_time, exit_time, study_time, late_status from myapp_member order by member_no asc")) {

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
    try (Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(
            "select member_no, date, student_name, entry_time, exit_time, study_time, late_status from myapp_member where member_no="
                + no)) {

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

    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public int update(Member member) {
    try (Statement stmt = con.createStatement()) {

      return stmt.executeUpdate(String.format(
          "update myapp_member set" + " student_name='%s'," + " entry_time='%s',"
              + " exit_time='%s'," + " study_time='%s'," + " late_status = '%s'"
              + " where member_no=%d",
          member.getStudentName(), member.getEntryTime(), member.getExitTime(),
          member.getStudyTime(), member.getLateStatus(), member.getNo()));

    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public int delete(int no) {
    try (Statement stmt = con.createStatement()) {

      return stmt.executeUpdate(String.format("delete from myapp_member where member_no=%d", no));

    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

}
