package bitcamp.myapp.handler;

import bitcamp.myapp.vo.Member;
import bitcamp.util.BreadcrumbPrompt;
import bitcamp.util.List;

public class MemberListListener extends AbstractMemberListener {

  public MemberListListener(List list) {
    super(list);
  }

  @Override
  public void service(BreadcrumbPrompt prompt) {

    System.out.println("=====================================================================");
    System.out.println("\t날짜\t이름\t입실 시간  퇴실 시간  스터디 시간  지각 여부");
    System.out.println("=====================================================================");

    for (int i = 0; i < this.list.size(); i++) {
      Member m = (Member) this.list.get(i);
      System.out.printf("%-3d %-10s %-7s %-10s %-11s %-13s %-10s\n", m.getNo(), m.getDate(),
          m.getStudentName(), m.getEntryTime(), m.getExitTime(), m.getStudyTime(),
          m.getLateStatus());
    }
  }

}
