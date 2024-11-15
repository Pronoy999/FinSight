package com.fin.sight.database.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "tbl_tran_log")
@Data
public class TranLog {
    @Id
    private long id;
    @Column(name = "user_guid", nullable = false)
    private String userGuid;
    @Column(name = "year", nullable = false)
    private int year;
    @Column(name = "month", nullable = false)
    private int month;
    @Column(name = "date", nullable = false)
    private String date;
    @Column(name = "account_name", nullable = false)
    private String accountName;
    @Column(name = "txn_category", nullable = false)
    private String txnCategory;
    @Column(name = "txn_sub_category", nullable = false)
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
    private LocalDateTime createTime;
    @UpdateTimestamp
    private LocalDateTime updateTime;
}
