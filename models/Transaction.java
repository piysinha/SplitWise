package com.scaler.splitwise.models;

import com.scaler.splitwise.models.enums.TransactionStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
// Response Object
// This is not stored in the table
public class Transaction {
    private User payingUser;

    private User recevingUser;

    private Integer amount;

    private TransactionStatus transactionStatus;

    public Transaction() {

    }

    public Transaction(User payingUser,User recevingUser,Integer amount,TransactionStatus transactionStatus){
        this.payingUser = payingUser;
        this.recevingUser = recevingUser;
        this.amount = amount;
        this.transactionStatus = transactionStatus;
    }

}
