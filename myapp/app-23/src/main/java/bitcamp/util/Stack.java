package bitcamp.util;

public class Stack extends LinkedList {

  public void push(Object value) {
    // 목록 맨 끝에 추가한다.
    // 따로 만들 필요가 없다.
    // 수퍼 클래스에 있는 메서드를 이용하여 기능을 구현한다.
    this.add(value); // 상속 받은 메서드 = 서브 클래스에서 사용할 수 있는 수퍼 클래스의 메서드
  }

  public Object pop() {
    if (this.empty()) {
      return null;
    }
    return this.remove(this.size() - 1);
  }

  public Object peek() {
    if (this.empty()) {
      return null;
    }
    return this.get(this.size() - 1);
  }

  public boolean empty() {
    return this.size() == 0;
  }
}
