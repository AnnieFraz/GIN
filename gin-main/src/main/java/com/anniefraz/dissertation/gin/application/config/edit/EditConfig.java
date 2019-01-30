package com.anniefraz.dissertation.gin.application.config.edit;

import com.anniefraz.dissertation.gin.edit.Edit;
import com.anniefraz.dissertation.gin.edit.block.MoveBlockEdit;
import com.anniefraz.dissertation.gin.edit.block.RemoveBlockEdit;
import com.anniefraz.dissertation.gin.edit.block.SwapBlockEdit;
import com.anniefraz.dissertation.gin.edit.line.InsertLineEdit;
import com.anniefraz.dissertation.gin.edit.line.MoveLineEdit;
import com.anniefraz.dissertation.gin.edit.line.RemoveLineEdit;
import com.anniefraz.dissertation.gin.edit.line.SwapLineEdit;
import com.anniefraz.dissertation.gin.edit.noEdit.NoEditEdit;
import com.anniefraz.dissertation.gin.edit.operators.IfStatementEdit;
import com.anniefraz.dissertation.gin.edit.statement.InsertBreakEdit;
import com.anniefraz.dissertation.gin.source.AnnaClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Function;

@Configuration
public class EditConfig {

    @Bean
    //TODO: Need to figure out what to be inserted
    //USE THIS EDIT. If you want it to compile succesfully guarenteed everytime
    public Function<AnnaClass, Edit> insertLineEdit(){
        return anAnnaClass ->{
            int size = anAnnaClass.getLines().size();
            int whichLine = Double.valueOf(Math.floor(Math.random() * size)).intValue();
            //int whichLine = 6; //This was for the bubble sort tests
            return new InsertLineEdit(whichLine,"//this is a comment" , anAnnaClass.getPath());
        };
    }

    @Bean
    public Function<AnnaClass, Edit> removeLineEdit() {
        return anAnnaClass -> {
            int size = anAnnaClass.getLines().size();
            int whichLine = Double.valueOf(Math.floor(Math.random() * size)).intValue();
            return new RemoveLineEdit(whichLine, anAnnaClass.getPath());
        };
    }

    @Bean
    public Function<AnnaClass, Edit> insertBreakEdit(){
        return anAnnaClass -> {
            int size = anAnnaClass.getLines().size();
            int whichLine = Double.valueOf(Math.floor(Math.random() * size)).intValue();
            return new InsertBreakEdit(whichLine,"break;" , anAnnaClass.getPath());

        };
    }

    @Bean
    public Function<AnnaClass, Edit> swapLineEdit(){
        return anAnnaClass -> {
            int size = anAnnaClass.getLines().size();
            int whichFirstLine = Double.valueOf(Math.floor(Math.random() * size)).intValue();
            int whichSecondLine = Double.valueOf(Math.floor(Math.random() * size)).intValue();
            return new SwapLineEdit(whichFirstLine, whichSecondLine, anAnnaClass.getPath());
        };
    }

    @Bean
    public Function<AnnaClass, Edit> moveLineEdit(){
        return anAnnaClass -> {
            int size = anAnnaClass.getLines().size();
            int sourceLine = Double.valueOf(Math.floor(Math.random() * size)).intValue();
            int destinationLine = Double.valueOf(Math.floor(Math.random() * size)).intValue();
            return new MoveLineEdit(sourceLine, destinationLine, anAnnaClass.getPath());
        };
    }


    @Bean
    //TODO: GET THIS EDIT TO SWAP CONDITIONS
    public Function<AnnaClass, Edit> operatorEdit(){
        return anAnnaClass -> {
            int size = anAnnaClass.getLines().size();
            int whichLine = Double.valueOf(Math.floor(Math.random() * size)).intValue();
            return new IfStatementEdit(whichLine, anAnnaClass.getPath());
        };
    }
/*
    @Bean
    //TODO: Double checks this works
    public Function<AnnaClass, Edit> removeBlockEdit(){
        return anAnnaClass -> {
            int size = anAnnaClass.getLines().size();
            int startInt = Double.valueOf(Math.floor(Math.random() * size)).intValue();
            int endInt = Double.valueOf(Math.floor(Math.random() * size)).intValue();
            return new RemoveBlockEdit(startInt, endInt, anAnnaClass.getPath());
        };
    }


    @Bean
    public Function<AnnaClass, Edit> moveBlockEdit(){
        return anAnnaClass -> {
            int size = anAnnaClass.getLines().size();
            int startInt = Double.valueOf(Math.floor(Math.random() * size)).intValue();
            int endInt = Double.valueOf(Math.floor(Math.random() * size)).intValue();
            int newLocation = Double.valueOf(Math.floor(Math.random() * size)).intValue();
            return new MoveBlockEdit(startInt, endInt, newLocation, anAnnaClass.getPath());
        };
    }


    @Bean
    public Function<AnnaClass, Edit> swapBlockEdit(){
        return anAnnaClass -> {
            int size = anAnnaClass.getLines().size();
            int blockOneStartNo = Double.valueOf(Math.floor(Math.random() * size)).intValue();
            int blockOneEndNo = Double.valueOf(Math.floor(Math.random() * size)).intValue();
            int blockTwoStartNo = Double.valueOf(Math.floor(Math.random() * size)).intValue();
            int blockTwoEndNo = Double.valueOf(Math.floor(Math.random() * size)).intValue();
            return new SwapBlockEdit(blockOneStartNo,blockOneEndNo,blockTwoStartNo,blockTwoEndNo, anAnnaClass.getPath());
        };
    }

*/
    @Bean
    public Function<AnnaClass,  Edit> noEditEdit(){
        return anAnnaClass -> {
            return new NoEditEdit(anAnnaClass.getPath());
        };
    }

}
