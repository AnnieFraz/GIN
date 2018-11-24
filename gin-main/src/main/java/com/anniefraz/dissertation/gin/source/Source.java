package com.anniefraz.dissertation.gin.source;

import com.anniefraz.dissertation.gin.edit.Edit;

import java.util.List;

public interface Source extends Cloneable{
    List<AnnaPath> getPaths();

    void apply(Edit edit);

    List<AnnaClass> getAnnaClasses();

    Source clone();


}
