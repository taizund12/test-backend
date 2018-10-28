package com.qbank.qbanksystem.jpa.product.dao;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.qbank.qbanksystem.jpa.product.Product;

public interface ProductDao extends CrudRepository<Product, Long> {

	public Optional<Product> findByUuid(String id);
	public Optional<Product> findByName(String name);

}
