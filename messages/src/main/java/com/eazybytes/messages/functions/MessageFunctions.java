package com.eazybytes.messages.functions;

import com.eazybytes.messages.dto.AccountsMessageDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Function;

@Configuration
public class MessageFunctions {
    private static final Logger logger = LoggerFactory.getLogger(MessageFunctions.class);

    @Bean
    public Function<AccountsMessageDTO, AccountsMessageDTO> email(){
        return accountsMessageDTO -> {
          logger.info("Sending email with details: " + accountsMessageDTO.toString());
          return accountsMessageDTO;
        };
    }

    @Bean
    public Function<AccountsMessageDTO, Long> sms(){
        return accountsMessageDTO -> {
            logger.info("Sending SMS with details: " + accountsMessageDTO.toString());
            return accountsMessageDTO.accountNumber();
        };
    }
}
