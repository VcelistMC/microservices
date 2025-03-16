package com.eazybytes.accounts.dto;

import jakarta.persistence.Id;
import lombok.Data;

@Data
public class AccountDTO {
    private Long accountNumber;

    private String accountType;

    private String branchAddress;
}
