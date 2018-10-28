package com.qbank.qbanksystem.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.qbank.qbanksystem.jpa.category.CategoryService;
import com.qbank.qbanksystem.jpa.category.api.CategoryController;
import com.qbank.qbanksystem.jpa.product.ProductService;
import com.qbank.qbanksystem.jpa.product.api.ProductController;
import com.qbank.qbanksystem.jpa.subject.SubjectService;
import com.qbank.qbanksystem.jpa.subject.api.SubjectController;

@Configuration
public class ProductConfig {

	@Bean
	public ProductController productController() {
		ProductController productController = new ProductController();
		productController.setProductService(productService());
		return productController;
	}

	@Bean
	public ProductService productService() {
		return new ProductService();
	}

	@Bean
	public SubjectController subjectController() {
		SubjectController subjectController = new SubjectController();
		subjectController.setSubjectService(subjectService());
		return subjectController;
	}

	@Bean
	public SubjectService subjectService() {
		return new SubjectService();
	}

	@Bean
	public CategoryController categoryController() {
		CategoryController categoryController = new CategoryController();
		categoryController.setCategoryService(categoryService());
		return categoryController;
	}

	@Bean
	public CategoryService categoryService() {
		return new CategoryService();
	}

}
