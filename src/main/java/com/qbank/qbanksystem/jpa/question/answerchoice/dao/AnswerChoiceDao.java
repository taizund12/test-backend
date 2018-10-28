package com.qbank.qbanksystem.jpa.question.answerchoice.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.qbank.qbanksystem.jpa.question.answerchoice.AnswerChoice;

public interface AnswerChoiceDao extends CrudRepository<AnswerChoice, Long> {

	public AnswerChoice findByChoice(String choice);

	public List<AnswerChoice> findByQuestionId(Long id);
}
