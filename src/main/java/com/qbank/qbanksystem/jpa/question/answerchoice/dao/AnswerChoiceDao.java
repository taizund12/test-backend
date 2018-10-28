package com.qbank.qbanksystem.jpa.question.answerchoice.dao;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.qbank.qbanksystem.jpa.question.answerchoice.AnswerChoice;

public interface AnswerChoiceDao extends CrudRepository<AnswerChoice, Long> {

	public Optional<AnswerChoice> findByAnswer(String answer);

	public Iterable<AnswerChoice> findByQuestionId(String id);
}
