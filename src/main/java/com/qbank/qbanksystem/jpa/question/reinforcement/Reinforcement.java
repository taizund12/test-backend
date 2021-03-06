package com.qbank.qbanksystem.jpa.question.reinforcement;

import static com.qbank.qbanksystem.jpa.util.Constants.Tables.REINFORCEMENT;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.qbank.qbanksystem.common.AbstractTimestampEntity;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity(name = REINFORCEMENT)
public class Reinforcement extends AbstractTimestampEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Long id;

	@Column(name = "question", unique = true, nullable = false)
	private String question;

	@Column(name = "answer", nullable = false)
	private String answer;

	@Column(name = "question_id", nullable = false)
	private Long questionId;
}
