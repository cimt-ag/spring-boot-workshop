package de.cimtag.rateyourbooks.controller;

import de.cimtag.rateyourbooks.dto.BookDto;
import de.cimtag.rateyourbooks.exception.BookNotFoundException;
import de.cimtag.rateyourbooks.service.BookService;
import java.net.URI;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for managing books.
 *
 * @author Niklas Witzel
 * @version 1.0
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/books")
public class BookController {

  private final BookService bookService;

  /**
   * Searches for books by title and/or author.
   *
   * @param title  the title of the book to search for (optional)
   * @param author the author of the book to search for (optional)
   * @return a list of books matching the search criteria
   */
  @GetMapping
  public ResponseEntity<List<BookDto>> searchBooks(@RequestParam(required = false) String title, @RequestParam(required = false) String author) {
    if (title != null && !title.isBlank()) {
      return author != null && !author.isBlank() ? ResponseEntity.ok(List.of(bookService.findBookByTitleAndAuthor(title, author)))
          : ResponseEntity.ok(List.of(bookService.findBookByTitle(title)));
    }

    return author != null && !author.isBlank() ? ResponseEntity.ok(bookService.findAllBooksByAuthor(author)) : ResponseEntity.ok(bookService.findAllBooks());
  }

  /**
   * Finds a book by its ID.
   *
   * @param id the ID of the book to find
   * @return the book with the specified ID
   * @throws BookNotFoundException if no book with given ID can be found
   */
  @GetMapping("/{id}")
  public ResponseEntity<BookDto> findBookById(@PathVariable Long id) {
    return ResponseEntity.ok(bookService.findBookById(id));
  }

  /**
   * Creates a new book.
   *
   * @param bookDto the details of the book to create
   * @return the newly created book
   */
  @PostMapping
  public ResponseEntity<BookDto> createNewBook(@RequestBody BookDto bookDto) {
    BookDto createdBook = bookService.createBook(bookDto);
    return ResponseEntity.created(URI.create("/api/books/" + createdBook.id())).body(createdBook);
  }

  /**
   * Deletes a book by its ID.
   *
   * @param id the ID of the book to delete
   * @return a response indicating the result of the deletion
   */
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
    bookService.deleteBook(id);
    return ResponseEntity.ok().build();
  }

  /**
   * Updates an existing book.
   *
   * @param id      the ID of the book to update
   * @param bookDto the updated details of the book
   * @return the updated book
   */
  @PutMapping("/{id}")
  public ResponseEntity<BookDto> updateBook(@PathVariable Long id, @RequestBody BookDto bookDto) {
    return ResponseEntity.ok(bookService.updateBook(id, bookDto));
  }
}
