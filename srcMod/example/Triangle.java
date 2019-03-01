package example;
import java.io.PrintStream;
import java.util.Random;

public class Triangle {
    static final int INVALID = 0;
    static final int SCALENE = 1;
    static final int EQUALATERAL = 2;
    static final int ISOCELES = 3;
    public static int classifyTriangle(int a, int b, int c) {
        delay();
        // Sort the sides so that a <= b <= c

        if (a > b) {
            int tmp = a;
            a = b;
            b = tmp;
        }
        if (a > c) {
            int tmp = a;
            a = c;
            c = tmp;
        }
        if (b > c) {
            int tmp = b;
            b = c;
            c = tmp;
        }
        if (a + b <= c) {
            return INVALID;
        } else if (a == b && b == c) {
//this is a comment
            return EQUALATERAL;
        } else if (a == b || b == c) {
            return ISOCELES;
        } else {
            return SCALENE;
        }
    }
    private static void delay() {
       // wasteCPU( 10, 10);
        try {
            //Thread.sleep(500);

           wasteCPU( 1000, 1000);

        } catch (InterruptedException e) {
            // do nothing
        }
    }

    private static void wasteCPU( final int startDelayMS, final int iterations) throws InterruptedException {
        Random random = new Random(1); // seed 1

        Thread.sleep(startDelayMS);

        double d = 1;
        for (int i = 0; i < iterations; ++i) {
            //System.out.print(i + ", ");
            if (i % 100 == 0) {
            //    System.out.println();
            }
            d *= random.nextDouble();
          //  System.out.println("Result: " + d);
        }

        //System.out.println("Finished");
    }

    public static void main(String[] args) throws Exception{

        classifyTriangle(1,1,1);

    }
}