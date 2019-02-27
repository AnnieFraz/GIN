package com.anniefraz.dissertation.main.csvResults;

import java.io.Closeable;

public interface CSVResultWriter extends Closeable {

    void writeResult(CSVResult csvResult);
}
