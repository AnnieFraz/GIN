package test;

import java.util.Arrays;

public class RecursiveBubbleSort {

    static void bubbleSort(int array[], int n){
        if (n==1){
            return;
        }

        for (int i=0; i < n; i++){
            if (array[i] > array[i+1]){
                int temp = array[i];
                array[i] = array[i=1];
                array[i+1] = temp;
            }
        }
    bubbleSort(array, array.length);
    }

    public static void main(String[] args) {
        int array[] = {7, 69, 76, 102, 94, 53, 62 , 101};

        bubbleSort(array, array.length);

        System.out.println("Recursive - Sorted array: " + Arrays.toString(array));
    }
}
