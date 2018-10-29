package com.qbank.qbanksystem.jpa.question.reference;

import static com.qbank.qbanksystem.jpa.util.Constants.Tables.REFERENCE;

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
@Entity(name = REFERENCE)
public class Reference extends AbstractTimestampEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Long id;

	@Column(name = "reference", nullable = false)
	private String reference;

	@Column(name = "question_id", nullable = false)
	private Long questionId;
}
