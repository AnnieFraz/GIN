package com.anniefraz.dissertation.gin.edit.statement;

import com.anniefraz.dissertation.gin.edit.SingleClassEdit;
import com.anniefraz.dissertation.gin.source.AnnaClass;
import com.anniefraz.dissertation.gin.source.AnnaPath;

public class InsertBreakEdit extends SingleClassEdit {

    private final int lineNumber;
    private final String lineContents;

    public InsertBreakEdit(int lineNumber, String lineContents, AnnaPath annaPath) {
        super(annaPath);
        this.lineNumber = lineNumber;
        this.lineContents = lineContents;
    }

    @Override
    public String toString() {
        return "InsertBreakEdit{" +
                "lineNumber=" + lineNumber +
                ", lineContents='" + lineContents + '\'' +
                '}';
    }


    @Override
    protected void applyMethod(AnnaClass annaClass) {
        annaClass.getLines().add(lineNumber, lineContents);
    }
}