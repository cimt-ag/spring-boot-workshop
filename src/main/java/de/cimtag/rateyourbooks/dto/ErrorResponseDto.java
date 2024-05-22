package de.cimtag.rateyourbooks.dto;

import lombok.Builder;
import org.springframework.http.HttpStatusCode;

@Builder
public record ErrorResponseDto(
    HttpStatusCode code,
    String title,
    String detail
) {

}
