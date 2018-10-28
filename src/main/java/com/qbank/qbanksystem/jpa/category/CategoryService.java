package com.qbank.qbanksystem.jpa.category;

import lombok.Data;

@Data
public class CategoryService {

	public CategoryWS convertToCategoryWS(Category category, String subjectUuid) {
		CategoryWS categoryws = new CategoryWS();
		categoryws.setName(category.getName());
		categoryws.setUuid(category.getUuid());
		categoryws.setSubjectUuid(subjectUuid);
		return categoryws;
	}

}
