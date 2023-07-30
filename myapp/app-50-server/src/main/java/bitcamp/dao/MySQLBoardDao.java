package bitcamp.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import bitcamp.myapp.dao.BoardDao;
import bitcamp.myapp.vo.Board;
import bitcamp.myapp.vo.Member;

public class MySQLBoardDao implements BoardDao {

  Connection con;

  public MySQLBoardDao(Connection con) {
    this.con = con;
  }

  // @Override
  // public int getMemberLevel() {
  // try (PreparedStatement stmt =
  // con.prepareStatement("select level from myapp_member where member_no =?")) {
  // stmt.setInt(1, ServerApp.loginUser.getNo());
  // try (ResultSet rs = stmt.executeQuery()) {
  // if (rs.next()) {
  // return rs.getInt("level");
  // } else {
  // return -1; // 해당 이름의 회원이 없을 경우
  // }
  // }
  // } catch (Exception e) {
  // throw new RuntimeException(e);
  // }
  // }

  @Override
  public void insert(Board board) {
    try (PreparedStatement stmt = con.prepareStatement(
        "insert into myapp_board(title, content, writer, password) values(?,?,?,sha1(?))")) {

      stmt.setString(1, board.getTitle());
      stmt.setString(2, board.getContent());
      stmt.setInt(3, board.getWriter().getNo());
      stmt.setString(4, board.getPassword());

      stmt.executeUpdate();

    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public List<Board> list() {
    try (PreparedStatement stmt = con.prepareStatement(
        "select b.board_no, b.title, m.member_no, m.name, b.writer, b.view_count, b.created_date from myapp_board b inner join myapp_member m"
            + " on b.writer = m.member_no" + " where m.level = 2" + " order by board_no desc;")) {

      try (ResultSet rs = stmt.executeQuery()) {

        List<Board> list = new ArrayList<>();

        while (rs.next()) {
          Board board = new Board();
          board.setNo(rs.getInt("board_no"));
          board.setTitle(rs.getString("title"));
          board.setViewCount(rs.getInt("view_count"));
          board.setCreatedDate(rs.getTimestamp("created_date"));

          Member writer = new Member();
          writer.setNo(rs.getInt("member_no"));
          writer.setName(rs.getString("name"));
          board.setWriter(writer);
          // board.setWriter(rs.getString("writer"));
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
        "select b.board_no, b.title, b.content, m.member_no, m.name, b.view_count, b.created_date"
            + " from myapp_board b inner join myapp_member m" + " on b.writer = m.member_no"
            + " where board_no= ? order by board_no desc;")) {

      stmt.setInt(1, no);

      try (ResultSet rs = stmt.executeQuery()) {

        if (rs.next()) {
          Board board = new Board();
          board.setNo(rs.getInt("board_no"));
          board.setTitle(rs.getString("title"));
          board.setContent(rs.getString("content"));
          board.setViewCount(rs.getInt("view_count"));
          board.setCreatedDate(rs.getTimestamp("created_date"));

          Member writer = new Member();
          writer.setNo(rs.getInt("member_no"));
          writer.setName(rs.getString("name"));
          board.setWriter(writer);

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
        + " content = ? " + " where board_no = ? and writer=?")) {

      stmt.setString(1, board.getTitle());
      stmt.setString(2, board.getContent());
      stmt.setInt(3, board.getNo());
      stmt.setInt(4, board.getWriter().getNo());

      return stmt.executeUpdate();

    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public int delete(Board board) {
    try (PreparedStatement stmt =
        con.prepareStatement("delete from myapp_board where board_no=? and writer=?")) {

      stmt.setInt(1, board.getNo());
      stmt.setInt(2, board.getWriter().getNo());

      return stmt.executeUpdate();

    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

}
