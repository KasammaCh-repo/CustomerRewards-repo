package com.example.rewards.controller;

import com.example.rewards.dto.CustomerRewards;
import com.example.rewards.exception.CustomerNotFoundException;
import com.example.rewards.exception.InvalidDateRangeException;
import com.example.rewards.service.CustomerRewardsService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CustomerRewardsController.class)
public class CustomerRewardsControllerTest {

    @MockitoBean
    private CustomerRewardsService rewardsService;
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testGetCustomerRewards_ValidRequest() throws Exception {
        String customerId = "12345";
        LocalDate startDate = LocalDate.of(2023, 1, 1);
        LocalDate endDate = LocalDate.of(2023, 3, 31);
        CustomerRewards mockRewards = new CustomerRewards();
        mockRewards.setCustomerId(customerId);
        mockRewards.setTotalPoints(100);
        Mockito.when(rewardsService.getCustomerRewards(customerId, startDate, endDate))
                .thenReturn(mockRewards);
        mockMvc.perform(get("/rewards/getRewardPoints/{customerId}", customerId)
                        .param("startDate", "2023-01-01")
                        .param("endDate", "2023-03-31"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customerId").value(customerId))
                .andExpect(jsonPath("$.totalPoints").value(100));
    }

    @Test
    public void testGetCustomerRewards_CustomerNotFound() throws Exception {
        String customerId = "99999";
        Mockito.when(rewardsService.getCustomerRewards(customerId, LocalDate.of(2023, 1, 1), LocalDate.of(2023, 3, 31)))
                .thenThrow(new CustomerNotFoundException("Customer not found: " + customerId));
        mockMvc.perform(get("/rewards/getRewardPoints/{customerId}", customerId)
                        .param("startDate", "2023-01-01")
                        .param("endDate", "2023-03-31"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Customer not found: " + customerId));
    }

    @Test
    public void testGetCustomerRewards_InvalidDateRange() throws Exception {
        String customerId = "12345";
        Mockito.when(rewardsService.getCustomerRewards(customerId, LocalDate.of(2023, 4, 1), LocalDate.of(2023, 1, 31)))
                .thenThrow(new InvalidDateRangeException("Start date must be before end date"));
        mockMvc.perform(get("/rewards/getRewardPoints/{customerId}", customerId)
                        .param("startDate", "2023-04-01")
                        .param("endDate", "2023-01-31"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Start date must be before end date"));
    }

    @Test
    public void testGetCustomerRewards_MissingRequestParams() throws Exception {
        String customerId = "12345";
        mockMvc.perform(get("/rewards/getRewardPoints/{customerId}", customerId))
                .andExpect(status().isBadRequest());
    }
}

