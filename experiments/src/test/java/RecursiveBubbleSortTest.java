import loops.RecursiveBubbleSort;
import org.junit.jupiter.api.Test;

class RecursiveBubbleSortTest {

    private RecursiveBubbleSort rbs;

    //ASCENDING TESTS
    @Test
    void ascTest1(){
        int array[] = {1,2,3,4,5,6,7,8,9,10};
        rbs.bubbleSort(array, array.length);
    }
    @Test
    void ascTest2(){
        int array[] = {10,20,30,40,50,60,70,80,90,100};
        rbs.bubbleSort(array, array.length);
    }
    @Test
    void ascTest3(){
        int array[] = {100,200,300,400,500,600,700,800,900,1000};
        rbs.bubbleSort(array, array.length);
    }
    @Test
    void ascTest4(){
        int array[] = {1000,2000,3000,4000,5000,6000,7000,8000,9000,10000};
        rbs.bubbleSort(array, array.length);
    }
    @Test
    void ascTest5(){
        int array[] = {63,64,65,66,67};
        rbs.bubbleSort(array, array.length);
    }
    @Test
    void ascTest6(){
        int array[] = {8001,8002,8003,8004,8005};
        rbs.bubbleSort(array, array.length);
    }

    @Test
    void ascTest7(){
        int array[] = {15,17,19,21,23,25,27,29};
        rbs.bubbleSort(array, array.length);
    }
    @Test
    void ascTest8(){
        int array[] = {198,199,200,201,202,203,204};
        rbs.bubbleSort(array, array.length);
    }
    @Test
    void ascTest9(){
        int array[] = {15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35};
        rbs.bubbleSort(array, array.length);
    }
    @Test
    void ascTest10(){
        int array[] = {198,199,200,201,202,203,204,205,206,207,208,209,210,211,212,213,214,215,216,217,218};
        rbs.bubbleSort(array, array.length);
    }

    //DESCENDING TESTS
    @Test
    void descTest1(){
        int array[] = {10,9,8,7,6,5,4,3,2,1};
        rbs.bubbleSort(array, array.length);
    }
    @Test
    void descTest2(){
        int array[] = {100,90,80,70,60,50,40,30,20,10};
        rbs.bubbleSort(array, array.length);
    }
    @Test
    void descTest3(){
        int array[] = {1000,900,800,700,600,500,400,300,200,100};
        rbs.bubbleSort(array, array.length);
    }
    @Test
    void descTest4(){
        int array[] = {10000,9000,8000,7000,6000,5000,4000,3000,2000,1000};
        rbs.bubbleSort(array, array.length);
    }
    @Test
    void descTest5(){
        int array[] = {67,66,65,64,63};
        rbs.bubbleSort(array, array.length);
    }
    @Test
    void descTest6(){
        int array[] = {8005,8004,8003,8002,8001};
        rbs.bubbleSort(array, array.length);
    }

    @Test
    void descTest7(){
        int array[] = {29, 27, 25,23,21,19,17,15};
        rbs.bubbleSort(array, array.length);
    }
    @Test
    void descTest8(){
        int array[] = {204,203,202,201,200,199,198};
        rbs.bubbleSort(array, array.length);
    }
    @Test
    void descTest9(){
        int array[] = {35,34,33,32,31,30,29,28,27,26,25,24,23,22,21,20,19,18,17,16,15};
        rbs.bubbleSort(array, array.length);
    }
    @Test
    void descTest10(){
        int array[] = {218,217,216,215,214,213,212,211,210,209,208,207,206,205,204,203,202,201,200,199,198};
        rbs.bubbleSort(array, array.length);
    }

    //RANDOM ARRAY TESTS
    @Test
    void randTest1(){
        int array[] = {3, 6, 8, 9, 1, 2, 7, 10, 4, 5};
        rbs.bubbleSort(array, array.length);
    }
    @Test
    void randTest2(){
        int array[] = {20, 7, 6, 12, 13, 14, 17, 5, 19, 2};
        rbs.bubbleSort(array, array.length);
    }
    @Test
    void randTest3(){
        int array[] = {69, 54, 98, 41, 32, 96, 59, 48, 84, 85};
        rbs.bubbleSort(array, array.length);
    }
    @Test
    void randTest4(){
        int array[] = {864, 497, 995, 31, 491, 111, 613, 235, 971, 100};
        rbs.bubbleSort(array, array.length);
    }
    @Test
    void randTest5(){
        int array[] = {763, 19, 737, 861, 808};
        rbs.bubbleSort(array, array.length);
    }
    @Test
    void randTest6(){
        int array[] = {179, 886, 924, 822, 614};
        rbs.bubbleSort(array, array.length);
    }
    @Test
    void randTest7(){
        int array[] = {1, 72, 68, 13, 14, 57, 39, 3};
        rbs.bubbleSort(array, array.length);
    }
    @Test
    void randTest8(){
        int array[] = {75, 194, 248, 109, 68, 156, 182, 204};
        rbs.bubbleSort(array, array.length);
    }
    @Test
    void randTest9(){
        int array[] = {4, 235, 17, 33, 22, 239, 139, 158, 173, 24, 206, 74, 147, 125, 155, 138, 54, 140, 195, 97};
        rbs.bubbleSort(array, array.length);
    }
    @Test
    void randTest10(){
        int array[] = {138, 225, 142, 214, 239, 101, 201, 205, 135, 223, 109, 245, 154, 107, 167, 100, 137, 124, 113, 191};
        rbs.bubbleSort(array, array.length);
    }


}