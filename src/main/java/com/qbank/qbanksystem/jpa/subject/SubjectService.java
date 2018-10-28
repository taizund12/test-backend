package com.qbank.qbanksystem.jpa.subject;

import lombok.Data;

@Data
public class SubjectService {

	public SubjectWS convertToSubjectWS(Subject subject, String productUuid) {
		SubjectWS subjectws = new SubjectWS();
		subjectws.setName(subject.getName());
		subjectws.setUuid(subject.getUuid());
		subjectws.setProductUuid(productUuid);
		return subjectws;
	}

}
