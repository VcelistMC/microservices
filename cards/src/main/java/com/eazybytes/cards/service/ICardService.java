package com.eazybytes.cards.service;

import com.eazybytes.cards.dto.CardDTO;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

public interface ICardService {
    void createCard(String mobileNumber);

    CardDTO fetchCard(String mobileNumber);

    boolean updateCard(@Valid CardDTO cardDTO);

    boolean deleteCard(String mobileNumber);
}
