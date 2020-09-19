/*
 * Name: <Zohan Marediya>
 * EID: <zmm459>
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

/**
 * Your solution goes in this class.
 * 
 * Please do not modify the other files we have provided for you, as we will use
 * our own versions of those files when grading your project. You are
 * responsible for ensuring that your solution works with the original version
 * of all the other files we have provided for you.
 * 
 * That said, please feel free to add additional files and classes to your
 * solution, as you see fit. We will use ALL of your additional files when
 * grading your solution.
 */

public class Program1 extends AbstractProgram1 {
	/*	m = residences
	 * 	n = students
	 * 	residency preference: arraylist of arraylist 0 - m - 1 for 0 to n - 1
	 * student preference: arraylist of arraylist 0 to n - 1 for 0 to m - 1;
	 * residency slots: array list of slots for each residency
	 */

    /**
     * Determines whether a candidate Matching represents a solution to the Stable Marriage problem.
     * Study the description of a Matching in the project documentation to help you with this.
     */
    @Override
    public boolean isStableMatching(Matching marriage) {
		/*
		 * //impelement stability checker ArrayList<Integer> studentMatching =
		 * marriage.getStudentMatching();
		 * 
		 * for(int student = 0; student < studentMatching.size(); student++) {
		 * 
		 * int residency = studentMatching.indexOf(student);
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * }
		 * 
		 * return false;
		 */
    	return false;
    }
    
    public int getLeastRankedIndex(ArrayList<ArrayList<Integer>> residencyPref,
    		ArrayList<ArrayList<Integer>> residency_matching, int currentStudent, int preferredResidency) {
    		int leastRankedStudent = -1;
    		for(int i = 0; i < residency_matching.get(preferredResidency).size(); i++) {
    			int lowestRank = residencyPref.get(preferredResidency).get(currentStudent);
    			int nextStudent = residency_matching.get(preferredResidency).get(i);
    			if(lowestRank > residencyPref.get(preferredResidency).get(nextStudent)) {
    				lowestRank = residencyPref.get(preferredResidency).get(nextStudent);
    				leastRankedStudent = i;
    			}
    		}
    	return leastRankedStudent;
    }
 

    /**
     * Determines a solution to the Stable Marriage problem from the given input set. Study the
     * project description to understand the variables which represent the input to your solution.
     *
     * @return A stable Matching.
     */
    @Override
    public Matching stableMarriageGaleShapley_studentoptimal(Matching marriage) {
        /* TODO implement this function */
    	LinkedList<Integer> queue = new LinkedList<Integer>();
    	
    	//creating a queue, and adding all the students
    	int totalStudents = marriage.getStudentCount();
    	for(int i = 0; i < totalStudents; i++) {
    		queue.push(i);
    	}
    
    	//counting total slots
    	int totalResidencySlots = 0;
    	int residencyCount = marriage.getResidencyCount();
    	ArrayList<Integer> residencySlots = marriage.getResidencySlots();
    	for(int i = 0; i < residencyCount; i++) {
    		for(int j = 0; j <residencySlots.indexOf(i); j++) {
    			totalResidencySlots++;
    		}
    	}
    	
    	//creating a way to get O(1) access for student ranks
    	ArrayList<ArrayList<Integer>> residency_preference = marriage.getResidencyPreference();
    	ArrayList<ArrayList<Integer>> residencyPref = new ArrayList<ArrayList<Integer>>(residencyCount);
    	for(int i = 0; i < residencyCount; i++) {
    		ArrayList<Integer> pref = new ArrayList<Integer>(totalStudents);
    		for(int j = 0; j < totalStudents; j++) {
    			int inverse = residency_preference.get(i).get(j);
    			pref.add(inverse,j);
    		}
    		residencyPref.add(i,pref);
    	}
    	
    	//keeping track of the open slots
    	int filledSlots[] = new int[residencyCount];
    	
    	
    	// creating a array to keep track of highest proposed
    	int proposalIndex[] = new int[totalStudents];
    	
    	ArrayList<ArrayList<Integer>> residency_matching = new ArrayList<ArrayList<Integer>>(residencyCount);
    	for(int i = 0; i < residency_matching.size(); i++) {
    		residency_matching.add(new ArrayList<Integer>(residencySlots.get(i)));
    	}
    	
    	ArrayList<Integer> student_matching = new ArrayList<Integer>(totalStudents);
    	ArrayList<ArrayList<Integer>> student_preference = marriage.getStudentPreference();
    	
    	while(queue.size() > 0) {
    		// getting the highest ranked residency
    		int currentStudent = queue.peek();
    		int currentIndex = proposalIndex[currentStudent];
    		if(currentIndex >= totalResidencySlots) {
    			queue.pop();
    			continue;
    		}
    		int preferredResidency = student_preference.get(currentStudent).get(currentIndex);
    		if(filledSlots[preferredResidency] < residencySlots.get(preferredResidency)) {
    			student_matching.add(currentStudent,preferredResidency);
    			residency_matching.get(preferredResidency).add(filledSlots[preferredResidency], currentStudent);
    			filledSlots[preferredResidency]++;
    			queue.pop();
    		}else {
    			int otherStudentIndex = getLeastRankedIndex(residencyPref,residency_matching,currentStudent,preferredResidency);
    			if(otherStudentIndex == -1) {
    				proposalIndex[currentStudent]++;
    			}else {
    				int otherStudent = residency_matching.get(preferredResidency).get(otherStudentIndex);
    				student_matching.add(currentStudent,preferredResidency);
    				student_matching.add(otherStudent,-1);	//remove him from the list
    				residency_matching.get(preferredResidency).add(otherStudentIndex,currentStudent);
    				queue.pop();
    				queue.push(otherStudent);
    			}
    		}
    		
    	}
    	marriage.setStudentMatching(student_matching);
        return marriage;
    }

    /**
     * Determines a solution to the Stable Marriage problem from the given input set. Study the
     * project description to understand the variables which represent the input to your solution.
     *
     * @return A stable Matching.
     */
    
    @Override
    public Matching stableMarriageGaleShapley_residencyoptimal(Matching marriage) {
        /* TODO implement this function */
    	LinkedList<Integer> queue = new LinkedList<Integer>();
    	
        return marriage;
    }
}