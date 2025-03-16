package com.eazybytes.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(name = "Response", description = "Generic response structure for API responses")
public class ResponseDTO {

    @Schema(description = "The status code of the response")
    private String statusCode;

    @Schema(description = "A message describing the status")
    private String statusMsg;
}
