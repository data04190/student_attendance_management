package myapp;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Scanner;

public class App {

  public static void main(String[] args) {
    System.out.println("나의 출결 관리 시스템");
    System.out.println("=================================");

    Scanner scanner = new Scanner(System.in);

    final int SIZE = 3;

    LocalDate date = LocalDate.now();
    String[] studentName = new String[SIZE];
    LocalTime[] entryTime = new LocalTime[SIZE];
    LocalTime[] exitTime = new LocalTime[SIZE];
    LocalTime[] studyTime = new LocalTime[SIZE];


    for (int i = 0; i < SIZE; i++) {

      System.out.printf("날짜: %s\n", date.toString());

      System.out.print("이름? ");
      studentName[i] = scanner.next();
      scanner.nextLine();

      System.out.print("입실 시간(HH:MM) ");
      entryTime[i] = LocalTime.parse(scanner.nextLine());

      System.out.print("퇴실 시간(HH:MM) ");
      exitTime[i] = LocalTime.parse(scanner.nextLine());

      System.out.print("퇴실 시간(HH:MM) ");
      studyTime[i] = LocalTime.parse(scanner.nextLine());

    }

    System.out.println("=================================");

    for (int i = 0; i < SIZE; i++) {

      System.out.printf("날짜: %s\n", date.toString());
      System.out.printf("이름: %s\n", studentName[i]);
      System.out.println("입실 시간: " + entryTime[i]);
      System.out.println("퇴실 시간: " + exitTime[i]);
      System.out.println("스터디 시간: " + studyTime[i]);
    }
    scanner.close();

  }
}
