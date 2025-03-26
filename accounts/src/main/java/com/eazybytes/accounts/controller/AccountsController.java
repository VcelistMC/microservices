package com.eazybytes.accounts.controller;

import com.eazybytes.accounts.consts.AccountConstants;
import com.eazybytes.accounts.dto.AccountsContactInfoDTO;
import com.eazybytes.accounts.dto.CustomerDTO;
import com.eazybytes.accounts.dto.ErrorResponseDTO;
import com.eazybytes.accounts.dto.ResponseDTO;
import com.eazybytes.accounts.entity.Customer;
import com.eazybytes.accounts.mapper.CustomerMapper;
import com.eazybytes.accounts.service.IAccountService;
import com.eazybytes.accounts.validators.MobileNumber;
import io.github.resilience4j.retry.annotation.Retry;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.TimeUnit;

@Slf4j
@Tag(
        name = "CRUD REST APIs for Eazybank",
        description = "CRUD REST APIs for Eazybank to CREATE, UPDATE, DELETE, and FETCH account details"
)
@RequestMapping(path = "/api", produces = {MediaType.APPLICATION_JSON_VALUE})
@RestController
@RequiredArgsConstructor
@Validated
public class AccountsController {

    @Autowired
    private IAccountService accountService;

    @Value("${build.version}")
    private String buildVersion;

    @Autowired
    private Environment environment;

    @Autowired
    private AccountsContactInfoDTO contactInfoDTO;

    @Operation(
            summary = "Create Account REST API",
            description = "Create new account and customer inside easybank"
    )
    @ApiResponse(
            responseCode = "201",
            description = "HTTP CREATED"
    )
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

    @Operation(
            summary = "Fetch Account REST API",
            description = "fetch account details inside easybank"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP OK"
    )
    @GetMapping("/fetch")
    public ResponseEntity<CustomerDTO> fetchAccountDetails(
            @MobileNumber @RequestParam String mobileNumber
    ){
        CustomerDTO details = accountService.fetchAccount(mobileNumber);

        return ResponseEntity
                .ok(details);
    }

    @Operation(
            summary = "Update Account REST API",
            description = "update account details inside easybank"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP OK"
            ),
            @ApiResponse(
                    responseCode = "417",
                    description = "HTTP EXPECTATION FAILED"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP INTERNAL SERVER ERROR",
                    content = @Content(
                            schema = @Schema(
                                    implementation = ErrorResponseDTO.class
                            )
                    )
            )
    })
    @PatchMapping("/update")
    public ResponseEntity<ResponseDTO> updateAccount(
        @Valid @RequestBody CustomerDTO customerDTO
    ){
        var isUpdated = accountService.updateAccount(customerDTO);
        if(isUpdated){
            return ResponseEntity.ok(new ResponseDTO(AccountConstants.STATUS_200, AccountConstants.MESSAGE_200));
        }
        else{
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseDTO(AccountConstants.STATUS_417, AccountConstants.MESSAGE_417_UPDATE));
        }
    }

    @Operation(
            summary = "Delete Account REST API",
            description = "Delete account details inside easybank"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP OK"
            ),
            @ApiResponse(
                    responseCode = "417",
                    description = "HTTP EXPECTATION FAILED"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP INTERNAL SERVER ERROR"
            )
    })
    @DeleteMapping("/delete")
    public ResponseEntity<ResponseDTO> deleteAccount(
        @MobileNumber @RequestParam String mobileNumber
    ){
        var isDeleted = accountService.deleteAccount(mobileNumber);
        if(isDeleted){
            return ResponseEntity.ok(new ResponseDTO(AccountConstants.STATUS_200, AccountConstants.MESSAGE_200));
        }
        else{
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseDTO(AccountConstants.STATUS_417, AccountConstants.MESSAGE_417_DELETE));
        }
    }

    @Retry(name = "version", fallbackMethod = "versionFallback")
    @GetMapping("/version")
    public ResponseEntity<String> version(){
       return ResponseEntity.ok(buildVersion);
    }

    public ResponseEntity<String> versionFallback(Throwable ex){
        return ResponseEntity.ok("Issue Occured");
    }

    @GetMapping("/contact")
    public ResponseEntity<AccountsContactInfoDTO> contact(){
        return ResponseEntity.ok(contactInfoDTO);
    }
}
