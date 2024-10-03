package com.architecture.application.dto;

public class SpecialLectureDTO {
	private String id;
	private String name;
	private String lecturer;

	public SpecialLectureDTO(String id, String name, String lecturer) {
		this.id = id;
		this.name = name;
		this.lecturer = lecturer;
	}

	// Getters and Setters
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLecturer() {
		return lecturer;
	}

	public void setLecturer(String lecturer) {
		this.lecturer = lecturer;
	}
}
