package com.library.management.BookStore.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.library.management.BookStore.dto.Newbookreq;
import com.library.management.BookStore.entity.Book;
import com.library.management.BookStore.entity.Transaction;
import com.library.management.BookStore.entity.User;
import com.library.management.BookStore.modal.NewUser;
import com.library.management.BookStore.repository.BookRepository;
import com.library.management.BookStore.repository.TransactionRepository;
import com.library.management.BookStore.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class BookServiceImpl implements BookService {

	@Autowired
	private BookRepository bookRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private TransactionRepository transactionRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	public Page<Book> getAllBooks(Pageable pageable) {
		return bookRepository.findAll(pageable);
	}

	public Page<Book> searchBooks(Pageable pageable, String query) {
		return bookRepository.search(pageable, query);
	}

	@Transactional
	public void issueBook(Long bookId, String userId) {
		Book book = bookRepository.findById(bookId).orElseThrow(() -> new RuntimeException("Book not found"));
		if (book.getIsIssued())
			throw new RuntimeException("Book already issued");

		book.setIsIssued(true);
		bookRepository.save(book);

		Transaction transaction = new Transaction();
		transaction.setBook(book);
		transaction.setIssueDate(LocalDateTime.now());
		transaction.setReturnDate((transaction.getIssueDate().plusDays(7)));
		transaction.setUser(userRepository.findByUsername(userId));
		transactionRepository.save(transaction);
	}

	public void returnBook(Long bookId) {
		Book book = bookRepository.findById(bookId).orElseThrow(() -> new RuntimeException("Book not found"));
		if (!book.getIsIssued())
			throw new RuntimeException("Book is not issued");

		book.setIsIssued(false);

		bookRepository.save(book);
		LocalDateTime returndate = LocalDateTime.now();

		Transaction transaction = transactionRepository.findActiveTransactionByBookId(bookId);
		transaction.setReturnDate(returndate);
		transactionRepository.save(transaction);
	}

	@Override
	public String register(NewUser newuser) {
		// TODO Auto-generated method stub
		User u = User.builder().email(newuser.getEmail()).name(newuser.getName())
				.password(passwordEncoder.encode(newuser.getPassword())).build();
		if (u != null) {

			userRepository.save(u);
			System.out.println("user registered successfully ");

		}

		return "user registered successfully";

	}

	@Override
	public String save(Newbookreq newbookreq) {
		// TODO Auto-generated method stub

		Book b = Book.builder().author(newbookreq.getAuthor()).title(newbookreq.getTitle()).build();
		b.setIsIssued(false);
		bookRepository.save(b);
		return "Book Added successfully";
	}

	@Override
	public Page<Book> getAllBooks() {
		// TODO Auto-generated method stub
		return null;
	}

}
