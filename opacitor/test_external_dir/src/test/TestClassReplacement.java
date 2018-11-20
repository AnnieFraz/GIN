package test;


import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Random;

/**
 *  this is the class to be measured
 */
class TestClass2Replacement {

	private static void wasteCPU(PrintStream out, final int startDelayMS, final int iterations) throws InterruptedException {
		out.println("Testing....");

		Random random = new Random(1); // seed 1
		
		Thread.sleep(startDelayMS);
		
		double d = 1;
		for (int i = 0; i < iterations/2; ++i) {
			out.print(i + ", ");
			if (i % 100 == 0) {
				out.println();
			}
			d *= random.nextDouble();
			out.println("Result: " + d);
		}
		
		out.println("Finished");
	}
	
	/**
	 * Runs a loop to waste time / energy
	 * 
	 * @param args Output filename, start delay ms, iterations
	 * @throws FileNotFoundException If the output file can't be written
	 * @throws InterruptedException If the sleep is interrupted
	 * @throws NumberFormatException If the argument can't be correctly parsed
	 */
	public static void main(String[] args) throws FileNotFoundException, NumberFormatException, InterruptedException {
		if (args.length < 3) {
			throw new IllegalArgumentException("Not enough arguments...");
		}

		PrintStream out = new PrintStream(new FileOutputStream(args[0]));

		wasteCPU(out, Integer.parseInt(args[1]), Integer.parseInt(args[2]));

		out.close();
	}

}
