package com.qbank.qbanksystem.jpa.subject;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.qbank.qbanksystem.common.AbstractTimestampEntity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity(name = "subject")
@Data
@EqualsAndHashCode(callSuper = true)
public class Subject extends AbstractTimestampEntity implements Serializable {
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
	@ApiModelProperty(value = "unique identifier for subject", required = false)
	private String uuid;

	@Column(name = "name", unique = true, nullable = false)
	@ApiModelProperty(value = "name of subject", required = true)
	private String name;

	@Column(name = "product_id", nullable = false)
	@ApiModelProperty(value = "unique identifier for product of the the subject", required = true)
	private Long productId;

}
