package com.scaler.splitwise.Repositories;

import com.scaler.splitwise.models.Expense;
import com.scaler.splitwise.models.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExpenseRepositories extends JpaRepository <Expense,Long> {
    public List<Expense> findAllByGroup (Group group);
}
