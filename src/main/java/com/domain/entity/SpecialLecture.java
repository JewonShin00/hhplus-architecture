package com.domain.entity;

public class SpecialLecture {

	private String lectureId;	//강의ID
	private String lectureName;	//강의명
	private String lectureDate; //특강일

	public SpecialLecture(String lectureId, String lectureName, String lectureDate) {
		this.lectureId = lectureId;
		this.lectureName = lectureName;
		this.lectureDate = lectureDate;
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
}
