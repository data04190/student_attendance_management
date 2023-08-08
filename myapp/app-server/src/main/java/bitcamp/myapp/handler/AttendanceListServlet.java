package bitcamp.myapp.handler;

import java.io.PrintWriter;
import java.util.List;
import bitcamp.myapp.dao.AttendanceDao;
import bitcamp.myapp.vo.Attendance;
import bitcamp.util.Component;
import bitcamp.util.HttpServletRequest;
import bitcamp.util.HttpServletResponse;
import bitcamp.util.Servlet;

@Component("/attendance/list")
public class AttendanceListServlet implements Servlet {

  AttendanceDao attendanceDao;

  public AttendanceListServlet(AttendanceDao attendanceDao) {
    this.attendanceDao = attendanceDao;
  }

  @Override
  public void service(HttpServletRequest request, HttpServletResponse response) throws Exception {

    response.setContentType("text/html;charset=UTF-8");
    PrintWriter out = response.getWriter();
    out.println("<!DOCTYPE html>");
    out.println("<html>");
    out.println("<head>");
    out.println("<meta charset='UTF-8'>");
    out.println("<title>출결 관리</title>");
    out.println("</head>");
    out.println("<body>");
    out.println("<h1>출결 관리</h1>");
    out.println("<div style='margin:5px;'>");
    out.printf("<a href='/attendance/form'>새 글</a>\n");
    out.println("</div>");
    out.println("<table border='1'>");
    out.println("<thead>");
    out.println(
        "  <tr><th>번호</th> <th>날짜</th> <th>이름</th> <th>입실 시간</th> <th>퇴실 시간</th> <th>스터디 시간</th> <th>지각 여부</th> </tr>");
    out.println("</thead>");

    List<Attendance> list = attendanceDao.findAll();

    out.println("<tbody>");
    for (Attendance a : list) {
      out.printf(
          "<tr>" + " <td>%d</td>" + " <td><a href='/attendance/detail?no=%d'>%s</a></td>"
              + " <td>%s</td>" + " <td>%s</td>" + " <td>%s</td>" + " <td>%s</td>"
              + " <td>%s</td></tr>\n",
          a.getNo(), a.getNo(), a.getDate(), a.getStudentNo().getName(), a.getEntryTime(),
          a.getExitTime(), a.getStudyTime(), a.getLateStatus());
    }
    out.println("</tbody>");
    out.println("</table>");
    out.println("<a href='/'>메인</a>");
    out.println("</body>");
    out.println("</html>");
  }
}
