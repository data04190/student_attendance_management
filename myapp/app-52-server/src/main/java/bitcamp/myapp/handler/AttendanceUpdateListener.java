package bitcamp.myapp.handler;

import java.io.IOException;
import java.time.LocalTime;
import org.apache.ibatis.session.SqlSessionFactory;
import bitcamp.myapp.dao.AttendanceDao;
import bitcamp.myapp.vo.Attendance;
import bitcamp.util.BreadcrumbPrompt;

public class AttendanceUpdateListener implements AttendanceActionListener {

  AttendanceDao attendanceDao;
  SqlSessionFactory sqlSessionFactory;

  public AttendanceUpdateListener(AttendanceDao memberDao, SqlSessionFactory sqlSessionFactory) {
    this.attendanceDao = memberDao;
    this.sqlSessionFactory = sqlSessionFactory;
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
      sqlSessionFactory.openSession(false).commit();

    } catch (Exception e) {
      sqlSessionFactory.openSession(false).rollback();
      throw new RuntimeException(e);
    }

  }

}
