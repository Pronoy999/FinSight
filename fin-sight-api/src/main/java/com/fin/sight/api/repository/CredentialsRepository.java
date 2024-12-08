package com.fin.sight.api.repository;

import com.fin.sight.api.entities.Credentials;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CredentialsRepository extends JpaRepository<Credentials, Integer> {
    List<Credentials> findByEmailIdAndIsActive(String emailId, boolean active);

    Credentials findByEmailIdAndPasswordAndIsActive(String emailId, String password, boolean active);
}
