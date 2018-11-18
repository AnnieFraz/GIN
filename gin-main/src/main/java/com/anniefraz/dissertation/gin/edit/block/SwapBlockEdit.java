package com.anniefraz.dissertation.gin.edit.block;

import com.anniefraz.dissertation.gin.edit.Edit;
import com.anniefraz.dissertation.gin.edit.SingleClassEdit;
import com.anniefraz.dissertation.gin.source.AnnaClass;
import com.anniefraz.dissertation.gin.source.AnnaPath;
import com.github.javaparser.utils.Log;

import java.util.Collections;
import java.util.List;

public class SwapBlockEdit extends SingleClassEdit {

    private final int blockOneStartNo;
    private final int blockOneEndNo;
    private final int blockTwoStartNo;
    private final int blockTwoEndNo;

    public SwapBlockEdit(int blockOneStartNo, int blockOneEndNo, int blockTwoStartNo, int blockTwoEndNo,AnnaPath annaPath) {
        super(annaPath);
        this.blockOneStartNo = blockOneStartNo;
        this.blockOneEndNo = blockOneEndNo;
        this.blockTwoStartNo = blockTwoStartNo;
        this.blockTwoEndNo = blockTwoEndNo;
    }

    @Override
    protected void applyMethod(AnnaClass annaClass){

        if ((blockTwoEndNo > blockTwoStartNo)|| (blockOneEndNo > blockOneStartNo)) {
            Log.error("The end block index is less than Start");
        }

        List<String> lines = annaClass.getLines();

        // (blockOneEndNo > blockTwoEndNo){
            for (int i = blockOneStartNo; i < blockOneEndNo; i++ ){

                System.out.println(blockOneStartNo+i);
                System.out.println(blockTwoStartNo+i);
                System.out.println(lines);

                String line1 = lines.remove(i);
                //System.out.println("yo"+lines.remove(i));
                System.out.println("l1: "+line1);
                String line2 = lines.remove(i + blockTwoStartNo);
                System.out.println("l2: "+line1);

                lines.add(blockTwoStartNo, line1);
                lines.add(blockOneStartNo, line2);

                System.out.println(lines);

                //Collections.swap(lines, blockOneStartNo1, blockTwoStartNo1);
            //}
            /*
        } else if (blockTwoEndNo > blockOneEndNo){
            for (int i = 0; i < blockTwoEndNo; i++ ){

                System.out.println(blockOneStartNo+i);
                System.out.println(blockTwoStartNo+i);
                int blockOneStartNo1 = blockOneStartNo+i;
                int blockTwoStartNo1 = blockTwoStartNo+i;

                //Collections.swap(lines, blockOneStartNo1, blockTwoStartNo1);
            }*/
        }








    }
}
