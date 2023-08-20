package bitcamp.myapp.handler;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/attendance/form")
public class AttendanceFormServlet extends HttpServlet {

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
    out.println("<h1>출결 체크</h1>");
    out.println("<form action='/attendance/add' method='post'>");
    // out.println("학생 번호: <input type='number' name='studentNo.no'><br>"); // 학생 번호 입력
    out.println("입실 시간: <input type='time' name='entryTime'><br>"); // 입실 시간 입력
    out.println("퇴실 시간: <input type='time' name='exitTime'><br>"); // 퇴실 시간 입력
    out.println("스터디 시간: <input type='time' name='studyTime'><br>");
    out.println("<button>등록</button>");
    out.println("</form>");
    out.println("</body>");
    out.println("</html>");
  }

}
