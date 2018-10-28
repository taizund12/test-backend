package com.qbank.qbanksystem.jpa.question.dao;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.qbank.qbanksystem.jpa.question.Question;

public interface QuestionDao extends CrudRepository<Question, Long> {
	public Optional<Question> findByUuid(String id);

	public Iterable<Question> findByProductId(String id);

	public Iterable<Question> findBySubjectId(String id);

	public Iterable<Question> findByCategoryId(String id);
}
