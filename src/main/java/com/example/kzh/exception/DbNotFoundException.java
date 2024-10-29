package com.example.kzh.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
public class DbNotFoundException extends RuntimeException {
    private String error;
    private String message;

}
