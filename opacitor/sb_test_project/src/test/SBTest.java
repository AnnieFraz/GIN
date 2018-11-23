package test;

//import java.lang.reflect.Method;

import java.io.File;

import opacitor.enumerations.GoalDirection;
import opacitor.enumerations.MeasurementType;
import opacitor.Opacitor;

public class SBTest {
	public static void main(String[] args) {		
		try {
			Opacitor o;
			double d;
			
			o = new Opacitor.OpacitorBuilder("sbtest", "SBTest_SuperSimpleJalen", new String[]{})
					.measurementType(MeasurementType.CODE_LENGTH)
					.goalDirection(GoalDirection.MINIMISING) // default
					.build();
			
			d = o.fitness(new String[]{"outCL1.txt", "0", "10000"});
			System.out.println("Opacitor output 1: " + d);
			
			d = o.fitness(new String[]{"outCL2.txt", "0", "20000"});
			System.out.println("Opacitor output 2: " + d);
			
			o = new Opacitor.OpacitorBuilder("sbtest", "SBTest_SuperSimpleJalen", new String[]{})
					.measurementType(MeasurementType.SUPER_SIMPLE_JALEN)
					.goalDirection(GoalDirection.MINIMISING) // default
					.build();
	
			d = o.fitness(new String[]{"outSSJ1.txt", "0", "10000"});
			System.out.println("Opacitor output 3: " + d);
			
			d = o.fitness(new String[]{"outSSJ2.txt", "0", "20000"});
			System.out.println("Opacitor output 4: " + d);
			
			o = new Opacitor.OpacitorBuilder("sbtest", "SBTest_SuperSimpleJalen", new String[]{})
					.measurementType(MeasurementType.SIMPLE_JALEN)
					.goalDirection(GoalDirection.MINIMISING) // default
					.singleThreadedTargetJalen(true) // must be disabled if multithreaded (falls back to wall-clock not CPU time)
					.manuallyInjectJalen(false) // default - will search for method `SimpleJalen' and "measure" it, if not found will "measure" main method
					.build();
		
			d = o.fitness(new String[]{"outSJ1.txt", "0", "10000"});
			System.out.println("Opacitor output 5: " + d);
			
			d = o.fitness(new String[]{"outSJ2.txt", "0", "20000"});
			System.out.println("Opacitor output 6: " + d);
			
			o = new Opacitor.OpacitorBuilder("sbtest", "SBTest_SimpleJalen", new String[]{})
					.measurementType(MeasurementType.SIMPLE_JALEN)
					.goalDirection(GoalDirection.MINIMISING) // default
					.singleThreadedTargetJalen(true) // must be disabled if multithreaded (falls back to wall-clock not CPU time)
					.manuallyInjectJalen(false) // default - will search for method `SimpleJalen' and "measure" it, if not found will "measure" main method
					.build();
		
			d = o.fitness(new String[]{"outSJ3.txt", "0", "10000"});
			System.out.println("Opacitor output 7: " + d);
			
			d = o.fitness(new String[]{"outSJ4.txt", "0", "20000"});
			System.out.println("Opacitor output: " + d);
			
			o = new Opacitor.OpacitorBuilder("sbtest", "SBTest_SuperSimpleJalen", new String[]{})
					.measurementType(MeasurementType.BYTECODE_HISTOGRAM)
					.enableJIT(false) // default
					.enableGC(false) // default
					.goalDirection(GoalDirection.MINIMISING) // default
					.pathToDebugJava("C:" + File.separator + "Opacitor" + File.separator + "java-windows-x64") // default is (./)java-[windows|linux]-[x64|x86]
					.build();
		
			d = o.fitness(new String[]{"outBCT1.txt", "0", "10000"});
			System.out.println("Opacitor output 8: " + d);
			
			d = o.fitness(new String[]{"outBCT2.txt", "0", "20000"});
			System.out.println("Opacitor output 9: " + d);
			
			o = new Opacitor.OpacitorBuilder("sbtest", "SBTest_SuperSimpleJalen", new String[]{})
					.measurementType(MeasurementType.BYTECODE_HISTOGRAM)
					.enableJIT(true)
					.enableGC(true)
					.goalDirection(GoalDirection.MINIMISING) // default
					.pathToDebugJava("C:" + File.separator + "Opacitor" + File.separator + "java-windows-x64") // default is (./)java-[windows|linux]-[x64|x86]
					.build();
		
			d = o.fitness(new String[]{"outBCT3.txt", "0", "10000"});
			System.out.println("Opacitor output 10: " + d);
			
			d = o.fitness(new String[]{"outBCT4.txt", "0", "20000"});
			System.out.println("Opacitor output 11: " + d);
			
			o = new Opacitor.OpacitorBuilder("sbtest", "SBTestScala", new String[]{})
			.measurementType(MeasurementType.BYTECODE_HISTOGRAM)
			.enableJIT(false)
			.enableGC(false)
			.goalDirection(GoalDirection.MINIMISING) // default
			.performInitialCompilation(false)
			.pathToDebugJava("C:" + File.separator + "Opacitor" + File.separator + "java-windows-x64") // default is (./)java-[windows|linux]-[x64|x86]
			.build();

			d = o.fitness(new String[]{"10000"});
			System.out.println("Scala Opacitor output 12: " + d);

			d = o.fitness(new String[]{"20000"});
			System.out.println("Scala Opacitor output 13: " + d);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
=======
package test;

//import java.lang.reflect.Method;

import java.io.File;

import opacitor.enumerations.GoalDirection;
import opacitor.enumerations.MeasurementType;
import opacitor.Opacitor;

public class SBTest {
	public static void main(String[] args) {		
		try {
			Opacitor o;
			double d;
			
			o = new Opacitor.OpacitorBuilder("sbtest", "SBTest_SuperSimpleJalen", new String[]{})
					.measurementType(MeasurementType.CODE_LENGTH)
					.goalDirection(GoalDirection.MINIMISING) // default
					.build();
			
			d = o.fitness(new String[]{"outCL1.txt", "0", "10000"});
			System.out.println("Opacitor output 1: " + d);
			
			d = o.fitness(new String[]{"outCL2.txt", "0", "20000"});
			System.out.println("Opacitor output 2: " + d);
			
			o = new Opacitor.OpacitorBuilder("sbtest", "SBTest_SuperSimpleJalen", new String[]{})
					.measurementType(MeasurementType.SUPER_SIMPLE_JALEN)
					.goalDirection(GoalDirection.MINIMISING) // default
					.build();
	
			d = o.fitness(new String[]{"outSSJ1.txt", "0", "10000"});
			System.out.println("Opacitor output 3: " + d);
			
			d = o.fitness(new String[]{"outSSJ2.txt", "0", "20000"});
			System.out.println("Opacitor output 4: " + d);
			
			o = new Opacitor.OpacitorBuilder("sbtest", "SBTest_SuperSimpleJalen", new String[]{})
					.measurementType(MeasurementType.SIMPLE_JALEN)
					.goalDirection(GoalDirection.MINIMISING) // default
					.singleThreadedTargetJalen(true) // must be disabled if multithreaded (falls back to wall-clock not CPU time)
					.manuallyInjectJalen(false) // default - will search for method `SimpleJalen' and "measure" it, if not found will "measure" main method
					.build();
		
			d = o.fitness(new String[]{"outSJ1.txt", "0", "10000"});
			System.out.println("Opacitor output 5: " + d);
			
			d = o.fitness(new String[]{"outSJ2.txt", "0", "20000"});
			System.out.println("Opacitor output 6: " + d);
			
			o = new Opacitor.OpacitorBuilder("sbtest", "SBTest_SimpleJalen", new String[]{})
					.measurementType(MeasurementType.SIMPLE_JALEN)
					.goalDirection(GoalDirection.MINIMISING) // default
					.singleThreadedTargetJalen(true) // must be disabled if multithreaded (falls back to wall-clock not CPU time)
					.manuallyInjectJalen(false) // default - will search for method `SimpleJalen' and "measure" it, if not found will "measure" main method
					.build();
		
			d = o.fitness(new String[]{"outSJ3.txt", "0", "10000"});
			System.out.println("Opacitor output 7: " + d);
			
			d = o.fitness(new String[]{"outSJ4.txt", "0", "20000"});
			System.out.println("Opacitor output: " + d);
			
			o = new Opacitor.OpacitorBuilder("sbtest", "SBTest_SuperSimpleJalen", new String[]{})
					.measurementType(MeasurementType.BYTECODE_HISTOGRAM)
					.enableJIT(false) // default
					.enableGC(false) // default
					.goalDirection(GoalDirection.MINIMISING) // default
					.pathToDebugJava("C:" + File.separator + "Opacitor" + File.separator + "java-windows-x64") // default is (./)java-[windows|linux]-[x64|x86]
					.build();
		
			d = o.fitness(new String[]{"outBCT1.txt", "0", "10000"});
			System.out.println("Opacitor output 8: " + d);
			
			d = o.fitness(new String[]{"outBCT2.txt", "0", "20000"});
			System.out.println("Opacitor output 9: " + d);
			
			o = new Opacitor.OpacitorBuilder("sbtest", "SBTest_SuperSimpleJalen", new String[]{})
					.measurementType(MeasurementType.BYTECODE_HISTOGRAM)
					.enableJIT(true)
					.enableGC(true)
					.goalDirection(GoalDirection.MINIMISING) // default
					.pathToDebugJava("C:" + File.separator + "Opacitor" + File.separator + "java-windows-x64") // default is (./)java-[windows|linux]-[x64|x86]
					.build();
		
			d = o.fitness(new String[]{"outBCT3.txt", "0", "10000"});
			System.out.println("Opacitor output 10: " + d);
			
			d = o.fitness(new String[]{"outBCT4.txt", "0", "20000"});
			System.out.println("Opacitor output 11: " + d);
			
			o = new Opacitor.OpacitorBuilder("sbtest", "SBTestScala", new String[]{})
			.measurementType(MeasurementType.BYTECODE_HISTOGRAM)
			.enableJIT(false)
			.enableGC(false)
			.goalDirection(GoalDirection.MINIMISING) // default
			.performInitialCompilation(false)
			.pathToDebugJava("C:" + File.separator + "Opacitor" + File.separator + "java-windows-x64") // default is (./)java-[windows|linux]-[x64|x86]
			.build();

			d = o.fitness(new String[]{"10000"});
			System.out.println("Scala Opacitor output 12: " + d);

			d = o.fitness(new String[]{"20000"});
			System.out.println("Scala Opacitor output 13: " + d);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
>>>>>>> testrunnerthings
