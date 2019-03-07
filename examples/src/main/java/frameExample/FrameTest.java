package frameExample;

import complex.Complex;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class FrameTest {

    private TestFrameExample testFrameExample;
    @Before
    public void initialise(){

    }

    @Test
    public void testReturn(){
        int result  = 2+2;
        Assert.assertEquals(4, result);
    }

}
