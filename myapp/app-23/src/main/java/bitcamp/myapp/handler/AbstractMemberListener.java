package bitcamp.myapp.handler;

import bitcamp.myapp.vo.Member;
import bitcamp.util.ActionListener;
import bitcamp.util.List;

public abstract class AbstractMemberListener implements ActionListener {

  protected List list;

  public AbstractMemberListener(List list) {
    this.list = list;
  }

  protected Member findBy(int no) {
    for (int i = 0; i < this.list.size(); i++) {
      Member m = (Member) this.list.get(i);
      if (m.getNo() == no) {
        return m;
      }
    }
    return null;
  }

}
