package com.example.unitech.common;

import static lombok.AccessLevel.PRIVATE;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@NoArgsConstructor(access = PRIVATE)
public final class Errors {

    @NoArgsConstructor(access = PRIVATE)
    public static final class OpenAPI {
        public static final String INVALID_REQUEST_BODY = "Invalid request body";
        public static final String INVALID_BEARER = "Invalid bearer";
        public static final String INVALID_CREDENTIALS = "Invalid credentials";
    }

    public static String buildAlreadyExistsMessage(
            String entity, String uniqueField, String value) {
        return entity + " with " + uniqueField + " = " + value + " already exists";
    }

    public static String buildNotFoundMessage(String entity, String field, String value) {
        return entity + " with " + field + " = " + value + " not found";
    }

    @NoArgsConstructor(access = PRIVATE)
    public static final class Transfer {
        @Getter
        @RequiredArgsConstructor
        public enum InsufficientBalanceError {
            NOT_ENOUGH_BALANCE_MESSAGE("Doesn't have enough balance");

            private final String message;
        }

        @Getter
        @RequiredArgsConstructor
        public enum TransferToSameAccountError {
            TRANSFER_TO_SAME_ACCOUNT_MESSAGE("Transfer to same account is not allowed");

            private final String message;
        }

        @Getter
        @RequiredArgsConstructor
        public enum TransferToInactiveAccountError {
            INACTIVE_ACCOUNT_TRANSFER_MESSAGE("Transfer to/from inactive account is not allowed");

            private final String message;
        }
    }

    @NoArgsConstructor(access = PRIVATE)
    public static final class Jwt {

        @Getter
        @RequiredArgsConstructor
        public enum JwtExpiredError {
            JWT_EXPIRED_MESSAGE("Jwt is expired");

            private final String message;
        }

        @Getter
        @RequiredArgsConstructor
        public enum JwtInvalidError {
            JWT_INVALID_MESSAGE("Jwt is invalid");

            private final String message;
        }
    }

    @NoArgsConstructor(access = PRIVATE)
    public static final class Auth {

        @Getter
        @RequiredArgsConstructor
        public enum InvalidCredentialsError {
            INVALID_CREDENTIALS_ERROR("Invalid credentials");
            private final String message;
        }
    }
}
