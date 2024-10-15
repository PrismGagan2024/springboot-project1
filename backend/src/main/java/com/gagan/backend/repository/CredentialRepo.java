package com.gagan.backend.repository;

import com.gagan.backend.entity.Credential;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CredentialRepo extends MongoRepository<Credential, String> {
    Optional<Credential> findByEmployeeId(String employeeId);
}
