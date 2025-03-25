package com.eazybytes.accounts.service.impl;

import com.eazybytes.accounts.dto.AccountDTO;
import com.eazybytes.accounts.dto.CardDTO;
import com.eazybytes.accounts.dto.CustomerDetailsDTO;
import com.eazybytes.accounts.dto.LoansDto;
import com.eazybytes.accounts.entity.Account;
import com.eazybytes.accounts.entity.Customer;
import com.eazybytes.accounts.exception.ResourceNotFoundException;
import com.eazybytes.accounts.mapper.AccountMapper;
import com.eazybytes.accounts.mapper.CustomerMapper;
import com.eazybytes.accounts.repository.AccountRepository;
import com.eazybytes.accounts.repository.CustomerRepository;
import com.eazybytes.accounts.service.ICustomerService;
import com.eazybytes.accounts.service.client.CardsFeignClient;
import com.eazybytes.accounts.service.client.LoansFeignClient;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CustomerServiceImpl implements ICustomerService {
    private AccountRepository accountRepository;
    private CustomerRepository customerRepository;
    private LoansFeignClient loansFeignClient;
    private CardsFeignClient cardsFeignClient;


    @Override
    public CustomerDetailsDTO fetchCustomerDetails(String mobileNumber, String correlationId) {
        Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Customer", "Mobile Number", mobileNumber)
        );

        Account account = accountRepository.findByCustomerId(customer.getCustomerId()).orElseThrow(
                () -> new ResourceNotFoundException("Account", "Customer Id", customer.getCustomerId().toString())
        );

        AccountDTO accountDTO = AccountMapper.mapToAccountDTO(account, new AccountDTO());
        CustomerDetailsDTO customerDetailsDTO = CustomerMapper.mapToCustomerDetailsDto(customer, new CustomerDetailsDTO());

        customerDetailsDTO.setAccountsDto(accountDTO);

        ResponseEntity<LoansDto> loansDtoResponseEntity = loansFeignClient.fetchLoanDetails(mobileNumber, correlationId);
        if(loansDtoResponseEntity != null) {
            customerDetailsDTO.setLoansDto(loansDtoResponseEntity.getBody());
        }

        ResponseEntity<CardDTO> cardDtoResponseEntity = cardsFeignClient.fetchCard(mobileNumber, correlationId);
        if(cardDtoResponseEntity != null) {
            customerDetailsDTO.setCardsDto(cardDtoResponseEntity.getBody());
        }

        return customerDetailsDTO;
    }
}
