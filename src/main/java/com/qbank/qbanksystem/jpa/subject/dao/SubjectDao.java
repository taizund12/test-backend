package com.qbank.qbanksystem.jpa.subject.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.qbank.qbanksystem.jpa.subject.Subject;

public interface SubjectDao extends CrudRepository<Subject, Long> {

	public Optional<Subject> findByName(String name);

	public Optional<Subject> findByUuid(String id);

	public List<Subject> findByProductId(String id);
}
