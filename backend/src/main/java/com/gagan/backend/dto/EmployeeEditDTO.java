package com.gagan.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeEditDTO {
    private String firstName;
    private String lastName;
    private String email;
    private Long contact;
    private String address;
}
