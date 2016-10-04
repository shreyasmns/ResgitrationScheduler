package src.util;

import static src.util.Constants.NO_OF_CHOICES;
import static src.util.Constants.NO_OF_SUBJECTS;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;
import java.util.Vector;
import java.util.Map.Entry;

import src.util.Logger.debugLevels;

/**
 * @author ${Shreyas Mahanthappa Nagaraj}
 * 
 */
public class Scheduler {
	
	private Vector<Integer> preferences = null; 
	private volatile Vector<String> courses = null; 
	private Map<Integer, Integer> subjects_count = new HashMap<>();
	private TreeMap<Integer, String> choices = new TreeMap<>();
	private int other_students = 2;
	private volatile int count = 0;
	private Map<Integer, String> subjects = new HashMap<Integer, String>() {
	private static final long serialVersionUID = 1L;
			{
				put(0, "A");
				put(1, "B");
				put(2, "C");
				put(3, "D");
				put(4, "E");
				put(5, "F");
				put(6, "G");
			}
		};
	private Map<String, Integer> subjectsValues = new HashMap<String, Integer>() {
			private static final long serialVersionUID = 1L;
			{
				put("A", 0);
				put("B", 1);
				put("C", 2);
				put("D", 3);
				put("E", 4);
				put("F", 5);
				put("G", 6);
			}
		};


	ObjectPool pool = ObjectPool.getInstance();
		

	/**
	 * calls 'assign' helper method to assign courses passing student id
	 * and their preferences
	 * @return none
	 */
	public void assignCourses(String line) {
		String[] tokens = line.split(" +");

		if (tokens.length != 8) {
			System.err.println("Input File Format is Wrong.");
			System.exit(1);
		}
		preferences = new Vector<Integer>(NO_OF_CHOICES);
		for (int i = 0; i < NO_OF_CHOICES; i++) {
			preferences.add(Integer.parseInt(tokens[i + 1]));
		}
		pool.getList().add(preferences);
		
		String subj = null;
		for (int j = 0; j < NO_OF_CHOICES; j++) {
			subj = subjects.get(j);
			choices.put(pool.getList().get(count).get(j), subj);
		}
		assign(choices, count);
		count++;
	}

	/**
	 * Method try assigning courses to students according to preferences
	 * 
	 * @param choices
	 * @param studentid
	 * @return none
	 */
	@SuppressWarnings("static-access")
	public void assign(TreeMap<Integer, String> map, int i) {
		
		courses = new Vector<String>(NO_OF_CHOICES);
		int totalpref = 0;
		int no_of_subjects = 0;

		for (Map.Entry<Integer, String> entry : map.entrySet()) {
			int pref = entry.getKey();
			String sub = entry.getValue();

			if (no_of_subjects < NO_OF_SUBJECTS) {
				if (pool.isCourseAvailable(sub)) {
					courses.add(sub);
					pool.updateCourseSlotsAssign(sub);
					totalpref = totalpref + pref;
					++no_of_subjects;
					subjects_count.put(i, no_of_subjects);
				}
			}
		}
		pool.getTotalPref().put(i, totalpref);
		pool.getStudentsCourses().add(courses);
		Logger.writeOutput(debugLevels.ENTRY, "Courses assigned to Student_"+(i+1)+ ": "
				+ ""+courses.toString()+"\n");
	}

		
	/**
	 * This method checks, whether every student has been assigned 
	 * exactly five subjects
	 * 
	 */
	public void checkCourseAssignment() {

		Iterator<Entry<Integer, Integer>> it = subjects_count.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<Integer, Integer> pair = (Map.Entry<Integer, Integer>) it.next();
			int student = pair.getKey();
			int subjects = pair.getValue();

			if (subjects < NO_OF_SUBJECTS) {
				reAssignCourses(student, subjects);
			}
		}
	}

	/**
	 * If any student has bene assigned less than 3 courses, 
	 * Re-assignment will be done for such student
	 * 
	 * @param student
	 * @param subjects2
	 * (The number of subjects student has been allotted currently)
	 * @return none
	 */
	@SuppressWarnings("static-access")
	public void reAssignCourses(int i, int subjects2) { // i is student with
														// less than 5 courses
		int difference = NO_OF_SUBJECTS - subjects2;
		int replace_pref = 4;
		String subjReplace = null;
		String subjReassign = null;

		for (int j = 1; j <= difference; j++) {
			if (replace_pref > 5)
				replace_pref = 4;
			Integer sixth_pref = pool.getList().get(other_students).indexOf(6);
			Integer first_pref = pool.getList().get(other_students).indexOf(replace_pref);
			subjReplace = subjects.get(sixth_pref);
			subjReassign = subjects.get(first_pref);

			pool.getStudentsCourses().get(other_students).remove(subjReassign);
			pool.getStudentsCourses().get(other_students).add(subjReplace);
			Logger.writeOutput(debugLevels.ENTRY, "Course, " + subjReplace + " has been re-assigned to the student "
					+ ""+other_students);

			// Updating preference scores after re-assigning subjects
			int prev_pref = pool.getTotalPref().get(other_students);
			prev_pref += (6 - replace_pref);
			pool.getTotalPref().put(other_students, prev_pref);

			// To assign student a course, who didn't get 5 courses initially
			pool.getStudentsCourses().get(i).add(subjReassign);
			Logger.writeOutput(debugLevels.ENTRY, "Course, " + subjReassign + " has been re-assigned to the student"+i);
			int index = subjectsValues.get(subjReassign);
			int actualPref = pool.getList().get(i).get(index);

			actualPref = actualPref + pool.getTotalPref().get(i);
			pool.getTotalPref().put(i, actualPref);
			updateCourseAvailability(subjReplace, subjReassign);

			other_students++;
			replace_pref++;
		}
	}

	/**
	 * Updates the each course available count
	 * 
	 * @param subjReplace
	 * @param subjReassign
	 */
	public void updateCourseAvailability(String subjReplace, String subjReassign) {

		if (subjReplace != null) {
			pool.updateCourseSlotsAssign(subjReplace);
		}

		if (subjReassign != null) {
			pool.updateCourseSlotsReturn(subjReassign);
		}
	}
}
