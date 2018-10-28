package com.qbank.qbanksystem.jpa.category.api;

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
import com.qbank.qbanksystem.jpa.category.Category;
import com.qbank.qbanksystem.jpa.category.CategoryService;
import com.qbank.qbanksystem.jpa.category.CategoryWS;
import com.qbank.qbanksystem.jpa.category.dao.CategoryDao;
import com.qbank.qbanksystem.jpa.subject.Subject;
import com.qbank.qbanksystem.jpa.subject.dao.SubjectDao;

@RestController
@RequestMapping(value = Constants.V1)
public class CategoryController {

	private static final String CATEGORIES_PATH = "/categories";
	private static final String CATEGORIES_PRODUCT_ID_PATH = "/categories/subjectId/{subjectUuid}";
	private static final String CATEGORIES_ID_PATH = "/categories/{categoryUuid}";

	private CategoryService categoryService;

	@Autowired
	private CategoryDao categoryDao;

	@Autowired
	private SubjectDao subjectDao;

	@PostConstruct
	public void init() {
		Preconditions.checkNotNull(categoryService, "categoryService is required");
	}

	public void setCategoryService(CategoryService categoryService) {
		this.categoryService = categoryService;
	}

	// Get all categories
	@GetMapping(CATEGORIES_PATH)
	public ResponseEntity<List<CategoryWS>> getAllCategories() {
		List<Category> categories = StreamSupport.stream(categoryDao.findAll().spliterator(), false)
				.collect(Collectors.toList());
		return ResponseEntity.ok().body(getCategories(categories));
	}

	// Get all categories for a subject
	@GetMapping(CATEGORIES_PRODUCT_ID_PATH)
	public ResponseEntity<List<CategoryWS>> getAllCategoriesBySubjectId(
			@PathVariable(value = "subjectUuid") String subjectUuid) {
		subjectDao.findByUuid(subjectUuid)
				.orElseThrow(() -> new ResourceNotFoundException("subject", "uuid", subjectUuid));
		return ResponseEntity.ok().body(getCategories(categoryDao.findBySubjectId(subjectUuid)));
	}

	// Create a new category
	@PostMapping(CATEGORIES_PATH)
	public ResponseEntity<CategoryWS> createCategory(@Valid @RequestBody CategoryWS categoryws) {
		if (categoryDao.findByName(categoryws.getName()).isPresent()) {
			throw new ResourceAlreadyExistsException("category", "name", categoryws.getName());
		} else {
			Subject subject = subjectDao.findByUuid(categoryws.getSubjectUuid())
					.orElseThrow(() -> new ResourceNotFoundException("subject", "uuid", categoryws.getSubjectUuid()));
			Category category = new Category();
			category.setUuid(UUID.randomUUID().toString());
			category.setName(categoryws.getName());
			category.setSubjectId(subject.getId());
			categoryDao.save(category);
			return ResponseEntity.ok().body(categoryws);
		}
	}

	// Get a single category by id
	@GetMapping(CATEGORIES_ID_PATH)
	public ResponseEntity<CategoryWS> getCategoryById(@PathVariable(value = "categoryUuid") String categoryUuid) {
		Category category = categoryDao.findByUuid(categoryUuid)
				.orElseThrow(() -> new ResourceNotFoundException("category", "uuid", categoryUuid));
		return ResponseEntity.ok().body(categoryService.convertToCategoryWS(category,
				subjectDao.findById(category.getSubjectId()).get().getUuid()));
	}

	// Update a category by id
	@PutMapping(CATEGORIES_ID_PATH)
	public ResponseEntity<CategoryWS> updateCategoryById(@PathVariable(value = "categoryUuid") String categoryUuid,
			@Valid @RequestBody Category categoryDetails) {
		Category category = categoryDao.findByUuid(categoryUuid)
				.orElseThrow(() -> new ResourceNotFoundException("category", "uuid", categoryUuid));
		return ResponseEntity.ok().body(categoryService.convertToCategoryWS(category,
				subjectDao.findById(category.getSubjectId()).get().getUuid()));
	}

	// Delete a Category by id
	@DeleteMapping(CATEGORIES_ID_PATH)
	public ResponseEntity<?> deleteCategoryById(@PathVariable(value = "categoryUuid") String categoryUuid) {
		Category category = categoryDao.findByUuid(categoryUuid)
				.orElseThrow(() -> new ResourceNotFoundException("category", "uuid", categoryUuid));
		categoryDao.delete(category);
		return ResponseEntity.ok().build();
	}

	// Delete all Categories
	@DeleteMapping(CATEGORIES_PATH)
	public ResponseEntity<?> deleteAllCategories() {
		categoryDao.deleteAll();
		return ResponseEntity.ok().build();
	}

	private List<CategoryWS> getCategories(List<Category> categories) {
		List<CategoryWS> list = new ArrayList<>();
		for (Category category : categories) {
			list.add(categoryService.convertToCategoryWS(category,
					subjectDao.findById(category.getSubjectId()).get().getUuid()));
		}
		return list;
	}
}
