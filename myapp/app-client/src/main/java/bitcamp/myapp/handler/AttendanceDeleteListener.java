package bitcamp.myapp.handler;

import bitcamp.myapp.dao.AttendanceDao;
import bitcamp.util.ActionListener;
import bitcamp.util.BreadcrumbPrompt;

public class AttendanceDeleteListener implements ActionListener {

  AttendanceDao memberDao;

  public AttendanceDeleteListener(AttendanceDao memberDao) {
    this.memberDao = memberDao;
  }

  public void service(BreadcrumbPrompt prompt) {
    if (memberDao.delete(prompt.inputInt("번호? ")) == 0) {
      System.out.println("해당 번호의 회원이 없습니다!");
    }
  }
}
