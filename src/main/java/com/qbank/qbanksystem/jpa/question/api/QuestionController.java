package com.qbank.qbanksystem.jpa.question.api;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.qbank.qbanksystem.exception.ResourceAlreadyExistsException;
import com.qbank.qbanksystem.exception.ResourceNotFoundException;
import com.qbank.qbanksystem.jpa.category.Category;
import com.qbank.qbanksystem.jpa.category.dao.CategoryDao;
import com.qbank.qbanksystem.jpa.product.Product;
import com.qbank.qbanksystem.jpa.product.dao.ProductDao;
import com.qbank.qbanksystem.jpa.question.Question;
import com.qbank.qbanksystem.jpa.question.QuestionWS;
import com.qbank.qbanksystem.jpa.question.answerchoice.AnswerChoice;
import com.qbank.qbanksystem.jpa.question.answerchoice.AnswerChoiceWS;
import com.qbank.qbanksystem.jpa.question.answerchoice.dao.AnswerChoiceDao;
import com.qbank.qbanksystem.jpa.question.dao.QuestionDao;
import com.qbank.qbanksystem.jpa.question.keypoint.KeyPoint;
import com.qbank.qbanksystem.jpa.question.keypoint.KeyPointWS;
import com.qbank.qbanksystem.jpa.question.keypoint.dao.KeyPointDao;
import com.qbank.qbanksystem.jpa.question.reference.Reference;
import com.qbank.qbanksystem.jpa.question.reference.ReferenceWS;
import com.qbank.qbanksystem.jpa.question.reference.dao.ReferenceDao;
import com.qbank.qbanksystem.jpa.question.reinforcement.Reinforcement;
import com.qbank.qbanksystem.jpa.question.reinforcement.ReinforcementWS;
import com.qbank.qbanksystem.jpa.question.reinforcement.dao.ReinforcementDao;
import com.qbank.qbanksystem.jpa.subject.Subject;
import com.qbank.qbanksystem.jpa.subject.dao.SubjectDao;

@RestController
@RequestMapping(value = "/api/v1")
public class QuestionController {

	private static final String QUESTIONS_PATH = "/questions";
	private static final String QUESTIONS_ID_PATH = "/questions/{questionUuid}";
	private static final String QUESTIONS_PRODUCT_PATH = "/questions/productId/{productUuid}";
	private static final String QUESTIONS_SUBJECT_PATH = "/questions/productId/{subjectUuid}";
	private static final String QUESTIONS_CATEGORY_PATH = "/questions/categoryId/{categoryUuid}";

	@Autowired
	private QuestionDao questionDao;

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

	// Get all question for a product
	@GetMapping(QUESTIONS_PRODUCT_PATH)
	public ResponseEntity<Iterable<Question>> getAllQuestionsForProduct(
			@PathVariable(value = "productUuid") String productUuid) {
		Product product = productDao.findByUuid(productUuid)
				.orElseThrow(() -> new ResourceNotFoundException("product", "uuid", productUuid));
		return ResponseEntity.ok().body(questionDao.findByProductId(product.getId()));
	}

	// Get all question for a subject
	@GetMapping(QUESTIONS_SUBJECT_PATH)
	public ResponseEntity<Iterable<Question>> getAllQuestionsForSubject(
			@PathVariable(value = "subjectUuid") String subjectUuid) {
		Subject subject = subjectDao.findByUuid(subjectUuid)
				.orElseThrow(() -> new ResourceNotFoundException("subject", "uuid", subjectUuid));
		return ResponseEntity.ok().body(questionDao.findBySubjectId(subject.getId()));
	}

	// Get all question for a category
	@GetMapping(QUESTIONS_CATEGORY_PATH)
	public ResponseEntity<Iterable<Question>> getAllQuestionsForCategory(
			@PathVariable(value = "categoryUuid") String categoryUuid) {
		Category category = categoryDao.findByUuid(categoryUuid)
				.orElseThrow(() -> new ResourceNotFoundException("category", "uuid", categoryUuid));
		return ResponseEntity.ok().body(questionDao.findByCategoryId(category.getId()));
	}

	// Get a single question by id
	@GetMapping(QUESTIONS_ID_PATH)
	public ResponseEntity<Question> getQuestionById(@PathVariable(value = "questionUuid") String questionUuid) {
		Question question = questionDao.findByUuid(questionUuid)
				.orElseThrow(() -> new ResourceNotFoundException("question", "uuid", questionUuid));
		return ResponseEntity.ok().body(question);
	}

	// Create a new question
	@PostMapping(QUESTIONS_PATH)
	public QuestionWS createSubject(@Valid @RequestBody QuestionWS questionRequest) {
		if (questionDao.findByQuestionStem(questionRequest.getQuestionStem()).isPresent()) {
			throw new ResourceAlreadyExistsException("question", "stem", questionRequest.getQuestionStem());
		} else {

		}

		Question question = new Question();
		String id = UUID.randomUUID().toString();
		question.setUuid(id);
		questionRequest.setQuestionId(id);

		Product product = productDao.findByUuid(questionRequest.getProductId())
				.orElseThrow(() -> new ResourceNotFoundException("product", "id", questionRequest.getProductId()));
		question.setProductId(product.getId());

		Subject subject = subjectDao.findByUuid(questionRequest.getSubjectId())
				.orElseThrow(() -> new ResourceNotFoundException("subject", "id", questionRequest.getSubjectId()));
		question.setSubjectId(subject.getId());

		Category category = categoryDao.findByUuid(questionRequest.getCategoryId())
				.orElseThrow(() -> new ResourceNotFoundException("category", "id", questionRequest.getCategoryId()));
		question.setCategoryId(category.getId());

		question.setExplanation(questionRequest.getExplanation());
		question.setQuestionStem(questionRequest.getQuestionStem());
		question.setLearningObjective(questionRequest.getLearningObjective());

		List<Reinforcement> reinforcementList = new ArrayList<>();
		for (ReinforcementWS reinforcementQuestionWs : questionRequest.getReinforcementQuestions()) {
			if (reinforcementDao.findByQuestion(reinforcementQuestionWs.getQuestion()).isPresent()) {
				throw new ResourceAlreadyExistsException("reinforcement", "question",
						reinforcementQuestionWs.getQuestion());
			} else {
				Reinforcement reinforcement = new Reinforcement();
				reinforcement.setAnswer(reinforcementQuestionWs.getAnswer());
				reinforcement.setQuestion(reinforcementQuestionWs.getQuestion());
				reinforcementList.add(reinforcement);
			}
		}

		Long questionId = questionDao.save(question).getId();

		for (Reinforcement reinforcement : reinforcementList) {
			reinforcement.setQuestionId(questionId);
			reinforcementDao.save(reinforcement);
		}

		for (ReferenceWS referenceWs : questionRequest.getReferences()) {
			Reference reference = new Reference();
			reference.setReference(referenceWs.getReference());
			reference.setQuestionId(questionId);
			referenceDao.save(reference);
		}

		for (AnswerChoiceWS answerChoiceWs : questionRequest.getAnswerChoices()) {
			AnswerChoice answerChoice = new AnswerChoice();
			answerChoice.setChoice(answerChoiceWs.getChoice());
			answerChoice.setCorrect(answerChoiceWs.isCorrect());
			answerChoice.setQuestionId(questionId);
			answerChoiceDao.save(answerChoice);
		}

		for (KeyPointWS keyPointWs : questionRequest.getKeyPoints()) {
			KeyPoint keyPoint = new KeyPoint();
			keyPoint.setKeyPoint(keyPointWs.getKeyPoint());
			keyPoint.setQuestionId(questionId);
			keyPointDao.save(keyPoint);
		}

		return questionRequest;
	}
}
