package com.architecture.infrastructure.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.architecture.application.repository.LectureRegistrationRepository;
import com.architecture.domain.entity.LectureRegistration;
import com.architecture.domain.entity.LectureRegistrationId;
import com.architecture.domain.entity.SpecialLecture;

@Repository
public class LectureRegistrationRepositoryImpl implements LectureRegistrationRepository {
	@PersistenceContext
	private EntityManager entityManager; //데이터베이스와 상호작용

	@Override
	public boolean isUserAlreadyRegistered(String userId, String lectureId) {
		// JPQL을 사용한 쿼리 작성
		LectureRegistrationId id = new LectureRegistrationId(lectureId, userId);
		String query = "SELECT lr FROM LectureRegistration lr WHERE lr.id = :id"; // id 필드를 사용
		TypedQuery<LectureRegistration> typedQuery = entityManager.createQuery(query, LectureRegistration.class);
		typedQuery.setParameter("id", id); // id 객체로 파라미터 설정
		return !typedQuery.getResultList().isEmpty();
	}

	@Override
	public int getRegistrationCount(String lectureId) {
		// 실제 구현체 작성
		return 0; // 예시로 0 반환
	}

	@Override
	public void save(LectureRegistration registration) {
		// 실제 구현체 작성
		entityManager.persist(registration);
	}

	@Override
	public List<SpecialLecture> getLecturesByDate(String date) {
		// 실제 구현체 작성
		return null;
	}

	@Override
	public void deleteAll() {

	}
}
