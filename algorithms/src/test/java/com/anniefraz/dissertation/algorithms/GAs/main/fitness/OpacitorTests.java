package com.anniefraz.dissertation.algorithms.GAs.main.fitness;

import com.anniefraz.dissertation.main.input.UserInput;
import jeep.tuple.Tuple3;
import opacitor.Opacitor;
import opacitor.enumerations.GoalDirection;
import opacitor.enumerations.MeasurementType;
import opacitor.exceptions.*;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Paths;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class OpacitorTests {

    private String testSrcDir;
    private String testBinDir;

    private static UserInput userInput;

    private String helloWorldOutput;

    @BeforeEach
    public void initialise(){
        testSrcDir = "C:\\Users\\user\\IdeaProjects\\Anna-Gin\\test-runner\\examples\\unittests";
        testBinDir = "C:\\Users\\user\\IdeaProjects\\Anna-Gin\\test-runner\\target\\classes";

         userInput = UserInput.getBuilder()
                .setClassFileName("HelloWorld")
                .setTestFileName("HelloWorld")
                .setPackageName("Test")
                .build();

        List<String> lines = new ArrayList<String>();
        lines.add("[package Test;, , public class HelloWorld {,     public HelloWorld() {,     },     public static void main(String[] args) {,     System.out.println(\"Hello, World\");,      }, }]");
        helloWorldOutput = String.join(System.lineSeparator(), lines);
        System.out.println(helloWorldOutput);

    }

    @Test
    public void testCodeLength() throws IOException, UnsupportedArchitectureException, InterruptedException, UnsupportedOSException, NoSuchAlgorithmException, NoSuchMeasurementTypeException, FailedToCompileException, FailedToRunException {
        Opacitor opacitor = new Opacitor.OpacitorBuilder(userInput.getPackageName(), userInput.getClassFileName(), new String[]{})
                .srcDirectory(testSrcDir)
                .binDirectory(testBinDir)
                .measurementType(MeasurementType.CODE_LENGTH)
                .goalDirection(GoalDirection.MINIMISING)
                .compiler(Paths.get("C:\\Program Files\\Java\\jdk1.8.0_191\\bin\\javac.exe").toAbsolutePath().toString())
                .build();

        double measurement;
        opacitor.updateCode(Collections.singletonList(new Tuple3<>(helloWorldOutput, "", "Triangle")));
        measurement = opacitor.fitness(new String[]{"test1.txt", "1000", "10000"});
        System.out.println(measurement);




    }

    @Test
    public void testBytecode() throws IOException, UnsupportedArchitectureException, InterruptedException, UnsupportedOSException, NoSuchAlgorithmException, NoSuchMeasurementTypeException, FailedToCompileException, FailedToRunException {

        Opacitor opacitor = new Opacitor.OpacitorBuilder(userInput.getPackageName(), userInput.getClassFileName(), new String[]{})
                .srcDirectory(testSrcDir)
                .binDirectory(testBinDir)
                .measurementType(MeasurementType.BYTECODE_HISTOGRAM)
                .goalDirection(GoalDirection.MINIMISING)
                .enableJIT(false) // default
                .enableGC(false) // default
                .compiler(Paths.get("C:\\Program Files\\Java\\jdk1.8.0_191\\bin\\javac.exe").toAbsolutePath().toString())
                .build();

        double measurement;
        opacitor.updateCode(Collections.singletonList(new Tuple3<>(helloWorldOutput, "", "Triangle")));
        measurement = opacitor.fitness(new String[]{"test1.txt", "1000", "10000"});
        System.out.println(measurement);


    }

    @Test void testJalen() throws IOException, UnsupportedArchitectureException, InterruptedException, UnsupportedOSException, NoSuchAlgorithmException, NoSuchMeasurementTypeException, FailedToCompileException, FailedToRunException {
        Opacitor opacitor = new Opacitor.OpacitorBuilder("example", "Triangle", new String[]{})
                .srcDirectory(testSrcDir)
                .binDirectory(testBinDir)
                .measurementType(MeasurementType.SUPER_SIMPLE_JALEN)
                .performInitialCompilation(true)
                .goalDirection(GoalDirection.MINIMISING)
                .singleThreadedTargetJalen(true)
                .manuallyInjectJalen(false)
                .compiler(Paths.get("C:\\Program Files\\Java\\jdk1.8.0_191\\bin\\javac.exe").toAbsolutePath().toString())
                .build();

        double measurement;
        opacitor.updateCode(Collections.singletonList(new Tuple3<>(helloWorldOutput, "", "Triangle")));
        measurement = opacitor.fitness(new String[]{"test1.txt", "1000", "10000"});
        System.out.println(measurement);

    }
}
