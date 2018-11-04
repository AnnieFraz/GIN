package com.anniefraz.dissertation.gin.patch;

import com.anniefraz.dissertation.gin.edit.Edit;
import com.anniefraz.dissertation.gin.edit.line.InsertLineEdit;
import com.anniefraz.dissertation.gin.edit.line.MoveLineEdit;
import com.anniefraz.dissertation.gin.edit.line.RemoveLineEdit;
import com.anniefraz.dissertation.gin.edit.line.SwapLineEdit;
import com.anniefraz.dissertation.gin.source.AnnaClass;
import com.anniefraz.dissertation.gin.source.AnnaPath;
import jdk.internal.instrumentation.Logger;
import org.apache.commons.io.FileUtils;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;


public class Patch {

    LinkedList<Edit> edits = new LinkedList<>();

    LinkedList<String> lines = new LinkedList<>();

    List<Edit> EditTypes;

    AnnaPath annaPath;


    public Patch(AnnaPath annaPath) {
        this.annaPath = annaPath;
    }

    public void addEdits(Edit edit) {
        this.edits.add(edit);
    }

    public int getSize() {
        return this.edits.size();
    }

    public void removeEdit(int index) {
        this.edits.remove(index);
    }


    //TODO: this
    public String apply() {

        AnnaClass annaClass = new AnnaClass(annaPath, lines);

        boolean appliedOK = true;

        for (Edit edit : edits) {
            edit.apply(annaClass);
            appliedOK = true;
        }

        return "";

    }


    //TODO: This
    public Edit randomEdit(List<Edit> EditTypes) {

        Edit edit = null;

        Random rand = new Random();

        int editType = rand.nextInt(4);

        switch (editType) {
            case (0):
                edit = new RemoveLineEdit(0, annaPath);
                break;
            case (1):
                edit = new InsertLineEdit(0, "", annaPath);
                break;
            case (2):
                edit = new MoveLineEdit(0, 1, annaPath);
                break;
            case (3):
                edit = new SwapLineEdit(0, 1, annaPath);
                break;

        }
        return edit;
    }


    public void writePatchedProgramToFile(String fileName) {
        String sourceFile = this.apply();
        File file = new File(fileName);

        try {
            FileUtils.writeStringToFile(file, sourceFile);
        } catch (Exception e) {
            System.out.println(e);
            System.exit(-1);
        }
    }

    @Override
    public String toString(){
        String description = "";
        for (Edit edit : edits){
            description = description + edit.toString();
        }
        return description;
    }
}

