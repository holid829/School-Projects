package onlineTest;

import java.io.*;
import java.util.*;

public class SystemManager implements Serializable, Manager {

	private ArrayList<Exam> exams = new ArrayList<Exam>();
	private ArrayList<String> students = new ArrayList<String>();
	String[] letterGrades;
	double[] cutoffs;

	public boolean addExam(int examId, String title) {
		Exam toAdd = new Exam(examId, title);
		for (int i = 0; i < exams.size(); i++) {
			if (exams.get(i).equals(toAdd))
				return false;
		}

		exams.add(toAdd);
		return true;
	}

	public void addTrueFalseQuestion(int examId, int questionNumber, String text, double points, boolean answer) {
		for (int i = 0; i < exams.size(); i++) {
			if (exams.get(i).ID == examId) {
				exams.get(i).addNewTFQuestion(questionNumber, text, points, answer);
			}
		}
	}

	public void addMultipleChoiceQuestion(int examId, int questionNumber, String text, double points, String[] answer) {
		for (int i = 0; i < exams.size(); i++) {
			if (exams.get(i).ID == examId) {
				exams.get(i).addNewMCQuestion(questionNumber, text, points, answer);
			}
		}
	}

	public void addFillInTheBlanksQuestion(int examId, int questionNumber, String text, double points,
			String[] answer) {
		for (int i = 0; i < exams.size(); i++) {
			if (exams.get(i).ID == examId) {
				exams.get(i).addNewFTBQuestion(questionNumber, text, points, answer);
			}
		}
	}

	public String getKey(int examId) {

		Exam check = getExam(examId);

		if (check == null)
			return "Exam not found";

		String toRet = "";
		ArrayList<Question> questions = check.questions;

		for (int i = 0; i < questions.size(); i++) {
			Question current = questions.get(i);
			toRet += "Question Text: " + current.text + "\n";
			toRet += "Points: " + current.points + "\n";
			toRet += "Correct Answer: " + current.getAnswer() + "\n";
		}

		return toRet;
	}

	public boolean addStudent(String name) {
		if (getStudent(name) != null)
			return false;

		for (int i = 0; i < students.size(); i++) {
			if (students.get(i).compareTo(name) > 0) {
				students.add(i, name);
				return true;
			}
		}
		students.add(name);

		return true;
	}

	public void answerTrueFalseQuestion(String studentName, int examId, int questionNumber, boolean answer) {
		getExam(examId).get(questionNumber).addAnswer(studentName, answer);
	}

	public void answerMultipleChoiceQuestion(String studentName, int examId, int questionNumber, String[] answer) {
		getExam(examId).get(questionNumber).addAnswer(studentName, answer);

	}

	public void answerFillInTheBlanksQuestion(String studentName, int examId, int questionNumber, String[] answer) {
		getExam(examId).get(questionNumber).addAnswer(studentName, answer);
	}

	public double getExamScore(String studentName, int examId) {
		Exam check = getExam(examId);
		double score = 0;
		double max = 0;

		for (int i = 0; i < check.questions.size(); i++) {
			Question current = check.questions.get(i);
			max += current.points;
			double curr = current.checkAnswers(studentName);
			if (curr == -1)
				return -1;
			score += curr;
		}

		return score;
	}

	public String getGradingReport(String studentName, int examId) {
		Exam report = getExam(examId);
		String toRet = "";
		double score = 0;
		double total = 0;
		for (int i = 0; i < report.questions.size(); i++) {
			Question current = report.questions.get(i);
			toRet += "Question #" + current.number + " ";
			double curr = current.checkAnswers(studentName);

			if (curr == -1) {
				return null;
			}

			total += current.points;
			score += curr;
			toRet += curr + " points out of " + current.points + "\n";
		}

		toRet += "Final Score: " + score + " out of " + total;
		return toRet;
	}

	public void setLetterGradesCutoffs(String[] letterGrades, double[] cutoffs) {
		this.letterGrades = letterGrades;
		this.cutoffs = cutoffs;
	}

	public double getCourseNumericGrade(String studentName) {
		double score = 0;
		double total = 0;
		for (int i = 0; i < exams.size(); i++) {
			double curr = getExamScore(studentName, exams.get(i).ID);

			if (curr == -1)
				i++;
			else {
				score += curr / exams.get(i).getTotalPoints();

			}
		}

		return score / exams.size() * 100;
	}

	public String getCourseLetterGrade(String studentName) {
		double percent = getCourseNumericGrade(studentName);
		for (int i = 0; i < cutoffs.length; i++) {
			if (cutoffs[i] <= percent)
				return letterGrades[i];

		}
		return letterGrades[letterGrades.length - 1];
	}

	/**
	 * Returns a listing with the grades for each student. For each student the
	 * report will include the following information: <br />
	 * {studentName} {courseNumericGrade} {courseLetterGrade}<br />
	 * The names will appear in sorted order.
	 * 
	 * @return grades
	 */
	public String getCourseGrades() {
		String toRet = "";

		for (int i = 0; i < students.size(); i++) {
			String current = students.get(i);
			toRet += current + " " + getCourseNumericGrade(current) + " " + getCourseLetterGrade(current) + "\n";
		}

		return toRet;
	}

	@Override
	public double getMaxScore(int examId) {
		double toRet = 0;
		double nextGrade = 0;

		for (int i = 0; i < students.size(); i++) {
			nextGrade = getExamScore(students.get(i), examId);

			if (nextGrade > toRet)
				toRet = nextGrade;
		}

		return toRet;
	}

	@Override
	public double getMinScore(int examId) {
		double toRet = Integer.MAX_VALUE;
		double nextGrade = 0;

		for (int i = 0; i < students.size(); i++) {
			nextGrade = getExamScore(students.get(i), examId);

			if (nextGrade < toRet)
				toRet = nextGrade;
		}

		return toRet;
	}

	@Override
	public double getAverageScore(int examId) {
		double toRet = 0;

		for (int i = 0; i < students.size(); i++) {
			toRet += getExamScore(students.get(i), examId);
		}

		return toRet / students.size();
	}

	@Override
	public void saveManager(Manager manager, String fileName) {
		try {
			FileOutputStream file = new FileOutputStream(new File(fileName));
			ObjectOutputStream out = new ObjectOutputStream(file);

	
			out.writeObject(this);

			out.close();
			file.close();

		}

		catch (IOException ex) {
		}
		
	
	}

	@Override
	public Manager restoreManager(String fileName) {
		SystemManager deserialized = null;
		try {

			FileInputStream file = new FileInputStream(new File(fileName));
			ObjectInputStream in = new ObjectInputStream(file);


			deserialized = (SystemManager) in.readObject();

			in.close();
			file.close();

		}

		catch (IOException ex) {
		} catch (ClassNotFoundException e) {
		}

		return deserialized;
	}

	// Finds a certain examID
	private Exam getExam(int ID) {
		for (int i = 0; i < exams.size(); i++) {
			if (exams.get(i).ID == ID) {
				return exams.get(i);
			}
		}
		return null;
	}

	// Finds a certain student
	private String getStudent(String get) {
		for (int i = 0; i < students.size(); i++) {
			if (students.get(i).equals(get)) {
				return students.get(i);
			}
		}
		return null;
	}

}
