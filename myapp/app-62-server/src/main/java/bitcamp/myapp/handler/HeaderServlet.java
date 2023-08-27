package bitcamp.myapp.handler;

import bitcamp.myapp.vo.Member;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/header")
public class HeaderServlet extends HttpServlet {

  private static final long serialVersionUID = 1L;

  @Override
  protected void service(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException {

    response.setContentType("text/html;charset=UTF-8");
    PrintWriter out = response.getWriter();
    out.println("<div style='height:50px;background-color:orange;'>");
    out.println("<img src='https://www.ncloud.com/public/img/logo-m.png' style='height:40px'>");
    out.println("<a href='/member/list'>학생 관리</a>");
    out.println("<a href='/attendance/list'>출결 체크</a>");
    out.println("<a href='/board/list'>공지 사항</a>");

    Member loginUser = (Member) request.getSession().getAttribute("loginUser");
    if (loginUser == null) {
      out.println("<a href='/auth/form.html'>로그인</a>");
    } else {
      out.printf("%s %s <a href='/auth/logout'>로그아웃</a>\n",
              (loginUser.getPhoto() == null ?
                      "<img style='height:40px' src='/images/avatar.png'>" :
                      String.format("<img src='http://kgddbipzoniy19010732.cdn.ntruss.com/student_member/%s?type=f&w=30&h=40&faceopt=true&ttype=jpg'>",
                              loginUser.getPhoto())),
              loginUser.getName());
    }
      out.println("</div>");
  }
}