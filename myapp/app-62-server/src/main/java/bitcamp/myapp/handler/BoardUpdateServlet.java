package bitcamp.myapp.handler;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import bitcamp.myapp.vo.AttachedFile;
import bitcamp.myapp.vo.Board;
import bitcamp.myapp.vo.Member;

@WebServlet("/board/update")
@MultipartConfig(maxFileSize = 1024 * 1024 * 10)

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

    try {
      Board board = new Board();
      board.setWriter(loginUser);
      board.setNo(Integer.parseInt(request.getParameter("no")));
      board.setTitle(request.getParameter("title"));
      board.setContent(request.getParameter("content"));

      ArrayList<AttachedFile> attachedFiles = new ArrayList<>();
      for (Part part : request.getParts()) {
        if (part.getName().equals("files") && part.getSize() > 0) {
          String uploadFileUrl = InitServlet.ncpObjectStorageService.uploadFile(
                  "bitcamp-nc7-bucket-26", "student_board/", part);
          AttachedFile attachedFile = new AttachedFile();
          attachedFile.setFilePath(uploadFileUrl);
          attachedFiles.add(attachedFile);
          }
        }
      board.setAttachedFiles(attachedFiles);

      if (InitServlet.boardDao.update(board) == 0) {
        throw new Exception("게시글이 없거나 변경 권한이 없습니다.");
      } else {
        if (attachedFiles.size() > 0) {
          int count = InitServlet.boardDao.insertFiles(board);
          System.out.println(count);
        }
        InitServlet.sqlSessionFactory.openSession(false).commit();
        response.sendRedirect("list");
      }
    } catch (Exception e) {
      InitServlet.sqlSessionFactory.openSession(false).rollback();

      request.setAttribute("error", e);
      request.setAttribute("message", e.getMessage());
      request.setAttribute("refresh", "15;url=list");

      request.getRequestDispatcher("/error").forward(request, response);
    }












//    response.setContentType("text/html;charset=UTF-8");
//    PrintWriter out = response.getWriter();
//    out.println("<!DOCTYPE html>");
//    out.println("<html>");
//    out.println("<head>");
//    out.println("<meta charset='UTF-8'>");
//    // out.printf("<meta http-equiv='refresh' content='1;url=/board/list'>\n");
//    out.println("<title>NAVER CLOUD 학생 관리 시스템</title>");
//    out.println("</head>");
//    out.println("<body>");
//    out.println("<h1>게시글 변경</h1>");
//
//    if (loginUser.getLevel() == 2) {
//
//      try {
//
//        Board board = new Board();
//        board.setWriter(loginUser);
//        board.setNo(Integer.parseInt(request.getParameter("no")));
//        board.setTitle(request.getParameter("title"));
//        board.setContent(request.getParameter("content"));
//
//        ArrayList<AttachedFile> attachedFiles = new ArrayList<>();
//
//
//        for (Part part : request.getParts()) {
//          if (part.getName().equals("files") && part.getSize() > 0) {
//            String uploadDir = InitServlet.ncpObjectStorageService
//                .uploadFile("bitcamp-nc7-bucket-26", "student_board/", part);
//            AttachedFile attachedFile = new AttachedFile();
//            attachedFile.setFilePath(uploadDir);
//            attachedFiles.add(attachedFile);
//
//          }
//        }
//        board.setAttachedFiles(attachedFiles);
//
//        if (InitServlet.boardDao.update(board) == 0) {
//          out.println("<p>게시글이 없거나 변경 권한이 없습니다.</p>");
//        } else {
//          if (attachedFiles.size() > 0) {
//
//            // 게시글 정상적으로 변경했으면, 게시글의 첨부파일 추가
//            int count = InitServlet.boardDao.insertFiles(board);
//            System.out.println(count);
//          }
//
//          out.println("<p>변경했습니다!</p>");
//          response.setHeader("refresh", "1;url=/board/list");
//        }
//        InitServlet.sqlSessionFactory.openSession(false).commit();
//
//      } catch (Exception e) {
//        InitServlet.sqlSessionFactory.openSession(false).rollback();
//        out.println("<p>게시글 변경 실패입니다!</p>");
//        e.printStackTrace();
//      }
//
//    } else {
//      out.println("<p>해당 레벨의 회원은 게시글을 변경할 수 없습니다.</p>");
//    }
//    out.println("</body>");
//    out.println("</html>");
  }
}
