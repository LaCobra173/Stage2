package ru.marat.javacore;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.util.*;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RealTest {
    Real<String> real;
    @BeforeEach
    void setUp() {
        real = new Real<>(new String[] {"Hello", "World", "City", "Third", "Obama", "Java"});
    }

    @Test
    void size() {
        int result = 6;
        assertEquals(result, real.size());
    }

    @Test
    void isEmpty() {
        boolean result = false;
        assertEquals(result, real.isEmpty());
    }

    @Test
    void contains() {
        assertTrue(real.contains("Java"));
        assertFalse(real.contains("JavaRush"));
    }

    @Test
    void iterator() {
        Object[] result = {"Hello", "World", "City", "Third", "Obama", "Java"};
        Iterator<String> iterator = real.iterator();
        while (iterator.hasNext()) {
            iterator.next();
        }

        assertArrayEquals(result, real.toArray());
    }

    @Test
    void toArray() {
        Object[] result = {"Hello", "World", "City", "Third", "Obama", "Java"};
        assertArrayEquals(result, real.toArray());
    }

    @ParameterizedTest
    @MethodSource()
    void testToArray(Object[] firstArray, Object[] secondArray) {
        assertArrayEquals(firstArray, real.toArray(secondArray));
    }

    static Stream<Arguments> testToArray() {
        return Stream.of(
                Arguments.of(Arrays.asList("Hello", "World", "City", "Third", "Obama", "Java").toArray(),
                        Arrays.asList("First", "Hey", "Git", "Idea").toArray()),
                Arguments.of(Arrays.asList("Hello", "World", "City", "Third", "Obama", "Java", null, null).toArray(),
                        Arrays.asList("Hello", "World", "City", "Third", "Obama", "Java", "Bowling", "Jumping").toArray())
        );
    }

    @Test
    void add() {
        boolean result = true;
        assertEquals(result, real.add("NewWord"));
    }

    @Test
    void remove() {
        assertTrue(real.remove("Third"));
        assertFalse(real.remove("Undefined"));
    }

    @Test
    void containsAll() {
        List<String> listTrue = new ArrayList<>();
        listTrue.add("City");
        listTrue.add("Third");
        List<String> listFalse = new ArrayList<>();
        listFalse.add("City");
        listFalse.add("False");

        assertTrue(real.containsAll(listTrue));
        assertFalse(real.containsAll(listFalse));
    }

    @Test
    void addAll() {
        List<String> listTrue = new ArrayList<>();
        List<String> listFalse = new ArrayList<>();
        listTrue.add("Hawaii");
        listTrue.add("Sochi");
        assertTrue(real.addAll(listTrue));
        assertFalse(real.addAll(listFalse));
    }

    @Test
    void testAddAll() {
        List<String> listTrue = new ArrayList<>();
        List<String> listFalse = new ArrayList<>();
        listTrue.add("Montana");
        listTrue.add("Gaz");
        assertTrue(real.addAll(3, listTrue));
        assertFalse(real.addAll(3, listFalse));

        Throwable thrown = assertThrows(IndexOutOfBoundsException.class, () -> {
           real.addAll(10, listTrue);
        });
        assertNotNull(thrown.getMessage());
    }

    @Test
    void removeAll() {
        List<String> listTrue = new ArrayList<>();
        List<String> listFalse = new ArrayList<>();
        listTrue.add("City");
        listFalse.add("Export");
        assertTrue(real.removeAll(listTrue));
        assertFalse(real.removeAll(listFalse));
    }

    @Test
    void retainAll() {
        List<String> listTrue = new ArrayList<>();
        List<String> listFalse = new ArrayList<>();
        listTrue.add("City");
        listTrue.add("Java");
        listTrue.add("Third");
        listFalse.add("Boolean");
        assertTrue(real.retainAll(listTrue));
        assertFalse(real.retainAll(listFalse));
    }

    @Test
    void clear() { // ?????
        real.clear();
        assertTrue(real.isEmpty());
    }

    @Test
    void get() {
        String result = "City";
        assertEquals(result, real.get(2));

        Throwable thrown = assertThrows(IndexOutOfBoundsException.class, () -> {
            real.get(10);
        });
        assertNotNull(thrown.getMessage());
    }

    @Test
    void set() {
        String result = "Java";
        assertEquals(result, real.set(5, "JavaCore"));

        Throwable thrown = assertThrows(IndexOutOfBoundsException.class, () -> {
            real.set(10, "Setting");
        });
        assertNotNull(thrown.getMessage());
    }

    @Test
    void testAdd() {
        Object[] result = Arrays.asList("Hello", "Biomes", "World", "City", "Third", "Obama", "Java").toArray();
        real.add(1, "Biomes");
        assertArrayEquals(result, real.toArray());

        Throwable thrown = assertThrows(IndexOutOfBoundsException.class, () -> {
            real.add(10, "Daewoo");
        });
        assertNotNull(thrown.getMessage());
    }

    @Test
    void testRemove() {
        String word = "World";
        assertEquals(word, real.remove(1));

        Throwable thrown = assertThrows(IndexOutOfBoundsException.class, () -> {
            real.remove(-1);
        });
        assertNotNull(thrown.getMessage());
    }

    @Test
    void indexOf() {
        int resultTrue = 4;
        assertEquals(resultTrue, real.indexOf("Obama"));

        int resultFalse = -1;
        assertEquals(resultFalse, real.indexOf("HP"));
    }

    @Test
    void lastIndexOf() {
        Real<String> list = new Real<>(new String[] {"Hello", "World", "City", "Third", "Obama", "Java", "City", "Rein"});
        int resultTrue = 6;
        assertEquals(resultTrue, list.lastIndexOf("City"));

        int resultFalse = -1;
        assertEquals(resultFalse, list.lastIndexOf("HP"));
    }

    @Test
    void listIterator() {

        Object[] result = {"Hello", "World", "Water", "City", "Third", "Obama", "Butterfly"};
        ListIterator<String> listIterator = real.listIterator();
        while(listIterator.hasNext()) {
            if (Objects.equals(listIterator.next(), "Obama"))
                listIterator.set("Butterfly");
            if (listIterator.nextIndex() == 3)
                listIterator.add("Water");
        }

        assertArrayEquals(result, real.toArray());
    }

    @Test
    void testListIterator() {
        Object[] result = {"Hello", "World", "City", "Obama", "Java"};
        ListIterator<String> listIterator = real.listIterator(5);
        while(listIterator.hasPrevious()) {
            if (listIterator.previousIndex() == 2)
                listIterator.remove();
            listIterator.previous();
        }

        assertArrayEquals(result, real.toArray());

        Throwable thrown = assertThrows(IndexOutOfBoundsException.class, () -> {
            real.listIterator(100);
        });
        assertNotNull(thrown.getMessage());
    }

    @Test
    void subList() {
        List<String> result = new ArrayList<>();
        result.add("World");
        result.add("City");
        result.add("Third");
        assertEquals(result, real.subList(1, 4));

        Throwable thrown = assertThrows(IndexOutOfBoundsException.class, () -> {
            real.subList(10, 6);
        });
        assertNotNull(thrown.getMessage());
    }

    @Test
    void testToString() {
        String result = "[Hello, World, City, Third, Obama, Java]";
        assertEquals(result, real.toString());
    }

    @Test
    void getAuthor() {
        String result = "Yakupov Marat";
        assertEquals(result, real.getAuthor());
    }
}