package com.anniefraz.dissertation.experiments;

import com.anniefraz.dissertation.experiments.results.ResultBS;
import com.anniefraz.dissertation.experiments.results.ResultBSFileWriter;
import com.anniefraz.dissertation.experiments.results.ResultBSWriter;
import opacitor.Opacitor;
import opacitor.enumerations.GoalDirection;
import opacitor.enumerations.MeasurementType;
import opacitor.exceptions.*;
import org.apache.commons.io.FileUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;


public class Main {

    public static void main(String[] args) throws Exception {

        //System.out.println("start: " + L);
        String helpText = "Arg1: BSType(ITERATIVE OR RECURSIVE), Arg2: Output file, Arg3: arraySize, Arg4 (optimional): Seed ";

        if (args.length < 3) {
            System.err.println("Nah pal");
            throw new IllegalArgumentException(helpText);
        }

        final BSType bsType = BSType.valueOf(args[0]);

        String outputFile = args[1];

        final int arraySize = Integer.parseInt(args[2]);

        int seed = new Random().nextInt();

        if (args.length > 4) {
            seed = Integer.parseInt(args[3]);
        }

        final int[] array = generateArray(arraySize, seed);


        // String outputFileString = String.join(System.lineSeparator(), a);

        final ResultBSWriter resultBSWriter = new ResultBSFileWriter(outputFile);
        final String[] newArgs = toStringArray(array);

        final Path source = Files.createTempDirectory("source");
        final Path bin = Files.createTempDirectory("bin");

        String location = bsType.getLocation();
        InputStream resource = Main.class.getClassLoader().getResourceAsStream(location);
        System.out.println(resource);
        FileUtils.copyInputStreamToFile(resource, source.resolve(Paths.get(location)).toFile());
        System.out.println(source.toAbsolutePath().toString());
        List<Callable<ResultBS>> callables = new ArrayList<>();


        final String pathToDebugJava = Paths.get("Opacitor", "debugJVM").toAbsolutePath().toString();
        try {
            Opacitor opacitor = new Opacitor.OpacitorBuilder(bsType.getPackageName(), bsType.getClassName(), newArgs)
                    .pathToDebugJava(pathToDebugJava) // default is (./)java-[windows|linux]-[x64|x86]
                    .build();
        } catch (Exception e){
            System.out.println("hi");
        }

        for (int i = 0; i < 30; i++) {
            final int finalSeed = seed;
            final int finalI = i;
            callables.add(new Callable<ResultBS>() {
                @Override
                public ResultBS call() {
                    try {
                        Opacitor opacitor = new Opacitor.OpacitorBuilder(bsType.getPackageName(), bsType.getClassName(), newArgs)
                                .srcDirectory(source.toAbsolutePath().toString())
                                .binDirectory(bin.toAbsolutePath().toString())
                                .measurementType(MeasurementType.BYTECODE_HISTOGRAM)
                                .goalDirection(GoalDirection.MINIMISING)
                                .enableJIT(false) // default
                                .enableGC(false) // default
                                //.compiler(Paths.get("C:\\Program Files\\Java\\jdk1.8.0_191\\bin\\javac.exe").toAbsolutePath().toString())
                                .pathToDebugJava(pathToDebugJava) // default is (./)java-[windows|linux]-[x64|x86]
                                .build();
                        double measurement = opacitor.fitness(newArgs);

                        double measurement2 = opacitor.fitness(newArgs);

                        return ResultBS.getBuilder()
                                .setCurrentRep(finalI)
                                .setBsType(bsType)
                                .setOpacitorMeasurement1(measurement)
                                .setOpacitorMeasurement2(measurement2)
                                .setSeed(finalSeed)
                                .setArraySize(arraySize)
                                .setArray(array)
                                .build();

                    } catch (NoSuchMeasurementTypeException | NoSuchAlgorithmException | IOException | UnsupportedOSException | UnsupportedArchitectureException | InterruptedException | FailedToCompileException | FailedToRunException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
        }
        //do the thread stuff
        ExecutorService executor = new ThreadPoolExecutor(30, 30, 1, TimeUnit.DAYS, new ArrayBlockingQueue<Runnable>(40));
        List<Future<ResultBS>> futures = executor.invokeAll(callables);
        for (Future<ResultBS> future :
                futures) {
            resultBSWriter.writeResult(future.get());
        }
        executor.shutdown();
        resultBSWriter.close();
    }

    private static String[] toStringArray(int[] array) {
        String[] args = new String[array.length];
        for (int i = 0; i < array.length; i++) {
            args[i] = String.valueOf(array[i]);
        }
        return args;
    }

    public static int[] generateArray(int arraySize, int seed) {

        Random random = new Random(seed);
        int[] array = new int[arraySize];

        for (int i = 0; i < array.length; i++) {
            array[i] = random.nextInt(10);
        }

        System.out.println(Arrays.toString(array));
        return array;
    }
}
