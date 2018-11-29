package com.anniefraz.dissertation.gin.application;

import com.anniefraz.dissertation.gin.application.config.ApplicationConfig;
import com.anniefraz.dissertation.gin.patch.Patch;
import com.anniefraz.dissertation.gin.patch.PatchFactory;
import com.anniefraz.dissertation.gin.source.AnnaClass;
import com.anniefraz.dissertation.gin.source.AnnaPath;
import com.anniefraz.dissertation.gin.source.Source;
import com.anniefraz.dissertation.gin.source.SourceFactory;
import com.anniefraz.dissertation.gin.test.TestRunner;
import com.opencsv.CSVWriter;
import org.mdkt.compiler.InMemoryJavaCompiler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.*;
import java.util.Date;
import java.util.List;
import java.util.Random;

//import opacitor.Opacitor;

public class ApplicationMain {

    private static int REPS = 10;

    private static int editNumberSeed = 4;

    private static boolean compileSuccess;

    private static int noofEditsNoRandom = 1;

    //private Opacitor opacitor;

    private TestRunner testRunner;

    static Logger LOG = LoggerFactory.getLogger(ApplicationMain.class);

    static String  outputfileString = "PatchExperiments.csv" ;


    public static void main(String[] args) throws IOException, Exception {
        //ApplicationContext allows to spring to properly interject beans into the application
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(ApplicationConfig.class);

        //Configures/gets the beans from the factories
        PatchFactory patchFactory = applicationContext.getBean(PatchFactory.class);
        SourceFactory sourceFactory = applicationContext.getBean(SourceFactory.class);

        //Gets the file we want to apply edits
        AnnaPath annaPath = AnnaPath.getBuilder().addPackage("example").setClassName("Triangle").build();

        //Gets the source file from that anna path
        Source source = sourceFactory.getSourceFromAnnaPath(annaPath);

        //random number of edits generator. Maximum is 4
        //Random random = new Random();
       // int noOfEdits = random.nextInt(editNumberSeed) + 1;
       // LOG.info("Number of Edits: " + noOfEdits);

        int noOfEdits = noofEditsNoRandom;

        FileWriter outputfile = new FileWriter(outputfileString,true);

        // create CSVWriter object filewriter object as parameter
        CSVWriter writer = new CSVWriter(outputfile);

        // adding header to csv
        String[] header = { "Date", "Repatitions", "Patch", "Output", "Time" };
        writer.writeNext(header);

        compile((Closeable) applicationContext, patchFactory, source, noOfEdits);

    }

    private static void compile(Closeable applicationContext, PatchFactory patchFactory, Source source, int noOfEdits) throws IOException {
        int i = 0;
        while (i < REPS) {

            //Creation of a patch with many different edits
            Patch patch = patchFactory.getPatchForSourceWithEdits(source, noOfEdits);

            //This gives the source with the edits applied so 'changed code'
            Source outputSource = patch.getOutputSource();
            LOG.info("Output source: " + outputSource);

            applicationContext.close();

            LOG.info("Which edits: " + patch.getEdits());
            System.out.println("which edits: " + patch.getEdits());

            //This needs to be in seperate method. will show to sandy tho - 12/11
            Class<?> compiledClass = null;

            List<AnnaClass> classList = outputSource.getAnnaClasses();

            //Getting the right class.
            //Maybe do as a loop for multiple classes
            AnnaClass ac = classList.get(0);

            //Changing into a string so it can be put into in memory java compiler
            String outputfileString = String.join(System.lineSeparator(), ac.getLines());


            compiledClass = InMemoryJavaCompiler.newInstance().compile("Triangle", outputfileString);

            //Here I want to call the testRunner
            //Here I want to call the opacitor

            long time = 0;

            if (compiledClass != null) {

                System.out.println("Didn't compile");
                System.out.println("time:" + System.currentTimeMillis());
                time = System.currentTimeMillis();
            } else {
                System.out.println("Compiled Successfully");
                System.out.println("time:" + System.currentTimeMillis());
                time = System.currentTimeMillis();
                i++;
            }

            writeToFile(i, patch, outputfileString, time);

        }
    }

    private static void writeToFile(int i, Patch patch, String outputfileString, long time) throws FileNotFoundException {
        /*PrintWriter pw = new PrintWriter(new File("PatchExperiments.csv"));
        StringBuilder fileData = new StringBuilder();


        fileData.append(',');
        fileData.append(',');
        fileData.append(i);
        fileData.append(outputfileString);
        fileData.append(',');
        fileData.append(patch.getEdits());
        fileData.append(',');
        fileData.append(patch.getEdits().toString());

        fileData.append('\n');

        pw.write(fileData.toString());
        pw.close();*/

        File file = new File("PatchExperiments.csv");

        try {
            // create FileWriter object with file as parameter
            FileWriter outputfile = new FileWriter(outputfileString,true);

            CSVWriter writer = new CSVWriter(outputfile);
            // add data to csv
            Date date = new Date();
            String[] data = { date.toString(), Integer.toString(i), patch.getEdits().toString(), outputfileString, Long.toString(time)};

            writer.writeNext(data);
            // closing writer connection
            writer.close();
            outputfile.close();
        }
        catch (Exception e){

        }
    }





    private void sendToTestRunner() {
        //Here I want when I have made a new patch for it to go to the test runner automatically

        //testRunner =  new TestRunner(exampleDir, className, TestConfiguration.TEST_RESOURCES_DIR, tests);

    }

    private void sendToOpacitor(String outputString) {

        /*
        String testSrcDir = "C:/Users/user/IdeaProjects/AnnaGin/Opacitor/test_external_dir/src";
        String testBinDir = "C:/Users/user/IdeaProjects/AnnaGin/Opacitor/test_external_dir/bin";
        String testReplacementCode = outputString;

        Opacitor opacitor = new Opacitor.OpacitorBuilder("test", "TestClass2", new String[]{})
                .srcDirectory(testSrcDir)
                .binDirectory(testBinDir)
                .measurementType(MeasurementType.CODE_LENGTH)
                .performInitialCompilation(true)
                .goalDirection(GoalDirection.MINIMISING)
                .build();
                */
    }

}
