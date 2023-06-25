package bitcamp.util;

public class Queue extends LinkedList {

  public void offer(Object value) {
    this.add(value);
  }

  public Object poll() {
    if (this.size() == 0) {
      return null;
    }
    return this.remove(0);
  }
}
