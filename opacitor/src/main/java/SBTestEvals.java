//import java.lang.reflect.Method;

import java.io.File;

import opacitor.enumerations.GoalDirection;
import opacitor.enumerations.MeasurementType;
import opacitor.Opacitor;

public class SBTestEvals {
	public static void main(String[] args) {	
		try {
			System.out.println("Running...");
			Opacitor op;
			
//			op = new Opacitor.OpacitorBuilder("hypermlp", "HyperMLP2", new String[0])
//	          .measurementType(MeasurementType.BYTECODE_HISTOGRAM)
//	          .enableJIT(false)
//	          .enableGC(false)
//	          .goalDirection(GoalDirection.MINIMISING)
//	          .performInitialCompilation(false) // tells Opacitor not to try to compile any code during instantiation (usually used to test future evals will work)
//	          //.pathToDebugJava("D:/Opacitor/java-windows-x64")
//	          .pathToDebugJava("C:" + File.separator + "Opacitor" + File.separator + "java-windows-x64") // default is (./)java-[windows|linux]-[x64|x86]
//	          .build();
//
//			for (int i = 4; i < 14; i++) {
//				double f = op.fitness(("-evaluate -datafile=resources\\weka-3-6-12\\data\\diabetes.arff -validation=false -seed=4 -mlp1=0.5505093124538353  -mlp2=0.7019861344398167  -mlp3=0.8489891044751847  -mlp4=0.5179706592040311  -hls="+i).split("\\s+"));
//				System.out.println("FitnessMLP2NoV:\t" + i + "\t" + f);
//			}
			
//			RunHyperMLP.main(new String[] {"none","resources\\weka-3-6-12\\data\\diabetes.arff", "validation"});
//			op = new Opacitor.OpacitorBuilder("hypermlp", "HyperMLP2", new String[0])
//	          .measurementType(MeasurementType.BYTECODE_HISTOGRAM)
//	          .enableJIT(false)
//	          .enableGC(false)
//	          .goalDirection(GoalDirection.MINIMISING)
//	          .performInitialCompilation(false) // tells Opacitor not to try to compile any code during instantiation (usually used to test future evals will work)
//	          //.pathToDebugJava("D:/Opacitor/java-windows-x64")
//	          .pathToDebugJava("C:" + File.separator + "Opacitor" + File.separator + "java-windows-x64") // default is (./)java-[windows|linux]-[x64|x86]
//	          .build();
//
//			for (int i = 4; i < 14; i++) {
//				double f = op.fitness(("-evaluate -datafile=resources\\weka-3-6-12\\data\\diabetes.arff -validation=true -seed=4 -mlp1=0.5505093124538353  -mlp2=0.7019861344398167  -mlp3=0.8489891044751847  -mlp4=0.5179706592040311  -hls="+i).split("\\s+"));
//				System.out.println("FitnessMLP2V:\t" + i + "\t" + f);
//			}
//			
			
//			op = new Opacitor.OpacitorBuilder("hypermlp", "HyperMLP3", new String[0])
//	          .measurementType(MeasurementType.BYTECODE_HISTOGRAM)
//	          .enableJIT(false)
//	          .enableGC(false)
//	          .goalDirection(GoalDirection.MINIMISING)
//	          .performInitialCompilation(false) // tells Opacitor not to try to compile any code during instantiation (usually used to test future evals will work)
//	          //.pathToDebugJava("D:/Opacitor/java-windows-x64")
//	          .pathToDebugJava("C:" + File.separator + "Opacitor" + File.separator + "java-windows-x64") // default is (./)java-[windows|linux]-[x64|x86]
//	          .build();
//
//			for (int i = 4; i < 14; i++) {
//				double f = op.fitness(("-evaluate -datafile=resources\\weka-3-6-12\\data\\diabetes.arff -validation=false -seed=4 -mlp1=0.5505093124538353  -mlp2=0.7019861344398167  -mlp3=0.8489891044751847  -mlp4=0.5179706592040311  -hls="+i+" -ll=0.5 -alpha=0.5").split("\\s+"));
//				System.out.println("FitnessMLP3NoV:\t" + i + "\t" + f);
//			}
			
			
			op = new Opacitor.OpacitorBuilder("hypermlp", "HyperMLP3", new String[0])
	          .measurementType(MeasurementType.BYTECODE_HISTOGRAM)
	          .enableJIT(false)
	          .enableGC(false)
	          .goalDirection(GoalDirection.MINIMISING)
	          .performInitialCompilation(false) // tells Opacitor not to try to compile any code during instantiation (usually used to test future evals will work)
	          //.pathToDebugJava("D:/Opacitor/java-windows-x64")
	          .pathToDebugJava("C:" + File.separator + "Opacitor" + File.separator + "java-windows-x64") // default is (./)java-[windows|linux]-[x64|x86]
	          .build();

			for (int i = 4; i < 14; i++) {
				double f = op.fitness(("-evaluate -datafile=resources\\weka-3-6-12\\data\\diabetes.arff -validation=true -seed=4 -mlp1=0.5505093124538353  -mlp2=0.7019861344398167  -mlp3=0.8489891044751847  -mlp4=0.5179706592040311  -hls="+i+" -ll=0.5 -alpha=0.5").split("\\s+"));
				System.out.println("FitnessMLP3V:\t" + i + "\t" + f);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
