package com.qbank.qbanksystem.jpa.product.api;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import javax.annotation.PostConstruct;
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

import com.google.common.base.Preconditions;
import com.qbank.qbanksystem.common.Constants;
import com.qbank.qbanksystem.exception.ResourceAlreadyExistsException;
import com.qbank.qbanksystem.exception.ResourceNotFoundException;
import com.qbank.qbanksystem.jpa.product.Product;
import com.qbank.qbanksystem.jpa.product.ProductService;
import com.qbank.qbanksystem.jpa.product.ProductWS;
import com.qbank.qbanksystem.jpa.product.dao.ProductDao;

@RestController
@RequestMapping(value = Constants.V1)
public class ProductController {

	private static final String PRODUCTS_PATH = "/products";
	private static final String PRODUCT_ID_PATH = "/products/{productUuid}";

	private ProductService productService;

	@Autowired
	private ProductDao dao;

	@PostConstruct
	public void init() {
		Preconditions.checkNotNull(productService, "productService is required");
	}

	public void setProductService(ProductService productService) {
		this.productService = productService;
	}

	@GetMapping(PRODUCTS_PATH)
	public ResponseEntity<List<ProductWS>> getAllProducts() {
		return ResponseEntity.ok().body(productService.convertToProductWS(
				StreamSupport.stream(dao.findAll().spliterator(), false).collect(Collectors.toList())));
	}

	// Create a new product
	@PostMapping(PRODUCTS_PATH)
	public ResponseEntity<ProductWS> createProduct(@Valid @RequestBody ProductWS productws) {
		if (dao.findByName(productws.getName()).isPresent()) {
			throw new ResourceAlreadyExistsException("product", "name", productws.getName());
		} else {
			Product product = new Product();
			product.setUuid(UUID.randomUUID().toString());
			product.setName(productws.getName());
			return ResponseEntity.ok().body(productService.convertToProductWS(dao.save(product)));
		}
	}

	// Get a single product by id
	@GetMapping(PRODUCT_ID_PATH)
	public ResponseEntity<ProductWS> getProductById(@PathVariable(value = "productUuid") String productUuid) {
		Product product = dao.findByUuid(productUuid)
				.orElseThrow(() -> new ResourceNotFoundException("product", "uuid", productUuid));
		return ResponseEntity.ok().body(productService.convertToProductWS(product));
	}

	// Update a product by id
	@PutMapping(PRODUCT_ID_PATH)
	public ResponseEntity<ProductWS> updateProductById(@PathVariable(value = "productUuid") String productUuid,
			@Valid @RequestBody ProductWS productws) {
		Product product = dao.findByUuid(productUuid)
				.orElseThrow(() -> new ResourceNotFoundException("product", "uuid", productUuid));
		product.setName(productws.getName());
		return ResponseEntity.ok().body(productService.convertToProductWS(dao.save(product)));
	}

	// Delete a Product by id
	@DeleteMapping(PRODUCT_ID_PATH)
	public ResponseEntity<?> deleteProductById(@PathVariable(value = "productUuid") String productUuid) {
		Product product = dao.findByUuid(productUuid)
				.orElseThrow(() -> new ResourceNotFoundException("product", "uuid", productUuid));
		dao.delete(product);
		return ResponseEntity.ok().build();
	}
}
