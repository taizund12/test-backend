package com.qbank.qbanksystem.jpa.question.keypoint;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.qbank.qbanksystem.common.AbstractTimestampEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity(name = "key_point")
@Data
@EqualsAndHashCode(callSuper = true)
public class KeyPoint extends AbstractTimestampEntity implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Long id;

	@Column(name = "key_point", nullable = false)
	private String keyPoint;

	@Column(name = "question_id", nullable = false)
	private Long questionId;

}
