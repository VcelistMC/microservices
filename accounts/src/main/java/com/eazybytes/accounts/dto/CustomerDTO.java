package com.eazybytes.accounts.dto;

import com.eazybytes.accounts.entity.Account;
import com.eazybytes.accounts.validators.MobileNumber;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Schema(
        name = "Customer",
        description = "Schema to hold customer and account information"
)
@Data
public class CustomerDTO {

    @Schema(
            name = "Customer Name",
            example = "Peter Atef"
    )
    @NotEmpty(message = "Name cannot be empty or null")
    @Size(min = 5, max = 30, message = "Name's length should be between 5 and 30 characters")
    private String name;

    @Schema(
            name = "Customer Email",
            example = "email@email.com"
    )
    @NotEmpty(message = "Email cannot be empty or null")
    @Email(message = "Email should be valid")
    private String email;

    @Schema(
            name = "Customer Phone Number",
            example = "01234567890"
    )
    @NotEmpty(message = "Mobile number cannot be empty or null")
    @MobileNumber
    private String mobileNumber;

    @Schema(
            name = "Account details for the customer"
    )
    private AccountDTO account;
}
