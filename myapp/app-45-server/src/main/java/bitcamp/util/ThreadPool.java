package bitcamp.util;

import java.util.ArrayList;

public class ThreadPool implements ResourcePool<ManagedThread> {

  ArrayList<ManagedThread> list = new ArrayList<>();

  @Override
  public ManagedThread getResource() {
    ManagedThread t = null;

    if (list.size() == 0) {
      t = new ManagedThread(this);
      t.start();

      try {
        Thread.sleep(100);
      } catch (Exception e) {
      }

      return t;
    }

    t = list.remove(0);
    System.out.printf("기존 스레드 리턴: %d\n", t.no);
    return t;
  }

  @Override
  public void returnResource(ManagedThread resource) {
    list.add(resource);
    System.out.printf("스레드 반납: %d\n", resource.no);

  }

}
