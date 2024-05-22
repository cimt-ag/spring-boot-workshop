package de.cimtag.rateyourbooks.dto;

import lombok.Builder;
import org.springframework.http.HttpStatusCode;

/**
 * Data Transfer Object for error responses.
 * <p>
 * This class is used to provide detailed error information in the API responses.
 * </p>
 *
 * @author Niklas Witzel
 */
@Builder
public record ErrorResponseDto(
    HttpStatusCode code,
    String title,
    String detail
) {

}
