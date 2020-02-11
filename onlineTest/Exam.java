package onlineTest;

import java.io.Serializable;
import java.util.ArrayList;

public class Exam implements Serializable{
	
	protected int ID;
	protected String name;
	protected ArrayList<Question>questions;
	
	
	public Exam(int examId, String title) {
		ID = examId;
		name = title;
		questions = new ArrayList<Question>();
	}

	public Question get(int qNumber) {
		for(int i = 0; i < questions.size(); i++) {
			if(questions.get(i).number == qNumber) {
				return questions.get(i);
			}
		}
		return null;
	}
	
	public void addNewTFQuestion(int questionNumber, String text, double points, boolean answer) {
		TFQuestion newQuest = new TFQuestion(text, points, answer, questionNumber);
		
		for(int i = 0; i < questions.size(); i++) {
			
			int temp = questions.get(i).number;
			if(temp == questionNumber) {
				questions.set(i,newQuest);
				return;
			}
			if(temp > questionNumber) {
				questions.add(i,newQuest);
				return;
			}
		}
		
		questions.add(questions.size(),newQuest);
	}
	
	
	public void addNewMCQuestion(int questionNumber, String text, double points, String[] answer) {
		MCQuestion newQuest = new MCQuestion(text, points, answer, questionNumber);
		
		for(int i = 0; i < questions.size(); i++) {
			if(questions.get(i).number == questionNumber) {
				questions.set(i,newQuest);
				return;
			}
			
			if(questions.get(i).number > questionNumber) {
				questions.add(i,newQuest);
				return;
			}
		}
		
		questions.add(questions.size(),newQuest);
	}

	
	public void addNewFTBQuestion(int questionNumber, String text, double points, String[] answer) {
		FTBQuestion newQuest = new FTBQuestion(text, points, answer, questionNumber);
		
		for(int i = 0; i < questions.size(); i++) {
			if(questions.get(i).number == questionNumber) {
				questions.set(i,newQuest);
				return;
			}
			
			if(questions.get(i).number > questionNumber) {
				questions.add(i,newQuest);
				return;
			}
		}
		
		questions.add(questions.size(),newQuest);
	}
	
	public int getTotalPoints() {
		int total =0;
		for(int i = 0; i < questions.size(); i++) {
			total += questions.get(i).points;
		}
		return total;
	}
}
