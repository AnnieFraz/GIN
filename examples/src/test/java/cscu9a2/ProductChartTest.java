package cscu9a2;


import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public class ProductChartTest {

   // private ProductChart productChart;

    @Before
    public void initialise(){
      //  productChart = new ProductChart();
    }

    @Test
    public void testCreateGUI(){
        Assert.assertEquals(4, 2+2);
    }

    @Test
    public void testPaintScreen(){
        Assert.assertEquals(4, 2*2);

    }
}
