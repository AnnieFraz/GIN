package com.anniefraz.dissertation.gin.edit;

import com.anniefraz.dissertation.gin.source.AnnaClass;
import com.anniefraz.dissertation.gin.source.AnnaPath;
import com.anniefraz.dissertation.gin.source.Source;

import java.util.List;

public abstract class MultipleClassEdit implements Edit {

    private List<AnnaPath> annaPaths;

    public MultipleClassEdit(List<AnnaPath> annaPaths) {
        this.annaPaths = annaPaths;
    }

    @Override
    public void apply(Source source) {
        List<AnnaClass> annaClasses = source.getAnnaClasses();
        for (AnnaClass annaClass :
                annaClasses) {
            if (annaPaths.contains(annaClass.getPath())){
                applyMethod(annaClass);
            }
        }
    }

    protected abstract void applyMethod(AnnaClass annaClaszss);


}
