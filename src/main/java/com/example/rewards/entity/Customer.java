package com.example.rewards.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@Table
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
    private String name;

    /*@OneToMany(mappedBy = "customerId")
    private List<Transactions> transactionsList;
*/
}
