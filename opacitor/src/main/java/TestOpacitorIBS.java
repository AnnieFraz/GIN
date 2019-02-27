import com.google.common.io.Files;
import jeep.tuple.Tuple3;
import opacitor.Opacitor;
import opacitor.enumerations.GoalDirection;
import opacitor.enumerations.MeasurementType;

import java.io.File;
import java.nio.charset.Charset;
import java.util.Collections;
import java.util.List;

public class TestOpacitorIBS {

    private static String testSrcDir = null;
    private static String testBinDir = null;
    private static String testReplacementCode= null;


    public static void main(String[] args) {
        final String osNameProperty = System.getProperty("os.name").toLowerCase();
        if (osNameProperty.contains("windows")) {

            testSrcDir = "C:/Users/user/IdeaProjects/AnnaGin/Opacitor/test_external_dir/src";
            testBinDir = "C:/Users/user/IdeaProjects/AnnaGin/Opacitor/test_external_dir/bin";
            testReplacementCode = "C:/Users/user/IdeaProjects/AnnaGin/Opacitor/test_external_dir/src/test/TestClassReplacement.java";
        }
        else if (osNameProperty.contains("ios")) {

            //Mac Paths
             testSrcDir = "/Users/annarasburn/Documents/gin/AnnaGin/opacitor/test_external_dir/src";
             testBinDir = "/Users/annarasburn/Documents/gin/AnnaGin/opacitor/test_external_dir/bin";
             testReplacementCode = "/Users/annarasburn/Documents/gin/AnnaGin/opacitor/test_external_dir/src/test/TestClassReplacement.java";
        }
        // length. simply measure the length of the code
        System.out.println("CodeLength");

        try {
            Opacitor opacitor = new Opacitor.OpacitorBuilder("test", "IterativeBubbleSort", new String[]{})
                    .srcDirectory(testSrcDir)
                    .binDirectory(testBinDir)
                    .measurementType(MeasurementType.CODE_LENGTH)
                    .performInitialCompilation(true)
                    .debug(true)
                    .goalDirection(GoalDirection.MINIMISING)
                    .compiler("C:\\Program Files\\Java\\jdk1.7.0_80\\bin\\javac.exe")
                    .build();


            // first we try running with different params
            double measurement;
            measurement = opacitor.fitness(new String[]{"test1.txt", "1000", "10000"});
            System.out.println(measurement);
            measurement = opacitor.fitness(new String[]{"test2.txt", "0", "20000"});
            System.out.println(measurement);

            // now try running with different code
            List<String> replacementList = Files.readLines(new File(testReplacementCode), Charset.defaultCharset());
            String replacement = String.join(System.lineSeparator(), replacementList);
            opacitor.updateCode(Collections.<Tuple3<String,String,String>>singletonList(new Tuple3<String,String,String>(replacement, "test", "IterativeBubbleSort")));
            measurement = opacitor.fitness(new String[]{"test1.txt", "1000", "10000"});
            System.out.println(measurement);

        } catch (Exception e) {
            e.printStackTrace();
        }


        System.out.println("SuperSimpleJalen");

        // supersimplejalen. run code, measure time in single thread. multiply to get joules
        try {
            Opacitor opacitor = new Opacitor.OpacitorBuilder("test", "IterativeBubbleSort", new String[]{})
                    .srcDirectory(testSrcDir)
                    .binDirectory(testBinDir)
                    .measurementType(MeasurementType.SUPER_SIMPLE_JALEN)
                    .performInitialCompilation(true)
                    .goalDirection(GoalDirection.MINIMISING)
                    .build();

            // first we try running with different params
            double measurement;
            measurement = opacitor.fitness(new String[]{"test1.txt", "1000", "10000"});
            System.out.println(measurement);
            measurement = opacitor.fitness(new String[]{"test2.txt", "0", "20000"});
            System.out.println(measurement);

            // now try running with different code
            List<String> replacementList = Files.readLines(new File(testReplacementCode), Charset.defaultCharset());
            String replacement = String.join(System.lineSeparator(), replacementList);
            opacitor.updateCode(Collections.<Tuple3<String,String,String>>singletonList(new Tuple3<String,String,String>(replacement, "test", "IterativeBubbleSort")));
            measurement = opacitor.fitness(new String[]{"test1.txt", "1000", "10000"});
            System.out.println(measurement);

        } catch (Exception e) {
            e.printStackTrace();
        }
        // simplejalen. run code, measure time across threads. multiply to get joules
        // OK - can't get this working yet. But the others do!
        System.out.println("SimpleJalen");

        try {
            Opacitor opacitor = new Opacitor.OpacitorBuilder("test", "IterativeBubbleSort", new String[]{})
                    .srcDirectory(testSrcDir)
                    .binDirectory(testBinDir)
                    .measurementType(MeasurementType.SIMPLE_JALEN)
                    .singleThreadedTargetJalen(true)
                    .performInitialCompilation(true)
                    .goalDirection(GoalDirection.MINIMISING)
                    .build();

            // first we try running with different params
            double measurement;
            measurement = opacitor.fitness(new String[]{"test1.txt", "1000", "10000"});
            System.out.println(measurement);
            measurement = opacitor.fitness(new String[]{"test2.txt", "0", "20000"});
            System.out.println(measurement);

            // now try running with different code
            List<String> replacementList = Files.readLines(new File(testReplacementCode), Charset.defaultCharset());
            String replacement = String.join(System.lineSeparator(), replacementList);
            opacitor.updateCode(Collections.<Tuple3<String,String,String>>singletonList(new Tuple3<String,String,String>(replacement, "test", "IterativeBubbleSort")));
            measurement = opacitor.fitness(new String[]{"test1.txt", "1000", "10000"});
            System.out.println(measurement);

        } catch (Exception e) {
            e.printStackTrace();
        }
        // the full opacitor, counting bytecodes and measuring energy
        System.out.println("BytecodeTrace");

        //final String osNameProperty = System.getProperty("os.name").toLowerCase();
        String pathToDebugJava = "";
        if (osNameProperty.contains("linux")) {
            pathToDebugJava = "/home/sbr/Opacitor";
        } else if (osNameProperty.contains("windows")) {
            pathToDebugJava = "C:" + File.separator + "Opacitor" + File.separator + "java-windows-x64";
        } else {
            System.err.println("Testing on non-Windows/Linux isn't supported!");
            System.exit(1);
        }


        try {
            Opacitor opacitor = new Opacitor.OpacitorBuilder("test", "IterativeBubbleSort", new String[]{})
                    .srcDirectory(testSrcDir)
                    .binDirectory(testBinDir)
                    .measurementType(MeasurementType.BYTECODE_HISTOGRAM)
                    .enableJIT(false) // for bytecode_histogram - makes com.anniefraz.dissertation.experiments.results more predictable
                    .enableGC(false) // for bytecode_histogram - makes com.anniefraz.dissertation.experiments.results more predictable
                    .performInitialCompilation(true)
                    .pathToDebugJava(pathToDebugJava) // default is (./)java-[windows|linux]-[x64|x86]
                    .goalDirection(GoalDirection.MINIMISING)
                    .build();

            // first we try running with different params
            double measurement;
            measurement = opacitor.fitness(new String[]{"test1.txt", "1000", "10000"});
            System.out.println(measurement);
            measurement = opacitor.fitness(new String[]{"test2.txt", "0", "20000"});
            System.out.println(measurement);

            // now try running with different code
            List<String> replacementList = Files.readLines(new File(testReplacementCode), Charset.defaultCharset());
            String replacement = String.join(System.lineSeparator(), replacementList);
            opacitor.updateCode(Collections.<Tuple3<String,String,String>>singletonList(new Tuple3<String,String,String>(replacement, "test", "IterativeBubbleSort")));
            measurement = opacitor.fitness(new String[]{"test1.txt", "1000", "10000"});
            System.out.println(measurement);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}