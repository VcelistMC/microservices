package com.eazybytes.accounts.service.client.fallbacks;

import com.eazybytes.accounts.dto.CardDTO;
import com.eazybytes.accounts.service.client.CardsFeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class CardsFallback implements CardsFeignClient {
    @Override
    public ResponseEntity<CardDTO> fetchCard(String mobileNumber, String correlationId) {
        return null;
    }
}
