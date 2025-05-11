package com.fin.sight.api.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Random;

@Entity
@Table(name = "tbl_accounts")
@Data
public class Accounts {
    @Id
    @Column(name = "account_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long accountId;
    @Column(name = "account_name", nullable = false)
    private String accountName;
    @Column(name = "account_type")
    private String accountType;
    @Column(name = "user_guid", nullable = false)
    private String userGuid;
    @CreationTimestamp
    private LocalDateTime created;
    @UpdateTimestamp
    private LocalDateTime updated;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_guid", referencedColumnName = "guid", insertable = false, updatable = false)
    @JsonIgnore
    private User user;
}
