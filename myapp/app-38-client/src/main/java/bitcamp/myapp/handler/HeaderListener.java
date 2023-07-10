package bitcamp.myapp.handler;

import bitcamp.util.ActionListener;
import bitcamp.util.BreadcrumbPrompt;

public class HeaderListener implements ActionListener {
  @Override
  public void service(BreadcrumbPrompt prompt) {
    System.out.println("============[비트캠프 학생 출결 관리 시스템]============");
  }
}
