package vandy.mooc.functional;

import io.magnum.autograder.junit.Rubric;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.function.UnaryOperator;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@SuppressWarnings("unchecked")
public class ArrayTests {
    @Rule
    public Timeout timeout = new Timeout(3, TimeUnit.SECONDS);

    /**
     * Method under test: {@link Array.ArrayIterator#hasNext()}
     */
    @Test
    @Rubric(
        value = "testArrayIteratorHasNext",
        goal = "ArrayIterator.hasNext() returns false when there are no elements in the array.",
        points = 1.0,
        reference = "ArrayTests.java"
    )
    public void testArrayIteratorHasNext() {
        assertFalse(((new Array<String>()).new ArrayIterator()).hasNext());
    }

    /**
     * Method under test: {@link Array.ArrayIterator#hasNext()}
     */
    @Test
    @Rubric(
        value = "testArrayIteratorHasNext2",
        goal = "ArrayIterator.hasNext() throws a NullPointerException when the array is null.",
        points = 1.0,
        reference = "ArrayTests.java"
    )
    public void testArrayIteratorHasNext2() {
        Array<String> array = null;
        assertThrows(
            NullPointerException.class,
            () -> (array.new ArrayIterator()).hasNext());
    }

    /**
     * Method under test: {@link Array.ArrayIterator#hasNext()}
     */
    @Test
    @Rubric(
        value = "testArrayIteratorHasNext3",
        goal = "ArrayIterator.hasNext() returns true when there is at least one element in " +
            "the array.",
        points = 1.0,
        reference = "ArrayTests.java"
    )
    public void testArrayIteratorHasNext3() {
        Array array = new Array<String>();
        array.add("Element");
        assertTrue((array.new ArrayIterator()).hasNext());
    }

    /**
     * Method under test: {@link Array.ArrayIterator#next()}
     */
    @Test
    @Rubric(
        value = "testArrayIteratorNext",
        goal = "ArrayIterator.next() throws a NoSuchElementException when there are no " +
            "elements in the array.",
        points = 1.0,
        reference = "ArrayTests.java"
    )
    public void testArrayIteratorNext() {
        assertThrows(
            NoSuchElementException.class,
            () -> ((new Array<String>()).new ArrayIterator()).next());
    }

    /**
     * Method under test: {@link Array.ArrayIterator#next()}
     */
    @Test
    @Rubric(
        value = "testArrayIteratorNext2",
        goal = "ArrayIterator.next() throws a NoSuchElementException when there is at least " +
            "one element in the array.",
        points = 1.0,
        reference = "ArrayTests.java"
    )
    public void testArrayIteratorNext2() {
        Array array = new Array<String>();
        assertThrows(NoSuchElementException.class, () -> (array.new ArrayIterator()).next());
    }

    /**
     * Method under test: {@link Array.ArrayIterator#remove()}
     */
    @Test
    @Rubric(
        value = "testArrayIteratorRemove",
        goal = "ArrayIterator.remove() throws an IllegalStateException when remove() is from " +
            "an empty ArrayIterator.",
        points = 1.0,
        reference = "ArrayTests.java"
    )
    public void testArrayIteratorRemove() {
        assertThrows(
            IllegalStateException.class,
            () -> ((new Array<String>()).new ArrayIterator()).remove());
    }

    /**
     * Method under test: {@link Array#isEmpty()}
     */
    @Test
    @Rubric(
        value = "testIsEmpty",
        goal = "Array.isEmpty() returns true when the array is empty.",
        points = 1.0,
        reference = "ArrayTests.java"
    )
    public void testIsEmpty() {
        assertTrue((new Array<>()).isEmpty());
    }

    /**
     * Method under test: {@link Array#isEmpty()}
     */
    @Test
    @Rubric(
        value = "testIsEmpty2",
        goal = "Check if the array is empty after adding an element.",
        points = 1.0,
        reference = "ArrayTests.java"
    )
    public void testIsEmpty2() {
        Array<Object> array = new Array<>();
        array.add("Element");
        assertFalse(array.isEmpty());
    }

    /**
     * Method under test: {@link Array#indexOf(Object)}
     */
    @Test
    @Rubric(
        value = "testIndexOf",
        goal = "Get the index of an element in the array.",
        points = 1.0,
        reference = "ArrayTests.java"
    )
    public void testIndexOf() {
        assertEquals(-1, (new Array<>()).indexOf("42"));
        assertEquals(-1, (new Array<>()).indexOf(null));
    }

    /**
     * Method under test: {@link Array#indexOf(Object)}
     */
    @Test
    @Rubric(
        value = "testIndexOf2",
        goal = "Get the index of an element in the array.",
        points = 1.0,
        reference = "ArrayTests.java"
    )
    public void testIndexOf2() {
        Array<Object> array = new Array<>();
        array.add("Element");
        assertEquals(-1, array.indexOf("42"));
    }

    /**
     * Method under test: {@link Array#indexOf(Object)}
     */
    @Test
    @Rubric(
        value = "testIndexOf3",
        goal = "Get the index of an element in the array.",
        points = 1.0,
        reference = "ArrayTests.java"
    )
    public void testIndexOf3() {
        Array<Object> array = new Array<>();
        array.add("42");
        assertEquals(0, array.indexOf("42"));
    }

    /**
     * Method under test: {@link Array#indexOf(Object)}
     */
    @Test
    @Rubric(
        value = "testIndexOf4",
        goal = "Get the index of an element in the array.",
        points = 1.0,
        reference = "ArrayTests.java"
    )
    public void testIndexOf4() {
        Array<Object> array = new Array<>();
        array.add("Element");
        assertEquals(-1, array.indexOf(null));
    }

    /**
     * Method under test: {@link Array#indexOf(Object)}
     */
    @Test
    @Rubric(
        value = "testIndexOf5",
        goal = "Get the index of an element in the array.",
        points = 1.0,
        reference = "ArrayTests.java"
    )
    public void testIndexOf5() {
        Array<Object> array = new Array<>();
        array.add(null);
        assertEquals(0, array.indexOf(null));
    }

    /**
     * Method under test: {@link Array#addAll(Collection)}
     */
    @Test
    @Rubric(
        value = "testAddAll",
        goal = "Add all elements from a collection to the array.",
        points = 1.0,
        reference = "ArrayTests.java"
    )
    public void testAddAll() {
        Array<Object> array = new Array<>();
        assertFalse(array.addAll(new ArrayList<>()));
        assertTrue(array.isEmpty());
        assertEquals(10, array.mElementData.length);
    }

    /***************************************************/

    /**
     * Method under test: {@link Array#addAll(Collection)}
     */
    @Test
    @Rubric(
        value = "testAddAll2",
        goal = "Add all elements from an empty collection to the array.",
        points = 1.0,
        reference = "ArrayTests.java"
    )
    public void testAddAll2() {
        Array<Object> array = new Array<>(10);
        assertFalse(array.addAll(new ArrayList<>()));
        assertTrue(array.isEmpty());
    }

    /**
     * Method under test: {@link Array#addAll(Collection)}
     */
    @Test
    @Rubric(
        value = "testAddAll3",
        goal = "Add all elements from a collection to the array.",
        points = 1.0,
        reference = "ArrayTests.java"
    )
    public void testAddAll3() {
        Array<Object> array = new Array<>();

        ArrayList<Object> c = new ArrayList<>();
        c.add("42");
        assertTrue(array.addAll(c));
        assertFalse(array.isEmpty());
        assertEquals(10, array.mElementData.length);
    }

    /**
     * Method under test: {@link Array#addAll(Array)}
     */
    @Test
    @Rubric(
        value = "testAddAll4",
        goal = "Add all elements from an empty array to the array.",
        points = 1.0,
        reference = "ArrayTests.java"
    )
    public void testAddAll4() {
        Array<Object> array = new Array<>();
        assertFalse(array.addAll(new Array<>()));
        assertTrue(array.isEmpty());
        assertEquals(10, array.mElementData.length);
    }

    /**
     * Method under test: {@link Array#addAll(Array)}
     */
    @Test
    @Rubric(
        value = "testAddAll5",
        goal = "Add all elements from an empty array to the array with a specified capacity.",
        points = 1.0,
        reference = "ArrayTests.java"
    )
    public void testAddAll5() {
        Array<Object> array = new Array<>(10);
        assertFalse(array.addAll(new Array<>()));
        assertTrue(array.isEmpty());
    }

    /**
     * Method under test: {@link Array#addAll(Array)}
     */
    @Test
    @Rubric(
        value = "testAddAll6",
        goal = "Check if adding a null array throws a NullPointerException.",
        points = 1.0,
        reference = "ArrayTests.java"
    )
    public void testAddAll6() {
        assertThrows(
            NullPointerException.class,
            () -> (new Array<>()).addAll((Array<Object>) null));
    }

    /**
     * Method under test: {@link Array#addAll(Array)}
     */
    @Test
    @Rubric(
        value = "testAddAll7",
        goal = "Add all elements from an array to the array.",
        points = 1.0,
        reference = "ArrayTests.java"
    )
    public void testAddAll7() {
        Array<Object> array = new Array<>();

        Array<Object> array2 = new Array<>();
        array2.add("Element");
        assertTrue(array.addAll(array2));
        assertFalse(array.isEmpty());
        assertEquals(10, array.mElementData.length);
    }

    /**
     * Method under test: {@link Array#remove(int)}
     */
    @Test
    @Rubric(
        value = "testRemove",
        goal = "Check if removing an element at an invalid index throws an " +
            "IndexOutOfBoundsException.",
        points = 1.0,
        reference = "ArrayTests.java"
    )
    public void testRemove() {
        assertThrows(
            IndexOutOfBoundsException.class,
            () -> (new Array<>()).remove(1));
    }

    /**
     * Method under test: {@link Array#remove(int)}
     */
    @Test
    @Rubric(
        value = "testRemove2",
        goal = "Check if removing an element at a negative index throws an " +
            "IndexOutOfBoundsException.",
        points = 1.0,
        reference = "ArrayTests.java"
    )
    public void testRemove2() {
        assertThrows(
            IndexOutOfBoundsException.class,
            () -> (new Array<>()).remove(-1));
    }

    /**
     * Method under test: {@link Array#remove(int)}
     */
    @Test
    @Rubric(
        value = "testRemove3",
        goal = "Remove an element at a valid index and check if the array is not empty.",
        points = 1.0,
        reference = "ArrayTests.java"
    )
    public void testRemove3() {
        Array<Object> array = new Array<>();
        array.add("Element");
        array.add("Element");
        assertEquals("Element", array.remove(1));
        assertFalse(array.isEmpty());
    }

    /**
     * Method under test: {@link Array#remove(int)}
     */
    @Test
    @Rubric(
        value = "testRemove4",
        goal = "Remove an element at a valid index and check if the array is not empty.",
        points = 1.0,
        reference = "ArrayTests.java"
    )
    public void testRemove4() {
        Array<Object> array = new Array<>();
        array.add("Element");
        array.add("Element");
        array.add("Element");
        assertEquals("Element", array.remove(1));
        assertFalse(array.isEmpty());
    }

    /**
     * Method under test: {@link Array#rangeCheck(int)}
     */
    @Test
    @Rubric(
        value = "testRangeCheck",
        goal = "Check if rangeCheck throws an IndexOutOfBoundsException for an invalid index.",
        points = 1.0,
        reference = "ArrayTests.java"
    )
    public void testRangeCheck() {
        assertThrows(
            IndexOutOfBoundsException.class,
            () -> (new Array<>()).rangeCheck(1));
    }

    /**
     * Method under test: {@link Array#rangeCheck(int)}
     */
    @Test
    @Rubric(
        value = "testRangeCheck2",
        goal = "Check if rangeCheck throws an IndexOutOfBoundsException for a negative index.",
        points = 1.0,
        reference = "ArrayTests.java"
    )
    public void testRangeCheck2() {
        assertThrows(
            IndexOutOfBoundsException.class,
            () -> new Array<>().rangeCheck(-1));
    }

    /**
     * Method under test: {@link Array#rangeCheck(int)}
     */
    @Test
    @Rubric(
        value = "testRangeCheck3",
        goal = "Check if rangeCheck does not throw an exception for a valid index.",
        points = 1.0,
        reference = "ArrayTests.java"
    )
    public void testRangeCheck3() {
        Array<Object> array = new Array<>();
        array.add("Element");
        array.add("Element");
        array.rangeCheck(1);
        assertFalse(array.isEmpty());
        assertEquals(10, array.mElementData.length);
    }

    /**
     * Method under test: {@link Array#get(int)}
     */
    @Test
    @Rubric(
        value = "testGet",
        goal = "Check if get throws an IndexOutOfBoundsException for an invalid index.",
        points = 1.0,
        reference = "ArrayTests.java"
    )
    public void testGet() {
        assertThrows(
            IndexOutOfBoundsException.class,
            () -> new Array<>().get(1));
    }

    /**
     * Method under test: {@link Array#get(int)}
     */
    @Test
    @Rubric(
        value = "testGet2",
        goal = "Check if get throws an IndexOutOfBoundsException for a negative index.",
        points = 1.0,
        reference = "ArrayTests.java"
    )
    public void testGet2() {
        assertThrows(
            IndexOutOfBoundsException.class,
            () -> new Array<>().get(-1));
    }

    /**
     * Method under test: {@link Array#get(int)}
     */
    @Test
    @Rubric(
        value = "testGet3",
        goal = "Get an element at a valid index and check if it matches the expected value.",
        points = 1.0,
        reference = "ArrayTests.java"
    )
    public void testGet3() {
        Array<Object> array = new Array<>();
        array.add("Element");
        array.add("Element");
        assertEquals("Element", array.get(1));
    }

    /**
     * Method under test: {@link Array#set(int, Object)}
     */
    @Test
    @Rubric(
        value = "testSet",
        goal = "Check if set throws an IndexOutOfBoundsException for an invalid index.",
        points = 1.0,
        reference = "ArrayTests.java"
    )
    public void testSet() {
        assertThrows(
            IndexOutOfBoundsException.class,
            () -> new Array<>().set(1, "Element"));
    }

    /**
     * Method under test: {@link Array#set(int, Object)}
     */
    @Test
    @Rubric(
        value = "testSet2",
        goal = "Check if set throws an IndexOutOfBoundsException for a negative index.",
        points = 1.0,
        reference = "ArrayTests.java"
    )
    public void testSet2() {
        assertThrows(
            IndexOutOfBoundsException.class,
            () -> new Array<>().set(-1, "Element"));
    }

    /**
     * Method under test: {@link Array#set(int, Object)}
     */
    @Test
    @Rubric(
        value = "testSet3",
        goal = "Set an element at a valid index and check if it returns the previous element.",
        points = 1.0,
        reference = "ArrayTests.java"
    )
    public void testSet3() {
        Array<Object> array = new Array<>();
        array.add("Element");
        array.add("Element");
        assertEquals("Element", array.set(1, "Element"));
    }

    /**
     * Method under test: {@link Array#add(Object)}
     */
    @Test
    @Rubric(
        value = "testAdd",
        goal = "Add an element to the array and check if it is not empty.",
        points = 1.0,
        reference = "ArrayTests.java"
    )
    public void testAdd() {
        Array<Object> array = new Array<>();
        assertTrue(array.add("Element"));
        assertFalse(array.isEmpty());
        assertEquals(10, array.mElementData.length);
    }

    /**
     * Method under test: {@link Array#add(Object)}
     */
    @Test
    @Rubric(
        value = "testAdd2",
        goal = "Add an element to the array with a specific capacity and check if it is not empty.",
        points = 1.0,
        reference = "ArrayTests.java"
    )
    public void testAdd2() {
        Array<Object> array = new Array<>(1);
        assertTrue(array.add("Element"));
        assertFalse(array.isEmpty());
    }

    /**
     * Method under test: {@link Array#add(Object)}
     */
    @Test
    @Rubric(
        value = "testAdd3",
        goal = "Add elements to the array with a specific capacity and check if it is not empty " +
            "and the capacity increases.",
        points = 1.0,
        reference = "ArrayTests.java"
    )
    public void testAdd3() {
        Array<Object> array = new Array<>(1);
        array.add("Element");
        array.add("Element");
        assertTrue(array.add("Element"));
        assertFalse(array.isEmpty());
        assertEquals(3, array.mElementData.length);
    }

    /**
     * Method under test: {@link Array#ensureCapacityInternal(int)}
     */
    @Test
    @Rubric(
        value = "testEnsureCapacityInternal",
        goal = "Check if ensureCapacityInternal sets the capacity to at least the specified " +
            "minimum capacity.",
        points = 1.0,
        reference = "ArrayTests.java"
    )
    public void testEnsureCapacityInternal() {
        Array<Object> array = new Array<>();
        array.ensureCapacityInternal(1);
        assertEquals(10, array.mElementData.length);
    }

    /**
     * Method under test: {@link Array#ensureCapacityInternal(int)}
     */
    @Test
    @Rubric(
        value = "testEnsureCapacityInternal2",
        goal = "Check if ensureCapacityInternal does not change the capacity when it is already " +
            "larger than the specified minimum capacity.",
        points = 1.0,
        reference = "ArrayTests.java"
    )
    public void testEnsureCapacityInternal2() {
        Array<Object> array = new Array<>(10);
        array.ensureCapacityInternal(1);
        assertTrue(array.isEmpty());
        assertEquals(10, array.mElementData.length);
    }

    /**
     * Method under test: {@link Array#uncheckedToArray()}
     */
    @Test
    @Rubric(
        value = "testUncheckedToArray",
        goal = "Check if uncheckedToArray returns the internal array and its length.",
        points = 1.0,
        reference = "ArrayTests.java"
    )
    public void testUncheckedToArray() {
        Array<Object> array = new Array<>();
        Object[] actualUncheckedToArrayResult = array.uncheckedToArray();
        assertSame(array.mElementData, actualUncheckedToArrayResult);
        assertEquals(0, actualUncheckedToArrayResult.length);
    }

    /**
     * Method under test: {@link Array#toArray()}
     */
    @Test
    @Rubric(
        value = "testToArray",
        goal = "Check if toArray returns an empty array.",
        points = 1.0,
        reference = "ArrayTests.java"
    )
    public void testToArray() {
        assertEquals(0, (new Array<>()).toArray().length);
    }

    /**
     * Method under test: {@link Array#toArray(Object[])}
     */
    @Test
    @Rubric(
        value = "testToArray2",
        goal = "Check if toArray returns the same array when passed as an argument and the length" +
            " matches the number of elements in the array.",
        points = 1.0,
        reference = "ArrayTests.java"
    )
    public void testToArray2() {
        Object[] array = new Object[]{"Array"};
        Object[] actualToArrayResult = (new Array<>()).toArray(array);
        assertSame(array, actualToArrayResult);
        assertEquals(1, actualToArrayResult.length);
    }

    /**
     * Method under test: {@link Array#toArray(Object[])}
     */
    @Test
    @Rubric(
        value = "testToArray3",
        goal = "Check if toArray returns the same array when passed as an argument, the length " +
            "matches the number of elements in the array, and the elements are copied to the " +
            "specified array.",
        points = 1.0,
        reference = "ArrayTests.java"
    )
    public void testToArray3() {
        Array<Object> array = new Array<>();
        array.add("Element");
        Object[] array2 = new Object[]{"Array"};
        Object[] actualToArrayResult = array.toArray(array2);
        assertSame(array2, actualToArrayResult);
        assertEquals(1, actualToArrayResult.length);
    }

    /**
     * Method under test: {@link Array#toArray(Object[])}
     */
    @Test
    @Rubric(
        value = "testToArray4",
        goal = "Check if toArray returns a new array with the elements from the array and the " +
            "specified array length matches the number of elements in the array.",
        points = 1.0,
        reference = "ArrayTests.java"
    )
    public void testToArray4() {
        Array<Object> array = new Array<>();
        array.add("Element");
        array.add("Element");
        assertEquals(2, array.toArray(new Object[]{"Array"}).length);
    }

    /**
     * Method under test: {@link Array#iterator()}
     */
    @Test
    @Rubric(
        value = "testIterator",
        goal = "Check if the iterator returned by iterator() is an instance of ArrayIterator.",
        points = 1.0,
        reference = "ArrayTests.java"
    )
    public void testIterator() {
        assertTrue((new Array<>()).iterator() instanceof Array.ArrayIterator);
    }

    /**
     * Method under test: {@link Array#replaceAll(UnaryOperator)}
     */
    @Test
    @Rubric(
        value = "testReplaceAll",
        goal = "Check if replaceAll does not modify an empty array.",
        points = 1.0,
        reference = "ArrayTests.java"
    )
    public void testReplaceAll() {
        Array<Object> array = new Array<>();
        array.replaceAll(mock(UnaryOperator.class));
        assertTrue(array.isEmpty());
        assertEquals(0, array.mElementData.length);
    }

    /**
     * Method under test: {@link Array#replaceAll(UnaryOperator)}
     */
    @Test
    @Rubric(
        value = "testReplaceAll2",
        goal = "Check if replaceAll applies the specified operator to each element in the array.",
        points = 1.0,
        reference = "ArrayTests.java"
    )
    public void testReplaceAll2() {
        Array<Object> array = new Array<>();
        array.add("Element");
        UnaryOperator<Object> operator = mock(UnaryOperator.class);
        when(operator.apply(Mockito.any())).thenReturn("Apply");
        array.replaceAll(operator);
        verify(operator).apply(Mockito.any());
    }

    /**
     * Method under test: {@link Array#replaceAll(UnaryOperator)}
     */
    @Test
    @Rubric(
        value = "testReplaceAll3",
        goal = "Check if replaceAll throws an IllegalArgumentException and applies the specified " +
            "operator to each element in the array.",
        points = 1.0,
        reference = "ArrayTests.java"
    )
    public void testReplaceAll3() {
        Array<Object> array = new Array<>();
        array.add("Element");
        UnaryOperator<Object> operator = mock(UnaryOperator.class);
        when(operator.apply(Mockito.any())).thenThrow(new IllegalArgumentException(
            "foo"));
        assertThrows(
            IllegalArgumentException.class,
            () -> array.replaceAll(operator));
        verify(operator).apply(Mockito.any());
    }

    /**
     * Method under test: {@link Array#forEach(Consumer)}
     */
    @Test
    @Rubric(
        value = "testForEach",
        goal = "Check if forEach applies the specified action to each element in the array.",
        points = 1.0,
        reference = "ArrayTests.java"
    )
    public void testForEach() {
        List<Integer> list = List.of(1, 2, 3);
        List<Integer> list2 = new ArrayList<>();
        Array<Integer> array = new Array<>();
        list.forEach(array::add);
        array.forEach(list2::add);
        assertEquals(list, list2);
    }

    /**
     * Method under test: {@link Array#forEach(Consumer)}
     */
    @Test
    @Rubric(
        value = "testForEach2",
        goal = "Check if forEach applies the specified action to each element in the array when " +
            "the action accepts the elements.",
        points = 1.0,
        reference = "ArrayTests.java"
    )
    public void testForEach2() {
        Array<Object> array = new Array<>();
        array.add("Element");
        Consumer<Object> action = mock(Consumer.class);
        doNothing().when(action).accept(Mockito.any());
        array.forEach(action);
        verify(action).accept(Mockito.any());
    }

    /**
     * Method under test: {@link Array#forEach(Consumer)}
     */
    @Test
    @Rubric(
        value = "testForEach3",
        goal = "Check if forEach throws an IllegalArgumentException when the action throws an " +
            "IllegalArgumentException.",
        points = 1.0,
        reference = "ArrayTests.java"
    )
    public void testForEach3() {
        Array<Object> array = new Array<>();
        array.add("Element");
        Consumer<Object> action = mock(Consumer.class);
        doThrow(new IllegalArgumentException("foo"))
            .when(action)
            .accept(Mockito.any());
        assertThrows(
            IllegalArgumentException.class,
            () -> array.forEach(action));
        verify(action).accept(Mockito.any());
    }

    /**
     * Methods under test:
     *
     * <ul>
     *   <li>{@link Array#Array()}
     *   <li>{@link Array#size()}
     * </ul>
     */
    @Test
    @Rubric(
        value = "testConstructor",
        goal = "Check if the default constructor creates an empty array and the size is zero.",
        points = 1.0,
        reference = "ArrayTests.java"
    )
    public void testConstructor() {
        assertEquals(0, (new Array<>()).size());
        assertEquals(0, (new Array<>()).mElementData.length);
        assertEquals(1, (new Array<>(1)).mElementData.length);
        assertThrows(IllegalArgumentException.class, () -> new Array<>(-1));
    }

    /**
     * Method under test: {@link Array#Array(Collection)}
     */
    @Test
    @Rubric(
        value = "testConstructor2",
        goal = "Check if the constructor initializes the array with the elements from the " +
            "collection.",
        points = 1.0,
        reference = "ArrayTests.java"
    )
    public void testConstructor2() {
        Array<Object> actualArray = new Array<>(new ArrayList<>());
        assertTrue(actualArray.isEmpty());
        assertEquals(0, actualArray.mElementData.length);
    }

    /**
     * Method under test: {@link Array#Array(Collection)}
     */
    @Test
    @Rubric(
        value = "testConstructor3",
        goal = "Check if the constructor throws a NullPointerException when passed a null " +
            "collection.",
        points = 1.0,
        reference = "ArrayTests.java"
    )
    public void testConstructor3() {
        assertThrows(NullPointerException.class, () -> new Array<>(null));
    }

    @Test
    @Rubric(
        value = "testConstructor3",
        goal = "Check if the array is properly converted to a list.",
        points = 1.0,
        reference = "ArrayTests.java"
    )
    public void testAsList() {
        Array<Object> array = new Array<>();
        for (int i = 100; i > 0; i--) {
            array.add(i);
        }
        List<Object> list = array.asList();
        assertEquals(array.size(), list.size());
        for (int i = 0; i < 100; i++) {
            assertEquals(array.get(i), list.get(i));
        }
    }
}

