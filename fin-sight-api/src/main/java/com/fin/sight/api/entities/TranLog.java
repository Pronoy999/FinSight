package com.fin.sight.api.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "tbl_tran_log")
@Data
public class TranLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "user_guid", nullable = false)
    private String userGuid;

    @Column(nullable = false)
    private Integer year;

    @Column(nullable = false)
    private Integer month;

    @Column(nullable = false)
    private Integer date;

    @Column(name = "account_id", nullable = false)
    private Long accountId;

    @Column(name = "txn_frequency", nullable = false)
    private String txnFrequency;

    @Column(name = "recurring_id")
    private Long recurringId;

    @Column(name = "transfer_type", nullable = false)
    private String transferType;

    @Column(name = "txn_amount", nullable = false, precision = 10, scale = 2)
    private BigDecimal txnAmount;

    @CreationTimestamp
    private LocalDateTime created;

    @UpdateTimestamp
    private LocalDateTime updated;

    // Relationships

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_guid", referencedColumnName = "guid", insertable = false, updatable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", referencedColumnName = "account_id", insertable = false, updatable = false)
    @JsonIgnore
    private Accounts account;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "txn_category_id", referencedColumnName = "id", nullable = false)
    private TxnCategory txnCategory;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "txn_sub_category_id", referencedColumnName = "id")
    private TxnSubCategory txnSubCategory;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recurring_id", referencedColumnName = "recurring_id", insertable = false, updatable = false)
    @JsonIgnore
    private Recurring recurring;
}
