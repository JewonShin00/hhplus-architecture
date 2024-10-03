package com.architecture.domain.entity;

import java.io.Serializable;
import java.util.Objects;

public class LectureRegistrationId implements Serializable {
	private String lectureId;
	private String userId;

	// 기본 생성자
	public LectureRegistrationId() {}

	public LectureRegistrationId(String lectureId, String userId) {
		this.lectureId = lectureId;
		this.userId = userId;
	}

	// getter/setter
	public String getLectureId() {
		return lectureId;
	}

	public String getUserId() {
		return userId;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		LectureRegistrationId that = (LectureRegistrationId) o;
		return Objects.equals(lectureId, that.lectureId) && Objects.equals(userId, that.userId);
	}

	@Override
	public int hashCode() {
		return Objects.hash(lectureId, userId);
	}
}