package com.fin.sight.api.repository;

import com.fin.sight.api.entities.Recurring;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecurringRepository extends JpaRepository<Recurring, Integer> {

    @Query("SELECT r FROM Recurring r JOIN FETCH r.accounts WHERE r.userGuid = :userGuid")
    List<Recurring> findAllWithAccountByUserGuid(@Param("userGuid") String userGuid);
}
