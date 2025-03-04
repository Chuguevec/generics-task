package org.example;

import java.util.Arrays;
import java.util.Iterator;
import java.util.function.Function;
/*
 *Внутренийк ласс
 * Объект внутреннего класса может быть создан только внутри внешнего класса.
 * У него есть доступ ко всем полям объекта.
 *
 * Внутренний статический класс
 * Объект класса может быть создан без объекта внешнего класса. MyList.MyIterator()
 * У него есть доступ только к статическим полям внешнего класса.
 */

public class MyList<T extends Number> implements Iterable<T> {
    private Number[] array;
    private int size;

    public MyList() {
        array = new Number[16];
        size = 0;
    }

    public void add(T o) {
        array[size] = o;
        size++;
        if (size == array.length) {
            resize();
        }
    }

    public T get(int index) {
        if (index >= size || size == 0) {
            throw new IndexOutOfBoundsException();
        }
        return (T) array[index];
    }

    private void resize() {
        Number[] newArray = new Number[array.length * 2];
        System.arraycopy(array, 0, newArray, 0, size);
        array = newArray;
    }

    public T remove(int index) {
        T value = get(index);
        if (index == 0) {
            Number[] newArray = new Number[array.length];
            System.arraycopy(array, 1, newArray, 0, size);
            array = newArray;
        } else if (index != size) {
            Number[] newArray = new Number[array.length];
            System.arraycopy(array, 0, newArray, 0, index);
            System.arraycopy(array, index + 1, newArray, index, size - index);
            array = newArray;
        }
        size--;
        return value;
    }

    public <R extends Number> MyList<R> map(Function<T, R> f) {
        MyList<R> list = new MyList<>();
        for (int i = 0; i < size; i++) {
            R value = (R) f.apply((T) array[i]);
            list.add(value);
        }
        return list;
    }

    public int size() {
        return size;
    }

    @Override
    public Iterator<T> iterator() {
        return new MyIterator();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MyList<?> myList = (MyList<?>) o;
        if (size != myList.size) {
            return false;
        }
        boolean isEquals = true;
        for (int i = 0; i < size; i++) {
            if (!this.get(i).equals(myList.get(i))) {
                isEquals = false;
                break;
            }
        }
        return isEquals;
    }

    @Override
    public int hashCode() {
        int result = 1;
        for (T t : this) {
            result += t.hashCode();
        }
        result = 31 * result + size;
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        for (int i = 0; i < size - 1; i++) {
            sb.append(array[i]).append(", ");
        }
        sb.append(array[size - 1]);
        sb.append('}');
        return sb.toString();
    }

    public class MyIterator implements Iterator<T> {
        private int index = 0;

        @Override
        public boolean hasNext() {
            return index < size;
        }

        @Override
        public T next() {
            return (T) array[index++];
        }
    }
}

