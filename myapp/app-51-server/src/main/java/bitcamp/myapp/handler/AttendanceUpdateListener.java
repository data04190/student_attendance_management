package bitcamp.myapp.handler;

import java.io.IOException;
import java.time.LocalTime;
import bitcamp.myapp.dao.AttendanceDao;
import bitcamp.myapp.vo.Attendance;
import bitcamp.util.BreadcrumbPrompt;
import bitcamp.util.DataSource;

public class AttendanceUpdateListener implements AttendanceActionListener {

  AttendanceDao attendanceDao;
  DataSource ds;

  public AttendanceUpdateListener(AttendanceDao memberDao, DataSource ds) {
    this.attendanceDao = memberDao;
    this.ds = ds;
  }

  @Override
  public void service(BreadcrumbPrompt prompt) throws IOException {
    int memberNo = prompt.inputInt("번호? ");

    Attendance m = attendanceDao.findBy(memberNo);
    if (m == null) {
      prompt.println("해당 번호의 출결 정보가 없습니다.");
      return;
    }

    // m.setStudentNo(prompt.inputInt("ID(%d)? ", m.getStudentNo()));
    m.setEntryTime(LocalTime.parse(prompt.inputString("입실 시간(%s)? ", m.getEntryTime())));
    m.setExitTime(LocalTime.parse(prompt.inputString("퇴실 시간(%s)? ", m.getExitTime())));
    m.setStudyTime(LocalTime.parse(prompt.inputString("스터디 시간(%s)? ", m.getStudyTime())));
    m.setLateStatus();

    try {
      attendanceDao.update(m);
      ds.getConnection().commit();

    } catch (Exception e) {
      try {
        ds.getConnection().rollback();
      } catch (Exception e2) {
      }
      throw new RuntimeException(e);
    }

  }

}
