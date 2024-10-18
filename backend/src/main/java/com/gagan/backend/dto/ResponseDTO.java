package com.gagan.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseDTO<T> {
    private boolean success;
    private int status;
    private String message;
    private String token;
    private T data;
}
