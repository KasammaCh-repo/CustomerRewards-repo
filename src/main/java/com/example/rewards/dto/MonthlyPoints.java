package com.example.rewards.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MonthlyPoints {
    private String transactionMonth;
    private Integer rewardPoints;
}
