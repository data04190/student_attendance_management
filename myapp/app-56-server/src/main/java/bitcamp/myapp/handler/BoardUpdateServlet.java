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

@WebServlet("/board/update")
public class BoardUpdateServlet extends HttpServlet {

  private static final long serialVersionUID = 1L;

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    Member loginUser = (Member) request.getSession().getAttribute("loginUser");
    if (loginUser == null) {
      response.sendRedirect("/auth/form.html");
      return;
    }

    response.setContentType("text/html;charset=UTF-8");
    PrintWriter out = response.getWriter();
    out.println("<!DOCTYPE html>");
    out.println("<html>");
    out.println("<head>");
    out.println("<meta charset='UTF-8'>");
    out.printf("<meta http-equiv='refresh' content='1;url=/board/list'>\n");
    out.println("<title>NAVER CLOUD 학생 관리 시스템</title>");
    out.println("</head>");
    out.println("<body>");

    if (loginUser.getLevel() == 2) {

      Board board = new Board();
      board.setNo(Integer.parseInt(request.getParameter("no")));
      board.setTitle(request.getParameter("title"));
      board.setContent(request.getParameter("content"));
      board.setWriter(loginUser);

      out.println("<h1>게시글 변경</h1>");
      try {
        if (InitServlet.boardDao.update(board) == 0) {
          out.println("<p>게시글이 없거나 변경 권한이 없습니다.</p>");
        } else {
          out.println("<p>변경했습니다!</p>");
        }
        InitServlet.sqlSessionFactory.openSession(false).commit();

      } catch (Exception e) {
        InitServlet.sqlSessionFactory.openSession(false).rollback();
        out.println("<p>게시글 변경 실패입니다!</p>");
        e.printStackTrace();
      }

    } else {
      out.println("<p>해당 레벨의 회원은 게시글을 변경할 수 없습니다.</p>");
    }
    out.println("</body>");
    out.println("</html>");
  }
}
