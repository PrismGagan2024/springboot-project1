package com.gagan.backend.repository;

import com.gagan.backend.entity.Organization;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrganizationRepo extends MongoRepository<Organization, String> {
    Optional<Organization> findByEmail(String email);
}
