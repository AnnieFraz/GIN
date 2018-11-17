package com.anniefraz.dissertation.gin.source;


import com.anniefraz.dissertation.gin.edit.Edit;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AnnaClass implements Source {
    private final AnnaPath className;
    private final List<String> lines;

    public AnnaClass(AnnaPath className, List<String> lines) {
        this.className = className;
        this.lines = lines;
    }

    public List<String> getLines() {
        return lines;
    }

    @Override
    public List<AnnaPath> getPaths() {
        return Collections.singletonList(getPath());
    }

    @Override
    public void apply(Edit edit) {
        edit.apply(this);
    }

    @Override
    public List<AnnaClass> getAnnaClasses() {
        return Collections.singletonList(getAnnaClass());
    }

    @Override
    public Source clone() {
        return new AnnaClass(className, new ArrayList<>(lines));
    }

    public AnnaPath getPath(){
        return className;
    }

    public AnnaClass getAnnaClass(){
        return this;
    }

    public String getClassName(){return className.toString();}

    @Override
    public String toString() {
        return "AnnaClass{" +
                "className=" + className +
                ", lines=" + lines +
                '}';
    }
}
