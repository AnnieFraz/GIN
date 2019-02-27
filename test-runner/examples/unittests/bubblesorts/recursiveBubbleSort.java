import java.util.Arrays;

public class recursiveBubbleSort {

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


    }

    public static void main(String[] args) {
        int array[] = {7, 69, 76, 102, 94, 53, 62 , 101};

        bubbleSort(array, array.length);

        System.out.println("Recursive - Sorted array: " + Arrays.toString(array));
    }
}
