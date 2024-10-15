package com.gagan.backend.controller;

import com.gagan.backend.dto.OrganizationDTO;
import com.gagan.backend.dto.ResponseDTO;
import com.gagan.backend.entity.Organization;
import com.gagan.backend.service.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/org")
public class OrganizationController {
    @Autowired
    private OrganizationService organizationService;

    @PostMapping("/register")
    public ResponseEntity<ResponseDTO<Organization>> registerOrganization(@RequestBody OrganizationDTO organizationDTO) {
        ResponseDTO<Organization> response = organizationService.registerOrganization(organizationDTO);
        return ResponseEntity.status(response.getStatus()).body(response);
    }
}
