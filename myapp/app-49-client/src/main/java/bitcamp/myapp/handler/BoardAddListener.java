package bitcamp.myapp.handler;

import bitcamp.myapp.ClientApp;
import bitcamp.myapp.dao.BoardDao;
import bitcamp.myapp.vo.Board;
import bitcamp.util.ActionListener;
import bitcamp.util.BreadcrumbPrompt;


public class BoardAddListener implements ActionListener {

  BoardDao boardDao;

  public BoardAddListener(BoardDao boardDao) {
    this.boardDao = boardDao;
  }

  @Override
  public void service(BreadcrumbPrompt prompt) {

    if (ClientApp.loginUser.getLevel() == 2) { // 작성자의 레벨이 2인 경우만 게시글 작성 가능

      Board board = new Board();
      board.setTitle(prompt.inputString("제목? "));
      board.setContent(prompt.inputString("내용? "));
      board.setWriter(ClientApp.loginUser);

      boardDao.insert(board);
    } else {
      System.out.println("해당 레벨의 회원은 게시글을 작성할 수 없습니다.");
    }
  }


}
