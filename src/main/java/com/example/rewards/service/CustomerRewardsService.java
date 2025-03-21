package com.example.rewards.service;

import com.example.rewards.dto.CustomerRewards;
import com.example.rewards.entity.Transactions;
import com.example.rewards.repository.TransactionsRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.time.LocalDate;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
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

    public CustomerRewards getCustomerRewards(String customerId, LocalDate startDate, LocalDate endDate) {
        CustomerRewards customerRewards = new CustomerRewards();
        List<Transactions> transactionsList = transactionsRepository.findByCustomerIdAndDateBetween(customerId, startDate.toString(), endDate.toString());
        Map<String, Integer> monthlyPoints = getMonthlyPoints(transactionsList);
        int totalPoints = transactionsList.stream().mapToInt(t -> calculatePoints(t.getAmount())).sum();
        customerRewards.setCustomerId(customerId);
        customerRewards.setTotalPoints(totalPoints);
        customerRewards.setMonthlyPoints(monthlyPoints);
        customerRewards.setTransactionsList(transactionsList);
        return customerRewards;
    }

    public Map<String, Integer> getMonthlyPoints(List<Transactions> transactionsList){
        Function<Transactions, String> monthValue = t -> String.valueOf(t.getDate().getMonthValue());
        Map<String, List<Transactions>> groupedByMonth = transactionsList.stream()
                .collect(Collectors.groupingBy(monthValue));
        Map<String, Integer> monthlyPoints = new HashMap<>();
        for(Map.Entry<String, List<Transactions>> entry : groupedByMonth.entrySet()){
            String month = entry.getKey();
            List<Transactions> monthlyTransactions = entry.getValue();




            int monthTotalPoints = monthlyTransactions.stream()
                    .mapToInt(t -> calculatePoints(t.getAmount())).sum();
            monthlyPoints.put(month, monthTotalPoints);
        }
        return monthlyPoints;
    }
}
