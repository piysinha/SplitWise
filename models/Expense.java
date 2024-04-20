package com.scaler.splitwise.models;

import ch.qos.logback.core.model.Model;
import com.scaler.splitwise.models.enums.ExpenseType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
public class Expense extends BaseModel {

    private String description;

    private int amount;

    @Enumerated(EnumType.ORDINAL)
    private ExpenseType expenseType;

    @ManyToOne
    private Group group;

    @OneToMany
    private List<UserExpense> userExpensesList;
}
