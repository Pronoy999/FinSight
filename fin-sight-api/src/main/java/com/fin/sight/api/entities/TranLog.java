package com.fin.sight.api.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

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
    @Column(name = "year", nullable = false)
    private int year;
    @Column(name = "month", nullable = false)
    private int month;
    @Column(name = "date", nullable = false)
    private String date;
    @Column(name = "account_id", nullable = false)
    private String accountId;
    @Column(name = "txn_category", nullable = false)
    private String txnCategory;
    @Column(name = "txn_sub_category")
    private String txnSubCategory;
    @Column(name = "txn_nature", nullable = false)
    private String txnNature;
    @Column(name = "txn_frequency", nullable = false)
    private String txnFrequency;
    @Column(name = "transfer_type", nullable = false)
    private String transferType;
    @Column(name = "estimated_amount", nullable = false)
    private float estimatedAmount;
    @Column(name = "actual_amount", nullable = false)
    private float actualAmount;
    @CreationTimestamp
    private LocalDateTime created;
    @UpdateTimestamp
    private LocalDateTime updated;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_guid", referencedColumnName = "guid", insertable = false, updatable = false)
    private User user;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", referencedColumnName = "account_id", insertable = false, updatable = false)
    private Accounts account;
}
