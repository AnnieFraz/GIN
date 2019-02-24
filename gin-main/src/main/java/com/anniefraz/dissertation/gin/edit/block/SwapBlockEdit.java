package com.anniefraz.dissertation.gin.edit.block;

import com.anniefraz.dissertation.gin.edit.SingleClassEdit;
import com.anniefraz.dissertation.gin.source.AnnaClass;
import com.anniefraz.dissertation.gin.source.AnnaPath;
import com.github.javaparser.utils.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class SwapBlockEdit extends SingleClassEdit {

    private static final Logger LOG = LoggerFactory.getLogger(SwapBlockEdit.class);

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
            LOG.error("The end block index is less than Start");
        }
        List<String> lines = annaClass.getLines();

        List<String> block1 = new ArrayList<>();
        List<String> block2 = new ArrayList<>();

        if (block1.size() != block2.size()) {
            Log.error("Error, The Size of the Blocks are different");
        } else {

            for (int i = 0; i <= (block1.size() + 2 ); i++) {

                String line1 = lines.remove(blockOneStartNo + i);
                String line2 = lines.remove((blockTwoStartNo -1) + i);
                lines.add((blockTwoStartNo -1)+ i, line1);
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

