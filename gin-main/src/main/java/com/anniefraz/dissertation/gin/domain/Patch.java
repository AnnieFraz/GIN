package com.anniefraz.dissertation.gin.domain;

public interface Patch {

    int numberOfEdits();

    boolean add(Edit edit);

    Edit remove(int index);

    String apply();
}
