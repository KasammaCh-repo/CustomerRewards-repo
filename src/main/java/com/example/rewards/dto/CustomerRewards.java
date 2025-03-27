package com.example.rewards.dto;

import com.example.rewards.entity.Transactions;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CustomerRewards {
    private String customerId;
    private Integer totalPoints;
    private List<MonthlyPoints> monthlyPoints;
    private List<Transactions> transactionsList;
}
