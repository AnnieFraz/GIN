package com.anniefraz.dissertation.gin.application;


import com.anniefraz.dissertation.gin.patch.Patch;
//import com.anniefraz.dissertation.gin.testRunner.UnitTestResult;
import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.LinkedList;

public class Results {


    private int currentRep;
    private Patch patch;
    private String outputFileString;
    private long time;
    private Class<?> compiledClass;

    private File file = null;
    private boolean compileSuccess;
    //Add Test Runner Data
    private boolean passed;

    private double opacitorMeasurement1;
    private double opacitorMeasurement2;



    //Add Opacitor Data
    private String opacitorResults;

    //The constructor that has everything
    public Results(int currentRep, Patch patch, String outputFileString, long time, Class<?> compiledClass, boolean compileSuccess, boolean passed, double opacitorMeasurement1, double opacitorMeasurement2) {
        this.currentRep = currentRep;
        this.patch = patch;
        this.outputFileString = outputFileString;
        this.time = time;
        this.compiledClass = compiledClass;
        this.compileSuccess = compileSuccess;
    }

    //The Patch file Constructor
    public Results(int currentRep, Patch patch, String outputFileString, long time, Class<?> compiledClass, boolean compileSuccess) {
        this.currentRep = currentRep;
        this.patch = patch;
        this.outputFileString = outputFileString;
        this.time = time;
        this.compiledClass = compiledClass;
        this.compileSuccess = compileSuccess;
    }

    //The Test Runner Constructor
    public Results(boolean passed, Results results){
        this(results.currentRep, results.patch, results.outputFileString, results.time, results.compiledClass, results.compileSuccess, passed, results.getOpacitorMeasurement1(), results.getOpacitorMeasurement2());
        this.passed = passed;

    }

    //The Opacitor Constructur
    public Results(double opacitorMeasurement1, double opacitorMeasurement2, Results results) {
        this(results.currentRep, results.patch, results.outputFileString, results.time, results.compiledClass, results.compileSuccess, results.getPassed(), opacitorMeasurement1, opacitorMeasurement2);
        this.opacitorMeasurement1 = opacitorMeasurement1;
        this.opacitorMeasurement2 = opacitorMeasurement2;

    }


    private File createFile() {
        file = new File("PatchExperiments.csv");
        System.out.println("file has been made");

        try {
            // create FileWriter object with file as parameter
            FileWriter outputfile = new FileWriter(file, true);

            CSVWriter writer = new CSVWriter(outputfile);
            // add data to csv
            Date date = new Date();
            String[] header = {"Date", "Repetitions", "Patch", "Output", "Time", "Compiled", "Passed Unit Tests?", "Opacitor measurement 1", "Opacitor measurement 2"};

            writer.writeNext(header);
            // closing writer connection
            writer.close();
            outputfile.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }

        public void writeToFile() throws FileNotFoundException {

       // if (file == null){
           file = createFile();
        //}else{
            try {
                // create FileWriter object with file as parameter
                FileWriter outputFile = new FileWriter(file,true);

                CSVWriter writer = new CSVWriter(outputFile);
                // add data to csv
                Date date = new Date();
                String[] data = { date.toString(), Integer.toString(getCurrentRep()), getPatch().getEdits().toString(), getOutputFileString(), Long.toString(getTime()), String.valueOf(getCompileSuccess()), String.valueOf(getPassed()), String.valueOf(getOpacitorMeasurement1()), String.valueOf(getOpacitorMeasurement2())}; //, compiledClass.toString() };
                writer.writeNext(data);
                // closing writer connection
                System.out.println("File has been written to");
                writer.close();
                outputFile.close();
            }
            catch (Exception e){

            }
        //}



    }
    public int getCurrentRep() {
        return currentRep;
    }

    public void setCurrentRep(int currentRep) {
        this.currentRep = currentRep;
    }

    public Patch getPatch() {
        return patch;
    }

    public void setPatch(Patch patch) {
        this.patch = patch;
    }

    public String getOutputFileString() {
        return outputFileString;
    }

    public void setOutputFileString(String outputFileString) {
        this.outputFileString = outputFileString;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public Class<?> getCompiledClass() {
        return compiledClass;
    }

    public void setCompiledClass(Class<?> compiledClass) {
        this.compiledClass = compiledClass;
    }

    public void setCompileSuccess(boolean compileSuccess) {
        this.compileSuccess = compileSuccess;
    }

    public boolean getCompileSuccess() {
        return compileSuccess;
    }

    public void setPassedTests(Boolean passed) {
    this.passed = passed;

    }
    public boolean getPassed(){
    return passed;
    }

    public double getOpacitorMeasurement1() {
        return opacitorMeasurement1;
    }

    public void setOpacitorMeasurement1(double opacitorMeasurement1) {
        this.opacitorMeasurement1 = opacitorMeasurement1;
    }

    public double getOpacitorMeasurement2() {
        return opacitorMeasurement2;
    }

    public void setOpacitorMeasurement2(double opacitorMeasurement2) {
        this.opacitorMeasurement2 = opacitorMeasurement2;
    }
}
