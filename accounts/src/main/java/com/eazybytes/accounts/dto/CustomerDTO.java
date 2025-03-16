package com.eazybytes.accounts.dto;

import com.eazybytes.accounts.entity.Account;
import lombok.Data;

@Data
public class CustomerDTO {

    private String name;

    private String email;

    private String mobileNumber;

    private AccountDTO account;
}
