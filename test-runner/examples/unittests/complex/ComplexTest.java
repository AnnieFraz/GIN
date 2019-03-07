package complex;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ComplexTest {

    private double testRealPart;
    private double testImaginePart;

    private Complex testComplex;

    @Before
    public void initialise(){
        testRealPart = 3.3;
        testImaginePart = 6.5;
        testComplex = new Complex(testRealPart, testImaginePart);
    }

    @Test
    public void testReturn(){

        testComplex.re() ;
        testComplex.im() ;
    }
}
