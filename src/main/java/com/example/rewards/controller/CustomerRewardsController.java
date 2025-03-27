package com.example.rewards.controller;

import com.example.rewards.dto.CustomerRewards;
import com.example.rewards.exception.CustomerNotFoundException;
import com.example.rewards.exception.InvalidDateRangeException;
import com.example.rewards.service.CustomerRewardsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.time.LocalDate;

@RestController
@RequestMapping("/rewards")
public class CustomerRewardsController {

    @Autowired
    private CustomerRewardsService rewardsService;

    @GetMapping("/getRewardPoints/{customerId}")
    public CustomerRewards getCustomerRewards(@PathVariable String customerId, @RequestParam(value ="startDate")  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
                                              @RequestParam(value = "endDate")  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) throws CustomerNotFoundException, InvalidDateRangeException {
        return rewardsService.getCustomerRewards(customerId, startDate, endDate);
    }
}
