package onlineTest;

import java.io.Serializable;

public abstract class Question implements Serializable{
	
	protected int number;
	protected double points;
	protected String text;

	
	public abstract String getAnswer();


	protected abstract void addAnswer(String studentName, boolean answer);
	protected abstract void addAnswer(String studentName, String[] answer);


	protected abstract double checkAnswers(String studentName);
	
	

}
