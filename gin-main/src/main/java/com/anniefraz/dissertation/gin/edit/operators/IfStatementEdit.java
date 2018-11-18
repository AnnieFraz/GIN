package com.anniefraz.dissertation.gin.edit.operators;

import com.anniefraz.dissertation.gin.edit.SingleClassEdit;
import com.anniefraz.dissertation.gin.source.AnnaClass;
import com.anniefraz.dissertation.gin.source.AnnaPath;

import java.util.List;

public class IfStatementEdit extends SingleClassEdit {

    private static final String LESS_THAN = "<";
    private static final String GREATER_THAN = ">";


    private final int lineIndex;

    public IfStatementEdit(int lineIndex, AnnaPath annaPath) {
        super(annaPath);
        this.lineIndex = lineIndex;
    }


    String getOperator(String line){
        int lessThan = line.indexOf(LESS_THAN);
        int greaterThan = line.indexOf(GREATER_THAN);
        if (greaterThan==-1 && lessThan==-1){
            System.out.println("oh no!");
        }
        if (lessThan==-1){
            lessThan=Integer.MAX_VALUE;
        }
        if (greaterThan==-1){
            greaterThan=Integer.MAX_VALUE;
        }

        return lessThan < greaterThan ? LESS_THAN : GREATER_THAN;
    }


    String changeOperator(String line, String operator){
        //get brackets
        //get index
        //
        int operatorIndex = line.indexOf(operator);
        if (operator.equals(">")){
            operator.equals("<");
            line = line.substring(0,operatorIndex)+'<'+line.substring(operatorIndex+1);
        } else if (operator.equals("<")){
            operator.equals(">");
            line = line.substring(0,operatorIndex)+'>'+line.substring(operatorIndex+1);
        }
        return line;
    }

    @Override
    public void applyMethod(AnnaClass annaClass){

        List<String> lines = annaClass.getLines();
        String operatorApply = "";
        String line = "";

        for (int i = 0; i < lines.size(); i++){
             line = lines.get(i);
             lines.remove(line);
             line = changeOperator(line, operatorApply);
             lines.add(line);
             operatorApply = getOperator(line);

        }
    }

}
