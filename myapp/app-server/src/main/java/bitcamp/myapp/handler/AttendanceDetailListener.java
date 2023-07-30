package bitcamp.myapp.handler;

import java.io.IOException;
import bitcamp.myapp.dao.AttendanceDao;
import bitcamp.myapp.vo.Attendance;
import bitcamp.util.ActionListener;
import bitcamp.util.BreadcrumbPrompt;


public class AttendanceDetailListener implements ActionListener {

  AttendanceDao memberDao;

  public AttendanceDetailListener(AttendanceDao memberDao) {
    this.memberDao = memberDao;
  }

  public void service(BreadcrumbPrompt prompt) throws IOException {
    int memberNo = prompt.inputInt("번호? ");

    Attendance m = memberDao.findBy(memberNo);
    if (m == null) {
      prompt.println("해당 번호의 회원이 없습니다.");
      return;
    }

    prompt.printf("이름: %s\n", m.getStudentNo().getName());
    prompt.printf("입실 시간: %s\n", m.getEntryTime());
    prompt.printf("퇴실 시간: %s\n", m.getExitTime());
    prompt.printf("스터디 시간: %s\n", m.getStudyTime());
    prompt.printf("지각 여부: %s\n", m.getLateStatus());
  }
}
