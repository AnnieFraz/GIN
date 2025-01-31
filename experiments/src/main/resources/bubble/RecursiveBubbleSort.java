package bubble;

import java.util.Arrays;

public class RecursiveBubbleSort {

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
               // bubbleSort(array);


            }

        }
        if (n-1 >1){
            bubbleSort(array, n-1);
        }
        System.out.println("Recursive - Sorted array: " + Arrays.toString(array));
        System.out.println("time:" + System.currentTimeMillis());
    }

    public static void main(String[] args) {
        int[] array = new int[args.length];
        for (int i = 0; i < args.length; i++) {
            array[i] = Integer.parseInt(args[i]);
        }  bubbleSort(array, array.length);



    }
}
