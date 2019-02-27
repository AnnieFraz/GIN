package com.anniefraz.dissertation.main.algorithms;

import com.anniefraz.dissertation.algorithms.GAs.main.GA;
import com.anniefraz.dissertation.algorithms.HillClimbing.HillClimbing;
import com.anniefraz.dissertation.gin.application.config.ApplicationConfig;
import com.anniefraz.dissertation.gin.patch.Patch;
import com.anniefraz.dissertation.gin.patch.PatchFactory;
import com.anniefraz.dissertation.gin.source.AnnaPath;
import com.anniefraz.dissertation.gin.source.Source;
import com.anniefraz.dissertation.gin.source.SourceFactory;
import com.anniefraz.dissertation.main.csvResults.CSVResultFileWriter;
import com.anniefraz.dissertation.main.csvResults.CSVResultWriter;
import com.anniefraz.dissertation.main.input.UserInput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.Closeable;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class HCMain {

    private static final Logger LOG = LoggerFactory.getLogger(HCMain.class);
    static ApplicationContext APPLICATIONCONTEXT;
    private static final String PATHNAME = "C:\\Users\\user\\IdeaProjects\\Anna-Gin\\test-runner\\examples\\unittests";
    private static int ITERATIONS = 1;
    private static int editNumberSeed = 4;
    private static int noofEditsNoRandom = 1;
    private static boolean compileSuccess;
    //private static final String PATHNAME = "/Users/annarasburn/Documents/gin/AnnaGin/test-runner/examples/unittests/";
    private static CSVResultWriter RESULTWRITER;
    private static GA genetic; // = new GA();

    static {
        try {
            RESULTWRITER = new CSVResultFileWriter("Hill-Climber");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {
        if (args.length < 4) {
            LOG.error("There are not enough Parameters");
        } else {
            UserInput userInput = UserInput.getBuilder()
                    .setClassFileName(args[0])
                    .setTestFileName(args[1])
                    .setPackageName(args[2])
                    .setIterations(Integer.parseInt(args[3]))
                    .build();
            LOG.info("Recieved User Input: {}", userInput);
            initialise(userInput);
        }
    }

    /**
     * @param userInput
     * @throws Exception
     */
    private static void initialise(UserInput userInput) throws Exception {
        APPLICATIONCONTEXT = new AnnotationConfigApplicationContext(ApplicationConfig.class);
        PatchFactory patchFactory = APPLICATIONCONTEXT.getBean(PatchFactory.class);
        SourceFactory sourceFactory = new SourceFactory(Paths.get(PATHNAME));
        AnnaPath annaPath = AnnaPath.getBuilder().addPackage(userInput.getPackageName()).setClassName(userInput.getClassFileName()).build();
        Source source = sourceFactory.getSourceFromAnnaPath(annaPath);
        HillClimbing hillClimbing = new HillClimbing(userInput, patchFactory, source);
        getHillClimbing(hillClimbing, userInput);
        ((Closeable) APPLICATIONCONTEXT).close();
    }

    private static HillClimbing getHillClimbing(HillClimbing hillClimbing, UserInput userInput){

        PatchFactory patchFactory = hillClimbing.getPatchFactory();
        Source source = hillClimbing.getSource();
        for (int i = 0; i < userInput.getIterations() ; i++) {
            System.out.println();
            LOG.info("CURRENT ITERATION:{}", i);
            hillClimbing.generateABunchOfPatches(patchFactory, source, 1);
        }
        hillClimbing.stop();

        return hillClimbing;


    }
}
