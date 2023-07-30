package bitcamp.myapp.handler;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import bitcamp.myapp.dao.AttendanceDao;
import bitcamp.myapp.vo.Attendance;
import bitcamp.myapp.vo.Member;
import bitcamp.util.BreadcrumbPrompt;


public class AttendanceAddListener implements AttendanceActionListener {

  AttendanceDao attendanceDao;

  public AttendanceAddListener(AttendanceDao attendanceDao) {
    this.attendanceDao = attendanceDao;
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

    attendanceDao.insert(a);
  }
}
