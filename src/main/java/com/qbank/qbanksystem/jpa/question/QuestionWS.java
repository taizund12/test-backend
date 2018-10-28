package com.qbank.qbanksystem.jpa.question;

import java.util.List;

import com.qbank.qbanksystem.jpa.question.answerchoice.AnswerChoiceWS;
import com.qbank.qbanksystem.jpa.question.keypoint.KeyPointWS;
import com.qbank.qbanksystem.jpa.question.reference.ReferenceWS;
import com.qbank.qbanksystem.jpa.question.reinforcement.ReinforcementWS;

import lombok.Data;

@Data
public class QuestionWS {
	private String questionId;

	private String productId;

	private String subjectId;

	private String categoryId;

	private String learningObjective;

	private String questionStem;

	private List<AnswerChoiceWS> answerChoices;

	private String explanation;

	private List<ReinforcementWS> reinforcementQuestions;

	private List<KeyPointWS> keyPoints;

	private List<ReferenceWS> references;

}
