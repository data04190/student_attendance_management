package bitcamp.myapp.handler;

import java.io.IOException;
import bitcamp.myapp.dao.BoardDao;
import bitcamp.myapp.vo.Board;
import bitcamp.myapp.vo.Member;
import bitcamp.util.ActionListener;
import bitcamp.util.BreadcrumbPrompt;


public class BoardDeleteListener implements ActionListener {

  BoardDao boardDao;

  public BoardDeleteListener(BoardDao boardDao) {
    this.boardDao = boardDao;
  }

  @Override
  public void service(BreadcrumbPrompt prompt) throws IOException {

    if (((Member) prompt.getAttribute("loginUser")).getLevel() == 2) {

      Board b = new Board();
      b.setNo(prompt.inputInt("번호? "));
      b.setWriter((Member) prompt.getAttribute("loginUser"));

      if (boardDao.delete(b) == 0) {
        prompt.println("해당 번호의 게시글이 없거나 삭제 권한이 없습니다.");
      } else {
        prompt.println("삭제했습니다.");
      }
    } else {
      prompt.println("해당 레벨의 회원은 게시글을 삭제할 수 없습니다.");
    }

  }
}

