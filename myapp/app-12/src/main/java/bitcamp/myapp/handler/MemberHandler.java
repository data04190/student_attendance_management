package bitcamp.myapp.handler;

import bitcamp.myapp.vo.Member;
import bitcamp.util.Prompt;
import java.time.LocalTime;

public class MemberHandler {

  static final int MAX_SIZE = 100;

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
    m.setStudentName(Prompt.inputString("이름? "));
    m.setEntryTime(LocalTime.parse(Prompt.inputString("입실 시간(HH:MM) ")));
    m.setExitTime(LocalTime.parse(Prompt.inputString("퇴실 시간(HH:MM) ")));
    m.setStudyTime(LocalTime.parse(Prompt.inputString("스터디 시간(HH:MM) ")));
    m.setLateStatus();
    m.setNo(userId++);

    members[length++] = m;
  }

  public static void printMembers() {
    System.out.println("=====================================================================");
    System.out.println("\t날짜\t이름\t입실 시간  퇴실 시간  스터디 시간  지각 여부");
    System.out.println("=====================================================================");
    for (int i = 0; i < length; i++) {
      Member m = members[i];
      System.out.printf("%-3d %-10s %-8s %-10s %-10s %-10s %-6s\n", 
                      m.getNo(), m.getDate(), m.getStudentName(), m.getEntryTime(), 
                      m.getExitTime(), m.getStudyTime(), m.getLateStatus());
    }
  }

  public static void viewMember() {
    String memberNo = Prompt.inputString("번호? ");
    for (int i = 0; i < length; i++) {
      Member m = members[i];
      if(m.getNo() == Integer.parseInt(memberNo)) {
        System.out.printf("이름: %s\n", m.getStudentName());
        System.out.printf("입실 시간: %s\n", m.getEntryTime());
        System.out.printf("퇴실 시간: %s\n", m.getExitTime());
        System.out.printf("스터디 시간: %s\n",  m.getStudyTime());
        System.out.printf("지각 여부: %s\n", m.getLateStatus());
        return;
      }
    }
    System.out.println("해당 번호의 회원이 없습니다!");
  }

  public static void updateMember() {
    String memberNo = Prompt.inputString("번호? ");
    for (int i = 0; i < length; i++) {
      Member m = members[i];
      if(m.getNo() == Integer.parseInt(memberNo)) {
        System.out.printf("이름(%s)? ", m.getStudentName());
        m.setStudentName(Prompt.inputString(""));
        System.out.printf("입실 시간(%s)? ", m.getEntryTime());
        m.setEntryTime(LocalTime.parse(Prompt.inputString("")));
        System.out.printf("퇴실 시간(%s)? ", m.getExitTime());
        m.setExitTime(LocalTime.parse(Prompt.inputString("")));
        System.out.printf("스터디 시간(%s)? ", m.getStudyTime());
        m.setStudyTime(LocalTime.parse(Prompt.inputString("")));

        m.setLateStatus();
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
      if (m.getNo() == memberNo) {
        return i;
      }
    }
    return -1;
  }

  private static boolean available() {
    return length < MAX_SIZE;
  }

}