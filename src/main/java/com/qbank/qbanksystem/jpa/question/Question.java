package com.qbank.qbanksystem.jpa.question;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.qbank.qbanksystem.common.AbstractTimestampEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity(name = "question")
@Data
@EqualsAndHashCode(callSuper = true)
public class Question extends AbstractTimestampEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

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

	@Column(name = "question_stem", nullable = false)
	private String questionStem;

	@Column(name = "answer_index", nullable = false)
	private Long answerIndex;

	@Column(name = "explanation", nullable = false)
	private String explanation;

}
