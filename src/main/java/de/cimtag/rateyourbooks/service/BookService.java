package de.cimtag.rateyourbooks.service;

import de.cimtag.rateyourbooks.dto.BookDto;
import java.util.List;

/**
 * Service interface for managing books.
 * <p>
 * This interface defines the methods for various operations related to books, such as finding, creating, updating, and deleting books.
 * </p>
 *
 * @author Niklas Witzel
 */
public interface BookService {

  /**
   * Finds a book by its ID.
   *
   * @param id the ID of the book to find
   * @return the found book as a BookDto
   */
  BookDto findBookById(Long id);

  /**
   * Finds a book by its title.
   *
   * @param title the title of the book to find
   * @return the found book as a BookDto
   */
  BookDto findBookByTitle(String title);

  /**
   * Finds a book by its title and author.
   *
   * @param title  the title of the book to find
   * @param author the author of the book to find
   * @return the found book as a BookDto
   */
  BookDto findBookByTitleAndAuthor(String title, String author);

  /**
   * Finds all books by a specific author.
   *
   * @param author the author of the books to find
   * @return a list of books by the specified author
   */
  List<BookDto> findAllBooksByAuthor(String author);

  /**
   * Finds all books.
   *
   * @return a list of all books
   */
  List<BookDto> findAllBooks();

  /**
   * Creates a new book.
   *
   * @param bookDto the book data transfer object containing the details of the book to create
   * @return the created book as a BookDto
   */
  BookDto createBook(BookDto bookDto);

  /**
   * Deletes a book by its ID.
   *
   * @param id the ID of the book to delete
   */
  void deleteBook(Long id);

  /**
   * Updates an existing book.
   *
   * @param id             the ID of the book to update
   * @param updatedBookDto the book data transfer object containing the updated details of the book
   * @return the updated book as a BookDto
   */
  BookDto updateBook(Long id, BookDto updatedBookDto);
}
