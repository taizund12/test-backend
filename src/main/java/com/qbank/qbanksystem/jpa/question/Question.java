package com.qbank.qbanksystem.jpa.question;

import static com.qbank.qbanksystem.jpa.util.Constants.Tables.QUESTIONS;

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
@Entity(name = QUESTIONS)
public class Question extends AbstractTimestampEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Long id;

	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "uuid", nullable = false, unique = true, updatable = false, length = 36)
	private String uuid;

	@Column(name = "product_id", nullable = false)
	private Long productId;

	@Column(name = "subject_id", nullable = false)
	private Long subjectId;

	@Column(name = "category_id", nullable = false)
	private Long categoryId;

	@Column(name = "learning_objective", nullable = false)
	private String learningObjective;

	@Column(name = "question_stem", unique = true, nullable = false)
	private String questionStem;

	@Column(name = "explanation", nullable = false)
	private String explanation;
}
