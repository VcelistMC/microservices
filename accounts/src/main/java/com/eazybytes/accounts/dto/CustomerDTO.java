package com.eazybytes.accounts.dto;

import com.eazybytes.accounts.entity.Account;
import com.eazybytes.accounts.validators.MobileNumber;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CustomerDTO {
    @NotEmpty(message = "Name cannot be empty or null")
    @Size(min = 5, max = 30, message = "Name's length should be between 5 and 30 characters")
    private String name;

    @NotEmpty(message = "Email cannot be empty or null")
    @Email(message = "Email should be valid")
    private String email;

    @NotEmpty(message = "Mobile number cannot be empty or null")
    @MobileNumber
    private String mobileNumber;

    private AccountDTO account;
}
