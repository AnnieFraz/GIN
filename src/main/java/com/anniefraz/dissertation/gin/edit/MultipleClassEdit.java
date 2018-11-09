package com.anniefraz.dissertation.gin.edit;

import com.anniefraz.dissertation.gin.source.AnnaClass;
import com.anniefraz.dissertation.gin.source.AnnaPackage;
import com.anniefraz.dissertation.gin.source.AnnaPath;

import java.util.List;

public abstract class MultipleClassEdit implements Edit {
    protected final AnnaPath annaPath;

    protected final AnnaPackage annaPackage;

    public MultipleClassEdit(AnnaPath annaPath, AnnaPackage annaPackage) {
        this.annaPath = annaPath;
        this.annaPackage = annaPackage;
    }

    @Override
    public void apply(AnnaClass annaClass) {

        List<AnnaClass> annaClassList = annaPackage.getAnnaClasses();

        for (int i = 0; i < annaClassList.size(); i++){
            annaClass = annaClassList.get(i);
            if (annaPath.equals(annaClass.getPath())) {
                applyMethod(annaClass);
            }
        }

        if (annaPath.equals(annaClass.getPath())) {
            applyMethod(annaClass);
        }
    }

    protected abstract void applyMethod(AnnaClass annaClass);


}
