package com.fin.sight.database.repository;

import com.fin.sight.database.entities.TranLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TranLogInterface extends JpaRepository<TranLog, Integer> {
}
