package com.eazybytes.messages.dto;

public record AccountsMessageDTO(
        Long accountNumber,
        String name,
        String email,
        String mobileNumber
) {
}
