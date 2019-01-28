package com.anniefraz.dissertation.experiments.results;

import com.opencsv.CSVWriter;
import org.joda.time.LocalDateTime;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

public class ResultBSFileWriter implements ResultBSWriter {


    private CSVWriter csvWriter;

    public ResultBSFileWriter(String outputFile) throws IOException {
        File file = new File(outputFile + ".csv");
        boolean fileExists = !file.exists();

        FileWriter outputfile = new FileWriter(file, true);

        csvWriter = new CSVWriter(outputfile);
        // add data to csv
        if (fileExists) {
            String[] header = {"Date", "Repetition", "Iterative or Recursive", "Opacitor 1", "Opacitor 2", "array", "outputFile", "seed", "Array Length"};
            csvWriter.writeNext(header);
        }
    }


    @Override
    public void writeResult(ResultBS resultBS) {
        System.out.println("Writing: " + resultBS);
        LocalDateTime date = LocalDateTime.now();
        String[] data = {
                date.toString(),
                Integer.toString(resultBS.getCurrentRep()),
                resultBS.getBsType().getClassName(),
                Double.toString(resultBS.getOpacitorMeasurement1()),
                Double.toString(resultBS.getOpacitorMeasurement2()),
                Arrays.toString(resultBS.getArray()),
                Integer.toString(resultBS.getSeed()),
                Integer.toString(resultBS.getArraySize())
        };
        csvWriter.writeNext(data);
    }

    @Override
    public void close() throws IOException {
        csvWriter.flush();
        csvWriter.close();
    }


}
