package bitcamp.myapp.handler;

import bitcamp.myapp.vo.Member;
import bitcamp.util.BreadcrumbPrompt;
import bitcamp.util.List;

public class MemberDetailListener extends AbstractMemberListener {

  public MemberDetailListener(List list) {
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
    System.out.printf("이름: %s\n", m.getStudentName());
    System.out.printf("입실 시간: %s\n", m.getEntryTime());
    System.out.printf("퇴실 시간: %s\n", m.getExitTime());
    System.out.printf("스터디 시간: %s\n", m.getStudyTime());
    System.out.printf("지각 여부: %s\n", m.getLateStatus());
  }
}
