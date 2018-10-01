package com.anniefraz.dissertation.gin.infrastructure;

import com.anniefraz.dissertation.gin.SourceFile;
import com.anniefraz.dissertation.gin.domain.Edit;
import com.anniefraz.dissertation.gin.domain.Patch;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class InMemoryPatch implements Patch {

    private List<Edit> edits;
    private SourceFile sourceFile;

    public InMemoryPatch(List<Edit> edits, SourceFile sourceFile) {
        this.edits = edits;
        this.sourceFile = sourceFile;
    }

    @Override
    public int numberOfEdits() {
        return edits.size();
    }

    @Override
    public boolean add(Edit edit) {
        return edits.add(edit);
    }

    @Override
    public Edit remove(int index) {
        return edits.remove(index);
    }

    @Override
    public String apply() {
        AtomicReference<SourceFile> atomicSourceFile = new AtomicReference<>(sourceFile);
        edits.forEach(atomicSourceFile::updateAndGet);
        return atomicSourceFile.get().toString();
    }
}
