package com.application.repository;

import java.util.List;

import com.domain.entity.LectureRegistration;
import com.domain.entity.SpecialLecture;

public interface LectureRegistrationRepository {
	boolean isUserAlreadyRegistered(String userId, String lectureId);  // 유저 중복 신청 확인
	int getRegistrationCount(String lectureId);  // 특강 신청 인원 확인
	void save(LectureRegistration registration);  // 특강 신청 정보 저장

	List<SpecialLecture> getLecturesByDate(String date);
}