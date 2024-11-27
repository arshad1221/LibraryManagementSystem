package com.library.management.BookStore.dto;

public class ReturnRequest {

	private Long bookId;

	// Constructors
	public ReturnRequest() {
	}

	public ReturnRequest(Long bookId) {
		this.bookId = bookId;
	}

	// Getters and Setters
	public Long getBookId() {
		return bookId;
	}

	public void setBookId(Long bookId) {
		this.bookId = bookId;
	}
}
