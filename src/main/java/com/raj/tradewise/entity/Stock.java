package com.raj.tradewise.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Stock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true , nullable = false)
    private String symbol;

    @Column(nullable = false)
    private String companyName;
    private String exchange;
    private String sector;

    @OneToMany(
            mappedBy = "stock"
    )
    private List<Holding> holdings = new ArrayList<>();



}
