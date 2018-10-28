package com.qbank.qbanksystem.jpa.question.reinforcement.dao;

import org.springframework.data.repository.CrudRepository;

import com.qbank.qbanksystem.jpa.question.reinforcement.Reinforcement;

public interface ReinforcementDao extends CrudRepository<Reinforcement, Long> {

	public Reinforcement findByReinforcement(String reinforcement);

	public Iterable<Reinforcement> findByQuestionId(String id);
}
