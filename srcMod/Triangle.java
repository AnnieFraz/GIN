import java.util.Arrays;

public class IterativeBubbleSort {

    static void bubbleSort(int array[], int n){
        if (n==1){
            return;
        }

        for (int i=0; i < n; i++){
            for (int j =1; j < (n-i); j++){
            if (array[j-1] > array[j]) {
                int temp = array[j-1];
                array[j-1] = array[j];
                array[j] = temp;
            }

            }

        }
        System.out.println("Iterative - Sorted array: " + Arrays.toString(array));
        System.out.println("time:" + System.currentTimeMillis());

    }

    public static void main(String[] args) {
//this is a comment
        int array[] = {7, 69, 76, 102, 94, 53, 62 , 101};

        bubbleSort(array, array.length);

      }
}