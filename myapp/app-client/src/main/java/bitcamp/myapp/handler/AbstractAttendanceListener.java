package bitcamp.myapp.handler;

import java.util.List;
import bitcamp.myapp.vo.Attendance;
import bitcamp.util.ActionListener;

public abstract class AbstractAttendanceListener implements ActionListener {

  protected List<Attendance> list;

  public AbstractAttendanceListener(List<Attendance> list) {
    this.list = list;
  }

  protected Attendance findBy(int no) {
    for (int i = 0; i < this.list.size(); i++) {
      Attendance m = this.list.get(i);
      if (m.getNo() == no) {
        return m;
      }
    }
    return null;
  }

}
