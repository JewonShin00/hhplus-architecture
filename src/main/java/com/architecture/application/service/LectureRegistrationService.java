package com.architecture.application.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.architecture.application.repository.LectureRegistrationRepository;
import com.architecture.domain.entity.LectureRegistration;
import com.architecture.domain.entity.SpecialLecture;
import com.architecture.infrastructure.repository.LectureRepository;

@Service
public class LectureRegistrationService {
	private static final Logger logger = LoggerFactory.getLogger(LectureRegistrationService.class);
	private final LectureRegistrationRepository lectureRegistrationRepository;
	private final LectureRepository lectureRepository;

	@Autowired
	public LectureRegistrationService(LectureRegistrationRepository lectureRegistrationRepository, LectureRepository lectureRepository) {
		this.lectureRegistrationRepository = lectureRegistrationRepository;
		this.lectureRepository = lectureRepository;
	}

	public void registerLecture(LectureRegistration lectureRegistration) throws Exception {
		String userId = lectureRegistration.getUserId();
		String lectureId = lectureRegistration.getLectureId();

		// 비관적 락을 통해 강의 정보를 안전하게 불러옴
		SpecialLecture lecture = lectureRepository.findByIdWithLock(lectureId)
			.orElseThrow(() -> new Exception("특강 정보를 찾을 수 없습니다."));

		// 중복 신청 여부 확인
		if (lectureRegistrationRepository.isUserAlreadyRegistered(userId, lectureId)) {
			throw new Exception("해당 유저는 이미 이 강의를 신청하셨습니다.");
		}

		// 30명 제한 확인
		if (lectureRegistrationRepository.getRegistrationCount(lectureId) >= 30) {
			throw new Exception("수강 가능 인원이 가득찼습니다.");
		}

		// 강의 신청 저장
		lectureRegistrationRepository.save(lectureRegistration);
	}

	public boolean isUserAlreadyRegistered(String userId, String lectureId) {
		return lectureRegistrationRepository.isUserAlreadyRegistered(userId, lectureId);
	}

	public List<SpecialLecture> getLecturesByDate(String date) {
		return lectureRegistrationRepository.getLecturesByDate(date);
	}
}
