package ua.kharkiv.yeremenko.exInterfaces;

import java.util.Iterator;

interface ListIterator extends Iterator<Object>{
    boolean hasPrevious();
    Object previous();
    void set(Object e);
    void remove();
}
