package com.qbank.qbanksystem.jpa.question.keypoint.dao;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.qbank.qbanksystem.jpa.question.keypoint.KeyPoint;
import com.qbank.qbanksystem.jpa.subject.Subject;

public interface KeyPointDao extends CrudRepository<KeyPoint, Long> {
	public Optional<Subject> findByKeyPoint(String keyPoint);

	public Optional<Subject> findByUuid(String id);

	public Iterable<Subject> findByQuestionId(String id);
}
