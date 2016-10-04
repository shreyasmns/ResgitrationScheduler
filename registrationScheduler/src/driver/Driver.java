package registrationScheduler.driver;

import java.io.File;
import registrationScheduler.util.Logger;
import registrationScheduler.store.FileDsiplayInterface;
import registrationScheduler.store.Results;
import registrationScheduler.store.StdOutDisplayInterface;
import registrationScheduler.util.Scheduler;
import registrationScheduler.util.FileProcessor;
import registrationScheduler.threadMgmt.CreateWorkers;

import static registrationScheduler.util.Constants.INPUT_FILE_INDEX;
import static registrationScheduler.util.Constants.OUTPUT_FILE_INDEX;
import static registrationScheduler.util.Constants.IN_THREADS_INDEX;
import static registrationScheduler.util.Constants.MINIMUM_THREADS;
import static registrationScheduler.util.Constants.MAXIMUM_THREADS;
import static registrationScheduler.util.Constants.MIN_DEBUG_LEVEL;
import static registrationScheduler.util.Constants.MAX_DEBUG_LEVEL;
import static registrationScheduler.util.Constants.IN_DEBUG_LEVEL_INDEX;

/**
 * @author ${Shreyas Mahanthappa Nagaraj}
 * 
 */
public class Driver {
	
	public static void main(String args[]) {
		Driver driver = new Driver();
		boolean valid = driver.validateInputArguements(args);
		if (valid) {
			boolean validFile = driver.validateInputFile(args);
			  if (validFile) {
				Logger.setDebugLevel(Integer.parseInt(args[IN_DEBUG_LEVEL_INDEX]));
				String inputfile = args[INPUT_FILE_INDEX];
				int numthreads = Integer.parseInt(args[IN_THREADS_INDEX]);
				
				FileProcessor fileprocessor = new FileProcessor(inputfile);
				Scheduler assigncourses = new Scheduler();
				FileDsiplayInterface fdinterface = new Results(args[OUTPUT_FILE_INDEX]);
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
	private boolean validateInputFile(String[] args) {
		System.out.println("file name :"+args[INPUT_FILE_INDEX]);
		File file = new File(args[INPUT_FILE_INDEX]);
		boolean res = true;
		if(!file.exists() || file.length()==0L){
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
