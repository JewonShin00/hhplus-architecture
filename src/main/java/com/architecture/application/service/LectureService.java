package com.architecture.application.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.architecture.application.dto.SpecialLectureDTO;
import com.architecture.application.repository.SpecialLectureRepository;
import com.architecture.domain.entity.SpecialLecture;

@Service
public class LectureService {

	@Autowired
	private SpecialLectureRepository specialLectureRepository;

	public List<SpecialLectureDTO> getAvailableLecturesByDate(String date) {
		List<SpecialLecture> lectures = specialLectureRepository.findLecturesByDate(date);
		return lectures.stream()
			.map(this::convertToDTO)
			.collect(Collectors.toList());
	}

	private SpecialLectureDTO convertToDTO(SpecialLecture lecture) {
		return new SpecialLectureDTO(lecture.getLectureId(), lecture.getLectureName(), lecture.getLecturer());
	}
}
