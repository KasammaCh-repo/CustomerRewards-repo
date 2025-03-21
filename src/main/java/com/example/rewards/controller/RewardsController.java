package com.example.rewards.controller;

import com.example.rewards.dto.CustomerRewards;
import com.example.rewards.service.RewardsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Map;

@RestController
@RequestMapping("/rewards")
public class RewardsController {

    @Autowired
    private RewardsService rewardsService;

    @GetMapping("/getRewardPoints/{customerId}")
    public CustomerRewards getCustomerRewards(@PathVariable String customerId,
                                              @RequestParam(value ="startDate")  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
                                              @RequestParam(value = "endDate")  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate){
        return rewardsService.getCustomerRewards(customerId, startDate, endDate);
    }
}
