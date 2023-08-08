package bitcamp.myapp.handler;

import java.io.PrintWriter;
import bitcamp.util.Component;
import bitcamp.util.HttpServletRequest;
import bitcamp.util.HttpServletResponse;
import bitcamp.util.Servlet;

@Component("/attendance/form")
public class AttendanceFormServlet implements Servlet {

  @Override
  public void service(HttpServletRequest request, HttpServletResponse response) throws Exception {
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
    out.println("<form action='/attendance/add' method='post'>");
    out.println("학생 번호: <input type='number' name='studentNo.no'><br>"); // 학생 번호 입력
    out.println("입실 시간: <input type='time' name='entryTime'><br>"); // 입실 시간 입력
    out.println("퇴실 시간: <input type='time' name='exitTime'><br>"); // 퇴실 시간 입력
    out.println("스터디 시간: <input type='time' name='studyTime'><br>");
    out.println("<button>등록</button>");
    out.println("</form>");
    out.println("</body>");
    out.println("</html>");
  }

}
