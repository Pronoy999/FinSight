package com.fin.sight.api.repository;

import com.fin.sight.api.entities.Accounts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountsRepository extends JpaRepository<Accounts, Integer> {
    List<Accounts> findAccountsByUserGuid(String userGuidId);
}
