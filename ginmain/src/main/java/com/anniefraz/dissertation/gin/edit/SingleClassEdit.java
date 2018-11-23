package com.anniefraz.dissertation.gin.edit;

import com.anniefraz.dissertation.gin.source.AnnaClass;
import com.anniefraz.dissertation.gin.source.AnnaPath;
import com.anniefraz.dissertation.gin.source.Source;

public abstract class SingleClassEdit implements Edit {
    protected final AnnaPath annaPath;

    public SingleClassEdit(AnnaPath annaPath) {
        this.annaPath = annaPath;
    }


    @Override
    public void apply(Source source) {
        for (AnnaClass annaClass :
                source.getAnnaClasses()) {
            if (annaPath.equals(annaClass.getPath())) {
                applyMethod(annaClass);
            }
        }
    }

    protected abstract void applyMethod(AnnaClass annaClass);


}
