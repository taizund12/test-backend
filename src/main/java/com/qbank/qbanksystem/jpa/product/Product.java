package com.qbank.qbanksystem.jpa.product;

import static com.qbank.qbanksystem.jpa.util.Constants.Tables.PRODUCT;

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
@Entity(name = PRODUCT)
public class Product extends AbstractTimestampEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Long id;

	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "uuid", nullable = false, unique = true, updatable = false, length = 36)
	private String uuid;

	@Column(name = "name", unique = true, nullable = false)
	private String name;
}
