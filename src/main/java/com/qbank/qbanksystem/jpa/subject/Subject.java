package com.qbank.qbanksystem.jpa.subject;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.qbank.qbanksystem.common.AbstractTimestampEntity;

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
	private String uuid;

	@Column(name = "name", unique = true, nullable = false)
	private String name;

	@Column(name = "product_id", nullable = false)
	private Long productId;

}
