package com.architecture.infrastructure.repository;

import java.util.Optional;

import javax.persistence.LockModeType;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.architecture.domain.entity.SpecialLecture;

public interface LectureRepository extends JpaRepository<SpecialLecture, String> {

	@Lock(LockModeType.PESSIMISTIC_WRITE)  // 비관적 락 적용
	@Query("SELECT sl FROM SpecialLecture sl WHERE sl.lectureId = :lectureId")
	Optional<SpecialLecture> findByIdWithLock(@Param("lectureId") String lectureId);
}
