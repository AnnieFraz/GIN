package com.anniefraz.dissertation.gin.edit.block;

import com.anniefraz.dissertation.gin.edit.SingleClassEdit;
import com.anniefraz.dissertation.gin.source.AnnaClass;
import com.anniefraz.dissertation.gin.source.AnnaPath;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class RemoveBlockEdit extends SingleClassEdit {

    private static final Logger LOG = LoggerFactory.getLogger(RemoveBlockEdit.class);
    private final int startBlockInt;
    private final int endBlockInt;


    public RemoveBlockEdit(int startBlockInt, int endBlockInt, AnnaPath annaPath) {
        super(annaPath);
        this.startBlockInt = startBlockInt;
        this.endBlockInt = endBlockInt;

    }

    @Override
    public String toString() {
        return "RemoveBlockEdit{" +
                "startBlockInt=" + startBlockInt +
                ", endBlockInt=" + endBlockInt +
                '}';
    }

    @Override
    protected void applyMethod(AnnaClass annaClass){

        if (endBlockInt < startBlockInt){
            LOG.error("The end block index is less than Start");
        }

        List<String> lines = annaClass.getLines();
        for (int i = startBlockInt; i <= endBlockInt; i++){
            lines.remove(startBlockInt);
        }


    }


}
