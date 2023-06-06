package myapp;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Scanner;

public class App {

  public static void main(String[] args) {
    System.out.println("나의 출결 관리 시스템");
    System.out.println("=================================");

    Scanner scanner = new Scanner(System.in);

    final int MAX_SIZE = 100;
    int userId = 1;
    int length = 0;

    LocalDate date = LocalDate.now();
    LocalTime lateTime = LocalTime.parse("09:30");

    int[] no = new int[MAX_SIZE];
    String[] studentName = new String[MAX_SIZE];
    LocalTime[] entryTime = new LocalTime[MAX_SIZE];
    LocalTime[] exitTime = new LocalTime[MAX_SIZE];
    LocalTime[] studyTime = new LocalTime[MAX_SIZE];
    String[] lateStatus =  new String[MAX_SIZE];

    //학생 정보 등록
    for (int i = 0; i < MAX_SIZE; i++) {

      System.out.printf("날짜: %s\n", date.toString());

      System.out.print("이름? ");
      studentName[i] = scanner.next();
      scanner.nextLine();

      System.out.print("입실 시간(HH:MM) ");
      entryTime[i] = LocalTime.parse(scanner.nextLine());

      System.out.print("퇴실 시간(HH:MM) ");
      exitTime[i] = LocalTime.parse(scanner.nextLine());

      System.out.print("스터디 시간(HH:MM) ");
      studyTime[i] = LocalTime.parse(scanner.nextLine());

      lateStatus[i] = entryTime[i].isAfter(lateTime) ? "O" : "X";

      no[i] = userId++;
      length++;

      System.out.print("계속 하시겠습니까?(Y/n) ");
      String response = scanner.nextLine();
      if (!response.equals("") && !response.equalsIgnoreCase("Y")) {
        break;
      }

    }

    System.out.println("=====================================================================");


    System.out.println("\t날짜\t이름\t입실 시간  퇴실 시간  스터디 시간  지각 여부");
    System.out.println("=====================================================================");
    for (int i = 0; i < length; i++) {
      System.out.printf("%-3d %-10s %-8s %-10s %-10s %-10s %-6s\n", 
                      no[i], date.toString(), studentName[i], entryTime[i], 
                      exitTime[i], studyTime[i], lateStatus[i]);
    }

    scanner.close();

  }
}
