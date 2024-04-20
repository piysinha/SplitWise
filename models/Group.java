package com.scaler.splitwise.models;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity(name = "UserGroup")
//Group is a reserved keyword
public class Group extends BaseModel{

    private String name;

    @ManyToOne
    private User createdBy;

    @ManyToMany
    private List<User> members;

    @OneToMany(mappedBy = "group")
    private List<Expense> expenses;

}
