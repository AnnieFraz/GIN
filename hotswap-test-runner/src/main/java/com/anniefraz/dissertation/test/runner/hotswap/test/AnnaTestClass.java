package com.anniefraz.dissertation.test.runner.hotswap.test;

import com.anniefraz.dissertation.gin.source.AnnaClass;
import com.anniefraz.dissertation.gin.source.AnnaPath;

import java.util.List;

public class AnnaTestClass extends AnnaClass {
    public AnnaTestClass(AnnaPath className, List<String> lines) {
        super(className, lines);
    }
    public AnnaTestClass(AnnaClass annaClass){
        super(annaClass.getPath(), annaClass.getLines());
    }
}
