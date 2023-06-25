package bitcamp.myapp.handler;


import java.time.LocalTime;
import bitcamp.myapp.vo.Member;
import bitcamp.util.List;
import bitcamp.util.MenuPrompt;


public class MemberHandler implements Handler {

  private List list;
  private MenuPrompt prompt;
  private String title;


  public MemberHandler(MenuPrompt prompt, String title, List list) {
    this.prompt = prompt;
    this.title = title;
    this.list = list;
  }

  public void execute() {

    prompt.appendBreadcrumb(this.title, getMenu());
    prompt.printMenu();

    while (true) {
      String menuNo = prompt.inputMenu();
      switch (menuNo) {
        case "0":
          prompt.removeBreadcrumb();
          return;
        case "1":
          this.inputMember();
          break;
        case "2":
          this.printMembers();
          break;
        case "3":
          this.viewMember();
          break;
        case "4":
          this.updateMember();
          break;
        case "5":
          this.deleteMember();
          break;
      }


    }
  }

  private static String getMenu() {
    StringBuilder menu = new StringBuilder();
    menu.append("1. 등록\n");
    menu.append("2. 목록\n");
    menu.append("3. 조회\n");
    menu.append("4. 변경\n");
    menu.append("5. 삭제\n");
    menu.append("0. 메인\n");
    return menu.toString();

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

    for (int i = 0; i < this.list.size(); i++) {
      Member m = (Member) this.list.get(i);
      System.out.printf("%-3d %-10s %-7s %-10s %-11s %-13s %-10s\n", m.getNo(), m.getDate(),
          m.getStudentName(), m.getEntryTime(), m.getExitTime(), m.getStudyTime(),
          m.getLateStatus());
    }
  }

  private void viewMember() {
    int memberNo = this.prompt.inputInt("번호? ");

    Member m = this.findBy(memberNo);
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

    Member m = this.findBy(memberNo);
    if (m == null) {
      System.out.println("해당 번호의 회원이 없습니다.");
      return;
    }

    m.setStudentName(this.prompt.inputString("이름(%s)? ", m.getStudentName()));
    m.setEntryTime(LocalTime.parse(this.prompt.inputString("입실 시간(%s)? ", m.getEntryTime())));
    m.setExitTime(LocalTime.parse(this.prompt.inputString("퇴실 시간(%s)? ", m.getExitTime())));
    m.setStudyTime(LocalTime.parse(this.prompt.inputString("스터디 시간(%s)? ", m.getStudyTime())));
    m.setLateStatus();

  }

  private void deleteMember() {

    if (!this.list.remove(new Member(this.prompt.inputInt("번호? ")))) {
      System.out.println("해당 번호의 회원이 없습니다.");

    }

  }

  private Member findBy(int no) {
    for (int i = 0; i < this.list.size(); i++) {
      Member m = (Member) this.list.get(i);
      if (m.getNo() == no) {
        return m;
      }
    }
    return null;
  }
}
