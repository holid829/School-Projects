package onlineTest;

import java.util.*;

public class FTBQuestion extends Question {

	protected ArrayList<String> answer;
	protected TreeMap<String,String[]> studentAnswers;
	
	public FTBQuestion(String name, double point, String[] a, int num) {
		text = name;
		points = point;
		number = num;
		studentAnswers = new TreeMap();
		
		answer = new ArrayList<String>();
		boolean added = false;
		
		for(int i = 0; i < a.length; i++) {
			for(int j = 0; j < answer.size(); j++) {
				if(a[i].compareTo(answer.get(j)) < 0) {
						answer.add(j,a[i]);
						j = answer.size();
						added = true;
				}
			}
			
			if(!added) {
				answer.add(answer.size(),a[i]);
			}
			added = false;
		}
			
	}
			
	
	public String getAnswer() {
		return answer.toString();
	}
	
	public void addAnswer(String studentName,String[] Answer) {
		studentAnswers.put(studentName,Answer);
	}


	protected void addAnswer(String studentName, boolean answer) {
		return;
		
	}
	
	/*public String toString() {
		String toRet="";
	
		toRet+= "[" + answer[0];
		for(int i = 1; i < answer.length; i ++) {
			toRet += ", "+answer[i];
		}
		
		toRet+="]";
		return toRet;
	}*/
	
	public double checkAnswers(String studentName) {
		
		String[] answers = studentAnswers.get(studentName);
		double toRet = 0;
		for(int i = 0; i < answers.length; i++) {
			if(answer.contains(answers[i])) {
				toRet += points/answer.size();
			}
		}
		
		
		return toRet;
	}
}
