package com.example.fenmoBE.Repository;

import com.example.fenmoBE.Dto.CategoryTotalDto;
import com.example.fenmoBE.Entity.Expense;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    Optional<Expense> findByIdempotencyKey(String idempotencyKey);

    Page<Expense> findByCategoryIgnoreCase(String category, Pageable pageable);

    @Query("""
    SELECT e.category, SUM(e.amount)
    FROM Expense e
    GROUP BY e.category
""")
    List<Object[]> getCategorySummaryRaw();
}
