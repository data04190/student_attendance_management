package bitcamp.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import bitcamp.myapp.dao.BoardDao;
import bitcamp.myapp.vo.Board;

public class MySQLBoardDao implements BoardDao {

  Connection con;

  public MySQLBoardDao(Connection con) {
    this.con = con;
  }

  @Override
  public void insert(Board board) {
    try (PreparedStatement stmt = con.prepareStatement(
        "insert into myapp_board(title, content, writer, password) values(?,?,?,sha1(?))")) {

      stmt.setString(1, board.getTitle());
      stmt.setString(2, board.getContent());
      stmt.setString(3, board.getWriter());
      stmt.setString(4, board.getPassword());

      stmt.executeUpdate();

    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public List<Board> list() {
    try (PreparedStatement stmt = con.prepareStatement(
        "select board_no, title, writer, view_count, created_date from myapp_board order by board_no desc")) {

      try (ResultSet rs = stmt.executeQuery()) {

        List<Board> list = new ArrayList<>();

        while (rs.next()) {
          Board board = new Board();
          board.setNo(rs.getInt("board_no"));
          board.setTitle(rs.getString("title"));
          board.setWriter(rs.getString("writer"));
          board.setViewCount(rs.getInt("view_count"));
          board.setCreatedDate(rs.getTimestamp("created_date"));

          list.add(board);
        }
        return list;
      }

    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public Board findBy(int no) {
    try (PreparedStatement stmt = con.prepareStatement(
        "select board_no, title, content, writer, view_count, created_date from myapp_board where board_no=?  order by board_no desc")) {

      stmt.setInt(1, no);

      try (ResultSet rs = stmt.executeQuery()) {

        if (rs.next()) {
          Board board = new Board();
          board.setNo(rs.getInt("board_no"));
          board.setTitle(rs.getString("title"));
          board.setContent(rs.getString("content"));
          board.setWriter(rs.getString("writer"));
          board.setViewCount(rs.getInt("view_count"));
          board.setCreatedDate(rs.getTimestamp("created_date"));

          stmt.executeUpdate(
              "update myapp_board set" + " view_count = view_count + 1" + " where board_no=" + no);

          return board;
        }

        return null;
      }
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public int update(Board board) {
    try (PreparedStatement stmt = con.prepareStatement("update myapp_board set " + " title = ?,"
        + " content = ? " + " where board_no = ? and password = sha1(?)")) {

      stmt.setString(1, board.getTitle());
      stmt.setString(2, board.getContent());
      stmt.setInt(3, board.getNo());
      stmt.setString(4, board.getPassword());

      return stmt.executeUpdate();

    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public int delete(Board board) {
    try (PreparedStatement stmt =
        con.prepareStatement("delete from myapp_board where board_no=? and password=sha1(?)")) {

      stmt.setInt(1, board.getNo());
      stmt.setString(2, board.getPassword());

      return stmt.executeUpdate();

    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

}
