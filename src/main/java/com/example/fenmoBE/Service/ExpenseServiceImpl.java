package com.example.fenmoBE.Service;

import com.example.fenmoBE.Dto.CategoryTotalDto;
import com.example.fenmoBE.Dto.ExpenseRequestDto;
import com.example.fenmoBE.Dto.ExpenseResponseDto;
import com.example.fenmoBE.Entity.Expense;
import com.example.fenmoBE.Repository.ExpenseRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ExpenseServiceImpl implements ExpenseService {

    private final ExpenseRepository repository;

    @Override
    @Transactional
    public ExpenseResponseDto createExpense(ExpenseRequestDto request, String idempotencyKey) {

        return repository.findByIdempotencyKey(idempotencyKey)
                .map(this::mapToResponse)
                .orElseGet(() -> {
                    Expense expense = mapToEntity(request, idempotencyKey);
                    Expense saved = repository.save(expense);
                    return mapToResponse(saved);
                });
    }

    @Override
    public Page<ExpenseResponseDto> getExpenses(String category, Pageable pageable) {

        Page<Expense> page;

        if (category != null && !category.isBlank()) {
            page = repository.findByCategoryIgnoreCase(category, pageable);
        } else {
            page = repository.findAll(pageable);
        }

        return page.map(this::mapToResponse);
    }

    @Override
    public List<CategoryTotalDto> getCategorySummary() {

        List<Object[]> result = repository.getCategorySummaryRaw();

        return result.stream()
                .map(obj -> new CategoryTotalDto(
                        (String) obj[0],
                        (BigDecimal) obj[1]
                ))
                .sorted((a, b) -> b.getTotal().compareTo(a.getTotal()))
                .toList();
    }

    private String normalizeCategory(String category) {
        return category.trim().toLowerCase();
    }

    private Expense mapToEntity(ExpenseRequestDto request, String key) {
        return Expense.builder()
                .amount(request.getAmount())
                .category(normalizeCategory(request.getCategory()))
                .description(request.getDescription())
                .date(request.getDate())
                .idempotencyKey(key)
                .build();
    }

    private ExpenseResponseDto mapToResponse(Expense expense) {
        return ExpenseResponseDto.builder()
                .id(expense.getId())
                .amount(expense.getAmount())
                .category(expense.getCategory())
                .description(expense.getDescription())
                .date(expense.getDate())
                .createdAt(expense.getCreatedAt())
                .updatedAt(expense.getUpdatedAt())
                .build();
    }
}
