package bitcamp.util;

public abstract class AbstractList<E> implements List<E> {

  protected int size;

  @Override
  public int size() {
    return this.size;
  }

  protected boolean isValid(int index) {
    return index >= 0 && index < this.size;
  }

  @Override
  public Iterator<E> iterator() {


    return new Iterator<E>() {
      int cursor;

      @Override
      public boolean hasNext() {
        return cursor < AbstractList.this.size();
      }

      @Override
      public E next() {
        return AbstractList.this.get(cursor++);
      }
    };
  }

  // static nested class
  static class ListIterator2<T> implements Iterator<T> {
    List<T> list;
    int cursor;

    public ListIterator2(List<T> list) {
      this.list = list;
    }

    @Override
    public boolean hasNext() {
      return cursor < list.size();
    }

    @Override
    public T next() {
      return list.get(cursor++);
    }
  }

  // non-static nested class = inner class
  class ListIterator3<T> implements Iterator<T> {

    int cursor;


    @Override
    public boolean hasNext() {
      return cursor < AbstractList.this.size();
    }

    @SuppressWarnings("unchecked")
    @Override
    public T next() {
      return (T) AbstractList.this.get(cursor++);
    }
  }
}
