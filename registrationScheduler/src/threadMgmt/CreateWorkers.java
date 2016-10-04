package src.threadMgmt;

import java.util.ArrayList;
import java.util.List;

import src.store.FileDsiplayInterface;
import src.store.Results;
import src.store.StdOutDisplayInterface;
import src.util.FileProcessor;
import src.util.Logger;
import src.util.Scheduler;

/**
 * @author ${Shreyas Mahanthappa Nagaraj}
 * 
 */

public class CreateWorkers {
	
	private FileProcessor fileprocessor = null;
	private Scheduler pool = null;
	private StdOutDisplayInterface stdoutI = null;
	private FileDsiplayInterface filedisI = null;
	
	public CreateWorkers(FileProcessor fileprocessorIn, Scheduler assigncoursesIn, StdOutDisplayInterface stdOutInterfaceIn,FileDsiplayInterface fileDisInterfaceIn ){
		Logger.writeOutput(Logger.debugLevels.CONSTRUCTOR, Thread.currentThread().getStackTrace()[1].getClassName()+
				" - "+Thread.currentThread().getStackTrace()[2].getLineNumber()+	": Constructor of "+this.getClass().getSimpleName()+ " class is in execution.\n\n");
		
		fileprocessor = fileprocessorIn;
		pool = assigncoursesIn;
		stdoutI = stdOutInterfaceIn;
		filedisI = fileDisInterfaceIn;
	}
	
	/**
	 * @param threads
	 * @return none
	 */
	public void startWorkers(int threadsIn) {
		WorkerThread workerThread = new WorkerThread(this.fileprocessor, this.pool, this.filedisI);
		List<Thread> threads = new ArrayList<>();
	
		for (int count = 0; count < threadsIn; count++) {
			Thread thread = new Thread(workerThread, count+"");
			thread.start();
			threads.add(thread);
		}

		for (Thread thread : threads) {
			try {
				thread.join();
			} catch (InterruptedException exception) {
				System.err.println("Error while joining threads");
				exception.printStackTrace();
				System.exit(1);
			}
		}
	}
	
}
