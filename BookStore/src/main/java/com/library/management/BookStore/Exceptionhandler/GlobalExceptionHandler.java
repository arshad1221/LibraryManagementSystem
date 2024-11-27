package com.library.management.BookStore.Exceptionhandler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(CustomException.class)
	public ModelAndView handleCustomException(CustomException ex) {
		ModelAndView modelAndView = new ModelAndView("error"); // Redirect to error.html
		modelAndView.addObject("statusCode", HttpStatus.BAD_REQUEST.value());
		modelAndView.addObject("message", ex.getMessage());
		modelAndView.addObject("details", "A custom exception occurred.");
		return modelAndView;
	}

	@ExceptionHandler(Exception.class)
	public ModelAndView handleGlobalException(Exception ex) {
		ModelAndView modelAndView = new ModelAndView("error");
		modelAndView.addObject("statusCode", HttpStatus.INTERNAL_SERVER_ERROR.value());
		modelAndView.addObject("message", ex.getMessage());
		modelAndView.addObject("details", "An unexpected error occurred.");
		return modelAndView;
	}
}
