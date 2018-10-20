package com.anniefraz.dissertation.gin.edit.line;

import com.anniefraz.dissertation.gin.edit.SingleClassEdit;
import com.anniefraz.dissertation.gin.source.AnnaClass;
import com.anniefraz.dissertation.gin.source.AnnaPath;
import org.apache.commons.collections4.ListUtils;

import java.util.Collections;
import java.util.List;

public class MoveLineEdit extends SingleClassEdit {
    private final int sourceLineIndex;
    private final int destinationLineIndex;

    public MoveLineEdit(int sourceLineIndex, int destinationLineIndex, AnnaPath annaPath) {
        super(annaPath);
        this.sourceLineIndex = sourceLineIndex;
        this.destinationLineIndex = destinationLineIndex;
    }



    @Override
    protected void applyMethod(AnnaClass annaClass) {
        List<String> lines = annaClass.getLines();
        String line = lines.remove(sourceLineIndex);
        lines.add(destinationLineIndex, line);


    }
}
