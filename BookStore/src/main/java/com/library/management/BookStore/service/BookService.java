package com.library.management.BookStore.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.library.management.BookStore.dto.Newbookreq;
import com.library.management.BookStore.entity.Book;
import com.library.management.BookStore.modal.NewUser;

public interface BookService {

	public Page<Book> getAllBooks();

	public Page<Book> searchBooks(Pageable pageable, String query);

	public void issueBook(Long bookId, String userId);

	public void returnBook(Long bookId);

	public String register(NewUser user);

	public String save(Newbookreq newbookreq);

	public Page<Book> getAllBooks(Pageable pageable);
}
