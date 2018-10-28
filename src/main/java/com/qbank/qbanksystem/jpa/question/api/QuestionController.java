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

import com.google.common.base.Preconditions;
import com.qbank.qbanksystem.exception.ResourceAlreadyExistsException;
import com.qbank.qbanksystem.exception.ResourceNotFoundException;
import com.qbank.qbanksystem.jpa.category.Category;
import com.qbank.qbanksystem.jpa.category.dao.CategoryDao;
import com.qbank.qbanksystem.jpa.product.Product;
import com.qbank.qbanksystem.jpa.product.dao.ProductDao;
import com.qbank.qbanksystem.jpa.question.Question;
import com.qbank.qbanksystem.jpa.question.QuestionService;
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

	private QuestionService questionService;

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

	public void init() {
		Preconditions.checkNotNull(questionService, "questionService is required");
	}

	public void setQuestionService(QuestionService questionService) {
		this.questionService = questionService;
	}

	// Get all question for a product
	@GetMapping(QUESTIONS_PRODUCT_PATH)
	public ResponseEntity<List<QuestionWS>> getAllQuestionsForProduct(
			@PathVariable(value = "productUuid") String productUuid) {
		Product product = productDao.findByUuid(productUuid)
				.orElseThrow(() -> new ResourceNotFoundException("product", "uuid", productUuid));
		List<Question> questions = questionDao.findByProductId(product.getId());
		List<QuestionWS> questionWsList = new ArrayList<>();
		for (Question question : questions) {
			questionWsList.add(getQuestionWs(question, product.getUuid(),
					subjectDao.findById(question.getSubjectId()).get().getUuid(),
					categoryDao.findById(question.getCategoryId()).get().getUuid()));
		}
		return ResponseEntity.ok().body(questionWsList);
	}

	// Get a single question by id
	@GetMapping(QUESTIONS_ID_PATH)
	public ResponseEntity<QuestionWS> getQuestionById(@PathVariable(value = "questionUuid") String questionUuid) {
		Question question = questionDao.findByUuid(questionUuid)
				.orElseThrow(() -> new ResourceNotFoundException("question", "uuid", questionUuid));

		List<AnswerChoice> answerChoices = answerChoiceDao.findByQuestionId(question.getId());
		List<Reinforcement> reinforcements = reinforcementDao.findByQuestionId(question.getId());
		List<KeyPoint> keypoints = keyPointDao.findByQuestionId(question.getId());
		List<Reference> references = referenceDao.findByQuestionId(question.getId());
		String productId = productDao.findById(question.getProductId()).get().getUuid();
		String subjectId = subjectDao.findById(question.getSubjectId()).get().getUuid();
		String categoryId = categoryDao.findById(question.getCategoryId()).get().getUuid();
		QuestionWS quetionWs = questionService.convertToQuestionWs(question, answerChoices, reinforcements, keypoints,
				references, productId, subjectId, categoryId);
		return ResponseEntity.ok().body(quetionWs);
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

	private QuestionWS getQuestionWs(Question question, String productId, String subjectId, String categoryId) {
		return questionService.convertToQuestionWs(question, answerChoiceDao.findByQuestionId(question.getId()),
				reinforcementDao.findByQuestionId(question.getId()), keyPointDao.findByQuestionId(question.getId()),
				referenceDao.findByQuestionId(question.getId()), productId, subjectId, categoryId);
	}
}
