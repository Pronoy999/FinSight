package com.fin.sight.api.repository;

import com.fin.sight.api.entities.Recurring;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecurringRepository extends JpaRepository<Recurring, Integer> {
}
