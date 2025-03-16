package com.eazybytes.cards.mapper;

import com.eazybytes.cards.dto.CardDTO;
import com.eazybytes.cards.entity.Card;

public class CardMapper {
    public static CardDTO mapToCardDTO(Card card, CardDTO cardDTO) {
        cardDTO.setMobileNumber(card.getMobileNumber());
        cardDTO.setCardNumber(String.valueOf(card.getCardNumber()));
        cardDTO.setCardType(card.getCardType());
        cardDTO.setAmountUsed(card.getAmountUsed());
        cardDTO.setTotalLimit(card.getTotalLimit());
        cardDTO.setAvailableAmount(card.getAvailableAmount());

        return cardDTO;
    }

    public static Card mapToCard(CardDTO cardDTO, Card card) {
        card.setMobileNumber(cardDTO.getMobileNumber());
        card.setCardNumber(cardDTO.getCardNumber());
        card.setCardType(cardDTO.getCardType());
        card.setAmountUsed(cardDTO.getAmountUsed());
        card.setTotalLimit(cardDTO.getTotalLimit());
        card.setAvailableAmount(cardDTO.getAvailableAmount());
        return card;
    }
}
