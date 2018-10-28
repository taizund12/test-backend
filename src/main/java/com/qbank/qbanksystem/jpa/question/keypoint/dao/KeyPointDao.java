package com.qbank.qbanksystem.jpa.question.keypoint.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.qbank.qbanksystem.jpa.question.keypoint.KeyPoint;

public interface KeyPointDao extends CrudRepository<KeyPoint, Long> {

	public Optional<KeyPoint> findByKeyPoint(String keyPoint);

	public List<KeyPoint> findByQuestionId(Long id);
}
