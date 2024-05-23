package de.cimtag.rateyourbooks.service;

import de.cimtag.rateyourbooks.dto.BookDto;
import de.cimtag.rateyourbooks.exception.BookNotFoundException;
import de.cimtag.rateyourbooks.model.Book;
import de.cimtag.rateyourbooks.repository.BookRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Implementation of the BookService interface.
 * <p>
 * This service provides methods for various operations related to books, such as finding, creating, updating, and deleting books.
 * </p>
 *
 * @author Niklas Witzel
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class BookServiceImpl implements BookService {

  private final BookRepository bookRepository;

  @Override
  public BookDto findBookById(Long id) {
    log.info("Find book by ID '{}'", id);

    return bookRepository.findById(id).map(book -> {
          BookDto bookDto = book.toDto();
          log.info("Found book with ID '{}': {}", id, bookDto);

          return bookDto;
        })
        .orElseThrow(() -> {
          log.warn("Book with ID '{}' not found", id);
          return new BookNotFoundException("Book with ID '" + id + "' not found!");
        });
  }

  @Override
  public BookDto findBookByTitle(String title) {
    log.info("Find book by title: {}", title);

    return bookRepository.findByTitle(title).map(book -> {
          BookDto bookDto = book.toDto();
          log.info("Found book with title '{}': {}", title, bookDto);

          return bookDto;
        })
        .orElseThrow(() -> {
          log.warn("Book with title '{}' not found", title);
          return new BookNotFoundException("Book with title '" + title + " not found!");
        });
  }

  @Override
  public BookDto findBookByTitleAndAuthor(String title, String author) {
    log.info("Find book by title '{}' and author '{}'", title, author);

    return bookRepository.findByTitleAndAuthor(title, author).map(book -> {
          BookDto bookDto = book.toDto();
          log.info("Found book with title '{}' and author '{}': {}", title, author, bookDto);

          return bookDto;
        })
        .orElseThrow(() -> {
          log.warn("Book with title '{}' and author '{}' not found", title, author);
          return new BookNotFoundException("Book with title '" + title + "' and author '" + author + "' not found!");
        });
  }


  @Override
  public List<BookDto> findAllBooksByAuthor(String author) {
    log.info("Find all books by author '{}'", author);

    List<BookDto> bookDtos = bookRepository.findAllByAuthor(author).stream().map(Book::toDto).toList();
    log.info("Found {} books by author '{}'", bookDtos.size(), author);

    return bookDtos;
  }

  @Override
  public List<BookDto> findAllBooks() {
    log.info("Find all books");

    List<BookDto> bookDtos = bookRepository.findAll().stream().map(Book::toDto).toList();
    log.info("Found {} books", bookDtos.size());

    return bookDtos;
  }

  @Override
  public BookDto createBook(BookDto bookDto) {
    log.info("Create new book: {}", bookDto);

    Book book = bookRepository.save(bookDto.toEntity());
    BookDto createdBookDto = book.toDto();
    log.info("Created book: {}", createdBookDto);

    return createdBookDto;
  }

  @Override
  public void deleteBook(Long id) {
    log.info("Delete book with ID '{}'", id);

    bookRepository.deleteById(id);
    log.info("Deleted book with ID '{}'", id);
  }

  @Override
  public BookDto updateBook(Long id, BookDto updatedBookDto) {
    log.info("Update book with ID '{}'", id);

    Book existingBook = bookRepository.findById(id)
        .orElseThrow(() -> {
          log.warn("Book with ID '{}' not found for update", id);
          return new BookNotFoundException("Book with ID '" + id + "' not found for update!");
        });

    if (updatedBookDto.title() != null && !updatedBookDto.title().isBlank()) {
      existingBook.setTitle(updatedBookDto.title());
    }

    if (updatedBookDto.author() != null && !updatedBookDto.author().isBlank()) {
      existingBook.setAuthor(updatedBookDto.author());
    }

    Book updatedBook = bookRepository.save(existingBook);
    BookDto updatedBookDtoResponse = updatedBook.toDto();
    log.info("Updated book with ID '{}': {}", id, updatedBookDtoResponse);

    return updatedBookDtoResponse;
  }
}
