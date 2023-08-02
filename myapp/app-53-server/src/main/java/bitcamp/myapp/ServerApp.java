package bitcamp.myapp;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import bitcamp.net.NetProtocol;
import bitcamp.util.BreadcrumbPrompt;
import bitcamp.util.DispatcherListener;
import bitcamp.util.MenuGroup;
import bitcamp.util.SqlSessionFactoryProxy;

public class ServerApp {

  // 자바 스레드풀 준비
  ExecutorService threadPool = Executors.newFixedThreadPool(2);

  MenuGroup mainMenu = new MenuGroup("/", "메인");
  DispatcherListener facadeListener = new DispatcherListener();

  int port;

  public ServerApp(int port) throws Exception {
    this.port = port;
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

      prompt.setAttribute("menuPath", "login");
      facadeListener.service(prompt);

      mainMenu.execute(prompt);
      out.writeUTF(NetProtocol.NET_END);

    } catch (Exception e) {
      System.out.println("클라이언트 통신 오류!");
      e.printStackTrace();


    } finally {
      SqlSessionFactoryProxy sqlSessionFactoryProxy =
          (SqlSessionFactoryProxy) facadeListener.getBean("sqlSessionFactory");
      sqlSessionFactoryProxy.clean();
    }
  }

  private void prepareMenu() {

    MenuGroup memberMenu = new MenuGroup("member", "학생 관리");
    memberMenu.add("member/add", "등록", facadeListener);
    memberMenu.add("member/list", "목록", facadeListener);
    memberMenu.add("member/detail", "조회", facadeListener);
    memberMenu.add("member/update", "변경", facadeListener);
    memberMenu.add("member/delete", "삭제", facadeListener);
    mainMenu.add(memberMenu);

    MenuGroup attendanceMenu = new MenuGroup("attendance", "출결 체크");
    attendanceMenu.add("attendance/add", "등록", facadeListener);
    attendanceMenu.add("attendance/list", "목록", facadeListener);
    attendanceMenu.add("attendance/detail", "조회", facadeListener);
    attendanceMenu.add("attendance/update", "변경", facadeListener);
    attendanceMenu.add("attendance/delete", "삭제", facadeListener);
    mainMenu.add(attendanceMenu);

    MenuGroup boardMenu = new MenuGroup("board", "공지사항");
    boardMenu.add("board/add", "등록", facadeListener);
    boardMenu.add("board/list", "목록", facadeListener);
    boardMenu.add("board/detail", "조회", facadeListener);
    boardMenu.add("board/update", "변경", facadeListener);
    boardMenu.add("board/delete", "삭제", facadeListener);
    mainMenu.add(boardMenu);

    // Menu helloMenu = new Menu("마이페이지");
    // helloMenu.addActionListener(new HeaderListener());
    // helloMenu.addActionListener(new HelloListener());
    // helloMenu.addActionListener(new FooterListener());
    // mainMenu.add(helloMenu);

  }

}
