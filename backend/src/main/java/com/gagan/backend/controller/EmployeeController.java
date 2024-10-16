package com.gagan.backend.controller;

import com.gagan.backend.dto.EmployeeDTO;
import com.gagan.backend.dto.EmployeeLoginDTO;
import com.gagan.backend.dto.ResponseDTO;
import com.gagan.backend.entity.Employee;
import com.gagan.backend.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/emp")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/register")
    @CrossOrigin
    public ResponseEntity<ResponseDTO<Employee>> registerEmployeeByOrgId(@RequestParam(name = "org") String orgId, @RequestBody EmployeeDTO employeeDTO) {
        ResponseDTO<Employee> response = employeeService.registerEmployeeByOrgId(orgId, employeeDTO);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @PostMapping("/login")
    @CrossOrigin
    public ResponseEntity<ResponseDTO<Employee>> loginEmployeeByOrgId(@RequestParam(name = "org") String orgId, @RequestBody EmployeeLoginDTO loginDTO) {
        ResponseDTO<Employee> response = employeeService.loginEmployeeByOrgId(orgId, loginDTO);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @GetMapping("/all")
    @CrossOrigin
    public ResponseEntity<ResponseDTO<List<Employee>>> getAllEmployeeByOrgId(@RequestParam(name = "org") String orgId) {
        ResponseDTO<List<Employee>> response = employeeService.getAllEmployeeByOrgId(orgId);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @DeleteMapping("/delete/{id}")
    @CrossOrigin
    public ResponseEntity<ResponseDTO<Employee>> deleteEmployee(@PathVariable("id") String empId) {
        ResponseDTO<Employee> response = employeeService.deleteEmployee(empId);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @PutMapping("/update/{id}")
    @CrossOrigin
    public ResponseEntity<ResponseDTO<Employee>> updateEmployee(@PathVariable("id") String empId, EmployeeDTO employeeDTO) {
        ResponseDTO<Employee> response = employeeService.updateEmployee(empId, employeeDTO);
        return ResponseEntity.status(response.getStatus()).body(response);
    }
}
