package com.eazybytes.accounts.dto;

import jakarta.persistence.Id;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class AccountDTO {

    @NotEmpty(message = "Account number cannot be empty or null")
    @Pattern(regexp = "(^$|[0-9]{19})", message = "account number should be 19 digits")
    private Long accountNumber;


    @NotEmpty(message = "Account type cannot be empty or null")
    private String accountType;

    @NotEmpty(message = "Branch address cannot be empty or null")
    private String branchAddress;
}
