package com.gagan.backend.service;

import com.gagan.backend.dto.EmployeeDTO;
import com.gagan.backend.dto.EmployeeLoginDTO;
import com.gagan.backend.dto.ResponseDTO;
import com.gagan.backend.entity.Credential;
import com.gagan.backend.entity.Employee;
import com.gagan.backend.entity.Organization;
import com.gagan.backend.repository.CredentialRepo;
import com.gagan.backend.repository.EmployeeRepo;
import com.gagan.backend.repository.OrganizationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {
    @Autowired
    private EmployeeRepo employeeRepo;

    @Autowired
    private OrganizationRepo organizationRepo;

    @Autowired
    private CredentialRepo credentialRepo;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(12);

    public ResponseDTO<Employee> registerEmployeeByOrgId(String orgId, EmployeeDTO employeeDTO) {
        Optional<Organization> organization = organizationRepo.findById(orgId);
        if(!organization.isPresent()) {
            return new ResponseDTO<>(false, 404, "Organization not found!", null);
        }

        Optional<Employee> employee = employeeRepo.findByEmail(employeeDTO.getEmail());
        if (employee.isPresent()) {
            return new ResponseDTO<>(false, 400, "Employee already registered", null);
        }

        Employee resEmployee = new Employee();
        resEmployee.setFirstName(employeeDTO.getFirstName());
        resEmployee.setLastName(employeeDTO.getLastName());
        resEmployee.setEmail(employeeDTO.getEmail());
        resEmployee.setAddress(employeeDTO.getAddress());
        resEmployee.setContact(employeeDTO.getContact());
        resEmployee.setOrganizationId(orgId);
        resEmployee.setActive(true);

        Employee savedEmployee = employeeRepo.save(resEmployee);

        String hashedPassword = passwordEncoder.encode(employeeDTO.getPassword());

        Credential credential = new Credential();
        credential.setPassword(hashedPassword);
        credential.setEmployeeId(savedEmployee.getId());

        credentialRepo.save(credential);

        return new ResponseDTO<>(true, 201, "Employee registered successfully", null);
    }

    public ResponseDTO<Employee> loginEmployeeByOrgId(String orgId, EmployeeLoginDTO loginDTO) {
        Optional<Organization> organization = organizationRepo.findById(orgId);
        if(!organization.isPresent()) {
            return new ResponseDTO<>(false, 404, "Organization not found!", null);
        }

        Optional<Employee> employeeOpt = employeeRepo.findByEmail(loginDTO.getEmail());
        if (!employeeOpt.isPresent()) {
            return new ResponseDTO<>(false, 400, "Employee not found!", null);
        }

        Employee employee = employeeOpt.get();

        if (!employee.getActive()) {
            return new ResponseDTO<>(false, 403, "Employee not found!", null);
        }

        Optional<Credential> employeeCredentialOpt = credentialRepo.findByEmployeeId(employee.getId());

        if (!employeeCredentialOpt.isPresent()) {
            return new ResponseDTO<>(false, 404, "Credentials not found!", null);
        }

        Credential employeeCredential = employeeCredentialOpt.get();

        if (!passwordEncoder.matches(loginDTO.getPassword(), employeeCredential.getPassword())) {
            return new ResponseDTO<>(false, 401, "Incorrect email or password!", null);
        }

        return new ResponseDTO<>(true, 200, "Login successful", employee);
    }

    public ResponseDTO<List<Employee>> getAllEmployeeByOrgId(String orgId) {
        Optional<Organization> organization = organizationRepo.findById(orgId);
        if(!organization.isPresent()) {
            return new ResponseDTO<>(false, 404, "Organization not found!", null);
        }

        List<Employee> employees = employeeRepo.findByOrganizationIdAndActiveTrue(orgId);

        return new ResponseDTO<>(true, 200, "Employees retrieved successfully", employees);
    }

    public ResponseDTO<Employee> deleteEmployee(String empId) {
        Optional<Employee> employee = employeeRepo.findById(empId);
        if (!employee.isPresent()) {
            return new ResponseDTO<>(false, 404, "Employee not found", null);
        }

        Employee mainEmployee = employee.get();

        mainEmployee.setActive(false);
        employeeRepo.save(mainEmployee);

        return new ResponseDTO<>(true, 200, "Employee deleted successfully", null);
    }

    public ResponseDTO<Employee> editEmployee(String empId, EmployeeDTO employeeDTO) {
        Optional<Employee> employee = employeeRepo.findByIdAndActiveTrue(empId);
        if (!employee.isPresent()) {
            return new ResponseDTO<>(false, 404, "Employee not found", null);
        }

        Employee updatedEmployee = employee.get();

        if(employeeDTO.getFirstName() != null){
            updatedEmployee.setFirstName(employeeDTO.getFirstName());
        }
        if(employeeDTO.getLastName() != null){
            updatedEmployee.setLastName(employeeDTO.getLastName());
        }
        if(employeeDTO.getEmail() != null){
            updatedEmployee.setEmail(employeeDTO.getEmail());
        }
        if(employeeDTO.getContact() != null){
            updatedEmployee.setContact(employeeDTO.getContact());
        }
        if(employeeDTO.getAddress() != null){
            updatedEmployee.setAddress(employeeDTO.getAddress());
        }

        Employee resEmployee = employeeRepo.save(updatedEmployee);

        return new ResponseDTO<>(true, 201, "Employee updated successfully", resEmployee);
    }
}
