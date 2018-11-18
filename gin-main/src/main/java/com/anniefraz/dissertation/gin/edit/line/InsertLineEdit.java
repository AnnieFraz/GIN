package com.anniefraz.dissertation.gin.edit.line;

import com.anniefraz.dissertation.gin.edit.SingleClassEdit;
import com.anniefraz.dissertation.gin.source.AnnaClass;
import com.anniefraz.dissertation.gin.source.AnnaPath;


public class InsertLineEdit extends SingleClassEdit {
    private final int lineNumber;
    private final String lineContents;

    public InsertLineEdit(int lineNumber, String lineContents, AnnaPath annaPath) {
        super(annaPath);
        this.lineNumber = lineNumber;
        this.lineContents = lineContents;
    }

    @Override
    public String toString() {
        return "InsertLineEdit{" +
                "lineNumber=" + lineNumber +
                ", lineContents='" + lineContents + '\'' +
                '}';
    }

    /*
      public String toString(){
        return  ReflectionToStringBuilder.toString(this);
    }
     */

    @Override
    protected void applyMethod(AnnaClass annaClass) {
        annaClass.getLines().add(lineNumber, lineContents);
    }
}
