package de.cimtag.rateyourbooks.controller;

import de.cimtag.rateyourbooks.dto.ErrorResponseDto;
import de.cimtag.rateyourbooks.exception.BookNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class BookControllerExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(BookNotFoundException.class)
  public ResponseEntity<ErrorResponseDto> handleBookNotFoundException(BookNotFoundException e) {
    ErrorResponseDto errorResponse = ErrorResponseDto.builder()
        .code(HttpStatus.NOT_FOUND)
        .title("BOOK_NOT_FOUND")
        .detail(e.getMessage())
        .build();

    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
  }

  @ExceptionHandler(Exception.class)
  public final ResponseEntity<ErrorResponseDto> handleAllExceptions(Exception e) {
    ErrorResponseDto errorResponse = ErrorResponseDto.builder()
        .code(HttpStatus.INTERNAL_SERVER_ERROR)
        .title("INTERNAL_SERVER_ERROR")
        .detail(e.getMessage())
        .build();

    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
  }
}
