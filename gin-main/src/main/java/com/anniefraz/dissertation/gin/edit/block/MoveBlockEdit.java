package com.anniefraz.dissertation.gin.edit.block;

import com.anniefraz.dissertation.gin.edit.SingleClassEdit;
import com.anniefraz.dissertation.gin.source.AnnaClass;
import com.anniefraz.dissertation.gin.source.AnnaPath;
import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.ast.stmt.Statement;
import com.github.javaparser.ast.visitor.GenericVisitor;
import com.github.javaparser.ast.visitor.VoidVisitor;
import com.github.javaparser.utils.Log;

import java.util.ArrayList;
import java.util.List;

//TODO: This needs to work
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

        int size = endBlockInt - startBlockInt;
      //  System.out.println(size);

        List<String> block = annaClass.getLines();

        for (int i = 0; i < size; i++){
            String line = lines.get(startBlockInt + i);
            block.add(line);
        }

       // System.out.println(block);

        for (int i = 0; i < size; i++){
            String line = lines.remove(startBlockInt + i);
            lines.add(newLocation+i, line);
        }

       // System.out.println(lines);


        //System.out.println(lines);

        //System.out.println(i);
            //System.out.println(newLocation + i);
            //System.out.println(line);
/*

            BlockStmt bs = new BlockStmt();
            for (int i = startBlockInt; i < endBlockInt; i++) {

                bs.getStatement(i);
            }
        System.out.println(bs);

            }
            bs.setStatements(
                 for (int i = 0; i < endBlockInt; i++) {
                    String line = lines.remove(startBlockInt);
            );


        List<String> block = new ArrayList<>();

        int total = endBlockInt - startBlockInt;

        //System.out.println(total);

        for (int i = startBlockInt; i < endBlockInt; i++) {
                block.add(i - startBlockInt, lines.get(i));
                //System.out.println("i: "+i);
        }
       // System.out.println(block);

        //BlockStmt stmt = block.getBody();

        for (int i = 0; i < total; i++) {
            //lines.add(newLocation+i, block.get(i));
            //lines.remove(startBlockInt+i);
            String line = lines.remove(startBlockInt+i);
            lines.add(newLocation+i, block.get(i));
            //lines.add(newLocation+i, line);
        }
        //System.out.println(lines);
*/

    }


    @Override
    public String toString() {
        return "MoveBlockEdit{" +
                "startBlockInt=" + startBlockInt +
                ", endBlockInt=" + endBlockInt +
                ", newLocation=" + newLocation +
                '}';
    }
}
