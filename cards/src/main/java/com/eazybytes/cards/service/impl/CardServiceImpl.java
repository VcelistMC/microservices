package com.eazybytes.cards.service.impl;

import com.eazybytes.cards.constants.CardConstants;
import com.eazybytes.cards.dto.CardDTO;
import com.eazybytes.cards.entity.Card;
import com.eazybytes.cards.exception.CardAlreadyExistsException;
import com.eazybytes.cards.exception.ResourceNotFoundException;
import com.eazybytes.cards.mapper.CardMapper;
import com.eazybytes.cards.repository.CardRepository;
import com.eazybytes.cards.service.ICardService;
import com.eazybytes.cards.utils.RandomDigitGenerator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CardServiceImpl implements ICardService {

    private CardRepository cardRepository;

    @Override
    public void createCard(String mobileNumber) {
        Card card = new Card();

        var cardAlreadyExists = cardRepository.findCardByMobileNumber(mobileNumber);
        if(cardAlreadyExists.isPresent()){
            throw new CardAlreadyExistsException("Card already exists with mobile number " + mobileNumber);
        }

        card.setMobileNumber(mobileNumber);
        card.setCardNumber(RandomDigitGenerator.generateRandomDigits(14));
        card.setCardType(CardConstants.CREDIT_CARD);
        card.setTotalLimit(CardConstants.NEW_CARD_LIMIT);
        card.setAvailableAmount(CardConstants.NEW_CARD_LIMIT);

        cardRepository.save(card);
    }

    @Override
    public CardDTO fetchCard(String mobileNumber) {
        var card = cardRepository.findCardByMobileNumber(mobileNumber).orElseThrow(
            () -> new ResourceNotFoundException("Card", "mobileNumber", mobileNumber)
        );

        return CardMapper.mapToCardDTO(card, new CardDTO());
    }

    @Override
    public boolean updateCard(CardDTO cardDTO) {
        var card = cardRepository.findCardByMobileNumber(cardDTO.getMobileNumber()).orElseThrow(
            () -> new ResourceNotFoundException("Card", "mobileNumber", cardDTO.getMobileNumber())
        );
        CardMapper.mapToCard(cardDTO, card);
        cardRepository.save(card);
        return true;
    }

    @Override
    public boolean deleteCard(String mobileNumber) {
        var card = cardRepository.findCardByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Card", "mobileNumber", mobileNumber)
        );

        cardRepository.deleteById(card.getCardId());

        return true;
    }
}
