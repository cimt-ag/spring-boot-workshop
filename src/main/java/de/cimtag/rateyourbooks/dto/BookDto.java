package de.cimtag.rateyourbooks.dto;

import de.cimtag.rateyourbooks.model.Book;
import lombok.Builder;

@Builder
public record BookDto(Long id, String title, String author) {

  public Book toEntity() {
    return Book.builder()
        .id(id)
        .title(title)
        .author(author)
        .build();
  }
}
