package com.anniefraz.dissertation.gin.patch;

import com.anniefraz.dissertation.gin.edit.Edit;
import com.anniefraz.dissertation.gin.source.Source;
import org.apache.commons.lang.builder.ToStringBuilder;

import java.util.List;


public class Offspring extends Patch {

    private String origin;
    private Source source;
    private List<Edit> edits;
    private long compileTime;

    @Override
    public double getFitnessScore() {
        return fitnessScore;
    }

    @Override
    public void setFitnessScore(double fitnessScore) {
        this.fitnessScore = fitnessScore;
    }

    private double fitnessScore;

    public Offspring(Source source, List<Edit> edits) {
        super(source, edits);
        this.source = source;
        this.edits = edits;

    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }



    public Patch getPatch() {
        return new Patch(source, edits);
    }

    @Override
    public void setCompileTime(long compileTime) {
        this.compileTime = compileTime;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("ORIGIN: ", origin)
                .append("NO.EDITS: ", edits.size())
               // .append("source", source)
                .append("EDITS: ", edits)
                .append("TIME: ", compileTime)
                .append("FITNESS SCORE: ", fitnessScore)
                .toString();
    }
}
