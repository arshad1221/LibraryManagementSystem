package com.library.management.BookStore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.library.management.BookStore.Exceptionhandler.CustomException;
import com.library.management.BookStore.dto.Newbookreq;
import com.library.management.BookStore.entity.Book;
import com.library.management.BookStore.modal.NewUser;
import com.library.management.BookStore.service.BookService;

@Controller
public class BookController {

	@Autowired
	private BookService bookService;

	@GetMapping("/test")
	public String test() {
		System.out.println("working");
		return "working fine";
	}

	@GetMapping("/registerstudent")
	public String showAvailableBooks(Model model) {
		model.addAttribute("allbooklist", bookService.getAllBooks()); // List of books
		model.addAttribute("newuser", new NewUser()); // Add an empty NewUser object
		return "registerstudent"; // Renders the availablebooks.html template
	}

	@GetMapping("/books")
	public String viewHomePage(@RequestParam(defaultValue = "0") int page, Model model) {
		int pageSize = 10; // Number of books per page
		Pageable pageable = PageRequest.of(page, pageSize);
		Page<Book> bookPage = bookService.getAllBooks(pageable);

		model.addAttribute("allbooklist", bookPage.getContent()); // List of books for the current page
		model.addAttribute("currentPage", page); // Current page number
		model.addAttribute("totalPages", bookPage.getTotalPages()); // Total number of pages

		return "homepage"; // Name of the Thymeleaf template
	}

	/*
	 * @GetMapping("/search") public List<Book> searchBooks(@RequestParam String
	 * query) { return bookService.searchBooks(query); }
	 */
//	@PostMapping("/issue")
//	public ResponseEntity<String> issueBook(@RequestBody IssueRequest request) {
//		bookService.issueBook(request.getBookId(), request.getUserId());
//		return ResponseEntity.ok("Book issued successfully!");
//	}
	/*
	 * @PostMapping("/return") public ResponseEntity<String> returnBook(@RequestBody
	 * ReturnRequest request) { bookService.returnBook(request.getBookId()); return
	 * ResponseEntity.ok("Book returned successfully!"); }
	 */

	/*
	 * @GetMapping("/") public String showIndexPage(Model model) {
	 * model.addAttribute("newuser", new NewUser()); return "index"; }
	 */

	@GetMapping("/register")
	public String showRegisterStudentPage(Model model) {
		model.addAttribute("newuser", new NewUser()); // Add a blank NewUser object
		return "registerstudent"; // Render the registerstudent.html template
	}

	@PostMapping("/save")
	public String saveEmployee(@ModelAttribute("newuser") NewUser newuser) {
		bookService.register(newuser);
		return "redirect:/";
	}

	// issueBook

	@PostMapping("/issue")
	public String issueBook(@RequestParam("bookId") Long bookId, @RequestParam("userId") Long userId,
			RedirectAttributes redirectAttributes) {
		try {
			bookService.issueBook(bookId, userId); // Pass both IDs to the service method
			redirectAttributes.addFlashAttribute("message", "Book issued successfully to user ID: " + userId);

		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("error", "Failed to issue the book: " + e.getMessage());
		}
		return "redirect:/books"; // Redirect back to the list
	}

	// return book

	@PostMapping("/return")
	public String returnBook(@RequestParam("bookId") Long bookId, RedirectAttributes redirectAttributes) {
		try {
			bookService.returnBook(bookId); // Call the service method to return the book
			redirectAttributes.addFlashAttribute("message", "Book returned successfully!");
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("error", "Failed to return the book: " + e.getMessage());
		}
		return "redirect:/books"; // Redirect back to the list
	}

	// searchBook

	@GetMapping("/search")
	public String searchBooks(@RequestParam(defaultValue = "0") int page, @RequestParam("query") String query,
			Model model) {

		// Update the list based on search results
		// Preserve the search query

		int pageSize = 10; // Number of books per page
		Pageable pageable = PageRequest.of(page, pageSize);
		Page<Book> bookPage = (Page<Book>) bookService.searchBooks(pageable, query);

		model.addAttribute("allbooklist", bookPage.getContent()); // List of books for the current page
		model.addAttribute("currentPage", page); // Current page number
		model.addAttribute("totalPages", bookPage.getTotalPages()); // Total number of pages

		return "homepage";
	}

	// login

	@GetMapping("/login")
	public String loginPage() {
		return "login";
	}

	@PostMapping("/add")
	@PreAuthorize("hasRole('ADMIN')")
	public String addBook(@RequestParam String title, @RequestParam String author,
			RedirectAttributes redirectAttributes) {
		// Logic to save the book to the database
		Newbookreq book = new Newbookreq();
		book.setTitle(title);
		book.setAuthor(author);
		bookService.save(book);
		redirectAttributes.addFlashAttribute("successMessage", "Book added successfully!");

		return "redirect:/books";
	}

	@GetMapping("/testException")
	public String testException() throws CustomException {
		throw new CustomException("This is a custom exception message!");
	}

}
