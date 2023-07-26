package bitcamp.myapp.handler;

import java.time.LocalTime;
import bitcamp.myapp.dao.AttendanceDao;
import bitcamp.myapp.vo.Attendance;
import bitcamp.util.BreadcrumbPrompt;

public class AttendanceUpdateListener implements AttendanceActionListener {

  AttendanceDao memberDao;

  public AttendanceUpdateListener(AttendanceDao memberDao) {
    this.memberDao = memberDao;
  }

  @Override
  public void service(BreadcrumbPrompt prompt) {
    int memberNo = prompt.inputInt("번호? ");

    Attendance m = memberDao.findBy(memberNo);
    if (m == null) {
      System.out.println("해당 번호의 출결 정보가 없습니다.");
      return;
    }

    // m.setStudentNo(prompt.inputInt("ID(%d)? ", m.getStudentNo()));
    m.setEntryTime(LocalTime.parse(prompt.inputString("입실 시간(%s)? ", m.getEntryTime())));
    m.setExitTime(LocalTime.parse(prompt.inputString("퇴실 시간(%s)? ", m.getExitTime())));
    m.setStudyTime(LocalTime.parse(prompt.inputString("스터디 시간(%s)? ", m.getStudyTime())));
    m.setLateStatus();

    memberDao.update(m);
  }

}
