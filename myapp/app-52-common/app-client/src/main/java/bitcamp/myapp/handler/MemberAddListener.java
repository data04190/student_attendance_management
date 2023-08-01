package bitcamp.myapp.handler;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import bitcamp.myapp.dao.MemberDao;
import bitcamp.myapp.vo.Member;
import bitcamp.util.BreadcrumbPrompt;


public class MemberAddListener implements MemberActionListener {

  MemberDao memberDao;

  public MemberAddListener(MemberDao memberDao) {
    this.memberDao = memberDao;
  }

  public void service(BreadcrumbPrompt prompt) {
    Member m = new Member();

    m.setStudentName(prompt.inputString("이름? "));
    m.setDate(LocalDate.now());

    while (true) {
      try {
        m.setEntryTime(LocalTime.parse(prompt.inputString("입실 시간(HH:MM) ")));
        break;
      } catch (DateTimeParseException e) {
        System.out.println("잘못된 형식입니다. 다시 입력해주세요.");
      }
    }

    while (true) {
      try {
        m.setExitTime(LocalTime.parse(prompt.inputString("퇴실 시간(HH:MM) ")));
        break;
      } catch (DateTimeParseException e) {
        System.out.println("잘못된 형식입니다. 다시 입력해주세요.");
      }
    }

    while (true) {
      try {
        m.setStudyTime(LocalTime.parse(prompt.inputString("스터디 시간(HH:MM) ")));
        break;
      } catch (DateTimeParseException e) {
        System.out.println("잘못된 형식입니다. 다시 입력해주세요.");
      }
    }

    m.setLateStatus();

    memberDao.insert(m);
  }
}
