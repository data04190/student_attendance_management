package bitcamp.myapp.handler;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import bitcamp.myapp.vo.Attendance;

@WebServlet("/attendance/list")
public class AttendanceListServlet extends HttpServlet {

  private static final long serialVersionUID = 1L;

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    response.setContentType("text/html;charset=UTF-8");
    PrintWriter out = response.getWriter();
    out.println("<!DOCTYPE html>");
    out.println("<html>");
    out.println("<head>");
    out.println("<meta charset='UTF-8'>");
    out.println("<title>NAVER CLOUD 학생 관리 시스템</title>");
    out.println("</head>");
    out.println("<body>");

    request.getRequestDispatcher("/header").include(request, response);

    out.println("<h1>출결 관리</h1>");
    out.println("<div style='margin:5px;'>");
    out.printf("<a href='/attendance/form'>새 글</a>\n");
    out.println("</div>");
    out.println("<table border='1'>");
    out.println("<thead>");
    out.println(
        "  <tr><th>번호</th> <th>날짜</th> <th>이름</th> <th>입실 시간</th> <th>퇴실 시간</th> <th>스터디 시간</th> <th>지각 여부</th> </tr>");
    out.println("</thead>");

    List<Attendance> list = InitServlet.attendanceDao.findAll();

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

    request.getRequestDispatcher("/footer").include(request, response);

    out.println("</body>");
    out.println("</html>");
  }
}
