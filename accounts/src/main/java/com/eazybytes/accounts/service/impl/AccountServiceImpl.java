package com.eazybytes.accounts.service.impl;

import com.eazybytes.accounts.consts.AccountConstants;
import com.eazybytes.accounts.dto.AccountDTO;
import com.eazybytes.accounts.dto.CustomerDTO;
import com.eazybytes.accounts.entity.Account;
import com.eazybytes.accounts.entity.Customer;
import com.eazybytes.accounts.exception.CustomerAlreadyExistsException;
import com.eazybytes.accounts.exception.ResourceNotFoundException;
import com.eazybytes.accounts.mapper.AccountMapper;
import com.eazybytes.accounts.mapper.CustomerMapper;
import com.eazybytes.accounts.repository.AccountRepository;
import com.eazybytes.accounts.repository.CustomerRepository;
import com.eazybytes.accounts.service.IAccountService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.concurrent.ThreadLocalRandom;

@Service
@AllArgsConstructor
public class AccountServiceImpl implements IAccountService {

    private AccountRepository accountRepository;
    private CustomerRepository customerRepository;

    @Override
    public void createAccount(CustomerDTO customerDTO) {
        Customer cusomter = CustomerMapper.mapToCustomer(customerDTO, new Customer());

        var existingCustomer = customerRepository.findByMobileNumber(customerDTO.getMobileNumber());
        if (existingCustomer.isPresent()) {
            throw new CustomerAlreadyExistsException("Customer already exists with mobile number " + customerDTO.getMobileNumber());
        }


        Customer savedCustomer = customerRepository.save(cusomter);
        Account account = createNewAccount(savedCustomer);

        accountRepository.save(account);
    }

    @Override
    public CustomerDTO fetchAccount(String mobileNumber) {
        Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Customer", "Mobile Number", mobileNumber)
        );

        Account account = accountRepository.findByCustomerId(customer.getCustomerId()).orElseThrow(
                () -> new ResourceNotFoundException("Account", "Customer Id", customer.getCustomerId().toString())
        );

        CustomerDTO customerDTO = CustomerMapper.mapToCustomerDto(customer, new CustomerDTO());
        AccountDTO accountDTO = AccountMapper.mapToAccountDTO(account, new AccountDTO());

        customerDTO.setAccount(accountDTO);

        return customerDTO;
    }


    @Override
    public boolean updateAccount(CustomerDTO customerDTO) {
        boolean isUpdated = false;

        AccountDTO accountDTO = customerDTO.getAccount();
        if(accountDTO != null) {
            Account account = accountRepository.findById(accountDTO.getAccountNumber()).orElseThrow(
                () -> new ResourceNotFoundException("Account", "Account Number", accountDTO.getAccountNumber().toString())
            );

            AccountMapper.mapToAccount(accountDTO, account);
            account.setUpdatedAt(LocalDateTime.now());
            account = accountRepository.save(account);

            long customerId = account.getCustomerId();
            Customer customer = customerRepository.findById(customerId).orElseThrow(
                () -> new ResourceNotFoundException("Customer", "Customer ID", String.valueOf(customerId))
            );

            CustomerMapper.mapToCustomer(customerDTO, customer);
            customerRepository.save(customer);
            isUpdated = true;

        }

        return isUpdated;
    }

    @Override
    public boolean deleteAccount(String mobileNumber) {
        boolean isDeleted = false;
        var customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
            () -> new ResourceNotFoundException("Customer", "Mobile Number", mobileNumber)
        );

        var account = accountRepository.findByCustomerId(customer.getCustomerId()).orElseThrow(
            () -> new ResourceNotFoundException("Account", "Customer Id", customer.getCustomerId().toString())
        );

        accountRepository.delete(account);
        customerRepository.delete(customer);

        isDeleted = true;

        return isDeleted;
    }


    private Account createNewAccount(Customer customer) {
        Account account = new Account();
        account.setCustomerId(customer.getCustomerId());
        long randomAccNumber = ThreadLocalRandom.current().nextLong();

        account.setAccountNumber(randomAccNumber);
        account.setAccountType(AccountConstants.SAVINGS);
        account.setBranchAddress(AccountConstants.ADDRESS);

        return account;
    }


}
