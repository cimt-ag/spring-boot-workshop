package de.cimtag.rateyourbooks.service;

import de.cimtag.rateyourbooks.model.Book;
import de.cimtag.rateyourbooks.repository.BookRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookService {

  private final BookRepository bookRepository;

  @Autowired
  public BookService(BookRepository bookRepository) {
    this.bookRepository = bookRepository;
  }

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

  public Book updateBook(Long id, Book updatedBookDto) {
    Book book = this.findBookById(id);

    if (updatedBookDto.getTitle() != null && !updatedBookDto.getTitle().isBlank()) {
      book.setTitle(updatedBookDto.getTitle());
    }

    if (updatedBookDto.getAuthor() != null && !updatedBookDto.getAuthor().isBlank()) {
      book.setAuthor(updatedBookDto.getAuthor());
    }

    return bookRepository.save(book);
  }
}
