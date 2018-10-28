package com.qbank.qbanksystem.jpa.category.api;

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
import com.qbank.qbanksystem.jpa.category.Category;
import com.qbank.qbanksystem.jpa.category.dao.CategoryDao;
import com.qbank.qbanksystem.jpa.subject.dao.SubjectDao;

@RestController
@RequestMapping(value = "/api/v1")
public class CategoryController {
	@Autowired
	private CategoryDao dao;

	@Autowired
	private SubjectDao subjectDao;

	// Get all categories
	@GetMapping("/categories")
	public ResponseEntity<Iterable<Category>> getAllCategorys() {
		return ResponseEntity.ok().body(dao.findAll());
	}

	// Get all categories for a subject
	@GetMapping("/categories/subjectId/{id}")
	public ResponseEntity<Iterable<Category>> getAllCategorysBySubjectId(@PathVariable(value = "id") String id) {
		return ResponseEntity.ok().body(dao.findBySubjectId(id));
	}

	// Create a new category
	@PostMapping("/categories")
	public Category createCategory(@Valid @RequestBody Category category) {
		category.setUuid(UUID.randomUUID().toString());
		subjectDao.findByUuid(category.getSubjectId())
				.orElseThrow(() -> new ResourceNotFoundException("subject", "id", category.getSubjectId()));
		if (dao.findByName(category.getName()).isPresent()) {
			throw new ResourceAlreadyExistsException("category", "name", category.getName());
		} else {
			return dao.save(category);
		}
	}

	// Get a single category by name
	@GetMapping("/categories/name/{name}")
	public ResponseEntity<Category> getCategoryByName(@PathVariable(value = "name") String name) {
		Category category = dao.findByName(name)
				.orElseThrow(() -> new ResourceNotFoundException("category", "name", name));
		return ResponseEntity.ok().body(category);
	}

	// Get a single category by id
	@GetMapping("/categories/id/{id}")
	public ResponseEntity<Category> getCategoryById(@PathVariable(value = "id") String id) {
		Category category = dao.findByUuid(id).orElseThrow(() -> new ResourceNotFoundException("category", "id", id));
		return ResponseEntity.ok().body(category);
	}

	// Update a category by name
	@PutMapping("/categories/name/{name}")
	public Category updateCategoryByName(@PathVariable(value = "name") String name,
			@Valid @RequestBody Category categoryDetails) {
		Category category = dao.findByName(name)
				.orElseThrow(() -> new ResourceNotFoundException("category", "name", name));
		return updateCategory(category, categoryDetails.getSubjectId(), categoryDetails.getName());
	}

	// Update a category by id
	@PutMapping("/categories/id/{id}")
	public Category updateCategoryById(@PathVariable(value = "id") String id,
			@Valid @RequestBody Category categoryDetails) {
		Category category = dao.findByUuid(id).orElseThrow(() -> new ResourceNotFoundException("category", "id", id));
		return updateCategory(category, categoryDetails.getSubjectId(), categoryDetails.getName());
	}

	// Delete a Category by name
	@DeleteMapping("/category/name/{name}")
	public ResponseEntity<?> deleteCategoryByName(@PathVariable(value = "name") String name) {
		Category category = dao.findByName(name)
				.orElseThrow(() -> new ResourceNotFoundException("category", "name", name));
		dao.delete(category);
		return ResponseEntity.ok().build();
	}

	// Delete a Category by id
	@DeleteMapping("/category/id/{id}")
	public ResponseEntity<?> deleteCategoryById(@PathVariable(value = "id") String id) {
		Category category = dao.findByUuid(id).orElseThrow(() -> new ResourceNotFoundException("category", "id", id));
		dao.delete(category);
		return ResponseEntity.ok().build();
	}

	// Delete all Categories
	@DeleteMapping("/categories")
	public ResponseEntity<?> deleteAllSubjects() {
		dao.deleteAll();
		return ResponseEntity.ok().build();
	}

	private Category updateCategory(Category category, String subjectId, String updatedName) {
		subjectDao.findByUuid(subjectId).orElseThrow(() -> new ResourceNotFoundException("subject", "id", subjectId));
		category.setName(updatedName);
		category.setSubjectId(subjectId);
		return dao.save(category);
	}
}
