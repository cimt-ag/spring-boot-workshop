package de.cimtag.rateyourbooks.repository;

import de.cimtag.rateyourbooks.model.Book;
import java.util.List;
import java.util.Optional;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for accessing book data.
 * <p>
 * This interface extends ListCrudRepository to provide CRUD operations on Book entities.
 * </p>
 *
 * @author Niklas Witzel
 */
@Repository
public interface BookRepository extends ListCrudRepository<Book, Long> {

  /**
   * Finds a book by its title.
   *
   * @param title the title of the book
   * @return an Optional containing the found book, or empty if no book is found
   */
  Optional<Book> findByTitle(String title);

  /**
   * Finds a book by its title and author.
   *
   * @param title  the title of the book
   * @param author the author of the book
   * @return an Optional containing the found book, or empty if no book is found
   */
  Optional<Book> findByTitleAndAuthor(String title, String author);

  /**
   * Finds all books by a specific author.
   *
   * @param author the author of the books
   * @return a list of books by the specified author
   */
  List<Book> findAllByAuthor(String author);
}
