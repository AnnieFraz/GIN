package com.anniefraz.dissertation.main.results;

import com.anniefraz.dissertation.gin.patch.Patch;

import java.io.File;

public class Result {

    private int currentRep;
    private Patch patch;
    private String outputFileString;
    private long time;
    private Class<?> compiledClass;
    private File file = null;
    private boolean compileSuccess;
    //Add Test Runner Data
    private boolean passed;
    //Opacitor Data
    private double opacitorMeasurement1;
    private double opacitorMeasurement2;
    private double fitnessScore;
    private double unitTestScore;




    private Result(int currentRep, Patch patch, String outputFileString, long time, Class<?> compiledClass, boolean compileSuccess, boolean passed, double opacitorMeasurement1, double opacitorMeasurement2, double fitnessScore, double unitTestScore) {
        this.currentRep = currentRep;
        this.patch = patch;
        this.outputFileString = outputFileString;
        this.time = time;
        this.compiledClass = compiledClass;
        this.compileSuccess = compileSuccess;
        this.passed = passed;
        this.opacitorMeasurement1 = opacitorMeasurement1;
        this.opacitorMeasurement2 = opacitorMeasurement2;
        this.fitnessScore = fitnessScore;
        this.unitTestScore = unitTestScore;

    }

    public double getUnitTestScore(){return unitTestScore;}

    public int getCurrentRep() {
        return currentRep;
    }

    public Patch getPatch() {
        return patch;
    }

    public String getOutputFileString() {
        return outputFileString;
    }

    public long getTime() {
        return time;
    }

    public Class<?> getCompiledClass() {
        return compiledClass;
    }

    public File getFile() {
        return file;
    }

    public boolean isCompileSuccess() {
        return compileSuccess;
    }

    public boolean isPassed() {
        return passed;
    }

    public double getOpacitorMeasurement1() {
        return opacitorMeasurement1;
    }

    public double getOpacitorMeasurement2() {
        return opacitorMeasurement2;
    }

    public double getFitnessScore() {
        return fitnessScore;
    }

    public static ResultBuilder getBuilder(){
        return new ResultBuilder();
    }





    public static class ResultBuilder {
        private int currentRep;
        private Patch patch;
        private String outputFileString;
        private long time;
        private Class<?> compiledClass;
        private boolean compileSuccess;
        private boolean passed;
        private double opacitorMeasurement1;
        private double opacitorMeasurement2;
        private double unitTestScore;
        private double fitnessScore;

        private ResultBuilder() {
        }

        public ResultBuilder setCurrentRep(int currentRep) {
            this.currentRep = currentRep;
            return this;
        }

        public ResultBuilder setPatch(Patch patch) {
            this.patch = patch;
            return this;
        }

        public ResultBuilder setOutputFileString(String outputFileString) {
            this.outputFileString = outputFileString;
            return this;
        }

        public ResultBuilder setTime(long time) {
            this.time = time;
            return this;
        }

        public ResultBuilder setCompiledClass(Class<?> compiledClass) {
            this.compiledClass = compiledClass;
            return this;
        }

        public ResultBuilder setCompileSuccess(boolean compileSuccess) {
            this.compileSuccess = compileSuccess;
            return this;
        }

        public ResultBuilder setUnitTestScore(double unitTestScore) {
            this.unitTestScore = unitTestScore;
            return this;
        }

        public ResultBuilder setPassed(boolean passed) {
            this.passed = passed;
            return this;
        }

        public ResultBuilder setOpacitorMeasurement1(double opacitorMeasurement1) {
            this.opacitorMeasurement1 = opacitorMeasurement1;
            return this;
        }

        public ResultBuilder setOpacitorMeasurement2(double opacitorMeasurement2) {
            this.opacitorMeasurement2 = opacitorMeasurement2;
            return this;
        }

        public double setFitnessScore(double fitnessScore) {
            this.fitnessScore = fitnessScore;
            return fitnessScore;
        }

        public Result build() {
            return new Result(currentRep, patch, outputFileString, time, compiledClass, compileSuccess, passed, opacitorMeasurement1, opacitorMeasurement2, fitnessScore, unitTestScore);
        }
    }
}


