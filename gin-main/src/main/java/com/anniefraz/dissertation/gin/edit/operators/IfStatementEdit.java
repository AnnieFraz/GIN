package com.anniefraz.dissertation.gin.edit.operators;

import com.anniefraz.dissertation.gin.edit.SingleClassEdit;
import com.anniefraz.dissertation.gin.source.AnnaClass;
import com.anniefraz.dissertation.gin.source.AnnaPath;

import java.util.List;

public class IfStatementEdit extends SingleClassEdit {

    private static final String LESS_THAN = "<";
    private static final String GREATER_THAN = ">";

    private static final String FIRST_CONDITION = "";
    private static final String SECOND_CONDITION = "";

    private final int lineIndex;

    public IfStatementEdit(int lineIndex, AnnaPath annaPath) {
        super(annaPath);
        this.lineIndex = lineIndex;
    }

    public static String findFirstCondition(String line){
        int operator = 5;
        if (line.contains(LESS_THAN)) {
            operator = line.indexOf(LESS_THAN);
        } else if(line.contains(GREATER_THAN)){
            operator = line.indexOf(GREATER_THAN);
        }
        int firdt = line.indexOf("(");
        String firstCondition = line.substring(firdt+1, operator);
        return firstCondition;
    }

    public static String findSecondCondition(String line){
        int operator= 5;
        if (line.contains(LESS_THAN)) {
            operator = line.indexOf(LESS_THAN);
        } else if(line.contains(GREATER_THAN)){
            operator = line.indexOf(GREATER_THAN);
        }
        int end = line.indexOf(")");
        String secondCondition = line.substring(operator+1, end);
        return secondCondition;
    }

    public  String swapConditions(String line, String operatorApply){
        String firstCondition = findFirstCondition(line);
        String secondCondition = findSecondCondition(line);
        System.out.println(firstCondition);
        System.out.println(secondCondition);
        String newLine = ""+changeOperator(line, operatorApply)+"";
         return newLine;

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


    String changeLine(String line, String operator){
        operator = getOperator(line);
        String first = findFirstCondition(line);
        first = first.replaceAll("\\s+","");

        String second = findSecondCondition(line);
        second = second.replaceAll("\\s+","");


        String operatorChar = "";
        int operatorIndex =line.indexOf(operator);
        if (operator.equals(">")){
            operator.equals("<");
            String line2 = line.substring(0,operatorIndex)+'<'+line.substring(operatorIndex+1);
            operatorChar.equals("<");
            operatorChar = "<";

            line = "        if ("+second + ""+ operatorChar+ ""+ first+"){";
        } else if (operator.equals("<")){
            operator.equals(">");
            operatorChar.equals(">");
            String line2 = line.substring(0,operatorIndex)+'>'+line.substring(operatorIndex+1);

            line = "        if ("+second + ""+ operatorChar+ ""+ first+"){";
        }


       line = "        if ("+second + " "+ operatorChar+ " "+ first+") {";

        return line;
    }

    String changeOperator(String line, String operator){
        int operatorIndex =line.indexOf(operator);
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
            if (line.contains("if (")) { //To verifiy that the line is actually an if statement
                 lines.remove(line);
                operatorApply = getOperator(line);
                System.out.println(operatorApply);
                changeOperator(line,operatorApply);
                 String newline = changeLine(line, operatorApply);
            lines.add(line);
                System.out.println(newline);
                System.out.println(lines);
                //operatorApply = getOperator(line);
            }else{
                continue;
             }

        }
    }

    @Override
    public String toString() {
        return "IfStatementEdit{" +
                "lineIndex=" + lineIndex +
                ", annaPath=" + annaPath +
                '}';
    }
}
