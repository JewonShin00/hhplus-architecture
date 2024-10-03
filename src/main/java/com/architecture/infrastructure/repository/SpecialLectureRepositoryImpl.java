package com.architecture.infrastructure.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.architecture.application.repository.SpecialLectureRepository;
import com.architecture.domain.entity.SpecialLecture;

@Repository
public class SpecialLectureRepositoryImpl extends SpecialLectureRepository {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<SpecialLecture> findLecturesByDate(String date) {
		String query = "SELECT sl FROM SpecialLecture sl WHERE sl.lectureDate = :date";
		TypedQuery<SpecialLecture> typedQuery = entityManager.createQuery(query, SpecialLecture.class);
		typedQuery.setParameter("date", date);
		return typedQuery.getResultList();
	}
}
