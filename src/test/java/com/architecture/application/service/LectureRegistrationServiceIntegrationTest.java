package com.architecture.application.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.architecture.application.repository.LectureRegistrationRepository;
import com.architecture.domain.entity.LectureRegistration;
import com.architecture.MyApplication;

@SpringBootTest(classes = MyApplication.class)
@Transactional
public class LectureRegistrationServiceIntegrationTest {

	@Autowired
	private LectureRegistrationService lectureRegistrationService;

	@Autowired
	private LectureRegistrationRepository lectureRegistrationRepository;

	@Test
	public void testLectureRegistration() throws Exception {
		// Given - 테스트 데이터 준비
		LectureRegistration lectureRegistration = new LectureRegistration("user1", "lecture1");

		// When - 수강신청
		lectureRegistrationService.registerLecture(lectureRegistration);

		// Then - 저장된 데이터 확인
		boolean isRegistered = lectureRegistrationRepository.isUserAlreadyRegistered("user1", "lecture1");
		assertTrue(isRegistered); // 유저가 정상적으로 등록되었는지 확인
	}

}