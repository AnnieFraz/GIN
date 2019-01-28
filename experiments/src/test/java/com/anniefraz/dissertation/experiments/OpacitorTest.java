package com.anniefraz.dissertation.experiments;

import opacitor.Opacitor;
import opacitor.enumerations.MeasurementType;
import opacitor.exceptions.FailedToCompileException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class OpacitorTest {

    @Test
    public void test() {
        try {
            Opacitor opacitor = new Opacitor.OpacitorBuilder("", "", new String[]{})
                    .measurementType(MeasurementType.CODE_LENGTH)
                    .build();
        } catch (Exception e) {
            assertTrue(e instanceof FailedToCompileException);
        }

    }
}
