package ua.kharkiv.yeremenko.exInterfaces;

public interface MyList extends Iterable<Object> {
    void add(Object e);
    void clear();
    boolean remove (Object e);
    Object[] toArray();
    int size();
    boolean contains(Object o);
    boolean containsAll(MyList c);
}
