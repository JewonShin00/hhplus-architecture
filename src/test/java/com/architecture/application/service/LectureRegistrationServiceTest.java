package com.architecture.application.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.architecture.application.repository.LectureRegistrationRepository;
import com.architecture.domain.entity.LectureRegistration;
import com.architecture.domain.entity.SpecialLecture;
import com.architecture.infrastructure.repository.LectureRepository;

public class LectureRegistrationServiceTest {
	private LectureRegistrationService lectureRegistrationService;
	private LectureRegistrationRepository lectureRegistrationRepository;
	private LectureRepository lectureRepository;

	@BeforeEach
	public void setUp() {
		lectureRegistrationRepository = Mockito.mock(LectureRegistrationRepository.class);
		lectureRepository = Mockito.mock(LectureRepository.class);  // lectureRepository를 Mock으로 초기화
		lectureRegistrationService = new LectureRegistrationService(lectureRegistrationRepository, lectureRepository);
	}

	@DisplayName("수강신청 테스트_기본")
	@Test
	public void testRegisterLecture() throws Exception {
		// 수강신청 할 강의와 수강신청하는 유저 세팅
		String userId = "user1";
		String lectureId = "lecture1";
		LectureRegistration lectureRegistration = new LectureRegistration(userId, lectureId);

		// 수강신청
		lectureRegistrationService.registerLecture(lectureRegistration);

		// 신청됐는지 확인(저장됐는지 확인)
		verify(lectureRegistrationRepository).save(any(LectureRegistration.class));
	}
	@DisplayName("수강신청 테스트_중복신청방지(이미 신청한 강의일 경우 예외처리)")
	@Test
	public void testRemoveLectureAlreadyRegistered() throws Exception{
		// 수강신청 할 강의와 수강신청하는 유저 세팅
		String userId = "user1";
		String lectureId = "lecture1";
		LectureRegistration lectureRegistration = new LectureRegistration(userId, lectureId);

		// 이미 해당 강의 신청했다고 가정
		when(lectureRegistrationRepository.isUserAlreadyRegistered(userId, lectureId)).thenReturn(true);

		// 중복 신청 예외처리
		Exception exception = assertThrows(Exception.class, () -> {
			lectureRegistrationService.registerLecture(lectureRegistration);
		});

		// 예외 메시지 확인
		assertEquals("해당 유저는 이미 이 강의를 신청하셨습니다.", exception.getMessage());

		// 저장소에 save 메서드가 호출되지 않아야 함
		verify(lectureRegistrationRepository, never()).save(any(LectureRegistration.class));

	}
	@DisplayName("수강신청 테스트_인원제한테스트(해당 강의 기신청인원이 30명 이상이면 예외처리)")
	@Test
	public void testLectureFullRegistration() throws Exception{
		// 수강신청 할 강의와 수강신청하는 유저 세팅
		String userId = "user1";
		String lectureId = "lecture1";
		LectureRegistration lectureRegistration = new LectureRegistration(userId, lectureId);

		// 해당 강의의 신청 인원이 30명인 상태로 설정 (Mock 설정)
		when(lectureRegistrationRepository.getRegistrationCount(lectureId)).thenReturn(30);

		// 인원 제한 예외 테스트
		Exception exception = assertThrows(Exception.class, () -> {
			lectureRegistrationService.registerLecture(lectureRegistration);
		});

		// 예외 메시지 확인
		assertEquals("수강 가능 인원이 가득찼습니다.", exception.getMessage());

		// 저장소에 save 메서드가 호출되지 않아야 함
		verify(lectureRegistrationRepository, never()).save(any(LectureRegistration.class));
	}

	@DisplayName("수강신청 테스트_인원제한테스트(해당 강의 기신청인원이 30명 이상이면 예외처리)")
	@Test
	public void testUserAlreadyRegistered() throws Exception{
		// 수강신청 할 강의와 수강신청하는 유저 세팅
		String userId = "user1";
		String lectureId = "lecture1";

		// 이미 신청한 상태로 설정 (Mock 설정)
		when(lectureRegistrationRepository.isUserAlreadyRegistered(userId, lectureId)).thenReturn(true);

		// 신청 여부 확인
		boolean isRegistered = lectureRegistrationService.isUserAlreadyRegistered(userId, lectureId);

		// 신청 여부 확인 결과 테스트
		assertTrue(isRegistered);

	}
	@DisplayName("특정 날짜의 특강 목록 조회 테스트")
	@Test
	public void testGetLecturesByDate() {
		// 날짜 설정 (조회할 날짜)
		String date = "2024-10-10";

		// Mock 설정: 해당 날짜에 열리는 특강 목록 반환
		List<SpecialLecture> expectedLectures = Arrays.asList(
			new SpecialLecture("lecture1", "특강 1", date, "111111"),
			new SpecialLecture("lecture2", "특강 2", date, "222222")
		);
		when(lectureRegistrationRepository.getLecturesByDate(date)).thenReturn(expectedLectures);

		// 서비스 호출 및 결과 확인
		List<SpecialLecture> lectures = lectureRegistrationService.getLecturesByDate(date);
		assertEquals(2, lectures.size());
		assertEquals("특강 1", lectures.get(0).getLectureName());
		assertEquals("특강 2", lectures.get(1).getLectureName());
	}

	@Nested
	@DisplayName("동시성 테스트")
	class ConcurrencyTests {

		private static final Logger logger = LoggerFactory.getLogger(ConcurrencyTests.class);  // Logger 추가

		@BeforeEach
		public void setUp() {
			logger.info("동시성 테스트를 위한 환경 설정 중...");
			lectureRegistrationService = new LectureRegistrationService(lectureRegistrationRepository, lectureRepository);
			SpecialLecture lecture = new SpecialLecture("lecture1", "강의1", "2024-10-03", "강사1");
			lectureRepository.save(lecture);
			logger.info("특강 {}을 DB에 저장했습니다.", lecture.getLectureId());
		}

		@Test
		@DisplayName("수강신청 테스트_동시성 테스트")
		public void testLectureRegistrationConcurrency() throws InterruptedException {
			String lectureId = "lecture1";
			int numberOfThreads = 50;
			ExecutorService executorService = Executors.newFixedThreadPool(numberOfThreads);
			CountDownLatch latch = new CountDownLatch(numberOfThreads);

			logger.info("동시성 테스트 시작: {}명의 사용자", numberOfThreads);

			for (int i = 0; i < numberOfThreads; i++) {
				final int userId = i;
				executorService.submit(() -> {
					try {
						logger.info("유저 {}가 특강 {}에 신청 시도 중", userId, lectureId);
						LectureRegistration lectureRegistration = new LectureRegistration(String.valueOf(userId), lectureId);
						lectureRegistrationService.registerLecture(lectureRegistration);
					} catch (Exception e) {
						logger.error("유저 {}가 특강 {}에 신청 중 예외 발생: {}", userId, lectureId, e.getMessage());
					} finally {
						latch.countDown();
					}
				});
			}

			latch.await();
			logger.info("동시성 테스트 완료");

			long registrationCount = lectureRegistrationRepository.getRegistrationCount(lectureId);
			logger.info("최종 특강 {} 신청 인원: {}", lectureId, registrationCount);
			assertEquals(30, registrationCount, "동시에 신청한 사용자 수는 30명을 초과할 수 없습니다.");
		}
	}

}
