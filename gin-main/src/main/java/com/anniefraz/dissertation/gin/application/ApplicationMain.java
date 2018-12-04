package com.anniefraz.dissertation.gin.application;

import com.anniefraz.dissertation.gin.application.config.ApplicationConfig;
import com.anniefraz.dissertation.gin.patch.Patch;
import com.anniefraz.dissertation.gin.patch.PatchFactory;
import com.anniefraz.dissertation.gin.source.AnnaClass;
import com.anniefraz.dissertation.gin.source.AnnaPath;
import com.anniefraz.dissertation.gin.source.Source;
import com.anniefraz.dissertation.gin.source.SourceFactory;
import org.mdkt.compiler.InMemoryJavaCompiler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.Closeable;
import java.io.IOException;
import java.util.List;

//import opacitor.Opacitor;

public class ApplicationMain {

    static Logger LOG = LoggerFactory.getLogger(ApplicationMain.class);
    private static int REPS = 100;
    private static int editNumberSeed = 4;
    private static boolean compileSuccess;

    //private Opacitor opacitor;
    private static int noofEditsNoRandom = 1;
    private static Results results;


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

        compile((Closeable) applicationContext, patchFactory, source, noOfEdits);

    }

    private static void compile(Closeable applicationContext, PatchFactory patchFactory, Source source, int noOfEdits) throws Exception {
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

            //Getting the right class.
            //Maybe do as a loop for multiple classes
            List<AnnaClass> classList = outputSource.getAnnaClasses();
            AnnaClass ac = classList.get(0);

            //Changing into a string so it can be put into in memory java compiler
            String outputfileString = String.join(System.lineSeparator(), ac.getLines());


            compiledClass = InMemoryJavaCompiler.newInstance().compile("Triangle", outputfileString);

            //Here I want to call the testRunner
            //Here I want to call the opacitor

            long time = 0;

            if (compiledClass == null) {

                System.out.println("Didn't compile");
                System.out.println("time:" + System.currentTimeMillis());
                time = System.currentTimeMillis();
            } else {
                System.out.println("Compiled Successfully");
                System.out.println("time:" + System.currentTimeMillis());
                time = System.currentTimeMillis();
                i++;
            }

            //System.out.println(outputfileString);
            results.setCurrentRep(i);
            results.setPatch(patch);
            results.setOutputFileString(outputfileString);
            results.setTime(time);
            results.setCompiledClass(compiledClass);
            results.writeToFile();


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
