package de.cimtag.rateyourbooks.service;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

  @Mock
  private BookRepository bookRepository;

  @Captor
  private ArgumentCaptor<Book> bookArgumentCaptor;

  @InjectMocks
  private BookService bookService;

  private Book book;

  @BeforeEach
  void beforeEach() {
    book = new Book();
    book.setId(1L);
    book.setTitle("Test Title");
    book.setAuthor("Test Author");
  }

  @Test
  void testFindBookById() {
    when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

    Book foundBook = bookService.findBookById(1L);

    assertThat(foundBook, is(notNullValue()));
    assertThat(foundBook.getId(), is(1L));
  }

  @Test
  void testFindBookByIdThrowsException() {
    when(bookRepository.findById(any(Long.class))).thenReturn(Optional.empty());

    assertThrows(RuntimeException.class, () -> bookService.findBookById(1L));
  }

  @Test
  void testFindBookByTitle() {
    when(bookRepository.findByTitle("Test Title")).thenReturn(Optional.of(book));

    Book foundBook = bookService.findBookByTitle("Test Title");

    assertThat(foundBook, is(notNullValue()));
    assertThat(foundBook.getTitle(), is("Test Title"));
  }

  @Test
  void testFindBookByTitleThrowsException() {
    when(bookRepository.findByTitle(any(String.class))).thenReturn(Optional.empty());

    assertThrows(RuntimeException.class, () -> bookService.findBookByTitle("Non-existent Title"));
  }

  @Test
  void testFindBookByTitleAndAuthor() {
    when(bookRepository.findByTitleAndAuthor("Test Title", "Test Author")).thenReturn(Optional.of(book));

    Book foundBook = bookService.findBookByTitleAndAuthor("Test Title", "Test Author");
    assertThat(foundBook, is(notNullValue()));
    assertThat(foundBook.getTitle(), is("Test Title"));
    assertThat(foundBook.getAuthor(), is("Test Author"));
  }

  @Test
  void testFindBookByTitleAndAuthorThrowsException() {
    when(bookRepository.findByTitleAndAuthor(any(String.class), any(String.class))).thenReturn(Optional.empty());

    assertThrows(RuntimeException.class, () -> bookService.findBookByTitleAndAuthor("Non-existent Title", "Non-existent Author"));
  }

  @Test
  void testFindAllBooksByAuthor() {
    Book book2 = new Book();
    book2.setAuthor("Test Author");
    List<Book> books = List.of(book, book2);
    when(bookRepository.findAllByAuthor("Test Author")).thenReturn(books);

    List<Book> foundBooks = bookService.findAllBooksByAuthor("Test Author");

    assertThat(foundBooks, hasSize(2));
    assertThat(foundBooks, hasItem(book));
    assertThat(foundBooks, hasItem(book2));
  }

  @Test
  void testFindAllBooks() {
    Book book2 = new Book();
    List<Book> books = List.of(book, book2);
    when(bookRepository.findAll()).thenReturn(books);

    List<Book> foundBooks = bookService.findAllBooks();
    assertThat(foundBooks, hasSize(2));
    assertThat(foundBooks, hasItem(book));
    assertThat(foundBooks, hasItem(book2));
  }

  @Test
  void testCreateBook() {
    when(bookRepository.save(any(Book.class))).thenReturn(book);

    Book createdBook = bookService.createBook(book);

    assertThat(createdBook, is(notNullValue()));
    assertThat(createdBook.getId(), is(1L));
    assertThat(createdBook.getTitle(), is("Test Title"));
    assertThat(createdBook.getAuthor(), is("Test Author"));
  }

  @Test
  void testDeleteBook() {
    doNothing().when(bookRepository).deleteById(any(Long.class));

    bookService.deleteBook(1L);

    verify(bookRepository, times(1)).deleteById(1L);
  }


  @Test
  void testUpdateBookBothFields() {
    Book existingBook = new Book();
    existingBook.setId(1L);
    existingBook.setTitle("Existing Title");
    existingBook.setAuthor("Existing Author");

    Book updateBookValues = new Book();
    updateBookValues.setTitle("Updated Title");
    updateBookValues.setAuthor("Updated Author");

    when(bookRepository.findById(1L)).thenReturn(Optional.of(existingBook));

    bookService.updateBook(1L, updateBookValues);

    verify(bookRepository).save(bookArgumentCaptor.capture());

    assertThat(bookArgumentCaptor.getValue().getTitle(), is("Updated Title"));
    assertThat(bookArgumentCaptor.getValue().getAuthor(), is("Updated Author"));
  }

  @Test
  void testUpdateBookTitleOnly() {
    Book existingBook = new Book();
    existingBook.setId(1L);
    existingBook.setTitle("Existing Title");
    existingBook.setAuthor("Existing Author");

    Book updateBookValues = new Book();
    updateBookValues.setTitle("Updated Title");

    when(bookRepository.findById(1L)).thenReturn(Optional.of(existingBook));

    bookService.updateBook(1L, updateBookValues);

    verify(bookRepository).save(bookArgumentCaptor.capture());

    assertThat(bookArgumentCaptor.getValue().getTitle(), is("Updated Title"));
    assertThat(bookArgumentCaptor.getValue().getAuthor(), is("Existing Author"));
  }

  @Test
  void testUpdateBookAuthorOnly() {
    Book existingBook = new Book();
    existingBook.setId(1L);
    existingBook.setTitle("Existing Title");
    existingBook.setAuthor("Existing Author");

    Book updateBookValues = new Book();
    updateBookValues.setAuthor("Updated Author");

    when(bookRepository.findById(1L)).thenReturn(Optional.of(existingBook));

    bookService.updateBook(1L, updateBookValues);

    verify(bookRepository).save(bookArgumentCaptor.capture());

    assertThat(bookArgumentCaptor.getValue().getTitle(), is("Existing Title"));
    assertThat(bookArgumentCaptor.getValue().getAuthor(), is("Updated Author"));
  }

  @Test
  void testUpdateBookNoFields() {
    Book existingBook = new Book();
    existingBook.setId(1L);
    existingBook.setTitle("Existing Title");
    existingBook.setAuthor("Existing Author");

    Book updateBookValues = new Book();

    when(bookRepository.findById(1L)).thenReturn(Optional.of(existingBook));

    bookService.updateBook(1L, updateBookValues);

    verify(bookRepository).save(bookArgumentCaptor.capture());

    assertThat(bookArgumentCaptor.getValue().getTitle(), is("Existing Title"));
    assertThat(bookArgumentCaptor.getValue().getAuthor(), is("Existing Author"));
  }

  @Test
  void testUpdateBookBlankFields() {
    Book existingBook = new Book();
    existingBook.setId(1L);
    existingBook.setTitle("Existing Title");
    existingBook.setAuthor("Existing Author");

    Book updateBookValues = new Book();
    updateBookValues.setTitle(" ");
    updateBookValues.setAuthor(" ");

    when(bookRepository.findById(1L)).thenReturn(Optional.of(existingBook));

    bookService.updateBook(1L, updateBookValues);

    verify(bookRepository).save(bookArgumentCaptor.capture());

    assertThat(bookArgumentCaptor.getValue().getTitle(), is("Existing Title"));
    assertThat(bookArgumentCaptor.getValue().getAuthor(), is("Existing Author"));
  }

  @Test
  void testUpdateBookThrowsException() {
    Book updateBookValues = new Book();
    updateBookValues.setTitle("Updated Title");
    updateBookValues.setAuthor("Updated Author");

    when(bookRepository.findById(any(Long.class))).thenReturn(Optional.empty());

    assertThrows(RuntimeException.class, () -> bookService.updateBook(1L, updateBookValues));
  }
}
