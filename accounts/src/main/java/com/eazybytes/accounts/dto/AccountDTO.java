package com.eazybytes.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Schema(
        name = "Account",
        description = "Schema to hold customer's account information"
)
@Data
public class AccountDTO {

    @Schema(
            name = "Customer's account Number",
            example = "6721753287224369542"
    )
    @NotEmpty(message = "Account number cannot be empty or null")
    @Pattern(regexp = "(^$|[0-9]{19})", message = "account number should be 19 digits")
    private Long accountNumber;

    @Schema(
            name = "Customer's account type",
            example = "Savings"
    )
    @NotEmpty(message = "Account type cannot be empty or null")
    private String accountType;

    @Schema(
            name = "Customer's account type",
            example = "123 Bank St."
    )
    @NotEmpty(message = "Branch address cannot be empty or null")
    private String branchAddress;
}
