package com.anniefraz.dissertation.experiments.results;

import com.anniefraz.dissertation.experiments.BSType;

import java.io.File;
import java.util.Arrays;

public class ResultBS {

    private BSType bsType;
    private double opacitorMeasurement1;
    private double OpacitorMeasurement2;
    private int[] array;
    private int seed;
    private int arraySize;
    private int currentRep;

    private ResultBS(int currentRep, BSType bsType, double opacitorMeasurement1, double opacitorMeasurement2, int[] array, int seed, int arraySize){
        this.currentRep = currentRep;
        this.bsType = bsType;
        this.opacitorMeasurement1 = opacitorMeasurement1;
        this.OpacitorMeasurement2 = opacitorMeasurement2;
        this.array = array;
        this.seed = seed;
        this.arraySize = arraySize;

    }

    public BSType getBsType() {
        return bsType;
    }

    public double getOpacitorMeasurement1() {
        return opacitorMeasurement1;
    }

    public double getOpacitorMeasurement2() {
        return OpacitorMeasurement2;
    }

    public int[] getArray() {
        return array;
    }


    public int getSeed() {
        return seed;
    }

    public int getArraySize() {
        return arraySize;
    }

    public int getCurrentRep() {
        return currentRep;
    }

    @Override
    public String toString() {
        return "ResultBS{" +
                "bsType=" + bsType +
                ", opacitorMeasurement1=" + opacitorMeasurement1 +
                ", OpacitorMeasurement2=" + OpacitorMeasurement2 +
                ", array=" + Arrays.toString(array) +
                ", seed=" + seed +
                ", arraySize=" + arraySize +
                ", currentRep=" + currentRep +
                '}';
    }

    public static ResultBSBuilder getBuilder(){
        return new ResultBSBuilder();
    }

    public static class ResultBSBuilder {
        private BSType bsType;
        private double opacitorMeasurement1;
        private double OpacitorMeasurement2;
        private int[] array;
        private int seed;
        private int arraySize;
        private int currentRep;

        public ResultBSBuilder setBsType(BSType bsType) {
            this.bsType = bsType;
            return this;
        }

        public ResultBSBuilder setOpacitorMeasurement1(double opacitorMeasurement1) {
            this.opacitorMeasurement1 = opacitorMeasurement1;
            return this;
        }

        public ResultBSBuilder setOpacitorMeasurement2(double opacitorMeasurement2) {
            OpacitorMeasurement2 = opacitorMeasurement2;
            return this;
        }

        public ResultBSBuilder setArray(int[] array) {
            this.array = array;
            return this;
        }

        public ResultBSBuilder setSeed(int seed) {
            this.seed = seed;
            return this;
        }

        public ResultBSBuilder setArraySize(int arraySize) {
            this.arraySize = arraySize;
            return this;
        }

        public ResultBSBuilder setCurrentRep(int currentRep) {
            this.currentRep = currentRep;
            return this;
        }

        public ResultBS build(){
            return new ResultBS(currentRep, bsType, opacitorMeasurement1, OpacitorMeasurement2, array, seed, arraySize);
        }
    }
}
