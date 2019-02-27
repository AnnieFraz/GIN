package com.anniefraz.dissertation.main.csvResults;

import com.anniefraz.dissertation.gin.patch.Neighbour;
import com.anniefraz.dissertation.gin.patch.Offspring;
import com.anniefraz.dissertation.gin.patch.Patch;
import com.opencsv.CSVWriter;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Closeable;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

public class CSVResultFileWriter implements CSVResultWriter {

    private static final Logger LOG = LoggerFactory.getLogger(CSVResultFileWriter.class);
    private CSVWriter csvWriter;

    public CSVResultFileWriter(String filename) throws IOException {
        File file = new File(filename + ".csv");
        boolean fileExists = !file.exists();
        FileWriter fileWriter = new FileWriter(file, true);

        csvWriter = new CSVWriter(fileWriter);
        if (fileExists){
            String[] headers = {"Date", "Iteration", "Population Size", "Population", "Offspring", "Neighbours"};
            csvWriter.writeNext(headers);
        }

    }

    @Override
    public void writeResult(CSVResult csvResult) {
        LOG.info("Writing to file");
        LocalDateTime dateTime = LocalDateTime.now();
        String[] data = {
                "Date:" + dateTime.toString(),
                "Iteration: " +Integer.toString(csvResult.getIteration()),
                "Population Size: " + Integer.toString(csvResult.getPopulationSize())
        };
        List < Patch > patches = csvResult.getPopulation();
        String[] patchData = new String[patches.size()];
        for (int i = 0; i < patches.size(); i++) {
            patchData[i] = patches.get(i).toString();
        }
        List <Offspring> offsprings = csvResult.getOffspring();
        String[] offspringData = new String[offsprings.size()];
        for (int i = 0; i < offsprings.size(); i++) {
            offspringData[i] = offsprings.get(i).toString();
        }
        List <Neighbour> neighbours = csvResult.getNeighbour();
        String[] neighbourData  = new String[neighbours.size()];
        for (int i = 0; i < neighbours.size(); i++) {
            neighbourData[i] = neighbours.get(i).toString();
        }

        //String[] all = (String[]) ArrayUtils.addAll(data, patchData, offspringData, neighbourData);
       //csvWriter.writeNext(all);
        String[] empty = new String[1];
        empty[0] = "";
        csvWriter.writeNext(data);
        csvWriter.writeNext(patchData);
        csvWriter.writeNext(offspringData);
        csvWriter.writeNext(neighbourData);
        csvWriter.writeNext(empty);
    }

    @Override
    public void close() throws IOException {
        csvWriter.flush();
        csvWriter.close();
    }
}
