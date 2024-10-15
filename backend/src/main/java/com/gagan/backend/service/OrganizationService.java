package com.gagan.backend.service;

import com.gagan.backend.dto.OrganizationDTO;
import com.gagan.backend.dto.ResponseDTO;
import com.gagan.backend.entity.Organization;
import com.gagan.backend.repository.OrganizationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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

    public ResponseDTO<List<Organization>> getAllOrganisation() {
        List<Organization> organizations = organizationRepo.findAll();
        return new ResponseDTO<>(true, 200, "Organization retrieved successfully", organizations);
    }

    public ResponseDTO<Organization> getOrganisationById(String orgId) {
        Optional<Organization> organization = organizationRepo.findById(orgId);
        if(!organization.isPresent()) {
            return new ResponseDTO<>(false, 404, "Organization not found!", null);
        }

        Organization resOrg = organization.get();
        return new ResponseDTO<>(true, 200, "Organization retrieved successfully", resOrg);
    }

    public ResponseDTO<Organization> editOrganizationById(String orgId, OrganizationDTO organizationDTO) {
        Optional<Organization> organization = organizationRepo.findById(orgId);
        if(!organization.isPresent()) {
            return new ResponseDTO<>(false, 404, "Organization not found!", null);
        }

        Organization updatedOrganization = organization.get();

        if(organizationDTO.getName() != null) {
            updatedOrganization.setName(organizationDTO.getName());
        }
        if(organizationDTO.getEmail() != null) {
            updatedOrganization.setEmail(organizationDTO.getEmail());
        }
        if(organizationDTO.getAddress() != null) {
            updatedOrganization.setAddress(organizationDTO.getAddress());
        }
        if(organizationDTO.getContact() != null) {
            updatedOrganization.setContact(organizationDTO.getContact());
        }

        Organization resOrg = organizationRepo.save(updatedOrganization);

        return new ResponseDTO<>(true, 201, "Organization updated successfully", resOrg);
    }
}
