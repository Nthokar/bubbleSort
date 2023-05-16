import org.junit.jupiter.api.Test;

import java.util.Comparator;

import static org.junit.jupiter.api.Assertions.*;

class SorterTest {

    @Test
    public void bubbleSortTest(){
        var array = new String[]{"abcd", "aacbcd", "bcde", "d"};
        var sorter = new Sorter();


        //стандартная сортировка строк в убывании
        sorter.bubbleSort(array, String::compareTo);
        assertArrayEquals(new String[]
                {"d", "bcde", "abcd","aacbcd"}, array);


        //стандартная сортировка строк в возрастании
        sorter.bubbleSort(array, Comparator.reverseOrder());
        assertArrayEquals(new String[]
                {"aacbcd", "abcd", "bcde", "d"}, array);


        //сортировка строк по длине в убывании
        sorter.bubbleSort(array, Comparator.comparingInt(String::length));
        assertArrayEquals(new String[]
                {"aacbcd", "abcd", "bcde", "d"}, array);
    }


    @Test
    public void swapTest(){
        var array = new String[]{"firstElement", "secondElement"};
        var sorter = new Sorter();
        sorter.swap(array, 0, 1);
        assertEquals("firstElement", array[1]);
        assertEquals("secondElement", array[0]);
    }
}