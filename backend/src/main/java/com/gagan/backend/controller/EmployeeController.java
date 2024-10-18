package com.gagan.backend.controller;

import com.gagan.backend.dto.EmployeeDTO;
import com.gagan.backend.dto.EmployeeEditDTO;
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
    public ResponseEntity<ResponseDTO<Employee>> registerEmployeeByOrgId(@RequestParam(name = "org") Long orgId, @RequestBody EmployeeDTO employeeDTO) {
        ResponseDTO<Employee> response = employeeService.registerEmployeeByOrgId(orgId, employeeDTO);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @PostMapping("/login")
    @CrossOrigin
    public ResponseEntity<ResponseDTO<Employee>> loginEmployeeByOrgId(@RequestParam(name = "org") Long orgId, @RequestBody EmployeeLoginDTO loginDTO) {
        ResponseDTO<Employee> response = employeeService.loginEmployeeByOrgId(orgId, loginDTO);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @GetMapping("/all")
    @CrossOrigin
    public ResponseEntity<ResponseDTO<List<Employee>>> getAllEmployeeByOrgId(@RequestParam(name = "org") Long orgId) {
        ResponseDTO<List<Employee>> response = employeeService.getAllEmployeeByOrgId(orgId);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @DeleteMapping("/delete/{id}")
    @CrossOrigin
    public ResponseEntity<ResponseDTO<Employee>> deleteEmployee(@PathVariable("id") String empId) {
        ResponseDTO<Employee> response = employeeService.deleteEmployee(empId);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @PutMapping("/edit/{id}")
    @CrossOrigin
    public ResponseEntity<ResponseDTO<Employee>> editEmployee(@PathVariable("id") String empId, @RequestBody EmployeeEditDTO employeeEditDTO) {
        ResponseDTO<Employee> response = employeeService.editEmployee(empId, employeeEditDTO);
        return ResponseEntity.status(response.getStatus()).body(response);
    }
}
