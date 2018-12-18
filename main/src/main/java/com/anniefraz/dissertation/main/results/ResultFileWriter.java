package com.anniefraz.dissertation.main.results;

import com.opencsv.CSVWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

public class ResultFileWriter implements ResultWriter {

    private static final Logger LOG = LoggerFactory.getLogger(ResultFileWriter.class);

    @Override
    public void writeResult(Result result) {
        writeToFile(result);
        System.out.println("🐶🐨🎇");
    }

    private File createFile() {
        File file = new File("PatchExperiments.csv");
        LOG.info("File has been made");

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

    private void writeToFile(Result result) {

        // if (file == null){
        File file = createFile();
        //}else{
        try {
            // create FileWriter object with file as parameter
            FileWriter outputFile = new FileWriter(file,true);

            CSVWriter writer = new CSVWriter(outputFile);
            // add data to csv
            Date date = new Date();
            String[] data = { date.toString(), Integer.toString(result.getCurrentRep()), result.getPatch().getEdits().toString(), result.getOutputFileString(), Long.toString(result.getTime()), String.valueOf(result.isCompileSuccess()), String.valueOf(result.isPassed()), String.valueOf(result.getOpacitorMeasurement1()), String.valueOf(result.getOpacitorMeasurement2())}; //, compiledClass.toString() };
            writer.writeNext(data);
            // closing writer connection
            LOG.info("File has been written to");
            writer.close();
            outputFile.close();
        }
        catch (FileNotFoundException e){
            LOG.warn("Problem: {}", e);
        } catch (IOException e) {
            LOG.warn("Problem: {}", e);
        }
        //}



    }

}
