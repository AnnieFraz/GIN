package com.anniefraz.dissertation.experiments.loops;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class IterativeBubbleSort {

    public static void bubbleSort(int array[], int n){
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
        int[] array = new int[args.length];
        for (int i = 0; i < args.length; i++) {
            array[i] = Integer.parseInt(args[i]);
        }

        bubbleSort(array, array.length);

      }
}
