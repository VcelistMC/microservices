package com.eazybytes.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Schema(name = "Error", description = "Details about the API error response")
public class ErrorResponseDTO {

    @Schema(description = "The path of the API that caused the error", example = "/api/v1/resource")
    private String apiPath;

    @Schema(description = "The HTTP status code of the error", example = "BAD_REQUEST")
    private HttpStatus errorCode;

    @Schema(description = "A human-readable error message", example = "Invalid request parameters")
    private String errorMessage;

    @Schema(description = "The timestamp when the error occurred", example = "2024-03-16T14:30:00")
    private LocalDateTime errorTime;
}