package bitcamp.myapp.handler;

import java.io.IOException;
import java.util.List;
import bitcamp.myapp.dao.AttendanceDao;
import bitcamp.myapp.vo.Attendance;
import bitcamp.util.ActionListener;
import bitcamp.util.BreadcrumbPrompt;


public class AttendanceListListener implements ActionListener {

  AttendanceDao attendanceDao;

  public AttendanceListListener(AttendanceDao attendanceDao) {
    this.attendanceDao = attendanceDao;
  }

  public void service(BreadcrumbPrompt prompt) throws IOException {

    prompt.println("=====================================================================");
    prompt.println("\t날짜\t이름\t입실 시간  퇴실 시간  스터디 시간  지각 여부");
    prompt.println("=====================================================================");

    // 목록에서 데이터를 대신 꺼내주는 객체를 얻는다.
    List<Attendance> list = attendanceDao.list();

    for (Attendance m : list) {
      prompt.printf("%-3d %-12s %-5s %-10s %-11s %-13s %-10s\n", m.getNo(), m.getDate(),
          m.getStudentNo().getName(), m.getEntryTime(), m.getExitTime(), m.getStudyTime(),
          m.getLateStatus());
    }
  }

}
