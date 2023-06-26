package bitcamp.myapp.handler;

import java.time.LocalTime;
import bitcamp.myapp.vo.Member;
import bitcamp.util.BreadcrumbPrompt;
import bitcamp.util.List;

public class MemberUpdateListener extends AbstractMemberListener {

  public MemberUpdateListener(List list) {
    super(list);
  }

  @Override
  public void service(BreadcrumbPrompt prompt) {
    int memberNo = prompt.inputInt("번호? ");

    Member m = this.findBy(memberNo);
    if (m == null) {
      System.out.println("해당 번호의 회원이 없습니다.");
      return;
    }

    m.setStudentName(prompt.inputString("이름(%s)? ", m.getStudentName()));
    m.setEntryTime(LocalTime.parse(prompt.inputString("입실 시간(%s)? ", m.getEntryTime())));
    m.setExitTime(LocalTime.parse(prompt.inputString("퇴실 시간(%s)? ", m.getExitTime())));
    m.setStudyTime(LocalTime.parse(prompt.inputString("스터디 시간(%s)? ", m.getStudyTime())));
    m.setLateStatus();
  }

}
