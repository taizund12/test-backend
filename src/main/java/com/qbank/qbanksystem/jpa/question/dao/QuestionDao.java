package com.qbank.qbanksystem.jpa.question.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.qbank.qbanksystem.jpa.question.Question;

public interface QuestionDao extends CrudRepository<Question, Long> {
	public Optional<Question> findByQuestionStem(String stem);

	public Optional<Question> findByUuid(String id);

	public List<Question> findByProductId(Long id);

	public List<Question> findBySubjectId(Long id);

	public List<Question> findByCategoryId(Long id);
}
