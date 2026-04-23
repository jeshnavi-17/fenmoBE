package com.example.fenmoBE.Controller;

import com.example.fenmoBE.Dto.CategoryTotalDto;
import com.example.fenmoBE.Dto.ExpenseRequestDto;
import com.example.fenmoBE.Dto.ExpenseResponseDto;
import com.example.fenmoBE.Exception.IdempotencyKeyMissingException;
import com.example.fenmoBE.Service.ExpenseService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/expenses")
@RequiredArgsConstructor
public class ExpenseController {

    private final ExpenseService service;

    @PostMapping
    public ResponseEntity<ExpenseResponseDto> createExpense(
            @RequestHeader(value = "Idempotency-Key", required = false) String idempotencyKey,
            @Valid @RequestBody ExpenseRequestDto request) {

        if (idempotencyKey == null || idempotencyKey.isBlank()) {
            throw new IdempotencyKeyMissingException("Idempotency-Key header is required");
        }

        return ResponseEntity.ok(service.createExpense(request, idempotencyKey));
    }

    @GetMapping
    public ResponseEntity<Page<ExpenseResponseDto>> getExpenses(
            @RequestParam(required = false) String category,
            @PageableDefault(sort = "date", direction = Sort.Direction.DESC) Pageable pageable) {

        return ResponseEntity.ok(service.getExpenses(category, pageable));
    }

    @GetMapping("/summary")
    public ResponseEntity<List<CategoryTotalDto>> getSummary() {
        return ResponseEntity.ok(service.getCategorySummary());
    }
}
