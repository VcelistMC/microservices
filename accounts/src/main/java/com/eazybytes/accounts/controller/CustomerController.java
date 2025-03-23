package com.eazybytes.accounts.controller;

import com.eazybytes.accounts.dto.CustomerDetailsDTO;
import com.eazybytes.accounts.service.ICustomerService;
import com.eazybytes.accounts.service.impl.CustomerServiceImpl;
import com.eazybytes.accounts.validators.MobileNumber;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(
        name = "REST APIs for Customers Eazybank",
        description = "CRUD REST APIs for Eazybank to FETCH customer details"
)
@RequestMapping(path = "/api", produces = {MediaType.APPLICATION_JSON_VALUE})
@RestController
@RequiredArgsConstructor
@Validated
public class CustomerController {

    @Autowired
    private final ICustomerService customerService;


    @Operation(
            summary = "Fetch Customer REST API",
            description = "fetch customer details inside easybank"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP OK"
    )
    @GetMapping("/fetchCustomerDetails")
    public ResponseEntity<CustomerDetailsDTO> fetchCustomerDetails(
            @MobileNumber @RequestParam String mobileNumber
    ){
        var customerDetails = customerService.fetchCustomerDetails(mobileNumber);
        return ResponseEntity.ok(customerDetails);
    }
}
