package src.driver;
import java.io.File;
import src.util.Logger;
import src.store.FileDsiplayInterface;
import src.store.Results;
import src.store.StdOutDisplayInterface;
import src.util.Scheduler;
import src.util.FileProcessor;
import src.threadMgmt.CreateWorkers;

import static src.util.Constants.INPUT_FILE_INDEX;
import static src.util.Constants.IN_THREADS_INDEX;
import static src.util.Constants.MINIMUM_THREADS;
import static src.util.Constants.MAXIMUM_THREADS;
import static src.util.Constants.MIN_DEBUG_LEVEL;
import static src.util.Constants.MAX_DEBUG_LEVEL;
import static src.util.Constants.IN_DEBUG_LEVEL_INDEX;

/**
 * @author ${Shreyas Mahanthappa Nagaraj}
 * 
 */
public class Driver {
	
	public static void main(String args[]) {
		Driver driver = new Driver();
//		boolean valid = driver.validateInputArguements(args);
		boolean valid = true;
		if (valid) {
//			boolean validFiles = driver.validateFiles(args);
			boolean validFile = true;
			  if (validFile) {
//				Logger.setDebugLevel(Integer.parseInt(args[IN_DEBUG_LEVEL_INDEX]));
				Logger.setDebugLevel(2);
//				String inputfile = args[INPUT_FILE_INDEX];
				String inputfile = "input.txt";
//				int numthreads = Integer.parseInt(args[IN_THREADS_INDEX]);
				int numthreads = 1;
				
				FileProcessor fileprocessor = new FileProcessor(inputfile);
				Scheduler assigncourses = new Scheduler();
//				Results resultsInterface = new Results(args[OUTPUT_FILE_INDEX]);
				FileDsiplayInterface fdinterface = new Results("output.txt");
				StdOutDisplayInterface stdoutInterface = new Results();
				
				CreateWorkers createWorkers = new CreateWorkers(fileprocessor, assigncourses, stdoutInterface,fdinterface);
				createWorkers.startWorkers(numthreads);
				
				stdoutInterface.writeSchedulesToScreen();
				fdinterface.writeSchedulesToFile();
				
				fdinterface.closeFilewriter();
				fileprocessor.returnResources();
			}
		}
	}
	
	/**
	 * @param args
	 * @return whether file is valid
	 * 
	 */
	private boolean validateFiles(String[] args) {
		System.out.println("file name :"+args[INPUT_FILE_INDEX]);
		File inFile = new File(args[INPUT_FILE_INDEX]);
		boolean res = true;
		
		String outFile = args[1];
		if(!outFile.endsWith(".txt")){
			res = false;
			System.err.println("Invalid Output File name");
			System.exit(1);
		}
		
		if(!inFile.exists() || inFile.length()==0L){
			res = false;
			System.err.println("Empty file or Input File not present");
			System.exit(1);
		}
		return res;
	}

	/**
	 * @param args
	 * @return, whether the number of arguments is valid
	 * 
	 */
	private boolean validateInputArguements(String[] args) {
		if (args.length != 4) {
			System.err.println("Invalid number of arguements. Hence exiting.!!");
			System.exit(1);
		}
		
		int threads = Integer.parseInt(args[IN_THREADS_INDEX]);
		if(threads < MINIMUM_THREADS || threads > MAXIMUM_THREADS ){
			System.err.println("Enter the valid number of Threads ");
			System.exit(1);
		}
		
		int debuglevel = Integer.parseInt(args[IN_DEBUG_LEVEL_INDEX]);
		if(debuglevel < MIN_DEBUG_LEVEL || debuglevel > MAX_DEBUG_LEVEL){
			System.err.println("Debug Level is out of Range(1..3");
			System.exit(1);
		}
		return true;
	}
	
}
