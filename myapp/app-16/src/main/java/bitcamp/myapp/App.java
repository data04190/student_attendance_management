package bitcamp.myapp;

import bitcamp.myapp.handler.BoardHandler;
import bitcamp.myapp.handler.MemberHandler;
import bitcamp.util.Prompt;

public class App {

  public static void main(String[] args) {

    Prompt prompt = new Prompt();

    MemberHandler memberhandler = new MemberHandler(prompt, "회원");
    BoardHandler boardhandler = new BoardHandler(prompt, "게시글");


    printTitle();

    printMenu();

    while (true) {
      String menuNo = prompt.inputString("main> ");
      if (menuNo.equals("0")) {
        break;
      } else if (menuNo.equals("menu")) {
        printMenu();
      } else if (menuNo.equals("1")) {
        memberhandler.execute();
      } else if (menuNo.equals("2")) {
        boardhandler.execute();
      } else {
        System.out.println("메뉴 번호가 옳지 않습니다.");
      }
    }

    prompt.close();
  }

  static void printMenu() {
    System.out.println("1. 회원");
    System.out.println("2. 게시글");
    System.out.println("0. 종료");
  }

  static void printTitle() {
    System.out.println("학생 출결 관리 시스템");
    System.out.println("=================================");
  }

}
