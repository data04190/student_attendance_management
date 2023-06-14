package bitcamp.myapp.handler;

import java.time.LocalTime;
import bitcamp.myapp.vo.Member;
import bitcamp.util.Prompt;

public class MemberHandler {

  static final int MAX_SIZE = 100;

  private Prompt prompt;

  private Member[] members = new Member[MAX_SIZE];

  private int length = 0;

  public MemberHandler(Prompt prompt) {
    this.prompt = prompt;
  }

  public void inputMember() {
    if (!this.available()) {
      System.out.println("더이상 입력할 수 없습니다!");
      return;
    }

    Member m = new Member();
    m.setStudentName(this.prompt.inputString("이름? "));
    m.setEntryTime(LocalTime.parse(this.prompt.inputString("입실 시간(HH:MM) ")));
    m.setExitTime(LocalTime.parse(this.prompt.inputString("퇴실 시간(HH:MM) ")));
    m.setStudyTime(LocalTime.parse(this.prompt.inputString("스터디 시간(HH:MM) ")));
    m.setLateStatus();


    members[this.length++] = m;
  }

  public void printMembers() {
    System.out.println("=====================================================================");
    System.out.println("\t날짜\t이름\t입실 시간  퇴실 시간  스터디 시간  지각 여부");
    System.out.println("=====================================================================");
    for (int i = 0; i < this.length; i++) {
      Member m = this.members[i];
      System.out.printf("%-3d %-10s %-7s %-10s %-11s %-13s %-10s\n", m.getNo(), m.getDate(),
          m.getStudentName(), m.getEntryTime(), m.getExitTime(), m.getStudyTime(),
          m.getLateStatus());
    }
  }

  public void viewMember() {
    String memberNo = this.prompt.inputString("번호? ");
    for (int i = 0; i < this.length; i++) {
      Member m = this.members[i];
      if (m.getNo() == Integer.parseInt(memberNo)) {
        System.out.printf("이름: %s\n", m.getStudentName());
        System.out.printf("입실 시간: %s\n", m.getEntryTime());
        System.out.printf("퇴실 시간: %s\n", m.getExitTime());
        System.out.printf("스터디 시간: %s\n", m.getStudyTime());
        System.out.printf("지각 여부: %s\n", m.getLateStatus());
        return;
      }
    }
    System.out.println("해당 번호의 회원이 없습니다!");
  }

  public void updateMember() {
    String memberNo = this.prompt.inputString("번호? ");
    for (int i = 0; i < this.length; i++) {
      Member m = this.members[i];
      if (m.getNo() == Integer.parseInt(memberNo)) {
        System.out.printf("이름(%s)? ", m.getStudentName());
        m.setStudentName(this.prompt.inputString(""));
        System.out.printf("입실 시간(%s)? ", m.getEntryTime());
        m.setEntryTime(LocalTime.parse(this.prompt.inputString("")));
        System.out.printf("퇴실 시간(%s)? ", m.getExitTime());
        m.setExitTime(LocalTime.parse(this.prompt.inputString("")));
        System.out.printf("스터디 시간(%s)? ", m.getStudyTime());
        m.setStudyTime(LocalTime.parse(this.prompt.inputString("")));

        m.setLateStatus();
        return;
      }
    }
    System.out.println("해당 번호의 회원이 없습니다!");
  }


  public void deleteMember() {

    int memberNo = this.prompt.inputInt("번호? ");

    int deletedIndex = indexOf(memberNo);
    if (deletedIndex == -1) {
      System.out.println("해당 번호의 회원이 없습니다!");
      return;
    }

    for (int i = deletedIndex; i < this.length - 1; i++) {
      this.members[i] = this.members[i + 1];
    }

    members[--length] = null;
  }

  private int indexOf(int memberNo) {
    for (int i = 0; i < this.length; i++) {
      Member m = this.members[i];
      if (m.getNo() == memberNo) {
        return i;
      }
    }
    return -1;
  }

  private boolean available() {
    return this.length < MAX_SIZE;
  }

}
