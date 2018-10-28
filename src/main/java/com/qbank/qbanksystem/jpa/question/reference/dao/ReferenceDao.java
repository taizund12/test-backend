package com.qbank.qbanksystem.jpa.question.reference.dao;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.qbank.qbanksystem.jpa.question.reference.Reference;

public interface ReferenceDao extends CrudRepository<Reference, Long> {
	public Optional<Reference> findByReference(String reference);

	public Optional<Reference> findByUuid(String id);

	public Iterable<Reference> findByQuestionId(String id);
}
