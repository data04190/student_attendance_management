package bitcamp.myapp.handler;

import bitcamp.myapp.ClientApp;
import bitcamp.myapp.dao.BoardDao;
import bitcamp.myapp.vo.Board;
import bitcamp.util.ActionListener;
import bitcamp.util.BreadcrumbPrompt;


public class BoardUpdateListener implements ActionListener {

  BoardDao boardDao;

  public BoardUpdateListener(BoardDao boardDao) {
    this.boardDao = boardDao;
  }

  @Override
  public void service(BreadcrumbPrompt prompt) {

    if (ClientApp.loginUser.getLevel() == 2) {

      int boardNo = prompt.inputInt("번호? ");

      Board board = boardDao.findBy(boardNo);
      if (board == null) {
        System.out.println("해당 번호의 게시글이 없습니다!");
        return;
      }

      board.setTitle(prompt.inputString("제목(%s)? ", board.getTitle()));
      board.setContent(prompt.inputString("내용(%s)? ", board.getContent()));
      board.setWriter(ClientApp.loginUser);

      if (boardDao.update(board) == 0) {
        System.out.println("게시글 변경 권한이 없습니다.");
      } else {
        System.out.println("변경했습니다!");
      }
    } else {
      System.out.println("해당 레벨의 회원은 게시글을 변경할 수 없습니다.");
    }
  }
}
