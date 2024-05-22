package de.cimtag.rateyourbooks.model;

import de.cimtag.rateyourbooks.dto.BookDto;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Entity class representing a book.
 * <p>
 * This class is mapped to the "books" table in the database and includes fields for the book's ID, title, and author.
 * </p>
 *
 * @author Niklas Witzel
 */
@Entity
@Table(name = "books")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Book {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String title;
  private String author;

  /**
   * Converts this {@link Book} entity to a {@link BookDto}.
   *
   * @return a {@link BookDto} with the same properties as this {@link Book} entity
   */
  public BookDto toDto() {
    return BookDto.builder()
        .id(id)
        .title(title)
        .author(author)
        .build();
  }
}
