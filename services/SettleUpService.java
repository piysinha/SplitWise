package com.scaler.splitwise.services;

import com.scaler.splitwise.Repositories.ExpenseRepositories;
import com.scaler.splitwise.Repositories.GroupRepositories;
import com.scaler.splitwise.Repositories.UserExpenseRepositories;
import com.scaler.splitwise.Repositories.UserRepositories;
import com.scaler.splitwise.models.*;
import com.scaler.splitwise.strategies.SettleUpStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class SettleUpService {

    private GroupRepositories groupRepositories;
    private ExpenseRepositories expenseRepositories;
    private SettleUpStrategy settleUpStrategy;
    private UserRepositories userRepositories;
    private UserExpenseRepositories userExpenseRepositories;

    @Autowired
    public SettleUpService(GroupRepositories groupRepositories, ExpenseRepositories expenseRepositories, SettleUpStrategy settleUpStrategy, UserRepositories userRepositories, UserExpenseRepositories userExpenseRepositories) {
        this.groupRepositories = groupRepositories;
        this.expenseRepositories = expenseRepositories;
        this.settleUpStrategy = settleUpStrategy;
        this.userRepositories = userRepositories;
        this.userExpenseRepositories = userExpenseRepositories;
    }


    public List<Transaction> settleUpUser(Long userId){
        //1. Get the user object from the user id.
        Optional <User> optionalUser = userRepositories.findById(userId);
        if(optionalUser.isEmpty()){
            throw new RuntimeException("User not found");
        }
        User user = optionalUser.get();

        //2. Get all the UserExpense object, for this user.
        List<UserExpense> userExpenses = userExpenseRepositories.findAllByUser(user);

        //3. Get an expense from the User expense objects
        Set<Expense> expenses = new HashSet<>();
        for(UserExpense userExpense : userExpenses){
            expenses.add(userExpense.getExpense());
        }

        //4. Get the settleUpTransaction, given the expenses
        List<Transaction> settleUpTransaction = settleUpStrategy.settleUp(expenses.stream().toList());

        //5. From settleUpTransaction get only those transaction
        //   where user is involved settleUpUserTransactions
        List<Transaction> finalSettleUpTransaction = new ArrayList<>();
        for(Transaction transaction: settleUpTransaction){
            if(transaction.getPayingUser()==user || transaction.getRecevingUser()==user){
                finalSettleUpTransaction.add(transaction);
            }
        }

        //6. Return this finalSettleUpTransactionForUser.
        return finalSettleUpTransaction;
    }

    public List<Transaction> settleUpGroup(Long groupId){
        //1. Get the group object using group id
        Optional<Group> optionalGroup = groupRepositories.findById(groupId);
        if(optionalGroup.isEmpty()){
            throw new RuntimeException("Group not found");
        }
        Group group = optionalGroup.get();

        //2. Get all the expenses.
        List <Expense> expenses = expenseRepositories.findAllByGroup(group);

        //3. Run the algorithm to get the list to settleUp transaction
        //   there are 2 ways to get the settle up transactions.
        List<Transaction> settleUpTransactions = settleUpStrategy.settleUp(expenses);

        //4. return the Transactions list
        return settleUpTransactions;
    }
}

// R
// expense1 (individual): whoPaid: R = 500,
//               hadToPay: R = 200, J = 100, Sh = 200

// expense2 (group): whoPaid: R = 1000, Sh = 100
//                hadToPay: R = 500, J = 300, Sh = 700

// expense3 (individual): whoPaid: Sh = 500
//          hadToPay: R = 200, J = 300


// expense4 (group): whoPaid: J = 1000
//          hadToPay: J = 200, Sh = 300


// run the strategy
// settleUpTransactions.
// R -> Sh : 500
// J -> R : 200
// Sh -> J : 1000

//    final settleUpUserTransactions
// R -> Sh : 500
// J -> R : 200