package bitcamp.myapp.handler;


import java.time.LocalTime;
import bitcamp.myapp.vo.Member;
import bitcamp.util.LinkedList;
import bitcamp.util.Prompt;


public class MemberHandler implements Handler {

  private LinkedList list = new LinkedList();
  private Prompt prompt;
  private String title;

  public MemberHandler(Prompt prompt, String title) {
    this.prompt = prompt;
    this.title = title;
  }

  public void execute() {
    printMenu();

    while (true) {
      String menuNo = prompt.inputString("%s> ", this.title);
      if (menuNo.equals("0")) {
        break;
      } else if (menuNo.equals("menu")) {
        printMenu();
      } else if (menuNo.equals("1")) {
        this.inputMember();
      } else if (menuNo.equals("2")) {
        this.printMembers();
      } else if (menuNo.equals("3")) {
        this.viewMember();
      } else if (menuNo.equals("4")) {
        this.updateMember();
      } else if (menuNo.equals("5")) {
        this.deleteMember();
      } else {
        System.out.println("메뉴 번호가 옳지 않습니다!");
      }

    }
  }

  private static void printMenu() {
    System.out.println("1. 등록");
    System.out.println("2. 목록");
    System.out.println("3. 조회");
    System.out.println("4. 변경");
    System.out.println("5. 삭제");
    System.out.println("0. 메인");
  }

  private void inputMember() {

    Member m = new Member();
    m.setStudentName(this.prompt.inputString("이름? "));
    m.setEntryTime(LocalTime.parse(this.prompt.inputString("입실 시간(HH:MM) ")));
    m.setExitTime(LocalTime.parse(this.prompt.inputString("퇴실 시간(HH:MM) ")));
    m.setStudyTime(LocalTime.parse(this.prompt.inputString("스터디 시간(HH:MM) ")));
    m.setLateStatus();

    this.list.add(m);

  }

  private void printMembers() {
    System.out.println("=====================================================================");
    System.out.println("\t날짜\t이름\t입실 시간  퇴실 시간  스터디 시간  지각 여부");
    System.out.println("=====================================================================");

    Object[] arr = this.list.getList();
    for (Object obj : arr) {
      Member m = (Member) obj;
      System.out.printf("%-3d %-10s %-7s %-10s %-11s %-13s %-10s\n", m.getNo(), m.getDate(),
          m.getStudentName(), m.getEntryTime(), m.getExitTime(), m.getStudyTime(),
          m.getLateStatus());
    }
  }

  private void viewMember() {
    int memberNo = this.prompt.inputInt("번호? ");

    Member m = (Member) this.list.retrieve(new Member(memberNo));
    if (m == null) {
      System.out.println("해당 번호의 회원이 없습니다.");
      return;
    }
    System.out.printf("이름: %s\n", m.getStudentName());
    System.out.printf("입실 시간: %s\n", m.getEntryTime());
    System.out.printf("퇴실 시간: %s\n", m.getExitTime());
    System.out.printf("스터디 시간: %s\n", m.getStudyTime());
    System.out.printf("지각 여부: %s\n", m.getLateStatus());
  }


  private void updateMember() {

    int memberNo = this.prompt.inputInt("번호? ");

    Member m = (Member) this.list.retrieve(new Member(memberNo));
    if (m == null) {
      System.out.println("해당 번호의 회원이 없습니다.");
      return;
    }

    m.setStudentName(this.prompt.inputString("이름(%s)? ", m.getStudentName()));
    m.setEntryTime(LocalTime.parse(this.prompt.inputString("입실 시간(%s)? ", m.getEntryTime())));
    m.setExitTime(LocalTime.parse(this.prompt.inputString("퇴실 시간(%s)? ", m.getExitTime())));
    m.setStudyTime(LocalTime.parse(this.prompt.inputString("스터디 시간(%s)? ", m.getStudyTime())));
    m.setLateStatus();
    return;
  }

  private void deleteMember() {

    if (!this.list.remove(new Member(this.prompt.inputInt("번호? ")))) {
      System.out.println("해당 번호의 회원이 없습니다.");

    }

  }
}
