package com.gagan.backend.controller;

import com.gagan.backend.dto.OrganizationDTO;
import com.gagan.backend.dto.ResponseDTO;
import com.gagan.backend.entity.Organization;
import com.gagan.backend.service.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/org")
public class OrganizationController {
    @Autowired
    private OrganizationService organizationService;

    @PostMapping("/register")
    @CrossOrigin
    public ResponseEntity<ResponseDTO<Organization>> registerOrganization(@RequestBody OrganizationDTO organizationDTO) {
        ResponseDTO<Organization> response = organizationService.registerOrganization(organizationDTO);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @GetMapping("/all")
    @CrossOrigin
    public ResponseEntity<ResponseDTO<List<Organization>>> getAllOrganisation() {
        ResponseDTO<List<Organization>> response = organizationService.getAllOrganisation();
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @GetMapping("/{id}")
    @CrossOrigin
    public ResponseEntity<ResponseDTO<Organization>> getOrganisationById(@PathVariable("id") String orgId) {
        ResponseDTO<Organization> response = organizationService.getOrganisationById(orgId);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @PutMapping("/edit/{id}")
    @CrossOrigin
    public ResponseEntity<ResponseDTO<Organization>> updateOrganization(@PathVariable("id") String orgId, @RequestBody OrganizationDTO organizationDTO) {
        ResponseDTO<Organization> response = organizationService.editOrganizationById(orgId, organizationDTO);
        return ResponseEntity.status(response.getStatus()).body(response);
    }
}
