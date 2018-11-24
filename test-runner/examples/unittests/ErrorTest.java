import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ErrorTest {

    Error error;

    @Before
    public void setUp() throws Exception {
        error = new Error();
    }

    @Test
    public void testException() throws Exception {
        error.returnTen(5);
    }

    // Timeout is in ms
    @Test(timeout=1500)
    public void testTimeout() throws Exception {
        error.thisMethodTakesASecond();
    }

    @Test
    public void testAssertionError() {
        int shouldBeTen = error.returnTen(0);
        assertEquals(15, shouldBeTen);
    }


}
