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
import java.util.Random;

public class ApplicationMain {

    private static int REPS = 10;

    private static int editNumberSeed = 4;

    private static boolean compileSuccess;

    static Logger LOG = LoggerFactory.getLogger(ApplicationMain.class);


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
        Random random = new Random();
        int  noOfEdits = random.nextInt(editNumberSeed) + 1;
        LOG.info("Number of Edits: "+ noOfEdits);

        int i =0;
        //To get the number of reps
        //for (int i = 0; i <= REPS; i++) {
            while (i < REPS){

            //Creation of a patch with many different edits
            Patch patch = patchFactory.getPatchForSourceWithEdits(source, noOfEdits);

            //This gives the source with the edits applied so 'changed code'
            Source outputSource = patch.getOutputSource();
            LOG.info("Output source: " + outputSource);

            ((Closeable) applicationContext).close();

            LOG.info("Which edits: "+patch.getEdits());
            System.out.println("which edits: " + patch.getEdits());

                //This needs to be in seperate method. will show to sandy tho - 12/11
            Class<?> compiledClass = null;

            List<AnnaClass> classList = outputSource.getAnnaClasses();

            //Getting the right class.
            //Maybe do as a loop for multiple classes
            AnnaClass ac = classList.get(0);

            //Changing into a string so it can be put into in memory java compiler
            String outputfileString = String.join(System.lineSeparator(), ac.getLines());

            //Removing the commas so hopefully it can compile better
            //String str = outputfileString.replace(",", "\n");

            System.out.println(outputfileString);

       compiledClass = InMemoryJavaCompiler.newInstance().compile("Triangle", outputfileString) ;

       if (compiledClass != null){

           System.out.println("Didn't compile");
           System.out.println("time:"+System.currentTimeMillis());
       }else{
           System.out.println("Compiled Successfully");
           System.out.println("time:"+System.currentTimeMillis());
           i++;
       }
        }

    }

    private static void compile(Source code) throws Exception{





    }

    private void sendToTestRunner(){
        //Here I want when I have made a new patch for it to go to the test runner automatically
    }

}
