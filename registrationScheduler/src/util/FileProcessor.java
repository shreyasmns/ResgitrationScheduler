package src.util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * @author ${Shreyas Mahanthappa Nagaraj}
 * 
 */
public class FileProcessor {
	
	private FileReader filereader;
	private BufferedReader br;
	
	/**
	 * FileProcessor Constructor, takes inputFile and tries to create the
	 * Reader objects
	 * 
	 * @param  input file name
	 * @throws FileNotFoundException
	 */
	public FileProcessor(String inputFile){
		Logger.writeOutput(Logger.debugLevels.CONSTRUCTOR, Thread.currentThread().getStackTrace()[1].getClassName()+
				" - "+Thread.currentThread().getStackTrace()[2].getLineNumber()+	": Constructor of "+this.getClass().getSimpleName()+ " class is in execution.\n\n");
		
		try {
			filereader = new FileReader(inputFile);
			br = new BufferedReader(filereader);
		} catch (FileNotFoundException e) {
			System.err.println("Exception while reading input file");
			e.printStackTrace();
			System.exit(1);
		}
	}
	
   /**
	* This method reads a line from the input file and returns it as a 
	* String and synchronised make this operation thread-safe
	* 
	* @throws IOException
	*/
	public synchronized String readLine(){
		try {
			return br.readLine();
		} catch (IOException e1) {
			System.err.println("Error while Reading a line from Input file");
			System.exit(1);
		}
		return null;
	}
		
   /**
	* This method is to return the resources like filereader and 
	* bufferedreader back to the system
	* 
	*/
	public void returnResources(){
		try {
			filereader.close();
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
	
}
