package com.fin.sight.database.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "tbl_recurring")
@Data
public class Recurring {
    @Id
    @Column(name = "recurring_id", nullable = false)
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
    private String estimatedAmount;
    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;
    @ManyToOne
    @JoinColumn(name = "account_id", referencedColumnName = "account_id", insertable = false, updatable = false)
    private Accounts accounts;
    @ManyToOne
    @JoinColumn(name = "user_guid", referencedColumnName = "guid", insertable = false, updatable = false)
    private User user;
}
