package com.anniefraz.dissertation.gin.edit.line;

import com.anniefraz.dissertation.gin.edit.SingleClassEdit;
import com.anniefraz.dissertation.gin.source.AnnaClass;
import com.anniefraz.dissertation.gin.source.AnnaPath;

import java.util.Collections;
import java.util.List;

public class SwapLineEdit extends SingleClassEdit {
    private final int firstLineIndex;
    private final int secondLineIndex;

    public SwapLineEdit(int firstLineIndex, int secondLineIndex, AnnaPath annaPath) {
        super(annaPath);

        if (firstLineIndex  > secondLineIndex){
            this.firstLineIndex = secondLineIndex;
            this.secondLineIndex = firstLineIndex;
        } else{
            this.firstLineIndex = firstLineIndex;
            this.secondLineIndex = secondLineIndex;
        }
    }

    @Override
    protected void applyMethod(AnnaClass annaClass) {
        if (firstLineIndex == secondLineIndex){
            return;
        }
        List<String> lines = annaClass.getLines();
        String line1 = lines.remove(firstLineIndex);
        String line2 = lines.remove(secondLineIndex-1);
        lines.add(firstLineIndex, line2);
        lines.add(secondLineIndex, line1);
    }

    @Override
    public String toString() {
        return "SwapLineEdit{" +
                "firstLineIndex=" + firstLineIndex +
                ", secondLineIndex=" + secondLineIndex +
                '}';
    }
}
