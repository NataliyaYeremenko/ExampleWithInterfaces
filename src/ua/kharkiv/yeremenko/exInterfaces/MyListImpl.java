package ua.kharkiv.yeremenko.exInterfaces;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MyListImpl implements MyList, ListIterable {
    private List<Object> arrayOfObjects = new ArrayList<>();
    private static boolean checkCallNext = false;
    private static boolean checkCallRemove = false;
    private static boolean checkCallPrevious = false;
    private static boolean checkCallSet = false;

    @Override
    public void add(Object e) {
        arrayOfObjects.add(e);
    }

    @Override
    public void clear() {
        int arraySize = arrayOfObjects.size();
        for (int i = arraySize - 1; i >= 0; i--){
            arrayOfObjects.remove(i);
        }
    }

    @Override
    public boolean remove(Object e) {
        int arraySize = arrayOfObjects.size();
        for (int i = 0; i < arraySize; i++){
            if (arrayOfObjects.get(i).equals(e)) {
                arrayOfObjects.remove(i);
                return true;
            }
        }
        return false;
    }

    @Override
    public Object[] toArray() {
        Object[] objectsArray = new Object[arrayOfObjects.size()];
        int index = 0;
        for (Object object:arrayOfObjects) {
            objectsArray[index] = object;
            index++;
        }
        return objectsArray;
    }

    @Override
    public int size() {
        int arraySize = 0;
        for (Object object:arrayOfObjects) {
            arraySize++;
        }
        return arraySize;
    }

    @Override
    public boolean contains(Object o) {
        for (Object object:arrayOfObjects) {
            if (object.equals(o)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean containsAll(MyList c) {
        Object[] arrayList = c.toArray();
        boolean checkEquals = false;
        for (Object objectMyList:arrayList) {
            checkEquals = false;
            for (Object objectCon:arrayOfObjects) {
                if (objectCon.equals(objectMyList)) {
                    checkEquals = true;
                    break;
                }
            }
            if (!checkEquals) return false;
        }
        return checkEquals;
    }

    @Override
    public String toString(){
        String result = "[";
        if (arrayOfObjects.isEmpty()) return result + "]";
        else {
            for (int i = 0; i < arrayOfObjects.size() - 1; i++) {
                result += arrayOfObjects.get(i).toString() + ", ";
            }
            return result + arrayOfObjects.get(arrayOfObjects.size()-1).toString() + "]";
        }
    }
    public Iterator<Object> iterator(){
        return new IteratorImpl();
    }

    public ListIterator listIterator() {
        return new ListIteratorImpl();
    }

    private class IteratorImpl implements Iterator<Object>{
        private int currentIndex = 0;
        private int currentSize = arrayOfObjects.size();

        @Override
        public boolean hasNext() {
            return currentIndex < currentSize && arrayOfObjects.get(currentIndex) != null;
        }

        @Override
        public Object next() {
            if (currentIndex < arrayOfObjects.size()) {
                checkCallNext = true;
                checkCallRemove = false;
                checkCallPrevious = false;
                return arrayOfObjects.get(currentIndex++);
            }
            else {
                currentSize = arrayOfObjects.size();
                return null;
            }
        }

        @Override
        public void remove() {
            if (!checkCallNext | checkCallRemove)throw new IllegalStateException();
            if (currentIndex != 0) arrayOfObjects.remove(arrayOfObjects.get(--currentIndex));
            else arrayOfObjects.remove(arrayOfObjects.get(currentIndex));
            currentSize--;
            checkCallRemove = true;
        }
    }

    private class ListIteratorImpl extends IteratorImpl implements ListIterator{
        private int currentSize = arrayOfObjects.size();
        private int currentIndex = currentSize - 1;

        @Override
        public boolean hasPrevious() {
            currentSize = arrayOfObjects.size();
            if (currentSize == 1 && currentIndex == -1) currentIndex = 0;
            return (currentIndex == 0) || (currentIndex > 0 && arrayOfObjects.get(currentIndex) != null);
        }

        @Override
        public Object previous() {
            if (currentIndex == 0) return arrayOfObjects.get(currentIndex--);
            if (currentIndex > 0) {
                checkCallPrevious = true;
                checkCallRemove = false;
                checkCallSet = false;
                checkCallNext = false;
                return arrayOfObjects.get(currentIndex--);
            }
            else {
                currentSize = arrayOfObjects.size();
                return null;
            }
        }

        @Override
        public void set(Object e) {
            if (!checkCallNext & !checkCallPrevious | checkCallSet) throw new IllegalStateException();
            checkCallSet = true;
            arrayOfObjects.set(++currentIndex, e);
        }

        @Override
        public void remove() {
            if (!checkCallNext & !checkCallPrevious | checkCallRemove) throw new IllegalStateException();
            if (checkCallNext) super.remove();
            if (checkCallPrevious){
                arrayOfObjects.remove(arrayOfObjects.get(++currentIndex));
            }
            currentSize--;
            currentIndex--;
            checkCallRemove = true;
        }
    }
}
