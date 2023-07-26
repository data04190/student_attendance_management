package bitcamp.myapp;

import java.sql.Connection;
import java.sql.DriverManager;
import bitcamp.dao.MySQLAttendanceDao;
import bitcamp.dao.MySQLBoardDao;
import bitcamp.dao.MySQLMemberDao;
import bitcamp.myapp.dao.AttendanceDao;
import bitcamp.myapp.dao.BoardDao;
import bitcamp.myapp.dao.MemberDao;
import bitcamp.myapp.handler.AttendanceAddListener;
import bitcamp.myapp.handler.AttendanceDeleteListener;
import bitcamp.myapp.handler.AttendanceDetailListener;
import bitcamp.myapp.handler.AttendanceListListener;
import bitcamp.myapp.handler.AttendanceUpdateListener;
import bitcamp.myapp.handler.BoardAddListener;
import bitcamp.myapp.handler.BoardDeleteListener;
import bitcamp.myapp.handler.BoardDetailListener;
import bitcamp.myapp.handler.BoardListListener;
import bitcamp.myapp.handler.BoardUpdateListener;
import bitcamp.myapp.handler.FooterListener;
import bitcamp.myapp.handler.HeaderListener;
import bitcamp.myapp.handler.HelloListener;
import bitcamp.myapp.handler.MemberAddListener;
import bitcamp.myapp.handler.MemberDeleteListener;
import bitcamp.myapp.handler.MemberDetailListener;
import bitcamp.myapp.handler.MemberListListener;
import bitcamp.myapp.handler.MemberUpdateListener;
import bitcamp.util.BreadcrumbPrompt;
import bitcamp.util.Menu;
import bitcamp.util.MenuGroup;


public class ClientApp {

  MemberDao memberDao;
  AttendanceDao attendanceDao;
  BoardDao boardDao;

  BreadcrumbPrompt prompt = new BreadcrumbPrompt();

  MenuGroup mainMenu = new MenuGroup("메인");

  public ClientApp(String ip, int port) throws Exception {

    Connection con = DriverManager.getConnection("jdbc:mysql://study:1111@localhost:3306/studentDB" // JDBC
    // URL
    );
    this.memberDao = new MySQLMemberDao(con);
    this.attendanceDao = new MySQLAttendanceDao(con);
    this.boardDao = new MySQLBoardDao(con);

    prepareMenu();
  }

  public void close() throws Exception {
    prompt.close();
  }


  public static void main(String[] args) throws Exception {
    if (args.length < 2) {
      System.out.println("실행 예) java ... bitcamp.myapp.ClientApp 서버주소 포트번호");
      return;
    }

    ClientApp app = new ClientApp(args[0], Integer.parseInt(args[1]));
    app.execute();
    app.close();
  }

  static void printTitle() {
    System.out.println("학생 출결 관리 시스템");
    System.out.println("=================================");
  }

  public void execute() {
    printTitle();
    mainMenu.execute(prompt);
  }


  private void prepareMenu() {

    MenuGroup memberMenu = new MenuGroup("학생 관리");
    memberMenu.add(new Menu("등록", new MemberAddListener(memberDao)));
    memberMenu.add(new Menu("목록", new MemberListListener(memberDao)));
    memberMenu.add(new Menu("조회", new MemberDetailListener(memberDao)));
    memberMenu.add(new Menu("변경", new MemberUpdateListener(memberDao)));
    memberMenu.add(new Menu("삭제", new MemberDeleteListener(memberDao)));
    mainMenu.add(memberMenu);

    MenuGroup attendanceMenu = new MenuGroup("출결 체크");
    attendanceMenu.add(new Menu("등록", new AttendanceAddListener(attendanceDao)));
    attendanceMenu.add(new Menu("목록", new AttendanceListListener(attendanceDao)));
    attendanceMenu.add(new Menu("조회", new AttendanceDetailListener(attendanceDao)));
    attendanceMenu.add(new Menu("변경", new AttendanceUpdateListener(attendanceDao)));
    attendanceMenu.add(new Menu("삭제", new AttendanceDeleteListener(attendanceDao)));
    mainMenu.add(attendanceMenu);

    MenuGroup boardMenu = new MenuGroup("공지사항");
    boardMenu.add(new Menu("등록", new BoardAddListener(boardDao)));
    boardMenu.add(new Menu("목록", new BoardListListener(boardDao)));
    boardMenu.add(new Menu("조회", new BoardDetailListener(boardDao)));
    boardMenu.add(new Menu("변경", new BoardUpdateListener(boardDao)));
    boardMenu.add(new Menu("삭제", new BoardDeleteListener(boardDao)));
    mainMenu.add(boardMenu);

    Menu helloMenu = new Menu("마이페이지");
    helloMenu.addActionListener(new HeaderListener());
    helloMenu.addActionListener(new HelloListener());
    helloMenu.addActionListener(new FooterListener());
    mainMenu.add(helloMenu);

  }

}
