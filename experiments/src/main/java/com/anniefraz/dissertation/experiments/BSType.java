package com.anniefraz.dissertation.experiments;

import java.io.File;

public enum BSType {

    ITERATIVE("IterativeBubbleSort", "bubble", "bubble/IterativeBubbleSort.java"),
    RECURSIVE("RecursiveBubbleSort", "bubble", "bubble/RecursiveBubbleSort.java");


    private String className;
    private String packageName;
    private String location;

    BSType(String className, String packageName, String location) {
        this.className = className;
        this.packageName = packageName;
        this.location = location;
    }

    public String getPackageName() {
        return packageName;
    }

    public String getClassName() {
        return className;
    }

    public String getLocation() {
        return location;
    }
}
