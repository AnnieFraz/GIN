import java.io.PrintStream;
import java.util.Random;
public class TriangleCPU {
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
            return EQUALATERAL;
        } else if (a == b || b == c) {
            return ISOCELES;
        } else {
            return SCALENE;
        }
    }
    private static void delay() {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            // do nothing
        }
    }

    private static void wasteCPU(PrintStream out, final int startDelayMS, final int iterations) throws InterruptedException {
        Random random = new Random(1); // seed 1

        Thread.sleep(startDelayMS);

        double d = 1;
        for (int i = 0; i < iterations; ++i) {
            out.print(i + ", ");
            if (i % 100 == 0) {
                out.println();
            }
            d *= random.nextDouble();
            out.println("Result: " + d);
        }

        out.println("Finished");
    }

    public static void main(String[] args) {
        classifyTriangle(1,1,1);

        PrintStream out = new PrintStream(System.out);

        wasteCPU(out, 500, 500);

        out.close();
    }


}