package com.anniefraz.dissertation.gin.patch;

import com.anniefraz.dissertation.gin.edit.Edit;
import com.anniefraz.dissertation.gin.source.Source;

import java.util.List;

public class Neighbour extends Patch {

    private Patch parent;

    public Neighbour(Offspring offspring) {
        super(offspring.getSource(), offspring.getEdits());
        offspring.clone(offspring);
    }

    public Patch getParent() {
        return parent;
    }

    public void setParent(Patch parent) {
        this.parent = parent;
    }


    @Override
    public String toString() {
        return "Neighbour{" +
                "parent=" + parent.toString() +
                '}';
    }
}
