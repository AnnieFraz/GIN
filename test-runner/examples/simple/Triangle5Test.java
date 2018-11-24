import org.junit.rules.Timeout;
import static org.junit.Assert.*;

public class Triangle5Test {

    private void checkClassification(int[][] triangles, int expectedResult) {
        for (int[] triangle: triangles) {
            int triangleType = Triangle5.classifyTriangle(triangle[0], triangle[1], triangle[2]);
            assertEquals(expectedResult, triangleType);
        }
    }

    @org.junit.Test(timeout=1000)
    public void testInvalidTriangles() throws Exception {
        int[][] invalidTriangles = {{1, 2, 9}, {-1, 1, 1}, {1, -1, 1}, {1, 1, -1}, {100, 80, 10000}};
        checkClassification(invalidTriangles, Triangle.INVALID);
    }

    @org.junit.Test(timeout=100)
    public void testEqualateralTriangles() throws Exception {
        int[][] equalateralTriangles = {{1, 1, 1}, {100, 100, 100}, {99, 99, 99}};
        checkClassification(equalateralTriangles, Triangle.EQUALATERAL);
    }

    @org.junit.Test(timeout=1000)
    public void testIsocelesTriangles() throws Exception {
        int[][] isocelesTriangles = {{100, 90, 90}, {1000, 900, 900}, {3,2,2}, {30,16,16}};
        checkClassification(isocelesTriangles, Triangle.ISOCELES);
    }

    @org.junit.Test(timeout=1000)
    public void testScaleneTriangles() throws Exception {
        int[][] scaleneTriangles = {{5, 4, 3}, {1000, 900, 101}, {3,20,21}, {999, 501, 600}};
        checkClassification(scaleneTriangles, Triangle.SCALENE);
    }

    @org.junit.Test(timeout=1000)
    public void testNullException() {
        throw new  NullPointerException();
    }
}
