package com.anniefraz.dissertation.gin.application.config.edit;

import com.anniefraz.dissertation.gin.edit.Edit;
import com.anniefraz.dissertation.gin.edit.line.InsertLineEdit;
import com.anniefraz.dissertation.gin.edit.line.MoveLineEdit;
import com.anniefraz.dissertation.gin.edit.line.RemoveLineEdit;
import com.anniefraz.dissertation.gin.edit.line.SwapLineEdit;
import com.anniefraz.dissertation.gin.edit.operators.IfStatementEdit;
import com.anniefraz.dissertation.gin.source.AnnaClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Function;

@Configuration
public class EditConfig {

    @Bean
    public Function<AnnaClass, Edit> removeLineEdit() {
        return anAnnaClass -> {
            int size = anAnnaClass.getLines().size();
            int whichLine = Double.valueOf(Math.floor(Math.random() * size)).intValue();
            return new RemoveLineEdit(whichLine, anAnnaClass.getPath());
        };
    }

    @Bean
    //TODO: Need to figure out what to be inserted
    public Function<AnnaClass, Edit> insertLineedit(){
        return anAnnaClass ->{
            int size = anAnnaClass.getLines().size();
            int whichLine = Double.valueOf(Math.floor(Math.random() * size)).intValue();
            return new InsertLineEdit(whichLine,"Thread.sleep(100)" , anAnnaClass.getPath());
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

    /*
    @Bean
    public Function<AnnaClass, Edit> operatorEdit(){
        return anAnnaClass -> {
            int size = anAnnaClass.getLines().size();
            int whichLine = Double.valueOf(Math.floor(Math.random() * size)).intValue();
            return new IfStatementEdit(whichLine, anAnnaClass.getPath());
        };
    }*/

}
