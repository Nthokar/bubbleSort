import java.util.Comparator;

public class Sorter {
    Integer operations = 0;
    public void bubbleSort(String[] a, Comparator<String> comparator) {
        for (int i = a.length - 1; i > 0; i--){
            operations+=2;
            for (int j = 0; j < i; j++){
                operations+=4;
                if ( comparator.compare(a[i], a[j]) > 0 )
                    swap(a, i, j);
            }
        }
    }
    public void swap(String[] a, int index1, int index2){
        operations+=3;
        var temp = a[index1];
        a[index1] = a[index2];
        a[index2] = temp;
    }
}
