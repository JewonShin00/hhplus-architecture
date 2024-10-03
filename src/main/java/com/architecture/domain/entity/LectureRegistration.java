package com.architecture.domain.entity;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
@Entity
@Table(name = "LectureRegistration")
public class LectureRegistration {

	@EmbeddedId
	@Access(AccessType.FIELD)
	private LectureRegistrationId id;

	// 기본 생성자
	public LectureRegistration() {}

	public LectureRegistration(String userId, String lectureId) {
		this.id = new LectureRegistrationId(lectureId, userId);
	}

	// getter
	public String getUserId() {
		return id.getUserId();
	}

	public String getLectureId() {
		return id.getLectureId();
	}
}
