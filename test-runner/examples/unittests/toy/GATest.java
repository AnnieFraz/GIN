package toy;

import org.junit.Before;
import org.junit.Test;

public class GATest {

    private GA2 ga;

    @Before
    public void intialise(){
         ga = new GA2();
    }
    @Test
    public void start(){
        ga.population.initializePopulation(10);
    }
}
