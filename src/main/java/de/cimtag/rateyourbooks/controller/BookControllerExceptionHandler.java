package de.cimtag.rateyourbooks.controller;

import de.cimtag.rateyourbooks.dto.ErrorResponseDto;
import de.cimtag.rateyourbooks.exception.BookNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


/**
 * Global exception handler for the BookController.
 * <p>
 * This class handles specific exceptions such as BookNotFoundException and generic exceptions, providing appropriate HTTP status codes and error responses.
 * </p>
 *
 * @author Niklas Witzel
 */
@RestControllerAdvice
@Slf4j
public class BookControllerExceptionHandler extends ResponseEntityExceptionHandler {

  /**
   * Handles BookNotFoundException and returns a 404 Not Found response.
   *
   * @param e the exception thrown when a book is not found
   * @return a ResponseEntity containing an {@link ErrorResponseDto} with details of the error
   */
  @ExceptionHandler(BookNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public ResponseEntity<ErrorResponseDto> handleBookNotFoundException(BookNotFoundException e) {
    log.warn("BookNotFoundException: {}", e.getMessage());

    ErrorResponseDto errorResponse = ErrorResponseDto.builder()
        .code(HttpStatus.NOT_FOUND)
        .title("BOOK_NOT_FOUND")
        .detail(e.getMessage())
        .build();

    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
  }

  /**
   * Handles all other exceptions and returns a 500 Internal Server Error response.
   *
   * @param e the exception thrown
   * @return a ResponseEntity containing an {@link ErrorResponseDto} with details of the error
   */
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  @ExceptionHandler(Exception.class)
  public final ResponseEntity<ErrorResponseDto> handleAllExceptions(Exception e) {
    log.error("Exception: {}", e.getMessage(), e);

    ErrorResponseDto errorResponse = ErrorResponseDto.builder()
        .code(HttpStatus.INTERNAL_SERVER_ERROR)
        .title("INTERNAL_SERVER_ERROR")
        .detail(e.getMessage())
        .build();

    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
  }
}
