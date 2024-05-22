package de.cimtag.rateyourbooks.service;

import de.cimtag.rateyourbooks.dto.BookDto;
import java.util.List;

public interface BookService {

  BookDto findBookById(Long id);

  BookDto findBookByTitle(String title);

  BookDto findBookByTitleAndAuthor(String title, String author);

  List<BookDto> findAllBooksByAuthor(String author);

  List<BookDto> findAllBooks();

  BookDto createBook(BookDto bookDto);

  void deleteBook(Long id);

  BookDto updateBook(Long id, BookDto updatedBookDto);
}
