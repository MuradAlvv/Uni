package com.example.unitech.dto.currency;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder(setterPrefix = "set")
public class CurrencyResponseDto {
    private Long id;
    private String code;
    private String name;
    private String symbol;
}
