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
    private boolean success;

    @Override
    public String toString() {
        return "Patch{" +
                "source=" + source +
                ", edits=" + edits +
                ", outputSource=" + outputSource +
                ", fitnessScore=" + fitnessScore +
                ", compileTime=" + compileTime +
                ", success=" + success +
                '}';
    }

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

    public void setFitnessScore(double score){fitnessScore = score; }

    public double getFitnessScore() {return fitnessScore;}

    public void setTime(long time){compileTime = time;}

    public long getTime(){return compileTime;}

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public boolean isSuccess() {
        return success;
    }

}
