package com.example.unitech.dto.transfer;

import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TransferCreateDto {
    @NotNull private Long fromAccountId;
    @NotNull private Long toAccountId;
    @NotNull private BigDecimal amount;
}
