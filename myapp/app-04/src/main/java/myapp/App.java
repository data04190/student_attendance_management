package myapp;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Scanner;

public class App {

  public static void main(String[] args) {
    System.out.println("나의 출결 관리 시스템");
    System.out.println("=================================");

    Scanner scanner = new Scanner(System.in);

    LocalDate date = LocalDate.now();
    System.out.printf("날짜: %s\n", date.toString());

    System.out.print("이름? ");
    String studentName = scanner.next();
    scanner.nextLine();

    System.out.print("입실시간(HH:MM) ");
    LocalTime entryTime = LocalTime.parse(scanner.nextLine());

    System.out.print("퇴실시간(HH:MM) ");
    LocalTime exitTime = LocalTime.parse(scanner.nextLine());

    System.out.print("스터디 시간(HH:MM) ");
    LocalTime studyTime = LocalTime.parse(scanner.nextLine());
  
    
    System.out.println("=================================");

    System.out.printf("날짜: %s\n", date.toString());
    System.out.printf("이름: %s\n", studentName);
    System.out.println("입실 시간: " + entryTime);
    System.out.println("퇴실 시간: " + exitTime);
    System.out.println("스터디 시간: " + studyTime);

    scanner.close();

  }
}
