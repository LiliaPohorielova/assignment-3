package vandy.mooc.functional;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.UnaryOperator;

@SuppressWarnings({"unchecked", "JavadocBlankLines", "JavadocDeclaration"})
public class Array<E> implements Iterable<E> {

    static final int DEFAULT_CAPACITY = 10;
    static final Object[] EMPTY_ELEMENTDATA = {};

    Object[] mElementData;
    int mSize;

    public Array() {
        mElementData = EMPTY_ELEMENTDATA;
    }

    public Array(int initialCapacity) {
        if (initialCapacity < 0)
            throw new IllegalArgumentException("Illegal Capacity: " + initialCapacity);
        mElementData = new Object[initialCapacity];
    }

    public Array(Collection<? extends E> c) {
        mElementData = c.toArray();
        if ((mSize = mElementData.length) != 0) {
            if (mElementData.getClass() != Object[].class)
                mElementData = Arrays.copyOf(mElementData, mSize, Object[].class);
        } else {
            mElementData = EMPTY_ELEMENTDATA;
        }
    }

    public boolean isEmpty() {
        return mSize == 0;
    }

    public int size() {
        return mSize;
    }

    public int indexOf(Object o) {
        if (o == null) {
            for (int i = 0; i < mSize; i++)
                if (mElementData[i] == null)
                    return i;
        } else {
            for (int i = 0; i < mSize; i++)
                if (o.equals(mElementData[i]))
                    return i;
        }
        return -1;
    }

    public boolean addAll(Array<E> array) {
        int numNew = array.size();
        ensureCapacityInternal(mSize + numNew);
        System.arraycopy(array.mElementData, 0, mElementData, mSize, numNew);
        mSize += numNew;
        return numNew != 0;
    }

    public boolean addAll(Collection<? extends E> c) {
        int numNew = c.size();
        ensureCapacityInternal(mSize + numNew);
        Object[] a = c.toArray();
        System.arraycopy(a, 0, mElementData, mSize, numNew);
        mSize += numNew;
        return numNew != 0;
    }

    public boolean add(E element) {
        ensureCapacityInternal(mSize + 1);
        mElementData[mSize++] = element;
        return true;
    }

    public E remove(int index) {
        rangeCheck(index);

        E oldValue = elementData(index);

        int numMoved = mSize - index - 1;
        if (numMoved > 0)
            System.arraycopy(mElementData, index + 1, mElementData, index, numMoved);
        mElementData[--mSize] = null;

        return oldValue;
    }

    public void rangeCheck(int index) {
        if (index >= mSize || index < 0)
            throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
    }

    private String outOfBoundsMsg(int index) {
        return "Index: " + index + ", Size: " + mSize;
    }

    @SuppressWarnings("unchecked")
    E elementData(int index) {
        return (E) mElementData[index];
    }

    public E get(int index) {
        rangeCheck(index);
        return elementData(index);
    }

    public E set(int index, E element) {
        rangeCheck(index);
        E oldValue = elementData(index);
        mElementData[index] = element;
        return oldValue;
    }

    protected void ensureCapacityInternal(int minCapacity) {
        grow(minCapacity);
    }

    private void grow(int minCapacity) {
        int newCapacity;
        if (mElementData == EMPTY_ELEMENTDATA) {
            newCapacity = DEFAULT_CAPACITY;
        } else if (minCapacity >= mElementData.length) {
            newCapacity = minCapacity;
        } else {
            newCapacity = Math.max(DEFAULT_CAPACITY, minCapacity);
        }
        mElementData = Arrays.copyOf(mElementData, newCapacity);
    }

    public Iterator<E> iterator() {
        return new ArrayIterator();
    }

    public class ArrayIterator implements Iterator<E> {
        int cursor;
        int lastRet = -1;

        @Override
        public boolean hasNext() {
            return cursor != mSize;
        }

        @Override
        public E next() {
            int i = cursor;
            if (i >= mSize)
                throw new NoSuchElementException();
            Object[] elementData = Array.this.mElementData;
            cursor = i + 1;
            return (E) elementData[lastRet = i];
        }

        @Override
        public void remove() {
            if (lastRet < 0)
                throw new IllegalStateException();
            try {
                Array.this.remove(lastRet);
                cursor = lastRet;
                lastRet = -1;
            } catch (IndexOutOfBoundsException ex) {
                throw new ConcurrentModificationException();
            }
        }
    }

    public void replaceAll(UnaryOperator<E> operator) {
        Objects.requireNonNull(operator);
        for (int i = 0; i < mSize; i++) {
            mElementData[i] = operator.apply(elementData(i));
        }
    }

    public void forEach(Consumer<? super E> action) {
        for (int i = 0; i < mSize; i++) {
            action.accept(elementData(i));
        }
    }

    public List<E> asList() {
        List<E> list = new ArrayList<>(mSize);
        for (int i = 0; i < mSize; i++) {
            list.add(elementData(i));
        }
        return list;
    }

    public Object[] uncheckedToArray() {
        return mElementData;
    }

    public Object[] toArray() {
        return Arrays.copyOf(mElementData, mSize);
    }

    @SuppressWarnings("unchecked")
    public <T> T[] toArray(T[] array) {
        if (array.length < mSize) {
            return (T[]) Arrays.copyOf(mElementData,
                    mSize,
                    array.getClass());
        } else {
            System.arraycopy(mElementData, 0,
                    array, 0,
                    mSize);
            if (array.length > mSize) {
                array[mSize] = null;
            }
            return array;
        }
    }
}
