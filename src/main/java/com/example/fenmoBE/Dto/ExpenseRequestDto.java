package com.example.fenmoBE.Dto;

import jakarta.validation.constraints.*;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExpenseRequestDto {

    @NotNull(message = "Amount is required")
    @DecimalMin(value = "0.01", message = "Amount must be greater than 0")
    @Digits(integer = 8, fraction = 2, message = "Invalid amount format")
    private BigDecimal amount;

    @NotBlank(message = "Category is required")
    @Size(max = 100, message = "Category too long")
    private String category;

    @Size(max = 500, message = "Description too long")
    private String description;

    @NotNull(message = "Date is required")
    private LocalDate date;
}
