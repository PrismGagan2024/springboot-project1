package com.gagan.backend.service;

import com.gagan.backend.dto.EmployeeDTO;
import com.gagan.backend.dto.EmployeeEditDTO;
import com.gagan.backend.dto.EmployeeLoginDTO;
import com.gagan.backend.dto.ResponseDTO;
import com.gagan.backend.entity.Credential;
import com.gagan.backend.entity.Employee;
import com.gagan.backend.entity.Organization;
import com.gagan.backend.repository.CredentialRepo;
import com.gagan.backend.repository.EmployeeRepo;
import com.gagan.backend.repository.OrganizationRepo;
import com.gagan.backend.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
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

    public ResponseDTO<Employee> registerEmployeeByOrgId(Long orgId, EmployeeDTO employeeDTO) {
        Optional<Organization> organization = organizationRepo.findByOrgId(orgId);
        if(!organization.isPresent()) {
            return new ResponseDTO<>(false, 404, "Organization not found!", null, null);
        }

        Optional<Employee> employee = employeeRepo.findByEmail(employeeDTO.getEmail());
        if (employee.isPresent()) {
            return new ResponseDTO<>(false, 400, "Employee already registered", null, null);
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

        return new ResponseDTO<>(true, 201, "Employee registered successfully", null, null);
    }

    public ResponseDTO<Employee> loginEmployeeByOrgId(Long orgId, EmployeeLoginDTO loginDTO) {
        Optional<Organization> organization = organizationRepo.findByOrgId(orgId);
        if(!organization.isPresent()) {
            return new ResponseDTO<>(false, 404, "Organization not found!", null, null);
        }

        Optional<Employee> employeeOpt = employeeRepo.findByEmail(loginDTO.getEmail());
        if (!employeeOpt.isPresent()) {
            return new ResponseDTO<>(false, 400, "Employee not found!", null, null);
        }

        Employee employee = employeeOpt.get();

        if (!employee.getActive()) {
            return new ResponseDTO<>(false, 403, "Employee not found!", null, null);
        }

        Optional<Credential> employeeCredentialOpt = credentialRepo.findByEmployeeId(employee.getId());

        if (!employeeCredentialOpt.isPresent()) {
            return new ResponseDTO<>(false, 404, "Credentials not found!", null, null);
        }

        Credential employeeCredential = employeeCredentialOpt.get();

        if (!passwordEncoder.matches(loginDTO.getPassword(), employeeCredential.getPassword())) {
            return new ResponseDTO<>(false, 401, "Incorrect email or password!", null, null);
        }

        Map<String, Object> payload = Map.of(
                "employeeId", employee.getId(),
                "name", employee.getFirstName() + " " + employee.getLastName(),
                "email", employee.getEmail()
        );

        String token = JwtUtil.generateToken(employee.getEmail(), payload);

        return new ResponseDTO<>(true, 200, "Login successful",token, employee);
    }

    public ResponseDTO<List<Employee>> getAllEmployeeByOrgId(Long orgId) {
        Optional<Organization> organization = organizationRepo.findByOrgId(orgId);
        if(!organization.isPresent()) {
            return new ResponseDTO<>(false, 404, "Organization not found!", null, null);
        }

        List<Employee> employees = employeeRepo.findByOrganizationIdAndActiveTrue(orgId);

        return new ResponseDTO<>(true, 200, "Employees retrieved successfully",null,  employees);
    }

    public ResponseDTO<Employee> deleteEmployee(String empId) {
        Optional<Employee> employee = employeeRepo.findById(empId);
        if (!employee.isPresent()) {
            return new ResponseDTO<>(false, 404, "Employee not found", null, null);
        }

        Employee mainEmployee = employee.get();

        mainEmployee.setActive(false);
        employeeRepo.save(mainEmployee);

        return new ResponseDTO<>(true, 200, "Employee deleted successfully", null, null);
    }

    public ResponseDTO<Employee> editEmployee(String empId, EmployeeEditDTO employeeEditDTO) {

        System.out.println(employeeEditDTO);

        Optional<Employee> employeeOptional = employeeRepo.findByIdAndActiveTrue(empId);

        if (employeeOptional.isEmpty()) {
            return new ResponseDTO<>(false, 404, "Employee not found", null, null);
        }

        Employee existingEmployee = employeeOptional.get();

        if (employeeEditDTO.getFirstName() != null) {
            existingEmployee.setFirstName(employeeEditDTO.getFirstName());
        }
        if (employeeEditDTO.getLastName() != null) {
            existingEmployee.setLastName(employeeEditDTO.getLastName());
        }
        if (employeeEditDTO.getEmail() != null) {
            existingEmployee.setEmail(employeeEditDTO.getEmail());
        }
        if (employeeEditDTO.getContact() != null) {
            existingEmployee.setContact(employeeEditDTO.getContact());
        }
        if (employeeEditDTO.getAddress() != null) {
            existingEmployee.setAddress(employeeEditDTO.getAddress());
        }

        Employee updatedEmployee = employeeRepo.save(existingEmployee);

        return new ResponseDTO<>(true, 200, "Employee updated successfully",null, updatedEmployee);
    }

}
