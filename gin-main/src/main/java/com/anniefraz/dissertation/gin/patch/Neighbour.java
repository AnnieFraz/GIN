package com.anniefraz.dissertation.gin.patch;

import com.anniefraz.dissertation.gin.edit.Edit;
import com.anniefraz.dissertation.gin.source.Source;
import org.apache.commons.lang.builder.ToStringBuilder;

import java.util.List;

public class Neighbour extends Patch {

    private final Patch parent;
    private List<Edit> edits;

    public Neighbour(Offspring offspring) {
        super(offspring);
        parent = offspring;
    }

    public Neighbour( List<Edit> edits, Offspring offspring) {
        super(offspring);
        this.parent = offspring;
        this.edits = edits;
    }

    public Patch getParent() {
        return parent;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("SUPER PATCH:", super.toString())
                .append("PARENT: ", parent)
                .append("EDITS: ", edits)
                .toString();
    }
}
