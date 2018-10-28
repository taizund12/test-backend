package com.qbank.qbanksystem.jpa.category.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.qbank.qbanksystem.jpa.category.Category;

public interface CategoryDao extends CrudRepository<Category, Long> {

	Optional<Category> findByName(String name);

	Optional<Category> findByUuid(String id);

	List<Category> findBySubjectId(String id);
}
