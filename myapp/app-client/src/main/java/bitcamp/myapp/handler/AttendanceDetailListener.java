package bitcamp.myapp.handler;

import bitcamp.myapp.dao.AttendanceDao;
import bitcamp.myapp.vo.Attendance;
import bitcamp.util.ActionListener;
import bitcamp.util.BreadcrumbPrompt;


public class AttendanceDetailListener implements ActionListener {

  AttendanceDao memberDao;

  public AttendanceDetailListener(AttendanceDao memberDao) {
    this.memberDao = memberDao;
  }

  public void service(BreadcrumbPrompt prompt) {
    int memberNo = prompt.inputInt("번호? ");

    Attendance m = memberDao.findBy(memberNo);
    if (m == null) {
      System.out.println("해당 번호의 회원이 없습니다.");
      return;
    }

    System.out.printf("이름: %s\n", m.getStudentNo().getName());
    System.out.printf("입실 시간: %s\n", m.getEntryTime());
    System.out.printf("퇴실 시간: %s\n", m.getExitTime());
    System.out.printf("스터디 시간: %s\n", m.getStudyTime());
    System.out.printf("지각 여부: %s\n", m.getLateStatus());
  }
}
