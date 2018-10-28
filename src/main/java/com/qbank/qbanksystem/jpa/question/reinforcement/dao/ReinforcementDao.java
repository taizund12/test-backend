package com.qbank.qbanksystem.jpa.question.reinforcement.dao;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.qbank.qbanksystem.jpa.question.reinforcement.Reinforcement;
import com.qbank.qbanksystem.jpa.subject.Subject;

public interface ReinforcementDao extends CrudRepository<Reinforcement, Long> {

	public Optional<Subject> findByUuid(String id);

	public Iterable<Subject> findByQuestionId(String id);
}
