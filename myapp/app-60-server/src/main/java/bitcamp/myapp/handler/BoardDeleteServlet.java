package bitcamp.myapp.handler;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import bitcamp.myapp.vo.Board;
import bitcamp.myapp.vo.Member;

@WebServlet("/board/delete")
public class BoardDeleteServlet extends HttpServlet {

  private static final long serialVersionUID = 1L;

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    Member loginUser = (Member) request.getSession().getAttribute("loginUser");
    if (loginUser == null) {
      response.sendRedirect("/auth/form.html");
      return;
    }

    response.setContentType("text/html;charset=UTF-8");
    PrintWriter out = response.getWriter();
    // out.println("<!DOCTYPE html>");
    // out.println("<html>");
    // out.println("<head>");
    // out.println("<meta charset='UTF-8'>");
    // out.printf("<meta http-equiv='refresh' content='1;url=/board/list'>\n");
    // out.println("<title>NAVER CLOUD 학생 관리 시스템</title>");
    // out.println("</head>");
    // out.println("<body>");

    if (loginUser.getLevel() == 2) {
      Board b = new Board();
      b.setNo(Integer.parseInt(request.getParameter("no")));
      b.setWriter(loginUser);

      try {
        if (InitServlet.boardDao.delete(b) == 0) {
          throw new Exception("해당 번호의 게시글이 없거나 삭제 권한이 없습니다.");
        } else {
          response.sendRedirect("/board/list");
        }
        InitServlet.sqlSessionFactory.openSession(false).commit();

      } catch (Exception e) {
        InitServlet.sqlSessionFactory.openSession(false).rollback();
        throw new RuntimeException(e);
      }
    } else {
      out.println("<p>해당 레벨의 회원은 게시글을 삭제할 수 없습니다.</p>");
    }
    out.println("</body>");
    out.println("</html>");
  }
}

