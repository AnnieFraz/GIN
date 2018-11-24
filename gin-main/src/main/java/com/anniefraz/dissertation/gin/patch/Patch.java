package com.anniefraz.dissertation.gin.patch;

import com.anniefraz.dissertation.gin.edit.Edit;
import com.anniefraz.dissertation.gin.source.Source;

import java.util.List;

public class Patch {
    private final Source source;
    private final List<Edit> edits;
    private final Source outputSource;

    public Patch(Source source, List<Edit> edits) {
        this.source = source;
        this.edits = edits;
        outputSource = source.clone();
        edits.forEach(outputSource::apply);
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
}
