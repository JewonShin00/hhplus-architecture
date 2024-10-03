package com.architecture.presentation.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.architecture.application.dto.SpecialLectureDTO;
import com.architecture.application.service.LectureService;

@RestController
@RequestMapping("/lectures")
public class LectureController {

	@Autowired
	private LectureService lectureService;

	@GetMapping
	public ResponseEntity<List<SpecialLectureDTO>> getAvailableLectures(@RequestParam String date) {
		List<SpecialLectureDTO> availableLectures = lectureService.getAvailableLecturesByDate(date);
		return ResponseEntity.ok(availableLectures);
	}
}