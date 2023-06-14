package bitcamp.myapp;

import bitcamp.myapp.handler.BoardHandler;
import bitcamp.myapp.handler.MemberHandler;
import bitcamp.util.Prompt;

public class App {

  public static void main(String[] args) {

    Prompt prompt = new Prompt();

    MemberHandler memberhandler = new MemberHandler(prompt);
    BoardHandler boardhandler = new BoardHandler(prompt);


    printTitle();

    printMenu();

    while (true) {
      String menuNo = prompt.inputString("main> ");
      if (menuNo.equals("99")) {
        break;
      } else if (menuNo.equals("menu")) {
        printMenu();
      } else if (menuNo.equals("1")) {
        memberhandler.inputMember();
      } else if (menuNo.equals("2")) {
        memberhandler.printMembers();
      } else if (menuNo.equals("3")) {
        memberhandler.viewMember();
      } else if (menuNo.equals("4")) {
        memberhandler.updateMember();
      } else if (menuNo.equals("5")) {
        memberhandler.deleteMember();
      } else if (menuNo.equals("6")) {
        boardhandler.inputBoard();
      } else if (menuNo.equals("7")) {
        boardhandler.printBoards();
      } else if (menuNo.equals("8")) {
        boardhandler.viewBoard();
      } else if (menuNo.equals("9")) {
        boardhandler.updateBoard();
      } else if (menuNo.equals("10")) {
        boardhandler.deleteBoard();
      } else {
        System.out.println("메뉴 번호가 옳지 않습니다.");
      }
    }

    prompt.close();
  }

  static void printMenu() {
    System.out.println("1. 회원등록");
    System.out.println("2. 회원목록");
    System.out.println("3. 회원조회");
    System.out.println("4. 회원변경");
    System.out.println("5. 회원삭제");
    System.out.println("6. 게시글등록");
    System.out.println("7. 게시글목록");
    System.out.println("8. 게시글조회");
    System.out.println("9. 게시글변경");
    System.out.println("10. 게시글삭제");
    System.out.println("99. 종료");

  }

  static void printTitle() {
    System.out.println("학생 출결 관리 시스템");
    System.out.println("=================================");
  }

}
