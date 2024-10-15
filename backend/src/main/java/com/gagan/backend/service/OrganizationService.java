package com.gagan.backend.service;

import com.gagan.backend.dto.OrganizationDTO;
import com.gagan.backend.dto.ResponseDTO;
import com.gagan.backend.entity.Organization;
import com.gagan.backend.repository.OrganizationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OrganizationService {
    @Autowired
    private OrganizationRepo organizationRepo;

    public ResponseDTO<Organization> registerOrganization(OrganizationDTO organizationDTO) {
        Optional<Organization> organizationOpt = organizationRepo.findByEmail(organizationDTO.getEmail());
        if(organizationOpt.isPresent()) {
            return new ResponseDTO<>(false, 400, "Organization already exist!", null);
        }

        Organization org = new Organization();

        org.setName(organizationDTO.getName());
        org.setEmail(organizationDTO.getEmail());
        org.setAddress(organizationDTO.getAddress());
        org.setContact(organizationDTO.getContact());

        organizationRepo.save(org);
        return new ResponseDTO<>(true, 201, "Organization created successfully", null);
    }
}
