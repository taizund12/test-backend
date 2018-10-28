package com.qbank.qbanksystem.jpa.question.reinforcement;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.qbank.qbanksystem.common.AbstractTimestampEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity(name = "reinforcement")
@Data
@EqualsAndHashCode(callSuper = true)
public class Reinforcement extends AbstractTimestampEntity implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Long id;

	@Column(name = "question", unique = true, nullable = false)
	private String question;

	@Column(name = "answer", nullable = false)
	private String answer;

	@Column(name = "question_id", nullable = false)
	private String questionId;

}
