package de.cimtag.rateyourbooks.controller;

import de.cimtag.rateyourbooks.dto.BookDto;
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

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/books")
public class BookController {

  private final BookService bookService;

  @GetMapping
  public ResponseEntity<List<BookDto>> searchBooks(@RequestParam(required = false) String title, @RequestParam(required = false) String author) {
    if (title != null && !title.isBlank()) {
      return author != null && !author.isBlank() ? ResponseEntity.ok(List.of(bookService.findBookByTitleAndAuthor(title, author)))
          : ResponseEntity.ok(List.of(bookService.findBookByTitle(title)));
    }

    return author != null && !author.isBlank() ? ResponseEntity.ok(bookService.findAllBooksByAuthor(author)) : ResponseEntity.ok(bookService.findAllBooks());
  }

  @GetMapping("/{id}")
  public ResponseEntity<BookDto> findBookById(@PathVariable Long id) {
    return ResponseEntity.ok(bookService.findBookById(id));
  }

  @PostMapping
  public ResponseEntity<BookDto> createNewBook(@RequestBody BookDto bookDto) {
    BookDto createdBook = bookService.createBook(bookDto);
    return ResponseEntity.created(URI.create("/api/books/" + createdBook.id())).body(createdBook);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
    bookService.deleteBook(id);
    return ResponseEntity.ok().build();
  }

  @PutMapping("/{id}")
  public ResponseEntity<BookDto> updateBook(@PathVariable Long id, @RequestBody BookDto bookDto) {
    return ResponseEntity.ok(bookService.updateBook(id, bookDto));
  }
}
