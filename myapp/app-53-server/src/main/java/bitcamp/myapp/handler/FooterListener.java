package bitcamp.myapp.handler;

import bitcamp.util.ActionListener;
import bitcamp.util.BreadcrumbPrompt;

public class FooterListener implements ActionListener {
  @Override
  public void service(BreadcrumbPrompt prompt) {
    prompt.println("Copyright \u00a9 by NAVER CLOUD 7기----------------------------");
  }
}
