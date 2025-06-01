package com.fin.sight.api.repository;

import com.fin.sight.api.entities.Credentials;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CredentialsRepository extends JpaRepository<Credentials, Integer> {
    List<Credentials> findByEmailIdAndIsActive(String emailId, boolean active);

    Credentials findByEmailIdAndPasswordAndIsActive(String emailId, String password, boolean active);

    @Modifying
    @Query("UPDATE Credentials c SET c.thirdPartyToken = :token, c.updated = CURRENT_TIMESTAMP WHERE c.emailId = :emailId")
    int updateThirdPartyTokenByEmailId(@Param("token") String token, @Param("emailId") String emailId);
}
