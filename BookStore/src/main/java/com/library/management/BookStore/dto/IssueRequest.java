package com.library.management.BookStore.dto;

public class IssueRequest {

	private Long bookId;
	private Long userId;

	// Constructors
	public IssueRequest() {
	}

	public IssueRequest(Long bookId, Long userId) {
		this.bookId = bookId;
		this.userId = userId;
	}

	// Getters and Setters
	public Long getBookId() {
		return bookId;
	}

	public void setBookId(Long bookId) {
		this.bookId = bookId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}
}
