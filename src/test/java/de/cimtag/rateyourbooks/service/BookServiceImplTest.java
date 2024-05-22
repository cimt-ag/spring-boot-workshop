package de.cimtag.rateyourbooks.service;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import de.cimtag.rateyourbooks.dto.BookDto;
import de.cimtag.rateyourbooks.exception.BookNotFoundException;
import de.cimtag.rateyourbooks.model.Book;
import de.cimtag.rateyourbooks.repository.BookRepository;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * Unit tests for the BookServiceImpl class.
 * <p>
 * This class tests the methods of the BookServiceImpl to ensure correct functionality and exception handling.
 * </p>
 *
 * @author Niklas Witzel
 */
@ExtendWith(MockitoExtension.class)
class BookServiceImplTest {

  @Mock
  private BookRepository bookRepository;

  @Captor
  private ArgumentCaptor<Book> bookArgumentCaptor;

  @InjectMocks
  private BookServiceImpl bookServiceImpl;

  private Book book;

  @BeforeEach
  void beforeEach() {
    book = Book.builder()
        .id(1L)
        .title("Test Title")
        .author("Test Author")
        .build();
  }

  @Test
  void testFindBookById() {
    when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

    BookDto foundBook = bookServiceImpl.findBookById(1L);

    assertThat(foundBook, is(notNullValue()));
    assertThat(foundBook.id(), is(1L));
  }

  @Test
  void testFindBookByIdThrowsException() {
    when(bookRepository.findById(any(Long.class))).thenReturn(Optional.empty());

    assertThrows(BookNotFoundException.class, () -> bookServiceImpl.findBookById(1L));
  }

  @Test
  void testFindBookByTitle() {
    when(bookRepository.findByTitle("Test Title")).thenReturn(Optional.of(book));

    BookDto foundBook = bookServiceImpl.findBookByTitle("Test Title");

    assertThat(foundBook, is(notNullValue()));
    assertThat(foundBook.title(), is("Test Title"));
  }

  @Test
  void testFindBookByTitleThrowsException() {
    when(bookRepository.findByTitle(any(String.class))).thenReturn(Optional.empty());

    assertThrows(BookNotFoundException.class, () -> bookServiceImpl.findBookByTitle("Non-existent Title"));
  }

  @Test
  void testFindBookByTitleAndAuthor() {
    when(bookRepository.findByTitleAndAuthor("Test Title", "Test Author")).thenReturn(Optional.of(book));

    BookDto foundBook = bookServiceImpl.findBookByTitleAndAuthor("Test Title", "Test Author");
    assertThat(foundBook, is(notNullValue()));
    assertThat(foundBook.title(), is("Test Title"));
    assertThat(foundBook.author(), is("Test Author"));
  }

  @Test
  void testFindBookByTitleAndAuthorThrowsException() {
    when(bookRepository.findByTitleAndAuthor(any(String.class), any(String.class))).thenReturn(Optional.empty());

    assertThrows(BookNotFoundException.class, () -> bookServiceImpl.findBookByTitleAndAuthor("Non-existent Title", "Non-existent Author"));
  }

  @Test
  void testFindAllBooksByAuthor() {
    Book book2 = Book.builder()
        .title("Test Title 2")
        .author("Test Author")
        .build();
    List<Book> books = List.of(book, book2);
    when(bookRepository.findAllByAuthor("Test Author")).thenReturn(books);

    List<BookDto> foundBooks = bookServiceImpl.findAllBooksByAuthor("Test Author");

    assertThat(foundBooks, hasSize(2));
    assertThat(foundBooks.getFirst().title(), is("Test Title"));
    assertThat(foundBooks.getFirst().author(), is("Test Author"));
    assertThat(foundBooks.get(1).title(), is("Test Title 2"));
    assertThat(foundBooks.get(1).author(), is("Test Author"));
  }

  @Test
  void testFindAllBooks() {
    Book book2 = Book.builder()
        .title("Test Title 2")
        .author("Test Author")
        .build();
    List<Book> books = List.of(book, book2);
    when(bookRepository.findAll()).thenReturn(books);

    List<BookDto> foundBooks = bookServiceImpl.findAllBooks();

    assertThat(foundBooks, hasSize(2));
    assertThat(foundBooks.getFirst().title(), is("Test Title"));
    assertThat(foundBooks.getFirst().author(), is("Test Author"));
    assertThat(foundBooks.get(1).title(), is("Test Title 2"));
    assertThat(foundBooks.get(1).author(), is("Test Author"));
  }

  @Test
  void testCreateBook() {
    when(bookRepository.save(any(Book.class))).thenReturn(book);

    BookDto createdBookDto = bookServiceImpl.createBook(BookDto.builder()
        .title("Test Title")
        .author("Test Author")
        .build());

    assertThat(createdBookDto, is(notNullValue()));
    assertThat(createdBookDto.id(), is(1L));
    assertThat(createdBookDto.title(), is("Test Title"));
    assertThat(createdBookDto.author(), is("Test Author"));
  }

  @Test
  void testDeleteBook() {
    doNothing().when(bookRepository).deleteById(any(Long.class));

    bookServiceImpl.deleteBook(1L);

    verify(bookRepository, times(1)).deleteById(1L);
  }


  @Test
  void testUpdateBookBothFields() {
    when(bookRepository.findById(1L)).thenReturn(Optional.of(createExistingBook()));
    when(bookRepository.save(any(Book.class))).thenReturn(book);

    bookServiceImpl.updateBook(1L, createUpdateBookValues("Updated Title", "Updated Author"));

    verify(bookRepository).save(bookArgumentCaptor.capture());

    assertThat(bookArgumentCaptor.getValue().getTitle(), is("Updated Title"));
    assertThat(bookArgumentCaptor.getValue().getAuthor(), is("Updated Author"));
  }

  @Test
  void testUpdateBookTitleOnly() {
    when(bookRepository.findById(1L)).thenReturn(Optional.of(createExistingBook()));
    when(bookRepository.save(any(Book.class))).thenReturn(book);

    bookServiceImpl.updateBook(1L, createUpdateBookValues("Updated Title", null));

    verify(bookRepository).save(bookArgumentCaptor.capture());

    assertThat(bookArgumentCaptor.getValue().getTitle(), is("Updated Title"));
    assertThat(bookArgumentCaptor.getValue().getAuthor(), is("Existing Author"));
  }

  @Test
  void testUpdateBookAuthorOnly() {
    when(bookRepository.findById(1L)).thenReturn(Optional.of(createExistingBook()));
    when(bookRepository.save(any(Book.class))).thenReturn(book);

    bookServiceImpl.updateBook(1L, createUpdateBookValues(null, "Updated Author"));

    verify(bookRepository).save(bookArgumentCaptor.capture());

    assertThat(bookArgumentCaptor.getValue().getTitle(), is("Existing Title"));
    assertThat(bookArgumentCaptor.getValue().getAuthor(), is("Updated Author"));
  }

  @Test
  void testUpdateBookNoFields() {
    when(bookRepository.findById(1L)).thenReturn(Optional.of(createExistingBook()));
    when(bookRepository.save(any(Book.class))).thenReturn(book);

    bookServiceImpl.updateBook(1L, createUpdateBookValues(null, null));

    verify(bookRepository).save(bookArgumentCaptor.capture());

    assertThat(bookArgumentCaptor.getValue().getTitle(), is("Existing Title"));
    assertThat(bookArgumentCaptor.getValue().getAuthor(), is("Existing Author"));
  }

  @Test
  void testUpdateBookBlankFields() {
    when(bookRepository.findById(1L)).thenReturn(Optional.of(createExistingBook()));
    when(bookRepository.save(any(Book.class))).thenReturn(book);

    bookServiceImpl.updateBook(1L, createUpdateBookValues(" ", " "));

    verify(bookRepository).save(bookArgumentCaptor.capture());

    assertThat(bookArgumentCaptor.getValue().getTitle(), is("Existing Title"));
    assertThat(bookArgumentCaptor.getValue().getAuthor(), is("Existing Author"));
  }

  @Test
  void testUpdateBookThrowsException() {
    when(bookRepository.findById(any(Long.class))).thenReturn(Optional.empty());

    BookDto updateBookValues = createUpdateBookValues("Updated Title", "Updated Author");
    assertThrows(BookNotFoundException.class, () -> bookServiceImpl.updateBook(1L, updateBookValues));
  }

  private Book createExistingBook() {
    return Book.builder()
        .id(1L)
        .title("Existing Title")
        .author("Existing Author")
        .build();
  }

  private BookDto createUpdateBookValues(String title, String author) {
    return BookDto.builder()
        .title(title)
        .author(author)
        .build();
  }
}
