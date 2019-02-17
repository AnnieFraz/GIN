package com.anniefraz.dissertation.algorithms.GAs.main.fitness;

import com.anniefraz.dissertation.main.input.UserInput;
import jeep.tuple.Tuple3;
import opacitor.Opacitor;
import opacitor.enumerations.GoalDirection;
import opacitor.enumerations.MeasurementType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Paths;
import java.util.Collections;

public class FitnessEnergy implements FitnessMeasurement<String> {

    static Logger LOG = LoggerFactory.getLogger(FitnessEnergy.class);

    //private String testSrcDir = "C:\\Users\\user\\IdeaProjects\\Anna-Gin\\test-runner\\src\\main\\java";
    private String testSrcDir = "C:\\Users\\user\\IdeaProjects\\Anna-Gin\\test-runner\\examples\\unittests";
    private String testBinDir = "C:\\Users\\user\\IdeaProjects\\Anna-Gin\\test-runner\\target\\classes";

    //String testSrcDir = "C:\\Users\\user\\IdeaProjects\\Anna-Gin\\opacitor\\test_external_dir\\src\\test";
    //String testBinDir = "C:\\Users\\user\\IdeaProjects\\Anna-Gin\\Opacitor\\test_external_dir\\bin\\test";
    //String testBinDir = "C:\\Users\\user\\IdeaProjects\\Anna-Gin\\test-runner\\target\\test-classes\\unittest\\example\\Triangle.java";

    private String output;

    private double result;
    private static UserInput userInput;

    public FitnessEnergy(UserInput userInput) {
        this.userInput = userInput;
    }

    @Override
    public double measure (String output)  {
        setOutput(output);
        try {
           //result  = codeLengthOpacitor();
         result = bytecodeHistogramOpacitor();
          // result = superSimpleJalenOpacitor();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public double codeLengthOpacitor() throws Exception{
        Opacitor opacitor = new Opacitor.OpacitorBuilder(userInput.getPackageName(), userInput.getClassFileName(), new String[]{})
                .srcDirectory(testSrcDir)
                .binDirectory(testBinDir)
                .measurementType(MeasurementType.CODE_LENGTH)
                .goalDirection(GoalDirection.MINIMISING)
                .compiler(Paths.get("C:\\Program Files\\Java\\jdk1.8.0_191\\bin\\javac.exe").toAbsolutePath().toString())
                .build();

        output = getOutput();

        double measurement;
        opacitor.updateCode(Collections.singletonList(new Tuple3<>(output, "", "Triangle")));
        measurement = opacitor.fitness(new String[]{"test1.txt", "1000", "10000"});
        LOG.info("Measurement 1: {}", measurement);

        double measurement2 = opacitor.fitness(new String[]{"test1.txt", "1000", "10000"});
        LOG.info("Measurement 2: {} ", measurement2);

        return measurement;
    }

    public double superSimpleJalenOpacitor() throws Exception{
        Opacitor opacitor = new Opacitor.OpacitorBuilder("example", "Triangle", new String[]{})
                .srcDirectory(testSrcDir)
                .binDirectory(testBinDir)
                .measurementType(MeasurementType.SUPER_SIMPLE_JALEN)
                //.performInitialCompilation(true)
                .goalDirection(GoalDirection.MINIMISING)
                //.singleThreadedTargetJalen(true)
                //.manuallyInjectJalen(false)
                .compiler(Paths.get("C:\\Program Files\\Java\\jdk1.8.0_191\\bin\\javac.exe").toAbsolutePath().toString())
                .build();

        output = getOutput();

        double measurement;
        opacitor.updateCode(Collections.singletonList(new Tuple3<>(output, "example", "Triangle")));
        measurement = opacitor.fitness(new String[]{"test1.txt", "1000", "10000"});
        LOG.info("Measurement:{}", measurement);
        double measurement2 = opacitor.fitness(new String[]{"test1.txt", "1000", "10000"});
        LOG.info("Measurement:{}", measurement2);

        return measurement;
    }

    public double bytecodeHistogramOpacitor() throws Exception{
        //Opacitor opacitor = new Opacitor.OpacitorBuilder("foo", "ReverseString", new String[]{})
       // Opacitor opacitor = new Opacitor.OpacitorBuilder("example", "Triangle", new String[]{})
        Opacitor opacitor = new Opacitor.OpacitorBuilder(userInput.getPackageName(), userInput.getClassFileName(), new String[]{})
                .srcDirectory(testSrcDir)
                .binDirectory(testBinDir)
                .measurementType(MeasurementType.BYTECODE_HISTOGRAM)
                .goalDirection(GoalDirection.MINIMISING)
                .enableJIT(false) // default
                .enableGC(false) // default
                .compiler(Paths.get("C:\\Program Files\\Java\\jdk1.8.0_191\\bin\\javac.exe").toAbsolutePath().toString())
                .build();

        output = getOutput();

        double measurement;
        opacitor.updateCode(Collections.singletonList(new Tuple3<>(output, userInput.getPackageName(), userInput.getClassFileName())));
        measurement = opacitor.fitness(new String[]{"test1.txt", "1000", "10000"});
        LOG.info("Measurement: {}", measurement);

        double measurement2 = opacitor.fitness(new String[]{"test1.txt", "1000", "10000"});
        LOG.info("Measurement: {}", measurement2);

        return measurement;
    }

    public String getOutput() {
        return output;
    }

    public void setOutput(String output) {
        this.output = output;
    }

}
