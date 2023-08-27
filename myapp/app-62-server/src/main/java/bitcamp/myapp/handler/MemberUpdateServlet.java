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

@WebServlet("/member/update")
@MultipartConfig(maxFileSize = 1024 * 1024 * 10)
public class MemberUpdateServlet extends HttpServlet {

  private static final long serialVersionUID = 1L;

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    Member member = new Member();
    member.setNo(Integer.parseInt(request.getParameter("no")));
    member.setName(request.getParameter("name"));
    member.setEmail(request.getParameter("email"));
    member.setPassword(request.getParameter("password"));
    member.setLevel(Integer.parseInt(request.getParameter("level")));

    Part photoPart = request.getPart("photo");
    if (photoPart.getSize() > 0) {
      String uploadFileUrl = InitServlet.ncpObjectStorageService.uploadFile("bitcamp-nc7-bucket-26",
          "student_member/", photoPart);
      member.setPhoto(uploadFileUrl);
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
//    out.println("<h1>회원 변경</h1>");

    try {
      if (InitServlet.memberDao.update(member) == 0) {
        throw new Exception("회원이 없습니다.");
      } else {
        InitServlet.sqlSessionFactory.openSession(false).commit();
        response.sendRedirect("list");
      }
    } catch (Exception e) {
      InitServlet.sqlSessionFactory.openSession(false).rollback();

      request.setAttribute("error", e);
      request.setAttribute("message", e.getMessage());
      request.setAttribute("refresh", "2;url=list");

      request.getRequestDispatcher("/error").forward(request, response);
    }

  }

}
