package com.example.rewards.dto;

import com.example.rewards.entity.Transactions;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CustomerRewards {
    private String customerId;
    private Integer totalPoints;
    private Map<String, Integer> monthlyPoints;
    private List<Transactions> transactionsList;
}
