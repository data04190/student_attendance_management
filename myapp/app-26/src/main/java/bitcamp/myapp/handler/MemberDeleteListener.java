package bitcamp.myapp.handler;

import java.util.List;
import bitcamp.myapp.vo.Member;
import bitcamp.util.BreadcrumbPrompt;

public class MemberDeleteListener extends AbstractMemberListener {

  public MemberDeleteListener(List<Member> list) {
    super(list);
  }

  @Override
  public void service(BreadcrumbPrompt prompt) {
    int no = prompt.inputInt("번호? ");

    for (int i = 0; i < this.list.size(); i++) {
      Member m = this.list.get(i);
      if (m.getNo() == no) {
        this.list.remove(i);
        return;
      }
    }
    System.out.println("해당 번호의 회원이 없습니다!");

  }
}
