package com.example.rewards.service;

import com.example.rewards.dto.CustomerRewards;
import com.example.rewards.dto.MonthlyPoints;
import com.example.rewards.entity.Transactions;
import com.example.rewards.exception.CustomerNotFoundException;
import com.example.rewards.exception.InvalidDateRangeException;
import com.example.rewards.repository.TransactionsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class CustomerRewardsServiceTest {

    @Mock
    private TransactionsRepository transactionsRepository;
    @Mock
    private CustomerRewardsService rewardsService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    public void testGetCustomerRewards() throws CustomerNotFoundException, InvalidDateRangeException {
        TransactionsRepository transactionsRepository = Mockito.mock(TransactionsRepository.class);
        CustomerRewardsService service = new CustomerRewardsService(transactionsRepository);
        String customerId = "10001";
        LocalDate startDate = LocalDate.of(2023, 1, 1);
        LocalDate endDate = LocalDate.of(2023, 3, 31);
        List<Transactions> transactions = Arrays.asList(
                new Transactions(1L,customerId, 150.0, startDate),
                new Transactions(2L, customerId, 200.0, endDate)
        );
        Mockito.when(transactionsRepository.findByCustomerIdAndDateBetween(customerId, "2023-01-01", "2023-03-31"))
                .thenReturn(transactions);
        List<MonthlyPoints> mockMonthlyPoints = Arrays.asList(
                new MonthlyPoints("FEBRUARY", 200),
                new MonthlyPoints("MARCH", 150)
        );
        Mockito.doReturn(mockMonthlyPoints).when(rewardsService).getMonthlyPoints(Mockito.anyList());
        Mockito.doReturn(50).when(rewardsService).calculatePoints((double) Mockito.anyInt());
        CustomerRewards rewards = service.getCustomerRewards(customerId, startDate, endDate);

        assertEquals(customerId, rewards.getCustomerId());
        assertEquals(400, rewards.getTotalPoints());
        assertEquals(mockMonthlyPoints.size(), rewards.getMonthlyPoints().size());
        assertEquals(transactions, rewards.getTransactionsList());
    }

    @Test
    public void testGetCustomerRewards_EmptyTransactionList() {
        TransactionsRepository transactionsRepository = Mockito.mock(TransactionsRepository.class);
        CustomerRewardsService service = new CustomerRewardsService(transactionsRepository);
        String customerId = "67890";
        LocalDate startDate = LocalDate.of(2023, 1, 1);
        LocalDate endDate = LocalDate.of(2023, 1, 31);

        Mockito.when(transactionsRepository.findByCustomerIdAndDateBetween(customerId, "2023-01-01", "2023-01-31"))
                .thenReturn(Collections.emptyList());
        assertThrows(CustomerNotFoundException.class, () ->
                service.getCustomerRewards(customerId, startDate, endDate)
        );
    }

    @Test
    public void testGetCustomerRewards_InvalidDateRange() {
        TransactionsRepository transactionsRepository = Mockito.mock(TransactionsRepository.class);
        CustomerRewardsService service = new CustomerRewardsService(transactionsRepository);
        String customerId = "12345";
        LocalDate startDate = LocalDate.of(2023, 4, 1);
        LocalDate endDate = LocalDate.of(2023, 1, 31);

        assertThrows(InvalidDateRangeException.class, () ->
                service.getCustomerRewards(customerId, startDate, endDate)
        );
    }

}
