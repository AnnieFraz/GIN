package foo;

import org.junit.Assert;
import org.junit.Test;
public class ReverseStringTest {

    @Test
    public void test1(){
        new ReverseString("look");

    }
    @Test
    public void test2(){
        new ReverseString("supercalifragilisticexpialidocious");
    }
    @Test
   public void canDealWithUpperCaseTest(){
        new ReverseString("ACDE");
    }
    @Test
    public void failWithNumbers(){
        new ReverseString(1234+"");
    }
}
