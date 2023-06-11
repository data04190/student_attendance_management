package bitcamp.myapp.handler;

import bitcamp.myapp.vo.Member;
import bitcamp.util.Prompt;
import java.time.LocalDate;
import java.time.LocalTime;

public class MemberHandler {

  static final int MAX_SIZE = 100;
  static LocalDate date = LocalDate.now();
  static LocalTime lateTime = LocalTime.parse("09:30");

  static int[] no = new int[MAX_SIZE];
  static Member[] members = new Member[MAX_SIZE];

  static int userId = 1;
  static int length = 0;


  public static void inputMember() {
    if (!available()) {
      System.out.println("더이상 입력할 수 없습니다!");
      return;
    }

    Member m = new Member();
    m.studentName = Prompt.inputString("이름? ");
    m.entryTime = LocalTime.parse(Prompt.inputString("입실 시간(HH:MM) "));
    m.exitTime = LocalTime.parse(Prompt.inputString("퇴실 시간(HH:MM) "));
    m.studyTime = LocalTime.parse(Prompt.inputString("스터디 시간(HH:MM) "));
    m.lateStatus = m.entryTime.isAfter(lateTime) ? "O" : "X";
    m.no = userId++;

    members[length++] = m;
  }

  public static void printMembers() {
    System.out.println("=====================================================================");
    System.out.println("\t날짜\t이름\t입실 시간  퇴실 시간  스터디 시간  지각 여부");
    System.out.println("=====================================================================");
    for (int i = 0; i < length; i++) {
      Member m = members[i];
      System.out.printf("%-3d %-10s %-8s %-10s %-10s %-10s %-6s\n", 
                      m.no, date.toString(), m.studentName, m.entryTime, 
                      m.exitTime, m.studyTime, m.lateStatus);
    }
  }

  public static void viewMember() {
    String memberNo = Prompt.inputString("번호? ");
    for (int i = 0; i < length; i++) {
      Member m = members[i];
      if(m.no == Integer.parseInt(memberNo)) {
        System.out.printf("이름: %s\n", m.studentName);
        System.out.printf("입실 시간: %s\n", m.entryTime);
        System.out.printf("퇴실 시간: %s\n", m.exitTime);
        System.out.printf("스터디 시간: %s\n", m.studyTime);
        System.out.printf("지각 여부: %s\n", m.lateStatus);
        return;
      }
    }
    System.out.println("해당 번호의 회원이 없습니다!");
  }

  public static void updateMember() {
    String memberNo = Prompt.inputString("번호? ");
    for (int i = 0; i < length; i++) {
      Member m = members[i];
      if(m.no == Integer.parseInt(memberNo)) {
        System.out.printf("이름(%s)? ", m.studentName);
        m.studentName = Prompt.inputString("");
        System.out.printf("입실 시간(%s)? ", m.entryTime);
        m.entryTime = LocalTime.parse(Prompt.inputString(""));
        System.out.printf("퇴실 시간(%s)? ", m.exitTime);
        m.exitTime = LocalTime.parse(Prompt.inputString(""));
        System.out.printf("스터디 시간(%s)? ", m.studyTime);
        m.studyTime = LocalTime.parse(Prompt.inputString(""));

        m.lateStatus = m.entryTime.isAfter(lateTime) ? "O" : "X";
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
      members[i] = members[i + 1];
    }

    members[--length] = null;
  }

  private static int indexOf(int memberNo) {
    for (int i = 0; i < length; i++) {
      Member m = members[i];
      if (m.no == memberNo) {
        return i;
      }
    }
    return -1;
  }

  private static boolean available() {
    return length < MAX_SIZE;
  }

}