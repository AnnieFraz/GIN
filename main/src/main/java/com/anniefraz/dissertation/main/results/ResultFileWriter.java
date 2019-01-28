package com.anniefraz.dissertation.main.results;

import com.opencsv.CSVWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Date;

public class ResultFileWriter implements ResultWriter {

    private static final Logger LOG = LoggerFactory.getLogger(ResultFileWriter.class);

    private CSVWriter csvWriter;

    public ResultFileWriter(String filename) throws IOException {
        File file = new File(filename + ".csv");
        boolean fileExists = !file.exists();

        FileWriter fileWriter = new FileWriter(file, true);

        csvWriter = new CSVWriter(fileWriter);
        if (fileExists){
            String[] header = {"Date", "Repetitions", "Patch", "Output", "Time", "Compiled", "Passed Unit Tests?", "Opacitor measurement 1", "Opacitor measurement 2"};
            csvWriter.writeNext(header);
        }
    }

    public void writeResult(Result result) {
        System.out.println("🎇🎇🎇🎇NEXT REP🎇🎇🎇🎇");
        LOG.info("Writing Result to file");
        LocalDateTime date = LocalDateTime.now();
        String[] data = {
                date.toString(),
                Integer.toString(result.getCurrentRep()),
                result.getPatch().getEdits().toString(),
                result.getOutputFileString(),
                Long.toString(result.getTime()),
                String.valueOf(result.isCompileSuccess()),
                String.valueOf(result.isPassed()),
                String.valueOf(result.getOpacitorMeasurement1()),
                String.valueOf(result.getOpacitorMeasurement2())
        }; //, compiledClass.toString() };
        csvWriter.writeNext(data);
    }

    @Override
    public void close() throws IOException {
        csvWriter.flush();
        csvWriter.close();
    }

}
