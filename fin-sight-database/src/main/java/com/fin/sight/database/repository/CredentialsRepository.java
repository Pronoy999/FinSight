package com.fin.sight.database.repository;

import com.fin.sight.database.entities.Credentials;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CredentialsRepository extends JpaRepository<Credentials, Integer> {
}
