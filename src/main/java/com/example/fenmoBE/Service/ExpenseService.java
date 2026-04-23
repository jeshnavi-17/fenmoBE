package com.example.fenmoBE.Service;


import com.example.fenmoBE.Dto.CategoryTotalDto;
import com.example.fenmoBE.Dto.ExpenseRequestDto;
import com.example.fenmoBE.Dto.ExpenseResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ExpenseService {

    ExpenseResponseDto createExpense(ExpenseRequestDto request, String idempotencyKey);

    Page<ExpenseResponseDto> getExpenses(String category, Pageable pageable);

    public List<CategoryTotalDto> getCategorySummary();
}
