package de.cimtag.rateyourbooks.controller;

import de.cimtag.rateyourbooks.model.Book;
import de.cimtag.rateyourbooks.service.BookService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BookController {

  private final BookService bookService;

  @Autowired
  public BookController(BookService bookService) {
    this.bookService = bookService;
  }

  @GetMapping("/api/books")
  public List<Book> searchBooks(@RequestParam(name = "title", required = false) String title, @RequestParam(name = "author", required = false) String author) {
    if (title != null && !title.isBlank()) {
      return author != null && !author.isBlank()
          ? List.of(bookService.findBookByTitleAndAuthor(title, author))
          : List.of(bookService.findBookByTitle(title));
    }

    return author != null && !author.isBlank()
        ? bookService.findAllBooksByAuthor(author)
        : bookService.findAllBooks();
  }

  @GetMapping("/api/books/{id}")
  public Book findBookById(@PathVariable(name = "id", required = true) Long id) {
    return bookService.findBookById(id);
  }

  @PostMapping("/api/books")
  public Book createNewBook(@RequestBody(required = true) Book book) {
    return bookService.createBook(book);
  }

  @DeleteMapping("/api/books/{id}")
  public void deleteBook(@PathVariable(name = "id", required = true) Long id) {
    bookService.deleteBook(id);
  }

  @PutMapping("/api/books/{id}")
  public Book updateBook(@PathVariable(name = "id", required = true) Long id, @RequestBody(required = true) Book book) {
    return bookService.updateBook(id, book);
  }
}
