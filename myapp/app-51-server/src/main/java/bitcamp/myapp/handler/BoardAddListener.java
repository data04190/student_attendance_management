package bitcamp.myapp.handler;

import java.io.IOException;
import bitcamp.myapp.dao.BoardDao;
import bitcamp.myapp.vo.Board;
import bitcamp.myapp.vo.Member;
import bitcamp.util.ActionListener;
import bitcamp.util.BreadcrumbPrompt;
import bitcamp.util.DataSource;


public class BoardAddListener implements ActionListener {

  BoardDao boardDao;
  DataSource ds;

  public BoardAddListener(BoardDao boardDao, DataSource ds) {
    this.boardDao = boardDao;
    this.ds = ds;
  }

  @Override
  public void service(BreadcrumbPrompt prompt) throws IOException {

    if (((Member) prompt.getAttribute("loginUser")).getLevel() == 2) { // 작성자의 레벨이 2인 경우만 게시글 작성 가능

      Board board = new Board();
      board.setTitle(prompt.inputString("제목? "));
      board.setContent(prompt.inputString("내용? "));
      board.setWriter((Member) prompt.getAttribute("loginUser"));

      try {
        boardDao.insert(board);
        ds.getConnection().commit();

      } catch (Exception e) {
        try {
          ds.getConnection().rollback();
        } catch (Exception e2) {
        }
        throw new RuntimeException(e);
      }

    } else {
      prompt.println("해당 레벨의 회원은 게시글을 작성할 수 없습니다.");
    }
  }


}
