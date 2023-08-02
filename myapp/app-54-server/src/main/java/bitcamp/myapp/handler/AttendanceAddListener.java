package bitcamp.myapp.handler;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import org.apache.ibatis.session.SqlSessionFactory;
import bitcamp.myapp.dao.AttendanceDao;
import bitcamp.myapp.vo.Attendance;
import bitcamp.myapp.vo.Member;
import bitcamp.util.BreadcrumbPrompt;
import bitcamp.util.Component;

@Component("/attendance/add")
public class AttendanceAddListener implements AttendanceActionListener {

  AttendanceDao attendanceDao;
  SqlSessionFactory sqlSessionFactory;

  public AttendanceAddListener(AttendanceDao attendanceDao, SqlSessionFactory sqlSessionFactory) {
    this.attendanceDao = attendanceDao;
    this.sqlSessionFactory = sqlSessionFactory;

  }

  public void service(BreadcrumbPrompt prompt) throws IOException {
    Attendance a = new Attendance();

    a.setDate(LocalDate.now());

    Member writer = new Member();
    writer.setNo(prompt.inputInt("학생 ID? "));
    a.setStudentNo(writer);

    while (true) {
      try {
        a.setEntryTime(LocalTime.parse(prompt.inputString("입실 시간(HH:MM) ")));
        break;
      } catch (DateTimeParseException e) {
        prompt.println("잘못된 형식입니다. 다시 입력해주세요.");
      }
    }

    while (true) {
      try {
        a.setExitTime(LocalTime.parse(prompt.inputString("퇴실 시간(HH:MM) ")));
        break;
      } catch (DateTimeParseException e) {
        prompt.println("잘못된 형식입니다. 다시 입력해주세요.");
      }
    }

    while (true) {
      try {
        a.setStudyTime(LocalTime.parse(prompt.inputString("스터디 시간(HH:MM) ")));
        break;
      } catch (DateTimeParseException e) {
        prompt.println("잘못된 형식입니다. 다시 입력해주세요.");
      }
    }

    a.setLateStatus();

    try {
      attendanceDao.insert(a);
      sqlSessionFactory.openSession(false).commit();
    } catch (Exception e) {
      sqlSessionFactory.openSession(false).rollback();
      throw new RuntimeException(e);
    }

  }
}
