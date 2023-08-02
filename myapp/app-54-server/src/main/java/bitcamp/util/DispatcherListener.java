package bitcamp.util;

import java.io.IOException;

public class DispatcherListener implements ActionListener {

  ApplicationContext iocContainer;

  public DispatcherListener(ApplicationContext iocContainer) throws Exception {
    this.iocContainer = iocContainer;
  }

  public void service(BreadcrumbPrompt prompt) throws IOException {

    String menuPath = (String) prompt.getAttribute("menuPath");
    System.out.println("Requested menu path: " + menuPath);

    ActionListener listener = (ActionListener) iocContainer.getBean(menuPath);
    if (listener == null) {
      throw new RuntimeException("해당 요청을 처리할 수 없습니다.");
    }
    listener.service(prompt);
  }
}
