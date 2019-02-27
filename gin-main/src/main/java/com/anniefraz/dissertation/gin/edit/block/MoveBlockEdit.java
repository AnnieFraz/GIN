package com.anniefraz.dissertation.gin.edit.block;

import com.anniefraz.dissertation.gin.edit.SingleClassEdit;
import com.anniefraz.dissertation.gin.source.AnnaClass;
import com.anniefraz.dissertation.gin.source.AnnaPath;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

//TODO: This needs to work
public class MoveBlockEdit extends SingleClassEdit {

   private static final Logger LOG = LoggerFactory.getLogger(MoveBlockEdit.class);

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

        if (endBlockInt < startBlockInt){
            LOG.error("The end block index is less than Start");
        } else {
            List<String> lines = annaClass.getLines();
            int size = endBlockInt - startBlockInt;
            for (int i = 0; i <= size; i++) {
                String line = lines.remove(startBlockInt );
                lines.add((newLocation -1 ) , line);
            }
        }
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
