package com.architecture.presentation.controller;

import com.architecture.application.service.LectureRegistrationService;
import com.architecture.domain.entity.LectureRegistration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/lectures")
public class LectureRegistrationController {

	private final LectureRegistrationService lectureRegistrationService;

	@Autowired
	public LectureRegistrationController(LectureRegistrationService lectureRegistrationService) {
		this.lectureRegistrationService = lectureRegistrationService;
	}

	// 특강 신청
	@PostMapping("/register")
	public ResponseEntity<String> registerLecture(@RequestBody LectureRegistration registration) {
		try {
			lectureRegistrationService.registerLecture(registration);
			return ResponseEntity.ok("특강 신청이 완료되었습니다.");
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	// 사용자가 이미 신청한 특강인지 확인
	@GetMapping("/check-registration")
	public ResponseEntity<Boolean> checkUserRegistration(@RequestParam String userId, @RequestParam String lectureId) {
		boolean isRegistered = lectureRegistrationService.isUserAlreadyRegistered(userId, lectureId);
		return ResponseEntity.ok(isRegistered);
	}
}