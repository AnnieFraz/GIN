package com.anniefraz.dissertation.gin.edit.block;

import com.anniefraz.dissertation.gin.edit.Edit;
import com.anniefraz.dissertation.gin.edit.SingleClassEdit;
import com.anniefraz.dissertation.gin.source.AnnaClass;
import com.anniefraz.dissertation.gin.source.AnnaPath;
import com.github.javaparser.utils.Log;

import java.util.*;

//TODO: This needs to work
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

        if ((blockTwoEndNo < blockTwoStartNo)|| (blockOneEndNo < blockOneStartNo)) {
            Log.error("The end block index is less than Start");
            //return;
        }

        List<String> lines = annaClass.getLines();

        System.out.println(lines);
        List<String> block1 = new ArrayList<>();
        List<String> block2 = new ArrayList<>();

        if (blockOneEndNo < blockOneStartNo) {
            Log.error("The end block index is less than Start");
        } else {
            int block1Size = blockOneEndNo - blockOneStartNo;
            for (int i = 0; i < block1Size; i++) {
                String line = lines.get(blockOneStartNo+i);
                 block1.add(line);
            }
        }

        System.out.println(block1);
        System.out.println(block1.size());

        if (blockTwoEndNo  < blockTwoStartNo) {
            Log.error("The end block index is less than Start");
        } else {
            int block2Size = blockTwoEndNo - blockTwoStartNo;
            for (int i = 0; i < block2Size; i++) {
                String line = lines.get(blockTwoStartNo+i);
                block2.add(line);
            }
        }

        System.out.println(block2);
        System.out.println(block2.size());

        if (block1.size() != block2.size()){
            System.err.println("Error");
        }else {

            for (int i = 0; i < block1.size()+1; i++) {

                String line1 = lines.remove(blockOneStartNo+i);
                String line2 = lines.remove(blockTwoStartNo+i);
                lines.add(blockTwoStartNo+i, line1);
                lines.add(blockOneStartNo+i, line2);
            }
        }
        System.out.println(lines);
/*

        // (blockOneEndNo > blockTwoEndNo){
            //for (int i = blockOneStartNo; i < blockOneEndNo-1; i++ ){

                System.out.println(blockOneStartNo+i);
                System.out.println(blockTwoStartNo+i);
                System.out.println(lines);


                String line1 = lines.remove(i);
                //System.out.println("yo"+lines.remove(i));
                //System.out.println("l1: "+line1);
                String line2 = lines.remove( blockTwoStartNo + i);
                //System.out.println("l2: "+line1);

                lines.add(blockTwoStartNo, line1);
                lines.add(blockOneStartNo, line2);

               // System.out.println(lines);

                //Collections.swap(lines, blockOneStartNo1, blockTwoStartNo1);
            //}

        } else if (blockTwoEndNo > blockOneEndNo){
            for (int i = 0; i < blockTwoEndNo; i++ ){

                System.out.println(blockOneStartNo+i);
                System.out.println(blockTwoStartNo+i);
                int blockOneStartNo1 = blockOneStartNo+i;
                int blockTwoStartNo1 = blockTwoStartNo+i;

                //Collections.swap(lines, blockOneStartNo1, blockTwoStartNo1);
            }*/
        }


    @Override
    public String toString() {
        return "SwapBlockEdit{" +
                "blockOneStartNo=" + blockOneStartNo +
                ", blockOneEndNo=" + blockOneEndNo +
                ", blockTwoStartNo=" + blockTwoStartNo +
                ", blockTwoEndNo=" + blockTwoEndNo +
                '}';
    }
}

