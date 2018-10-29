package com.qbank.qbanksystem.jpa.question.answerchoice;

import static com.qbank.qbanksystem.jpa.util.Constants.Tables.ANSWER_CHOICES;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;
import lombok.EqualsAndHashCode;

import org.hibernate.annotations.Type;

import com.qbank.qbanksystem.common.AbstractTimestampEntity;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity(name = ANSWER_CHOICES)
public class AnswerChoice extends AbstractTimestampEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Long id;

	@Column(name = "choice", nullable = false)
	private String choice;

	@Column(name = "correct", nullable = false)
	@Type(type = "org.hibernate.type.NumericBooleanType")
	private boolean correct = false;

	@Column(name = "question_id", nullable = false)
	private Long questionId;
}
