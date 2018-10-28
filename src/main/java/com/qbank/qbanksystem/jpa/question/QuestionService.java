package com.qbank.qbanksystem.jpa.question;

import java.util.List;
import java.util.stream.Collectors;

import com.qbank.qbanksystem.jpa.question.answerchoice.AnswerChoice;
import com.qbank.qbanksystem.jpa.question.answerchoice.AnswerChoiceWS;
import com.qbank.qbanksystem.jpa.question.keypoint.KeyPoint;
import com.qbank.qbanksystem.jpa.question.keypoint.KeyPointWS;
import com.qbank.qbanksystem.jpa.question.reference.Reference;
import com.qbank.qbanksystem.jpa.question.reference.ReferenceWS;
import com.qbank.qbanksystem.jpa.question.reinforcement.Reinforcement;
import com.qbank.qbanksystem.jpa.question.reinforcement.ReinforcementWS;

public class QuestionService {

	public QuestionWS convertToQuestionWs(Question question, List<AnswerChoice> answerChoices,
			List<Reinforcement> reinforcements, List<KeyPoint> keypoints, List<Reference> references, String productId,
			String subjectId, String categoryId) {
		QuestionWS questionWs = new QuestionWS();

		questionWs.setAnswerChoices(answerChoices.stream().map(this::convertToAnswerWs).collect(Collectors.toList()));
		questionWs.setReinforcementQuestions(
				reinforcements.stream().map(this::convertToReinforcementWS).collect(Collectors.toList()));
		questionWs.setKeyPoints(keypoints.stream().map(this::convertToKeyPointWS).collect(Collectors.toList()));
		questionWs.setReferences(references.stream().map(this::convertToReferenceWS).collect(Collectors.toList()));

		questionWs.setCategoryId(categoryId);
		questionWs.setExplanation(question.getExplanation());
		questionWs.setLearningObjective(question.getLearningObjective());
		questionWs.setProductId(productId);
		questionWs.setQuestionId(question.getUuid());
		questionWs.setQuestionStem(question.getQuestionStem());
		return questionWs;

	}

	private AnswerChoiceWS convertToAnswerWs(AnswerChoice answer) {
		AnswerChoiceWS answerWs = new AnswerChoiceWS();
		answerWs.setChoice(answer.getChoice());
		answerWs.setCorrect(answer.getCorrect());
		return answerWs;
	}

	private ReinforcementWS convertToReinforcementWS(Reinforcement reinforcement) {
		ReinforcementWS reinforcementWs = new ReinforcementWS();
		reinforcementWs.setAnswer(reinforcement.getAnswer());
		reinforcementWs.setQuestion(reinforcement.getQuestion());
		return reinforcementWs;
	}

	private KeyPointWS convertToKeyPointWS(KeyPoint keypoint) {
		KeyPointWS keypointWs = new KeyPointWS();
		keypointWs.setKeyPoint(keypoint.getKeyPoint());
		return keypointWs;
	}

	private ReferenceWS convertToReferenceWS(Reference reference) {
		ReferenceWS referenceWs = new ReferenceWS();
		referenceWs.setReference(reference.getReference());
		return referenceWs;
	}
}
