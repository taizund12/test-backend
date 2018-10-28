package com.qbank.qbanksystem.jpa.product.api;

import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.qbank.qbanksystem.exception.ResourceAlreadyExistsException;
import com.qbank.qbanksystem.exception.ResourceNotFoundException;
import com.qbank.qbanksystem.jpa.product.Product;
import com.qbank.qbanksystem.jpa.product.ProductWS;
import com.qbank.qbanksystem.jpa.product.dao.ProductDao;

@RestController
@RequestMapping(value = "/api/v1")
public class ProductController {

	@Autowired
	private ProductDao dao;

	@GetMapping("/products")
	public ResponseEntity<Iterable<ProductWS>> getAllProducts() {
		return ResponseEntity.ok().body(dao.findAll());
	}

	// Create a new product
	@PostMapping("/products")
	public ProductWS createProduct(@Valid @RequestBody ProductWS productws) {

		productws.setUuid(UUID.randomUUID().toString());
		if (dao.findByName(productws.getName()).isPresent()) {
			throw new ResourceAlreadyExistsException("product", "name", productws.getName());
		} else {
			return dao.save(productws);
		}
	}

	// Get a single product by id
	@GetMapping("/products/{productUuid}")
	public ResponseEntity<ProductWS> getProductById(@PathVariable(value = "productUuid") String productUuid) {
		Product product = dao.findByUuid(productUuid)
				.orElseThrow(() -> new ResourceNotFoundException("product", "id", productUuid));
		return ResponseEntity.ok().body(product);
	}

	// Update a product by id
	@PutMapping("/products/{productUuid}")
	public Product updateProductById(@PathVariable(value = "productUuid") String productUuid,
			@Valid @RequestBody Product productDetails) {
		Product product = dao.findByUuid(id).orElseThrow(() -> new ResourceNotFoundException("product", "id", id));
		product.setName(productDetails.getName());
		return dao.save(product);
	}

	// Delete a Product by name
	@DeleteMapping("/product/{name}")
	public ResponseEntity<?> deleteProductByName(@PathVariable(value = "name") String name) {
		Product product = dao.findByName(name)
				.orElseThrow(() -> new ResourceNotFoundException("product", "name", name));
		dao.delete(product);
		return ResponseEntity.ok().build();
	}

	// Delete a Product by id
	@DeleteMapping("/product/{id}")
	public ResponseEntity<?> deleteProductById(@PathVariable(value = "id") String id) {
		Product product = dao.findByUuid(id).orElseThrow(() -> new ResourceNotFoundException("product", "id", id));
		dao.delete(product);
		return ResponseEntity.ok().build();
	}

	private Product createProduct(ProductWS product) {

	}
}
