package de.cimtag.rateyourbooks.exception;

public class BookNotFoundException extends RuntimeException {

  public BookNotFoundException(String message) {
    super(message);
  }
}
