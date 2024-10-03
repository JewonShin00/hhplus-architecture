package com.domain.entity;

public class LectureRegistration {

	private String lectureId; //신청한 강의ID
	private String userId;    //신청한 유저ID

	public LectureRegistration(String userId, String lectureId) {
		this.userId = userId;
		this.lectureId = lectureId;
	}

	public String getUserId() {
		return userId;
	}

	public String getLectureId() {
		return lectureId;
	}
}
