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
        //int array[] = {7, 69, 76, 102, 94, 53, 62 , 101};
        int array[] = {102418, 391041, 979493, 765640, 899906, 832710, 806520, 699731, 63768, 68175, 521103, 122171, 776709, 890837, 644347, 974933, 34, 474389, 963630, 709592, 576313, 623896, 509591, 510928, 213};

        bubbleSort(array, array.length);

      }
}