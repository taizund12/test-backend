package com.qbank.qbanksystem.jpa.product;

import java.util.ArrayList;
import java.util.List;

public class ProductService {

	public List<ProductWS> convertToProductWS(List<Product> products) {
		List<ProductWS> list = new ArrayList<>();
		for (Product product : products) {
			list.add(convertToProductWS(product));
		}
		return list;
	}

	public ProductWS convertToProductWS(Product product) {
		ProductWS productws = new ProductWS();
		productws.setName(product.getName());
		productws.setUuid(product.getUuid());
		return productws;
	}
}
