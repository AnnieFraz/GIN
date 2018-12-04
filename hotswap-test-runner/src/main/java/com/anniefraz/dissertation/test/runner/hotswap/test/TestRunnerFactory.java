package com.anniefraz.dissertation.test.runner.hotswap.test;

public interface TestRunnerFactory {
    TestRunner getJunit4TestRunner(TestSource testSource);
}
