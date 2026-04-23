package com.example.fenmoBE.Dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExpenseResponseDto {

    private Long id;
    private BigDecimal amount;
    private String category;
    private String description;
    private LocalDate date;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
