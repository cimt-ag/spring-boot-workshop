package de.cimtag.rateyourbooks.dto;

import de.cimtag.rateyourbooks.model.Book;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

/**
 * Data Transfer Object for the Book entity.
 * <p>
 * This class is used to transfer data between layers of the application. It includes a method to convert the DTO to a Book entity.
 * </p>
 *
 * @author Niklas Witzel
 */
@Builder
public record BookDto(
    Long id,
    @NotNull String title,
    @NotNull String author
) {

  /**
   * Converts this {@link BookDto} to a {@link Book} entity.
   *
   * @return a {@link Book} entity with the same properties as this {@link BookDto}
   */
  public Book toEntity() {
    return Book.builder()
        .id(id)
        .title(title)
        .author(author)
        .build();
  }
}
