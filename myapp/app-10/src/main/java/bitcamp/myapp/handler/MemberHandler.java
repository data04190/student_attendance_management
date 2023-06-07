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
    if (!available()) {
      System.out.println("더이상 입력할 수 없습니다!");
      return;
    }

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

  public static void viewMember() {
    String memberNo = Prompt.inputString("번호? ");
    for (int i = 0; i < length; i++) {
      if(no[i] == Integer.parseInt(memberNo)) {
        System.out.printf("이름: %s\n", studentName[i]);
        System.out.printf("입실 시간: %s\n", entryTime[i]);
        System.out.printf("퇴실 시간: %s\n", exitTime[i]);
        System.out.printf("스터디 시간: %s\n", studyTime[i]);
        System.out.printf("지각 여부: %s\n", lateStatus[i]);
        return;
      }
    }
    System.out.println("해당 번호의 회원이 없습니다!");
  }

  public static void updateMember() {
    String memberNo = Prompt.inputString("번호? ");
    for (int i = 0; i < length; i++) {
      if(no[i] == Integer.parseInt(memberNo)) {
        System.out.printf("이름(%s)? ", studentName[i]);
        studentName[i] = Prompt.inputString("");
        System.out.printf("입실 시간(%s)? ", entryTime[i]);
        entryTime[i] = LocalTime.parse(Prompt.inputString(""));
        System.out.printf("퇴실 시간(%s)? ", exitTime[i]);
        exitTime[i] = LocalTime.parse(Prompt.inputString(""));
        System.out.printf("스터디 시간(%s)? ", studyTime[i]);
        studyTime[i] = LocalTime.parse(Prompt.inputString(""));

        lateStatus[i] = entryTime[i].isAfter(lateTime) ? "O" : "X";
        return;
      }
    }
    System.out.println("해당 번호의 회원이 없습니다!");
  }


  public static void deleteMember() {

    int memberNo = Prompt.inputInt("번호? ");

    int deletedIndex = indexOf(memberNo);
    if (deletedIndex == -1) {
      System.out.println("해당 번호의 회원이 없습니다!");
      return;
    }

    for (int i = deletedIndex; i < length - 1; i++) {
      no[i] = no[i + 1];
      studentName[i] = studentName[i + 1];
      entryTime[i] = entryTime[i + 1];
      exitTime[i] = exitTime[i + 1];
      studyTime[i] = studyTime[i + 1];
      lateStatus[i] = lateStatus[i + 1];
    }

    no[length - 1] = 0;
    studentName[length - 1] = null;
    entryTime[length - 1] = null;
    exitTime[length - 1] = null;
    studyTime[length - 1] = null;
    lateStatus[length - 1] = null;

    length--;
  }

  private static int indexOf(int memberNo) {
    for (int i = 0; i < length; i++) {
      if (no[i] == memberNo) {
        return i;
      }
    }
    return -1;
  }

  private static boolean available() {
    return length < MAX_SIZE;
  }

}