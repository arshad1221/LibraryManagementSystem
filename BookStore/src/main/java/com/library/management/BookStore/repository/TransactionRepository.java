package com.library.management.BookStore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.library.management.BookStore.entity.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

	@Query("SELECT t FROM Transaction t WHERE t.book.id = :bookId ")
	Transaction findActiveTransactionByBookId(@Param("bookId") Long bookId);

}
