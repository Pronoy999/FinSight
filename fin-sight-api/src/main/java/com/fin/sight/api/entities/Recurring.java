package com.fin.sight.api.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "tbl_recurring")
@Data
public class Recurring {
    @Id
    @Column(name = "recurring_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "account_id", nullable = false)
    private long accountId;

    @Column(name = "user_guid", nullable = false)
    private String userGuid;

    @Column(name = "nature")
    private String nature;

    @Column(name = "type")
    private String type;

    @Column(name = "frequency", nullable = false)
    private String frequency;

    @Column(name = "transfer_type", nullable = false)
    private String transferType;

    @Column(name = "estimated_amount", nullable = false)
    private BigDecimal estimatedAmount;

    @CreationTimestamp
    private LocalDateTime created;

    @UpdateTimestamp
    private LocalDateTime updated;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "account_id", referencedColumnName = "account_id", insertable = false, updatable = false)
    private Accounts accounts;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "user_guid", referencedColumnName = "guid", insertable = false, updatable = false)
    private User user;
}
