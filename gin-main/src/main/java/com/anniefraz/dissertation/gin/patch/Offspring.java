package com.anniefraz.dissertation.gin.patch;

import com.anniefraz.dissertation.gin.edit.Edit;
import com.anniefraz.dissertation.gin.source.Source;

import java.util.List;

public class Offspring extends Patch {

    private String origin;
    private Source source;
    private List<Edit> edits;

    public Offspring(Source source, List<Edit> edits) {
        super(source, edits);
        this.source = source;
        this.edits = edits;

    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    @Override
    public String toString() {
        return "Offspring{" +
                "origin='" + origin + '\'' +
                '}';
    }

    public Patch getPatch() {
        return new Patch(source, edits);
    }
}
