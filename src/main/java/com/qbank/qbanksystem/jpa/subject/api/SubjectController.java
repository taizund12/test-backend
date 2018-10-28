package com.qbank.qbanksystem.jpa.subject.api;

import java.util.UUID;

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

import com.qbank.qbanksystem.exception.ResourceAlreadyExistsException;
import com.qbank.qbanksystem.exception.ResourceNotFoundException;
import com.qbank.qbanksystem.jpa.product.dao.ProductDao;
import com.qbank.qbanksystem.jpa.subject.Subject;
import com.qbank.qbanksystem.jpa.subject.dao.SubjectDao;

@RestController
@RequestMapping(value = "/api/v1")
public class SubjectController {
	@Autowired
	private SubjectDao subjectDao;

	@Autowired
	private ProductDao productDao;

	// Get all subjects
	@GetMapping("/subjects")
	public ResponseEntity<Iterable<Subject>> getAllSubjects() {
		return ResponseEntity.ok().body(subjectDao.findAll());
	}

	// Get all subjects for a product
	@GetMapping("/subjects/productId/{id}")
	public ResponseEntity<Iterable<Subject>> getAllSubjectsByProductId(@PathVariable(value = "id") String id) {
		productDao.findByUuid(id).orElseThrow(() -> new ResourceNotFoundException("product", "id", id));
		return ResponseEntity.ok().body(subjectDao.findByProductId(id));
	}

	// Create a new subject
	@PostMapping("/subjects")
	public Subject createSubject(@Valid @RequestBody Subject subject) {
		subject.setUuid(UUID.randomUUID().toString());
		productDao.findByUuid(subject.getProductId())
				.orElseThrow(() -> new ResourceNotFoundException("product", "id", subject.getProductId()));
		if (subjectDao.findByName(subject.getName()).isPresent()) {
			throw new ResourceAlreadyExistsException("subject", "name", subject.getName());
		} else {
			return subjectDao.save(subject);
		}
	}

	// Get a single subject by name
	@GetMapping("/subjects/name/{name}")
	public ResponseEntity<Subject> getSubjectByName(@PathVariable(value = "name") String name) {
		Subject subject = subjectDao.findByName(name)
				.orElseThrow(() -> new ResourceNotFoundException("subject", "name", name));
		return ResponseEntity.ok().body(subject);
	}

	// Get a single subject by id
	@GetMapping("/subjects/id/{id}")
	public ResponseEntity<Subject> getSubjectById(@PathVariable(value = "id") String id) {
		Subject subject = subjectDao.findByUuid(id).orElseThrow(() -> new ResourceNotFoundException("subject", "id", id));
		return ResponseEntity.ok().body(subject);
	}

	// Update a subject by name
	@PutMapping("/subjects/name/{name}")
	public Subject updateSubjectByName(@PathVariable(value = "name") String name,
			@Valid @RequestBody Subject subjectDetails) {
		Subject subject = subjectDao.findByName(name)
				.orElseThrow(() -> new ResourceNotFoundException("subject", "name", name));
		return updateSubject(subject, subjectDetails.getProductId(), subjectDetails.getName());
	}

	// Update a subject by id
	@PutMapping("/subjects/id/{id}")
	public Subject updateSubjectById(@PathVariable(value = "id") String id,
			@Valid @RequestBody Subject subjectDetails) {
		Subject subject = subjectDao.findByUuid(id).orElseThrow(() -> new ResourceNotFoundException("subject", "id", id));
		return updateSubject(subject, subjectDetails.getProductId(), subjectDetails.getName());
	}

	// Delete a Subject by name
	@DeleteMapping("/subject/name/{name}")
	public ResponseEntity<?> deleteSubjectByName(@PathVariable(value = "name") String name) {
		Subject subject = subjectDao.findByName(name)
				.orElseThrow(() -> new ResourceNotFoundException("subject", "name", name));
		subjectDao.delete(subject);
		return ResponseEntity.ok().build();
	}

	// Delete a Subject by id
	@DeleteMapping("/subject/id/{id}")
	public ResponseEntity<?> deleteSubjectById(@PathVariable(value = "id") String id) {
		Subject subject = subjectDao.findByUuid(id).orElseThrow(() -> new ResourceNotFoundException("subject", "id", id));
		subjectDao.delete(subject);
		return ResponseEntity.ok().build();
	}

	// Delete all Subjects
	@DeleteMapping("/subject")
	public ResponseEntity<?> deleteAllSubjects() {
		subjectDao.deleteAll();
		return ResponseEntity.ok().build();
	}

	private Subject updateSubject(Subject subject, String productId, String updatedName) {
		productDao.findByUuid(productId).orElseThrow(() -> new ResourceNotFoundException("product", "id", productId));
		subject.setName(updatedName);
		subject.setProductId(productId);
		return subjectDao.save(subject);
	}
}
