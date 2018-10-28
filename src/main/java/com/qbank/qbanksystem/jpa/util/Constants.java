package com.qbank.qbanksystem.jpa.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Constants {

	@NoArgsConstructor(access = AccessLevel.PRIVATE)
	public static final class Tables {
		public static final String PRODUCT = "product";
		public static final String CATEGORY = "category";
		public static final String ANSWER_CHOICES = "answer_choices";
		public static final String KEY_POINTS = "key_points";
		public static final String REFERENCE = "reference";
		public static final String REINFORCEMENT = "reinforcement";
		public static final String QUESTIONS = "questions";
	}
}
