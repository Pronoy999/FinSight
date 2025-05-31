package com.fin.sight.api.repository;

import com.fin.sight.api.entities.TranLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TranLogRepository extends JpaRepository<TranLog, Integer> {
    TranLog findByIdAndUserGuid(Integer id, String userGuid);
}
