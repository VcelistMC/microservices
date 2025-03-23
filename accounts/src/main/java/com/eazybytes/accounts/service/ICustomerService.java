package com.eazybytes.accounts.service;

import com.eazybytes.accounts.dto.CustomerDetailsDTO;
import org.springframework.web.bind.annotation.RequestParam;

public interface ICustomerService {
    CustomerDetailsDTO fetchCustomerDetails(String mobileNumber);
}
