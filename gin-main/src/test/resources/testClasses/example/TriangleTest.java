package example;

import org.junit.Assert;
import org.junit.Test;

public class TriangleTest {
    public TriangleTest() {
    }

    private void checkClassification(int[][] var1, int var2) {
        int[][] var3 = var1;
        int var4 = var1.length;

        for(int var5 = 0; var5 < var4; ++var5) {
            int[] var6 = var3[var5];
            int var7 = Triangle.classifyTriangle(var6[0], var6[1], var6[2]);
            Assert.assertEquals((long)var2, (long)var7);
        }

    }

    @Test
    public void testInvalidTriangles() throws Exception {
        int[][] var1 = new int[][]{{1, 2, 9}, {-1, 1, 1}, {1, -1, 1}, {1, 1, -1}, {100, 80, 10000}};
        this.checkClassification(var1, 0);
    }

    @Test
    public void testEqualateralTriangles() throws Exception {
        int[][] var1 = new int[][]{{1, 1, 1}, {100, 100, 100}, {99, 99, 99}};
        this.checkClassification(var1, 2);
    }

    @Test
    public void testIsocelesTriangles() throws Exception {
        int[][] var1 = new int[][]{{100, 90, 90}, {1000, 900, 900}, {3, 2, 2}, {30, 16, 16}};
        this.checkClassification(var1, 3);
    }

    @Test
    public void testScaleneTriangles() throws Exception {
        int[][] var1 = new int[][]{{5, 4, 3}, {1000, 900, 101}, {3, 20, 21}, {999, 501, 600}};
        this.checkClassification(var1, 1);
    }
}
