package ru.marat.javacore;

import java.util.*;
public class Real<T> implements List<T>, AuthorHolder {
    private Object[] array;
    private int size;
    private static final String FirstName = "Yakupov";
    private static final String Name = "Marat";
    public Real(Object[] object) {
        this.array = Arrays.copyOf(object, object.length);
        size = object.length;
    }

    @Override
    public int size() {
        return array.length;
    }

    @Override
    public boolean isEmpty() {
        return array.length == 0;
    }

    @Override
    public boolean contains(Object o) {
        for (Object value : array) {
            if (value.equals(o))
                return true;
        }
        return false;
    }

    @Override
    public Iterator<T> iterator() {
        return new MyIterator();
    }

    @Override
    public Object[] toArray() {
        return Arrays.copyOf(array, size);
    }

    @Override
    public <T1> T1[] toArray(T1[] a) {
        if (a.length <= size) {
            return (T1[]) Arrays.copyOf(array, size);
        }

        a = (T1[]) Arrays.copyOf(array, a.length);
        for (int i = size; i < a.length; i++) {
            a[i] = null;
        }
        return a;
    }

    @Override
    public boolean add(T t) {
        if (size == array.length)
            array = Arrays.copyOf(array, size + 1);
        array[size] = t;
        return true;
    }

    @Override // передвинуть правые елементы влево от удаленного элемента
    public boolean remove(Object o) {
        Object[] obj = new Object[array.length - 1];
        for (int i = 0; i < array.length - 1; i++) {
            if (!array[i].equals(o)) {
                obj[i] = array[i];
            }
        }

        array = obj;
        size--;
        return true;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        for (Object o : c) {
            if (!contains(o)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        Object[] newArray = c.toArray();
        int newSize = size + c.size();
        int counter = 0;
        array = Arrays.copyOf(array, newSize);

        for (int i = 0; i < newSize; i++) {
            if (i >= size) {
                array[i] = newArray[counter];
                counter++;
            }

        }
        size = newSize;
        return true;
    }

    @Override
    public boolean addAll(int index, Collection<? extends T> c) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }

        Object[] newArray = c.toArray();
        int newSize = size + c.size();
        int sizeDifference = newSize - size;
        int counter = 0;
        array = Arrays.copyOf(array, newSize);

        for (int i = newSize - 1; i >= 0; i--) {
            if (i == index) {
                for (int j = i; j < i + sizeDifference; j++) {
                    array[j] = newArray[counter];
                    counter++;
                }
                break;
            }
            else
                array[i] = array[i - sizeDifference];
        }
        size = newSize;
        return true;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        Object[] obj = new Object[size];
        int counter = 0;

        for (int i = 0; i < size; i++) {
            if (!c.contains(array[i])) {
                obj[counter] = array[i];
                counter++;
            }
        }

        if (size == counter)
            return false;

        Object[] newObject = new Object[counter];
        counter = 0;
        for (int i = 0; i < size; i++) {
            if (obj[i] != null) {
                newObject[counter] = obj[i];
                counter++;
            }
        }

        array = newObject;
        size = counter;
        return true;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        Object[] obj = new Object[size];
        int counter = 0;

        for (int i = 0; i < size; i++) {
            if (!c.contains(array[i])) {
                obj[counter] = array[i];
                counter++;
            }
        }

        if (size == counter)
            return false;

        Object[] newObject = new Object[counter];
        counter = 0;

        for (int i = 0; i < size; i++) {
            if (obj[i] != null) {
                newObject[counter] = obj[i];
                counter++;
            }
        }

        array = newObject;
        size = counter;
        return true;
    }

    @Override
    public void clear() {
        array = new Object[0];
        size = 0;
    }

    @Override
    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        return (T) array[index];
    }

    @Override
    public T set(int index, T element) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        T oldElement = (T) array[index];
        array[index] = element;
        return oldElement;
    }

    @Override
    public void add(int index, T element) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        array = Arrays.copyOf(array, size + 1);
        int counter = 1;

        for (int i = size; i >= 0; i--) {
            if (i == index) {
                array[index] = element;
                break;
            }
            if (size - counter >= 0) {
                array[i] = array[size - counter];
                counter++;
            }
        }
        size++;
    }

    @Override
    public T remove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        T element = (T) array[index];
        Object[] obj = new Object[size - 1];
        int counter = 0;

        for (int i = 0; i < size; i++) {
            if (i != index) {
                obj[counter] = array[i];
                counter++;
            }
        }

        array = obj;
        size--;
        return element;
    }

    @Override
    public int indexOf(Object o) {
        for (int i = 0; i < size; i++) {
            if (array[i].equals(o)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public int lastIndexOf(Object o) {
        for (int i = size - 1; i >= 0; i--) {
            if (array[i].equals(o)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public ListIterator<T> listIterator() {
        return new MyListIterator();
    }

    @Override
    public ListIterator<T> listIterator(int index) {
        return new MyListIterator(index);
    }

    @Override
    public List<T> subList(int fromIndex, int toIndex) {
        if (fromIndex < 0 || toIndex > size || fromIndex > toIndex) {
            throw new IndexOutOfBoundsException("From Index: " + fromIndex + ", To Index: " + toIndex + ", Size: " + size);
        }

        int newSize = toIndex - fromIndex;
        T[] obj = (T[]) new Object[newSize];

        int counter = 0;
        for (int i = fromIndex; i < toIndex; i++) {
            obj[counter] = (T) array[i];
            counter++;
        }

        return Arrays.stream(obj).toList();
    }

    @Override
    public String toString() {
        return Arrays.toString(array) +
                ", index=" + size;
    }

    @Override
    public String getAuthor() {
        return FirstName + " " + Name;
    }

    private class MyIterator implements Iterator<T> {
        private int index = 0;

        @Override
        public boolean hasNext() {
            return index < size;
        }

        @Override
        public T next() {
            T element = (T) array[index];
            index++;
            return element;
        }
    }

    private class MyListIterator implements ListIterator<T> {
        private int index = 0;
        private int lastIndex = size - 1;
        private boolean calledNext, calledPrevious, calledRemove, calledAdd = false;
        public MyListIterator() {}
        public MyListIterator(int index) {
            if (index < 0 || index >= size) {
                throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
            }

            this.index = index;
        }

        @Override
        public boolean hasNext() {
            return index < size;
        }

        @Override
        public T next() {
            calledNext = true;
            T element = (T) array[index];
            index++;
            return element;
        }

        @Override
        public boolean hasPrevious() {
            return lastIndex < size;
        }

        @Override
        public T previous() {
            calledPrevious = true;
            T element = (T) array[lastIndex];
            lastIndex--;
            return element;
        }

        @Override
        public int nextIndex() {
            int nextIndex = index + 1;
            return nextIndex == size - 1 ? size : nextIndex;
        }

        @Override
        public int previousIndex() {
            int previousIndex = lastIndex - 1;
            return previousIndex == 0 ? -1 : previousIndex;
        }

        @Override
        public void remove() {
            calledRemove = true;
            Real.this.remove(index);
        }

        @Override
        public void set(T t) {
            if ((!calledAdd || !calledRemove) && (calledPrevious || calledNext))
                Real.this.set(index, t);
        }

        @Override
        public void add(T t) {
            calledAdd = true;
            Real.this.add(index, t);
            index++;
        }
    }

}
