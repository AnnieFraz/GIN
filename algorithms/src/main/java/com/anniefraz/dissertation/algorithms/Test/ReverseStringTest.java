package com.anniefraz.dissertation.algorithms.Test;

import org.junit.jupiter.api.Test;
public class ReverseStringTest {

    public ReverseStringTest() {
    }

    @Test
    void test1(){
        new ReverseString("look");

    }
    @Test
    void test2(){
        new ReverseString("supercalifragilisticexpialidocious");
    }
    @Test
    void canDealWithUpperCaseTest(){
        new ReverseString("ACDE");
    }
    @Test
    void failWithNumbers(){
        new ReverseString(1234+"");
    }
}
