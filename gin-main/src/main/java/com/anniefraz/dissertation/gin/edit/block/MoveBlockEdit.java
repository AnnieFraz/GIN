package com.anniefraz.dissertation.gin.edit.block;

import com.anniefraz.dissertation.gin.edit.SingleClassEdit;
import com.anniefraz.dissertation.gin.source.AnnaClass;
import com.anniefraz.dissertation.gin.source.AnnaPath;
import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.utils.Log;

import java.util.List;

public class MoveBlockEdit extends SingleClassEdit {

    private final int startBlockInt;
    private final int endBlockInt;
    private final int newLocation;

    public MoveBlockEdit(int startBlockInt, int endBlockInt, int newLocation, AnnaPath annaPath) {
        super(annaPath);
        this.startBlockInt = startBlockInt;
        this.endBlockInt = endBlockInt;
        this.newLocation = newLocation;
    }

    @Override
    protected void applyMethod(AnnaClass annaClass) {

        if (endBlockInt > startBlockInt){
            Log.error("The end block index is less than Start");
        }

        List<String> lines = annaClass.getLines();

            //System.out.println(i);
            //System.out.println(newLocation + i);
            //System.out.println(line);

/*
            BlockStmt bs = new BlockStmt();
            bs.setStatements(
                 for (int i = 0; i < endBlockInt; i++) {
                    String line = lines.remove(startBlockInt);
            );*/

            //lines.add(newLocation, bs);




        }



}
