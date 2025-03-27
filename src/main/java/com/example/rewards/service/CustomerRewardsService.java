package com.example.rewards.service;

import com.example.rewards.dto.CustomerRewards;
import com.example.rewards.dto.MonthlyPoints;
import com.example.rewards.entity.Transactions;
import com.example.rewards.exception.CustomerNotFoundException;
import com.example.rewards.exception.InvalidDateRangeException;
import com.example.rewards.repository.TransactionsRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.time.LocalDate;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class CustomerRewardsService {

    @Autowired
    private TransactionsRepository transactionsRepository;

    public Integer calculatePoints(Double amount){
        int points = 0;
        if (amount > 100){
            points += (int) ((amount -100) * 2);
            amount = 100.0;
        }
        if (amount > 50){
            points += (int) (amount - 50);
        }
        return points;
    }

    public CustomerRewards getCustomerRewards(String customerId, LocalDate startDate, LocalDate endDate) throws CustomerNotFoundException, InvalidDateRangeException {
        log.info("Calculating reward points for Customer:{} ", customerId);
        CustomerRewards customerRewards = new CustomerRewards();
        if(startDate.isAfter(endDate)) {
            throw new InvalidDateRangeException("Start date must be before end date");
        }
        List<Transactions> transactionsList = transactionsRepository.findByCustomerIdAndDateBetween(customerId, startDate.toString(), endDate.toString());
        if(transactionsList.isEmpty()) {
            throw new CustomerNotFoundException("Customer not found: " +customerId);
        }
        List<MonthlyPoints> monthlyPoints = getMonthlyPoints(transactionsList);
        // calculating total points for the given time frame
        int totalPoints = transactionsList.stream()
                .mapToInt(t -> calculatePoints(t.getAmount())).sum();
        log.info("Total points calculated:{}", totalPoints);
        customerRewards.setCustomerId(customerId);
        customerRewards.setTotalPoints(totalPoints);
        customerRewards.setMonthlyPoints(monthlyPoints);
        customerRewards.setTransactionsList(transactionsList);
        return customerRewards;
    }

    public List<MonthlyPoints> getMonthlyPoints(List<Transactions> transactionsList){
        Map<String, Integer> monthlyPointsMap = new HashMap<>();
        // Group transactions by month and calculate points for each month
        transactionsList.stream()
                .collect(Collectors.groupingBy(
                        t -> t.getDate().getMonth().toString(),
                        Collectors.summingInt(t -> calculatePoints(t.getAmount()))
                )).forEach(monthlyPointsMap::put);
        List<MonthlyPoints> monthlyPoints = monthlyPointsMap.entrySet().stream()
                        .map(entry -> new MonthlyPoints(entry.getKey(), entry.getValue()))
                                .collect(Collectors.toList());
        log.info("Monthly points calculated:{}", monthlyPoints);
        return monthlyPoints;
    }
}
