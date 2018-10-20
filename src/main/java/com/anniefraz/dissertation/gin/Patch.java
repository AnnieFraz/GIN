package com.anniefraz.dissertation.gin;

import com.anniefraz.dissertation.gin.edit.Edit;

import java.util.LinkedList;
import java.util.Random;

/**
 * Represents a patch, a potential set of changes to a sourcefile.
 */
public class Patch {

    LinkedList<Edit> edits;


    public void apply(){

}
public static Patch random(){

     Random random = new Random();
        int rnd = random.nextInt(10);

        Patch patch = new Patch();

        for (int i =0; i < rnd; i++){
            Edit edit = null;

            patch.edits.add(edit);
        }

return patch;
}

public void apply(Edit edit){

}


}

