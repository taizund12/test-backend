package com.qbank.qbanksystem.jpa.question;

import java.util.List;

import com.qbank.qbanksystem.jpa.question.answerchoice.AnswerChoice;
import com.qbank.qbanksystem.jpa.question.answerchoice.AnswerChoiceWS;
import com.qbank.qbanksystem.jpa.question.keypoint.KeyPoint;
import com.qbank.qbanksystem.jpa.question.reference.Reference;
import com.qbank.qbanksystem.jpa.question.reinforcement.Reinforcement;

public class QuestionWS {
	private String productId;

	private String subjectId;

	private String categoryId;

	private String learningObjective;

	private String questionStem;

	private List<AnswerChoiceWS> answerChoices;

	private int answerIndex;

	private String explanation;

	private List<Reinforcement> reinforcementQuestions;

	private List<KeyPoint> keyPoints;

	private List<Reference> references;

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(String subjectId) {
		this.subjectId = subjectId;
	}

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	public String getLearningObjective() {
		return learningObjective;
	}

	public void setLearningObjective(String learningObjective) {
		this.learningObjective = learningObjective;
	}

	public String getQuestionStem() {
		return questionStem;
	}

	public void setQuestionStem(String questionStem) {
		this.questionStem = questionStem;
	}

	public List<AnswerChoice> getAnswerChoices() {
		return answerChoices;
	}

	public void setAnswerChoices(List<AnswerChoice> answerChoices) {
		this.answerChoices = answerChoices;
	}

	public int getAnswerIndex() {
		return answerIndex;
	}

	public void setAnswerIndex(int answerIndex) {
		this.answerIndex = answerIndex;
	}

	public String getExplanation() {
		return explanation;
	}

	public void setExplanation(String explanation) {
		this.explanation = explanation;
	}

	public List<Reinforcement> getReinforcementQuestions() {
		return reinforcementQuestions;
	}

	public void setReinforcementQuestions(List<Reinforcement> reinforcementQuestions) {
		this.reinforcementQuestions = reinforcementQuestions;
	}

	public List<KeyPoint> getKeyPoints() {
		return keyPoints;
	}

	public void setKeyPoints(List<KeyPoint> keyPoints) {
		this.keyPoints = keyPoints;
	}

	public List<Reference> getReferences() {
		return references;
	}

	public void setReferences(List<Reference> references) {
		this.references = references;
	}

}
