package src.threadMgmt;

import src.store.FileDsiplayInterface;
import src.store.StdOutDisplayInterface;
import src.util.Scheduler;
import src.util.FileProcessor;
import src.util.Logger;

/**
 * @author ${Shreyas Mahanthappa Nagaraj}
 * 
 */
public class WorkerThread implements Runnable {
		
	private FileProcessor fileprocessor = null;
	private Scheduler assigncourses = null;
	
	public WorkerThread(FileProcessor fileprocessorIn, Scheduler pool, FileDsiplayInterface filedisI){
		Logger.writeOutput(Logger.debugLevels.CONSTRUCTOR, Thread.currentThread().getStackTrace()[1].getClassName()+
				" - "+Thread.currentThread().getStackTrace()[2].getLineNumber()+	": Constructor of "+this.getClass().getSimpleName()+ " class is in execution.\n\n");
		
		fileprocessor  = fileprocessorIn;
		assigncourses = pool;
	}
	
   /**
    * reads a line from the input file and tries to schedule courses
	* @return none
	* 
	*/
	public synchronized void run() {
		StringBuilder threadname = new StringBuilder(Thread.currentThread().getName());
		Logger.writeOutput(Logger.debugLevels.RUN, Thread.currentThread().getStackTrace()[1].getClassName()+
				" - "+Thread.currentThread().getStackTrace()[1].getLineNumber()+ ": Thread-"+threadname+ " is in execution.\n\n");
		String inLine = null;
		while((inLine = fileprocessor.readLine()) != null){
			assigncourses.assignCourses(inLine);
		}
		assigncourses.checkCourseAssignment();
	}
	
}
