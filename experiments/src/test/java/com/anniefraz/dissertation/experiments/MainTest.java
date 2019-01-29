package com.anniefraz.dissertation.experiments;

import org.junit.jupiter.api.Test;

public class MainTest {

    @Test
    public void test() throws Exception {
        Main.main(new String[]{"ITERATIVE", "testFile", "10", "100"});
    }

    @Test
    public void test2() throws Exception {
        Main.main(new String[]{"ITERATIVE", "testFile", "100", "10"});
    }

    @Test
    public void test3() throws Exception {
        Main.main(new String[]{"ITERATIVE", "testFile", "1000", "100"});
    }
}
