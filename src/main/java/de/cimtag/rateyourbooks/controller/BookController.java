package de.cimtag.rateyourbooks.controller;

import de.cimtag.rateyourbooks.model.Book;
import de.cimtag.rateyourbooks.service.BookService;
import java.util.List;
import lombok.RequiredArgsConstructor;
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
  public List<Book> searchBooks(@RequestParam(required = false) String title, @RequestParam(required = false) String author) {
    if (title != null && !title.isBlank()) {
      return author != null && !author.isBlank()
          ? List.of(bookService.findBookByTitleAndAuthor(title, author))
          : List.of(bookService.findBookByTitle(title));
    }

    return author != null && !author.isBlank()
        ? bookService.findAllBooksByAuthor(author)
        : bookService.findAllBooks();
  }

  @GetMapping("/{id}")
  public Book findBookById(@PathVariable Long id) {
    return bookService.findBookById(id);
  }

  @PostMapping
  public Book createNewBook(@RequestBody Book book) {
    return bookService.createBook(book);
  }

  @DeleteMapping("/{id}")
  public void deleteBook(@PathVariable Long id) {
    bookService.deleteBook(id);
  }

  @PutMapping("/{id}")
  public Book updateBook(@PathVariable Long id, @RequestBody Book book) {
    return bookService.updateBook(id, book);
  }
}
