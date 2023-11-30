package com.example.unitech.util;

import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public final class Errors {

    public static String buildAlreadyExistsMessage(String entity, String uniqueField, String value) {
        return entity + " with " + uniqueField + " = " + value + " already exists";
    }
}
