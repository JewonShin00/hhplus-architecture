package com.architecture.controller;

import com.architecture.application.dto.SpecialLectureDTO;
import com.architecture.application.service.LectureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/lectures")
public class LectureController {

	private final LectureService lectureService;

	@Autowired
	public LectureController(LectureService lectureService) {
		this.lectureService = lectureService;
	}

	// 특정 날짜에 열리는 특강 조회
	@GetMapping("/available")
	public ResponseEntity<List<SpecialLectureDTO>> getAvailableLectures(@RequestParam String date) {
		List<SpecialLectureDTO> lectures = lectureService.getAvailableLecturesByDate(date);
		return ResponseEntity.ok(lectures);
	}
}