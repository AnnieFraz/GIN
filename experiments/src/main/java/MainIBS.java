import com.anniefraz.dissertation.gin.application.config.ApplicationConfig;
import com.anniefraz.dissertation.gin.patch.Patch;
import com.anniefraz.dissertation.gin.patch.PatchFactory;
import com.anniefraz.dissertation.gin.source.AnnaClass;
import com.anniefraz.dissertation.gin.source.AnnaPath;
import com.anniefraz.dissertation.gin.source.Source;
import com.anniefraz.dissertation.gin.source.SourceFactory;
import jeep.tuple.Tuple3;
import opacitor.Opacitor;
import opacitor.enumerations.GoalDirection;
import opacitor.enumerations.MeasurementType;
import org.mdkt.compiler.CompilationException;
import org.mdkt.compiler.InMemoryJavaCompiler;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import results.Result;
import results.ResultFileWriter;
import results.ResultWriter;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
//import java.util.Optional;

public class MainIBS {

    private static int REPS = 30;
    private static boolean compileSuccess;

    static ApplicationContext applicationContext;

    public static double getOpacitorMeasurement2() {
        return opacitorMeasurement2;
    }

    public static void setOpacitorMeasurement2(double opacitorMeasurement2) {
        MainRBS.opacitorMeasurement2 = opacitorMeasurement2;
    }

    static double opacitorMeasurement2;

    private static final String PATHNAME = "C:\\Users\\user\\IdeaProjects\\Anna-Gin\\experiments\\loops\\bubbleSort\\java";

    public static void main(String[] args) throws IOException, Exception {
        //ApplicationContext allows to spring to properly interject beans into the application
        applicationContext = new AnnotationConfigApplicationContext(ApplicationConfig.class);

        //Configures/gets the beans from the factories
        PatchFactory patchFactory = applicationContext.getBean(PatchFactory.class);
        SourceFactory sourceFactory = new SourceFactory(Paths.get(PATHNAME));
        //SourceFactory sourceFactory = applicationContext.getBean(SourceFactory.class);

        //Gets the file we want to apply edits
        AnnaPath annaPath = AnnaPath.getBuilder().setClassName("IterativeBubbleSort").build();

        //Gets the source file from that anna path
        Source source = sourceFactory.getSourceFromAnnaPath(annaPath);
        //Applies a number of edits
        int noOfEdits = 1;

        compile(patchFactory, source, noOfEdits);

        ((Closeable) applicationContext).close();
    }


    private static void compile(PatchFactory patchFactory, Source source, int noOfEdits) throws Exception {
        int i = 0;
        ResultWriter resultWriter = new ResultFileWriter();
        while (i < REPS) {

            //Creation of a patch with many different edits
            Patch patch = patchFactory.getPatchForSourceWithEdits(source, noOfEdits);

            //This gives the source with the edits applied so 'changed code'
            Source outputSource = patch.getOutputSource();
            //System.out.println("Output source: " + outputSource);


           // LOG.info("Which edits: " + patch.getEdits());

            //This needs to be in seperate method. will show to sandy tho - 12/11
            Class<?> compiledClass = null;

            //Getting the right class.
            //Maybe do as a loop for multiple classes
            List<AnnaClass> classList = outputSource.getAnnaClasses();
            AnnaClass ac = classList.get(0);

            //Changing into a string so it can be put into in memory java compiler
            String outputFileString = String.join(System.lineSeparator(), ac.getLines());
            //System.out.println(outputFileString);

            try {
                compiledClass = InMemoryJavaCompiler.newInstance().compile("IterativeBubbleSort", outputFileString);
            } catch (CompilationException e){

            }

            long time;

            double measurement = 0;


            if (compiledClass == null) {
                System.out.println("DID NOT COMPILE");
                System.out.println("TIME:" + System.currentTimeMillis());
                time = System.currentTimeMillis();

                compileSuccess = false;
                i++;
            } else {
                System.out.println("Compiled Successfully");
                System.out.println("TIME:" + System.currentTimeMillis());
                time = System.currentTimeMillis();
                compileSuccess = true;
                                measurement = sendToOpacitor(outputFileString);
                i++;
            }
            System.out.println("Current Repetition: {}"+ i);

            Result result = Result.getBuilder()
                    .setCurrentRep(i)
                    .setPatch(patch)
                    .setOutputFileString(outputFileString)
                    .setTime(time)
                    .setCompiledClass(compiledClass)
                    .setCompileSuccess(compileSuccess)
                    .setOpacitorMeasurement2(getOpacitorMeasurement2())
                    .setOpacitorMeasurement1(measurement)
                    .build();

            resultWriter.writeResult(result);
            //setResults(i, patch, outputFileString, time, compiledClass, compileSuccess, unitTestResultSet);

        }
    }

    private static double sendToOpacitor(String outputString) throws Exception {

       // String testSrcDir = "C:\\Users\\user\\IdeaProjects\\Anna-Gin\\opacitor\\test_external_dir\\src\\test";
       // String testBinDir = "C:\\Users\\user\\IdeaProjects\\Anna-Gin\\Opacitor\\test_external_dir\\bin\\test";

        // String testSrcDir = "/Users/annarasburn/Documents/gin/AnnaGin/opacitor/test_external_dir/src";
        // String testBinDir = "/Users/annarasburn/Documents/gin/AnnaGin/opacitor/test_external_dir/bin";

        //String testBinDir = "D:\\Users\\tglew\\intelliProjects\\UpdatedGin\\GIN\\opacitor\\test_external_dir\\bin\\test";
        //String testSrcDir = "D:\\Users\\tglew\\intelliProjects\\UpdatedGin\\GIN\\opacitor\\test_external_dir\\src\\test";

        String testSrcDir = "C:\\Users\\user\\IdeaProjects\\Anna-Gin\\experiments\\src\\main\\java";
        String testBinDir = "C:\\Users\\user\\IdeaProjects\\Anna-Gin\\experiments\\target\\classes";


        String testReplacementCode = outputString;

        Opacitor opacitor = new Opacitor.OpacitorBuilder("loops", "IterativeBubbleSort", new String[]{})
                .srcDirectory(testSrcDir)
                .binDirectory(testBinDir)
                .measurementType(MeasurementType.SUPER_SIMPLE_JALEN)
               // .performInitialCompilation(true)
                .goalDirection(GoalDirection.MINIMISING)
                .compiler(Paths.get("C:\\Program Files\\Java\\jdk1.8.0_191\\bin\\javac.exe").toAbsolutePath().toString())
                .build();

        double measurement;

        opacitor.updateCode(Collections.singletonList(new Tuple3<>(testReplacementCode, "", "loops.IterativeBubbleSort")));
        measurement = opacitor.fitness(new String[]{"test1.txt", "1000", "10000"});
        System.out.println("Measurement: {}" + measurement);

        double measurement2 = opacitor.fitness(new String[]{"test1.txt", "1000", "10000"});
        System.out.println("Measurement: {}" + measurement2);

        setOpacitorMeasurement2(measurement2);

        return measurement;

    }
}
