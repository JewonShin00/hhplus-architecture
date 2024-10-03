package com.architecture.domain.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "special_lecture")
public class SpecialLecture {

	@Id
	private String lectureId;
	private String lectureName;
	private String lectureDate;
	private String lecturer;  // 강연자 정보 필드 추가

	public SpecialLecture() {}

	public SpecialLecture(String lectureId, String lectureName, String lectureDate, String lecturer) {
		this.lectureId = lectureId;
		this.lectureName = lectureName;
		this.lectureDate = lectureDate;
		this.lecturer = lecturer;
	}

	public String getLectureId() {
		return lectureId;
	}

	public String getLectureName() {
		return lectureName;
	}

	public String getLectureDate() {
		return lectureDate;
	}

	public String getLecturer() {  // 강연자 정보 반환 메서드 추가
		return lecturer;
	}
}