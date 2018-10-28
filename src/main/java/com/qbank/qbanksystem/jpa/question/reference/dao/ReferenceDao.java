package com.qbank.qbanksystem.jpa.question.reference.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.qbank.qbanksystem.jpa.question.reference.Reference;

public interface ReferenceDao extends CrudRepository<Reference, Long> {

	public Optional<Reference> findByQuestion(String question);

	public List<Reference> findByQuestionId(String id);
}
