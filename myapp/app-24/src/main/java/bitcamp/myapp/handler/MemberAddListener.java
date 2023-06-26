package bitcamp.myapp.handler;

import java.time.LocalTime;
import bitcamp.myapp.vo.Member;
import bitcamp.util.BreadcrumbPrompt;
import bitcamp.util.List;

public class MemberAddListener extends AbstractMemberListener {

  public MemberAddListener(List list) {
    super(list);
  }

  @Override
  public void service(BreadcrumbPrompt prompt) {
    Member m = new Member();
    m.setStudentName(prompt.inputString("이름? "));
    m.setEntryTime(LocalTime.parse(prompt.inputString("입실 시간(HH:MM) ")));
    m.setExitTime(LocalTime.parse(prompt.inputString("퇴실 시간(HH:MM) ")));
    m.setStudyTime(LocalTime.parse(prompt.inputString("스터디 시간(HH:MM) ")));
    m.setLateStatus();

    this.list.add(m);
  }
}
