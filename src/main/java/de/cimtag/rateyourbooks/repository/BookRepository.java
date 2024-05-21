package de.cimtag.rateyourbooks.repository;

import de.cimtag.rateyourbooks.model.Book;
import java.util.List;
import java.util.Optional;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends ListCrudRepository<Book, Long> {

  Optional<Book> findByTitle(String title);

  Optional<Book> findByTitleAndAuthor(String title, String author);

  List<Book> findAllByAuthor(String author);
}
