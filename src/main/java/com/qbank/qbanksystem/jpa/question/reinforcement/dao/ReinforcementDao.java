package com.qbank.qbanksystem.jpa.question.reinforcement.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.qbank.qbanksystem.jpa.question.reinforcement.Reinforcement;

public interface ReinforcementDao extends CrudRepository<Reinforcement, Long> {

	public Optional<Reinforcement> findByQuestion(String question);

	public List<Reinforcement> findByQuestionId(Long id);
}
