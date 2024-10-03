package com.domain.entity;

public class User {
	private String userId;    // 유저ID
	private String userName;  // 유저 이름
	private String email;     // 유저 이메일 주소

	public User(String userId, String userName, String email) {
		this.userId = userId;
		this.userName = userName;
		this.email = email;
	}

	// Getter 메서드들
	public String getUserId() {
		return userId;
	}

	public String getUserName() {
		return userName;
	}

	public String getEmail() {
		return email;
	}
}
