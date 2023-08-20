package bitcamp.myapp.handler;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import bitcamp.myapp.vo.AttachedFile;
import bitcamp.myapp.vo.Board;
import bitcamp.myapp.vo.Member;

@WebServlet("/board/add")
public class BoardAddServlet extends HttpServlet {

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
      // 멀티파트의 각 파트 데이터를 저장할 객체를 만드는 공장
      DiskFileItemFactory factory = new DiskFileItemFactory();

      // 멀티파트 형식으로 넘어 온 요청 파라미터를 분석하여 처리하는 객체
      ServletFileUpload upload = new ServletFileUpload(factory);

      // 멀티파트 요청 파라미터 분석
      List<FileItem> parts = upload.parseRequest(request);

      // 각각의 파트에서 값 꺼내기
      Board board = new Board();
      board.setWriter(loginUser);

      // 웹 어플리케이션 환경 정보를 알고 있는 객체 꺼내기
      ServletContext 웹애플리케이션환경정보 = request.getServletContext();

      // 웹 애플리케이션 환경정보에서 /upload/board 디렉토리의 실제 경로를 추출
      String uploadDir = 웹애플리케이션환경정보.getRealPath("/upload/board");
      // System.out.println(uploadDir);

      ArrayList<AttachedFile> attachedFiles = new ArrayList<>();

      for (FileItem part : parts) {
        if (part.isFormField()) { // 일반 데이터
          if (part.getFieldName().equals("title")) {
            board.setTitle(part.getString("UTF-8"));
          } else if (part.getFieldName().equals("content")) {
            board.setContent(part.getString("UTF-8"));
          }
        } else { // 파일 데이터
          String filename = UUID.randomUUID().toString();
          part.write(new File(uploadDir, filename));
          AttachedFile attachedFile = new AttachedFile();
          attachedFile.setFilePath(filename);
          attachedFiles.add(attachedFile);

        }
      }
      board.setAttachedFiles(attachedFiles);

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
      out.println("<h1>게시글 등록</h1>");


      if (loginUser.getLevel() == 2) {

        try {
          InitServlet.boardDao.insert(board);

          int count = InitServlet.boardDao.insertFiles(board);
          System.out.println(count);

          InitServlet.sqlSessionFactory.openSession(false).commit();
          out.println("<p>등록 성공입니다!</p>");

        } catch (Exception e) {
          InitServlet.sqlSessionFactory.openSession(false).rollback();
          out.println("<p>등록 실패입니다!</p>");
          e.printStackTrace();
        }

      } else {
        out.println("<p>해당 레벨의 회원은 게시글을 작성할 수 없습니다.</p>");
      }
      out.println("</body>");
      out.println("</html>");

    } catch (Exception e) {
      throw new ServletException(e);
    }
  }
}
