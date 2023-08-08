package bitcamp.myapp.handler;

import java.io.PrintWriter;
import org.apache.ibatis.session.SqlSessionFactory;
import bitcamp.myapp.dao.BoardDao;
import bitcamp.myapp.vo.Board;
import bitcamp.myapp.vo.Member;
import bitcamp.util.Component;
import bitcamp.util.HttpServletRequest;
import bitcamp.util.HttpServletResponse;
import bitcamp.util.Servlet;

@Component("/board/add")
public class BoardAddServlet implements Servlet {

  BoardDao boardDao;
  SqlSessionFactory sqlSessionFactory;

  public BoardAddServlet(BoardDao boardDao, SqlSessionFactory sqlSessionFactory) {
    this.boardDao = boardDao;
    this.sqlSessionFactory = sqlSessionFactory;
  }

  @Override
  public void service(HttpServletRequest request, HttpServletResponse response) throws Exception {

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
    out.println("<title>게시글</title>");
    out.println("</head>");
    out.println("<body>");

    if (loginUser.getLevel() == 2) {

      Board board = new Board();
      board.setTitle(request.getParameter("title"));
      board.setContent(request.getParameter("content"));
      board.setWriter(loginUser);

      out.println("<h1>게시글 등록</h1>");
      try {
        boardDao.insert(board);
        sqlSessionFactory.openSession(false).commit();
        out.println("<p>등록 성공입니다!</p>");

      } catch (Exception e) {
        sqlSessionFactory.openSession(false).rollback();
        out.println("<p>등록 실패입니다!</p>");
        e.printStackTrace();
      }


    } else {
      out.println("<p>해당 레벨의 회원은 게시글을 작성할 수 없습니다.</p>");
    }
    out.println("</body>");
    out.println("</html>");
  }

}
