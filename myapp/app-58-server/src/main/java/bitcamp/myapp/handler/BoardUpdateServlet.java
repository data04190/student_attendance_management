package bitcamp.myapp.handler;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
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

    response.setContentType("text/html;charset=UTF-8");
    PrintWriter out = response.getWriter();
    out.println("<!DOCTYPE html>");
    out.println("<html>");
    out.println("<head>");
    out.println("<meta charset='UTF-8'>");
    // out.printf("<meta http-equiv='refresh' content='1;url=/board/list'>\n");
    out.println("<title>NAVER CLOUD 학생 관리 시스템</title>");
    out.println("</head>");
    out.println("<body>");
    out.println("<h1>게시글 변경</h1>");

    if (loginUser.getLevel() == 2) {

      try {

        Board board = new Board();
        board.setWriter(loginUser);
        board.setNo(Integer.parseInt(request.getParameter("no")));
        board.setTitle(request.getParameter("title"));
        board.setContent(request.getParameter("content"));

        String uploadDir = request.getServletContext().getRealPath("/upload/board/");
        ArrayList<AttachedFile> attachedFiles = new ArrayList<>();

        List<FileItem> parts =
            new ServletFileUpload(new DiskFileItemFactory()).parseRequest(request);

        for (Part part : request.getParts()) {
          if (part.getName().equals("files") && part.getSize() > 0) {
            String filename = UUID.randomUUID().toString();
            part.write(uploadDir + filename);
            AttachedFile attachedFile = new AttachedFile();
            attachedFile.setFilePath(filename);
            attachedFiles.add(attachedFile);

          }
        }
        board.setAttachedFiles(attachedFiles);

        if (InitServlet.boardDao.update(board) == 0) {
          out.println("<p>게시글이 없거나 변경 권한이 없습니다.</p>");
        } else {
          if (attachedFiles.size() > 0) {

            // 게시글 정상적으로 변경했으면, 게시글의 첨부파일 추가
            int count = InitServlet.boardDao.insertFiles(board);
            System.out.println(count);
          }

          out.println("<p>변경했습니다!</p>");
          response.setHeader("refresh", "1;url=/board/list");
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