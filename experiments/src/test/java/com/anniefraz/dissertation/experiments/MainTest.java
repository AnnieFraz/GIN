package com.anniefraz.dissertation.experiments;

import org.junit.jupiter.api.Test;

public class MainTest {

    @Test
    public void test() throws Exception {
        Main.main(new String[]{"ITERATIVE", "testFile", "10", "10"});
    }
}
