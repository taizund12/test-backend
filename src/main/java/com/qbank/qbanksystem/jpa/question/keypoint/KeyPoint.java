package com.qbank.qbanksystem.jpa.question.keypoint;

import static com.qbank.qbanksystem.jpa.util.Constants.Tables.KEY_POINTS;

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
@Entity(name = KEY_POINTS)
public class KeyPoint extends AbstractTimestampEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Long id;

	@Column(name = "key_point", nullable = false)
	private String keyPoint;

	@Column(name = "question_id", nullable = false)
	private Long questionId;
}
