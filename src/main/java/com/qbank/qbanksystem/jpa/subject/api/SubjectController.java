package com.qbank.qbanksystem.jpa.subject.api;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import javax.annotation.PostConstruct;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.base.Preconditions;
import com.qbank.qbanksystem.common.Constants;
import com.qbank.qbanksystem.exception.ResourceAlreadyExistsException;
import com.qbank.qbanksystem.exception.ResourceNotFoundException;
import com.qbank.qbanksystem.jpa.product.Product;
import com.qbank.qbanksystem.jpa.product.dao.ProductDao;
import com.qbank.qbanksystem.jpa.subject.Subject;
import com.qbank.qbanksystem.jpa.subject.SubjectService;
import com.qbank.qbanksystem.jpa.subject.SubjectWS;
import com.qbank.qbanksystem.jpa.subject.dao.SubjectDao;

@RestController
@RequestMapping(value = Constants.V1)
public class SubjectController {

	private static final String SUBJECTS_PATH = "/subjects";
	private static final String SUBJECTS_PRODUCT_ID_PATH = "/subjects/productId/{productUuid}";
	private static final String SUBJECTS_ID_PATH = "/subjects/{subjectUuid}";

	private SubjectService subjectService;

	@Autowired
	private SubjectDao subjectDao;

	@Autowired
	private ProductDao productDao;

	@PostConstruct
	public void init() {
		Preconditions.checkNotNull(subjectService, "subjectService is required");
	}

	public void setSubjectService(SubjectService subjectService) {
		this.subjectService = subjectService;
	}

	// Get all subjects
	@GetMapping(SUBJECTS_PATH)
	public ResponseEntity<List<SubjectWS>> getAllSubjects() {
		List<Subject> subjects = StreamSupport.stream(subjectDao.findAll().spliterator(), false)
				.collect(Collectors.toList());
		return ResponseEntity.ok().body(getSubjects(subjects));
	}

	// Get all subjects for a product
	@GetMapping(SUBJECTS_PRODUCT_ID_PATH)
	public ResponseEntity<List<SubjectWS>> getAllSubjectsByProductId(
			@PathVariable(value = "productUuid") String productUuid) {
		productDao.findByUuid(productUuid)
				.orElseThrow(() -> new ResourceNotFoundException("product", "uuid", productUuid));
		return ResponseEntity.ok().body(getSubjects(subjectDao.findByProductId(productUuid)));
	}

	// Create a new subject
	@PostMapping(SUBJECTS_PATH)
	public ResponseEntity<SubjectWS> createSubject(@Valid @RequestBody SubjectWS subjectws) {
		if (subjectDao.findByName(subjectws.getName()).isPresent()) {
			throw new ResourceAlreadyExistsException("subject", "name", subjectws.getName());
		} else {
			Product product = productDao.findByUuid(subjectws.getProductUuid())
					.orElseThrow(() -> new ResourceNotFoundException("product", "uuid", subjectws.getProductUuid()));
			Subject subject = new Subject();
			subject.setUuid(UUID.randomUUID().toString());
			subject.setName(subjectws.getName());
			subject.setProductId(product.getId());
			subjectDao.save(subject);
			return ResponseEntity.ok().body(subjectws);
		}
	}

	// Get a single subject by id
	@GetMapping(SUBJECTS_ID_PATH)
	public ResponseEntity<SubjectWS> getSubjectById(@PathVariable(value = "subjectUuid") String subjectUuid) {
		Subject subject = subjectDao.findByUuid(subjectUuid)
				.orElseThrow(() -> new ResourceNotFoundException("subject", "uuid", subjectUuid));
		return ResponseEntity.ok().body(subjectService.convertToSubjectWS(subject,
				productDao.findById(subject.getProductId()).get().getUuid()));
	}

	// Update a subject by id
	@PutMapping(SUBJECTS_ID_PATH)
	public ResponseEntity<SubjectWS> updateSubjectById(@PathVariable(value = "subjectUuid") String subjectUuid,
			@Valid @RequestBody Subject subjectDetails) {
		Subject subject = subjectDao.findByUuid(subjectUuid)
				.orElseThrow(() -> new ResourceNotFoundException("subject", "uuid", subjectUuid));
		return ResponseEntity.ok().body(subjectService.convertToSubjectWS(subject,
				productDao.findById(subject.getProductId()).get().getUuid()));
	}

	// Delete a Subject by id
	@DeleteMapping(SUBJECTS_ID_PATH)
	public ResponseEntity<?> deleteSubjectById(@PathVariable(value = "subjectUuid") String subjectUuid) {
		Subject subject = subjectDao.findByUuid(subjectUuid)
				.orElseThrow(() -> new ResourceNotFoundException("subject", "uuid", subjectUuid));
		subjectDao.delete(subject);
		return ResponseEntity.ok().build();
	}

	// Delete all Subjects
	@DeleteMapping(SUBJECTS_PATH)
	public ResponseEntity<?> deleteAllSubjects() {
		subjectDao.deleteAll();
		return ResponseEntity.ok().build();
	}

	private List<SubjectWS> getSubjects(List<Subject> subjects) {
		List<SubjectWS> list = new ArrayList<>();
		for (Subject subject : subjects) {
			list.add(subjectService.convertToSubjectWS(subject,
					productDao.findById(subject.getProductId()).get().getUuid()));
		}
		return list;
	}
}
