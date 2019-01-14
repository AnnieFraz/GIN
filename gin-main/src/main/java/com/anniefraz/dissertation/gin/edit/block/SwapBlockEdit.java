package com.anniefraz.dissertation.gin.edit.block;

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

    public SwapBlockEdit(int blockOneStartNo, int blockOneEndNo, int blockTwoStartNo, int blockTwoEndNo, AnnaPath annaPath) {
        super(annaPath);
        this.blockOneStartNo = blockOneStartNo;
        this.blockOneEndNo = blockOneEndNo;
        this.blockTwoStartNo = blockTwoStartNo;
        this.blockTwoEndNo = blockTwoEndNo;
    }

    @Override
    protected void applyMethod(AnnaClass annaClass) {

        if ((blockTwoEndNo < blockTwoStartNo) || (blockOneEndNo < blockOneStartNo)) {
            Log.error("The end block index is less than Start");
            //return;
        }
        List<String> lines = annaClass.getLines();

        List<String> block1 = new ArrayList<>();
        List<String> block2 = new ArrayList<>();

        if (blockOneEndNo < blockOneStartNo) {
            Log.error("The end block index is less than Start");
        } else {
            int block1Size = blockOneEndNo - blockOneStartNo;
            for (int i = 0; i < block1Size; i++) {
                String line = lines.get(blockOneStartNo + i);
                block1.add(line);
            }
        }

        if (blockTwoEndNo < blockTwoStartNo) {
            Log.error("The end block index is less than Start");
        } else {
            int block2Size = blockTwoEndNo - blockTwoStartNo;
            for (int i = 0; i < block2Size; i++) {
                String line = lines.get(blockTwoStartNo + i);
                block2.add(line);
            }
        }

        if (block1.size() != block2.size()) {
            Log.error("Error, The Size of the Blocks are different");
        } else {

            for (int i = 0; i < block1.size() + 1; i++) {

                String line1 = lines.remove(blockOneStartNo + i);
                String line2 = lines.remove(blockTwoStartNo + i);
                lines.add(blockTwoStartNo + i, line1);
                lines.add(blockOneStartNo + i, line2);
            }
        }
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

