package com.anniefraz.dissertation.gin.patch;

import com.anniefraz.dissertation.gin.edit.Edit;
import com.anniefraz.dissertation.gin.source.Source;

import java.util.List;

public class Patch {
    private final Source source;
    private final List<Edit> edits;
    private final Source outputSource;
    private double fitnessScore;
    private long compileTime;
    private double unitTestScore;
    private double opacitorMeasurement1;
    private double opacitorMeasurement2;
    private boolean compiled;

    public Patch(Source source, List<Edit> edits) {
        this.source = source;
        this.edits = edits;
        outputSource = source.clone();
        edits.forEach(outputSource::apply);
    }

    public Patch clone(Patch patch){
        Patch clone = new Patch(patch.getSource(), patch.getEdits());
        return clone;

    }
    public Source getSource() {
        return source;
    }

    public List<Edit> getEdits() {
        return edits;
    }

    public Source getOutputSource() {
        return outputSource;
    }

    public double getFitnessScore() {
        return fitnessScore;
    }

    public void setFitnessScore(double fitnessScore) {
        this.fitnessScore = fitnessScore;
    }

    public long getCompileTime() {
        return compileTime;
    }

    public void setCompileTime(long compileTime) {
        this.compileTime = compileTime;
    }

    public double getUnitTestScore() {
        return unitTestScore;
    }

    public void setUnitTestScore(double unitTestScore) {
        this.unitTestScore = unitTestScore;
    }

    public double getOpacitorMeasurement1() {
        return opacitorMeasurement1;
    }

    public void setOpacitorMeasurement1(double opacitorMeasurement1) {
        this.opacitorMeasurement1 = opacitorMeasurement1;
    }

    public double getOpacitorMeasurement2() {
        return opacitorMeasurement2;
    }

    public void setOpacitorMeasurement2(double opacitorMeasurement2) {
        this.opacitorMeasurement2 = opacitorMeasurement2;
    }

    public boolean isCompiled() {
        return compiled;
    }

    public void setCompiled(boolean compiled) {
        this.compiled = compiled;
    }

    @Override
    public String toString() {
        return "Patch{" +
                //"source=" + source +
                ", edits=" + edits +
               // ", outputSource=" + outputSource +
                ", fitnessScore=" + fitnessScore +
                ", compileTime=" + compileTime +
                ", unitTestScore=" + unitTestScore +
                ", opacitorMeasurement1=" + opacitorMeasurement1 +
                ", opacitorMeasurement2=" + opacitorMeasurement2 +
                ", compiled=" + compiled +
                '}';
    }
}
