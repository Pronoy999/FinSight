package com.fin.sight.api.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "txn_category")
@Data
public class TxnCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String nature;
}
