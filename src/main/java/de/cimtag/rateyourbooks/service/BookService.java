package de.cimtag.rateyourbooks.service;

import de.cimtag.rateyourbooks.model.Book;
import de.cimtag.rateyourbooks.repository.BookRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookService {

  private final BookRepository bookRepository;

  public Book findBookById(Long id) throws RuntimeException {
    return bookRepository.findById(id).orElseThrow(RuntimeException::new);
  }

  public Book findBookByTitle(String title) throws RuntimeException {
    return bookRepository.findByTitle(title).orElseThrow(RuntimeException::new);
  }

  public Book findBookByTitleAndAuthor(String title, String author) {
    return bookRepository.findByTitleAndAuthor(title, author).orElseThrow(RuntimeException::new);
  }

  public List<Book> findAllBooksByAuthor(String author) {
    return bookRepository.findAllByAuthor(author);
  }

  public List<Book> findAllBooks() {
    return bookRepository.findAll();
  }

  public Book createBook(Book book) {
    return bookRepository.save(book);
  }

  public void deleteBook(Long id) {
    bookRepository.deleteById(id);
  }

  public Book updateBook(Long id, Book updatedBook) {
    Book book = this.findBookById(id);

    if (updatedBook.getTitle() != null && !updatedBook.getTitle().isBlank()) {
      book.setTitle(updatedBook.getTitle());
    }

    if (updatedBook.getAuthor() != null && !updatedBook.getAuthor().isBlank()) {
      book.setAuthor(updatedBook.getAuthor());
    }

    return bookRepository.save(book);
  }
}
