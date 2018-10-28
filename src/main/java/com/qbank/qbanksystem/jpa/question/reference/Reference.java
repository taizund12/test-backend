package com.qbank.qbanksystem.jpa.question.reference;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.qbank.qbanksystem.common.AbstractTimestampEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity(name = "reference")
@Data
@EqualsAndHashCode(callSuper = true)
public class Reference extends AbstractTimestampEntity implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Long id;

	@Column(name = "reference", nullable = false)
	private String reference;

	@Column(name = "question_id", nullable = false)
	private Long questionId;
}
