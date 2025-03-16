package com.eazybytes.cards.utils;

import java.util.Random;

public class RandomDigitGenerator {
    public static String generateRandomDigits(int length) {
        Random random = new Random();
        StringBuilder randomDigits = new StringBuilder();

        // Generate 14 random digits
        for (int i = 0; i < length; i++) {
            randomDigits.append(random.nextInt(10));  // Generates a random digit between 0 and 9
        }

        return randomDigits.toString();
    }
}
