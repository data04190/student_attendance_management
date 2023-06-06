package bitcamp.myapp.handler;

import bitcamp.util.Prompt;
import java.time.LocalDate;
import java.time.LocalTime;

public class MemberHandler {

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


  public static void inputMember() {

    // System.out.printf("날짜: %s\n", date.toString());

    studentName[length] = Prompt.inputString("이름? ");
    entryTime[length] = LocalTime.parse(Prompt.inputString("입실 시간(HH:MM) "));
    exitTime[length] = LocalTime.parse(Prompt.inputString("퇴실 시간(HH:MM) "));
    studyTime[length] = LocalTime.parse(Prompt.inputString("스터디 시간(HH:MM) "));

    lateStatus[length] = entryTime[length].isAfter(lateTime) ? "O" : "X";

    no[length] = userId++;
    length++;
    
  }

  public static void printMembers() {
    System.out.println("=====================================================================");
    System.out.println("\t날짜\t이름\t입실 시간  퇴실 시간  스터디 시간  지각 여부");
    System.out.println("=====================================================================");
    for (int i = 0; i < length; i++) {
      System.out.printf("%-3d %-10s %-8s %-10s %-10s %-10s %-6s\n", 
                      no[i], date.toString(), studentName[i], entryTime[i], 
                      exitTime[i], studyTime[i], lateStatus[i]);
    }
  }

  public static boolean available() {
    return length < MAX_SIZE;
  }
}