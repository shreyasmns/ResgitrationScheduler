package src.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import static src.util.Constants.CAPACITY;
import static src.util.Constants.NO_OF_STUDENTS;

/**
 * @author ${Shreyas Mahanthappa Nagaraj}
 * 
 */
public class ObjectPool {

	private static ObjectPool poolInstance;
	private int COURSE_A = CAPACITY, COURSE_B = CAPACITY, COURSE_C = CAPACITY;
	private int COURSE_D = CAPACITY, COURSE_E = CAPACITY, COURSE_F = CAPACITY;
	private int COURSE_G = CAPACITY;
	private static Double totalPreference = 0.0;

	private volatile Vector<Vector<Integer>> list = new Vector<Vector<Integer>>(NO_OF_STUDENTS);
	private volatile static List<Vector<String>> studentsCourses = new Vector<Vector<String>>(NO_OF_STUDENTS);
	public volatile static Map<Integer, Integer> totalPref = new HashMap<>(NO_OF_STUDENTS);
	private Map<String, Integer> courseSlots = new HashMap<String, Integer>() {
		private static final long serialVersionUID = 1L;
				{
					put("A", COURSE_A);
					put("B", COURSE_B);
					put("C", COURSE_C);
					put("D", COURSE_D);
					put("E", COURSE_E);
					put("F", COURSE_F);
					put("G", COURSE_G);
				}
			};
		

	/**
	 * making ObjectPool class, a singleton class using 
	 * Double-checked locking
	 * 
	 * @return ObjectPool instance
	 */
	public static ObjectPool getInstance(){
		if(poolInstance == null){
			synchronized(ObjectPool.class){
				if(poolInstance == null){
					poolInstance = new ObjectPool();
				}
			}
		}
		return poolInstance;
	}
	
	
	/**
	 * To implement the logic of checking whether particular 
	 * course slots are available for scheduling
	 * 
	 * @param courseIn
	 * @return boolean
	 */
	public boolean isCourseAvailable(String courseIn){
		int subj = courseSlots.get(courseIn);
		if(subj > 0){
			return true;
		}
		return false;
	}
	
	
	/**
	 * to update the slots of particular course
	 * after particular course has been assigned
	 * 
	 * @param course 
	 * @return none
	 */
	public void updateCourseSlotsAssign(String subjIn){
		int subj = courseSlots.get(subjIn);
		courseSlots.put(subjIn, subj--);
	}
	

	/**
	 * to update the slots of particular course
	 * after particular course has been assigned
	 * 
	 * @param course 
	 * @return none
	 */
	public void updateCourseSlotsReturn(String subjIn){
		int subj = courseSlots.get(subjIn);
		courseSlots.put(subjIn, subj++);
	}
	
	
	/**
	 * @return the totalPreference
	 */
	public static Double getTotalPreference() {
		return totalPreference;
	}

	/**
	 * @param totalPreference
	 * the totalPreference to set
	 */
	public static void setTotalPreference(Double totalPreference) {
		ObjectPool.totalPreference = totalPreference;
	}

	/**
	 * @return the list
	 */
	public Vector<Vector<Integer>> getList() {
		return list;
	}

	/**
	 * @param list
	 * the list to set
	 */
	public void setList(Vector<Vector<Integer>> list) {
		this.list = list;
	}

	/**
	 * @return the studentsCourses
	 */
	public static List<Vector<String>> getStudentsCourses() {
		return studentsCourses;
	}

	/**
	 * @param studentsCourses
	 * the studentsCourses to set
	 */
	public static void setStudentsCourses(List<Vector<String>> studentsCourses) {
		ObjectPool.studentsCourses = studentsCourses;
	}

	/**
	 * @return the totalPref
	 */
	public static Map<Integer, Integer> getTotalPref() {
		return totalPref;
	}

	/**
	 * @param totalPref
	 * the totalPref to set
	 */
	public static void setTotalPref(Map<Integer, Integer> totalPref) {
		ObjectPool.totalPref = totalPref;
	}

	/**
	 * @return String
	 */
	@Override
	public String toString() {
		return "studentsCourses " + studentsCourses + ", TotalPreference " + totalPreference + ", totalPref "
				+ totalPref;
	}
	
}
