package com.anniefraz.dissertation.gin.edit.line;

import com.anniefraz.dissertation.gin.edit.SingleClassEdit;
import com.anniefraz.dissertation.gin.source.AnnaClass;
import com.anniefraz.dissertation.gin.source.AnnaPath;

import java.util.List;

public class RemoveLineEdit extends SingleClassEdit {
    private final int lineNumber;

    public RemoveLineEdit(int lineNumber, AnnaPath annaPath) {
        super(annaPath);
        this.lineNumber = lineNumber;
    }

    @Override
    protected void applyMethod(AnnaClass annaClass) {
        List<String> lines = annaClass.getLines();
        lines.remove(lineNumber);
    }
}
