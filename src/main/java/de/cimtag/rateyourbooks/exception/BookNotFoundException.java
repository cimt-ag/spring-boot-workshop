package de.cimtag.rateyourbooks.exception;

/**
 * Exception thrown when a book is not found.
 *
 * @author Niklas Witzel
 */
public class BookNotFoundException extends RuntimeException {

  /**
   * Constructs a new BookNotFoundException with the specified detail message.
   *
   * @param message the detail message
   */
  public BookNotFoundException(String message) {
    super(message);
  }
}
