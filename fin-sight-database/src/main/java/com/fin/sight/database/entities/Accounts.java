package com.fin.sight.database.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "tbl_accounts")
@Data
public class Accounts {
    @Id
    @Column(name = "account_id", nullable = false)
    private long accountId;
    @Column(name = "account_name", nullable = false)
    private String accountName;
    @Column(name = "account_type")
    private String accountType;
    @Column(name = "user_guid", nullable = false)
    private String userGuid;
    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;
    @ManyToOne
    @JoinColumn(name = "user_guid", referencedColumnName = "guid", insertable = false, updatable = false)
    private User user;
}
