package bitcamp.myapp;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
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
import bitcamp.myapp.handler.LoginListener;
import bitcamp.myapp.handler.MemberAddListener;
import bitcamp.myapp.handler.MemberDeleteListener;
import bitcamp.myapp.handler.MemberDetailListener;
import bitcamp.myapp.handler.MemberListListener;
import bitcamp.myapp.handler.MemberUpdateListener;
import bitcamp.net.NetProtocol;
import bitcamp.util.BreadcrumbPrompt;
import bitcamp.util.DataSource;
import bitcamp.util.Menu;
import bitcamp.util.MenuGroup;

public class ServerApp {

  // 자바 스레드풀 준비
  ExecutorService threadPool = Executors.newFixedThreadPool(2);

  DataSource ds = new DataSource("jdbc:mysql://localhost:3306/studentDB", "study", "1111");
  MemberDao memberDao;
  AttendanceDao attendanceDao;
  BoardDao boardDao;

  MenuGroup mainMenu = new MenuGroup("메인");

  int port;

  public ServerApp(int port) throws Exception {

    this.port = port;


    this.memberDao = new MySQLMemberDao(ds);
    this.attendanceDao = new MySQLAttendanceDao(ds);
    this.boardDao = new MySQLBoardDao(ds);

    prepareMenu();
  }

  public void close() throws Exception {

  }


  public static void main(String[] args) throws Exception {
    ServerApp app = new ServerApp(8888);
    app.execute();
    app.close();
  }

  public void execute() {
    try (ServerSocket serverSocket = new ServerSocket(this.port)) {
      System.out.println("서버 실행 중...");

      while (true) {
        Socket socket = serverSocket.accept();
        threadPool.execute(() -> processRequest(socket));
      }
    } catch (Exception e) {
      System.out.println("서버 실행 오류!");
      e.printStackTrace();
    }
  }

  private void processRequest(Socket socket) {
    try (Socket s = socket;
        DataInputStream in = new DataInputStream(socket.getInputStream());
        DataOutputStream out = new DataOutputStream(socket.getOutputStream())) {

      BreadcrumbPrompt prompt = new BreadcrumbPrompt(in, out);

      InetSocketAddress clientAddress = (InetSocketAddress) socket.getRemoteSocketAddress();
      System.out.printf("%s 클라이언트 접속함!\n", clientAddress.getHostString());

      out.writeUTF("[학생 출결 관리 시스템]\n" + "-----------------------------------------");

      new LoginListener(memberDao).service(prompt);

      mainMenu.execute(prompt);
      out.writeUTF(NetProtocol.NET_END);

    } catch (Exception e) {
      System.out.println("클라이언트 통신 오류!");
      e.printStackTrace();
    } finally {
      ds.clean();
    }
  }

  private void prepareMenu() {

    MenuGroup memberMenu = new MenuGroup("학생 관리");
    memberMenu.add(new Menu("등록", new MemberAddListener(memberDao, ds)));
    memberMenu.add(new Menu("목록", new MemberListListener(memberDao)));
    memberMenu.add(new Menu("조회", new MemberDetailListener(memberDao)));
    memberMenu.add(new Menu("변경", new MemberUpdateListener(memberDao, ds)));
    memberMenu.add(new Menu("삭제", new MemberDeleteListener(memberDao, ds)));
    mainMenu.add(memberMenu);

    MenuGroup attendanceMenu = new MenuGroup("출결 체크");
    attendanceMenu.add(new Menu("등록", new AttendanceAddListener(attendanceDao, ds)));
    attendanceMenu.add(new Menu("목록", new AttendanceListListener(attendanceDao)));
    attendanceMenu.add(new Menu("조회", new AttendanceDetailListener(attendanceDao)));
    attendanceMenu.add(new Menu("변경", new AttendanceUpdateListener(attendanceDao, ds)));
    attendanceMenu.add(new Menu("삭제", new AttendanceDeleteListener(attendanceDao, ds)));
    mainMenu.add(attendanceMenu);

    MenuGroup boardMenu = new MenuGroup("공지사항");
    boardMenu.add(new Menu("등록", new BoardAddListener(boardDao, ds)));
    boardMenu.add(new Menu("목록", new BoardListListener(boardDao)));
    boardMenu.add(new Menu("조회", new BoardDetailListener(boardDao, ds)));
    boardMenu.add(new Menu("변경", new BoardUpdateListener(boardDao, ds)));
    boardMenu.add(new Menu("삭제", new BoardDeleteListener(boardDao, ds)));
    mainMenu.add(boardMenu);

    Menu helloMenu = new Menu("마이페이지");
    helloMenu.addActionListener(new HeaderListener());
    helloMenu.addActionListener(new HelloListener());
    helloMenu.addActionListener(new FooterListener());
    mainMenu.add(helloMenu);

  }

}
