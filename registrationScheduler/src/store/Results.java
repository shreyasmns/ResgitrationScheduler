package src.store;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Vector;

import src.util.ObjectPool;
import src.util.Constants;
import src.util.Logger;
import src.util.Logger.debugLevels;
import static src.util.Constants.NO_OF_STUDENTS;

/**
 * @author ${Shreyas Mahanthappa Nagaraj}
 * 
 */
public class Results implements StdOutDisplayInterface, FileDsiplayInterface {

	private File file = null;
	private FileWriter filewriter = null;
	private Double totalPreference1 = 0.0;
	private Double totalPreference2 = 0.0;

	public Results(String outputfile) {
		Logger.writeOutput(Logger.debugLevels.CONSTRUCTOR,
				Thread.currentThread().getStackTrace()[1].getClassName() + " - "
						+ Thread.currentThread().getStackTrace()[2].getLineNumber() + ": Constructor of "
						+ this.getClass().getSimpleName() + " class is in execution.\n\n");
		try {
			file = new File(outputfile);
			filewriter = new FileWriter(file);
		} catch (IOException e) {
			System.err.println("Errow while creating filewriter stream.");
			System.exit(1);
		}
	}

	/**
	 * 
	 */
	public Results() {
	
	}

	/**
	 * @return none
	 */
	public void writeSchedulesToFile() {

		Vector<String> subjects = null;
		for (int i = 0; i < NO_OF_STUDENTS; i++) {
			totalPreference1 += ObjectPool.getTotalPref().get(i);
			subjects = ObjectPool.getStudentsCourses().get(i);

			writeToOutFile("Student_" + (i + 1) + " ");
			for (int j = 0; j < subjects.size(); j++) {
				writeToOutFile(subjects.get(j) + " ");
			}
			writeToOutFile(ObjectPool.getTotalPref().get(i).toString());
			writeToOutFile("\n");
		}
		writeToOutFile("\nAverage preference_score is: "
				+ String.format("%.4f", (totalPreference1 / Constants.NO_OF_STUDENTS)));
	}

	/**
	 * Helper method to write to the output file
	 * 
	 * @param string
	 * @return none
	 */
	public void writeToOutFile(String string) {
		try {
			filewriter.write(string);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	/**
	 * Writes the result set to the standard output
	 * 
	 * @return none
	 */
	public void writeSchedulesToScreen() {

		Vector<String> subjects = null;
		for (int i = 0; i < NO_OF_STUDENTS; i++) {
			totalPreference2 += ObjectPool.getTotalPref().get(i);
			subjects = ObjectPool.getStudentsCourses().get(i);
			if (Logger.getDebugLevel() == debugLevels.CONTENTS) {
				Logger.writeOutput(debugLevels.CONTENTS, "Student_" + (i + 1) + " ");
				for (int j = 0; j < subjects.size(); j++) {
					Logger.writeOutput(debugLevels.CONTENTS, subjects.get(j) + " ");
				}
				Logger.writeOutput(debugLevels.CONTENTS, ObjectPool.getTotalPref().get(i).toString());
				Logger.writeOutput("\n");
			}
		}
		System.out.println("total pref score : "+totalPreference2/Constants.NO_OF_STUDENTS);
		Logger.writeOutput(debugLevels.PREF_SCORE, "\nAverage preference_score is: "
				+ String.format("%.4f", (totalPreference2 / Constants.NO_OF_STUDENTS)));
	}

	/**
	 * return the filewriter resource to the system
	 * @return none
	 */
	public void closeFilewriter() {
		try {
			filewriter.close();
		} catch (IOException e) {
			System.err.println("Error while closing fliewriter resource.");
			System.exit(1);
		}
	}

	/**
	 * @return String
	 * 
	 */
	@Override
	public String toString() {
		return "studentsCourses " + ObjectPool.getStudentsCourses() + ", TotalPreference "
				+ ObjectPool.getTotalPreference() + ", totalPref " + ObjectPool.getTotalPref();
	}
	
}
