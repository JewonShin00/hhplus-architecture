package com.architecture.application.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.architecture.application.repository.LectureRegistrationRepository;
import com.architecture.domain.entity.LectureRegistration;
import com.architecture.domain.entity.SpecialLecture;
@Service
public class LectureRegistrationService {

	private final LectureRegistrationRepository lectureRegistrationRepository;
	@Autowired
	public LectureRegistrationService(LectureRegistrationRepository lectureRegistrationRepository) {
		this.lectureRegistrationRepository = lectureRegistrationRepository;
	}
	//특강신청
	public void registerLecture(LectureRegistration lectureRegistration) throws Exception {
		String userId = lectureRegistration.getUserId();
		String lectureId = lectureRegistration.getLectureId();

		// 해당 강의 중복 신청 여부 확인
		if (lectureRegistrationRepository.isUserAlreadyRegistered(userId, lectureId)) {
			throw new Exception("해당 유저는 이미 이 강의를 신청하셨습니다.");
		}
		// 30명 제한 확인
		if (lectureRegistrationRepository.getRegistrationCount(lectureId) >= 30) {
			throw new Exception("수강 가능 인원이 가득찼습니다.");
		}
		// 저장소에 신청 정보 저장
		lectureRegistrationRepository.save(lectureRegistration);
	}
	// 특강 기 신청 여부 확인
	public boolean isUserAlreadyRegistered(String userId, String lectureId) {
		return lectureRegistrationRepository.isUserAlreadyRegistered(userId, lectureId);
	}
	//특정 날짜의 특강 목록 조회
	public List<SpecialLecture> getLecturesByDate(String date) {
		return lectureRegistrationRepository.getLecturesByDate(date);
	}
}
