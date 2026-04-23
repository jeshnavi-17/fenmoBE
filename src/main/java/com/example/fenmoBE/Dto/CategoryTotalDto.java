package com.example.fenmoBE.Dto;



import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
public class CategoryTotalDto {

    private String category;
    private BigDecimal total;
}
