package bitcamp.myapp;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
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
import bitcamp.myapp.vo.Board;
import bitcamp.myapp.vo.Member;
import bitcamp.util.BreadcrumbPrompt;
import bitcamp.util.Menu;
import bitcamp.util.MenuGroup;


public class App {

  ArrayList<Member> memberList = new ArrayList<>();
  LinkedList<Board> boardList = new LinkedList<>();

  BreadcrumbPrompt prompt = new BreadcrumbPrompt();

  MenuGroup mainMenu = new MenuGroup("메인");


  public App() {
    prepareMenu();
  }

  public static void main(String[] args) {
    new App().execute();
  }

  static void printTitle() {
    System.out.println("학생 출결 관리 시스템");
    System.out.println("=================================");
  }

  public void execute() {
    printTitle();

    loadData();
    mainMenu.execute(prompt);
    saveData();

    prompt.close();

  }

  private void loadData() {
    loadMember("member.data2", memberList);
    loadBoard("board.data2", boardList);
  }

  private void saveData() {
    saveMember("member.data2", memberList);
    saveBoard("board.data2", boardList);
  }

  private void prepareMenu() {

    MenuGroup memberMenu = new MenuGroup("회원");
    memberMenu.add(new Menu("등록", new MemberAddListener(memberList)));
    memberMenu.add(new Menu("목록", new MemberListListener(memberList)));
    memberMenu.add(new Menu("조회", new MemberDetailListener(memberList)));
    memberMenu.add(new Menu("변경", new MemberUpdateListener(memberList)));
    memberMenu.add(new Menu("삭제", new MemberDeleteListener(memberList)));
    mainMenu.add(memberMenu);

    MenuGroup boardMenu = new MenuGroup("게시글");
    boardMenu.add(new Menu("등록", new BoardAddListener(boardList)));
    boardMenu.add(new Menu("목록", new BoardListListener(boardList)));
    boardMenu.add(new Menu("조회", new BoardDetailListener(boardList)));
    boardMenu.add(new Menu("변경", new BoardUpdateListener(boardList)));
    boardMenu.add(new Menu("삭제", new BoardDeleteListener(boardList)));
    mainMenu.add(boardMenu);

    Menu helloMenu = new Menu("안녕하세요");
    helloMenu.addActionListener(new HeaderListener());
    helloMenu.addActionListener(new HelloListener());
    helloMenu.addActionListener(new FooterListener());
    mainMenu.add(helloMenu);

  }

  private void loadMember(String filename, List<Member> list) {
    try {
      FileInputStream in0 = new FileInputStream(filename);
      BufferedInputStream in1 = new BufferedInputStream(in0); // <== Decorator 역할을 수행!
      ObjectInputStream in = new ObjectInputStream(in1); // <== Decorator 역할을 수행!

      int size = in.readShort();

      for (int i = 0; i < size; i++) {
        list.add((Member) in.readObject());
      }

      if (list.size() > 0) {
        Member.userId = memberList.get(memberList.size() - 1).getNo() + 1;
      }

      in.close();

    } catch (Exception e) {
      System.out.println("회원 정보를 읽는 중 오류 발생!");
    }
  }

  private void loadBoard(String filename, List<Board> list) {

    try {
      FileInputStream in0 = new FileInputStream(filename);
      BufferedInputStream in1 = new BufferedInputStream(in0); // <== Decorator 역할을 수행!
      ObjectInputStream in = new ObjectInputStream(in1); // <== Decorator 역할을 수행!


      int size = in.readShort();

      for (int i = 0; i < size; i++) {
        list.add((Board) in.readObject());
      }

      if (list.size() > 0) {
        Board.boardNo = Math.max(Board.boardNo, list.get(list.size() - 1).getNo() + 1);
      }

      in.close();

    } catch (Exception e) {
      System.out.println(filename + " 파일을 읽는 중 오류 발생!");
    }
  }

  private void saveMember(String filename, List<Member> list) {
    try {
      FileOutputStream out0 = new FileOutputStream(filename);
      BufferedOutputStream out1 = new BufferedOutputStream(out0); // <== Decorator(장식품) 역할 수행!
      ObjectOutputStream out = new ObjectOutputStream(out1); // <== Decorator(장식품) 역할 수행!

      out.writeShort(list.size());

      for (Member member : list) {

        out.writeObject(member);
      }
      out.close();

    } catch (Exception e) {
      System.out.println("회원 정보를 저장하는 중 오류 발생!");
    }
  }

  private void saveBoard(String filename, List<Board> list) {
    try {
      FileOutputStream out0 = new FileOutputStream(filename);
      BufferedOutputStream out1 = new BufferedOutputStream(out0); // <== Decorator(장식품) 역할 수행!
      ObjectOutputStream out = new ObjectOutputStream(out1); // <== Decorator(장식품) 역할 수행!

      out.writeShort(list.size());

      for (Board board : list) {
        out.writeObject(board);
      }
      out.close();

    } catch (Exception e) {
      System.out.println(filename + " 파일을 저장하는 중 오류 발생!");
    }
  }

}
