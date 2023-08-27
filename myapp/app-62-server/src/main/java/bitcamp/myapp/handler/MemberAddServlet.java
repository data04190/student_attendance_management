package bitcamp.myapp.handler;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import bitcamp.myapp.vo.Member;

@WebServlet("/member/add")
@MultipartConfig(maxFileSize = 1024 * 1024 * 10)
public class MemberAddServlet extends HttpServlet {

  private static final long serialVersionUID = 1L;

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    Member m = new Member();
    m.setName(request.getParameter("name"));
    m.setEmail(request.getParameter("email"));
    m.setPassword(request.getParameter("password"));
    m.setLevel(Integer.parseInt(request.getParameter("level")));

    Part photoPart = request.getPart("photo");
    if (photoPart.getSize() > 0) {
      String uploadFileUrl = InitServlet.ncpObjectStorageService.uploadFile("bitcamp-nc7-bucket-26",
          "student_member/", photoPart);
      m.setPhoto(uploadFileUrl);
    }

//    response.setContentType("text/html;charset=UTF-8");
//    PrintWriter out = response.getWriter();
//    out.println("<!DOCTYPE html>");
//    out.println("<html>");
//    out.println("<head>");
//    out.println("<meta charset='UTF-8'>");
//    out.println("<meta http-equiv='refresh' content='1;url=/member/list'>");
//    out.println("<title>NAVER CLOUD 학생 관리 시스템</title>");
//    out.println("</head>");
//    out.println("<body>");
//    out.println("<h1>회원 등록</h1>");

    try {
      InitServlet.memberDao.insert(m);
      InitServlet.sqlSessionFactory.openSession(false).commit();
      response.sendRedirect("list");
//      out.println("<p>등록 성공입니다!</p>");

    } catch (Exception e) {
      InitServlet.sqlSessionFactory.openSession(false).rollback();

      request.setAttribute("error", e);
      request.setAttribute("message", "회원 등록 오류!");
      request.setAttribute("refresh", "2;url=list");

      request.getRequestDispatcher("/error").forward(request, response);
//      out.println("<p>등록 실패입니다!</p>");
//      e.printStackTrace();
    }

//    out.println("</body>");
//    out.println("</html>");
  }
}
