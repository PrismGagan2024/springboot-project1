package com.gagan.backend.service;

import com.gagan.backend.dto.OrganizationRegisterDTO;
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

    @Autowired
    private SequenceGenerator sequenceGenerator;

    public ResponseDTO<Organization> registerOrganization(OrganizationRegisterDTO organizationRegisterDTO) {
        Optional<Organization> organizationOpt = organizationRepo.findByEmail(organizationRegisterDTO.getEmail());
        if(organizationOpt.isPresent()) {
            return new ResponseDTO<>(false, 400, "Organization already exist!", null, null);
        }

        Organization org = new Organization();
        org.setOrgId(sequenceGenerator.getNextSequence());

        org.setName(organizationRegisterDTO.getName());
        org.setEmail(organizationRegisterDTO.getEmail());
        org.setAddress(organizationRegisterDTO.getAddress());
        org.setContact(organizationRegisterDTO.getContact());

        organizationRepo.save(org);
        return new ResponseDTO<>(true, 201, "Organization created successfully", null, null);
    }

    public ResponseDTO<List<Organization>> getAllOrganisation() {
        List<Organization> organizations = organizationRepo.findAll();
        return new ResponseDTO<>(true, 200, "Organization retrieved successfully",null, organizations);
    }

    public ResponseDTO<Organization> getOrganisationById(Long orgId) {
        Optional<Organization> organization = organizationRepo.findByOrgId(orgId);
        System.out.println(organization);
        if(!organization.isPresent()) {
            return new ResponseDTO<>(false, 404, "Organization not found!", null, null);
        }

        Organization resOrg = organization.get();
        return new ResponseDTO<>(true, 200, "Organization retrieved successfully",null, resOrg);
    }

    public ResponseDTO<Organization> editOrganizationById(Long orgId, OrganizationRegisterDTO organizationRegisterDTO) {
        Optional<Organization> organizationOptional = organizationRepo.findByOrgId(orgId);

        if (organizationOptional.isEmpty()) {
            return new ResponseDTO<>(false, 404, "Organization not found!", null, null);
        }

        Organization existingOrganization = organizationOptional.get();

        if (organizationRegisterDTO.getName() != null) {
            existingOrganization.setName(organizationRegisterDTO.getName());
        }
        if (organizationRegisterDTO.getEmail() != null) {
            existingOrganization.setEmail(organizationRegisterDTO.getEmail());
        }
        if (organizationRegisterDTO.getAddress() != null) {
            existingOrganization.setAddress(organizationRegisterDTO.getAddress());
        }
        if (organizationRegisterDTO.getContact() != null) {
            existingOrganization.setContact(organizationRegisterDTO.getContact());
        }

        Organization updatedOrganization = organizationRepo.save(existingOrganization);

        return new ResponseDTO<>(true, 200, "Organization updated successfully", null, updatedOrganization);
    }

}
