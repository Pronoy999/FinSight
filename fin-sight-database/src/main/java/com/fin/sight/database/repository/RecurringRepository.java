package com.fin.sight.database.repository;

import com.fin.sight.database.entities.Recurring;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecurringRepository extends JpaRepository<Recurring, Integer> {
}
