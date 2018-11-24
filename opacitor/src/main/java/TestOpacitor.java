import java.io.File;

import opacitor.Opacitor;
import opacitor.enumerations.GoalDirection;

public class TestOpacitor {

    public static void main(String[] args) {
        final String osNameProperty = System.getProperty("os.name").toLowerCase();
        String pathToDebugJava = "";
        if (osNameProperty.contains("linux")) {
            pathToDebugJava = "/home/sbr/Opacitor";
        } else if (osNameProperty.contains("windows")) {
            pathToDebugJava = "D:" + File.separator + "Opacitor" + File.separator + "java-windows-x64";
        } else {
            System.err.println("Testing on non-Windows/Linux isn't supported!");
            System.exit(1);
        }

        try {
            Opacitor opacitor = new Opacitor.OpacitorBuilder("test", "TestClass", new String[]{})
                    .enableJIT(false)
                    .enableGC(false)
                    .pathToDebugJava(pathToDebugJava) // default is (./)java-[windows|linux]-[x64|x86]
                    .goalDirection(GoalDirection.MINIMISING)
                    .build();

            double measurement;
            measurement = opacitor.fitness(new String[]{"test1.txt", "1000", "10000"});
            System.out.println(measurement);
            measurement = opacitor.fitness(new String[]{"test2.txt", "0", "20000"});
            System.out.println(measurement);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}