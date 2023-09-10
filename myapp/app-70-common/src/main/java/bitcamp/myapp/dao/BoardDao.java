package bitcamp.myapp.dao;

import java.util.List;
import bitcamp.myapp.vo.AttachedFile;
import bitcamp.myapp.vo.Board;

public interface BoardDao {
  int insert(Board board);
  List<Board> findAll();
  Board findBy(int no);
  int update(Board board);
  int updateCount(int no);
  int delete(int no);

  int insertFiles(Board board);
  AttachedFile findFileBy(int no);
  int deleteFile(int fileNo);
  int deleteFiles(int boardNo);


}
