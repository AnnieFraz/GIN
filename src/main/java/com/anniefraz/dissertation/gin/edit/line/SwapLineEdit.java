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
        this.firstLineIndex = firstLineIndex;
        this.secondLineIndex = secondLineIndex;
    }

    @Override
    protected void applyMethod(AnnaClass annaClass) {
        List<String> lines = annaClass.getLines();
        System.out.println(lines);
        //Collections.swap(lines, firstLineIndex, secondLineIndex);
        String line1 = lines.remove(firstLineIndex);
        System.out.println(line1);
        String line2 = lines.remove(secondLineIndex);
        System.out.println(line2);
        System.out.println(lines);
        lines.add(secondLineIndex-1, line1);
        lines.add(firstLineIndex-1, line2);
        //Collections.swap(lines, secondLineIndex, firstLineIndex);
        System.out.println(lines);
    }

    @Override
    public String toString() {
        return "SwapLineEdit{" +
                "firstLineIndex=" + firstLineIndex +
                ", secondLineIndex=" + secondLineIndex +
                '}';
    }
}
