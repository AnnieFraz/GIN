package com.anniefraz.dissertation.main.results;

import java.io.Closeable;

public interface ResultWriter extends Closeable {

    void writeResult(Result result);
}
