package bitcamp.util;

import java.lang.reflect.Array;

public class LinkedList<E> extends AbstractList<E> {

  Node<E> head;
  Node<E> tail;

  public static void main(String[] args) {
    LinkedList<Integer> list = new LinkedList<>();
    list.add(100);
    list.add(200);
    list.add(300);
    list.add(400);
    list.add(500);

    print(list);

    System.out.println(list.remove(300));
    System.out.println(list.remove(500));
    System.out.println(list.remove(100));
    System.out.println(list.remove(200));
    System.out.println(list.remove(400));
    System.out.println(list.remove(600));
    list.add(1000);
    list.add(2000);
    print(list);


    // System.out.println(list.retrieve(100));
    // System.out.println(list.retrieve(300));
    // System.out.println(list.retrieve(500));
    // System.out.println(list.retrieve(600));
  }

  static void print(LinkedList<Integer> list) {
    Object[] arr = list.toArray();
    for (Object obj : arr) {
      System.out.print(obj);
      System.out.print(", ");
    }
    System.out.println();
  }

  @Override
  public boolean add(E value) {
    Node<E> node = new Node<>();
    node.value = value;

    if (this.head == null) {
      this.head = node;
    } else if (this.tail != null) {
      this.tail.next = node;
    }

    this.tail = node;
    this.size++;
    return true;
  }

  @Override
  public Object[] toArray() {
    Object[] arr = new Object[this.size];

    Node<E> cursor = this.head;
    for (int i = 0; i < this.size; i++) {
      arr[i] = cursor.value;
      cursor = cursor.next;
    }

    return arr;
  }

  @SuppressWarnings("unchecked")
  @Override
  public <T> T[] toArray(T[] arr) {
    T[] values = null;

    if (arr.length < this.size) {
      values = (T[]) Array.newInstance(arr.getClass().getComponentType(), this.size);

    } else {
      values = arr;
    }

    Node<E> cursor = this.head;
    for (int i = 0; i < this.size; i++) {
      arr[i] = (T) cursor.value;
      cursor = cursor.next;
    }

    return values;
  }

  @Override
  public E get(int index) {
    if (!isValid(index)) {
      return null;
    }

    Node<E> cursor = this.head;

    for (int i = 0; i < index; i++) {
      cursor = cursor.next;
    }

    return cursor.value;
  }

  @Override
  public boolean remove(E value) {
    Node<E> prev = null;
    Node<E> cursor = this.head;

    while (cursor != null) {
      if (cursor.value.equals(value)) {
        if (prev == null) {
          // 삭제할 노드가 시작 노드라면
          this.head = cursor.next;

          // 삭제할 노드가 끝 노드라면
          if (this.head == null) {
            this.tail = null;
          }

        } else if (cursor.next == null) {
          // 삭제할 노드가 끝 노드라면
          this.tail = prev;
          this.tail.next = null;

        } else {
          // 중간 노드라면, 다음 노드의 주소를 이전 노드에 저장한다.
          prev.next = cursor.next;
        }
        this.size--;

        // 가비지 객체를 초기화시켜서 가비지가 인스턴스를 가리키지 않도록 한다.
        cursor.next = null;
        cursor.value = null;

        return true;
      }

      // 현재 커서가 가리키는 노드를 prev에 보관한다.
      prev = cursor;

      // 현재 커서를 다음 노드로 이동한다.
      cursor = cursor.next;
    }

    return false;
  }

  @Override
  public E remove(int index) {
    if (!isValid(index)) {
      return null;
    }

    // 삭제하려는 값이 있는 노드까지 이동한다.
    Node<E> prev = null;
    Node<E> cursor = this.head;

    for (int i = 0; i < index; i++) {
      prev = cursor; // 다음 노드로 이동하기 전에 현재 커서가 가리키는 노드를 prev에 보관한다.
      cursor = cursor.next; // 커서를 다음 노드로 이동시킨다.
    }

    // 삭제할 값을 리턴할 수 있도록 보관한다.
    E old = cursor.value;

    if (prev == null) {
      this.head = cursor.next;
      if (this.head == null) {
        this.tail = null;
      }
    } else if (cursor.next == null) {
      this.tail = prev;
      this.tail.next = null;
    } else {
      prev.next = cursor.next; // 현재 커서의 다음 노드를 현재 커서의 이전 노드와 연결한다.
    }
    size--;
    cursor.next = null;
    cursor.value = null;

    return old;
  }


  static class Node<T> {
    T value;
    Node<T> next;
  }

}
