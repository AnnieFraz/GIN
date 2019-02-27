package com.anniefraz.dissertation.experiments.results;

import java.io.Closeable;

public interface ResultBSWriter extends Closeable {

    void writeResult(ResultBS resultBS);

}
