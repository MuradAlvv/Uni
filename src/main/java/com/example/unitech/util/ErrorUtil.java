package com.example.unitech.util;

import static lombok.AccessLevel.PRIVATE;

import lombok.NoArgsConstructor;

@NoArgsConstructor(access = PRIVATE)
public final class ErrorUtil {

    public static String buildAlreadyExistsMessage(
            String entity, String uniqueField, String value) {
        return entity + " with " + uniqueField + " = " + value + " already exists";
    }

    public static String buildNotFoundMessage(String entity, String field, String value) {
        return entity + " with " + field + " = " + value + " not found";
    }
}
