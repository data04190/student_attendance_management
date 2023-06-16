package bitcamp.myapp.handler;

public class ArrayList {

  private static final int DEFAULT_SIZE = 3;

  private Object[] list = new Object[DEFAULT_SIZE];
  private int length = 0;

  public void add(Object obj) {
    if (this.length == list.length) {
      increase();
    }
    this.list[this.length++] = obj;
  }

  private void increase() {
    Object[] arr = new Object[list.length + (list.length >> 1)];

    for (int i = 0; i < list.length; i++) {
      arr[i] = list[i];
    }
    list = arr;

    // System.out.println("배열 확장: " + members.length);
  }

  public Object[] list() {
    Object[] arr = new Object[this.length];

    for (int i = 0; i < this.length; i++) {
      arr[i] = list[i];
    }
    return arr;
  }

  public Object get(Object obj) {
    for (int i = 0; i < this.length; i++) {
      Object item = this.list[i];
      if (item.equals(obj)) {
        return item;
      }
    }
    return null;
  }

  public boolean delete(Object obj) {

    int deletedIndex = indexOf(obj);
    if (deletedIndex == -1) {
      return false;
    }

    for (int i = deletedIndex; i < this.length - 1; i++) {
      this.list[i] = this.list[i + 1];
    }

    list[--length] = null;
    return true;

  }

  private int indexOf(Object obj) {
    for (int i = 0; i < this.length; i++) {
      Object item = this.list[i];
      if (item.equals(obj)) {
        return i;
      }
    }
    return -1;
  }

}
