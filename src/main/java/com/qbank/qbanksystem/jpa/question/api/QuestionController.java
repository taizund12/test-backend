package com.qbank.qbanksystem.jpa.question.api;

import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.qbank.qbanksystem.exception.ResourceNotFoundException;
import com.qbank.qbanksystem.jpa.category.dao.CategoryDao;
import com.qbank.qbanksystem.jpa.product.dao.ProductDao;
import com.qbank.qbanksystem.jpa.question.Question;
import com.qbank.qbanksystem.jpa.question.QuestionWS;
import com.qbank.qbanksystem.jpa.question.answerchoice.AnswerChoice;
import com.qbank.qbanksystem.jpa.question.answerchoice.dao.AnswerChoiceDao;
import com.qbank.qbanksystem.jpa.question.dao.QuestionDao;
import com.qbank.qbanksystem.jpa.question.keypoint.KeyPoint;
import com.qbank.qbanksystem.jpa.question.keypoint.dao.KeyPointDao;
import com.qbank.qbanksystem.jpa.question.reference.Reference;
import com.qbank.qbanksystem.jpa.question.reference.dao.ReferenceDao;
import com.qbank.qbanksystem.jpa.question.reinforcement.Reinforcement;
import com.qbank.qbanksystem.jpa.question.reinforcement.dao.ReinforcementDao;
import com.qbank.qbanksystem.jpa.subject.dao.SubjectDao;

@RestController
@RequestMapping(value = "/api/v1")
public class QuestionController {
	@Autowired
	private QuestionDao dao;

	@Autowired
	private ProductDao productDao;

	@Autowired
	private SubjectDao subjectDao;

	@Autowired
	private CategoryDao categoryDao;

	@Autowired
	private AnswerChoiceDao answerChoiceDao;

	@Autowired
	private ReinforcementDao reinforcementDao;

	@Autowired
	private KeyPointDao keyPointDao;

	@Autowired
	private ReferenceDao referenceDao;

	// Get all question
	@GetMapping("/questions")
	public ResponseEntity<Iterable<Question>> getAllQuestions() {
		return ResponseEntity.ok().body(dao.findAll());
	}

	// Get all question for a product
	@GetMapping("/questions/productId/{id}")
	public ResponseEntity<Iterable<Question>> getAllQuestionsForProduct(@PathVariable(value = "id") String id) {
		productDao.findByUuid(id).orElseThrow(() -> new ResourceNotFoundException("product", "id", id));
		return ResponseEntity.ok().body(dao.findByProductId(id));
	}

	// Get all question for a subject
	@GetMapping("/questions/subjectId/{id}")
	public ResponseEntity<Iterable<Question>> getAllQuestionsForSubject(@PathVariable(value = "id") String id) {
		subjectDao.findByUuid(id).orElseThrow(() -> new ResourceNotFoundException("subject", "id", id));
		return ResponseEntity.ok().body(dao.findBySubjectId(id));
	}

	// Get all question for a category
	@GetMapping("/questions/categoryId/{id}")
	public ResponseEntity<Iterable<Question>> getAllQuestionsForCategory(@PathVariable(value = "id") String id) {
		categoryDao.findByUuid(id).orElseThrow(() -> new ResourceNotFoundException("category", "id", id));
		return ResponseEntity.ok().body(dao.findByCategoryId(id));
	}

	// Get a single question by id
	@GetMapping("/questions/id/{id}")
	public ResponseEntity<Question> getQuestionById(@PathVariable(value = "id") String id) {
		Question question = dao.findByUuid(id).orElseThrow(() -> new ResourceNotFoundException("question", "id", id));
		return ResponseEntity.ok().body(question);
	}

	// Create a new question
	@PostMapping("/questions")
	public Question createSubject(@Valid @RequestBody QuestionWS questionRequest) {
		Question question = new Question();
		String questionId = UUID.randomUUID().toString();
		question.setUuid(UUID.randomUUID().toString());
		productDao.findByUuid(questionRequest.getProductId())
				.orElseThrow(() -> new ResourceNotFoundException("product", "id", questionRequest.getProductId()));
		subjectDao.findByUuid(questionRequest.getSubjectId())
				.orElseThrow(() -> new ResourceNotFoundException("subject", "id", questionRequest.getSubjectId()));
		categoryDao.findByUuid(questionRequest.getCategoryId())
				.orElseThrow(() -> new ResourceNotFoundException("category", "id", questionRequest.getCategoryId()));

		question.setAnswerIndex(questionRequest.getAnswerIndex());
		question.setExplanation(questionRequest.getExplanation());
		question.setQuestionStem(questionRequest.getQuestionStem());
		question.setLearningObjective(questionRequest.getLearningObjective());

		for (AnswerChoice answerChoice : questionRequest.getAnswerChoices()) {
			answerChoice.setQuestionId(questionId);
			answerChoiceDao.save(answerChoice);
		}

		for (KeyPoint keyPoint : questionRequest.getKeyPoints()) {
			keyPoint.setQuestionId(questionId);
			keyPointDao.save(keyPoint);
		}

		for (Reinforcement reinforcementQuestion : questionRequest.getReinforcementQuestions()) {
			reinforcementQuestion.setQuestionId(questionId);
			reinforcementDao.save(reinforcementQuestion);
		}

		for (Reference reference : questionRequest.getReferences()) {
			reference.setQuestionId(questionId);
			referenceDao.save(reference);
		}

		return dao.save(question);
	}

	// Update a question by id
	@PutMapping("/questions/id/{id}")
	public Question updateQuestionById(@PathVariable(value = "id") String id,
			@Valid @RequestBody Question updatedQuestion) {
		Question question = dao.findByUuid(id).orElseThrow(() -> new ResourceNotFoundException("question", "id", id));
		return updateQuestion(question, updatedQuestion);
	}

	private Question updateQuestion(Question question, Question updatedQuestion) {
		productDao.findByUuid(updatedQuestion.getProductId())
				.orElseThrow(() -> new ResourceNotFoundException("product", "id", updatedQuestion.getProductId()));
		subjectDao.findByUuid(updatedQuestion.getSubjectId())
				.orElseThrow(() -> new ResourceNotFoundException("subject", "id", updatedQuestion.getSubjectId()));
		categoryDao.findByUuid(updatedQuestion.getCategoryId())
				.orElseThrow(() -> new ResourceNotFoundException("category", "id", updatedQuestion.getCategoryId()));

		question.setProductId(updatedQuestion.getProductId());
		question.setSubjectId(updatedQuestion.getSubjectId());
		question.setCategoryId(updatedQuestion.getCategoryId());
		return dao.save(question);
	}
}
