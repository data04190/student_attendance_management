package bitcamp.myapp.handler;

import java.io.PrintWriter;
import bitcamp.myapp.dao.AttendanceDao;
import bitcamp.myapp.vo.Attendance;
import bitcamp.util.Component;
import bitcamp.util.HttpServletRequest;
import bitcamp.util.HttpServletResponse;
import bitcamp.util.Servlet;

@Component("/attendance/detail")
public class AttendanceDetailListener implements Servlet {

  AttendanceDao attendanceDao;

  public AttendanceDetailListener(AttendanceDao attendanceDao) {
    this.attendanceDao = attendanceDao;
  }

  @Override
  public void service(HttpServletRequest request, HttpServletResponse response) throws Exception {

    Attendance a = attendanceDao.findBy(Integer.parseInt(request.getParameter("no")));

    response.setContentType("text/html;charset=UTF-8");
    PrintWriter out = response.getWriter();
    out.println("<!DOCTYPE html>");
    out.println("<html>");
    out.println("<head>");
    out.println("<meta charset='UTF-8'>");
    out.println("<title>출결 체크</title>");
    out.println("</head>");
    out.println("<body>");
    out.println("<h1>출결 체크</h1>");

    if (a == null) {
      out.println("<p>해당 번호의 게시글이 없습니다!</p>");

    } else {
      out.println("<style>");
      out.println("input[type='time'] { width: 150px; }"); // 여기서 150px 부분을 원하는 넓이로 조절하시면 됩니다.
      out.println("</style>");
      out.println("<form action='/attendance/update' method='post'>");
      out.println("<table border='1'>");

      out.printf("<tr><th style='width:120px;'>번호</th>"
          + " <td style='width:150px;'><input type='text' name='no' value='%d' readonly></td></tr>\n",
          a.getNo());

      out.printf("<tr><th>이름</th> <td>%s</td></tr>\n", a.getStudentNo().getName());
      out.printf(
          "<tr><th>입실 시간</th>" + " <td><input type='time' name='entryTime' value='%s'></td></tr>\n",
          a.getEntryTime());
      out.printf(
          "<tr><th>퇴실 시간</th>" + " <td><input type='time' name='exitTime' value='%s'></td></tr>\n",
          a.getExitTime());
      out.printf(
          "<tr><th>스터디 시간</th>"
              + " <td><input type='time' name='studyTime' value='%s'></td></tr>\n",
          a.getStudyTime());
      out.printf("<tr><th>지각 여부</th> <td>%s</td></tr>\n", a.getLateStatus());
      out.println("</table>");

      out.println("<div>");
      out.println("<button>변경</button>");
      out.println("<button type='reset'>초기화</button>");
      out.printf("<a href='/attendance/delete?no=%d'>삭제</a>\n", a.getNo());
      out.printf("<a href='/attendance/list'>목록</a>\n");
      out.println("</div>");
      out.println("</form>");
    }

    out.println("</body>");
    out.println("</html>");
  }
}
