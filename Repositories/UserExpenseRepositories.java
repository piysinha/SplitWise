package com.scaler.splitwise.Repositories;

import com.scaler.splitwise.models.User;
import com.scaler.splitwise.models.UserExpense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface UserExpenseRepositories extends JpaRepository<UserExpense,Long> {
    public List<UserExpense> findAllByUser(User user);
}
