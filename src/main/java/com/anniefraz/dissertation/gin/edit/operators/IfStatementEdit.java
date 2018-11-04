package com.anniefraz.dissertation.gin.edit.operators;

import com.anniefraz.dissertation.gin.edit.SingleClassEdit;
import com.anniefraz.dissertation.gin.source.AnnaClass;
import com.anniefraz.dissertation.gin.source.AnnaPath;

import java.util.List;

public class IfStatementEdit extends SingleClassEdit {

    //TODO: Make this a test so you aren't testing it here

    int lineIndex = 11; // this is line: if (a > b) { in the Triangle.class
    int operatorIndex = 0;
    String operator = ""; // e.g. >
    String firstCondition = "";
    String secondCondition = "";

    public IfStatementEdit(int lineIndex, String operator, String firstCondition, String secondCondition, AnnaPath annaPath) {
        super(annaPath);
        this.lineIndex = lineIndex;
        this.operator = operator;
        this.firstCondition = firstCondition;
        this.secondCondition = secondCondition;
    }

    public int getOperatorIndex() {
        return operatorIndex;
    }

    public void setOperatorIndex(int operatorIndex) {
        this.operatorIndex = operatorIndex;
    }

    public String getOperator(String line){

        for (int i = 0; i < line.length(); i++) {
            String a_letter = Character.toString(line.charAt(0));

            if (a_letter.equals(">") || a_letter.equals("<") ){
                operator = a_letter;
                setOperatorIndex(i);
                int operatorIndex = getOperatorIndex();
            }
        }
        return operator;
    }


    public String changeOperator(String line, String operator){
        if (operator.equals(">")){
            operator.equals("<");
            line = line.substring(0,operatorIndex-1)+'<'+line.substring(operatorIndex);
        } else if (operator.equals("<")){
            operator.equals(">");
            line = line.substring(0,operatorIndex-1)+'>'+line.substring(operatorIndex);
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
