package com.eazybytes.accounts.service;

import com.eazybytes.accounts.dto.CustomerDetailsDTO;

public interface ICustomerService {
    CustomerDetailsDTO fetchCustomerDetails(String mobileNumber, String correlationId);
}
