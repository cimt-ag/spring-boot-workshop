package de.cimtag.rateyourbooks.service;

import de.cimtag.rateyourbooks.dto.BookDto;
import de.cimtag.rateyourbooks.exception.BookNotFoundException;
import de.cimtag.rateyourbooks.model.Book;
import de.cimtag.rateyourbooks.repository.BookRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

  private final BookRepository bookRepository;

  @Override
  public BookDto findBookById(Long id) {
    return bookRepository.findById(id).orElseThrow(() -> new BookNotFoundException("Book with ID " + id + " not found!")).toDto();
  }

  @Override
  public BookDto findBookByTitle(String title) {
    return bookRepository.findByTitle(title).orElseThrow(() -> new BookNotFoundException("Book with Title " + title + " not found!")).toDto();
  }

  @Override
  public BookDto findBookByTitleAndAuthor(String title, String author) {
    return bookRepository.findByTitleAndAuthor(title, author)
        .orElseThrow(() -> new BookNotFoundException("Book with Title " + title + " and author " + author + " not found!")).toDto();
  }

  @Override
  public List<BookDto> findAllBooksByAuthor(String author) {
    return bookRepository.findAllByAuthor(author).stream().map(Book::toDto).toList();
  }

  @Override
  public List<BookDto> findAllBooks() {
    return bookRepository.findAll().stream().map(Book::toDto).toList();
  }

  @Override
  public BookDto createBook(BookDto bookDto) {
    return bookRepository.save(bookDto.toEntity()).toDto();
  }

  @Override
  public void deleteBook(Long id) {
    bookRepository.deleteById(id);
  }

  @Override
  public BookDto updateBook(Long id, BookDto updatedBookDto) {
    Book existingBook = this.findBookById(id).toEntity();

    if (updatedBookDto.title() != null && !updatedBookDto.title().isBlank()) {
      existingBook.setTitle(updatedBookDto.title());
    }

    if (updatedBookDto.author() != null && !updatedBookDto.author().isBlank()) {
      existingBook.setAuthor(updatedBookDto.author());
    }

    return bookRepository.save(existingBook).toDto();
  }
}
