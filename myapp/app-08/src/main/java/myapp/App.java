package myapp;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Scanner;

public class App {

  static Scanner scanner = new Scanner(System.in);

    static final int MAX_SIZE = 100;
    static LocalDate date = LocalDate.now();
    static LocalTime lateTime = LocalTime.parse("09:30");

    static int[] no = new int[MAX_SIZE];
    static String[] studentName = new String[MAX_SIZE];
    static LocalTime[] entryTime = new LocalTime[MAX_SIZE];
    static LocalTime[] exitTime = new LocalTime[MAX_SIZE];
    static LocalTime[] studyTime = new LocalTime[MAX_SIZE];
    static String[] lateStatus =  new String[MAX_SIZE];
    static int userId = 1;
    static int length = 0;

  public static void main(String[] args) {

    printTitle();

    //학생 정보 등록

    while (length < MAX_SIZE) {
      inputMember();
      if (!promptContinue()) {
        break;
      }
    }

    printMembers();

    scanner.close();
  }


  static void printTitle() {
    System.out.println("나의 출결 관리 시스템");
    System.out.println("=================================");
  }

  static void inputMember() {

    System.out.printf("날짜: %s\n", date.toString());

    System.out.print("이름? ");
    studentName[length] = scanner.next();
    scanner.nextLine();

    System.out.print("입실 시간(HH:MM) ");
    entryTime[length] = LocalTime.parse(scanner.nextLine());

    System.out.print("퇴실 시간(HH:MM) ");
    exitTime[length] = LocalTime.parse(scanner.nextLine());

    System.out.print("스터디 시간(HH:MM) ");
    studyTime[length] = LocalTime.parse(scanner.nextLine());

    lateStatus[length] = entryTime[length].isAfter(lateTime) ? "O" : "X";

    no[length] = userId++;
    length++;
    
  }

  static boolean promptContinue() {

    String response = prompt("계속 하시겠습니까?(Y/n) ");
    if (!response.equals("") && !response.equalsIgnoreCase("Y")) {
      return false;
    }
    return true;
  }

  static void printMembers() {

    System.out.println("=====================================================================");
    System.out.println("\t날짜\t이름\t입실 시간  퇴실 시간  스터디 시간  지각 여부");
    System.out.println("=====================================================================");
    for (int i = 0; i < length; i++) {
      System.out.printf("%-3d %-10s %-8s %-10s %-10s %-10s %-6s\n", 
                      no[i], date.toString(), studentName[i], entryTime[i], 
                      exitTime[i], studyTime[i], lateStatus[i]);
    }
  }

  static String prompt(String title) {
    System.out.print(title);
    return scanner.nextLine();
  }

}
