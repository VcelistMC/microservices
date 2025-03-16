package com.eazybytes.accounts.controller;

import com.eazybytes.accounts.consts.AccountConstants;
import com.eazybytes.accounts.dto.CustomerDTO;
import com.eazybytes.accounts.dto.ResponseDTO;
import com.eazybytes.accounts.entity.Customer;
import com.eazybytes.accounts.mapper.CustomerMapper;
import com.eazybytes.accounts.service.IAccountService;
import com.eazybytes.accounts.validators.MobileNumber;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RequestMapping(path = "/api", produces = {MediaType.APPLICATION_JSON_VALUE})
@RestController
@AllArgsConstructor
@Validated
public class AccountsController {

    private IAccountService accountService;

    @PostMapping("/create")
    public ResponseEntity<ResponseDTO> createAccount(
            @Valid @RequestBody CustomerDTO customerDto
    ) {

        accountService.createAccount(customerDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDTO(
                        AccountConstants.STATUS_201,
                        AccountConstants.MESSAGE_201
                ));
    }

    @GetMapping("/fetch")
    public ResponseEntity<CustomerDTO> fetchAccountDetails(
            @MobileNumber @RequestParam String mobileNumber
    ){
        CustomerDTO details = accountService.fetchAccount(mobileNumber);

        return ResponseEntity
                .ok(details);
    }

    @PatchMapping("/update")
    public ResponseEntity<ResponseDTO> updateAccount(
        @Valid @RequestBody CustomerDTO customerDTO
    ){
        var isUpdated = accountService.updateAccount(customerDTO);
        if(isUpdated){
            return ResponseEntity.ok(new ResponseDTO(AccountConstants.STATUS_200, AccountConstants.MESSAGE_200));
        }
        else{
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseDTO(AccountConstants.STATUS_500, AccountConstants.MESSAGE_500_UPDATE));
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ResponseDTO> deleteAccount(
        @MobileNumber @RequestParam String mobileNumber
    ){
        var isDeleted = accountService.deleteAccount(mobileNumber);
        if(isDeleted){
            return ResponseEntity.ok(new ResponseDTO(AccountConstants.STATUS_200, AccountConstants.MESSAGE_200));
        }
        else{
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseDTO(AccountConstants.STATUS_500, AccountConstants.MESSAGE_500_UPDATE));
        }
    }
}
