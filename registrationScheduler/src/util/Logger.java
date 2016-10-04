package src.util;

/**
 * @author ${Shreyas Mahanthappa Nagaraj}
 * 
 */
public class Logger {
	
	public static enum debugLevels{
			CONSTRUCTOR,
			RUN,
			ENTRY,
			CONTENTS,
			PREF_SCORE
	 }
	
	private static debugLevels level;

	/**
	 * @return the level
	 */
	public static debugLevels getDebugLevel() {
		return level;
	}

	/**
	 * @param debuglevel
	 */
	public static void setDebugLevel(int levelIn) {
		
		switch(levelIn){
			case 4: level = debugLevels.CONSTRUCTOR; break;
			case 3: level = debugLevels.RUN;         break;
			case 2: level = debugLevels.ENTRY;		   break;
			case 1: level = debugLevels.CONTENTS;    break;
			case 0: level = debugLevels.PREF_SCORE;  break;
		}
	}

	/**
	 * To Print the output, based on the Debug Level
	 * 
	 */
	public static void writeOutput(debugLevels levelIn, String output){
		if(levelIn == level)
			System.out.print(output);
	}
	
	public static void writeOutput(String output){
		System.out.print(output);
	}
	
	public String toString(){
		return ""+level;
	}
}
