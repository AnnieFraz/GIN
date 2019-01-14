package com.anniefraz.dissertation.gin.edit.noEdit;

import com.anniefraz.dissertation.gin.edit.SingleClassEdit;
import com.anniefraz.dissertation.gin.source.AnnaClass;
import com.anniefraz.dissertation.gin.source.AnnaPath;

import java.util.List;

public class NoEditEdit extends SingleClassEdit {

    public NoEditEdit(AnnaPath annaPath) {
        super(annaPath);
    }

    @Override
    protected void applyMethod(AnnaClass annaClass) {
            List<String> lines = annaClass.getLines();
        }

    @Override
    public String toString() {
        return "NoEditEdit{}";
    }
}



