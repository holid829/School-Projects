package onlineTest;

import java.util.TreeMap;

public class TFQuestion extends Question{
	
	protected Boolean answer;
	protected TreeMap<String,Boolean> studentAnswers;
	
	public TFQuestion(String name, double point, Boolean answer,int num) {
		text = name;
		points = point;
		this.answer = answer;
		number = num;
		studentAnswers = new TreeMap<String,Boolean>();
	}
	
	public String getAnswer() {
		if(answer)
			return "True";
		return "False";
	}
	
	
	public void addAnswer(String studentName,String[] Answer) {
		return;
	}


	protected void addAnswer(String studentName, boolean answer) {
		studentAnswers.put(studentName,answer);
	}
	
	
	public double checkAnswers(String studentName) {
		Boolean answer = studentAnswers.get(studentName);
		if(answer == null) {
			return -1;
		}
		
		if(answer!=this.answer) {
			return 0;
		}
		return points;
	}
}
